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
    Q_UNUSED(index);
    Q_UNUSED(option);
    // create widget for use
    return new QLineEdit(parent);
}

void BibliotecaDelegate::setEditorData(QWidget *editor, const QModelIndex &index) const {
    // update model widget
    QString value = index.model()->data(index, Qt::EditRole).toString();
    QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
    editWidget->setText(value);
}

void BibliotecaDelegate::setModelData(QWidget *editor, QAbstractItemModel *model,   const QModelIndex &index) const {
    // store edited model data to model
    QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
    model->setData(index, editWidget->text(), Qt::EditRole);

}

void BibliotecaDelegate::updateEditorGeometry(QWidget *editor, const     QStyleOptionViewItem &option, const QModelIndex &index) const {
    Q_UNUSED(index);
    editor->setGeometry(option.rect);
}
/*
void BibliotecaDelegate::paint(QPainter *painter, const QStyleOptionViewItem &option, const QModelIndex &index) const
{
    QTableView* view = (QTableView*) this->parent();
    BibliotecaSortFilterProxyModel* model = (BibliotecaSortFilterProxyModel*) index.model();

    BibliotecaItem* item = model->getItem(index);

    painter->save();
    painter->setClipRect(option.rect);
    painter->translate(option.rect.topLeft());

    switch (index.column()) {
    case BibliotecaColumns::ID:
    {
        QLabel id(view);
        id.setText(item->getId());
        id.resize(option.rect.size());
        id.render(painter);
    }
        break;
    case BibliotecaColumns::AUTORI:
    {
        MyListView autori(view);
        autori.addItems(item->getAutori());
        autori.resize(autori.sizeHint());
        autori.render(painter);
    }
        break;
    case BibliotecaColumns::TITOLO:
    {
        QLabel titolo(view);
        titolo.setText(item->getTitolo());
        titolo.resize(option.rect.size());
        titolo.render(painter);
    }
        break;
    }

    painter->restore();
}
*/

QSize BibliotecaDelegate::sizeHint(const QStyleOptionViewItem &option, const QModelIndex &index) const
{
    if (index.column() == 1)
        return QSize(100,100);
    else
        return QStyledItemDelegate::sizeHint(option, index);
}
