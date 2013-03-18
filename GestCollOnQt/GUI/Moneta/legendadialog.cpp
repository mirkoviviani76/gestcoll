#include "legendadialog.h"
#include "ui_legendadialog.h"
#include <QDebug>

LegendaDialog::LegendaDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::LegendaDialog)
{
    ui->setupUi(this);
    //permette di gestire il click su un carattere speciale
    connect(this->ui->widget_2, SIGNAL(specialCharSelected(QString)),
            this, SLOT(specialCharSelected(QString)));
}

LegendaDialog::~LegendaDialog()
{
    delete ui;
}

void LegendaDialog::changeEvent(QEvent *e)
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


void LegendaDialog::setData(xml::Legenda* legenda)
{
    this->legenda = legenda;
    this->ui->testo->setText(legenda->testo);
    this->ui->scioglimento->setText(legenda->scioglimento);
}

void LegendaDialog::getData(QString* testo, QString* scioglimento)
{
    *testo = this->ui->testo->text();
    *scioglimento = this->ui->scioglimento->text();
}


void LegendaDialog::on_buttonBox_accepted()
{
}

/**
  Aggiunge il carattere selezionato nel testo della legenda.
  */
void LegendaDialog::specialCharSelected(QString sc)
{
    this->ui->testo->insert(sc);
    this->ui->testo->setFocus();
}
