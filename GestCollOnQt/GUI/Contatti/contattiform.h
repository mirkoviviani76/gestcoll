#ifndef CONTATTIFORM_H
#define CONTATTIFORM_H

#include <QWidget>
#include "genericmodel.h"
#include "contattixml.h"
#include <QMenu>

namespace Ui {
class ContattiForm;
}

class ContattiForm : public QWidget
{
        Q_OBJECT

    public:
        explicit ContattiForm(QWidget *parent = 0);
        ~ContattiForm();
    void addItem();
    void salva();


    private slots:
        void on_listView_customContextMenuRequested(const QPoint &pos);

        void on_listView_activated(const QModelIndex &index);

        void on_listView_doubleClicked(const QModelIndex &index);

        void on_listView_clicked(const QModelIndex &index);

private:
        QMenu contextMenu;
        Ui::ContattiForm *ui;
        GenericModel* contattiModel; ///< modello contatti
        ContattiXml contattiXml;
        xml::Contatto* contattoSelezionato;
        void loadData();

signals:
        void changesOccurred();
};

#endif // CONTATTIFORM_H
