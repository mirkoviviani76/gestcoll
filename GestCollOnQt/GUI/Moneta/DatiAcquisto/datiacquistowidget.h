#ifndef DATIACQUISTOWIDGET_H
#define DATIACQUISTOWIDGET_H

#include <QGroupBox>
#include "datiacquistomodel.h"

namespace Ui {
class DatiAcquistoWidget;
}

class DatiAcquistoWidget : public QGroupBox
{
    Q_OBJECT

public:
    explicit DatiAcquistoWidget(QWidget *parent = 0);
    ~DatiAcquistoWidget();
    void setData(gestColl::coins::datiAcquisto *datiAcquisto);
    void setEditable(bool editable);

private:
    Ui::DatiAcquistoWidget *ui;
    DatiAcquistoModel* modelloDatiAcquisto;
    gestColl::coins::datiAcquisto* datiAcquisto;

signals:
    void changesOccurred();
};

#endif // DATIACQUISTOWIDGET_H
