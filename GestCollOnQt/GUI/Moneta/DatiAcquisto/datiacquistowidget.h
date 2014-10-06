#ifndef DATIACQUISTOWIDGET_H
#define DATIACQUISTOWIDGET_H

#include <QGroupBox>
#include "scheda.hxx"

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
    gestColl::coins::datiAcquisto* datiAcquisto;

signals:
    void changesOccurred();
private slots:
    void on_luogo_editingFinished();
    void on_data_editingFinished();
    void on_prezzo_editingFinished();
};

#endif // DATIACQUISTOWIDGET_H
