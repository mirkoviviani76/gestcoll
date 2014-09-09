#ifndef VASSOIOFORM_H
#define VASSOIOFORM_H

#include <QWidget>
#include <generictabmodel.h>
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
    void setData(int riga, int colonna, GenericItem* data);
    void setCurrentIndex(int riga, int colonna);
    void setModel();
    void resizeRows();

protected:
    void changeEvent(QEvent *e);

private:
    GenericTabModel* model;
    QString contenitore;
    QString vassoio;
    QString dim;
    Ui::VassoioForm *ui;

signals:
    void idChangeRequest(QString newid);
    void newIdAdded(MonetaXml* newId);

private slots:
    void on_tableView_activated(QModelIndex index);
    void addItem(MonetaXml* newId);
};

#endif // VASSOIOFORM_H
