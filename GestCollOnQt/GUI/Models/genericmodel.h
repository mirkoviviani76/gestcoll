#ifndef GENERICMODEL_H
#define GENERICMODEL_H

#include <QAbstractListModel>
#include <genericitem.h>

#include <QList>

class GenericModel : public QAbstractTableModel
{
public:
    GenericModel(const int colNum = 1, QObject *parent = 0);
    virtual ~GenericModel();
    QVariant data(const QModelIndex &index, int role) const;
    int rowCount(const QModelIndex &parent = QModelIndex()) const;
    int columnCount(const QModelIndex &parent = QModelIndex()) const;
    GenericItem* getItem(const QModelIndex &index);
    GenericItem* getItem(int index);
    QModelIndex getIndex(int index);
    void clear();
    bool appendRow(GenericItem* item);


private:
    QList<GenericItem*> items;
    const int columns;

};

#endif // GENERICMODEL_H
