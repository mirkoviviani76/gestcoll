#ifndef VASSOIOFORM_H
#define VASSOIOFORM_H

#include <QWidget>
#include "vassoiomodel.h"

#include <monetaxml.h>

namespace Ui {
    class VassoioForm;
}

class VassoioForm : public QWidget
{
    Q_OBJECT

public:
    explicit VassoioForm(QWidget *parent = 0);
    ~VassoioForm();
    void setSize(QString cont, QString vass, int righe, int colonne, QString dim);
    void setData(const Casella& c, MonetaXml *data);
    void setCurrentIndex(const Casella& c);
    void setModel();
    void resizeRows();

protected:
    void changeEvent(QEvent *e);

private:
    VassoioModel* model;
    QString contenitore;
    QString vassoio;
    QString dim;
    Ui::VassoioForm *ui;

signals:
    void idChangeRequest(QString newid);
    void newIdAdded(MonetaXml* newId);

private slots:
    void on_tableView_doubleClicked(QModelIndex index);
    void addItem(MonetaXml* newId);
};

#endif // VASSOIOFORM_H
