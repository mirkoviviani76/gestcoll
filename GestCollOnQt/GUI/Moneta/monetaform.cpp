#include "monetaform.h"
#include "ui_monetaform.h"
#include <commondata.h>
#include <QMessageBox>
#include <letteraturadialog.h>
#include <notadialog.h>
#include <autoritadialog.h>
#include <legendadialog.h>
#include <zecchieredialog.h>
#include <utils.h>
#include <QFileInfo>
#include <vassoioform.h>
#include <QDateTime>
#include <gestlog.h>
#include <posizionedialog.h>
#include "descrizionedialog.h"
#include <QDebug>
#include <QClipboard>
#include <QColorDialog>
#include <QUrl>
#include <QInputDialog>
#include <QDesktopServices>
#include "collezionexml.h"
#include "xsltconverter.h"
#include "nuovamonetadialog.h"
#include "adddocumentdialog.h"
#include "setambitodialog.h"
#include "setcollezioneinfodialog.h"
#include "setimmaginemonetadialog.h"
#include "elencomonetedelegate.h"

#define ACTION_ADD ("Aggiungi")
#define ACTION_DEL ("Elimina")
#define ACTION_EDIT ("Modifica in finestra speciale")
#define ACTION_COPY_ID ("Copia l'id della moneta negli appunti")
#define ACTION_COPY ("Copia la descrizione della moneta negli appunti")

#define ACTION_SORT_BY_ID ("Ordina in base all'id")
#define ACTION_SORT_BY_COUNTRY ("Ordina in base al paese")
#define ACTION_SORT_BY_YEAR ("Ordina in base all'anno")
#define ACTION_SORT_BY_TYPE ("Ordina in base alla valuta")
#define ACTION_SORT_BY_CATEGORY ("Ordina in base all'ambito")

#define TAB_MONETA  (0) ///< id tab moneta
#define TAB_VASSOI  (1) ///< id tab vassoi


bool signalsAreBlocked = false;

/**
 * @brief Costruttore
 *
 * @param parent
*/
MonetaForm::MonetaForm(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::MonetaForm)
{
    ui->setupUi(this);

    this->ui->splitter->setStretchFactor(0, 0);
    this->ui->splitter->setStretchFactor(1, 1);

    this->collezioneModel = NULL;
    this->item = NULL;
    //legge i dati dei vassoi
    this->vassoi = new Posizioni();
    //carica i modelli per le monete e i vassoi
    this->setupModels();

    //setta il delegato per le monete
    this->ui->itemList->setItemDelegate(new ElencoMoneteDelegate(this->ui->itemList));

    this->enableEdit(false);
    this->contextMenu.addAction(ACTION_ADD);
    this->contextMenu.addAction(ACTION_DEL);
    this->contextMenu.addAction(ACTION_EDIT);
    this->contextMenu.addAction(ACTION_COPY_ID);
    this->contextMenu.addAction(ACTION_COPY);

    this->contextMenuForMoneteList.addAction(ACTION_SORT_BY_ID);
    this->contextMenuForMoneteList.addAction(ACTION_SORT_BY_COUNTRY);
    this->contextMenuForMoneteList.addAction(ACTION_SORT_BY_YEAR);
    this->contextMenuForMoneteList.addAction(ACTION_SORT_BY_TYPE);
    this->contextMenuForMoneteList.addAction(ACTION_SORT_BY_CATEGORY);

    this->contextMenuForAmbiti.addAction(ACTION_EDIT);

    this->contextMenuForImg.addAction(ACTION_EDIT);


    this->contextMenuEnableAction(ACTION_ADD, false);
    this->contextMenuEnableAction(ACTION_DEL, false);
    this->contextMenuEnableAction(ACTION_EDIT, false);
    this->contextMenuEnableAction(ACTION_COPY_ID, true);
    this->contextMenuEnableAction(ACTION_COPY, true);


    this->editingEnabled = false;
    this->ui->autorita->setContextMenuPolicy(Qt::CustomContextMenu);
    this->ui->letteratura->setContextMenuPolicy(Qt::CustomContextMenu);
    this->ui->note->setContextMenuPolicy(Qt::CustomContextMenu);
    this->ui->legendeDritto->setContextMenuPolicy(Qt::CustomContextMenu);
    this->ui->legendeRovescio->setContextMenuPolicy(Qt::CustomContextMenu);
    this->ui->legendeTaglio->setContextMenuPolicy(Qt::CustomContextMenu);
    this->ui->descrizioneDritto->setContextMenuPolicy(Qt::CustomContextMenu);
    this->ui->descrizioneRovescio->setContextMenuPolicy(Qt::CustomContextMenu);
    this->ui->descrizioneTaglio->setContextMenuPolicy(Qt::CustomContextMenu);
    this->ui->documenti->setContextMenuPolicy(Qt::CustomContextMenu);
    this->ui->zecchieri->setContextMenuPolicy(Qt::CustomContextMenu);
    this->ui->id->setContextMenuPolicy(Qt::CustomContextMenu);
    this->ui->ambiti->setContextMenuPolicy(Qt::CustomContextMenu);
    this->ui->imgDritto->setContextMenuPolicy(Qt::CustomContextMenu);
    this->ui->imgRovescio->setContextMenuPolicy(Qt::CustomContextMenu);
    this->ui->imgTaglio->setContextMenuPolicy(Qt::CustomContextMenu);

    this->ui->itemList->setContextMenuPolicy(Qt::CustomContextMenu);

    this->ui->tabMoneta->setVisible(false);

    /* non funziona il connect by name.... boh! */
    connect(this->ui->ambiti, SIGNAL(customContextMenuRequested(QPoint)), this, SLOT(on_ambiti_customContextMenuRequested(QPoint)));
    connect(this->ui->ambiti, SIGNAL(doubleClicked(QModelIndex)), this, SLOT(on_ambiti_doubleClicked(QModelIndex)));

    connect(this->ui->imgDritto, SIGNAL(customContextMenuRequested(QPoint)),
            this, SLOT(on_imgDritto_customContextMenuRequested(QPoint)));
    connect(this->ui->imgRovescio, SIGNAL(customContextMenuRequested(QPoint)),
            this, SLOT(on_imgRovescio_customContextMenuRequested(QPoint)));
    connect(this->ui->imgTaglio, SIGNAL(customContextMenuRequested(QPoint)),
            this, SLOT(on_imgTaglio_customContextMenuRequested(QPoint)));
}

