#ifndef BIBLIOTECASORTFILTERPROXYMODEL_H
#define BIBLIOTECASORTFILTERPROXYMODEL_H

#include <QSortFilterProxyModel>
#include "bibliotecaitem.h"

class BibliotecaSortFilterProxyModel : public QSortFilterProxyModel
{
public:
    BibliotecaSortFilterProxyModel(QObject *parent = 0);
    virtual ~BibliotecaSortFilterProxyModel();
    QVariant headerData(int section, Qt::Orientation orientation, int role) const;
    /**
     * @brief appendRow add an item
     * @param item
     */
    void appendRow(BibliotecaItem* item);
    /**
     * @brief getItem get the BibliotecaItem related to index
     * @param index
     * @return
     */
    BibliotecaItem* getItem(const QModelIndex& index);

    /**
     * @brief getIndex get the index related to item
     * @param item
     * @return
     */
    QModelIndex getIndex(const BibliotecaItem* item) const;

    bool lessThan(const QModelIndex &left, const QModelIndex &right) const;

    // QSortFilterProxyModel interface
protected:
    bool filterAcceptsRow(int source_row, const QModelIndex &source_parent) const;
};



#endif // BIBLIOTECASORTFILTERPROXYMODEL_H
