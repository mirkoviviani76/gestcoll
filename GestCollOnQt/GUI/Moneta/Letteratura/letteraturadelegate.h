#ifndef LETTERATURADELEGATE_H
#define LETTERATURADELEGATE_H

#include <QStyledItemDelegate>

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



#endif // LETTERATURADELEGATE_H
