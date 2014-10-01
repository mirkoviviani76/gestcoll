#include "contattomodel.h"
#include <QUrl>
#include <QLabel>
#include <QPainter>
#include <QTextBrowser>
#include <QStyleOptionGraphicsItem>
#include <QDebug>
#include <QTextDocument>
#include <QTextEdit>
#include <QLineEdit>

namespace {
  namespace ContattoRows {
    enum ContattoRows {
        NOME = 0,
        EMAIL,
        NOTE,
        LAST
    };
  }
}

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

    return QAbstractTableModel::flags(index) | Qt::ItemIsEditable;
}

int ContattoModel::columnCount(const QModelIndex &parent) const
{
    Q_UNUSED(parent);
    return ContattoRows::LAST;
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
        case ContattoRows::NOME :
            return QString::fromStdWString(item.nome());
        case ContattoRows::EMAIL :
            return QString::fromStdWString(item.email());
        case ContattoRows::NOTE :
            return QString::fromStdWString(item.note());
        }

    } else if (role == Qt::EditRole) {
        switch (index.column()) {
        case ContattoRows::NOME :
            return QString::fromStdWString(item.nome());
        case ContattoRows::EMAIL :
            return QString::fromStdWString(item.email());
        case ContattoRows::NOTE :
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
            case ContattoRows::NOME :
                return "Nome";
            case ContattoRows::EMAIL :
                return "Email";
            case ContattoRows::NOTE :
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
        case ContattoRows::NOME : {
            ::gestColl::contatti::contatto::nome_type nuovoNome = value.toString().toStdWString();
            this->items->at(index.row()).nome(nuovoNome);
            emit dataChanged(index, index);
        }
        break;
        case ContattoRows::EMAIL : {
            ::gestColl::contatti::contatto::email_type nuovoNome = value.toString().toStdWString();
            this->items->at(index.row()).email(nuovoNome);
            emit dataChanged(index, index);
        }
        break;
        case ContattoRows::NOTE : {
            ::gestColl::contatti::contatto::note_type nuovoNome = value.toString().toStdWString();
            this->items->at(index.row()).note(nuovoNome);
            emit dataChanged(index, index);
        }
        break;
    }
    }
    return ok;
}




ContattiDelegate::ContattiDelegate(QTableView *_view, QObject *parent)
    : QStyledItemDelegate(parent), view(_view)
{

}

ContattiDelegate::~ContattiDelegate() { }


QWidget *ContattiDelegate::createEditor(QWidget *parent, const QStyleOptionViewItem &option, const QModelIndex &index) const
{
    qDebug() << "create editor for " << index.column();
    return new QLineEdit(parent);
}

void ContattiDelegate::setEditorData(QWidget *editor, const QModelIndex &index) const
{
    QString value=index.data(Qt::DisplayRole).toString();
    QLineEdit *te=static_cast<QLineEdit*>(editor);
    te->setText(value);
}

void ContattiDelegate::setModelData(QWidget *editor, QAbstractItemModel *model, const QModelIndex &index) const
{
    QLineEdit *te=static_cast<QLineEdit*>(editor);
    model->setData(index, te->text());
}

void ContattiDelegate::updateEditorGeometry(QWidget *editor, const QStyleOptionViewItem &option, const QModelIndex &index) const
{
    Q_UNUSED(index);
    editor->setGeometry(option.rect);
}

