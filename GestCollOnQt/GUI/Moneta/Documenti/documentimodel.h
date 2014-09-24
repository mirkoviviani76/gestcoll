#ifndef DOCUMENTIMODEL_H
#define DOCUMENTIMODEL_H

#include <QAbstractListModel>
#include <QStyledItemDelegate>


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


class DocumentiDelegate : public QStyledItemDelegate
{
  Q_OBJECT
public:
    explicit DocumentiDelegate(QObject *parent = 0);


    // QAbstractItemDelegate interface
public:
    //QWidget *createEditor(QWidget *parent, const QStyleOptionViewItem &option, const QModelIndex &index) const;
    //void setEditorData(QWidget *editor, const QModelIndex &index) const;
    //void setModelData(QWidget *editor, QAbstractItemModel *model, const QModelIndex &index) const;
    //void updateEditorGeometry(QWidget *editor, const QStyleOptionViewItem &option, const QModelIndex &index) const;
};


#endif // DOCUMENTIMODEL_H
