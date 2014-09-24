#include "zecchieremodel.h"

#include <stdio.h>
#include <stdlib.h>
#include <QDebug>
#include <QBrush>
#include <QListWidget>
#include <QPainter>
#include "commondefs.h"

namespace {
  namespace ZecchiereRows {
   enum ZecchiereRows {
       NOME = 0,
       SEGNO,
       RUOLO
   };
  }
}


ZecchiereModel::ZecchiereModel(QObject *parent) : QAbstractTableModel(parent), items(NULL)
{
}

ZecchiereModel::~ZecchiereModel()
{
    this->clear();
}

bool ZecchiereModel::appendRow(gestColl::coins::zecchieri::zecchiere_type *item)
{
    int row = this->rowCount();
    this->items->zecchiere().push_back(*item);
    QModelIndex index = this->createIndex(row,0,item);
    this->insertRow(row, index);
    return true;
}

bool ZecchiereModel::removeRow(const QModelIndex &index)
{
    int row = 0;
    bool ok = false;
    for ( ::gestColl::coins::zecchieri::zecchiere_iterator it = this->items->zecchiere().begin();
          it != this->items->zecchiere().end(); ++it) {
        if (row == index.row()) {
            this->items->zecchiere().erase(it);
            ok = true;
            break;
        }
        row++;
    }
    return ok;
}


QVariant ZecchiereModel::headerData(int section, Qt::Orientation orientation, int role) const {

    if ((orientation == Qt::Horizontal) && (role == Qt::DisplayRole)) {
        switch (section) {
            case ZecchiereRows::NOME :
                return "Nome";
            case ZecchiereRows::SEGNO :
                return "Segno";
            case ZecchiereRows::RUOLO :
                return "Ruolo";
        }
    } else {
        return QAbstractTableModel::headerData(section, orientation, role);
    }
    return QVariant();
}

 gestColl::coins::moneta::zecchieri_type* ZecchiereModel::getItem()
{
    return items;
}

Qt::ItemFlags ZecchiereModel::flags(const QModelIndex &index) const
{
    if (!index.isValid())
            return Qt::ItemIsEnabled;

    return QAbstractItemModel::flags(index) | Qt::ItemIsEditable;
}

bool ZecchiereModel::setData(const QModelIndex &index, const QVariant &value, int role)
{
    bool ok = false;
    if ((index.isValid()) && (role == Qt::EditRole)) {

        int row = index.row();

        switch (index.column()) {
        case ZecchiereRows::NOME :
            this->items->zecchiere().at(row).nome(value.toString().toStdWString());
            emit dataChanged(index, index);
            ok = true;
            break;
        case ZecchiereRows::SEGNO :
            this->items->zecchiere().at(row).segno(value.toString().toStdWString());
            emit dataChanged(index, index);
            ok = true;
            break;
        case ZecchiereRows::RUOLO :
            this->items->zecchiere().at(row).ruolo(value.toString().toStdWString());
            emit dataChanged(index, index);
            ok = true;
            break;
        }

    }

    return ok;
}

void ZecchiereModel::setData(gestColl::coins::moneta::zecchieri_type *zecchieri)
{
    this->items = zecchieri;
}


QVariant ZecchiereModel::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    if (this->items == NULL)
        return QVariant();

    ::gestColl::coins::moneta::zecchieri_type::zecchiere_type zecchiere = this->items->zecchiere().at(index.row());

    if (role == Qt::DisplayRole)
    {
        switch (index.column()) {
        case ZecchiereRows::NOME :
        {
            if (zecchiere.nome().present())
                return QString::fromStdWString(zecchiere.nome().get());
            else
                return "";
        }
            break;
        case ZecchiereRows::SEGNO :
            if (zecchiere.segno().present())
                return QString::fromStdWString(zecchiere.segno().get());
            else
                return "";
        case ZecchiereRows::RUOLO :
            if (zecchiere.ruolo().present())
                return QString::fromStdWString(zecchiere.ruolo().get());
            else
                return "";
        }
    }
    if (role == Qt::EditRole) {
        QVariant ret;

        switch (index.column()) {
        case ZecchiereRows::NOME :
        {
            QString text = "";
            if (zecchiere.nome().present()) {
                text = QString::fromStdWString(zecchiere.nome().get());
            }
            ret.setValue(text);
            }
            break;
        case ZecchiereRows::SEGNO :
        {
            QString text = "";
            if (zecchiere.segno().present()) {
                text = QString::fromStdWString(zecchiere.segno().get());
            }
            ret.setValue(text);
            }
            break;
        case ZecchiereRows::RUOLO :
        {
            QString text = "";
            if (zecchiere.ruolo().present()) {
                text = QString::fromStdWString(zecchiere.ruolo().get());
            }
            ret.setValue(text);
            }
            break;
        }
        return ret;
    }

    return QVariant();

}

int ZecchiereModel::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    if (this->items == NULL)
        return 0;
    return (int) this->items->zecchiere().size();
}

int ZecchiereModel::columnCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return 3;
}

/*
QModelIndex ZecchiereModel::getIndex(int index)
{
    QModelIndex ind = this->createIndex(index, 0, 1);
    return ind;
}
*/

void ZecchiereModel::clear()
{
    this->beginResetModel();
    this->items = NULL;
    this->endResetModel();

}


#include <QLineEdit>

ZecchiereDelegate::ZecchiereDelegate(QObject* parent) : QStyledItemDelegate(parent)
{
}


QWidget* ZecchiereDelegate::createEditor(QWidget *parent, const   QStyleOptionViewItem &option, const QModelIndex &index) const {
    Q_UNUSED(option);
    // create widget for use
    switch (index.column()) {
    case ZecchiereRows::NOME :
        return new QLineEdit(parent);
    case ZecchiereRows::SEGNO :
        return new QLineEdit(parent);
    case ZecchiereRows::RUOLO :
        return new QLineEdit(parent);
    }

    return NULL;
}

void ZecchiereDelegate::setEditorData(QWidget *editor, const QModelIndex &index) const {
    // update model widget
    switch (index.column()) {
    case ZecchiereRows::NOME :
    {
        QString value = index.model()->data(index, Qt::EditRole).toString();
        QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
        editWidget->setText(value);
    }
        break;
    case ZecchiereRows::SEGNO :
    {
        QString value = index.model()->data(index, Qt::EditRole).toString();
        QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
        editWidget->setText(value);
    }
        break;
    case ZecchiereRows::RUOLO :
    {
        QString value = index.model()->data(index, Qt::EditRole).toString();
        QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
        editWidget->setText(value);
    }
        break;

    }
}

void ZecchiereDelegate::setModelData(QWidget *editor, QAbstractItemModel *model,   const QModelIndex &index) const {
    // store edited model data to model
    switch (index.column()) {
    case ZecchiereRows::NOME :
    {
        QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
        model->setData(index, editWidget->text(), Qt::EditRole);
    }
        break;
    case ZecchiereRows::SEGNO :
    {
        QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
        model->setData(index, editWidget->text(), Qt::EditRole);
    }
        break;
    case ZecchiereRows::RUOLO :
    {
        QLineEdit* editWidget = static_cast<QLineEdit*>(editor);
        model->setData(index, editWidget->text(), Qt::EditRole);
    }
        break;

    }

}

void ZecchiereDelegate::updateEditorGeometry(QWidget *editor, const     QStyleOptionViewItem &option, const QModelIndex &index) const {
    Q_UNUSED(index);
    editor->setGeometry(option.rect);
}
