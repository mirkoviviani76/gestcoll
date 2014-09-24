#include "newcontattodialog.h"
#include "ui_newcontattodialog.h"

NewContattoDialog::NewContattoDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::NewContattoDialog)
{
    ui->setupUi(this);
}

NewContattoDialog::~NewContattoDialog()
{
    delete ui;
}

QString NewContattoDialog::getNome() {
    QString c = this->ui->nomeLE->text();
    return c;
}

QString NewContattoDialog::getEmail() {
    QString c = this->ui->emailLE->text();
    return c;
}

QString NewContattoDialog::getNote() {
    QString c = this->ui->noteLE->text();
    return c;
}

void NewContattoDialog::setData(const gestColl::contatti::contatto &cont) {
    this->ui->nomeLE->setText(QString::fromStdWString(cont.nome()));
    this->ui->emailLE->setText(QString::fromStdWString(cont.email()));
    this->ui->noteLE->setText(QString::fromStdWString(cont.note()));
}

