#include "emissionedelegate.h"

#include <QLineEdit>

EmissioneDelegate::EmissioneDelegate(QObject* parent) : QStyledItemDelegate(parent)
{
}


QWidget* EmissioneDelegate::createEditor(QWidget *parent, const   QStyleOptionViewItem &option, const QModelIndex &index) const {
    Q_UNUSED(option);
    // create widget for use
    switch (index.column()) {
    case 0:
        return new QLineEdit(parent);
    case 1:
        return new QLineEdit(parent);
    case 2:
        return new QLineEdit(parent);
    case 3:
        return new QLineEdit(parent);
    }

    return NULL;
}

void EmissioneDelegate::setEditorData(QWidget *editor, const QModelIndex &index) const {
    // update model widget
    switch (index.column()) {
    case 0:
    case 1:
    case 2:
    case 3:
    {
        QString value = index.model()->data(index, Qt::EditRole).toString();
        QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
        editWidget->setText(value);
    }
        break;

    }
}

void EmissioneDelegate::setModelData(QWidget *editor, QAbstractItemModel *model,   const QModelIndex &index) const {
    // store edited model data to model
    switch (index.column()) {
    case 0:
    case 1:
    case 2:
    case 3:
    {
        QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
        model->setData(index, editWidget->text(), Qt::EditRole);
    }
        break;

    }

}

void EmissioneDelegate::updateEditorGeometry(QWidget *editor, const     QStyleOptionViewItem &option, const QModelIndex &index) const {
    Q_UNUSED(index);
    editor->setGeometry(option.rect);
}
