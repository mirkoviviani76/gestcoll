#ifndef DATIFISICIWIDGET_H
#define DATIFISICIWIDGET_H

#include <QGroupBox>
#include "scheda.hxx"

namespace Ui {
class DatiFisiciWidget;
}

class DatiFisiciWidget : public QGroupBox
{
    Q_OBJECT

public:
    explicit DatiFisiciWidget(QWidget *parent = 0);
    ~DatiFisiciWidget();
    void setData(gestColl::coins::datiFisici *datiFisici);
    void setEditable(bool editable);

private:
    Ui::DatiFisiciWidget *ui;
    gestColl::coins::datiFisici* datiFisici;

signals:
    void changesOccurred();
private slots:
    void on_peso_editingFinished();
    void on_dimensione_editingFinished();
    void on_forma_editingFinished();
    void on_metallo_editingFinished();
};

#endif // DATIFISICIWIDGET_H
