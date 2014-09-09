#include "misuraform.h"
#include "ui_misuraform.h"
#include <QDebug>

MisuraForm::MisuraForm(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::MisuraForm)
{
    ui->setupUi(this);
    this->changedUnita = false;
    this->changedValore = false;
}

MisuraForm::~MisuraForm()
{
    delete ui;
}

void MisuraForm::changeEvent(QEvent *e)
{
    QWidget::changeEvent(e);
    switch (e->type()) {
    case QEvent::LanguageChange:
        ui->retranslateUi(this);
        break;
    default:
        break;
    }
}

void MisuraForm::setValore(double valore)
{
    this->ui->valore->setText(QString("%1").arg(valore));
}

void MisuraForm::setValore(QString valore)
{
    this->ui->valore->setText(valore);
}

void MisuraForm::setUnita(QString unita)
{
    this->ui->unita->setText(unita);
}


void MisuraForm::setData(QString valore, QString unita)
{
    this->setUnita(unita);
    this->setValore(valore);
}

void MisuraForm::setData(xml::Misura misura)
{
    this->setUnita(misura.unita);
    this->setValore(misura.valore);
}


void MisuraForm::enableEdit(bool editable)
{
    this->ui->unita->setReadOnly(!editable);
    this->ui->valore->setReadOnly(!editable);
    this->ui->unita->setFrame(editable);
    this->ui->valore->setFrame(editable);
}


void MisuraForm::on_valore_textChanged(QString text)
{
    this->changedValore = true;
    if (this->changedValore || this->changedUnita)
    {
        emit this->textChanged(text, this->ui->unita->text());
        //reset
        this->changedUnita = false;
        this->changedValore = false;
    }
}

void MisuraForm::on_unita_textChanged(QString text)
{
    this->changedUnita = true;
    if (this->changedValore || this->changedUnita)
    {
        emit this->textChanged(this->ui->valore->text(), text);
        //reset
        this->changedUnita = false;
        this->changedValore = false;
    }

}
