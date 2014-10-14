#include "letteraturamodel.h"
#include <QLineEdit>
#include <bibliotecaxml.h>

namespace {
  namespace LetteraturaRows {
   enum LetteraturaRows {
       LIBRO = 0,
       SIGLA
   };
  }
}


LetteraturaModel::LetteraturaModel(QObject *parent) : QAbstractTableModel(parent)
{
}

LetteraturaModel::~LetteraturaModel()
{
    this->clear();
}

bool LetteraturaModel::appendRow(gestColl::coins::letteratura::libro_type *item)
{
    int row = this->rowCount();
    this->letteratura.append(item);
    QModelIndex index = this->createIndex(row,0,item);
    this->insertRow(row, index);
    return true;
}

QVariant LetteraturaModel::headerData(int section, Qt::Orientation orientation, int role) const {

    if ((orientation == Qt::Horizontal) && (role == Qt::DisplayRole)) {
        switch (section) {
            case LetteraturaRows::LIBRO :
                return "Libro";
            case LetteraturaRows::SIGLA :
                return "Sigla";
        }
    } else {
        return QAbstractTableModel::headerData(section, orientation, role);
    }
    return QVariant();
}

/*
gestColl::coins::datiAcquisto DatiAcquistoModel::getItem()
{
    return *(this->datiAcquisto);
}
*/

Qt::ItemFlags LetteraturaModel::flags(const QModelIndex &index) const
{
    if (!index.isValid())
            return Qt::ItemIsEnabled;

    return QAbstractItemModel::flags(index) | Qt::ItemIsEditable;
}

bool LetteraturaModel::setData(const QModelIndex &index, const QVariant &value, int role)
{
    bool ok = true;
    if ((index.isValid()) && (role == Qt::EditRole)) {
        int row = index.row();
        gestColl::coins::letteratura::libro_type* libro = this->letteratura.at(row);
        switch (index.column()) {
        case LetteraturaRows::LIBRO :
            libro->sigla(value.toString().toStdWString());
            emit dataChanged(index, index);
            break;
        case LetteraturaRows::SIGLA :
        {
            libro->numero(value.toString().toStdWString());
            emit dataChanged(index, index);
        }
            break;
        }
    }
    return ok;
}


QVariant LetteraturaModel::data(const QModelIndex &index, int role) const
{
    if (!index.isValid())
        return QVariant();

    if (index.row() >= this->letteratura.count() || index.row() < 0)
        return QVariant();

    if (this->letteratura.count() == 0)
        return QVariant();

    gestColl::coins::letteratura::libro_type* libro = this->letteratura.at(index.row());

    if (role == Qt::DisplayRole)
    {
        switch (index.column()) {
        case LetteraturaRows::LIBRO :
            return QString::fromStdWString(libro->sigla());
        case LetteraturaRows::SIGLA :
        {
            return QString::fromStdWString(libro->numero());
        }
            break;
        }
    } else if (role == Qt::EditRole) {
        QVariant ret;
        switch (index.column()) {
        case LetteraturaRows::LIBRO :
            ret.setValue(QString::fromStdWString(libro->sigla()));
            break;
        case LetteraturaRows::SIGLA :
            ret.setValue(QString::fromStdWString(libro->numero()));
            break;
        }
        return ret;
    } else if (role == Qt::ToolTipRole) {
        QString sigla = QString::fromStdWString(libro->sigla());
        BibliotecaItem* item = BibliotecaXml::getInstance()->getItem(sigla);
        if (item != NULL) {
            return item->toTooltip();
        } else {
            return "";
        }
    }

    return QVariant();

}

int LetteraturaModel::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return this->letteratura.count();
}

int LetteraturaModel::columnCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return 2;
}


gestColl::coins::letteratura::libro_type* LetteraturaModel::getItem(int index)
{
    return this->letteratura.at(index);
}





QModelIndex LetteraturaModel::getIndex(int index)
{
    QModelIndex ind = this->createIndex(index, 0, 1);
    return ind;
}

void LetteraturaModel::clear()
{
    this->beginResetModel();
    this->letteratura.clear();
    this->endResetModel();

}


LetteraturaDelegate::LetteraturaDelegate(QObject* parent) : QStyledItemDelegate(parent)
{
}


QWidget* LetteraturaDelegate::createEditor(QWidget *parent, const   QStyleOptionViewItem &option, const QModelIndex &index) const {
    Q_UNUSED(option);
    // create widget for use
    switch (index.column()) {
    case LetteraturaRows::LIBRO :
    case LetteraturaRows::SIGLA :
        return new QLineEdit(parent);
    }
    return NULL;
}

void LetteraturaDelegate::setEditorData(QWidget *editor, const QModelIndex &index) const {
    // update model widget
    switch (index.column()) {
    case LetteraturaRows::LIBRO :
    case LetteraturaRows::SIGLA :
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
    case LetteraturaRows::LIBRO :
    case LetteraturaRows::SIGLA :
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
