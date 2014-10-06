#include "datifisiciwidget.h"
#include "ui_datifisiciwidget.h"

DatiFisiciWidget::DatiFisiciWidget(QWidget *parent) :
    QGroupBox(parent),
    ui(new Ui::DatiFisiciWidget)
{
    ui->setupUi(this);

}

DatiFisiciWidget::~DatiFisiciWidget()
{
    delete ui;
}

void DatiFisiciWidget::setData(gestColl::coins::datiFisici* datiFisici)
{
    this->datiFisici = datiFisici;

    this->ui->dimensione->setValue(datiFisici->diametro().valore());
    this->ui->dimensione->setSuffix(" "+QString::fromStdWString(datiFisici->diametro().unita()));
    this->ui->peso->setValue(datiFisici->peso().valore());
    this->ui->peso->setSuffix(" "+QString::fromStdWString(datiFisici->peso().unita()));
    this->ui->forma->setText(QString::fromStdWString(datiFisici->forma()));
    this->ui->metallo->setText(QString::fromStdWString(datiFisici->metallo()));

}

void DatiFisiciWidget::setEditable(bool editable)
{

    if (editable) {
        this->ui->dimensione->setButtonSymbols(QAbstractSpinBox::UpDownArrows);
        this->ui->peso->setButtonSymbols(QAbstractSpinBox::UpDownArrows);
    } else {
        this->ui->dimensione->setButtonSymbols(QAbstractSpinBox::NoButtons);
        this->ui->peso->setButtonSymbols(QAbstractSpinBox::NoButtons);

    }

    this->ui->dimensione->setReadOnly(!editable);
    this->ui->peso->setReadOnly(!editable);
    this->ui->forma->setReadOnly(!editable);
    this->ui->metallo->setReadOnly(!editable);

    this->ui->dimensione->setFrame(editable);
    this->ui->peso->setFrame(editable);
    this->ui->forma->setFrame(editable);
    this->ui->metallo->setFrame(editable);



}

void DatiFisiciWidget::on_peso_editingFinished()
{
    this->datiFisici->peso().valore(this->ui->peso->value());
    emit this->changesOccurred();
}

void DatiFisiciWidget::on_dimensione_editingFinished()
{
    this->datiFisici->diametro().valore(this->ui->dimensione->value());
    emit this->changesOccurred();
}

void DatiFisiciWidget::on_forma_editingFinished()
{
    this->datiFisici->forma(this->ui->forma->text().toStdWString());
    emit this->changesOccurred();
}

void DatiFisiciWidget::on_metallo_editingFinished()
{
    this->datiFisici->metallo(this->ui->metallo->text().toStdWString());
    emit this->changesOccurred();
}
