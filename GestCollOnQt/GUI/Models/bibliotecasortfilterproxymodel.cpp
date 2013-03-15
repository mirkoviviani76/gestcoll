#include "bibliotecasortfilterproxymodel.h"
#include "genericmodel.h"
#include <QFont>
#include <QDebug>

BibliotecaSortFilterProxyModel::BibliotecaSortFilterProxyModel(QObject *parent)
    : QSortFilterProxyModel(parent)
{
    GenericModel* model = new GenericModel(3);
    this->setSourceModel(model);
    this->setSortCaseSensitivity(Qt::CaseInsensitive);
}

QVariant BibliotecaSortFilterProxyModel::headerData(int section, Qt::Orientation orientation, int role) const {

    if ((orientation == Qt::Horizontal) && (role == Qt::DisplayRole)) {
        switch (section) {
        case 0:
            return "ID";
        case 1:
            return "Autore";
        case 2:
            return "Titolo";
        }
    } else {
        return QSortFilterProxyModel::headerData(section, orientation, role);
    }
    return QVariant();
}


bool BibliotecaSortFilterProxyModel::lessThan(const QModelIndex &left, const QModelIndex &right) const {
    GenericModel* model = (GenericModel*)this->sourceModel();
    BibliotecaItem* l = (BibliotecaItem*)model->getItem(left);
    BibliotecaItem* r = (BibliotecaItem*)model->getItem(right);
    bool ret = ((QString::localeAwareCompare(l->toString(left.column()), r->toString(right.column())) == 1) ? false : true);
    return ret;

}



void BibliotecaSortFilterProxyModel::appendRow(BibliotecaItem *item) {
    GenericModel* genericmodel = (GenericModel*) this->sourceModel();
    genericmodel->appendRow(item);
}

BibliotecaItem* BibliotecaSortFilterProxyModel::getItem(const QModelIndex &index) {
    GenericModel* genericmodel = (GenericModel*) this->sourceModel();
    BibliotecaItem* item = (BibliotecaItem*)genericmodel->getItem(this->mapToSource(index));
    return item;
}

