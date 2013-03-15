#include "modifyambitodialog.h"
#include "ui_modifyambitodialog.h"
#include "commondata.h"

ModifyAmbitoDialog::ModifyAmbitoDialog(xml::Ambito* a, QWidget *parent) :
    QDialog(parent),
    ui(new Ui::ModifyAmbitoDialog)
{
    ui->setupUi(this);
    this->ui->titolo->setText(a->titolo);
    this->ui->icona->setText(a->icona);
    this->ambito = a;
}

ModifyAmbitoDialog::ModifyAmbitoDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::ModifyAmbitoDialog)
{
    ui->setupUi(this);
    this->ambito = new xml::Ambito("", "");
}


ModifyAmbitoDialog::~ModifyAmbitoDialog()
{
    delete ui;
}

xml::Ambito* ModifyAmbitoDialog::getData() {
    this->ambito->titolo = this->ui->titolo->text();
    this->ambito->icona = this->ui->icona->text();
    return this->ambito;
}

