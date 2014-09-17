#include "letteraturamodel.h"



LetteraturaModel::LetteraturaModel(QObject *parent) : QAbstractTableModel(parent)
{
}

LetteraturaModel::~LetteraturaModel()
{
    this->clear();
}

bool LetteraturaModel::appendRow(gestColl::coins::letteratura::libro_type *item)
{
    int row = this->rowCount();
    this->letteratura.append(item);
    QModelIndex index = this->createIndex(row,0,item);
    this->insertRow(row, index);
    return true;
}

QVariant LetteraturaModel::headerData(int section, Qt::Orientation orientation, int role) const {

    if ((orientation == Qt::Horizontal) && (role == Qt::DisplayRole)) {
        switch (section) {
            case 0:
                return "Libro";
            case 1:
                return "Sigla";
        }
    } else {
        return QAbstractTableModel::headerData(section, orientation, role);
    }
    return QVariant();
}

/*
gestColl::coins::datiAcquisto DatiAcquistoModel::getItem()
{
    return *(this->datiAcquisto);
}
*/

Qt::ItemFlags LetteraturaModel::flags(const QModelIndex &index) const
{
    if (!index.isValid())
            return Qt::ItemIsEnabled;

    return QAbstractItemModel::flags(index) | Qt::ItemIsEditable;
}

bool LetteraturaModel::setData(const QModelIndex &index, const QVariant &value, int role)
{
    bool ok = true;
    if ((index.isValid()) && (role == Qt::EditRole)) {
        int row = index.row();
        gestColl::coins::letteratura::libro_type* libro = this->letteratura.at(row);
        switch (index.column()) {
        case 0:
            libro->sigla(value.toString().toStdWString());
            emit dataChanged(index, index);
            break;
        case 1:
        {
            libro->numero(value.toString().toStdWString());
            emit dataChanged(index, index);
        }
            break;
        }
    }
    return ok;
}


QVariant LetteraturaModel::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    if (index.row() >= this->letteratura.count() || index.row() < 0)
        return QVariant();

    if (this->letteratura.count() == 0)
        return QVariant();

    if (role == Qt::DisplayRole)
    {
        switch (index.column()) {
        case 0:
            return QString::fromStdWString(this->letteratura.at(index.row())->sigla());
        case 1:
        {
            return QString::fromStdWString(this->letteratura.at(index.row())->numero());
        }
            break;
        }
    }
    if (role == Qt::EditRole) {
        QVariant ret;
        switch (index.column()) {
        case 0:
            ret.setValue(QString::fromStdWString(this->letteratura.at(index.row())->sigla()));
            break;
        case 1:
            ret.setValue(QString::fromStdWString(this->letteratura.at(index.row())->numero()));
            break;
        }
        return ret;
    }

    return QVariant();

}

int LetteraturaModel::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return this->letteratura.count();
}

int LetteraturaModel::columnCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return 2;
}


gestColl::coins::letteratura::libro_type* LetteraturaModel::getItem(int index)
{
    return this->letteratura.at(index);
}





QModelIndex LetteraturaModel::getIndex(int index)
{
    QModelIndex ind = this->createIndex(index, 0, 1);
    return ind;
}

void LetteraturaModel::clear()
{
    this->beginResetModel();
    this->letteratura.clear();
    this->endResetModel();

}
