#include "zeccawidget.h"
#include "ui_zeccawidget.h"
#include "zecchieredelegate.h"

ZeccaWidget::ZeccaWidget(QWidget *parent) :
    QGroupBox(parent),
    zecca(NULL),
    ui(new Ui::ZeccaWidget)
{
    ui->setupUi(this);

    this->modelloZecchiere = new ZecchiereModel(this);
    this->ui->zecchieri->setModel(this->modelloZecchiere);
    this->ui->zecchieri->setItemDelegate(new ZecchiereDelegate(this));
    connect(this->modelloZecchiere, SIGNAL(dataChanged(QModelIndex,QModelIndex)), this, SIGNAL(changesOccurred()));
    connect(this->ui->zecca, SIGNAL(textChanged(QString,QString)), this, SLOT(changeZecca(QString, QString)));

}

ZeccaWidget::~ZeccaWidget()
{
    delete ui;
}

void ZeccaWidget::setData(gestColl::coins::moneta::zecca_type *_zecca, gestColl::coins::moneta::zecchieri_type *_zecchieri)
{
    this->zecca = _zecca;
    QString nome = "";
    QString segno = "";
    if (this->zecca->nome().present()) {
        nome = QString::fromStdWString(this->zecca->nome().get());
    }
    if (this->zecca->segno().present()) {
        segno = QString::fromStdWString(this->zecca->segno().get());
    }
    this->ui->zecca->setData(nome, segno);

    this->modelloZecchiere->clear();
    this->modelloZecchiere->setData(_zecchieri);

    this->ui->zecchieri->resizeColumnsToContents();
}

void ZeccaWidget::setEditable(bool editable)
{
    if (editable) {
        this->ui->zecchieri->setEditTriggers(QAbstractItemView::DoubleClicked);
        this->ui->zecchieri->setSelectionBehavior(QAbstractItemView::SelectItems);
        this->ui->zecchieri->setSelectionMode(QAbstractItemView::SingleSelection);
    } else {
        this->ui->zecchieri->setEditTriggers(QAbstractItemView::NoEditTriggers);
        this->ui->zecchieri->setSelectionBehavior(QAbstractItemView::SelectRows);
        this->ui->zecchieri->setSelectionMode(QAbstractItemView::SingleSelection);
    }
    this->ui->addZecchiere->setVisible(editable);
    this->ui->deleteZecchiere->setVisible(editable);
    this->ui->zecca->enableEdit(editable);

}

void ZeccaWidget::on_addZecchiere_clicked()
{
    /* aggiunge il nodo al dom */
    ::gestColl::coins::zecchiere* newZecchiere = new ::gestColl::coins::zecchiere();
    this->modelloZecchiere->appendRow(newZecchiere);

    ::gestColl::coins::moneta::zecchieri_type* zecchieri = this->modelloZecchiere->getItem();
    this->modelloZecchiere->clear();
    this->modelloZecchiere->setData(zecchieri);
    this->ui->zecchieri->resizeColumnsToContents();

    //segnala la modifica
    emit this->changesOccurred();
}

void ZeccaWidget::on_deleteZecchiere_clicked()
{
    foreach (QModelIndex index, this->ui->zecchieri->selectionModel()->selectedIndexes()) {
        /* toglie il nodo dal dom */
        this->modelloZecchiere->removeRow(index);
    }
    ::gestColl::coins::moneta::zecchieri_type* zecchieri = this->modelloZecchiere->getItem();
    this->modelloZecchiere->clear();
    this->modelloZecchiere->setData(zecchieri);
    this->ui->zecchieri->resizeColumnsToContents();

    //segnala la modifica
    emit this->changesOccurred();
}

void ZeccaWidget::changeZecca(const QString &nome, const QString &segno)
{
    this->zecca->nome().set(nome.toStdWString());
    this->zecca->segno().set(segno.toStdWString());

    emit changesOccurred();
}
