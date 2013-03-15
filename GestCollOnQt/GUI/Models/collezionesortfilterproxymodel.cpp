#include "collezionesortfilterproxymodel.h"
#include "genericmodel.h"
#include <QFont>
#include <QDebug>

CollezioneSortFilterProxyModel::CollezioneSortFilterProxyModel(QObject *parent)
    : QSortFilterProxyModel(parent)
{
    GenericModel* model = new GenericModel(5);
    this->setSourceModel(model);
}

QVariant CollezioneSortFilterProxyModel::headerData(int section, Qt::Orientation orientation, int role) const {

    if ((orientation == Qt::Horizontal) && (role == Qt::DisplayRole)) {
        switch (section) {
        case 0:
            return "ID";
        case 1:
            return "Paese";
        case 2:
            return "Nominale";
        case 3:
            return "Anno";
        case 4:
            return "Ambiti";
        }
    } else {
        return QSortFilterProxyModel::headerData(section, orientation, role);
    }
    return QVariant();
}

bool CollezioneSortFilterProxyModel::lessThan(const QModelIndex &left, const QModelIndex &right) const {
    GenericModel* model = (GenericModel*)this->sourceModel();
    MonetaXml* l = (MonetaXml*)model->getItem(left);
    MonetaXml* r = (MonetaXml*)model->getItem(right);

    bool ret = false;
    switch (left.column()) {
    case 0:
        {
            QString id1 = (const QString) l->getId();
            QString id2 = (const QString) r->getId();
            //sostituisce X con spazio (' ' precede numeri e lettere)
            id1 = id1.replace("X", " ");
            id2 = id2.replace("X", " ");
            //se entrambi cominciano con lo stesso valore, controlla il progressivo
            if (id1.left(4) == id2.left(4)) {
                QString progressivo1 = id1.split("-")[1];
                QString progressivo2 = id2.split("-")[1];
                ret = (progressivo1 < progressivo2);
            } else {
                ret = (id1 < id2);
            }
        }
        break;
    case 1:
        {
            ret = ((QString::localeAwareCompare(l->getPaese(), r->getPaese()) == 1) ? false : true);
        }
        break;
    case 2:
        {
            ret = (l->getNominale().valuta < r->getNominale().valuta);
        }
        break;
    default:
        {
            ret = (l->toString(left.column()) < r->toString(right.column()));
        }
    }
    return ret;

}

/*
QVariant CollezioneSortFilterProxyModel::data(const QModelIndex &index, int role) const {
    return QSortFilterProxyModel::data(index, role);
}
*/

void CollezioneSortFilterProxyModel::appendRow(MonetaXml *item) {
    GenericModel* genericmodel = (GenericModel*) this->sourceModel();
    genericmodel->appendRow(item);
}

MonetaXml* CollezioneSortFilterProxyModel::getItem(const QModelIndex &index) {
    GenericModel* genericmodel = (GenericModel*) this->sourceModel();
    MonetaXml* item = (MonetaXml*)genericmodel->getItem(this->mapToSource(index));
    return item;
}


