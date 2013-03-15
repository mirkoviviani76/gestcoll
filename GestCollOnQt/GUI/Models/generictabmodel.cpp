#include "generictabmodel.h"
#include "monetaxml.h"
#include <QColor>
#include <QIcon>
#include <QDebug>
#include <QMetaClassInfo>
#include <QMetaObject>

GenericTabModel::GenericTabModel(QObject *parent) : QAbstractTableModel(parent)
{
}

GenericTabModel::~GenericTabModel()
{
    foreach(int i, this->items.keys())
    {
        foreach (GenericItem* a, this->items[i].values()) {
            if (a != NULL) {
                delete a;
                a = NULL;
            }
        }
        this->items[i].clear();
    }
    this->items.clear();
}

void GenericTabModel::setSize(int rows, int cols)
{
    this->rows = rows;
    this->cols = cols;
}


QVariant GenericTabModel::data(const QModelIndex &index, int role) const
{

    if (!index.isValid())
        return QVariant();

    GenericItem* item = this->getItem(index);

    if (role == Qt::DisplayRole) {
        if (item == NULL)
            return "";
        else
            return item->toString();
    }
    //FIXME funziona, ma troppo lento
#ifdef SHOW_LITTLE_COINS
    else if (role == Qt::DecorationRole) {
        if (item == NULL)
            return QIcon();
        else
            return item->toImg().scaledToHeight(50, Qt::SmoothTransformation);
    }
#endif
    else if (role == Qt::ToolTipRole) {
        if (item == NULL)
            return "";
        else
            return item->toTooltip();
    }
    else if (role == Qt::BackgroundColorRole) {
        if (index.row() % 2 == 1)
            return QColor(200,200,200); //gray
    }
    return QVariant();

}

void GenericTabModel::setData(int r, int c, GenericItem* data)
{
    this->items[r][c] = data;
}

GenericItem* GenericTabModel::getItem(const QModelIndex &index) const
{
    return this->items[index.row()][index.column()];
}

QModelIndex GenericTabModel::getIndex(int row, int col)
{
    QModelIndex ind = this->createIndex(row, col);
    return ind;
}

/**
  Mostra gli header (indici del vassoio)
  */
QVariant GenericTabModel::headerData (int section, Qt::Orientation orientation, int role) const
{
    if (role == Qt::DisplayRole)
    {
        switch (orientation)
        {
        case Qt::Vertical:
            return QString("%1").arg(this->rows - section);
            break;
        case Qt::Horizontal:
            return QString("%1").arg(section+1);
            break;
        }
    }
    else
    {
        return QVariant();
    }
    return QVariant();
}



