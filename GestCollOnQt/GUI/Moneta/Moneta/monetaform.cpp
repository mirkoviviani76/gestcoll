#include "monetaform.h"
#include "ui_monetaform.h"
#include <commondata.h>
#include <QMessageBox>
#include <notadialog.h>
#include <legendadialog.h>
#include <utils.h>
#include <QFileInfo>
#include <vassoioform.h>
#include <QDateTime>
#include <gestlog.h>
#include <posizionedialog.h>
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
#include "visualizzaimmagine.h"
#include "commondefs.h"
#include "collezionemodel.h"

#define TAB_MONETA  (0) ///< id tab moneta
#define TAB_VASSOI  (1) ///< id tab vassoi


bool signalsAreBlocked = false;

namespace {
/**
  * @brief compareIdVassoio funzione di ordinamento per gli id dei vassoi.
  * Ordinamento crescente in base 1. all'armadio e 2. in base al numero di vassoio
  * @param a primo id di vassoio
  * @param b secondo id di vassoio
  * @return 0 o 1
  */
 int compareIdVassoio(const QString& a, const QString& b) {
     QStringList datiA = a.split('-');
     QStringList datiB = b.split('-');
     int armadioA = datiA.at(0).toInt();
     int armadioB = datiB.at(0).toInt();
     int vassoioA = datiA.at(1).toInt();
     int vassoioB = datiB.at(1).toInt();

     if (armadioA == armadioB) {
         return vassoioA < vassoioB;
     } else {
         return armadioA < armadioB;
     }
     return 0;

 }
}

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

    this->ui->tabMoneta->setVisible(false);

    connect(this->ui->ambiti, SIGNAL(doubleClicked(QModelIndex)), this, SLOT(on_ambiti_doubleClicked(QModelIndex)));
    connect(this->ui->dritto, SIGNAL(changesOccurred()), this, SIGNAL(changesOccurred()));
    connect(this->ui->rovescio, SIGNAL(changesOccurred()), this, SIGNAL(changesOccurred()));
    connect(this->ui->taglio, SIGNAL(changesOccurred()), this, SIGNAL(changesOccurred()));
    connect(this->ui->emissione, SIGNAL(changesOccurred()), this, SIGNAL(changesOccurred()));
    connect(this->ui->datiAcquisto, SIGNAL(changesOccurred()), this, SIGNAL(changesOccurred()));
    connect(this->ui->datiFisici, SIGNAL(changesOccurred()), this, SIGNAL(changesOccurred()));
    connect(this->ui->zeccaEzecchieri, SIGNAL(changesOccurred()), this, SIGNAL(changesOccurred()));
    connect(this->ui->note, SIGNAL(changesOccurred()), this, SIGNAL(changesOccurred()));
    connect(this->ui->letteratura, SIGNAL(changesOccurred()), this, SIGNAL(changesOccurred()));
    connect(this->ui->documenti, SIGNAL(changesOccurred()), this, SIGNAL(changesOccurred()));
}

/**
 * @brief Distruttore
 *
*/
MonetaForm::~MonetaForm()
{
    delete ui;
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
    QString idTab = moneta->getPosizione().getIdVassoio();
    /* rimuove la cella  */
    VassoioForm* vf = this->tabVassoi[idTab];
    vf->setData(moneta->getPosizione().getCasella(), NULL);
}

VassoioForm* MonetaForm::createVassoioForm(const QString& idTab, const QString& monetaId) {
    //ottiene il numero di righe e di colonne per il vassoio corrente
    VassoioForm* currVassoio = new VassoioForm(this->ui->tabsVassoi);
    qApp->processEvents();
    connect(currVassoio, SIGNAL(idChangeRequest(QString)), this, SLOT(idChanged(QString)));
    connect(currVassoio, SIGNAL(newIdAdded(MonetaXml*)), this, SLOT(addItem(MonetaXml*)));

    VassoioXml* v = this->vassoi->getVassoio(idTab);
    if (v == NULL) {
        QString messg = QString("ERRORE: vassoio e/o contenitore errati in %1").arg(monetaId);
        Log::Logger::getInstance()->log(messg, Log::ERR);
    } else {
        currVassoio->setSize(v->getIdContenitore(),
                             v->getIdVassoio(),
                             v->getRighe(),
                             v->getColonne(),
                             v->getDimensione());

    }
    return currVassoio;
}

