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


Qt::ItemFlags ModelloAutorita::flags(const QModelIndex &index) const
{
    if (!index.isValid())
            return Qt::ItemIsEnabled;

    return QAbstractItemModel::flags(index) | Qt::ItemIsEditable;
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

    if (role == Qt::DisplayRole) {
        return QString::fromStdWString(item);
    } else if (role == Qt::EditRole) {
        return QString::fromStdWString(item);
    }

    return QVariant();

}

int ModelloAutorita::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return (int) this->items->nome().size();
}


::gestColl::coins::autorita::nome_type ModelloAutorita::getItem(const QModelIndex &index)
{
    return this->items->nome().at(index.row());
}

gestColl::coins::autorita::nome_type ModelloAutorita::getItem(int index)
{
    return this->items->nome().at(index);
}

bool ModelloAutorita::setData(const QModelIndex &index, const QVariant &value, int role)
{
    bool ok = true;
    if ((index.isValid()) && (role == Qt::EditRole)) {
        switch (index.column()) {
        case 0:
            ::gestColl::coins::autorita::nome_type nuovoNome = value.toString().toStdWString();
            this->items->nome().at(index.row()) = nuovoNome;
            emit dataChanged(index, index);
            ok = true;
            break;
        }
    }
    return ok;
}
