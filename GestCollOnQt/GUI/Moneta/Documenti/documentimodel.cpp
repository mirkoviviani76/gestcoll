#include "documentimodel.h"

#include <stdio.h>
#include <stdlib.h>
#include <QDebug>
#include <QBrush>
#include <QFileInfo>
#include "commondata.h"

DocumentiModel::DocumentiModel(QObject *parent) :
    QAbstractListModel(parent)
{
}

DocumentiModel::~DocumentiModel()
{
    this->clear();
}


void DocumentiModel::clear()
{
    this->beginResetModel();
    this->items.clear();
    this->endResetModel();
}


bool DocumentiModel::appendRow(::gestColl::coins::moneta::itemAddizionali_type::documento_type item)
{
    int row = this->rowCount();
    QModelIndex index = this->createIndex(row,0,&item);
    this->insertRow(row, index);
    this->items.insert(row, item);
    return true;
}

QVariant DocumentiModel::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    if (index.row() >= this->items.size() || index.row() < 0)
        return QVariant();

    ::gestColl::coins::moneta::itemAddizionali_type::documento_type item = this->items.at(index.row());
    QString filename = QString("%1/%2")
            .arg(CommonData::getInstance()->getBiblioDir())
            .arg(QString::fromStdWString(item.filename()));
    QFileInfo fi(filename);

    switch (role) {
    case Qt::ToolTipRole :
    {
        QString tooltip = QString::fromStdWString(item.filename());
        return tooltip;
    }
        break;
    case Qt::DisplayRole :
    {
        return QString::fromStdWString(item.descrizione());
    }
        break;
    case Qt::DecorationRole :
    {
        if (fi.exists())
            return QImage(":/icons/BookIcon.ico");
        else
            return QVariant();
    }
        break;
    default:
        return QVariant();
    }

    return QVariant();

}

int DocumentiModel::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return this->items.count();
}


::gestColl::coins::moneta::itemAddizionali_type::documento_type DocumentiModel::getItem(const QModelIndex &index)
{
    return this->items.at(index.row());
}

::gestColl::coins::moneta::itemAddizionali_type::documento_type DocumentiModel::getItem(int index)
{
    return this->items.at(index);
}


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
