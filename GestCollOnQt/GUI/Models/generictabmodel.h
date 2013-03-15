#ifndef GENERICTABMODEL_H
#define GENERICTABMODEL_H

#include <QAbstractTableModel>
#include <genericitem.h>
#include <QMap>

class GenericTabModel : public QAbstractTableModel
{
    Q_OBJECT
public:
    GenericTabModel(QObject *parent = 0);
    virtual ~GenericTabModel();
    void setSize(int rows, int cols);
    virtual inline int rowCount(const QModelIndex& m) const {Q_UNUSED(m); return rows;}
    virtual inline int columnCount(const QModelIndex& m) const {Q_UNUSED(m); return cols;}
    QVariant data(const QModelIndex &index, int role = Qt::DisplayRole) const;
    void setData(int r, int c, GenericItem* data);
    GenericItem* getItem(const QModelIndex &index) const;
    QModelIndex getIndex(int row, int col);
    virtual QVariant headerData (int section, Qt::Orientation orientation, int role = Qt::DisplayRole ) const;


private:

    int rows;
    int cols;
    QMap<int, QMap<int, GenericItem*> > items;
};

#endif // GENERICTABMODEL_H