/**
 * @brief Distruttore
 *
*/
MonetaForm::~MonetaForm()
{
    delete ui;
    if (modelloNote != NULL)
    {
        this->modelloNote->clear();
        delete modelloNote;
        this->modelloNote = NULL;
    }
    if (modelloZecchieri != NULL)
    {
        this->modelloZecchieri->clear();
        delete modelloZecchieri;
        this->modelloZecchieri = NULL;
    }
    if (modelloAutorita != NULL)
    {
        this->modelloAutorita->clear();
        delete modelloAutorita;
        this->modelloAutorita = NULL;
    }
    if (modelloLetteratura != NULL)
    {
        this->modelloLetteratura->clear();
        delete modelloLetteratura;
        this->modelloLetteratura = NULL;
    }
    if (modelloDoc != NULL)
    {
        this->modelloDoc->clear();
        delete modelloDoc;
        this->modelloDoc = NULL;
    }
    if (modelloLegendaT != NULL)
    {
        this->modelloLegendaT->clear();
        delete modelloLegendaT;
        this->modelloLegendaT = NULL;
    }
    if (modelloLegendaD != NULL)
    {
        this->modelloLegendaD->clear();
        delete modelloLegendaD;
        this->modelloLegendaD = NULL;
    }
    if (modelloLegendaR != NULL)
    {
        this->modelloLegendaR->clear();
        delete modelloLegendaR;
        this->modelloLegendaR = NULL;
    }
    if (modelloAmbiti != NULL)
    {
        this->modelloAmbiti->clear();
        delete modelloAmbiti;
        this->modelloAmbiti = NULL;
    }
    if (this->collezioneModel != NULL)
    {
        this->collezioneModel->clear();
        delete this->collezioneModel;
        this->collezioneModel = NULL;
    }
    if (this->vassoi != NULL)
    {
        delete this->vassoi;
        this->vassoi = NULL;
    }

}

void MonetaForm::addMoneta() {
    NuovaMonetaDialog nm(this);
    //apre un dialog preimpostato con la posizione e la dimensione
    nm.setParameters(0, 0, 0, 0, "A");
    int ret = nm.exec();
    if (ret == QDialog::Accepted)
    {
        MonetaXml* added = nm.getNuovaMoneta();
        //ricarica i dati del modello
        this->addItem(added);
        //ricarica i dati del vassoio
        emit this->newIdAdded(added);
    }
}


void MonetaForm::tabVassoiRemoveItem(MonetaXml* moneta)
{
    QString idTab = moneta->getIdVassoio();
    /* rimuove la cella  */
    VassoioForm* vf = (VassoioForm*)(this->ui->tabsVassoi->widget(this->tabVassoi[idTab]));
    vf->setData(moneta->getRiga(),
                moneta->getColonna(),
                NULL);
}

void MonetaForm::setupTabVassoi(MonetaXml* moneta)
{
    QString idTab = moneta->getIdVassoio();
    if (!tabVassoi.contains(idTab))
    {
        //ottiene il numero di righe e di colonne per il vassoio corrente
        VassoioForm* currVassoio = new VassoioForm(this->ui->tabsVassoi);
        qApp->processEvents();
        connect(currVassoio, SIGNAL(idChangeRequest(QString)), this, SLOT(idChanged(QString)));
        connect(currVassoio, SIGNAL(newIdAdded(MonetaXml*)), this, SLOT(addItem(MonetaXml*)));

        VassoioXml* v = this->vassoi->getVassoio(idTab);
        if (v == NULL) {
            QString messg = QString("ERRORE: vassoio e/o contenitore errati in %1").arg(moneta->getId());
            Log::Logger::getInstance()->log(messg, Log::ERR);
        } else {
            currVassoio->setSize(v->getIdContenitore(),
                                 v->getIdVassoio(),
                                 v->getRighe(),
                                 v->getColonne(),
                                 v->getDimensione());
            //aggiunge una tab per il nuovo vassoio
            int curr = this->ui->tabsVassoi->addTab(currVassoio, idTab);
            //salva l'id della tab
            this->tabVassoi.insert(idTab, curr);
        }


    }
    /* aggiunge l'id nella tab */
    VassoioForm* vf = (VassoioForm*)(this->ui->tabsVassoi->widget(this->tabVassoi[idTab]));
    vf->setModel();
    vf->setData(moneta->getRiga(),
                moneta->getColonna(),
                moneta);
}

void MonetaForm::setupModelMonete()
{
    if (collezioneModel != NULL)
        delete collezioneModel;

    collezioneModel = new CollezioneSortFilterProxyModel(this);
    this->ui->tabsVassoi->removeTab(1);
    this->ui->tabsVassoi->removeTab(0);

    foreach (QString id, CollezioneXml::getInstance()->getAllId())
    {
        MonetaXml* moneta = CollezioneXml::getInstance()->getMoneta(id);
        this->collezioneModel->appendRow(moneta);
        this->setupTabVassoi(moneta);
    }

    foreach (int id, this->tabVassoi.values()) {
        VassoioForm* vf = (VassoioForm*)(this->ui->tabsVassoi->widget(id));
        vf->resizeRows();
    }
    //ordina per id
    this->collezioneModel->sort(0, Qt::AscendingOrder);
    //setta il modello
    this->ui->itemList->setModel(this->collezioneModel);

}

void MonetaForm::setupModels()
{
    modelloNote = new GenericModel();
    modelloZecchieri = new GenericModel(3);
    modelloAutorita = new GenericModel();
    modelloLetteratura = new GenericModel(2);
    modelloDoc = new GenericModel();
    modelloLegendaD = new GenericModel();
    modelloLegendaR = new GenericModel();
    modelloLegendaT = new GenericModel();
    modelloAmbiti = new GenericModel(2);
    this->setupModelMonete();

    this->ui->letteratura->resizeRowsToContents();
    this->ui->zecchieri->resizeColumnsToContents();
}

void MonetaForm::changeEvent(QEvent *e)
{
    QWidget::changeEvent(e);
    switch (e->type()) {
    case QEvent::LanguageChange:
        ui->retranslateUi(this);
        break;
    default:
        break;
    }
}

void MonetaForm::setModel(GenericModel* model)
{
    this->ui->itemList->setModel(model);
}


