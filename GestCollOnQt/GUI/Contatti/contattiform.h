#ifndef CONTATTIFORM_H
#define CONTATTIFORM_H

#include <QWidget>
#include "contattomodel.h"
#include <QMenu>
#include <QFileInfo>
#include <QItemDelegate>

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
    bool save();
    void enableEdit(bool editable);

private:
        QMenu contextMenu;
        Ui::ContattiForm *ui;
        ContattoModel* contattiModel; ///< modello contatti
        void loadData();

        ::std::auto_ptr< ::gestColl::contatti::contatti > contattiData;
        bool editable;

        void readXml(const QFileInfo& file);
signals:
        void changesOccurred();

};


#endif // CONTATTIFORM_H
