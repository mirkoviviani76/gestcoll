#include "legendadialog.h"
#include "ui_legendadialog.h"
#include <QDebug>

LegendaDialog::LegendaDialog(bool editingMode, QWidget *parent) :
    QDialog(parent),
    ui(new Ui::LegendaDialog)
{
    ui->setupUi(this);
    if (editingMode == true) {
        this->ui->testo->setFrame(true);
        this->ui->testo->setReadOnly(false);
        this->ui->scioglimento->setFrame(true);
        this->ui->scioglimento->setReadOnly(false);
        this->setWindowTitle("Modifica Legenda");
        //permette di gestire il click su un carattere speciale
        connect(this->ui->widget_2, SIGNAL(specialCharSelected(QString)),
                this, SLOT(specialCharSelected(QString)));
    } else {
        this->ui->testo->setFrame(false);
        this->ui->testo->setReadOnly(true);
        this->ui->scioglimento->setFrame(false);
        this->ui->scioglimento->setReadOnly(true);
        this->ui->widget_2->setVisible(false);
        this->setWindowTitle("Legenda");
    }
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


void LegendaDialog::setData(::gestColl::coins::legenda legenda)
{
    this->ui->testo->setText(QString::fromStdWString(legenda.testo()));
    if (legenda.scioglimento().present()) {
        this->ui->scioglimento->setText(QString::fromStdWString(legenda.scioglimento().get()));
    } else {
        this->ui->scioglimento->setText("");
    }
}

void LegendaDialog::getData(QString* testo, QString* scioglimento)
{
    *testo = this->ui->testo->text();
    *scioglimento = this->ui->scioglimento->text();
}


/**
  Aggiunge il carattere selezionato nel testo della legenda.
  */
void LegendaDialog::specialCharSelected(QString sc)
{
    this->ui->testo->insert(sc);
    this->ui->testo->setFocus();
}

