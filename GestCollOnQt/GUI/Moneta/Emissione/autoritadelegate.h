#ifndef AUTORITADELEGATE_H
#define AUTORITADELEGATE_H

#include <QStyledItemDelegate>

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


#endif // AUTORITADELEGATE_H
