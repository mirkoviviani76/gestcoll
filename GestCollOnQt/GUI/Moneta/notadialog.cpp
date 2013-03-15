#include "notadialog.h"
#include "ui_notadialog.h"


NotaDialog::NotaDialog(bool editable, QWidget *parent) :
    QDialog(parent),
    ui(new Ui::NotaDialog)
{
    ui->setupUi(this);
    this->ui->textEdit->setReadOnly(!editable);
}

NotaDialog::~NotaDialog()
{
    delete ui;
}

void NotaDialog::changeEvent(QEvent *e)
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

void NotaDialog::setData(xml::Nota* nota)
{
    this->nota = nota;
    this->ui->textEdit->setText(nota->testo);
}


void NotaDialog::getData(xml::Nota* nota)
{
    nota->testo = this->ui->textEdit->toPlainText();
}

void NotaDialog::on_buttonBox_accepted()
{
}
