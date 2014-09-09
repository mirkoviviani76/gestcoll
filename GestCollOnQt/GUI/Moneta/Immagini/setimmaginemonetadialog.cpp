#include "setimmaginemonetadialog.h"
#include "ui_setimmaginemonetadialog.h"

#include <QFile>
#include <QImage>
#include <QFileDialog>
#include <QDebug>
#include <QMessageBox>
#include "commondata.h"

SetImmagineMonetaDialog::SetImmagineMonetaDialog(const QString &filename, const QString& id, const QString& _latoId, QWidget *parent) :
    QDialog(parent),
    monetaId(id),
    latoId(_latoId),
    ui(new Ui::SetImmagineMonetaDialog)
{
    this->oldFilename = filename;

    ui->setupUi(this);

    this->ui->title->setText(QString("Imposta immagine moneta %1").arg(id));

    this->suggestedFilename = QString("%1-%2.jpg")
            .arg(monetaId)
            .arg(latoId);

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

QString SetImmagineMonetaDialog::getNewFilename()
{
    return this->targetFilename;
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
        this->targetFilename = "";
        return;
    }

    QFileInfo info(filename);
    if (info.exists()) {
        this->targetFilename = "";
        if (this->ui->checkBox_rinomina->isChecked()) {
            this->targetFilename = this->suggestedFilename;
        } else {
            this->targetFilename = info.fileName();
        }
        //copia nella dir immagini
        QString target = QString("%1/%2")
                .arg(CommonData::getInstance()->getImgDir())
                .arg(targetFilename);
        QFileInfo infoTarget(target);
        if (infoTarget.exists() || filename == target) {
            QMessageBox::critical(this,
                                  CommonData::getInstance()->getAppName(),
                                  "File gia' presente");
        } else {
            QFile source(filename);
            bool ris = source.copy(target);
            if (!ris) {
                QMessageBox::critical(this,
                                      CommonData::getInstance()->getAppName(),
                                      "Copia non riuscita");
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
