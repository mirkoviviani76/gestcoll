#ifndef ZECCHIEREMODEL_H
#define ZECCHIEREMODEL_H

#include <QAbstractTableModel>
#include <QStyledItemDelegate>


#include <scheda.hxx>

class ZecchiereModel : public QAbstractTableModel
{
public:
    explicit ZecchiereModel(QObject *parent = 0);
    virtual ~ZecchiereModel();
    QVariant data(const QModelIndex &index, int role) const;
    int rowCount(const QModelIndex &parent = QModelIndex()) const;
    int columnCount(const QModelIndex &parent = QModelIndex()) const;
    //QModelIndex getIndex(int index);
    void clear();
    bool appendRow(gestColl::coins::moneta::zecchieri_type::zecchiere_type *item);
    bool removeRow(const QModelIndex &index);

    //QList<ScenarioData::Event*> getItems();
    QVariant headerData(int section, Qt::Orientation orientation, int role) const;

     gestColl::coins::moneta::zecchieri_type* getItem();

private:
    gestColl::coins::moneta::zecchieri_type * items;


    // QAbstractItemModel interface
public:
    Qt::ItemFlags flags(const QModelIndex &index) const;

    // QAbstractItemModel interface
public:
    bool setData(const QModelIndex &index, const QVariant &value, int role);
    void setData(gestColl::coins::moneta::zecchieri_type * zecchieri);

};


class ZecchiereDelegate : public QStyledItemDelegate
{
  Q_OBJECT
public:
    explicit ZecchiereDelegate(QObject *parent = 0);

    // QAbstractItemDelegate interface
public:
    QWidget *createEditor(QWidget *parent, const QStyleOptionViewItem &option, const QModelIndex &index) const;
    void setEditorData(QWidget *editor, const QModelIndex &index) const;
    void setModelData(QWidget *editor, QAbstractItemModel *model, const QModelIndex &index) const;
    void updateEditorGeometry(QWidget *editor, const QStyleOptionViewItem &option, const QModelIndex &index) const;
};


#endif // ZECCHIEREMODEL_H
