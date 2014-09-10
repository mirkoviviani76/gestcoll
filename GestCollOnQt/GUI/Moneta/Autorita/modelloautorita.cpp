#include "modelloautorita.h"

#include "modellolegenda.h"

#include <stdio.h>
#include <stdlib.h>
#include <QDebug>
#include <QBrush>

ModelloAutorita::ModelloAutorita(QObject *parent) :
    QAbstractListModel(parent)
{
}

ModelloAutorita::~ModelloAutorita()
{
    this->clear();
}


void ModelloAutorita::clear()
{
    this->beginResetModel();
    this->endResetModel();
}


bool ModelloAutorita::fillData(::gestColl::coins::autorita* _items)
{
    this->items = _items;
    int row = this->rowCount();
    for ( ::gestColl::coins::autorita::nome_iterator it = this->items->nome().begin();
          it != this->items->nome().end();
          ++it
          ) {
        QModelIndex index = this->createIndex(row,0,&(*it));
        this->insertRow(row, index);
    }
    return true;
}

QVariant ModelloAutorita::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    if (index.row() >= this->items->nome().size() || index.row() < 0)
        return QVariant();

    ::gestColl::coins::autorita::nome_type item = this->items->nome().at(index.row());

    if (role == Qt::DisplayRole)
    {
        return QString::fromStdWString(item);
    }

    return QVariant();

}

int ModelloAutorita::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return this->items->nome().size();
}


::gestColl::coins::autorita::nome_type ModelloAutorita::getItem(const QModelIndex &index)
{
    return this->items->nome().at(index.row());
}

gestColl::coins::autorita::nome_type ModelloAutorita::getItem(int index)
{
    return this->items->nome().at(index);
}

