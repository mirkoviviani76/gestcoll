#ifndef VASSOIOMODEL_H
#define VASSOIOMODEL_H


#include <QAbstractTableModel>
#include "monetaxml.h"
#include <QMap>

class VassoioModel : public QAbstractTableModel
{
    Q_OBJECT
public:
    VassoioModel(QObject *parent = 0);
    virtual ~VassoioModel();
    void setSize(int rows, int cols);
    virtual inline int rowCount(const QModelIndex& m) const {Q_UNUSED(m); return rows;}
    virtual inline int columnCount(const QModelIndex& m) const {Q_UNUSED(m); return cols;}
    QVariant data(const QModelIndex &index, int role = Qt::DisplayRole) const;
    void setData(int r, int c, MonetaXml* data);
    MonetaXml* getItem(const QModelIndex &index) const;
    QModelIndex getIndex(int row, int col);
    virtual QVariant headerData (int section, Qt::Orientation orientation, int role = Qt::DisplayRole ) const;


private:

    int rows;
    int cols;
    QMap<int, QMap<int, MonetaXml*> > items;
};


#endif // VASSOIOMODEL_H
