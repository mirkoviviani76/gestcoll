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

void NotaDialog::setData(::gestColl::coins::moneta::note_type::nota_type nota)
{
    this->ui->textEdit->setText(QString::fromStdWString(nota));
}


void NotaDialog::getData(gestColl::coins::note::nota_type* nota)
{
    *nota = this->ui->textEdit->toPlainText().toStdWString();
}

void NotaDialog::on_buttonBox_accepted()
{
}