void MonetaForm::addTabVassoio(const QString& idTab, VassoioForm* vf) {
    assert(vf != NULL);

    //salva l'id della tab
    this->tabVassoi.insert(idTab, vf);

    /* ordina gli id dei tab */
    QList<QString> tabs = this->tabVassoi.keys();
    qSort(tabs.begin(), tabs.end(), compareIdVassoio);
    /* trova dove deve andare la tab corrente */
    int tabIndex = tabs.indexOf(idTab);

    //aggiunge una tab per il nuovo vassoio
    this->ui->tabsVassoi->insertTab(tabIndex, vf, idTab);
}

void MonetaForm::setupTabVassoi(MonetaXml* moneta)
{
    QString idTab = moneta->getPosizione().getIdVassoio();
    VassoioForm* vf = NULL;
    if (!tabVassoi.contains(idTab))
    {
        /* non e' stata ancora creata una tab per questo vassoio. Lo fa adesso. */
        vf = createVassoioForm(idTab, moneta->getId());
        addTabVassoio(idTab, vf);
    } else {
        /* la tab era gia' presente. La recupera. */
        vf = this->tabVassoi[idTab];
    }

    /* aggiunge la moneta al vassoio */
    if (vf != NULL) {
        vf->setModel();
        vf->setData(moneta->getPosizione().getCasella(), moneta);
        vf->resizeRows();
    }
}

void MonetaForm::setupModelMonete()
{
    if (collezioneModel != NULL)
        delete collezioneModel;

    collezioneModel = new CollezioneSortFilterProxyModel(this);
    this->ui->tabsVassoi->removeTab(1);
    this->ui->tabsVassoi->removeTab(0);


    QStringList idVassoi;
    foreach (QString id, CollezioneXml::getInstance()->getAllId()) {
        MonetaXml* moneta = CollezioneXml::getInstance()->getMoneta(id);
        QString idVassoio = moneta->getPosizione().getIdVassoio();
        if (!(idVassoi.contains(idVassoio))) {
            idVassoi << idVassoio;
        }
    }
    qSort(idVassoi);



    foreach (QString id, CollezioneXml::getInstance()->getAllId())
    {
        MonetaXml* moneta = CollezioneXml::getInstance()->getMoneta(id);
        this->collezioneModel->appendRow(moneta);
        this->setupTabVassoi(moneta);
    }

    //ordina per id
    this->collezioneModel->sort(0, Qt::AscendingOrder);
    //setta il modello
    this->ui->itemList->setModel(this->collezioneModel);

}

