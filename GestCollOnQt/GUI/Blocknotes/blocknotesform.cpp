#include "blocknotesform.h"
#include "ui_blocknotesform.h"
#include "commondata.h"
#include <QFile>
#include <QDir>
#include <QDebug>
#include <QFileDialog>
#include <QMessageBox>
#include <QPushButton>

BlocknotesForm::BlocknotesForm(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::BlocknotesForm)
{
    ui->setupUi(this);
    this->ui->textEdit->setVisible(false);
    this->ui->charselector->setVisible(false);
    this->ui->splitter->setStretchFactor(0, 0);
    this->ui->splitter->setStretchFactor(1, 1);

    /* riempie i valori della lista selezionando la dir corretta */
    this->blocknotesListModel = new QFileSystemModel(this);
    QString bnDir = CommonData::getInstance()->getBlocknotesDir();
    this->blocknotesListModel->setRootPath(bnDir);

    this->selectedFile = "";

    /* setta il modello e la vista */
    this->ui->treeView->setModel(this->blocknotesListModel);
    this->ui->treeView->setRootIndex(blocknotesListModel->index(bnDir));
    //connette il segnale relativo al click sul pulsante del carattere speciale con lo slot che inserisce il carattere nella casella di testo
    connect(this->ui->charselector, SIGNAL(specialCharSelected(QString)), this->ui->textEdit, SLOT(insertPlainText(QString)));

}

BlocknotesForm::~BlocknotesForm()
{
    delete ui;
    if (this->blocknotesListModel != NULL) {
        delete this->blocknotesListModel;
        this->blocknotesListModel = NULL;
    }
}


void BlocknotesForm::salva() {
    /* ottiene il file */
    if (this->selectedFile == "") {
        QFileDialog dialog(this, "Salva nuova nota");
        dialog.setNameFilter(tr("Note (*.txt)"));
        dialog.setFileMode(QFileDialog::AnyFile);
        dialog.setDirectory(CommonData::getInstance()->getBlocknotesDir());
        if (dialog.exec()) {
            this->selectedFile = dialog.selectedFiles().at(0);
            /* crea il file */
            QFile file(this->selectedFile);
            file.open(QFile::WriteOnly);
            file.close();
        }
    }

    QFile file(this->selectedFile);
    if (!file.exists()) {
        Log::Logger::getInstance()->log(QString("Il file %1 non esiste.").arg(this->selectedFile), Log::ERR);
        return;
    }

    bool ret = false;
    /* apre il file e lo legge completamente */
    ret = file.open(QFile::ReadWrite);
    if (ret) {
        /* legge il contenuto */
        QString content = this->ui->textEdit->toPlainText();
        /* salva  */
        QTextStream stream(&file);
        stream << content;
        stream.flush();
    } else {
        Log::Logger::getInstance()->log(QString("Errore nell'apertura del file %1").arg(this->selectedFile), Log::ERR);
    }
    /* chiude il file */
    file.close();

}


void BlocknotesForm::on_treeView_activated(const QModelIndex &index)
{
    /* ottiene il file */
    this->selectedFile = this->blocknotesListModel->fileInfo(index).absoluteFilePath();
    QFile file(this->selectedFile);
    bool ret = false;
    /* apre il file e lo legge completamente */
    ret = file.open(QFile::ReadWrite);
    if (ret) {
        QString content = file.readAll();
        /* carica nella vista il contenuto */
        this->ui->textEdit->setText(content);
    } else {
        Log::Logger::getInstance()->log(QString("Errore nell'apertura del file %1").arg(this->selectedFile), Log::ERR);
    }
    /* chiude il file */
    file.close();

    this->ui->textEdit->setVisible(true);
    this->ui->charselector->setVisible(true);
}


void BlocknotesForm::addNote() {
    QMessageBox msgBox;
    msgBox.setModal(true);
    msgBox.setIcon(QMessageBox::Question);
    msgBox.setText("Scegli il tipo di documento.");
    msgBox.setInformativeText("Vuoi riempire il testo con indici utili per l'inserimento di una nuova moneta?");
    msgBox.setStandardButtons(QMessageBox::Yes | QMessageBox::No);
    int ret = msgBox.exec();
    QString text = "";
    if (ret == QMessageBox::Yes) { //nuova moneta
        /* legge il contenuto del template (c'è per forza) */
        QString fileTemplate = CommonData::getInstance()->getBlocknoteTemplate();
        QFile mytemplate(fileTemplate);
        mytemplate.open(QFile::ReadOnly);
        text = mytemplate.readAll();
        mytemplate.close();
    }
    this->ui->textEdit->setPlainText(text);
    this->selectedFile = "";
    this->ui->textEdit->setVisible(true);
    this->ui->charselector->setVisible(true);

}


void BlocknotesForm::on_textEdit_textChanged()
{
    emit this->changesOccurred();
}
