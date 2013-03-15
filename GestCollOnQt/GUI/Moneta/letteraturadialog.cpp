#include "letteraturadialog.h"
#include "ui_letteraturadialog.h"

LetteraturaDialog::LetteraturaDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::LetteraturaDialog)
{
    ui->setupUi(this);
}

LetteraturaDialog::~LetteraturaDialog()
{
    delete ui;
}

void LetteraturaDialog::changeEvent(QEvent *e)
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

void LetteraturaDialog::setData(xml::Libro* libro)
{
    this->libro = libro;
    this->ui->sigla->setText(libro->sigla);
    this->ui->numero->setText(libro->numero);
}

void LetteraturaDialog::getData(QString* sigla, QString* numero)
{
    /* salva i dati nell'albero dom */
    *numero = this->ui->numero->text();
    *sigla = this->ui->sigla->text();
}

void LetteraturaDialog::on_buttonBox_accepted()
{
}
