#ifndef SOMMARIOMONETE_H
#define SOMMARIOMONETE_H

#include <QTreeWidgetItem>

#include "monetaxml.h"

#include <QWidget>

namespace Ui {
    class SommarioMonete;
}

class SommarioMonete : public QWidget
{
    Q_OBJECT

    public:
        explicit SommarioMonete(QWidget *parent = 0);
        ~SommarioMonete();

    private slots:
        void on_treeWidget_itemDoubleClicked(QTreeWidgetItem *item, int column);

        void on_reloadButton_clicked();

    private:
        Ui::SommarioMonete* ui;
        void fillData();
        void checkAutorita();
        void checkPaesi();
        void checkNominali();

    signals:
        void itemSelected(QString id);


};

#endif // SOMMARIOMONETE_H
