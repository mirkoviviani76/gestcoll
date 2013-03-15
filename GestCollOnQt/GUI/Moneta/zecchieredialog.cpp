#include "zecchieredialog.h"
#include "ui_zecchieredialog.h"

ZecchiereDialog::ZecchiereDialog(bool enableEdit, QWidget *parent) :
    QDialog(parent),
    ui(new Ui::ZecchiereDialog)
{
    ui->setupUi(this);
    this->ui->ruolo->setEditable(enableEdit);
    this->ui->segno->setReadOnly(!enableEdit);
    this->ui->nome->setReadOnly(!enableEdit);
}

ZecchiereDialog::~ZecchiereDialog()
{
    delete ui;
}

void ZecchiereDialog::changeEvent(QEvent *e)
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

void ZecchiereDialog::setData(xml::Zecchiere* zecchiere)
{
    this->zecchiere = zecchiere;
    this->ui->nome->setText(zecchiere->nome);
    this->ui->segno->setText(zecchiere->segno);
    this->ui->ruolo->setEditText(zecchiere->ruolo);
}

void ZecchiereDialog::getData(QString* nome, QString* sigla, QString* ruolo)
{
    /* salva i dati nell'albero dom */
    *nome = this->ui->nome->text();
    *sigla = this->ui->segno->text();
    *ruolo = this->ui->ruolo->currentText();
}

void ZecchiereDialog::on_buttonBox_accepted()
{
}

