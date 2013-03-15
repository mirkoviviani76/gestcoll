#ifndef LEGENDADIALOG_H
#define LEGENDADIALOG_H

#include <QDialog>
#include <commondefs.h>

namespace Ui {
    class LegendaDialog;
}

class LegendaDialog : public QDialog
{
    Q_OBJECT

public:
    explicit LegendaDialog(QWidget *parent = 0);
    ~LegendaDialog();
    void setData(xml::Legenda* legenda);
    void getData(QString* testo, QString* scioglimento);

protected:
    void changeEvent(QEvent *e);

private:
    Ui::LegendaDialog *ui;
    xml::Legenda* legenda;

private slots:
    void on_buttonBox_accepted();
    void specialCharSelected(QString sc);
};

#endif // LEGENDADIALOG_H