void MonetaForm::loadData()
{
    this->blockSignals(true);
    signalsAreBlocked = true;
    //inibisce la segnalazione delle modifiche
    this->ui->id->setText(item->getId());
    this->ui->paese->setText(item->getPaese());
    this->ui->anno->setText(item->getAnno());
    xml::Nominale nom = item->getNominale();
    this->ui->nominale->setData(nom.valore, nom.valuta);

    xml::Zecca zec = item->getZecca();
    this->ui->zecca->setData(zec.nome,
                             zec.segno);

    this->ui->posizione->setText(QString("%1-%2-%3-%4")
                               .arg(item->getContenitore())
                               .arg(item->getVassoio())
                               .arg(item->getRiga())
                               .arg(item->getColonna()));
    this->ui->luogo->setText(item->getLuogo());
    this->ui->data->clear();
    QDate xmldata = item->getData();
    this->ui->data->setDisplayFormat("dd/MM/yyyy");
    this->ui->data->setDate(xmldata);
    this->ui->prezzo->setData(item->getPrezzo());

    this->modelloNote->clear();
    this->modelloZecchieri->clear();
    this->modelloAutorita->clear();
    this->modelloLetteratura->clear();
    this->modelloDoc->clear();
    this->modelloLegendaD->clear();
    this->modelloLegendaR->clear();
    this->modelloLegendaT->clear();
    this->modelloAmbiti->clear();

    /* legende dritto */

    foreach (xml::Legenda* a, item->getLegende(Moneta::DRITTO))
    {
        this->modelloLegendaD->appendRow(a);
    }
    this->ui->legendeDritto->setModel(this->modelloLegendaD);

    /* legende rovescio */
    foreach (xml::Legenda* a, item->getLegende(Moneta::ROVESCIO))
    {
        this->modelloLegendaR->appendRow(a);
    }
    this->ui->legendeRovescio->setModel(this->modelloLegendaR);

    /* legende taglio */
    foreach (xml::Legenda* a, item->getLegende(Moneta::TAGLIO))
    {
        this->modelloLegendaT->appendRow(a);
    }
    this->ui->legendeTaglio->setModel(this->modelloLegendaT);

    /* aggiunge le note */
    foreach (xml::Nota* a, item->getNote())
    {
        this->modelloNote->appendRow(a);
    }
    this->ui->note->setModel(this->modelloNote);


    /* aggiunge le autorita */
    foreach (xml::Autorita* a, item->getAutorita())
    {
        this->modelloAutorita->appendRow(a);
    }
    this->ui->autorita->setModel(this->modelloAutorita);


    /* aggiunge gli zecchieri */
    foreach (xml::Zecchiere* a, item->getZecchieri())
    {
        this->modelloZecchieri->appendRow(a);
    }
    this->ui->zecchieri->setModel(this->modelloZecchieri);


    /* aggiunge la letteratura */
    //TODO ASTE?
    foreach (xml::Libro* a, item->getLetteratura())
    {
        this->modelloLetteratura->appendRow(a);
    }
    this->ui->letteratura->setModel(this->modelloLetteratura);

    /* aggiunge i documenti */
    foreach (xml::Documento* a, item->getItemAddizionali())
    {
        this->modelloDoc->appendRow(a);
    }
    this->ui->documenti->setModel(this->modelloDoc);

    /* aggiunge gli ambiti */
    foreach (xml::Ambito* a, item->getAmbiti())
    {
        this->modelloAmbiti->appendRow(a);
    }
    this->ui->ambiti->setModel(this->modelloAmbiti);


    this->ui->forma->setText(item->getForma());
    this->ui->metallo->setText(item->getMetallo());
    this->ui->peso->setData(item->getPeso());
    this->ui->dimensione->setData(item->getDiametro());
    this->ui->descrizioneDritto->blockSignals(true);
    this->ui->descrizioneDritto->setText(item->getDescrizione(Moneta::DRITTO));
    this->ui->descrizioneDritto->blockSignals(false);

    this->ui->descrizioneRovescio->blockSignals(true);
    this->ui->descrizioneRovescio->setText(item->getDescrizione(Moneta::ROVESCIO));
    this->ui->descrizioneRovescio->blockSignals(false);

    this->ui->descrizioneTaglio->blockSignals(true);
    this->ui->descrizioneTaglio->setText(item->getDescrizione(Moneta::TAGLIO));
    this->ui->descrizioneTaglio->blockSignals(false);


    QString fileImgD = item->getImg(Moneta::DRITTO);
    this->ui->imgDritto->setupImg(fileImgD);

    QString fileImgR = item->getImg(Moneta::ROVESCIO);
    this->ui->imgRovescio->setupImg(fileImgR);

    QString fileImgT = item->getImg(Moneta::TAGLIO);
    this->ui->imgTaglio->setupImg(fileImgT);

#if 0
    foreach (Asta* a, item->letteratura.aste)
    {
        QString asta = QString("Asta: %1 %2 %3 %4 (%5 %6) (%7 %8)")
               .arg(a->casa)
               .arg(a->idAsta)
               .arg(a->data)
               .arg(a->lotto)
               .arg(a->stima.valore)
               .arg(a->stima.unita)
               .arg(a->aggiudicazione.valore)
               .arg(a->aggiudicazione.unita);
        this->ui->textEdit->append(asta);
    }
#endif

    /* prepara il led di stato */
    xml::Stato stato = this->item->getStato();
    this->ui->led->setOffColor1(QColor(stato.colore));
    this->ui->led->setOffColor2(QColor(stato.colore));
    this->ui->led->setOnColor1(QColor(stato.colore));
    this->ui->led->setOnColor2(QColor(stato.colore));
    this->ui->led->setToolTip(stato.motivo);
    this->ui->led->setChecked(true);

    //resize delle righe per le tabelle
    this->ui->letteratura->verticalHeader()->resizeSections(QHeaderView::ResizeToContents);
    this->ui->zecchieri->verticalHeader()->resizeSections(QHeaderView::ResizeToContents);


    //riabilita la segnalazione delle modifiche
    this->blockSignals(false);

    signalsAreBlocked = false;
}

void MonetaForm::on_itemList_activated(QModelIndex index)
{
    this->item = this->collezioneModel->getItem(index);

    // seleziona il tab monete
    if (this->ui->Tabs->currentIndex() != TAB_MONETA)
        this->ui->Tabs->setCurrentIndex(TAB_MONETA);
    if (this->ui->tabMoneta->isVisible() == false)
        this->ui->tabMoneta->setVisible(true);

    //ricarica i valori nella form
    this->loadData();

    /* seleziona la casella nei vassoi */
    QString idTab = this->item->getIdVassoio();
    if (this->tabVassoi.contains(idTab))
    {
        /* setta il vassoio giusto */
        int indexTab = this->tabVassoi[idTab];
        this->ui->tabsVassoi->setCurrentIndex(indexTab);
        VassoioForm* vf = (VassoioForm*)(this->ui->tabsVassoi->widget(indexTab));
        /* seleziona l'indice giusto nella tabella */
        int riga = this->item->getRiga();
        int colonna = this->item->getColonna();
        vf->setCurrentIndex(riga, colonna);
    }


}

void MonetaForm::enableEdit(bool editable)
{
    this->editingEnabled = editable;

    /* sistema le azioni del menu contestuale */
    this->contextMenuEnableAction(ACTION_ADD, editable);
    this->contextMenuEnableAction(ACTION_DEL, editable);
    this->contextMenuEnableAction(ACTION_EDIT, editable);
    this->contextMenuEnableAction(ACTION_COPY_ID, true);
    this->contextMenuEnableAction(ACTION_COPY, true);


    //abilita-disabilita gli item
    this->ui->paese->setReadOnly(!editable);
    this->ui->anno->setReadOnly(!editable);
    this->ui->led->setEnabled(editable);
    //this->ui->posizione->setEnabled(editable);
    //this->ui->posizione->setReadOnly(!editable);
    this->ui->luogo->setReadOnly(!editable);
    this->ui->data->setReadOnly(!editable);
    this->ui->forma->setReadOnly(!editable);
    this->ui->metallo->setReadOnly(!editable);
    this->ui->descrizioneDritto->setReadOnly(!editable);
    this->ui->descrizioneRovescio->setReadOnly(!editable);
    this->ui->descrizioneTaglio->setReadOnly(!editable);
    this->ui->zecca->enableEdit(editable);
    this->ui->nominale->enableEdit(editable);
    this->ui->dimensione->enableEdit(editable);
    this->ui->peso->enableEdit(editable);
    this->ui->prezzo->enableEdit(editable);
}



