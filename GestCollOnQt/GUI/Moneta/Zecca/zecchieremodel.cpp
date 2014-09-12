#include "zecchieremodel.h"

#include <stdio.h>
#include <stdlib.h>
#include <QDebug>
#include <QBrush>
#include <QListWidget>
#include <QPainter>
#include "commondefs.h"


ZecchiereModel::ZecchiereModel(QObject *parent) : QAbstractTableModel(parent), items(NULL)
{
}

ZecchiereModel::~ZecchiereModel()
{
    this->clear();
}

bool ZecchiereModel::appendRow(gestColl::coins::zecchieri::zecchiere_type *item)
{
    int row = this->rowCount();
    this->items->zecchiere().push_back(*item);
    QModelIndex index = this->createIndex(row,0,item);
    this->insertRow(row, index);
    return true;
}

bool ZecchiereModel::removeRow(const QModelIndex &index)
{
    int row = 0;
    bool ok = false;
    for ( ::gestColl::coins::zecchieri::zecchiere_iterator it = this->items->zecchiere().begin();
          it != this->items->zecchiere().end(); ++it) {
        if (row == index.row()) {
            this->items->zecchiere().erase(it);
            ok = true;
            break;
        }
        row++;
    }
    return ok;
}


QVariant ZecchiereModel::headerData(int section, Qt::Orientation orientation, int role) const {

    if ((orientation == Qt::Horizontal) && (role == Qt::DisplayRole)) {
        switch (section) {
            case 0:
                return "Nome";
            case 1:
                return "Segno";
            case 2:
                return "Ruolo";
        }
    } else {
        return QAbstractTableModel::headerData(section, orientation, role);
    }
    return QVariant();
}

 gestColl::coins::moneta::zecchieri_type* ZecchiereModel::getItem()
{
    return items;
}

Qt::ItemFlags ZecchiereModel::flags(const QModelIndex &index) const
{
    if (!index.isValid())
            return Qt::ItemIsEnabled;

    return QAbstractItemModel::flags(index) | Qt::ItemIsEditable;
}

bool ZecchiereModel::setData(const QModelIndex &index, const QVariant &value, int role)
{
    bool ok = false;
    if ((index.isValid()) && (role == Qt::EditRole)) {

        int row = index.row();

        switch (index.column()) {
        case 0:
            this->items->zecchiere().at(row).nome(value.toString().toStdWString());
            emit dataChanged(index, index);
            ok = true;
            break;
        case 1:
            this->items->zecchiere().at(row).segno(value.toString().toStdWString());
            emit dataChanged(index, index);
            ok = true;
            break;
        case 2:
            this->items->zecchiere().at(row).ruolo(value.toString().toStdWString());
            emit dataChanged(index, index);
            ok = true;
            break;
        }

    }

    return ok;
}

void ZecchiereModel::setData(gestColl::coins::moneta::zecchieri_type *zecchieri)
{
    this->items = zecchieri;
}


QVariant ZecchiereModel::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    if (this->items == NULL)
        return QVariant();

    ::gestColl::coins::moneta::zecchieri_type::zecchiere_type zecchiere = this->items->zecchiere().at(index.row());

    if (role == Qt::DisplayRole)
    {
        switch (index.column()) {
        case 0:
        {
            if (zecchiere.nome().present())
                return QString::fromStdWString(zecchiere.nome().get());
            else
                return "";
        }
            break;
        case 1:
            if (zecchiere.segno().present())
                return QString::fromStdWString(zecchiere.segno().get());
            else
                return "";
        case 2:
            if (zecchiere.ruolo().present())
                return QString::fromStdWString(zecchiere.ruolo().get());
            else
                return "";
        }
    }
    if (role == Qt::EditRole) {
        QVariant ret;

        switch (index.column()) {
        case 0:
        {
            QString text = "";
            if (zecchiere.nome().present()) {
                text = QString::fromStdWString(zecchiere.nome().get());
            }
            ret.setValue(text);
            }
            break;
        case 1:
        {
            QString text = "";
            if (zecchiere.segno().present()) {
                text = QString::fromStdWString(zecchiere.segno().get());
            }
            ret.setValue(text);
            }
            break;
        case 2:
        {
            QString text = "";
            if (zecchiere.ruolo().present()) {
                text = QString::fromStdWString(zecchiere.ruolo().get());
            }
            ret.setValue(text);
            }
            break;
        }
        return ret;
    }

    return QVariant();

}

int ZecchiereModel::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    if (this->items == NULL)
        return 0;
    return (int) this->items->zecchiere().size();
}

int ZecchiereModel::columnCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return 3;
}

/*
QModelIndex ZecchiereModel::getIndex(int index)
{
    QModelIndex ind = this->createIndex(index, 0, 1);
    return ind;
}
*/

void ZecchiereModel::clear()
{
    this->beginResetModel();
    this->items = NULL;
    this->endResetModel();

}
