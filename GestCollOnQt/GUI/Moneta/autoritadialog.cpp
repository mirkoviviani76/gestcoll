#include "autoritadialog.h"
#include "ui_autoritadialog.h"

AutoritaDialog::AutoritaDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::AutoritaDialog)
{
    ui->setupUi(this);
}

AutoritaDialog::~AutoritaDialog()
{
    delete ui;
}

void AutoritaDialog::changeEvent(QEvent *e)
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

void AutoritaDialog::setData(xml::Autorita* autorita)
{
    this->autorita = autorita;
    this->ui->lineEdit->setText(autorita->nome);
}


void AutoritaDialog::getData(QString* nome)
{
    *nome = this->ui->lineEdit->text();
}


void AutoritaDialog::on_buttonBox_accepted()
{

}
