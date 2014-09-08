#ifndef DATIFISICIDELEGATE_H
#define DATIFISICIDELEGATE_H

#include <QStyledItemDelegate>

class DatiFisiciDelegate : public QStyledItemDelegate
{
  Q_OBJECT
public:
    explicit DatiFisiciDelegate(QObject *parent = 0);


    // QAbstractItemDelegate interface
public:

    // QAbstractItemDelegate interface
public:
    QWidget *createEditor(QWidget *parent, const QStyleOptionViewItem &option, const QModelIndex &index) const;
    void setEditorData(QWidget *editor, const QModelIndex &index) const;
    void setModelData(QWidget *editor, QAbstractItemModel *model, const QModelIndex &index) const;
    void updateEditorGeometry(QWidget *editor, const QStyleOptionViewItem &option, const QModelIndex &index) const;
};

#endif // DATIFISICIDELEGATE_H
