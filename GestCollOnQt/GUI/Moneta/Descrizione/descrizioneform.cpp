#include "descrizioneform.h"
#include "ui_descrizioneform.h"
#include "setimmaginemonetadialog.h"
#include "visualizzaimmagine.h"
#include "descrizionedialog.h"
#include "legendadialog.h"

DescrizioneForm::DescrizioneForm(QWidget *parent) :
    QGroupBox(parent), editingEnabled(false),
    ui(new Ui::DescrizioneForm)
{
    ui->setupUi(this);
    modelloLegende = new GenericModel(1, this);

    this->ui->legende->setContextMenuPolicy(Qt::CustomContextMenu);
    this->ui->descrizione->setContextMenuPolicy(Qt::CustomContextMenu);
    this->ui->img->setContextMenuPolicy(Qt::CustomContextMenu);

    this->ui->specialEdit->setVisible(false);

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
    QList<xml::Legenda *> data = fillLegende();
    this->modelloLegende->clear();
    foreach (xml::Legenda* a, data) {
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
        this->updateLegenda(*vecchio, nuovo);
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
    xml::Legenda* vecchio = (xml::Legenda*) this->modelloLegende->getItem(index);
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
        this->updateLegenda(*vecchio, nuovo);
        //segnala la modifica
        emit this->changesOccurred();
    }
}

void DescrizioneForm::updateLegenda(const xml::Legenda& vecchio, const xml::Legenda& nuovo)
{
    for (::descrizioni::legenda_iterator it = this->xmlDom->legenda().begin(); it != this->xmlDom->legenda().end(); ++it) {
        if (updateLegenda(it, vecchio, nuovo) == true) {
            break;
        }
    }

    this->setLegende();

}


bool DescrizioneForm::updateLegenda(::descrizioni::legenda_iterator it, const xml::Legenda& vecchio, const xml::Legenda& nuovo)
{
    bool done = false;
    //cerca l'item "giusto"
    QString testo = QString::fromStdWString(it->testo());
    QString sciog = "";
    if (it->scioglimento().present())
    {
        sciog = QString::fromStdWString(it->scioglimento().get());
    }
    if ((testo == vecchio.testo) && (sciog == vecchio.scioglimento))
    {
        /* trovato: effettua le modifiche */
        it->testo(nuovo.testo.toStdWString());
        it->scioglimento(nuovo.scioglimento.toStdWString());
        done = true;
    }
    return done;
}



void DescrizioneForm::addLegenda(const xml::Legenda& l)
{
    ::legenda leg(L"");
    leg.testo(l.testo.toStdWString());
    leg.scioglimento(l.scioglimento.toStdWString());
    this->xmlDom->legenda().push_back(leg);
}


QList<xml::Legenda*> DescrizioneForm::fillLegende()
{
    QList<xml::Legenda*> target;
    descrizioni::legenda_sequence seq = this->xmlDom->legenda();

    for (descrizioni::legenda_iterator it(seq.begin()); it != seq.end(); ++it)
    {
        descrizioni::legenda_type curLeg = (*it);
        QString myT = QString::fromStdWString(curLeg.testo());
        descrizioni::legenda_type::scioglimento_optional sop = curLeg.scioglimento();
        QString myS = "";
        if (sop.present())
        {
            descrizioni::legenda_type::scioglimento_type st = sop.get();
            myS = QString::fromStdWString(st);
        }

        xml::Legenda* leg = new xml::Legenda(myT, myS);
        target.append(leg);
    }
    return target;

}
