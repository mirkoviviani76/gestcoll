#include "adddocumentdialog.h"
#include "ui_adddocumentdialog.h"
#include <QFileDialog>
#include <QFileInfo>
#include "commondata.h"

AddDocumentDialog::AddDocumentDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::AddDocumentDialog)
{
    ui->setupUi(this);
}

AddDocumentDialog::~AddDocumentDialog()
{
    delete ui;
}

QStringList AddDocumentDialog::getFilenames() {
    return this->selectedFiles;
}


QString AddDocumentDialog::getDescrizione() {
    return this->ui->descrizione->toPlainText();
}


void AddDocumentDialog::on_selectFile_clicked()
{
    this->selectedFiles.clear();
    QFileDialog dialog(this, "Scegli il documento");
    dialog.setFileMode(QFileDialog::ExistingFiles);
    QString docDir = CommonData::getInstance()->getDocDir();
    dialog.setDirectory(docDir);
    QFileInfo fullDocDir(docDir);
    fullDocDir.makeAbsolute();
    if (dialog.exec()) {
        QStringList temp = dialog.selectedFiles();
        foreach (QString curFile, temp) {
            QFileInfo file(curFile);
            //toglie dal nome del file la directory dei documenti
            QString shortFilename = file.absoluteFilePath().remove(fullDocDir.absoluteFilePath()+"/");
            this->selectedFiles << shortFilename;
        }

        this->ui->filename->setText(this->selectedFiles.join(", "));

    }
}

void AddDocumentDialog::setData(xml::Documento *doc) {
    this->ui->filename->setText(doc->filename);
    this->ui->descrizione->setPlainText(doc->descrizione);
}

