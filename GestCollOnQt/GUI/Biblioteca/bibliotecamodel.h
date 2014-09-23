#ifndef BIBLIOTECAMODEL_H
#define BIBLIOTECAMODEL_H

#include <QAbstractListModel>
#include "scheda.hxx"
#include "bibliotecaitem.h"

#include <QList>

class ModelloBiblioteca : public QAbstractListModel
{
public:
    ModelloBiblioteca(QObject *parent = 0);
    virtual ~ModelloBiblioteca();

    QVariant data(const QModelIndex &index, int role) const;
    int rowCount(const QModelIndex &parent = QModelIndex()) const;
    BibliotecaItem* getItem(const QModelIndex &index);
    BibliotecaItem* getItem(int index);
    void clear();
    bool appendRow(BibliotecaItem* _item);

    Qt::ItemFlags flags(const QModelIndex &index) const;
#if 0
    bool setData(const QModelIndex &index, const QVariant &value, int role);
#endif
private:
    QList<BibliotecaItem*> items;



    // QAbstractItemModel interface
public:
    int columnCount(const QModelIndex &parent) const;
};



#endif // BIBLIOTECAMODEL_H