/************************************************************************
  GESTIONE EDITING
*************************************************************************/

void MonetaForm::on_paese_textChanged(QString newText)
{
    this->item->setPaese(newText);
    emit this->changesOccurred();
}


void MonetaForm::on_anno_textChanged(QString newText)
{
    this->item->setAnno(newText);
    emit this->changesOccurred();

}

void MonetaForm::on_forma_textChanged(QString  newText)
{
    this->item->setForma(newText);
    emit this->changesOccurred();
}

void MonetaForm::on_metallo_textChanged(QString  newText)
{
    this->item->setMetallo(newText);
    emit this->changesOccurred();
}

void MonetaForm::on_luogo_textChanged(QString  newText)
{
    this->item->setLuogo(newText);
    emit this->changesOccurred();
}

void MonetaForm::on_descrizioneDritto_textChanged()
{
    QString newText = this->ui->descrizioneDritto->toPlainText();
    this->item->setDescrizione(Moneta::DRITTO, newText);
    emit this->changesOccurred();
}

void MonetaForm::on_descrizioneRovescio_textChanged()
{
    QString newText = this->ui->descrizioneRovescio->toPlainText();
    this->item->setDescrizione(Moneta::ROVESCIO, newText);
    emit this->changesOccurred();
}

void MonetaForm::on_descrizioneTaglio_textChanged()
{
    QString newText = this->ui->descrizioneTaglio->toPlainText();
    this->item->setDescrizione(Moneta::TAGLIO, newText);
    emit this->changesOccurred();
}

void MonetaForm::on_dimensione_textChanged(QString valore, QString unita)
{
    qreal val = valore.toDouble();
    this->item->setDimensione(val, unita);
    emit this->changesOccurred();
}

void MonetaForm::on_nominale_textChanged(QString valore, QString unita)
{
    this->item->setNominale(valore, unita);
    emit this->changesOccurred();
}

void MonetaForm::on_peso_textChanged(QString valore, QString unita)
{
    qreal val = valore.toDouble();
    this->item->setPeso(val, unita);
    emit this->changesOccurred();

}

void MonetaForm::on_prezzo_textChanged(QString valore, QString unita)
{
    qreal val = valore.toDouble();
    this->item->setPrezzo(val, unita);
    emit this->changesOccurred();
}

void MonetaForm::on_zecca_textChanged(QString nome, QString segno)
{
    this->item->setZecca(nome, segno);
    emit this->changesOccurred();
}


/**
  Ricarica il documento corrente da file
  */
void MonetaForm::reload()
{
    if (this->item != NULL)
    {
        //ricarica i valori nella form
        this->loadData();
    }
}

/**
  Salva il documento corrente
  */
void MonetaForm::salva()
{
    bool ret = false;
    //genera il nuovo filename
    QDateTime now = QDateTime::currentDateTime();
    QString backup = QString("%1.xml")
            .arg(now.toString("yyyyMMddhhmmss"));
    QString backupDir = CommonData::getInstance()->getBackupDir();
    QString backupfile = QString("%1/%2")
            .arg(backupDir)
            .arg(backup);
    //salva il backup compresso
    ret = Utils::saveAndBackup(CommonData::getInstance()->getCollezione(), backupfile);

    if (ret)
    {
        //salvataggio
        ret = CollezioneXml::getInstance()->save();
    }
    if (ret)
    {
        QString msg = QString("Salvato");
        QMessageBox::information(NULL, "GestColl", msg);
        Log::Logger::getInstance()->log(msg, Log::INFO);
    }
    else
        QMessageBox::warning(NULL, "GestColl", "Salvataggio fallito.");

}


void MonetaForm::on_note_doubleClicked(QModelIndex index)
{
    GenericModel* model = (GenericModel*)index.model();
    xml::Nota* vecchia = (xml::Nota*) model->getItem(index);
    NotaDialog notaDialog(this->editingEnabled, this);
    notaDialog.setData(vecchia);
    int ret = notaDialog.exec();
    if (ret == QDialog::Accepted && this->editingEnabled)
    {
        xml::Nota testo;
        notaDialog.getData(&testo);
        /* modifica/aggiunge il nodo al dom */
        this->item->setNota(*vecchia, testo);
        this->loadData();
        //segnala la modifica
        emit this->changesOccurred();

    }
}

void MonetaForm::on_autorita_doubleClicked(QModelIndex index)
{
    if (this->editingEnabled)
    {
        GenericModel* model = (GenericModel*)index.model();
        xml::Autorita* vecchio = (xml::Autorita*) model->getItem(index);
        AutoritaDialog autoritaDialog(this);
        autoritaDialog.setData(vecchio);
        int ret = autoritaDialog.exec();
        if (ret == QDialog::Accepted)
        {
            QString nuovoNome;
            autoritaDialog.getData(&nuovoNome);
            xml::Autorita nuovo(nuovoNome);
            /* modifica/aggiunge il nodo al dom */
            this->item->setAutorita(*vecchio, nuovo);
            this->loadData();
            //segnala la modifica
            emit this->changesOccurred();
        }
    }
}

void MonetaForm::on_letteratura_doubleClicked(QModelIndex index)
{
    if (this->editingEnabled)
    {
        GenericModel* model = (GenericModel*)index.model();
        xml::Libro* vecchio = (xml::Libro*) model->getItem(index);
        LetteraturaDialog libroDialog(this);
        libroDialog.setData(vecchio);
        int ret = libroDialog.exec();
        if (ret == QDialog::Accepted)
        {
            QString sigla;
            QString numero;
            libroDialog.getData(&sigla, &numero);
            /* modifica/aggiunge il nodo al dom */
            xml::Libro libro(sigla, numero);
            this->item->setLibro(*vecchio, libro);
            this->loadData();
            //segnala la modifica
            emit this->changesOccurred();
        }
    }
}

void MonetaForm::gestLegendaModifica(Moneta::Lato lato, QModelIndex index)
{
    GenericModel* model = (GenericModel*)index.model();
    xml::Legenda* vecchio = (xml::Legenda*) model->getItem(index);
    LegendaDialog legendaDialog(this);
    legendaDialog.setData(vecchio);
    int ret = legendaDialog.exec();
    if (ret == QDialog::Accepted && this->editingEnabled)
    {
        QString testo;
        QString scioglimento;
        legendaDialog.getData(&testo, &scioglimento);
        xml::Legenda nuovo(testo, scioglimento);
        /* modifica/aggiunge il nodo al dom */
        this->item->setLegenda(lato, *vecchio, nuovo);
        this->loadData();
        //segnala la modifica
        emit this->changesOccurred();
    }
}

void MonetaForm::on_legendeDritto_doubleClicked(QModelIndex index)
{
    gestLegendaModifica(Moneta::DRITTO, index);
}

void MonetaForm::on_legendeRovescio_doubleClicked(QModelIndex index)
{
    gestLegendaModifica(Moneta::ROVESCIO, index);
}

