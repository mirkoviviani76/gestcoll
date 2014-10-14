#include "ambitimodel.h"


AmbitiModel::AmbitiModel(QObject *parent) :
    QAbstractListModel(parent)
{
}

AmbitiModel::~AmbitiModel()
{
    this->clear();
}


void AmbitiModel::clear()
{
    this->beginResetModel();
    this->items.clear();
    this->endResetModel();
}


int AmbitiModel::columnCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return 3;
}

bool AmbitiModel::appendRow(xml::Ambito *_item) {
    this->items.append(_item);
    int row = this->rowCount();
    QModelIndex index = this->createIndex(row,0,_item);
    this->insertRow(row, index);
    return true;
}

QVariant AmbitiModel::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    if (index.row() >= this->rowCount() || index.row() < 0)
        return QVariant();

    xml::Ambito* item = this->items.at(index.row());

    if (role == Qt::DisplayRole) {
        return item->toString(index.column());
    }

    return QVariant();

}

int AmbitiModel::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return (int) this->items.count();
}


xml::Ambito* AmbitiModel::getItem(const QModelIndex &index)
{
    return this->items.at(index.row());
}

xml::Ambito* AmbitiModel::getItem(int index)
{
    return this->items.at(index);
}
