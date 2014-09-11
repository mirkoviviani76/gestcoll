#include "autoritadelegate.h"
#include "scheda.hxx"

#include <QLineEdit>

AutoritaDelegate::AutoritaDelegate(QObject* parent) : QStyledItemDelegate(parent)
{
}


QWidget* AutoritaDelegate::createEditor(QWidget *parent, const   QStyleOptionViewItem &option, const QModelIndex &index) const {
    // create widget for use
    switch (index.column()) {
    case 0:
        return new QLineEdit(parent);
    }

    return NULL;
}

void AutoritaDelegate::setEditorData(QWidget *editor, const QModelIndex &index) const {
    // update model widget
    switch (index.column()) {
    case 0:
    {
        QString value = index.model()->data(index, Qt::EditRole).toString();
        QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
        editWidget->setText(value);
    }
        break;

    }
}

void AutoritaDelegate::setModelData(QWidget *editor, QAbstractItemModel *model,   const QModelIndex &index) const {
    // store edited model data to model
    switch (index.column()) {
    case 0:
    {
        QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
        model->setData(index, editWidget->text(), Qt::EditRole);
    }
        break;

    }

}

void AutoritaDelegate::updateEditorGeometry(QWidget *editor, const     QStyleOptionViewItem &option, const QModelIndex &index) const {
    editor->setGeometry(option.rect);
}
