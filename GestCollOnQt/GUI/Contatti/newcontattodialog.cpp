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

void NewContattoDialog::setData(const xml::Contatto &cont) {
    this->ui->nomeLE->setText(cont.nome);
    this->ui->emailLE->setText(cont.email);
    this->ui->noteLE->setText(cont.note);
}

