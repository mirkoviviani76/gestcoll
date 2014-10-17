#include "modifyambitodialog.h"
#include "ui_modifyambitodialog.h"
#include "commondata.h"

ModifyAmbitoDialog::ModifyAmbitoDialog(xml::Ambito* a, QWidget *parent) :
    QDialog(parent),
    ui(new Ui::ModifyAmbitoDialog)
{
    ui->setupUi(this);
    this->ui->titolo->setText(a->getTitolo());
    this->ui->icona->setText(a->getIcona());
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
    this->ambito->setTitolo(this->ui->titolo->text());
    this->ambito->setIcona(this->ui->icona->text());
    return this->ambito;
}

