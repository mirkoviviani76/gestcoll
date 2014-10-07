#include "emissioneform.h"
#include "ui_emissioneform.h"
#include "utils.h"
#include <QUrl>
#include <QDesktopServices>

EmissioneForm::EmissioneForm(QWidget *parent) :
    QGroupBox(parent),
    ui(new Ui::EmissioneForm)
{
    ui->setupUi(this);

    connect(this->ui->autorita, SIGNAL(changesOccurred()), this, SIGNAL(changesOccurred()));
    connect(this->ui->paese, SIGNAL(contentsChanged()), this, SLOT(paeseChanged()));

}

EmissioneForm::~EmissioneForm()
{
    delete ui;
}

void EmissioneForm::setData(xml::Emissione _emissione)
{
    this->emissione = _emissione;
    this->ui->anno->setText(QString::fromStdWString(*this->emissione.anno));
    this->ui->valore->setText(QString::fromStdWString(this->emissione.nominale->valore()));
    this->ui->valuta->setText(QString::fromStdWString(this->emissione.nominale->valuta()));
    this->ui->paese->setText(QString::fromStdWString(*this->emissione.paese));
    this->ui->autorita->setData(_emissione.autorita, QString::fromStdWString(*(_emissione.paese)));
}

void EmissioneForm::clear() {
    this->ui->autorita->clear();
}


void EmissioneForm::setEditable(bool editable)
{
    this->ui->autorita->setEditable(editable);
    this->ui->paese->setEditingEnabled(editable);

    this->ui->valore->setReadOnly(!editable);
    this->ui->valuta->setReadOnly(!editable);
    this->ui->anno->setReadOnly(!editable);
    this->ui->valore->setFrame(editable);
    this->ui->valuta->setFrame(editable);
    this->ui->anno->setFrame(editable);

    editingEnabled = editable;

}


void EmissioneForm::on_valore_editingFinished()
{
    this->emissione.nominale->valore(this->ui->valore->text().toStdWString());
    emit changesOccurred();
}

void EmissioneForm::on_valuta_editingFinished()
{
    this->emissione.nominale->valuta(this->ui->valuta->text().toStdWString());
    emit changesOccurred();
}

void EmissioneForm::on_anno_editingFinished()
{
    *(this->emissione.anno) = (this->ui->anno->text().toStdWString());
    emit changesOccurred();
}

void EmissioneForm::paeseChanged()
{
    if (this->editingEnabled == false)
        return;

    QString newPaese = this->ui->paese->getPaese();
    *(this->emissione.paese) = (newPaese.toStdWString());
    emit changesOccurred();

}

