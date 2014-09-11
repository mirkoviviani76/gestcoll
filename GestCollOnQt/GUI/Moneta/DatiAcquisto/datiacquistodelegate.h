#ifndef DATIACQUISTODELEGATE_H
#define DATIACQUISTODELEGATE_H

#include <QStyledItemDelegate>

class DatiAcquistoDelegate : public QStyledItemDelegate
{
  Q_OBJECT
public:
    explicit DatiAcquistoDelegate(QObject *parent = 0);


    // QAbstractItemDelegate interface
public:

    // QAbstractItemDelegate interface
public:
    QWidget *createEditor(QWidget *parent, const QStyleOptionViewItem &option, const QModelIndex &index) const;
    void setEditorData(QWidget *editor, const QModelIndex &index) const;
    void setModelData(QWidget *editor, QAbstractItemModel *model, const QModelIndex &index) const;
    void updateEditorGeometry(QWidget *editor, const QStyleOptionViewItem &option, const QModelIndex &index) const;
};


#endif // DATIACQUISTODELEGATE_H
