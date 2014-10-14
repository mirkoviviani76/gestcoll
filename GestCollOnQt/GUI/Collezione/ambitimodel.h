#ifndef AMBITIMODEL_H
#define AMBITIMODEL_H

#include <QAbstractListModel>
#include "commondefs.h"
#include "bibliotecaitem.h"

#include <QList>

class AmbitiModel : public QAbstractListModel
{
public:
    AmbitiModel(QObject *parent = 0);
    virtual ~AmbitiModel();

    QVariant data(const QModelIndex &index, int role) const;
    int rowCount(const QModelIndex &parent = QModelIndex()) const;
    xml::Ambito* getItem(const QModelIndex &index);
    xml::Ambito* getItem(int index);
    void clear();
    bool appendRow(xml::Ambito* _item);

private:
    QList<xml::Ambito*> items;



    // QAbstractItemModel interface
public:
    int columnCount(const QModelIndex &parent) const;
};


#endif // AMBITIMODEL_H
