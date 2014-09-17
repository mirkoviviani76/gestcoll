#ifndef LETTERATURAFORM_H
#define LETTERATURAFORM_H

#include <QGroupBox>
#include "letteraturamodel.h"

namespace Ui {
class LetteraturaForm;
}

class LetteraturaForm : public QGroupBox
{
    Q_OBJECT

public:
    explicit LetteraturaForm(QWidget *parent = 0);
    ~LetteraturaForm();
    void setData(gestColl::coins::letteratura *_letteratura);
    void setEditable(bool editable);

private:
    Ui::LetteraturaForm *ui;

    LetteraturaModel* modelloLetteratura;
    gestColl::coins::letteratura * letteratura;
    bool editable;

signals:
    void changesOccurred();

private slots:
    void on_addLetteratura_clicked();
    void on_removeLetteratura_clicked();
};

#endif // LETTERATURAFORM_H
