#include "posizionedialog.h"
#include "ui_posizionedialog.h"

posizioneDialog::posizioneDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::posizioneDialog)
{
    ui->setupUi(this);
}

posizioneDialog::~posizioneDialog()
{
    delete ui;
}

void posizioneDialog::changeEvent(QEvent *e)
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


void posizioneDialog::setData(QString cont, QString vass, QString r, QString c)
{
    this->ui->contenitore->setText(cont);
    this->ui->vassoio->setText(vass);
    this->ui->riga->setText(r);
    this->ui->colonna->setText(c);
}

void posizioneDialog::getData(QString& cont, QString& vass, QString& r, QString& c)
{
    cont = this->ui->contenitore->text();
    vass = this->ui->vassoio->text();
    r = this->ui->riga->text();
    c = this->ui->colonna->text();
}


