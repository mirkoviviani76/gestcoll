#include "bibliotecadelegate.h"

#include <QDoubleSpinBox>
#include <QLineEdit>
#include "commondefs.h"
#include "bibliotecasortfilterproxymodel.h"
#include <QLabel>
#include <QTableView>
#include <QPainter>


MyListView::MyListView(QWidget* parent)
    : QListWidget(parent)
{
}

QSize MyListView::sizeHint() const
{
    int width = contentsRect().width();
    int height = contentsRect().height();
    return QSize(width, height);
}


BibliotecaDelegate::BibliotecaDelegate(QObject* parent) : QStyledItemDelegate(parent)
{
}


QWidget* BibliotecaDelegate::createEditor(QWidget *parent, const   QStyleOptionViewItem &option, const QModelIndex &index) const {
    Q_UNUSED(option);
    // create widget for use
    switch (index.column()) {
    case 0:
        return new QLineEdit(parent);
    case 1:
        return new QLineEdit(parent);
    case 2:
        return new QLineEdit(parent);
    }

    return NULL;
}

void BibliotecaDelegate::setEditorData(QWidget *editor, const QModelIndex &index) const {
    // update model widget
    switch (index.column()) {
    case 0:
    case 1:
    case 2:
    {
        QString value = index.model()->data(index, Qt::EditRole).toString();
        QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
        editWidget->setText(value);
    }
        break;

    }
}

void BibliotecaDelegate::setModelData(QWidget *editor, QAbstractItemModel *model,   const QModelIndex &index) const {
    // store edited model data to model
    switch (index.column()) {
    case 0:
    case 1:
    case 2:
    {
        QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
        model->setData(index, editWidget->text(), Qt::EditRole);
    }
        break;

    }

}

void BibliotecaDelegate::updateEditorGeometry(QWidget *editor, const     QStyleOptionViewItem &option, const QModelIndex &index) const {
    Q_UNUSED(index);
    editor->setGeometry(option.rect);
}

void BibliotecaDelegate::paint(QPainter *painter, const QStyleOptionViewItem &option, const QModelIndex &index) const
{
    QTableView* view = (QTableView*) this->parent();
    BibliotecaSortFilterProxyModel* model = (BibliotecaSortFilterProxyModel*) index.model();

    BibliotecaItem* item = model->getItem(index);

    painter->save();
    painter->setClipRect(option.rect);
    painter->translate(option.rect.topLeft());

    switch (index.column()) {
    case 0:
    {
        QLabel id(view);
        id.setText(item->toString(0));
        id.resize(option.rect.size());
        id.render(painter);
    }
        break;
    case 1:
    {
        MyListView autori(view);
        autori.addItems(item->getAutori());
        autori.resize(autori.sizeHint());
        autori.render(painter);
    }
        break;
    case 2:
    {
        QLabel titolo(view);
        titolo.setText(item->toString(2));
        titolo.resize(option.rect.size());
        titolo.render(painter);
    }
        break;
    }

    painter->restore();
}

QSize BibliotecaDelegate::sizeHint(const QStyleOptionViewItem &option, const QModelIndex &index) const
{
    switch (index.column()) {
    case 0:
        return QStyledItemDelegate::sizeHint(option, index);
    case 1:
        return QSize(100,100);
    case 2:
        return QStyledItemDelegate::sizeHint(option, index);
    }

    return QStyledItemDelegate::sizeHint(option, index);
}
