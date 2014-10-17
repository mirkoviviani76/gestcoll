#include "vassoiomodel.h"

#include "monetaxml.h"
#include <QColor>
#include <QIcon>
#include <QDebug>
#include <QMetaClassInfo>
#include <QMetaObject>

VassoioModel::VassoioModel(QObject *parent) : QAbstractTableModel(parent)
{
}

VassoioModel::~VassoioModel()
{
    this->items.clear();
    this->rows = 0;
    this->cols = 0;
}

void VassoioModel::setSize(int rows, int cols)
{
    this->rows = rows;
    this->cols = cols;
}


QVariant VassoioModel::data(const QModelIndex &index, int role) const
{

    if (!index.isValid())
        return QVariant();

    MonetaXml* item = this->getItem(index);
    if (item == NULL)
        return QVariant();



    if (role == Qt::DisplayRole) {
        QString valore = QString::fromStdWString(item->getDom()->nominale().valore());
        QString valuta = QString::fromStdWString(item->getDom()->nominale().valuta());
        QString label = QString("%1 - %2 %3")
                .arg(QString::fromStdWString(item->getDom()->paese()))
                .arg(valore)
                .arg(valuta);

        QString completeLabel = QString("%1: %2")
                .arg(item->getId())
                .arg(label);

        return completeLabel;

        return completeLabel;
    }
#if 0
    //FIXME funziona, ma troppo lento
    else if (role == Qt::DecorationRole) {
        return item->getImmagineComposita().scaledToHeight(50, Qt::FastTransformation);
    }
#endif
    else if (role == Qt::ToolTipRole) {
        return item->toTooltip();
    }
    else if (role == Qt::BackgroundColorRole) {
        if (index.row() % 2 == 1)
            return QColor(200,200,200); //gray
    }
    return QVariant();

}

void VassoioModel::setData(int r, int c, MonetaXml* data)
{
    this->items[r][c] = data;
}

MonetaXml* VassoioModel::getItem(const QModelIndex &index) const
{
    return this->items[index.row()][index.column()];
}

QModelIndex VassoioModel::getIndex(int row, int col)
{
    QModelIndex ind = this->createIndex(row, col);
    return ind;
}

/**
  Mostra gli header (indici del vassoio)
  */
QVariant VassoioModel::headerData (int section, Qt::Orientation orientation, int role) const
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



