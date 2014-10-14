#ifndef BIBLIOTECAFORM_H
#define BIBLIOTECAFORM_H

#include <QWidget>
#include "bibliotecasortfilterproxymodel.h"
#include <bibliotecaitem.h>
#include "bibliotecaxml.h"


namespace Ui {
    class BibliotecaForm;
}

class BibliotecaForm : public QWidget
{
    Q_OBJECT

public:
    explicit BibliotecaForm(QWidget *parent = 0);
    ~BibliotecaForm();
    void setModel(BibliotecaSortFilterProxyModel* model);
    void setEditable(bool editable);
    void fillData();

protected:
    void changeEvent(QEvent *e);

private:
    BibliotecaSortFilterProxyModel* model;
    Ui::BibliotecaForm *ui;
    bool editable;

private slots:
    void on_listView_activated(QModelIndex index);
    void on_textBrowser_anchorClicked(const QUrl &arg1);
    void on_filter_textChanged(const QString &filterText);

public slots:
    bool selectItem(const BibliotecaItem* item);
};

#endif // BIBLIOTECAFORM_H
