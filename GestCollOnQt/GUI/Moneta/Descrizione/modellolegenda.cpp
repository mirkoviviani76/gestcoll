#include "modellolegenda.h"

#include <stdio.h>
#include <stdlib.h>
#include <QDebug>
#include <QBrush>

ModelloLegenda::ModelloLegenda(QObject *parent) :
    QAbstractListModel(parent)
{
}

ModelloLegenda::~ModelloLegenda()
{
    this->clear();
}


void ModelloLegenda::clear()
{
    this->beginResetModel();
    this->items.clear();
    this->endResetModel();
}


bool ModelloLegenda::appendRow(::gestColl::coins::legenda item)
{
    int row = this->rowCount();
    QModelIndex index = this->createIndex(row,0,&item);
    this->insertRow(row, index);
    this->items.insert(row, item);
    return true;
}

QVariant ModelloLegenda::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    if (index.row() >= this->items.size() || index.row() < 0)
        return QVariant();

    ::gestColl::coins::legenda item = this->items.at(index.row());

    if (role == Qt::ToolTipRole)
    {
        QString tooltip = "";
        if (item.scioglimento().present()) {
            tooltip = QString::fromStdWString(item.scioglimento().get());
        }
        return tooltip;
    }
    if (role == Qt::DisplayRole)
    {
        return QString::fromStdWString(item.testo());
    }
    if (role == Qt::BackgroundRole) {
        if (index.row() % 2 == 0)
            return QBrush(Qt::white);
        else
            return QBrush(Qt::lightGray);
    }

    return QVariant();

}

int ModelloLegenda::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return this->items.count();
}


::gestColl::coins::legenda ModelloLegenda::getItem(const QModelIndex &index)
{
    return this->items.at(index.row());
}

gestColl::coins::legenda ModelloLegenda::getItem(int index)
{
    return this->items.at(index);
}

