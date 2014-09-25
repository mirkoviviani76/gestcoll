#ifndef CONTATTOMODEL_H
#define CONTATTOMODEL_H

#include <QList>
#include <QAbstractTableModel>
#include <QStyledItemDelegate>
#include <QItemDelegate>
#include "contatti.hxx"

class ContattoModel : public QAbstractTableModel
{
public:
    ContattoModel(QObject *parent = 0);
    virtual ~ContattoModel();

    QVariant data(const QModelIndex &index, int role) const;
    int rowCount(const QModelIndex &parent = QModelIndex()) const;
    int columnCount(const QModelIndex &parent = QModelIndex()) const;
    QVariant headerData(int section, Qt::Orientation orientation, int role) const;


    ::gestColl::contatti::contatto getItem(const QModelIndex &index);
    void clear();
    bool fillData(gestColl::contatti::contatti::contatto_sequence *_item);
    bool setData(const QModelIndex &index, const QVariant &value, int role);

    Qt::ItemFlags flags(const QModelIndex &index) const;

private:
    ::gestColl::contatti::contatti::contatto_sequence* items;


};



class EmailDelegate : public QItemDelegate
{
  Q_OBJECT
public:
  EmailDelegate(QObject *parent = 0);
  ~EmailDelegate();
  void paint(QPainter *painter, const QStyleOptionViewItem &option, const QModelIndex &index) const;
  QSize sizeHint(const QStyleOptionViewItem &option, const QModelIndex &index) const;
  QWidget *createEditor(QWidget *parent, const QStyleOptionViewItem &option, const QModelIndex &index) const;
  void setEditorData(QWidget *editor, const QModelIndex &index) const;
  void setModelData(QWidget *editor, QAbstractItemModel *model, const QModelIndex &index) const;
  void updateEditorGeometry(QWidget *editor, const QStyleOptionViewItem &option, const QModelIndex &index) const;
};


#endif // CONTATTOMODEL_H
