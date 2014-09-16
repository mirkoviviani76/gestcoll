#ifndef EMISSIONEMODEL_H
#define EMISSIONEMODEL_H

#include <QAbstractTableModel>

#include <scheda.hxx>
#include "commondefs.h"

class EmissioneModel : public QAbstractTableModel
{
public:
    explicit EmissioneModel(QObject *parent = 0);
    virtual ~EmissioneModel();
    QVariant data(const QModelIndex &index, int role) const;
    int rowCount(const QModelIndex &parent = QModelIndex()) const;
    int columnCount(const QModelIndex &parent = QModelIndex()) const;
    QModelIndex getIndex(int index);
    void clear();
    bool appendRow(xml::Emissione _emissione);
    QVariant headerData(int section, Qt::Orientation orientation, int role) const;

    xml::Emissione getItem();

private:
    xml::Emissione emissione;


    // QAbstractItemModel interface
public:
    Qt::ItemFlags flags(const QModelIndex &index) const;

    // QAbstractItemModel interface
public:
    bool setData(const QModelIndex &index, const QVariant &value, int role);
};



#endif // EMISSIONEMODEL_H
