#ifndef DATIFISICIMODEL_H
#define DATIFISICIMODEL_H

#include <QAbstractTableModel>

#include <scheda.hxx>

class DatiFisiciModel : public QAbstractTableModel
{
public:
    explicit DatiFisiciModel(QObject *parent = 0);
    virtual ~DatiFisiciModel();
    QVariant data(const QModelIndex &index, int role) const;
    int rowCount(const QModelIndex &parent = QModelIndex()) const;
    int columnCount(const QModelIndex &parent = QModelIndex()) const;
    QModelIndex getIndex(int index);
    void clear();
    bool appendRow(gestColl::coins::datiFisici *item);
    //bool removeRow(int row, const QModelIndex &parent = QModelIndex());
    //QList<ScenarioData::Event*> getItems();
    QVariant headerData(int section, Qt::Orientation orientation, int role) const;

    gestColl::coins::datiFisici getItem();

private:
    gestColl::coins::datiFisici* datiFisici;


    // QAbstractItemModel interface
public:
    Qt::ItemFlags flags(const QModelIndex &index) const;

    // QAbstractItemModel interface
public:
    bool setData(const QModelIndex &index, const QVariant &value, int role);
};



#endif // DATIFISICIMODEL_H