void MonetaForm::setupModels()
{
    modelloAmbiti = new GenericModel(2);
    this->setupModelMonete();
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



void MonetaForm::loadData()
{
    this->blockSignals(true);
    signalsAreBlocked = true;
    //inibisce la segnalazione delle modifiche
    this->ui->id->setText(item->getId());

    this->ui->posizione->setText(item->getPosizione().toString("-"));

    this->modelloAmbiti->clear();

    /* aggiunge i dati di emissione */
    xml::Emissione emissioneData;
    emissioneData.anno = &(item->getDom()->anno());
    emissioneData.nominale = &(item->getDom()->nominale());
    emissioneData.paese = &(item->getDom()->paese());
    emissioneData.autorita = &(this->item->getDom()->autorita());
    this->ui->emissione->clear();
    this->ui->emissione->setData(emissioneData);

    /* aggiunge i dati fisici */
    this->ui->datiFisici->setData(&(this->item->getDom()->datiFisici()));

    /* aggiunge i dati di acquisto */
    this->ui->datiAcquisto->setData(&(this->item->getDom()->datiAcquisto()));

    /* aggiunge zecca e zecchieri */
    this->ui->zeccaEzecchieri->setData(&(item->getDom()->zecca()), &(item->getDom()->zecchieri()));

    /* aggiunge le note */
    this->ui->note->setData(&(item->getDom()->note()));

    /* aggiunge la letteratura */
    //TODO ASTE?
    this->ui->letteratura->setData(&(item->getDom()->letteratura()));

    /* aggiunge i documenti */
    this->ui->documenti->setData(&(item->getDom()->itemAddizionali()));

    /* aggiunge gli ambiti */
    foreach (xml::Ambito* a, item->getAmbiti())
    {
        this->modelloAmbiti->appendRow(a);
    }
    this->ui->ambiti->setModel(this->modelloAmbiti);


    this->ui->dritto->setDati(item->getId(), "D", &(item->getDom()->datiArtistici().dritto()));
    this->ui->rovescio->setDati(item->getId(), "R", &(item->getDom()->datiArtistici().rovescio()));
    this->ui->taglio->setDati(item->getId(), "T", &(item->getDom()->datiArtistici().taglio()));

    /* prepara il led di stato */
    xml::Stato stato = this->item->getStato();
    this->ui->led->setOffColor1(QColor(stato.colore));
    this->ui->led->setOffColor2(QColor(stato.colore));
    this->ui->led->setOnColor1(QColor(stato.colore));
    this->ui->led->setOnColor2(QColor(stato.colore));
    this->ui->led->setToolTip(stato.motivo);
    this->ui->led->setChecked(true);

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
    QString idTab = this->item->getPosizione().getIdVassoio();
    if (this->tabVassoi.contains(idTab))
    {
        /* setta il vassoio giusto */
        VassoioForm* vf = this->tabVassoi[idTab];
        this->ui->tabsVassoi->setCurrentWidget(vf);
        /* seleziona l'indice giusto nella tabella */
        vf->setCurrentIndex(this->item->getPosizione().getCasella());
    }


}

void MonetaForm::enableEdit(bool editable)
{
    this->editingEnabled = editable;

    /* abilita-disabilita il frame */
    this->ui->posizione->setFlat(!editable);

    /* abilita-disabilita gli item */
    this->ui->led->setEnabled(editable);
    this->ui->dritto->setReadOnly(!editable);
    this->ui->rovescio->setReadOnly(!editable);
    this->ui->taglio->setReadOnly(!editable);

    this->ui->emissione->setEditable(editable);

    this->ui->datiAcquisto->setEditable(editable);
    this->ui->datiFisici->setEditable(editable);
    this->ui->zeccaEzecchieri->setEditable(editable);
    this->ui->note->setEditable(editable);
    this->ui->documenti->setEditable(editable);

    this->ui->letteratura->setEditable(editable);

}



/************************************************************************
  GESTIONE EDITING
*************************************************************************/


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
        this->item->updateRevision();
        //salvataggio
        ret = CollezioneXml::getInstance()->save();
    }
    if (ret)
    {
        QString msg = QString("Salvato");
        Log::Logger::getInstance()->log(msg, Log::INFO);
    }
    else
        QMessageBox::warning(NULL, "GestColl", "Salvataggio fallito.");

}


void MonetaForm::showQr() {
    //ottiene il nome della immagine png
    QString qrImg = CommonData::getInstance()->getQrDir()+"/"+this->item->getId()+".png";
    QFileInfo fi(qrImg);
    if (fi.exists()) {
        //visualizza l'immagine con il qr code
        VisualizzaImmagine v(qrImg, this);
        v.exec();
    } else {
        Log::Logger::getInstance()->log(QString("L'immagine %1 non esiste").arg(qrImg), Log::ERR);
    }
}






void MonetaForm::idChanged(QString id)
{
    /* cerca l'id fra gli items presenti */
    for (int i = 0 ; i < this->collezioneModel->rowCount(); i++)
    {
        // ottiene l'elemento corrente
        CollezioneModel* model = (CollezioneModel*)this->collezioneModel->sourceModel();
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
    CollezioneModel* model = (CollezioneModel*)this->collezioneModel->sourceModel();
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
            this->ui->posizione->setText(item->getPosizione().toString("-"));
            //aggiunge la cella nel vassoio
            this->setupTabVassoi(this->item);
        }

    }
}


#if 0
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
#endif

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


void MonetaForm::on_ambiti_doubleClicked(const QModelIndex &index)
{
    Q_UNUSED(index);
    this->setupAmbiti();
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

