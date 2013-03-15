#include "nuovamonetadialog.h"
#include "ui_nuovamonetadialog.h"
#include <utils.h>
#include <commondata.h>
#include <QFileInfoList>
#include <QFileInfo>
#include <QRegExp>
#include <QMessageBox>
#include <QDir>
#include "collezionexml.h"

NuovaMonetaDialog::NuovaMonetaDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::NuovaMonetaDialog)
{
    ui->setupUi(this);
    this->dim = "A";
    this->anno = "0000";
    this->added = NULL;

    foreach (QString id, CollezioneXml::getInstance()->getAllId())
    {
        QString anno = id.split("-").at(0);
        this->countByYear[anno]++;
    }

}

void NuovaMonetaDialog::setParameters(QString cont, QString vass, int r, int c, QString size)
{
    this->pos = QString("%1-%2-%3-%4")
                .arg(cont)
                .arg(vass)
                .arg(r)
                .arg(c);
    this->dim = size;

    this->ui->contenitore->setText(cont);
    this->ui->vassoio->setText(vass);
    this->ui->riga->setText(QString("%1").arg(r));
    this->ui->colonna->setText(QString("%1").arg(c));

    int currIndex = 0;
    if (this->dim == "A")
        currIndex = 0;
    else if (this->dim == "B")
        currIndex = 1;
    else if (this->dim == "C")
        currIndex = 2;
    else if (this->dim == "D")
        currIndex = 3;
    this->ui->comboBox->setCurrentIndex(currIndex);
}

NuovaMonetaDialog::~NuovaMonetaDialog()
{
    delete ui;
}


void NuovaMonetaDialog::changeEvent(QEvent *e)
{
    QDialog::changeEvent(e);
    switch (e->type()) {
    case QEvent::LanguageChange:
        ui->retranslateUi(this);
        break;
    default:
        break;
    }
}

void NuovaMonetaDialog::on_comboBox_currentIndexChanged(QString newDim)
{
    this->dim = newDim;
    this->updateId();
}

void NuovaMonetaDialog::on_buttonBox_accepted()
{
    MonetaXml* ret = NULL;
    QString id = this->ui->idSuggerito->text();
    QRegExp r("....-[ABCD]-[0-9]{4}");
    if (r.exactMatch(id))
    {
        QString contenitore = this->ui->contenitore->text();
        QString vassoio = this->ui->vassoio->text();
        QString riga = this->ui->riga->text();
        QString colonna = this->ui->colonna->text();

        QString anno = this->ui->anno->text();
        ret = CollezioneXml::getInstance()->addMoneta(id, anno, contenitore.toInt(), vassoio.toInt(), riga.toInt(), colonna.toInt());
        this->added = ret;

    }
    if (ret != NULL) {
        QString msg = QString("Moneta %1 creata con successo").arg(id);
        QMessageBox::information(this, "GestColl", msg);
    } else {
        QString msg = QString("Moneta %1 creata con successo").arg(id);
        QMessageBox::warning(this, "GestColl", msg);
    }

}

/**
  Ottiene la nuova moneta aggiunta o null in caso di errore
  */
MonetaXml* NuovaMonetaDialog::getNuovaMoneta() {
    return this->added;
}

void NuovaMonetaDialog::on_anno_textChanged(QString newanno)
{
    this->anno = newanno;
    this->updateId();
}

void NuovaMonetaDialog::updateId()
{
    //calcolare nuovo progressivo
    int progressivo = this->countByYear[this->anno]+1;
    QString prog = QString("%1").arg(progressivo).rightJustified(4, '0');
    QString newId = QString("%1-%2-%23")
                    .arg(this->anno)
                    .arg(this->dim)
                    .arg(prog);
    this->ui->idSuggerito->setText(newId);
    this->id = newId;
}
