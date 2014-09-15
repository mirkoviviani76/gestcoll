#ifndef NOTAMODEL_H
#define NOTAMODEL_H

#include <QAbstractListModel>
#include "scheda.hxx"

#include <QList>

class ModelloNota : public QAbstractListModel
{
public:
    ModelloNota(QObject *parent = 0);
    virtual ~ModelloNota();

    QVariant data(const QModelIndex &index, int role) const;
    int rowCount(const QModelIndex &parent = QModelIndex()) const;
    ::gestColl::coins::note::nota_type getItem(const QModelIndex &index);
    ::gestColl::coins::note::nota_type getItem(int index);
    void clear();
    bool fillData(::gestColl::coins::note *_items);

private:
    ::gestColl::coins::note* items;


};



#endif // NOTAMODEL_H
