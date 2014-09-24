#include "contattomodel.h"
#include <QUrl>

ContattoModel::ContattoModel(QObject *parent) :
    QAbstractTableModel(parent), items(NULL)
{
}

ContattoModel::~ContattoModel()
{
    this->clear();
}


void ContattoModel::clear()
{
    this->beginResetModel();
    this->items = NULL;
    this->endResetModel();
}


Qt::ItemFlags ContattoModel::flags(const QModelIndex &index) const
{
    if (!index.isValid())
            return Qt::ItemIsEnabled;

    return QAbstractItemModel::flags(index) | Qt::ItemIsEditable;
}

int ContattoModel::columnCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return 3;
}

bool ContattoModel::fillData(::gestColl::contatti::contatti::contatto_sequence* _item) {
    this->items = _item;
    int row = 0;
    for ( ::gestColl::contatti::contatti::contatto_iterator it = this->items->begin();
          it != this->items->end(); ++it) {
        QModelIndex index = this->createIndex(row,0,&(*it));
        this->insertRow(row, index);
        row++;
    }
    return true;
}

QVariant ContattoModel::data(const QModelIndex &index, int role) const
{

    if (!index.isValid())
        return QVariant();

    if (index.row() >= this->rowCount() || index.row() < 0)
        return QVariant();

    const gestColl::contatti::contatto item = this->items->at(index.row());

    if (role == Qt::DisplayRole) {
        switch (index.column()) {
        case 0:
            return QString::fromStdWString(item.nome());
        case 1:
            return QString::fromStdWString(item.email());
        case 2:
            return QString::fromStdWString(item.note());
        }

    } else if (role == Qt::EditRole) {
        switch (index.column()) {
        case 0:
            return QString::fromStdWString(item.nome());
        case 1:
            return QString::fromStdWString(item.email());
        case 2:
            return QString::fromStdWString(item.note());
        }
    }

    return QVariant();

}

int ContattoModel::rowCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    if (this->items == NULL)
        return 0;
    return (int) this->items->size();
}


QVariant ContattoModel::headerData(int section, Qt::Orientation orientation, int role) const
{
    if ((orientation == Qt::Horizontal) && (role == Qt::DisplayRole)) {
        switch (section) {
            case 0:
                return "Nome";
            case 1:
                return "Email";
            case 2:
                return "Note";
        }
    } else {
        return QAbstractTableModel::headerData(section, orientation, role);
    }
    return QVariant();
}



gestColl::contatti::contatto ContattoModel::getItem(const QModelIndex &index)
{
    return this->items->at(index.row());
}


bool ContattoModel::setData(const QModelIndex &index, const QVariant &value, int role)
{
    bool ok = true;
    if ((index.isValid()) && (role == Qt::EditRole)) {
        switch (index.column()) {
        case 0: {
            ::gestColl::contatti::contatto::nome_type nuovoNome = value.toString().toStdWString();
            this->items->at(index.row()).nome(nuovoNome);
            emit dataChanged(index, index);
        }
        break;
        case 1: {
            ::gestColl::contatti::contatto::email_type nuovoNome = value.toString().toStdWString();
            this->items->at(index.row()).email(nuovoNome);
            emit dataChanged(index, index);
        }
        break;
        case 2: {
            ::gestColl::contatti::contatto::note_type nuovoNome = value.toString().toStdWString();
            this->items->at(index.row()).note(nuovoNome);
            emit dataChanged(index, index);
        }
        break;
    }
    }
    return ok;
}

