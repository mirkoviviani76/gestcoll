#include "datifisicimodel.h"

#include "DatiFisiciModel.h"

#include <stdio.h>
#include <stdlib.h>
#include <QDebug>
#include <QBrush>
#include <QListWidget>
#include <QPainter>
#include "commondefs.h"


DatiFisiciModel::DatiFisiciModel(QObject *parent) : QAbstractTableModel(parent), datiFisici(NULL)
{
}

DatiFisiciModel::~DatiFisiciModel()
{
    this->clear();
}

bool DatiFisiciModel::appendRow(gestColl::coins::datiFisici* item)
{
    int row = this->rowCount();
    this->datiFisici = item;
    QModelIndex index = this->createIndex(row,0,this->datiFisici);
    this->insertRow(row, index);
    return true;
}


QVariant DatiFisiciModel::headerData(int section, Qt::Orientation orientation, int role) const {

    if ((orientation == Qt::Horizontal) && (role == Qt::DisplayRole)) {
        switch (section) {
            case 0:
                return "Peso";
            case 1:
                return "Dim.";
            case 2:
                return "Forma";
            case 3:
                return "Metallo";
        }
    } else {
        return QAbstractTableModel::headerData(section, orientation, role);
    }
    return QVariant();
}

gestColl::coins::datiFisici DatiFisiciModel::getItem()
{
    return *(this->datiFisici);
}

Qt::ItemFlags DatiFisiciModel::flags(const QModelIndex &index) const
{
    if (!index.isValid())
            return Qt::ItemIsEnabled;

    return QAbstractItemModel::flags(index) | Qt::ItemIsEditable;
}

bool DatiFisiciModel::setData(const QModelIndex &index, const QVariant &value, int role)
{
    bool ok = true;
    if ((index.isValid()) && (role == Qt::EditRole)) {
        switch (index.column()) {
        case 0:
            this->datiFisici->peso().valore(value.toDouble(&ok));
            emit dataChanged(index, index);
            break;
        case 1:
            this->datiFisici->diametro().valore(value.toDouble(&ok));
            emit dataChanged(index, index);
            break;
        case 2:
            this->datiFisici->forma(value.toString().toStdWString());
            emit dataChanged(index, index);
            ok = true;
            break;
        case 3:
            this->datiFisici->metallo(value.toString().toStdWString());
            emit dataChanged(index, index);
            ok = true;
            break;
        }
    }
    return ok;
}


QVariant DatiFisiciModel::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    if (index.row() >= 1 || index.row() < 0)
        return QVariant();

    if (this->datiFisici == NULL)
        return QVariant();

    if (role == Qt::DisplayRole)
    {
        switch (index.column()) {
            case 0:
                return QString("%1 %2")
                        .arg(this->datiFisici->peso().valore())
                        .arg(QString::fromStdWString(this->datiFisici->peso().unita()));
            case 1:
                return QString("%1 %2")
                        .arg(this->datiFisici->diametro().valore())
                        .arg(QString::fromStdWString(this->datiFisici->diametro().unita()));
            case 2:
                return QString::fromStdWString(this->datiFisici->forma());
            case 3:
                return QString::fromStdWString(this->datiFisici->metallo());
        }
    }
    if (role == Qt::EditRole) {
        QVariant ret;
        switch (index.column()) {
        case 0:
        {
            xml::Misura peso;
            peso.unita = QString::fromStdWString(this->datiFisici->peso().unita());
            peso.valore = this->datiFisici->peso().valore();
            ret.setValue(peso);
        }
            break;
        case 1:
        {
            xml::Misura diametro;
            diametro.unita = QString::fromStdWString(this->datiFisici->diametro().unita());
            diametro.valore = this->datiFisici->diametro().valore();
            ret.setValue(diametro);
        }
            break;
        case 2:
            ret.setValue(QString::fromStdWString(this->datiFisici->forma()));
            break;
        case 3:
            ret.setValue(QString::fromStdWString(this->datiFisici->metallo()));
            break;
        }
        return ret;

    }

    if (role == Qt::BackgroundRole) {
        /*QColor color = this->items.at(index.row()).getColor();
        if (color.isValid()) {
            //mette trasparente
            color.setAlpha(50);
            return QBrush(color);
        }/*
        else {
        */
        if (index.row() % 2 == 0)
            return QBrush(Qt::white);
        else
            return QBrush(Qt::gray);
    }

    return QVariant();

}

int DatiFisiciModel::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return 1;
}

int DatiFisiciModel::columnCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return 4;
}

QModelIndex DatiFisiciModel::getIndex(int index)
{
    QModelIndex ind = this->createIndex(index, 0, 1);
    return ind;
}

void DatiFisiciModel::clear()
{
    this->beginResetModel();
    this->datiFisici = NULL;
    this->endResetModel();

}

