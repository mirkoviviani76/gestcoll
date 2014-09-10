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

void AutoritaDialog::setData(::gestColl::coins::autorita::nome_type autorita)
{
    this->ui->lineEdit->setText(QString::fromStdWString(autorita));
}


void AutoritaDialog::getData(gestColl::coins::autorita::nome_type *nome)
{
    *nome = this->ui->lineEdit->text().toStdWString();
}

