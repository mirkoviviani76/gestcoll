#include "datiacquistowidget.h"
#include "ui_datiacquistowidget.h"

DatiAcquistoWidget::DatiAcquistoWidget(QWidget *parent) :
    QGroupBox(parent),
    ui(new Ui::DatiAcquistoWidget)
{
    ui->setupUi(this);
    this->modelloDatiAcquisto = new DatiAcquistoModel(this);
    this->ui->datiAcquistoView->setModel(this->modelloDatiAcquisto);
    connect(this->modelloDatiAcquisto, SIGNAL(dataChanged(QModelIndex,QModelIndex)), this, SIGNAL(changesOccurred()));
    this->ui->datiAcquistoView->setItemDelegate(new DatiAcquistoDelegate(this));
}

DatiAcquistoWidget::~DatiAcquistoWidget()
{
    delete ui;
}

void DatiAcquistoWidget::setData(gestColl::coins::datiAcquisto* datiAcquisto)
{
    this->modelloDatiAcquisto->clear();
    this->datiAcquisto = datiAcquisto;
    this->modelloDatiAcquisto->appendRow(datiAcquisto);

    this->ui->datiAcquistoView->resizeColumnsToContents();
}

void DatiAcquistoWidget::setEditable(bool editable)
{
    if (editable) {
        this->ui->datiAcquistoView->setEditTriggers(QAbstractItemView::DoubleClicked);
        this->ui->datiAcquistoView->setSelectionBehavior(QAbstractItemView::SelectItems);
        this->ui->datiAcquistoView->setSelectionMode(QAbstractItemView::SingleSelection);
    } else {
        this->ui->datiAcquistoView->setEditTriggers(QAbstractItemView::NoEditTriggers);
        this->ui->datiAcquistoView->setSelectionBehavior(QAbstractItemView::SelectRows);
        this->ui->datiAcquistoView->setSelectionMode(QAbstractItemView::SingleSelection);
    }
}
