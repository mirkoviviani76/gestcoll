#include "datifisicimodel.h"

#include <stdio.h>
#include <stdlib.h>
#include <QBrush>
#include <QListWidget>
#include <QDoubleSpinBox>
#include <QLineEdit>

#include "commondefs.h"

namespace {
namespace DatiFisiciRows {
enum DatiFisiciRows {
    PESO = 0,
    DIM,
    FORMA,
    METALLO
};
}
}

DatiFisiciModel::DatiFisiciModel(QObject *parent) : QAbstractTableModel(parent), datiFisici(NULL)
{
}

DatiFisiciModel::~DatiFisiciModel()
{
    this->clear();
}

bool DatiFisiciModel::appendRow(gestColl::coins::datiFisici* item)
{
    int row = this->rowCount();
    this->datiFisici = item;
    QModelIndex index = this->createIndex(row,0,this->datiFisici);
    this->insertRow(row, index);
    return true;
}


QVariant DatiFisiciModel::headerData(int section, Qt::Orientation orientation, int role) const {

    if ((orientation == Qt::Horizontal) && (role == Qt::DisplayRole)) {
        switch (section) {
            case DatiFisiciRows::PESO:
                return "Peso";
            case DatiFisiciRows::DIM:
                return "Dim.";
            case DatiFisiciRows::FORMA:
                return "Forma";
            case DatiFisiciRows::METALLO:
                return "Metallo";
        }
    } else {
        return QAbstractTableModel::headerData(section, orientation, role);
    }
    return QVariant();
}

gestColl::coins::datiFisici DatiFisiciModel::getItem()
{
    return *(this->datiFisici);
}

Qt::ItemFlags DatiFisiciModel::flags(const QModelIndex &index) const
{
    if (!index.isValid())
            return Qt::ItemIsEnabled;

    return QAbstractItemModel::flags(index) | Qt::ItemIsEditable;
}

bool DatiFisiciModel::setData(const QModelIndex &index, const QVariant &value, int role)
{
    bool ok = true;
    if ((index.isValid()) && (role == Qt::EditRole)) {
        switch (index.column()) {
        case DatiFisiciRows::PESO:
            this->datiFisici->peso().valore(value.toDouble(&ok));
            emit dataChanged(index, index);
            break;
        case DatiFisiciRows::DIM:
            this->datiFisici->diametro().valore(value.toDouble(&ok));
            emit dataChanged(index, index);
            break;
        case DatiFisiciRows::FORMA:
            this->datiFisici->forma(value.toString().toStdWString());
            emit dataChanged(index, index);
            ok = true;
            break;
        case DatiFisiciRows::METALLO:
            this->datiFisici->metallo(value.toString().toStdWString());
            emit dataChanged(index, index);
            ok = true;
            break;
        }
    }
    return ok;
}


QVariant DatiFisiciModel::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    if (index.row() >= 1 || index.row() < 0)
        return QVariant();

    if (this->datiFisici == NULL)
        return QVariant();

    if (role == Qt::DisplayRole)
    {
        switch (index.column()) {
            case DatiFisiciRows::PESO:
                return QString("%1 %2")
                        .arg(this->datiFisici->peso().valore())
                        .arg(QString::fromStdWString(this->datiFisici->peso().unita()));
            case DatiFisiciRows::DIM:
                return QString("%1 %2")
                        .arg(this->datiFisici->diametro().valore())
                        .arg(QString::fromStdWString(this->datiFisici->diametro().unita()));
            case DatiFisiciRows::FORMA:
                return QString::fromStdWString(this->datiFisici->forma());
            case DatiFisiciRows::METALLO:
                return QString::fromStdWString(this->datiFisici->metallo());
        }
    }
    if (role == Qt::EditRole) {
        QVariant ret;
        switch (index.column()) {
        case DatiFisiciRows::PESO:
        {
            xml::Misura peso;
            peso.unita = QString::fromStdWString(this->datiFisici->peso().unita());
            peso.valore = this->datiFisici->peso().valore();
            ret.setValue(peso);
        }
            break;
        case DatiFisiciRows::DIM:
        {
            xml::Misura diametro;
            diametro.unita = QString::fromStdWString(this->datiFisici->diametro().unita());
            diametro.valore = this->datiFisici->diametro().valore();
            ret.setValue(diametro);
        }
            break;
        case DatiFisiciRows::FORMA:
            ret.setValue(QString::fromStdWString(this->datiFisici->forma()));
            break;
        case DatiFisiciRows::METALLO:
            ret.setValue(QString::fromStdWString(this->datiFisici->metallo()));
            break;
        }
        return ret;

    }

    if (role == Qt::BackgroundRole) {
        /*QColor color = this->items.at(index.row()).getColor();
        if (color.isValid()) {
            //mette trasparente
            color.setAlpha(50);
            return QBrush(color);
        }/*
        else {
        */
        if (index.row() % 2 == 0)
            return QBrush(Qt::white);
        else
            return QBrush(Qt::gray);
    }

    return QVariant();

}

int DatiFisiciModel::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return 1;
}

int DatiFisiciModel::columnCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return 4;
}

QModelIndex DatiFisiciModel::getIndex(int index)
{
    QModelIndex ind = this->createIndex(index, 0, 1);
    return ind;
}

void DatiFisiciModel::clear()
{
    this->beginResetModel();
    this->datiFisici = NULL;
    this->endResetModel();

}





DatiFisiciDelegate::DatiFisiciDelegate(QObject* parent) : QStyledItemDelegate(parent)
{
}


QWidget* DatiFisiciDelegate::createEditor(QWidget *parent, const   QStyleOptionViewItem &option, const QModelIndex &index) const {
    Q_UNUSED(option);
    // create widget for use
    switch (index.column()) {
    case DatiFisiciRows::PESO:
        return new QDoubleSpinBox(parent);
    case DatiFisiciRows::DIM:
        return new QDoubleSpinBox(parent);
    case DatiFisiciRows::FORMA:
        return new QLineEdit(parent);
    case DatiFisiciRows::METALLO:
        return new QLineEdit(parent);
    }

    return NULL;
}

void DatiFisiciDelegate::setEditorData(QWidget *editor, const QModelIndex &index) const {
    // update model widget
    switch (index.column()) {
    case DatiFisiciRows::PESO:
    case DatiFisiciRows::DIM:
    {
        xml::Misura misura = qvariant_cast<xml::Misura>(index.model()->data(index, Qt::EditRole));
        double value = misura.valore;
        QDoubleSpinBox* editWidget = static_cast<QDoubleSpinBox*>(editor);
        editWidget->setSuffix(QString(" %1").arg(misura.unita));
        editWidget->setValue(value);
    }
        break;
    case DatiFisiciRows::FORMA:
    case DatiFisiciRows::METALLO:
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
    case DatiFisiciRows::PESO:
    case DatiFisiciRows::DIM:
    {
        QDoubleSpinBox* editWidget = static_cast<QDoubleSpinBox*>(editor);
        model->setData(index, editWidget->value(), Qt::EditRole);
    }
        break;
    case DatiFisiciRows::FORMA:
    case DatiFisiciRows::METALLO:
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


