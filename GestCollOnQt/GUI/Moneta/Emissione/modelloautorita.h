#ifndef MODELLOAUTORITA_H
#define MODELLOAUTORITA_H

#include <QAbstractListModel>
#include <QStyledItemDelegate>

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

    Qt::ItemFlags flags(const QModelIndex &index) const;
    bool setData(const QModelIndex &index, const QVariant &value, int role);
private:
    ::gestColl::coins::autorita* items;


};

class AutoritaDelegate : public QStyledItemDelegate
{
  Q_OBJECT
public:
    explicit AutoritaDelegate(QObject *parent = 0);


    // QAbstractItemDelegate interface
public:
    QWidget *createEditor(QWidget *parent, const QStyleOptionViewItem &option, const QModelIndex &index) const;
    void setEditorData(QWidget *editor, const QModelIndex &index) const;
    void setModelData(QWidget *editor, QAbstractItemModel *model, const QModelIndex &index) const;
    void updateEditorGeometry(QWidget *editor, const QStyleOptionViewItem &option, const QModelIndex &index) const;
};


#endif // MODELLOAUTORITA_H
