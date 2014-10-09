#ifndef COLLEZIONESORTFILTERPROXYMODEL_H
#define COLLEZIONESORTFILTERPROXYMODEL_H

#include <QSortFilterProxyModel>
#include <QStringList>
#include "monetaxml.h"

class CollezioneSortFilterProxyModel : public QSortFilterProxyModel
{
public:
    CollezioneSortFilterProxyModel(QObject *parent);
    QVariant headerData(int section, Qt::Orientation orientation, int role) const;
    bool lessThan(const QModelIndex &left, const QModelIndex &right) const;
    //QVariant data(const QModelIndex &index, int role) const;
    void appendRow(MonetaXml* item);
    MonetaXml* getItem(const QModelIndex& index);

protected:
    bool filterAcceptsRow(int sourceRow, const QModelIndex &sourceParent) const;
};

#endif // COLLEZIONESORTFILTERPROXYMODEL_H
