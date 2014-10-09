#include "collezionesortfilterproxymodel.h"
#include "collezionemodel.h"
#include <QFont>
#include <QDebug>

CollezioneSortFilterProxyModel::CollezioneSortFilterProxyModel(QObject *parent)
    : QSortFilterProxyModel(parent)
{
    CollezioneModel* model = new CollezioneModel(this);
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
    CollezioneModel* model = (CollezioneModel*)this->sourceModel();

    if (!left.isValid())
        return false;
    if (!right.isValid())
        return false;

    MonetaXml* l = (MonetaXml*)model->getItem(left);
    MonetaXml* r = (MonetaXml*)model->getItem(right);

    assert(l != NULL);
    assert(r != NULL);

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
            const QString paese1 = QString::fromStdWString(l->getDom()->paese());
            const QString paese2 = QString::fromStdWString(r->getDom()->paese());
            int v = QString::localeAwareCompare(paese1, paese2);
            if (v == 0)
                ret = false;
            else if (v > 0)
                ret = false;
            else if (v < 0)
               ret = true;
    }
        break;
    case 2:
    {
        QString valuta1 = QString::fromStdWString(l->getDom()->nominale().valuta());
        QString valuta2 = QString::fromStdWString(r->getDom()->nominale().valuta());
        ret = (valuta1 < valuta2);
    }
        break;
    case 3:
    {
        QString anno1 = QString::fromStdWString(l->getDom()->anno());
        QString anno2 = QString::fromStdWString(r->getDom()->anno());
        ret = (anno1 < anno2);
    }
        break;
    case 4:
    {
        QStringList a1;
        QStringList a2;
        foreach (xml::Ambito* a, l->getAmbiti()) {
            a1 << a->titolo;
        }
        foreach (xml::Ambito* a, r->getAmbiti()) {
            a2 << a->titolo;
        }

        ret = (a1.join(' ') < a2.join(' '));
    }
        break;
    }
    return ret;

}

/*
QVariant CollezioneSortFilterProxyModel::data(const QModelIndex &index, int role) const {
    return QSortFilterProxyModel::data(index, role);
}
*/

void CollezioneSortFilterProxyModel::appendRow(MonetaXml *item) {
    CollezioneModel* genericmodel = (CollezioneModel*) this->sourceModel();
    genericmodel->appendRow(item);
}

MonetaXml* CollezioneSortFilterProxyModel::getItem(const QModelIndex &index) {
    CollezioneModel* genericmodel = (CollezioneModel*) this->sourceModel();
    MonetaXml* item = (MonetaXml*)genericmodel->getItem(this->mapToSource(index));
    return item;
}




bool CollezioneSortFilterProxyModel::filterAcceptsRow(int sourceRow,
         const QModelIndex &sourceParent) const
 {
     QModelIndex index0 = sourceModel()->index(sourceRow, 0, sourceParent);
     QModelIndex index1 = sourceModel()->index(sourceRow, 1, sourceParent);
     QModelIndex index2 = sourceModel()->index(sourceRow, 2, sourceParent);
     QModelIndex index3 = sourceModel()->index(sourceRow, 3, sourceParent);
     QModelIndex index4 = sourceModel()->index(sourceRow, 4, sourceParent);

     return (sourceModel()->data(index0).toString().contains(filterRegExp())
             || sourceModel()->data(index1).toString().contains(filterRegExp())
             || sourceModel()->data(index2).toString().contains(filterRegExp())
             || sourceModel()->data(index3).toString().contains(filterRegExp())
             || sourceModel()->data(index4).toString().contains(filterRegExp()));
 }
