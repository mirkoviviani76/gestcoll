#include "collezionemodel.h"

#include <stdio.h>
#include <stdlib.h>
#include <QDebug>
#include <QBrush>

CollezioneModel::CollezioneModel(QObject *parent) :
    QAbstractTableModel(parent)
{
}

CollezioneModel::~CollezioneModel()
{
    this->clear();
}


void CollezioneModel::clear()
{
    this->beginResetModel();
    this->items.clear();
    this->endResetModel();
}


bool CollezioneModel::appendRow(MonetaXml* item)
{
    int row = this->rowCount();
    QModelIndex index = this->createIndex(row,0,item);
    this->insertRow(row, index);
    this->items.insert(row, item);
    return true;
}

QVariant CollezioneModel::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    if (index.row() >= this->items.size() || index.row() < 0)
        return QVariant();

    MonetaXml* item = this->getItem(index);

    if (role == Qt::ToolTipRole)
    {
        return item->toTooltip();
    }
    if (role == Qt::DecorationRole && index.column() == 0)
    {
        return *(item->getIcona());
    }
    if (role == Qt::DisplayRole)
    {
        switch (index.column()) {
        case 0:
        {
            return item->getId();
        }
            break;
        case 1:
        {
            return QString::fromStdWString(item->getDom()->paese());
        }
            break;
        case 2:
        {
            return QString("%1 %2").arg(QString::fromStdWString(item->getDom()->nominale().valore()))
                    .arg(QString::fromStdWString(item->getDom()->nominale().valuta()));
        }
            break;
        case 3:
        {
            return QString::fromStdWString(item->getDom()->anno());
        }
            break;
        case 4:
        {
            QStringList myambiti;
            foreach (xml::Ambito* a, item->getAmbiti()) {
                myambiti << a->getTitolo();
            }
            return myambiti.join(" | ");
        }
            break;
        }
    }
    if (role == Qt::BackgroundRole) {
        QColor color = item->getColor();
        if (color.isValid()) {
            //mette trasparente
            color.setAlpha(50);
            return QBrush(color);
        }
    }

    return QVariant();

}

int CollezioneModel::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return this->items.count();
}

int CollezioneModel::columnCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return 5;
}


MonetaXml* CollezioneModel::getItem(const QModelIndex &index) const
{
    return this->items.at(index.row());
}

MonetaXml* CollezioneModel::getItem(const int &index) const
{
    return this->items.at(index);
}


QModelIndex CollezioneModel::getIndex(int index)
{
    QModelIndex ind = this->createIndex(index, 0, this->items.at(index));
    return ind;
}


