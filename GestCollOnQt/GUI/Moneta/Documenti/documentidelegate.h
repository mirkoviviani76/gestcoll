#ifndef DOCUMENTIDELEGATE_H
#define DOCUMENTIDELEGATE_H

#include <QStyledItemDelegate>

class DocumentiDelegate : public QStyledItemDelegate
{
  Q_OBJECT
public:
    explicit DocumentiDelegate(QObject *parent = 0);


    // QAbstractItemDelegate interface
public:
    //QWidget *createEditor(QWidget *parent, const QStyleOptionViewItem &option, const QModelIndex &index) const;
    //void setEditorData(QWidget *editor, const QModelIndex &index) const;
    //void setModelData(QWidget *editor, QAbstractItemModel *model, const QModelIndex &index) const;
    //void updateEditorGeometry(QWidget *editor, const QStyleOptionViewItem &option, const QModelIndex &index) const;
};



#endif // DOCUMENTIDELEGATE_H
