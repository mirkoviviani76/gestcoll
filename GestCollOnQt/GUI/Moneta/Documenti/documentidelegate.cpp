#include "documentidelegate.h"

#include "scheda.hxx"

#include <QLineEdit>

DocumentiDelegate::DocumentiDelegate(QObject* parent) : QStyledItemDelegate(parent)
{
}

#if 0
QWidget* DocumentiDelegate::createEditor(QWidget *parent, const   QStyleOptionViewItem &option, const QModelIndex &index) const {
    Q_UNUSED(option);
    // create widget for use
    switch (index.column()) {
    case 0:
    case 1:
        return new QLineEdit(parent);
    }

    return NULL;
}
#endif

#if 0
void DocumentiDelegate::setEditorData(QWidget *editor, const QModelIndex &index) const {
    // update model widget
    switch (index.column()) {
    case 0:
    case 1:
    {
        QString value = index.model()->data(index, Qt::EditRole).toString();
        QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
        editWidget->setText(value);
    }
        break;

    }
}
#endif

#if 0
void DocumentiDelegate::setModelData(QWidget *editor, QAbstractItemModel *model,   const QModelIndex &index) const {
    // store edited model data to model
    switch (index.column()) {
    case 0:
    case 1:
    {
        QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
        model->setData(index, editWidget->text(), Qt::EditRole);
    }
        break;

    }
}
#endif

#if 0
void DocumentiDelegate::updateEditorGeometry(QWidget *editor, const     QStyleOptionViewItem &option, const QModelIndex &index) const {
    Q_UNUSED(index);
    editor->setGeometry(option.rect);
}
#endif
