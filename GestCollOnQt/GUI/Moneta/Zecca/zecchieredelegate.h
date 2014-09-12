#ifndef ZECCHIEREDELEGATE_H
#define ZECCHIEREDELEGATE_H

#include <QStyledItemDelegate>

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


#endif // ZECCHIEREDELEGATE_H
