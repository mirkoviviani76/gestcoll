#include "documentimodel.h"

#include <stdio.h>
#include <stdlib.h>
#include <QDebug>
#include <QBrush>
#include "commondata.h"

DocumentiModel::DocumentiModel(QObject *parent) :
    QAbstractListModel(parent)
{
}

DocumentiModel::~DocumentiModel()
{
    this->clear();
}


void DocumentiModel::clear()
{
    this->beginResetModel();
    this->items.clear();
    this->endResetModel();
}


bool DocumentiModel::appendRow(::gestColl::coins::moneta::itemAddizionali_type::documento_type item)
{
    int row = this->rowCount();
    QModelIndex index = this->createIndex(row,0,&item);
    this->insertRow(row, index);
    this->items.insert(row, item);
    return true;
}

QVariant DocumentiModel::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    if (index.row() >= this->items.size() || index.row() < 0)
        return QVariant();

    ::gestColl::coins::moneta::itemAddizionali_type::documento_type item = this->items.at(index.row());

    if (role == Qt::ToolTipRole)
    {
        QString tooltip = QString::fromStdWString(item.filename());
        return tooltip;
    }
    if (role == Qt::DisplayRole)
    {
        return QString::fromStdWString(item.descrizione());
    }

    return QVariant();

}

int DocumentiModel::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return this->items.count();
}


::gestColl::coins::moneta::itemAddizionali_type::documento_type DocumentiModel::getItem(const QModelIndex &index)
{
    return this->items.at(index.row());
}

::gestColl::coins::moneta::itemAddizionali_type::documento_type DocumentiModel::getItem(int index)
{
    return this->items.at(index);
}