void MonetaForm::on_legendeTaglio_doubleClicked(QModelIndex index)
{
    gestLegendaModifica(Moneta::TAGLIO, index);
}


void MonetaForm::on_zecchieri_doubleClicked(QModelIndex index)
{
    GenericModel* model = (GenericModel*)index.model();
    xml::Zecchiere* vecchio = (xml::Zecchiere*) model->getItem(index);
    ZecchiereDialog zecchiereDialog(this->editingEnabled, this);
    zecchiereDialog.setData(vecchio);
    int ret = zecchiereDialog.exec();
    if (ret == QDialog::Accepted && this->editingEnabled)
    {
        QString nome;
        QString sigla;
        QString ruolo;
        zecchiereDialog.getData(&nome, &sigla, &ruolo);
        xml::Zecchiere nuovo(nome, sigla, ruolo);
        /* modifica/aggiunge il nodo al dom */
        this->item->setZecchiere(*vecchio, nuovo);
        this->loadData();
        //segnala la modifica
        emit this->changesOccurred();

    }
}

void MonetaForm::on_autorita_customContextMenuRequested(QPoint pos)
{
    // for most widgets
    QPoint globalPos = this->ui->autorita->mapToGlobal(pos);
    // for QAbstractScrollArea and derived classes you would use:
    // QPoint globalPos = myWidget->viewport()->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenu.exec(globalPos);
    if (selectedItem)
    {
        if (selectedItem->text() == ACTION_ADD)
        {
            /* attiva la vera gestione */
            AutoritaDialog autoritaDialog(this);
            //autoritaDialog.setData(a);
            int ret = autoritaDialog.exec();
            if (ret == QDialog::Accepted)
            {
                QString nuovoNome;
                autoritaDialog.getData(&nuovoNome);
                xml::Autorita nuovo(nuovoNome);
                /* modifica/aggiunge il nodo al dom */
                this->item->addAutorita(nuovo);
                this->loadData();
                //segnala la modifica
                emit this->changesOccurred();

            }
        } else if (selectedItem->text() == ACTION_DEL) {
            //ottiene l'indice selezionato
            int index = this->ui->autorita->currentIndex().row();
            //ottiene l'item
            xml::Autorita* a = (xml::Autorita*) this->modelloAutorita->getItem(index);
            //cancella dalla lista
            this->item->deleteAutorita(a);
            //ricarica la vista
            this->loadData();
            //segnala la modifica
            emit this->changesOccurred();


        } else if (selectedItem->text() == ACTION_COPY_ID) {
            this->gestClipboardCopyId(this->item->getId());
        } else if (selectedItem->text() == ACTION_COPY) {
            this->gestClipboardCopy(this->item->getId());
        }


    }
    else
    {
        // nothing was chosen
    }
}

void MonetaForm::on_itemList_customContextMenuRequested(const QPoint &pos)
{
#if 0
    // for most widgets
    QPoint globalPos = this->ui->itemList->mapToGlobal(pos);
    // for QAbstractScrollArea and derived classes you would use:
    // QPoint globalPos = myWidget->viewport()->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenuForMoneteList.exec(globalPos);

    int column = -1;
    if (selectedItem)
    {
        if (selectedItem->text() == ACTION_SORT_BY_ID) {
            column = 0;
        } else if (selectedItem->text() == ACTION_SORT_BY_COUNTRY) {
            column = 1;
        } else if (selectedItem->text() == ACTION_SORT_BY_TYPE) {
            column = 2;
        } else if (selectedItem->text() == ACTION_SORT_BY_YEAR) {
            column = 3;
        } else if (selectedItem->text() == ACTION_SORT_BY_CATEGORY) {
            column = 4;
        }
        this->collezioneModel->sort(column);
    }
#endif
}


void MonetaForm::on_documenti_customContextMenuRequested(QPoint pos)
{
    // for most widgets
    QPoint globalPos = this->ui->documenti->mapToGlobal(pos);
    // for QAbstractScrollArea and derived classes you would use:
    // QPoint globalPos = myWidget->viewport()->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenu.exec(globalPos);
    if (selectedItem == NULL)
        return;

    if (selectedItem->text() == ACTION_ADD) {
        AddDocumentDialog addDoc(this);
        int ret = addDoc.exec();
        if (ret == QDialog::Accepted) {
            QStringList selection = addDoc.getFilenames();
            foreach (QString file, selection) {
                xml::Documento d(file, addDoc.getDescrizione());
                this->item->addDocumento(d);
            }
            this->loadData();
            //segnala la modifica
            emit this->changesOccurred();

        } else {
        }

    } else if (selectedItem->text() == ACTION_DEL) {
        //ottiene l'indice selezionato
        int index = this->ui->documenti->currentIndex().row();
        //ottiene l'item
        xml::Documento* a = (xml::Documento*) this->modelloDoc->getItem(index);
        //cancella dalla lista
        this->item->deleteDocumento(a);
        //ricarica la vista
        this->loadData();
        //segnala la modifica
        emit this->changesOccurred();

    } else if (selectedItem->text() == ACTION_COPY_ID) {
        qDebug() << "on_documenti_customContextMenuRequested " << ACTION_COPY_ID;
    } else if (selectedItem->text() == ACTION_COPY) {
        qDebug() << "on_documenti_customContextMenuRequested " << ACTION_COPY;
    }
}

void MonetaForm::on_legendeDritto_customContextMenuRequested(QPoint pos)
{
    // for most widgets
    QPoint globalPos = this->ui->legendeDritto->mapToGlobal(pos);
    // for QAbstractScrollArea and derived classes you would use:
    // QPoint globalPos = myWidget->viewport()->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenu.exec(globalPos);
    if (selectedItem)
    {
        if (selectedItem->text() == ACTION_ADD) {
            this->addLegenda(Moneta::DRITTO);
        } else if (selectedItem->text() == ACTION_DEL) {
            //ottiene l'indice selezionato
            int index = this->ui->legendeDritto->currentIndex().row();
            //ottiene l'item
            xml::Legenda* a = (xml::Legenda*) this->modelloLegendaD->getItem(index);
            //cancella dalla lista
            this->item->deleteLegenda(Moneta::DRITTO ,a);
            //ricarica la vista
            this->loadData();
            //segnala la modifica
            emit this->changesOccurred();

        } else if (selectedItem->text() == ACTION_COPY_ID) {
            this->gestClipboardCopyId(this->item->getId());
        } else if (selectedItem->text() == ACTION_COPY) {
            this->gestClipboardCopy(this->item->getId());
        }

    }
    else
    {

    }
}

