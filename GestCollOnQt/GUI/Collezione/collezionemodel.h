#ifndef COLLEZIONEMODEL_H
#define COLLEZIONEMODEL_H

#include <QAbstractListModel>
#include "monetaxml.h"
#include <QList>

class CollezioneModel : public QAbstractTableModel
{
public:
    CollezioneModel(QObject *parent = 0);
    virtual ~CollezioneModel();
    QVariant data(const QModelIndex &index, int role) const;
    int rowCount(const QModelIndex &parent = QModelIndex()) const;
    int columnCount(const QModelIndex &parent = QModelIndex()) const;
    MonetaXml* getItem(const QModelIndex &index) const;
    MonetaXml* getItem(const int &index) const;
    QModelIndex getIndex(int index);
    void clear();
    bool appendRow(MonetaXml *item);


private:
    QList<MonetaXml*> items;

};


#endif // COLLEZIONEMODEL_H
