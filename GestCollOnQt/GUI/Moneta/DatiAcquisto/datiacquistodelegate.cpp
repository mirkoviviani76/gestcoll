#include "datiacquistodelegate.h"

#include <QDoubleSpinBox>
#include <QLineEdit>
#include "commondefs.h"

#include <QDoubleSpinBox>
#include <QLineEdit>
#include <QDateEdit>
#include "commondefs.h"

DatiAcquistoDelegate::DatiAcquistoDelegate(QObject* parent) : QStyledItemDelegate(parent)
{
}


QWidget* DatiAcquistoDelegate::createEditor(QWidget *parent, const   QStyleOptionViewItem &option, const QModelIndex &index) const {
    // create widget for use
    switch (index.column()) {
    case 0:
        return new QLineEdit(parent);
    case 1:
        return new QDateEdit(parent);
    case 2:
        return new QDoubleSpinBox(parent);
    }

    return NULL;
}

void DatiAcquistoDelegate::setEditorData(QWidget *editor, const QModelIndex &index) const {
    // update model widget
    switch (index.column()) {
    case 0:
    {
        QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
        QString text = index.model()->data(index, Qt::EditRole).toString();
        editWidget->setText(text);
    }
        break;
    case 1:
    {
        QDateEdit* editWidget = static_cast<QDateEdit*>(editor);
        QDate data = qvariant_cast<QDate>(index.model()->data(index, Qt::EditRole));
        editWidget->setDate(data);
    }
        break;

    case 2:
    {
        xml::Misura misura = qvariant_cast<xml::Misura>(index.model()->data(index, Qt::EditRole));
        double value = misura.valore;
        QDoubleSpinBox* editWidget = static_cast<QDoubleSpinBox*>(editor);
        editWidget->setSuffix(QString(" %1").arg(misura.unita));
        editWidget->setValue(value);
    }
        break;
    }
}

void DatiAcquistoDelegate::setModelData(QWidget *editor, QAbstractItemModel *model,   const QModelIndex &index) const {
    // store edited model data to model
    switch (index.column()) {
    case 0:
    {
        QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
        model->setData(index, editWidget->text(), Qt::EditRole);
    }
        break;
    case 1:
    {
        QDateEdit* editWidget = static_cast<QDateEdit*>(editor);
        model->setData(index, editWidget->date(), Qt::EditRole);
    }
        break;

    case 2:
    {
        QDoubleSpinBox* editWidget = static_cast<QDoubleSpinBox*>(editor);
        model->setData(index, editWidget->value(), Qt::EditRole);
    }
        break;
    }

}

void DatiAcquistoDelegate::updateEditorGeometry(QWidget *editor, const     QStyleOptionViewItem &option, const QModelIndex &index) const {
    editor->setGeometry(option.rect);
}
