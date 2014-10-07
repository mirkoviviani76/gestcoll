#ifndef EMISSIONEFORM_H
#define EMISSIONEFORM_H

#include <QGroupBox>
#include "commondefs.h"
#include "scheda.hxx"

namespace Ui {
class EmissioneForm;
}

class EmissioneForm : public QGroupBox
{
    Q_OBJECT

public:
    explicit EmissioneForm(QWidget *parent = 0);
    ~EmissioneForm();
    void setData(xml::Emissione _emissione);
    void setEditable(bool editable);

    void clear();
private:
    Ui::EmissioneForm *ui;

    xml::Emissione emissione;
    bool editingEnabled;
signals:
    void changesOccurred();
private slots:
    void on_valore_editingFinished();
    void on_valuta_editingFinished();
    void on_anno_editingFinished();
    void paeseChanged();
};




#endif // EMISSIONEFORM_H
