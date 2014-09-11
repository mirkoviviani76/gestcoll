#include "datifisiciwidget.h"
#include "ui_datifisiciwidget.h"
#include "datifisicidelegate.h"

DatiFisiciWidget::DatiFisiciWidget(QWidget *parent) :
    QGroupBox(parent),
    ui(new Ui::DatiFisiciWidget)
{
    ui->setupUi(this);

    this->modelloDatiFisici = new DatiFisiciModel(this);
    this->ui->datiFisiciTable->setModel(this->modelloDatiFisici);
    connect(this->modelloDatiFisici, SIGNAL(dataChanged(QModelIndex,QModelIndex)), this, SIGNAL(changesOccurred()));
    this->ui->datiFisiciTable->setItemDelegate(new DatiFisiciDelegate(this));

}

DatiFisiciWidget::~DatiFisiciWidget()
{
    delete ui;
}

void DatiFisiciWidget::setData(gestColl::coins::datiFisici* datiFisici)
{
    this->modelloDatiFisici->clear();
    this->datiFisici = datiFisici;
    this->modelloDatiFisici->appendRow(datiFisici);
}

void DatiFisiciWidget::setEditable(bool editable)
{
    if (editable) {
        this->ui->datiFisiciTable->setEditTriggers(QAbstractItemView::DoubleClicked);
    } else {
        this->ui->datiFisiciTable->setEditTriggers(QAbstractItemView::NoEditTriggers);
    }

}
