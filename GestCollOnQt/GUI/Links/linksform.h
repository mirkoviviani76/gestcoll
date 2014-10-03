#ifndef LINKSFORM_H
#define LINKSFORM_H

#include <QWidget>
#include <QMenu>
#include <link.h>
#include <QStandardItemModel>
#include "treeitem.h"
#include "linksxml.h"

using namespace ::gestColl::links;

namespace Ui {
    class LinksForm;
}

class LinksForm : public QWidget
{
    Q_OBJECT

public:
    explicit LinksForm(QWidget *parent = 0);
    virtual ~LinksForm();
    bool isEditMode();
    void salva();
    void addItem();

protected:
    void changeEvent(QEvent *e);

public slots:
    void enableEdit(bool editmode);

private slots:
    void on_treeWidget_itemDoubleClicked(QTreeWidgetItem *item, int column);

    void on_treeWidget_customContextMenuRequested(const QPoint &pos);

private:
    Ui::LinksForm *ui;
    LinksXml linksXml;
    void setText();
    bool editMode;
    QMenu contextMenu;

signals:
    void changesOccurred();


};

#endif // LINKSFORM_H
