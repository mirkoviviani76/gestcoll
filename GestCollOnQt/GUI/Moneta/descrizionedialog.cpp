#include "descrizionedialog.h"
#include "ui_descrizionedialog.h"

DescrizioneDialog::DescrizioneDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::DescrizioneDialog)
{
    ui->setupUi(this);
    connect(this->ui->charSelector, SIGNAL(specialCharSelected(QString)),
            this, SLOT(specialCharSelected(QString)));
}

DescrizioneDialog::~DescrizioneDialog()
{
    delete ui;
}

void DescrizioneDialog::changeEvent(QEvent *e)
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

void DescrizioneDialog::setData(const QString& testo)
{
    this->ui->descrizione->setText(testo);
}

void DescrizioneDialog::getData(QString* testo)
{
    *testo = this->ui->descrizione->toPlainText();
}



/**
  Aggiunge il carattere selezionato nel testo della legenda.
  */
void DescrizioneDialog::specialCharSelected(QString sc)
{
    this->ui->descrizione->insertPlainText(sc);
}


