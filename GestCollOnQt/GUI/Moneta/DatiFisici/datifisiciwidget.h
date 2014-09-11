#ifndef DATIFISICIWIDGET_H
#define DATIFISICIWIDGET_H

#include <QGroupBox>
#include "datifisicimodel.h"

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
    DatiFisiciModel* modelloDatiFisici; ///< modello dati fisici
    gestColl::coins::datiFisici* datiFisici;

signals:
    void changesOccurred();
};

#endif // DATIFISICIWIDGET_H
