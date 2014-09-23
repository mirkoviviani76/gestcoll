#ifndef DOCUMENTIMODEL_H
#define DOCUMENTIMODEL_H

#include <QAbstractListModel>
#include "scheda.hxx"

#include <QList>

class DocumentiModel : public QAbstractListModel
{
public:
    DocumentiModel(QObject *parent = 0);
    virtual ~DocumentiModel();

    QVariant data(const QModelIndex &index, int role) const;
    int rowCount(const QModelIndex &parent = QModelIndex()) const;
    ::gestColl::coins::moneta::itemAddizionali_type::documento_type getItem(const QModelIndex &index);
    ::gestColl::coins::moneta::itemAddizionali_type::documento_type getItem(int index);
    void clear();
    bool appendRow(::gestColl::coins::moneta::itemAddizionali_type::documento_type item);

private:
    QList< ::gestColl::coins::moneta::itemAddizionali_type::documento_type > items;


};


#endif // DOCUMENTIMODEL_H
