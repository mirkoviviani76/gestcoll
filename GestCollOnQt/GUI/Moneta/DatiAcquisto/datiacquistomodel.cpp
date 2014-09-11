#include "datiacquistomodel.h"

#include <stdio.h>
#include <stdlib.h>
#include <QDebug>
#include <QBrush>
#include <QListWidget>
#include <QPainter>
#include "commondefs.h"


DatiAcquistoModel::DatiAcquistoModel(QObject *parent) : QAbstractTableModel(parent), datiAcquisto(NULL)
{
}

DatiAcquistoModel::~DatiAcquistoModel()
{
    this->clear();
}

bool DatiAcquistoModel::appendRow(gestColl::coins::datiAcquisto* item)
{
    int row = this->rowCount();
    this->datiAcquisto = item;
    QModelIndex index = this->createIndex(row,0,this->datiAcquisto);
    this->insertRow(row, index);
    return true;
}

QVariant DatiAcquistoModel::headerData(int section, Qt::Orientation orientation, int role) const {

    if ((orientation == Qt::Horizontal) && (role == Qt::DisplayRole)) {
        switch (section) {
            case 0:
                return "Luogo";
            case 1:
                return "Data";
            case 2:
                return "Prezzo";
        }
    } else {
        return QAbstractTableModel::headerData(section, orientation, role);
    }
    return QVariant();
}

gestColl::coins::datiAcquisto DatiAcquistoModel::getItem()
{
    return *(this->datiAcquisto);
}

Qt::ItemFlags DatiAcquistoModel::flags(const QModelIndex &index) const
{
    if (!index.isValid())
            return Qt::ItemIsEnabled;

    return QAbstractItemModel::flags(index) | Qt::ItemIsEditable;
}

bool DatiAcquistoModel::setData(const QModelIndex &index, const QVariant &value, int role)
{
    bool ok = true;
    if ((index.isValid()) && (role == Qt::EditRole)) {
        switch (index.column()) {
        case 0:
            this->datiAcquisto->luogo(value.toString().toStdWString());
            emit dataChanged(index, index);
            break;
        case 1:
        {
            QDate date = value.toDate();
            xml_schema::date xmlData(date.year(), date.month(), date.day());
            this->datiAcquisto->data(xmlData);
            emit dataChanged(index, index);
        }
            break;
        case 2:
            this->datiAcquisto->prezzo().valore(value.toDouble(&ok));
            emit dataChanged(index, index);
            ok = true;
            break;
        }
    }
    return ok;
}


QVariant DatiAcquistoModel::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    if (index.row() >= 1 || index.row() < 0)
        return QVariant();

    if (this->datiAcquisto == NULL)
        return QVariant();

    if (role == Qt::DisplayRole)
    {
        switch (index.column()) {
        case 0:
            return QString::fromStdWString(this->datiAcquisto->luogo());
        case 1:
        {
            QDate dataCorrente;
            dataCorrente.setDate(this->datiAcquisto->data().year(), this->datiAcquisto->data().month(), this->datiAcquisto->data().day());
            return dataCorrente.toString("dd/MM/yyyy");
        }
            break;
        case 2:
            return QString("%1 %2")
                    .arg(this->datiAcquisto->prezzo().valore())
                    .arg(QString::fromStdWString(this->datiAcquisto->prezzo().unita()));
        }
    }
    if (role == Qt::EditRole) {
        QVariant ret;
        switch (index.column()) {
        case 0:
            ret.setValue(QString::fromStdWString(this->datiAcquisto->luogo()));
            break;
        case 1:
        {
            QDate dataCorrente;
            dataCorrente.setDate(this->datiAcquisto->data().year(), this->datiAcquisto->data().month(), this->datiAcquisto->data().day());
            ret.setValue(dataCorrente);
        }
            break;
        case 2:
        {
            xml::Misura prezzo;
            prezzo.unita = QString::fromStdWString(this->datiAcquisto->prezzo().unita());
            prezzo.valore = this->datiAcquisto->prezzo().valore();
            ret.setValue(prezzo);
        }
            break;
        }
        return ret;
    }

    return QVariant();

}

int DatiAcquistoModel::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return 1;
}

int DatiAcquistoModel::columnCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return 4;
}

QModelIndex DatiAcquistoModel::getIndex(int index)
{
    QModelIndex ind = this->createIndex(index, 0, 1);
    return ind;
}

void DatiAcquistoModel::clear()
{
    this->beginResetModel();
    this->datiAcquisto = NULL;
    this->endResetModel();

}
