#include "datiacquistowidget.h"
#include "ui_datiacquistowidget.h"

DatiAcquistoWidget::DatiAcquistoWidget(QWidget *parent) :
    QGroupBox(parent),
    ui(new Ui::DatiAcquistoWidget)
{
    ui->setupUi(this);
}

DatiAcquistoWidget::~DatiAcquistoWidget()
{
    delete ui;
}

void DatiAcquistoWidget::setData(gestColl::coins::datiAcquisto* datiAcquisto)
{
    this->datiAcquisto = datiAcquisto;

    this->ui->luogo->setText(QString::fromStdWString(this->datiAcquisto->luogo()));
    QDate dataCorrente;
    dataCorrente.setDate(this->datiAcquisto->data().year(), this->datiAcquisto->data().month(), this->datiAcquisto->data().day());
    this->ui->data->setDate(dataCorrente);
    this->ui->prezzo->setValue(this->datiAcquisto->prezzo().valore());
    this->ui->prezzo->setSuffix(" "+QString::fromStdWString(this->datiAcquisto->prezzo().unita()));

}

void DatiAcquistoWidget::setEditable(bool editable)
{
    if (editable) {
        this->ui->prezzo->setButtonSymbols(QAbstractSpinBox::UpDownArrows);
    } else {
        this->ui->prezzo->setButtonSymbols(QAbstractSpinBox::NoButtons);
    }

    this->ui->data->setCalendarPopup(true);

    this->ui->luogo->setReadOnly(!editable);
    this->ui->data->setReadOnly(!editable);
    this->ui->prezzo->setReadOnly(!editable);

    this->ui->luogo->setFrame(editable);
    this->ui->data->setFrame(editable);
    this->ui->prezzo->setFrame(editable);

}

void DatiAcquistoWidget::on_luogo_editingFinished()
{
    this->datiAcquisto->luogo(this->ui->luogo->text().toStdWString());
    emit changesOccurred();
}

void DatiAcquistoWidget::on_data_editingFinished()
{
    QDate date = this->ui->data->date();
    xml_schema::date xmlData(date.year(), date.month(), date.day());
    this->datiAcquisto->data(xmlData);
    emit changesOccurred();
}

void DatiAcquistoWidget::on_prezzo_editingFinished()
{
    this->datiAcquisto->prezzo().valore(this->ui->prezzo->value());
    emit changesOccurred();
}
