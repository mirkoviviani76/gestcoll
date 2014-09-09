#include "setimmaginemonetadialog.h"
#include "ui_setimmaginemonetadialog.h"

#include <QFile>
#include <QImage>
#include <QFileDialog>
#include <QDebug>
#include <QMessageBox>

SetImmagineMonetaDialog::SetImmagineMonetaDialog(MonetaXml* _moneta, Moneta::Lato _lato, QWidget *parent) :
    QDialog(parent),
    ui(new Ui::SetImmagineMonetaDialog)
{
    this->moneta = _moneta;
    this->lato = _lato;
    this->oldFilename = this->moneta->getImg(lato);

    ui->setupUi(this);

    this->ui->title->setText(this->moneta->getId());



    QString latoAsString = "";
    switch (this->lato) {
    case Moneta::DRITTO:
        latoAsString = "D";
        break;
    case Moneta::ROVESCIO:
        latoAsString = "R";
        break;
    case Moneta::TAGLIO:
        latoAsString = "T";
        break;

    }

    this->suggestedFilename = QString("%1-%2.jpg")
            .arg(this->moneta->getId())
            .arg(latoAsString);

    QString textRinomina = QString("Rinomina in %1")
            .arg(this->suggestedFilename);
    this->ui->checkBox_rinomina->setText(textRinomina);


    QFile file(this->oldFilename);
    if (file.exists()) {
        this->ui->img->setupImg(this->oldFilename);
        this->ui->filename->setText(this->oldFilename);
    }

}

SetImmagineMonetaDialog::~SetImmagineMonetaDialog()
{
    delete ui;
}

void SetImmagineMonetaDialog::on_checkBox_rinomina_stateChanged(int arg1)
{
    switch (arg1) {
    case Qt::Unchecked : {

    }
    break;
    case Qt::Checked :
    case Qt::PartiallyChecked :  {

    }
    break;

    }
}

void SetImmagineMonetaDialog::on_checkBox_noImg_stateChanged(int arg1)
{
    switch (arg1) {
    case Qt::Unchecked : {
        this->ui->filename->setEnabled(true);
        this->ui->setFile->setEnabled(true);
        this->ui->checkBox_rinomina->setEnabled(true);
    }
    break;
    case Qt::Checked :
    case Qt::PartiallyChecked :  {
        this->ui->filename->setEnabled(false);
        this->ui->setFile->setEnabled(false);
        this->ui->checkBox_rinomina->setEnabled(false);
    }
    break;

    }

}

void SetImmagineMonetaDialog::on_buttonBox_accepted()
{
    QString filename = this->ui->filename->text();

    if (this->ui->checkBox_noImg->isChecked()) {
        this->moneta->setImmagine(this->lato, "");
        return;
    }

    QFileInfo info(filename);
    if (info.exists()) {
        QString targetfilename = "";
        if (this->ui->checkBox_rinomina->isChecked()) {
            targetfilename = this->suggestedFilename;
        } else {
            targetfilename = info.fileName();
        }

        //copia nella dir immagini
        QString target = QString("%1/%2")
                .arg(CommonData::getInstance()->getImgDir())
                .arg(targetfilename);
        QFileInfo infoTarget(target);
        if (infoTarget.exists() || filename == target) {
            QMessageBox::critical(this,
                                  CommonData::getInstance()->getAppName(),
                                  "File già presente");
        } else {
            QFile source(filename);
            bool ris = source.copy(target);
            if (!ris) {
                QMessageBox::critical(this,
                                      CommonData::getInstance()->getAppName(),
                                      "Copia non riuscita");
            } else {
                this->moneta->setImmagine(this->lato, targetfilename);
            }
        }
    }
}

void SetImmagineMonetaDialog::on_setFile_clicked()
{
    QFileDialog fd(this, "Scegli immagine");
    fd.setNameFilter(tr("Immagini JPG (*.jpg)"));
    fd.setFileMode(QFileDialog::AnyFile);
    QFileInfo f(this->ui->filename->text());
    if (f.exists()) {
        fd.setDirectory(f.path());
        fd.selectFile(this->ui->filename->text());
    }
    int ret = fd.exec();
    if (ret == QDialog::Accepted) {
        QString filename = fd.selectedFiles().at(0);
        this->ui->filename->setText(filename);
        this->ui->img->setupImg(filename);
    }

}
