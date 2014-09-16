#include "emissioneform.h"
#include "ui_emissioneform.h"
#include "emissionedelegate.h"
#include "utils.h"
#include <QUrl>
#include <QDesktopServices>

EmissioneForm::EmissioneForm(QWidget *parent) :
    QGroupBox(parent),
    ui(new Ui::EmissioneForm)
{
    ui->setupUi(this);

    this->modelloEmissione = new EmissioneModel(this);
    this->ui->emissioneView->setModel(this->modelloEmissione);
    this->ui->emissioneView->setItemDelegate(new EmissioneDelegate(this));

    connect(this->modelloEmissione, SIGNAL(dataChanged(QModelIndex,QModelIndex)), this, SIGNAL(changesOccurred()));
    connect(this->ui->autorita, SIGNAL(changesOccurred()), this, SIGNAL(changesOccurred()));

}

EmissioneForm::~EmissioneForm()
{
    delete ui;
}

void EmissioneForm::setData(xml::Emissione emissione)
{
    this->modelloEmissione->clear();
    //this->datiFisici = emissione;
    this->modelloEmissione->appendRow(emissione);

    this->ui->autorita->setData(emissione.autorita, QString::fromStdWString(*(emissione.paese)));
    this->ui->emissioneView->resizeColumnsToContents();
}

void EmissioneForm::clear() {
    this->modelloEmissione->clear();
    this->ui->emissioneView->reset();
    this->ui->autorita->clear();
}


void EmissioneForm::setEditable(bool editable)
{

    if (editable) {
        this->ui->emissioneView->setEditTriggers(QAbstractItemView::DoubleClicked);
        this->ui->emissioneView->setSelectionBehavior(QAbstractItemView::SelectItems);
        this->ui->emissioneView->setSelectionMode(QAbstractItemView::SingleSelection);
    } else {
        this->ui->emissioneView->setEditTriggers(QAbstractItemView::NoEditTriggers);
        this->ui->emissioneView->setSelectionBehavior(QAbstractItemView::SelectRows);
        this->ui->emissioneView->setSelectionMode(QAbstractItemView::SingleSelection);
    }
    this->ui->autorita->setEditable(editable);

    editingEnabled = editable;

}

void EmissioneForm::on_emissioneView_doubleClicked(const QModelIndex &index)
{
    if ((this->editingEnabled == false) && (index.column() == 0)) {

        QString paese = QString::fromStdWString((*this->modelloEmissione->getItem().paese));
        QDesktopServices::openUrl(Utils::getSearchUrl(paese));
    }

}
