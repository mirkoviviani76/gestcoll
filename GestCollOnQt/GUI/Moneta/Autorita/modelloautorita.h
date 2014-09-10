#ifndef MODELLOAUTORITA_H
#define MODELLOAUTORITA_H

#include <QAbstractListModel>
#include "scheda.hxx"

#include <QList>

class ModelloAutorita : public QAbstractListModel
{
public:
    ModelloAutorita(QObject *parent = 0);
    virtual ~ModelloAutorita();

    QVariant data(const QModelIndex &index, int role) const;
    int rowCount(const QModelIndex &parent = QModelIndex()) const;
    ::gestColl::coins::autorita::nome_type getItem(const QModelIndex &index);
    ::gestColl::coins::autorita::nome_type getItem(int index);
    void clear();
    bool fillData(gestColl::coins::autorita *_items);

private:
    ::gestColl::coins::autorita* items;


};


#endif // MODELLOAUTORITA_H