void MonetaForm::on_legendeRovescio_customContextMenuRequested(QPoint pos)
{
    // for most widgets
    QPoint globalPos = this->ui->legendeRovescio->mapToGlobal(pos);
    // for QAbstractScrollArea and derived classes you would use:
    // QPoint globalPos = myWidget->viewport()->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenu.exec(globalPos);
    if (selectedItem)
    {
        if (selectedItem->text() == ACTION_ADD) {
            this->addLegenda(Moneta::ROVESCIO);
        } else if (selectedItem->text() == ACTION_DEL) {
            //ottiene l'indice selezionato
            int index = this->ui->legendeRovescio->currentIndex().row();
            //ottiene l'item
            xml::Legenda* a = (xml::Legenda*) this->modelloLegendaR->getItem(index);
            //cancella dalla lista
            this->item->deleteLegenda(Moneta::ROVESCIO,a );
            //ricarica la vista
            this->loadData();
            //segnala la modifica
            emit this->changesOccurred();

        } else if (selectedItem->text() == ACTION_COPY_ID) {
            this->gestClipboardCopyId(this->item->getId());
        } else if (selectedItem->text() == ACTION_COPY) {
            this->gestClipboardCopy(this->item->getId());
        }
    }
    else
    {

    }
}


void MonetaForm::on_legendeTaglio_customContextMenuRequested(QPoint pos)
{
    // for most widgets
    QPoint globalPos = this->ui->legendeTaglio->mapToGlobal(pos);
    // for QAbstractScrollArea and derived classes you would use:
    // QPoint globalPos = myWidget->viewport()->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenu.exec(globalPos);
    if (selectedItem)
    {
        if (selectedItem->text() == ACTION_ADD) {
            this->addLegenda(Moneta::TAGLIO);
        } else if (selectedItem->text() == ACTION_DEL) {
            //ottiene l'indice selezionato
            int index = this->ui->legendeTaglio->currentIndex().row();
            //ottiene l'item
            xml::Legenda* a = (xml::Legenda*) this->modelloLegendaT->getItem(index);
            //cancella dalla lista
            this->item->deleteLegenda(Moneta::TAGLIO, a);
            //ricarica la vista
            this->loadData();
            //segnala la modifica
            emit this->changesOccurred();

        } else if (selectedItem->text() == ACTION_COPY_ID) {
            this->gestClipboardCopyId(this->item->getId());
        } else if (selectedItem->text() == ACTION_COPY) {
            this->gestClipboardCopy(this->item->getId());
        }
    }
    else
    {

    }
}

void MonetaForm::on_zecchieri_customContextMenuRequested(QPoint pos)
{
    // for most widgets
    QPoint globalPos = this->ui->zecchieri->mapToGlobal(pos);
    // for QAbstractScrollArea and derived classes you would use:
    // QPoint globalPos = myWidget->viewport()->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenu.exec(globalPos);
    if (selectedItem)
    {
        if (selectedItem->text() == ACTION_ADD) {
            /* attiva la vera gestione */
            ZecchiereDialog dialog(this);
            //dialog.setData(a);
            int ret = dialog.exec();
            if (ret == QDialog::Accepted)
            {
                QString nome;
                QString ruolo;
                QString sigla;
                dialog.getData(&nome, &sigla, &ruolo);
                xml::Zecchiere nuovo(nome, sigla, ruolo);
                /* modifica/aggiunge il nodo al dom */
                this->item->addZecchiere(nuovo);
                this->loadData();
                //segnala la modifica
                emit this->changesOccurred();

            }
        } else if (selectedItem->text() == ACTION_DEL) {
            qDebug() << "DEL: TODO";
        } else if (selectedItem->text() == ACTION_COPY_ID) {
            this->gestClipboardCopyId(this->item->getId());
        } else if (selectedItem->text() == ACTION_COPY) {
            this->gestClipboardCopy(this->item->getId());
        }

    }
    else
    {
        // nothing was chosen
    }
}

void MonetaForm::on_note_customContextMenuRequested(QPoint pos)
{
    // for most widgets
    QPoint globalPos = this->ui->note->mapToGlobal(pos);
    // for QAbstractScrollArea and derived classes you would use:
    // QPoint globalPos = myWidget->viewport()->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenu.exec(globalPos);
    if (selectedItem)
    {
        if (selectedItem->text() == ACTION_ADD) {
            /* aggiunge una nota di comodo */
            //xml::Nota* a = new xml::Nota();
            //this->modelloNote->appendRow(a);

            /* attiva la vera gestione */
            NotaDialog dialog(this);
            //dialog.setData(a);
            int ret = dialog.exec();
            if (ret == QDialog::Accepted)
            {
                QString nuovoTesto;
                xml::Nota n(nuovoTesto);
                dialog.getData(&n);
                /* modifica/aggiunge il nodo al dom */
                this->item->addNota(n);
                this->loadData();
                //segnala la modifica
                emit this->changesOccurred();

            }
        } else if (selectedItem->text() == ACTION_DEL) {
            //ottiene l'indice selezionato
            int index = this->ui->note->currentIndex().row();
            //ottiene l'item
            xml::Nota* n = (xml::Nota*) this->modelloNote->getItem(index);
            //cancella dalla lista
            this->item->deleteNota(n);
            //ricarica la vista
            this->loadData();
            //segnala la modifica
            emit this->changesOccurred();

        } else if (selectedItem->text() == ACTION_COPY_ID) {
            this->gestClipboardCopyId(this->item->getId());
        } else if (selectedItem->text() == ACTION_COPY) {
            this->gestClipboardCopy(this->item->getId());
        }
    }
    else
    {
        // nothing was chosen
    }
}

void MonetaForm::on_letteratura_customContextMenuRequested(QPoint pos)
{
    // for most widgets
    QPoint globalPos = this->ui->letteratura->mapToGlobal(pos);
    // for QAbstractScrollArea and derived classes you would use:
    // QPoint globalPos = myWidget->viewport()->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenu.exec(globalPos);
    if (selectedItem)
    {
        if (selectedItem->text() == ACTION_ADD) {
            /* attiva la vera gestione */
            LetteraturaDialog dialog(this);
            //dialog.setData(a);
            int ret = dialog.exec();
            if (ret == QDialog::Accepted)
            {
                QString sigla;
                QString numero;
                dialog.getData(&sigla, &numero);
                /* modifica/aggiunge il nodo al dom */
                xml::Libro nuovoLibro(sigla, numero);
                this->item->addLibro(nuovoLibro);
                this->loadData();
                //segnala la modifica
                emit this->changesOccurred();

            }
        } else if (selectedItem->text() == ACTION_DEL) {
            //ottiene l'indice selezionato
            int index = this->ui->letteratura->currentIndex().row();
            //ottiene l'item
            xml::Libro* a = (xml::Libro*) this->modelloLetteratura->getItem(index);
            //cancella dalla lista
            this->item->deleteLetteratura(a);
            //ricarica la vista
            this->loadData();
            //segnala la modifica
            emit this->changesOccurred();


        } else if (selectedItem->text() == ACTION_COPY_ID) {
            this->gestClipboardCopyId(this->item->getId());
        } else if (selectedItem->text() == ACTION_COPY) {
            this->gestClipboardCopy(this->item->getId());
        }
    }
    else
    {
        // nothing was chosen
    }
}

