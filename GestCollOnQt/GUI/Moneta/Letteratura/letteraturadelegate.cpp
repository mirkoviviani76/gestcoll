#include "letteraturadelegate.h"

#include <QLineEdit>

LetteraturaDelegate::LetteraturaDelegate(QObject* parent) : QStyledItemDelegate(parent)
{
}


QWidget* LetteraturaDelegate::createEditor(QWidget *parent, const   QStyleOptionViewItem &option, const QModelIndex &index) const {
    Q_UNUSED(option);
    // create widget for use
    switch (index.column()) {
    case 0:
    case 1:
        return new QLineEdit(parent);
    }
    return NULL;
}

void LetteraturaDelegate::setEditorData(QWidget *editor, const QModelIndex &index) const {
    // update model widget
    switch (index.column()) {
    case 0:
    case 1:
    {
        QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
        QString text = index.model()->data(index, Qt::EditRole).toString();
        editWidget->setText(text);
    }
        break;
    }
}

void LetteraturaDelegate::setModelData(QWidget *editor, QAbstractItemModel *model,   const QModelIndex &index) const {
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

void LetteraturaDelegate::updateEditorGeometry(QWidget *editor, const     QStyleOptionViewItem &option, const QModelIndex &index) const {
    Q_UNUSED(index);
    editor->setGeometry(option.rect);
}
