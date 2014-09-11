#ifndef LISTEMONETE_H
#define LISTEMONETE_H

#include <QWidget>
#include <QTreeWidgetItem>

#include "monetaxml.h"

namespace Ui {
    class ListeMonete;
}

class AnomalieMonete : public QWidget
{
    Q_OBJECT

public:
    explicit AnomalieMonete(QWidget *parent = 0);
    ~AnomalieMonete();

private slots:
    void on_treeWidget_itemDoubleClicked(QTreeWidgetItem *item, int column);

    void on_reloadButton_clicked();

    private:
    Ui::ListeMonete *ui;
    void fillData();
    void checkLetteratura();
    void checkNote();
    void checkAutorita();
    void checkPeso();

signals:
    void itemSelected(QString id);
};

#endif // LISTEMONETE_H
