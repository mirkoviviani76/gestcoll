#ifndef ZECCAWIDGET_H
#define ZECCAWIDGET_H

#include <QGroupBox>
#include "zecchieremodel.h"
#include "scheda.hxx"

namespace Ui {
class ZeccaWidget;
}

class ZeccaWidget : public QGroupBox
{
    Q_OBJECT

public:
    explicit ZeccaWidget(QWidget *parent = 0);
    ~ZeccaWidget();

    void setData(::gestColl::coins::moneta::zecca_type *_zecca, ::gestColl::coins::moneta::zecchieri_type *_zecchieri);
    void setEditable(bool editable);
private:
    Ui::ZeccaWidget *ui;
    ::gestColl::coins::moneta::zecca_type *zecca;

    ZecchiereModel* modelloZecchiere;

signals:
    void changesOccurred();
private slots:
    void on_addZecchiere_clicked();
    void on_deleteZecchiere_clicked();

    void on_nomeZecca_textChanged(QString text);
    void on_segnoZecca_textChanged(QString text);
};

#endif // ZECCAWIDGET_H
