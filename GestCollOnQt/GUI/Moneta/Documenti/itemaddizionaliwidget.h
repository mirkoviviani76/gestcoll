#ifndef ITEMADDIZIONALIWIDGET_H
#define ITEMADDIZIONALIWIDGET_H

#include <QGroupBox>
#include "documentimodel.h"

namespace Ui {
class ItemAddizionaliWidget;
}

class ItemAddizionaliWidget : public QGroupBox
{
    Q_OBJECT

public:
    explicit ItemAddizionaliWidget(QWidget *parent = 0);
    ~ItemAddizionaliWidget();
    void setData(::gestColl::coins::moneta::itemAddizionali_type* items);
    void setEditable(bool editable);

    void clear();
private:
    Ui::ItemAddizionaliWidget *ui;
    bool editingEnabled;
    DocumentiModel* documentiModel;

    ::gestColl::coins::moneta::itemAddizionali_type* xmlDom;

    void updateDocumento(const::gestColl::coins::documentoAddizionale &vecchio, const::gestColl::coins::documentoAddizionale &nuovo);
signals:
    void changesOccurred();
private slots:
    void on_itemsView_doubleClicked(const QModelIndex &index);
    void on_addItem_clicked();
    void on_removeItem_clicked();
};

#endif // ITEMADDIZIONALIWIDGET_H
