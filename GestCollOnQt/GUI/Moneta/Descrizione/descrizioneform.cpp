#include "descrizioneform.h"
#include "ui_descrizioneform.h"
#include "setimmaginemonetadialog.h"
#include "visualizzaimmagine.h"
#include "descrizionedialog.h"
#include "legendadialog.h"
#include "commondata.h"

bool operator==(const ::gestColl::coins::legenda &a, const ::gestColl::coins::legenda &b)
{
    if (QString::fromStdWString(a.testo()) != QString::fromStdWString(b.testo())) {
        return false;
    }
    if ((a.scioglimento().present()) && (!b.scioglimento().present())) {
        return false;
    }
    if ((!a.scioglimento().present()) && (b.scioglimento().present())) {
        return false;
    }
    if ((QString::fromStdWString(a.testo()) == QString::fromStdWString(b.testo())) &&
        (QString::fromStdWString(a.scioglimento().get()) == QString::fromStdWString(b.scioglimento().get()))) {
        return true;
    }
    return false;
}




DescrizioneForm::DescrizioneForm(QWidget *parent) :
    QGroupBox(parent), editingEnabled(false),
    ui(new Ui::DescrizioneForm)
{
    ui->setupUi(this);
    modelloLegende = new ModelloLegenda(this);

    this->ui->specialEdit->setVisible(false);
    this->ui->addLegenda->setVisible(false);
    this->ui->deleteLegenda->setVisible(false);

    connect(this->ui->img, SIGNAL(doubleClicked()), this, SLOT(imageDoubleClicked()));

}

DescrizioneForm::~DescrizioneForm()
{
    this->modelloLegende->clear();
    delete modelloLegende;
    this->modelloLegende = NULL;

    delete ui;
}

void DescrizioneForm::setDati(const QString &monetaId,  const QString &latoId, ::gestColl::coins::descrizioni* _xmlDom)
{
    this->xmlDom = _xmlDom;
    this->monetaId = monetaId;
    this->latoId = latoId;
    QString imgFilename = "";
    if (this->xmlDom->fileImmagine().present()) {
        imgFilename = QString("%1/%2")
                .arg(CommonData::getInstance()->getImgDir())
                .arg(QString::fromStdWString(this->xmlDom->fileImmagine().get()));
    }
    this->setImage(imgFilename);
    this->setLegende();
    this->setDescrizione(QString::fromStdWString(this->xmlDom->descrizione()));
}

void DescrizioneForm::setDescrizione(const QString &text)
{
  this->ui->descrizione->setText(text);
}

QString DescrizioneForm::getDescrizione()
{
    return this->ui->descrizione->toPlainText();
}

void DescrizioneForm::setImage(const QString &filename)
{
    this->ui->img->setupImg(filename);
}

void DescrizioneForm::setLegende()
{
    QList< ::gestColl::coins::legenda > data = fillLegende();
    this->modelloLegende->clear();
    foreach ( ::gestColl::coins::legenda a, data) {
        this->modelloLegende->appendRow(a);
    }
    this->ui->legende->setModel(this->modelloLegende);
}

void DescrizioneForm::setTitolo(const QString &titolo)
{
    this->setTitle(titolo);
}

void DescrizioneForm::setReadOnly(bool readonly)
{
    this->ui->descrizione->setReadOnly(readonly);
    if (readonly) {
        this->ui->legende->setEditTriggers(QAbstractItemView::NoEditTriggers);
    } else {
        this->ui->legende->setEditTriggers(QAbstractItemView::DoubleClicked);
    }
    this->editingEnabled = !readonly;

    this->ui->specialEdit->setVisible(this->editingEnabled);
    this->ui->addLegenda->setVisible(this->editingEnabled);
    this->ui->deleteLegenda->setVisible(this->editingEnabled);

}

void DescrizioneForm::imageDoubleClicked()
{
    if (this->editingEnabled == false)
    {
        /* mostra la finestra grande dell'immagine */
        VisualizzaImmagine bigImg(this->ui->img->getFilename(), this);
        bigImg.setModal(true);
        bigImg.showMaximized();
        bigImg.exec();
    } else {
        /* scegli immagine */
        QString filename = this->ui->img->getFilename();

        SetImmagineMonetaDialog* si = new SetImmagineMonetaDialog(filename, monetaId, latoId, this);
        int ret = si->exec();
        if (ret == QDialog::Accepted) {
            /* update dom */
            QString targetFilename = si->getNewFilename();
            this->xmlDom->fileImmagine(targetFilename.toStdWString());
            //segnala la modifica
            emit this->changesOccurred();
        }
    }
}

