#include "datifisicidelegate.h"
#include <QDoubleSpinBox>
#include <QLineEdit>
#include "commondefs.h"

DatiFisiciDelegate::DatiFisiciDelegate(QObject* parent) : QStyledItemDelegate(parent)
{
}


QWidget* DatiFisiciDelegate::createEditor(QWidget *parent, const   QStyleOptionViewItem &option, const QModelIndex &index) const {
    Q_UNUSED(option);
    // create widget for use
    switch (index.column()) {
    case 0:
        return new QDoubleSpinBox(parent);
    case 1:
        return new QDoubleSpinBox(parent);
    case 2:
        return new QLineEdit(parent);
    case 3:
        return new QLineEdit(parent);
    }

    return NULL;
}

void DatiFisiciDelegate::setEditorData(QWidget *editor, const QModelIndex &index) const {
    // update model widget
    switch (index.column()) {
    case 0:
    case 1:
    {
        xml::Misura misura = qvariant_cast<xml::Misura>(index.model()->data(index, Qt::EditRole));
        double value = misura.valore;
        QDoubleSpinBox* editWidget = static_cast<QDoubleSpinBox*>(editor);
        editWidget->setSuffix(QString(" %1").arg(misura.unita));
        editWidget->setValue(value);
    }
        break;
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

void DatiFisiciDelegate::setModelData(QWidget *editor, QAbstractItemModel *model,   const QModelIndex &index) const {
    // store edited model data to model
    switch (index.column()) {
    case 0:
    case 1:
    {
        QDoubleSpinBox* editWidget = static_cast<QDoubleSpinBox*>(editor);
        model->setData(index, editWidget->value(), Qt::EditRole);
    }
        break;
    case 2:
    case 3:
    {
        QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
        model->setData(index, editWidget->text(), Qt::EditRole);
    }
        break;

    }

}

void DatiFisiciDelegate::updateEditorGeometry(QWidget *editor, const     QStyleOptionViewItem &option, const QModelIndex &index) const {
    Q_UNUSED(index);
    editor->setGeometry(option.rect);
}

