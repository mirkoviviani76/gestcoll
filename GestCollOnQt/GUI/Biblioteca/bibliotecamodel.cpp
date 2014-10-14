#include "bibliotecamodel.h"



namespace {
   namespace BibliotecaColumns {
     enum BibliotecaColumns {
         ID = 0,
         AUTORI,
         TITOLO
     };
   }
}

ModelloBiblioteca::ModelloBiblioteca(QObject *parent) :
    QAbstractListModel(parent)
{
}

ModelloBiblioteca::~ModelloBiblioteca()
{
    this->clear();
}


void ModelloBiblioteca::clear()
{
    this->beginResetModel();
    this->endResetModel();
}


Qt::ItemFlags ModelloBiblioteca::flags(const QModelIndex &index) const
{
    if (!index.isValid())
            return Qt::ItemIsEnabled;

    //return QAbstractItemModel::flags(index) | Qt::ItemIsEditable;
    return QAbstractItemModel::flags(index);
}

int ModelloBiblioteca::columnCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return 3;
}

bool ModelloBiblioteca::appendRow(BibliotecaItem *_item) {
    this->items.append(_item);
    int row = this->rowCount();
    QModelIndex index = this->createIndex(row,0,_item);
    this->insertRow(row, index);
    return true;
}

QVariant ModelloBiblioteca::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    if (index.row() >= this->rowCount() || index.row() < 0)
        return QVariant();

    BibliotecaItem* item = this->items.at(index.row());

    if (role == Qt::DisplayRole) {
        switch (index.column()) {
        case BibliotecaColumns::ID:
            return item->getId();
        case BibliotecaColumns::AUTORI:
            return item->getAutori().join("; ");
        case BibliotecaColumns::TITOLO:
            return item->getTitolo();
        }

    }

    return QVariant();

}

int ModelloBiblioteca::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return (int) this->items.count();
}


BibliotecaItem* ModelloBiblioteca::getItem(const QModelIndex &index)
{
    return this->items.at(index.row());
}

BibliotecaItem* ModelloBiblioteca::getItem(int index)
{
    return this->items.at(index);
}

#if 0
bool ModelloBiblioteca::setData(const QModelIndex &index, const QVariant &value, int role)
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

#endif
