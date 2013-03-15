#include <genericmodel.h>
#include <stdio.h>
#include <stdlib.h>
#include <QDebug>
#include <QBrush>

GenericModel::GenericModel(const int colNum, QObject *parent) :
    QAbstractTableModel(parent),
    columns(colNum)
{
}

GenericModel::~GenericModel()
{
    foreach (GenericItem* item, this->items) {
        if (item != NULL) {
            delete item;
            item = NULL;
        }
    }

    this->clear();
}


void GenericModel::clear()
{
    //this->removeRows(0, this->rowCount());
    this->items.clear();
    this->reset();
}


bool GenericModel::appendRow(GenericItem* item)
{
    int row = this->rowCount();
    QModelIndex index = this->createIndex(row,0,item);
    this->insertRow(row, index);
    this->items.insert(row, item);
    return true;
}

QVariant GenericModel::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    if (index.row() >= this->items.size() || index.row() < 0)
        return QVariant();

    if (role == Qt::ToolTipRole)
    {
        GenericItem* item = this->items.at(index.row());
        QString tooltip = item->toTooltip();
        return tooltip;
    }
    if (role == Qt::DecorationRole && index.column() == 0)
    {
        GenericItem* item = this->items.at(index.row());
        QImage image = item->toImg();
        return image;
    }
    if (role == Qt::DisplayRole)
    {
        if (this->columnCount(index) > 1) {
            return this->items.at(index.row())->toString(index.column());
        } else {
            //l'item nella lista mostra il valore ritornato da item->getData
            return this->items.at(index.row())->toString();
        }
    }
    if (role == Qt::BackgroundRole) {
        QColor color = this->items.at(index.row())->getColor();
        if (color.isValid()) {
            //mette trasparente
            color.setAlpha(50);
            return QBrush(color);
        }/*
        else {
            if (index.row() % 2 == 0)
                return QBrush(Qt::white);
            else
                return QBrush(Qt::gray);
        }*/
    }

    return QVariant();

}

int GenericModel::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return this->items.count();
}

int GenericModel::columnCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return this->columns;
}


GenericItem* GenericModel::getItem(const QModelIndex &index)
{
    return this->items.at(index.row());
}

GenericItem* GenericModel::getItem(int index)
{
    return this->items.at(index);
}

QModelIndex GenericModel::getIndex(int index)
{
    QModelIndex ind = this->createIndex(index, 0, this->items.at(index));
    return ind;
}


