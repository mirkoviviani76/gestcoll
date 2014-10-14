#ifndef BIBLIOTECADELEGATE_H
#define BIBLIOTECADELEGATE_H

#include <QStyledItemDelegate>
#include <QListWidget>
#include <QHBoxLayout>
#include <QLabel>

class MyListView : public QListWidget {
    Q_OBJECT
public:
    MyListView(QWidget* parent = 0);

public:
    QSize sizeHint() const;
};

class BibliotecaDelegate : public QStyledItemDelegate
{
  Q_OBJECT
public:
    explicit BibliotecaDelegate(QObject *parent = 0);


    // QAbstractItemDelegate interface
public:

    // QAbstractItemDelegate interface
public:
    QWidget *createEditor(QWidget *parent, const QStyleOptionViewItem &option, const QModelIndex &index) const;
    void setEditorData(QWidget *editor, const QModelIndex &index) const;
    void setModelData(QWidget *editor, QAbstractItemModel *model, const QModelIndex &index) const;
    void updateEditorGeometry(QWidget *editor, const QStyleOptionViewItem &option, const QModelIndex &index) const;

    // QAbstractItemDelegate interface
public:
    //void paint(QPainter *painter, const QStyleOptionViewItem &option, const QModelIndex &index) const;



    // QAbstractItemDelegate interface
public:
    QSize sizeHint(const QStyleOptionViewItem &option, const QModelIndex &index) const;


};


#endif // BIBLIOTECADELEGATE_H
