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
    return 2;
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

    xml::Ambito* item = this->getItem(index);

    if (role == Qt::DisplayRole) {
        switch (index.column()) {
        case 0:
            return item->getTitolo();
        case 1:
            return item->getIcona();
        default:
            return "UNKNOWN COLUMN NR";
        }
    }

    return QVariant();

}

int AmbitiModel::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return (int) this->items.count();
}


xml::Ambito* AmbitiModel::getItem(const QModelIndex &index) const
{
    assert (this->items.count() >= index.row());
    return this->items.at(index.row());
}
