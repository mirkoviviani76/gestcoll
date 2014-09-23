#ifndef MODELLOLEGENDA_H
#define MODELLOLEGENDA_H

#include <QAbstractListModel>
#include "scheda.hxx"

#include <QList>

class ModelloLegenda : public QAbstractListModel
{
public:
    ModelloLegenda(QObject *parent = 0);
    virtual ~ModelloLegenda();

    QVariant data(const QModelIndex &index, int role) const;
    int rowCount(const QModelIndex &parent = QModelIndex()) const;
    ::gestColl::coins::legenda getItem(const QModelIndex &index);
    ::gestColl::coins::legenda getItem(int index);
    void clear();
    bool appendRow(gestColl::coins::legenda item);

private:
    QList< ::gestColl::coins::legenda > items;


};


#endif // MODELLOLEGENDA_H