void MonetaForm::addLegenda(Moneta::Lato lato)
{
    /* attiva la vera gestione */
    LegendaDialog dialog(this);
    //dialog.setData(a);
    int ret = dialog.exec();
    if (ret == QDialog::Accepted)
    {
        QString testo;
        QString scioglimento;
        dialog.getData(&testo, &scioglimento);
        xml::Legenda nuovo(testo, scioglimento);
        /* modifica/aggiunge il nodo al dom */
        this->item->addLegenda(lato, nuovo);
        this->loadData();
        //segnala la modifica
        emit this->changesOccurred();

    }
}




void MonetaForm::on_data_dateChanged(QDate date)
{
    if (this->editingEnabled) {
        this->item->setData(date);
        this->loadData();
        //segnala la modifica
        emit this->changesOccurred();
    }

}


void MonetaForm::idChanged(QString id)
{
    /* cerca l'id fra gli items presenti */
    for (int i = 0 ; i < this->collezioneModel->rowCount(); i++)
    {
        // ottiene l'elemento corrente
        GenericModel* model = (GenericModel*)this->collezioneModel->sourceModel();
        MonetaXml* currIndex = (MonetaXml*)model->getItem(i);
        //confronta l'id
        if (currIndex->getId() == id)
        {
            // genera l'indice (per il proxy)
            QModelIndex ind = this->collezioneModel->mapFromSource(model->getIndex(i));
            // seleziona l'oggetto
            this->ui->itemList->setCurrentIndex(ind);
            // seleziona il tab monete
            this->ui->Tabs->setCurrentIndex(TAB_MONETA);

            // visualizza i dati
            this->on_itemList_activated(ind);
            break;
        }
    }
}

void MonetaForm::addItem(MonetaXml* newMoneta)
{
    //aggiunge al modello lista
    GenericModel* model = (GenericModel*)this->collezioneModel->sourceModel();
    model->appendRow(newMoneta);
    //model->sort(-1);
    this->collezioneModel->invalidate();
}


void MonetaForm::on_posizione_clicked()
{
    if (this->editingEnabled)
    {
        posizioneDialog pd(this);
        QString posText = this->ui->posizione->text();
        QStringList p = posText.split("-");
        pd.setData(p[0], p[1], p[2], p[3]);
        int ret = pd.exec();
        if (ret == QDialog::Accepted)
        {
            //elimina la cella dal vassoio
            this->tabVassoiRemoveItem(this->item);
            /* aggiunge estrae i dati dalla form */
            QString cont;
            QString vass;
            QString r;
            QString c;
            pd.getData(cont, vass, r, c);
            /* modifica/aggiunge il nodo al dom */
            this->item->setPosizione(cont.toInt(), vass.toInt(), r.toInt(), c.toInt());

            //scrive il nuovo valore sul bottone
            this->ui->posizione->setText(QString("%1-%2-%3-%4")
                                       .arg(item->getContenitore())
                                       .arg(item->getVassoio())
                                       .arg(item->getRiga())
                                       .arg(item->getColonna()));
            //aggiunge la cella nel vassoio
            this->setupTabVassoi(this->item);
        }

    }
}

void MonetaForm::on_descrizioneDritto_customContextMenuRequested(QPoint pos)
{
    // for most widgets
    QPoint globalPos = this->ui->descrizioneDritto->mapToGlobal(pos);
    // for QAbstractScrollArea and derived classes you would use:
    // QPoint globalPos = myWidget->viewport()->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenu.exec(globalPos);
    if (selectedItem)
    {
        if (selectedItem->text() == ACTION_EDIT)
        {
            /* attiva la vera gestione */
            DescrizioneDialog dialog(this);
            dialog.setData(this->ui->descrizioneDritto->toPlainText());
            //autoritaDialog.setData(a);
            int ret = dialog.exec();
            if (ret == QDialog::Accepted)
            {
                QString nuovaDescrizione;
                dialog.getData(&nuovaDescrizione);
                /* modifica/aggiunge il nodo al dom */
                this->item->setDescrizione(Moneta::DRITTO, nuovaDescrizione);
                this->loadData();
                //segnala la modifica
                emit this->changesOccurred();

            }
        } else if (selectedItem->text() == ACTION_COPY_ID) {
            this->gestClipboardCopyId(this->item->getId());
        } else if (selectedItem->text() == ACTION_COPY) {
            this->gestClipboardCopy(this->item->getId());
        }
    }
    else
    {
        // nothing was chosen
    }

}

void MonetaForm::on_descrizioneRovescio_customContextMenuRequested(QPoint pos)
{
    // for most widgets
    QPoint globalPos = this->ui->descrizioneRovescio->mapToGlobal(pos);
    // for QAbstractScrollArea and derived classes you would use:
    // QPoint globalPos = myWidget->viewport()->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenu.exec(globalPos);
    if (selectedItem)
    {
        if (selectedItem->text() == ACTION_EDIT)
        {
            /* attiva la vera gestione */
            DescrizioneDialog dialog(this);
            dialog.setData(this->ui->descrizioneRovescio->toPlainText());
            //autoritaDialog.setData(a);
            int ret = dialog.exec();
            if (ret == QDialog::Accepted)
            {
                QString nuovaDescrizione;
                dialog.getData(&nuovaDescrizione);
                /* modifica/aggiunge il nodo al dom */
                this->item->setDescrizione(Moneta::ROVESCIO, nuovaDescrizione);
                this->loadData();
                //segnala la modifica
                emit this->changesOccurred();

            }
        } else if (selectedItem->text() == ACTION_COPY_ID) {
            this->gestClipboardCopyId(this->item->getId());
        } else if (selectedItem->text() == ACTION_COPY) {
            this->gestClipboardCopy(this->item->getId());
        }
    }
    else
    {
        // nothing was chosen
    }

}

void MonetaForm::on_descrizioneTaglio_customContextMenuRequested(QPoint pos)
{
    // for most widgets
    QPoint globalPos = this->ui->descrizioneTaglio->mapToGlobal(pos);
    // for QAbstractScrollArea and derived classes you would use:
    // QPoint globalPos = myWidget->viewport()->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenu.exec(globalPos);
    if (selectedItem)
    {
        if (selectedItem->text() == ACTION_EDIT)
        {
            /* attiva la vera gestione */
            DescrizioneDialog dialog(this);
            dialog.setData(this->ui->descrizioneTaglio->toPlainText());
            //autoritaDialog.setData(a);
            int ret = dialog.exec();
            if (ret == QDialog::Accepted)
            {
                QString nuovaDescrizione;
                dialog.getData(&nuovaDescrizione);
                /* modifica/aggiunge il nodo al dom */
                this->item->setDescrizione(Moneta::TAGLIO, nuovaDescrizione);
                this->loadData();
                //segnala la modifica
                emit this->changesOccurred();

            }
        } else if (selectedItem->text() == ACTION_COPY_ID) {
            this->gestClipboardCopyId(this->item->getId());
        } else if (selectedItem->text() == ACTION_COPY) {
            this->gestClipboardCopy(this->item->getId());
        }
    }
    else
    {
        // nothing was chosen
    }

}

