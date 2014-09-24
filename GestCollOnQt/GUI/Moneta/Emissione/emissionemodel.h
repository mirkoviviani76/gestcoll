#ifndef EMISSIONEMODEL_H
#define EMISSIONEMODEL_H

#include <QAbstractTableModel>
#include <QStyledItemDelegate>

#include <scheda.hxx>
#include "commondefs.h"

class EmissioneModel : public QAbstractTableModel
{
public:
    explicit EmissioneModel(QObject *parent = 0);
    virtual ~EmissioneModel();
    QVariant data(const QModelIndex &index, int role) const;
    int rowCount(const QModelIndex &parent = QModelIndex()) const;
    int columnCount(const QModelIndex &parent = QModelIndex()) const;
    QModelIndex getIndex(int index);
    void clear();
    bool appendRow(xml::Emissione _emissione);
    QVariant headerData(int section, Qt::Orientation orientation, int role) const;

    xml::Emissione getItem();

private:
    xml::Emissione emissione;


    // QAbstractItemModel interface
public:
    Qt::ItemFlags flags(const QModelIndex &index) const;

    // QAbstractItemModel interface
public:
    bool setData(const QModelIndex &index, const QVariant &value, int role);
};



class EmissioneDelegate : public QStyledItemDelegate
{
  Q_OBJECT
public:
    explicit EmissioneDelegate(QObject *parent = 0);


    // QAbstractItemDelegate interface
public:

    // QAbstractItemDelegate interface
public:
    QWidget *createEditor(QWidget *parent, const QStyleOptionViewItem &option, const QModelIndex &index) const;
    void setEditorData(QWidget *editor, const QModelIndex &index) const;
    void setModelData(QWidget *editor, QAbstractItemModel *model, const QModelIndex &index) const;
    void updateEditorGeometry(QWidget *editor, const QStyleOptionViewItem &option, const QModelIndex &index) const;
};



#endif // EMISSIONEMODEL_H
