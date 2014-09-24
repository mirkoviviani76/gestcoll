#ifndef LETTERATURAMODEL_H
#define LETTERATURAMODEL_H

#include <QAbstractTableModel>
#include <QStyledItemDelegate>


#include <scheda.hxx>

class LetteraturaModel : public QAbstractTableModel
{
public:
    explicit LetteraturaModel(QObject *parent = 0);
    virtual ~LetteraturaModel();
    QVariant data(const QModelIndex &index, int role) const;
    int rowCount(const QModelIndex &parent = QModelIndex()) const;
    int columnCount(const QModelIndex &parent = QModelIndex()) const;
    QModelIndex getIndex(int index);
    gestColl::coins::letteratura::libro_type *getItem(int index);

    void clear();
    bool appendRow(gestColl::coins::letteratura::libro_type *item);
    //bool removeRow(int row, const QModelIndex &parent = QModelIndex());
    //QList<ScenarioData::Event*> getItems();
    QVariant headerData(int section, Qt::Orientation orientation, int role) const;

    //gestColl::coins::letteratura::libro_type getItem();

private:
    QList< gestColl::coins::letteratura::libro_type* > letteratura;


    // QAbstractItemModel interface
public:
    Qt::ItemFlags flags(const QModelIndex &index) const;

    // QAbstractItemModel interface
public:
    bool setData(const QModelIndex &index, const QVariant &value, int role);
};


class LetteraturaDelegate : public QStyledItemDelegate
{
  Q_OBJECT
public:
    explicit LetteraturaDelegate(QObject *parent = 0);


    // QAbstractItemDelegate interface
public:

    // QAbstractItemDelegate interface
public:
    QWidget *createEditor(QWidget *parent, const QStyleOptionViewItem &option, const QModelIndex &index) const;
    void setEditorData(QWidget *editor, const QModelIndex &index) const;
    void setModelData(QWidget *editor, QAbstractItemModel *model, const QModelIndex &index) const;
    void updateEditorGeometry(QWidget *editor, const QStyleOptionViewItem &option, const QModelIndex &index) const;
};



#endif // LETTERATURAMODEL_H
