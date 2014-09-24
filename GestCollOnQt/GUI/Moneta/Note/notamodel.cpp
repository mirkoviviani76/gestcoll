#include "notamodel.h"


ModelloNota::ModelloNota(QObject *parent) :
    items(NULL),
    QAbstractListModel(parent)
{
}

ModelloNota::~ModelloNota()
{
    this->clear();
}


void ModelloNota::clear()
{
    this->beginResetModel();
    this->endResetModel();
    this->items = NULL;
}


bool ModelloNota::fillData(::gestColl::coins::note* _items)
{
    this->items = _items;
    int row = this->rowCount();
    for ( ::gestColl::coins::note::nota_iterator it = this->items->nota().begin();
          it != this->items->nota().end();
          ++it
          ) {
        QModelIndex index = this->createIndex(row,0,&(*it));
        this->insertRow(row, index);
    }
    return true;
}

QVariant ModelloNota::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    if (index.row() >= this->items->nota().size() || index.row() < 0)
        return QVariant();

    ::gestColl::coins::note::nota_type item = this->items->nota().at(index.row());

    if (role == Qt::DisplayRole) {
        return QString::fromStdWString(item);
    }

    return QVariant();

}

int ModelloNota::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    if (this->items == NULL)
        return 0;
    return (int) this->items->nota().size();
}

gestColl::coins::note::nota_type ModelloNota::getItem(const QModelIndex &index)
{
    return this->items->nota().at(index.row());
}
