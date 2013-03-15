#include "opzionidialog.h"
#include "ui_opzionidialog.h"
#include "commondata.h"
#include <QFileInfo>
#include <QDebug>

OpzioniDialog::OpzioniDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::OpzioniDialog)
{
    ui->setupUi(this);
    //il bottone 0 è "salva"
    connect(this->ui->buttonBox->buttons().at(0), SIGNAL(clicked()), this, SLOT(accept()));
    //il bottone 1 è "cancel"
    connect(this->ui->buttonBox->buttons().at(1), SIGNAL(clicked()), this, SLOT(reject()));

    /* sistema i valori */
    this->ui->backupDirLE->setText(CommonData::getInstance()->getBackupDir());
    this->ui->biblioDirLE->setText(CommonData::getInstance()->getBiblioDir());
    this->ui->binDirLE->setText(CommonData::getInstance()->getBinDir());
    this->ui->reportDirLE->setText(CommonData::getInstance()->getReportDir());
    QFileInfo fi;
    fi.setFile(CommonData::getInstance()->getCollezione());
    this->ui->collezioneLE->setText(fi.fileName());
    fi.setFile(CommonData::getInstance()->getContatti());
    this->ui->contattiLE->setText(fi.fileName());
    fi.setFile(CommonData::getInstance()->getLinks());
    this->ui->linksLE->setText(fi.fileName());
    fi.setFile(CommonData::getInstance()->getContenitori());
    this->ui->contenitoriLE->setText(fi.fileName());
    fi.setFile(CommonData::getInstance()->getBiblioteca());
    this->ui->bibliotecaLE->setText(fi.fileName());
}

OpzioniDialog::~OpzioniDialog()
{
    delete ui;
}

void OpzioniDialog::accept() {

    QString reportDir = this->ui->reportDirLE->text();
    QString biblioDir = this->ui->biblioDirLE->text();
    QString backupDir = this->ui->backupDirLE->text();
    QString binDir = this->ui->binDirLE->text();

    QString collezione = this->ui->collezioneLE->text();
    QString links = this->ui->linksLE->text();
    QString biblio = this->ui->bibliotecaLE->text();
    QString contenitori = this->ui->contenitoriLE->text();
    QString contatti = this->ui->contattiLE->text();

    CommonData::getInstance()->setValue("Dirs/reportDir", reportDir);
    CommonData::getInstance()->setValue("Dirs/binDir", binDir);
    CommonData::getInstance()->setValue("Dirs/backupDir", backupDir);
    CommonData::getInstance()->setValue("Dirs/biblioDir", biblioDir);
    CommonData::getInstance()->setValue("Files/collezione", collezione);
    CommonData::getInstance()->setValue("Files/links", links);
    CommonData::getInstance()->setValue("Files/biblioteca", biblio);
    CommonData::getInstance()->setValue("Files/contenitori", contenitori);
    CommonData::getInstance()->setValue("Files/contatti", contatti);

    QDialog::accept();
}

void OpzioniDialog::reject() {
    QDialog::reject();
}
