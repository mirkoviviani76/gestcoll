#include "bibliotecasortfilterproxymodel.h"
#include <QFont>
#include <QDebug>

#include "bibliotecamodel.h"

BibliotecaSortFilterProxyModel::BibliotecaSortFilterProxyModel(QObject *parent)
    : QSortFilterProxyModel(parent)
{
    ModelloBiblioteca* model = new ModelloBiblioteca(this);
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
    ModelloBiblioteca* model = (ModelloBiblioteca*)this->sourceModel();
    BibliotecaItem* l = (BibliotecaItem*)model->getItem(left);
    BibliotecaItem* r = (BibliotecaItem*)model->getItem(right);
    int v = QString::localeAwareCompare(l->toString(left.column()), r->toString(right.column()));
    bool ret;
    if (v == 0)
        ret = false;
    if (v > 0)
        ret = false;
    if (v < 0)
        ret = true;

    return ret;

}

QModelIndex BibliotecaSortFilterProxyModel::getIndex(const BibliotecaItem *item) const
{
    ModelloBiblioteca* model = (ModelloBiblioteca*)this->sourceModel();
    QModelIndex sourceIndex = model->getIndex(item);
    return this->mapFromSource(sourceIndex);
}



void BibliotecaSortFilterProxyModel::appendRow(BibliotecaItem *item) {
    ModelloBiblioteca* genericmodel = (ModelloBiblioteca*) this->sourceModel();
    genericmodel->appendRow(item);
}

BibliotecaItem* BibliotecaSortFilterProxyModel::getItem(const QModelIndex &index) {
    ModelloBiblioteca* genericmodel = (ModelloBiblioteca*) this->sourceModel();
    BibliotecaItem* item = (BibliotecaItem*)genericmodel->getItem(this->mapToSource(index));
    return item;
}


bool BibliotecaSortFilterProxyModel::filterAcceptsRow(int sourceRow,
         const QModelIndex &sourceParent) const
 {
     QModelIndex index0 = sourceModel()->index(sourceRow, 0, sourceParent);
     QModelIndex index1 = sourceModel()->index(sourceRow, 1, sourceParent);
     QModelIndex index2 = sourceModel()->index(sourceRow, 2, sourceParent);

     return (sourceModel()->data(index0).toString().contains(filterRegExp())
             || sourceModel()->data(index1).toString().contains(filterRegExp())
             || sourceModel()->data(index2).toString().contains(filterRegExp()));
 }
