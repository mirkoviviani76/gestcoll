#ifndef EMISSIONEDELEGATE_H
#define EMISSIONEDELEGATE_H

#include <QStyledItemDelegate>

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

#endif // EMISSIONEDELEGATE_H
