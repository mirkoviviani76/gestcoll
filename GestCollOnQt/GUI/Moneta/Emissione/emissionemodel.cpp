#include "emissionemodel.h"


#include <stdio.h>
#include <stdlib.h>
#include <QDebug>
#include <QBrush>
#include <QListWidget>
#include <QPainter>
#include "commondefs.h"


EmissioneModel::EmissioneModel(QObject *parent) : QAbstractTableModel(parent)
{
    this->emissione.anno = NULL;
    this->emissione.nominale = NULL;
    this->emissione.paese = NULL;
}

EmissioneModel::~EmissioneModel()
{
    this->clear();
}

bool EmissioneModel::appendRow(xml::Emissione _emissione)
{
    int row = this->rowCount();
    this->emissione.nominale = _emissione.nominale;
    this->emissione.paese = _emissione.paese;
    this->emissione.anno = _emissione.anno;

    QModelIndex index = this->createIndex(row,0,&(this->emissione));
    this->insertRow(row, index);
    return true;
}


QVariant EmissioneModel::headerData(int section, Qt::Orientation orientation, int role) const {

    if ((orientation == Qt::Horizontal) && (role == Qt::DisplayRole)) {
        switch (section) {
            case 0:
                return "Paese";
            case 1:
                return "Valore";
            case 2:
                return "Valuta";
            case 3:
                return "Anno";
        }
    } else {
        return QAbstractTableModel::headerData(section, orientation, role);
    }
    return QVariant();
}

xml::Emissione EmissioneModel::getItem()
{
    return this->emissione;
}

Qt::ItemFlags EmissioneModel::flags(const QModelIndex &index) const
{
    if (!index.isValid())
            return Qt::ItemIsEnabled;

    return QAbstractItemModel::flags(index) | Qt::ItemIsEditable;
}

bool EmissioneModel::setData(const QModelIndex &index, const QVariant &value, int role)
{
    bool ok = true;
    if ((index.isValid()) && (role == Qt::EditRole)) {
        switch (index.column()) {
        case 0:
            *(this->emissione.paese) = (value.toString().toStdWString());
            emit dataChanged(index, index);
            break;
        case 1:
            this->emissione.nominale->valore(value.toString().toStdWString());
            emit dataChanged(index, index);
            break;
        case 2:
            this->emissione.nominale->valuta(value.toString().toStdWString());
            emit dataChanged(index, index);
            break;
        case 3:
            *(this->emissione.anno) = (value.toString().toStdWString());
            emit dataChanged(index, index);
            break;
        }
    }
    return ok;
}


QVariant EmissioneModel::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    if (index.row() >= 1 || index.row() < 0)
        return QVariant();

    if ((this->emissione.anno == NULL) || (this->emissione.nominale == NULL) || (this->emissione.paese == NULL))
        return QVariant();

    if (role == Qt::DisplayRole)
    {
        switch (index.column()) {
        case 0:
            return QString::fromStdWString(*(this->emissione.paese));
        case 1:
            return QString::fromStdWString(this->emissione.nominale->valore());
        case 2:
            return QString::fromStdWString(this->emissione.nominale->valuta());
        case 3:
            return QString::fromStdWString(*(this->emissione.anno));
        }
    }
    if (role == Qt::EditRole) {
        QVariant ret;
        switch (index.column()) {
        case 0:
        {
            ret.setValue(QString::fromStdWString(*(this->emissione.paese)));
        }
            break;
        case 1:
        {
            ret.setValue(QString::fromStdWString(this->emissione.nominale->valore()));
        }
            break;
        case 2:
            ret.setValue(QString::fromStdWString(this->emissione.nominale->valuta()));
            break;
        case 3:
            ret.setValue(QString::fromStdWString(*(this->emissione.anno)));
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

int EmissioneModel::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return 1;
}

int EmissioneModel::columnCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return 4;
}

QModelIndex EmissioneModel::getIndex(int index)
{
    QModelIndex ind = this->createIndex(index, 0, 1);
    return ind;
}

void EmissioneModel::clear()
{
    this->beginResetModel();
    this->emissione.nominale = NULL;
    this->emissione.paese = NULL;
    this->emissione.anno = NULL;

    this->endResetModel();

}
