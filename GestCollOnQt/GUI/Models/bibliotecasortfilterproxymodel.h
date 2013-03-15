#ifndef BIBLIOTECASORTFILTERPROXYMODEL_H
#define BIBLIOTECASORTFILTERPROXYMODEL_H

#include <QSortFilterProxyModel>
#include "bibliotecaitem.h"

class BibliotecaSortFilterProxyModel : public QSortFilterProxyModel
{
public:
    BibliotecaSortFilterProxyModel(QObject *parent = 0);
    QVariant headerData(int section, Qt::Orientation orientation, int role) const;
    void appendRow(BibliotecaItem* item);
    BibliotecaItem* getItem(const QModelIndex& index);
    bool lessThan(const QModelIndex &left, const QModelIndex &right) const;
};



#endif // BIBLIOTECASORTFILTERPROXYMODEL_H