void DescrizioneForm::on_legende_doubleClicked(const QModelIndex &index)
{
    if (!index.isValid())
        return;

    ::gestColl::coins::legenda vecchio = this->modelloLegende->getItem(index);
    LegendaDialog legendaDialog(this);
    legendaDialog.setData(vecchio);
    int ret = legendaDialog.exec();
    if (ret == QDialog::Accepted && this->editingEnabled)
    {
        QString nuovoTesto;
        QString nuovoScioglimento;
        legendaDialog.getData(&nuovoTesto, &nuovoScioglimento);
        /* modifica/aggiunge il nodo al dom */
        this->updateLegenda(vecchio, nuovoTesto, nuovoScioglimento);
        emit this->changesOccurred();
    }

}


void DescrizioneForm::on_descrizione_textChanged()
{
    if (this->editingEnabled == true) {
        /* modifica/aggiunge il nodo al dom */
        QString nuovaDescrizione = this->ui->descrizione->toPlainText();
        this->xmlDom->descrizione(nuovaDescrizione.toStdWString());
        //segnala la modifica
        emit this->changesOccurred();
    }
}

void DescrizioneForm::on_specialEdit_clicked()
{
    if (this->editingEnabled == true) {
        /* attiva la vera gestione */
        DescrizioneDialog dialog(this);
        dialog.setData(this->ui->descrizione->toPlainText());
        int ret = dialog.exec();
        if (ret == QDialog::Accepted)
        {
            QString nuovaDescrizione;
            dialog.getData(&nuovaDescrizione);
            this->ui->descrizione->setText(nuovaDescrizione);
        }
    }
}

void DescrizioneForm::gestLegendaModifica(QModelIndex index)
{
    ::gestColl::coins::legenda vecchio = this->modelloLegende->getItem(index);
    LegendaDialog legendaDialog(this);
    legendaDialog.setData(vecchio);
    int ret = legendaDialog.exec();
    if (ret == QDialog::Accepted && this->editingEnabled)
    {
        QString testo;
        QString scioglimento;
        legendaDialog.getData(&testo, &scioglimento);
        /* modifica/aggiunge il nodo al dom */
        this->updateLegenda(vecchio, testo, scioglimento);
        //segnala la modifica
        emit this->changesOccurred();
    }
}

void DescrizioneForm::updateLegenda(const ::gestColl::coins::legenda& vecchio, const QString& nuovoTesto, const QString& nuovoScioglimento)
{
    for ( ::gestColl::coins::descrizioni::legenda_iterator it = this->xmlDom->legenda().begin();
          it != this->xmlDom->legenda().end(); ++it) {

        if (*it == vecchio) {
            /* trovato: effettua le modifiche */
            it->testo(nuovoTesto.toStdWString());
            it->scioglimento(nuovoScioglimento.toStdWString());
            break;
        }
    }

    this->setLegende();

}


QList<gestColl::coins::legenda> DescrizioneForm::fillLegende()
{
    QList< ::gestColl::coins::legenda > target;
    ::gestColl::coins::descrizioni::legenda_sequence seq = this->xmlDom->legenda();

    for (::gestColl::coins::descrizioni::legenda_iterator it(seq.begin()); it != seq.end(); ++it)
    {
        target.append(*it);
    }
    return target;

}

void DescrizioneForm::on_addLegenda_clicked()
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
        /* modifica/aggiunge il nodo al dom */
        ::gestColl::coins::legenda leg(testo.toStdWString());
        leg.scioglimento(scioglimento.toStdWString());
        this->xmlDom->legenda().push_back(leg);

        //aggiorna il modello
        this->setLegende();
        //segnala la modifica
        emit this->changesOccurred();
    }

}


void DescrizioneForm::on_deleteLegenda_clicked()
{
    foreach (QModelIndex index, this->ui->legende->selectionModel()->selectedIndexes()) {
        ::gestColl::coins::legenda selectedLeg = this->modelloLegende->getItem(index);

        gestColl::coins::descrizioni::legenda_sequence leg(this->xmlDom->legenda());
        for (unsigned int i = 0; i < leg.size(); i++) {
            ::gestColl::coins::legenda curLeg = leg.at(i);

            if (selectedLeg == curLeg) {
                leg.erase(leg.begin()+i);
                break;
            }
        }
        this->xmlDom->legenda(leg);
    }

    //aggiorna il modello
    this->setLegende();
    //segnala la modifica
    emit this->changesOccurred();
}