void MonetaForm::on_id_customContextMenuRequested(const QPoint &pos)
{
    // for most widgets
    QPoint globalPos = this->ui->id->mapToGlobal(pos);
    // for QAbstractScrollArea and derived classes you would use:
    // QPoint globalPos = myWidget->viewport()->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenu.exec(globalPos);
    if (selectedItem)
    {
        if (selectedItem->text() == ACTION_COPY_ID) {
            this->gestClipboardCopyId(this->item->getId());
        } else if (selectedItem->text() == ACTION_COPY) {
            this->gestClipboardCopy(this->item->getId());
        }
    }
    else
    {
        // nothing was chosen
    }
}

/**
  abilita/disabilita un'azione del menu contestuale
  */
void MonetaForm::contextMenuEnableAction(QString actionText, bool enable) {
    //sistema il menu contestuale
    foreach (QAction* act, this->contextMenu.actions()) {
        if (act->text() == actionText) {
            act->setEnabled(enable);
            break;
        }
    }
}

/**
  Gestisce la copia negli appunti dell'id moneta
  */
void MonetaForm::gestClipboardCopyId(const QString& id) {
    /* copia l'id negli appunti */
    QClipboard *clipboard = QApplication::clipboard();
    clipboard->setText(id);
}

/**
  Gestisce la copia negli appunti della descrizione della moneta
  facendo riferimento all'xsl del qr
  */
void MonetaForm::gestClipboardCopy(const QString& id) {
    XsltConverter converter;
    QString text = converter.convertToText(
                id,
                CommonData::getInstance()->getCollezione(),
                CommonData::getInstance()->getXml2QrXslt());

    /* copia l'id negli appunti */
    QClipboard *clipboard = QApplication::clipboard();
    clipboard->setText(text);
}


void MonetaForm::on_led_clicked()
{

    bool motivoOk;
    xml::Stato vecchioStato = this->item->getStato();
    QString vecchioMotivo = vecchioStato.motivo;
    QColor vecchioColore = QColor(vecchioStato.colore);

    QColorDialog colorDial(this);
    QColor color = colorDial.getColor(vecchioColore);
    if (!color.isValid()) {
        color = vecchioColore;
    }

    QString motivo = QInputDialog::getText(this, "Scrivi un motivo",
                                           "Motivo", QLineEdit::Normal,
                                           vecchioMotivo, &motivoOk);
    if (!motivoOk) {
        motivo = vecchioMotivo;
    }
    xml::Stato s(color.name(), motivo);
    this->item->setStato(s);
    this->loadData();
    if ((vecchioMotivo != motivo) || (vecchioColore != color))
        emit this->changesOccurred();
}

void MonetaForm::on_documenti_doubleClicked(const QModelIndex &index)
{
    GenericModel* model = (GenericModel*)index.model();
    xml::Documento* vecchio = (xml::Documento*) model->getItem(index);
    if (this->editingEnabled)
    {
        AddDocumentDialog dialog(this);
        dialog.setData(vecchio);
        int ret = dialog.exec();
        if (ret == QDialog::Accepted)
        {
            QString nuovoFilename = dialog.getFilenames().at(0);
            QString nuovaDescrizione = dialog.getDescrizione();
            xml::Documento nuovo(nuovoFilename, nuovaDescrizione);
            /* modifica/aggiunge il nodo al dom */
            this->item->setDocumento(*vecchio, nuovo);
            this->loadData();
            //segnala la modifica
            emit this->changesOccurred();

        }
    } else {
        QString file = CommonData::getInstance()->getDocDir()+"/"+vecchio->filename;
        QUrl url = QUrl::fromLocalFile(file);
        if (url.isValid() && QFile(file).exists()) {
            QDesktopServices::openUrl(url);
        } else {
            QMessageBox::warning(this, CommonData::getInstance()->getAppName(), QString("Impossible aprire %1")
                                 .arg(file));
        }
    }
}

void MonetaForm::on_ambiti_doubleClicked(const QModelIndex &index)
{
    Q_UNUSED(index);
    this->setupAmbiti();
}


void MonetaForm::on_ambiti_customContextMenuRequested(const QPoint &pos)
{
    // for most widgets
    QPoint globalPos = this->ui->ambiti->mapToGlobal(pos);
    // for QAbstractScrollArea and derived classes you would use:
    // QPoint globalPos = myWidget->viewport()->mapToGlobal(pos);
    //QAction* selectedItem = this->contextMenuForAmbiti.exec(globalPos);
    QAction* selectedItem = this->contextMenuForAmbiti.exec(globalPos);
    if (selectedItem)    {
        if (selectedItem->text() == ACTION_EDIT) {
            this->setupAmbiti();
        }
    }
}


void MonetaForm::on_imgDritto_customContextMenuRequested(const QPoint &pos) {
    // for most widgets
    QPoint globalPos = this->ui->imgDritto->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenuForImg.exec(globalPos);
    if (selectedItem)    {
        if (selectedItem->text() == ACTION_EDIT) {
            SetImmagineMonetaDialog si(this->item, Moneta::DRITTO, this);
            int ret = si.exec();
            if (ret == QDialog::Accepted) {
                this->loadData();
                //segnala la modifica
                emit this->changesOccurred();

            }
        }
    }

}

void MonetaForm::on_imgRovescio_customContextMenuRequested(const QPoint &pos) {
    // for most widgets
    QPoint globalPos = this->ui->imgRovescio->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenuForImg.exec(globalPos);
    if (selectedItem)    {
        if (selectedItem->text() == ACTION_EDIT) {
            SetImmagineMonetaDialog si(this->item, Moneta::ROVESCIO, this);
            int ret = si.exec();
            if (ret == QDialog::Accepted) {
                this->loadData();
                //segnala la modifica
                emit this->changesOccurred();

            }

        }
    }

}

void MonetaForm::on_imgTaglio_customContextMenuRequested(const QPoint &pos) {
    // for most widgets
    QPoint globalPos = this->ui->imgTaglio->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenuForImg.exec(globalPos);
    if (selectedItem)    {
        if (selectedItem->text() == ACTION_EDIT) {
            SetImmagineMonetaDialog si(this->item, Moneta::TAGLIO, this);
            int ret = si.exec();
            if (ret == QDialog::Accepted) {
                this->loadData();
                //segnala la modifica
                emit this->changesOccurred();

            }

        }
    }

}


void MonetaForm::setupAmbiti() {
    SetAmbitoDialog dialog(CollezioneXml::getInstance()->getInfo()->ambiti,
                           this->item->getAmbiti(), this);
    int ret = dialog.exec();
    if (ret == QDialog::Accepted)
    {
        QList<xml::Ambito*> selezionati = dialog.getAmbitiSelezionati();
        this->item->setAmbiti(selezionati);
        this->loadData();
        //segnala la modifica
        emit this->changesOccurred();

    }

}



void MonetaForm::on_setupCollezione_clicked()
{
    SetCollezioneInfoDialog infoDialog(this);
    int ret = infoDialog.exec();
    if (ret == QDialog::Accepted) {
        this->salva();
    }
}
