#ifndef EMISSIONEFORM_H
#define EMISSIONEFORM_H

#include <QGroupBox>
#include "emissionemodel.h"
#include "commondefs.h"

namespace Ui {
class EmissioneForm;
}

class EmissioneForm : public QGroupBox
{
    Q_OBJECT

public:
    explicit EmissioneForm(QWidget *parent = 0);
    ~EmissioneForm();
    void setData(xml::Emissione emissione);
    void setEditable(bool editable);

    void clear();
private:
    Ui::EmissioneForm *ui;
    EmissioneModel* modelloEmissione; ///< modello
    //gestColl::coins::datiFisici* datiFisici;

    bool editingEnabled;
signals:
    void changesOccurred();
private slots:
    void on_emissioneView_doubleClicked(const QModelIndex &index);
};




#endif // EMISSIONEFORM_H
