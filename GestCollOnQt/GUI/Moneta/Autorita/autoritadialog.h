#ifndef AUTORITADIALOG_H
#define AUTORITADIALOG_H

#include <QDialog>
#include <commondefs.h>

namespace Ui {
    class AutoritaDialog;
}

class AutoritaDialog : public QDialog
{
    Q_OBJECT

public:
    explicit AutoritaDialog(QWidget *parent = 0);
    ~AutoritaDialog();
    void setData(xml::Autorita* autorita);
    void getData(QString* nome);

protected:
    void changeEvent(QEvent *e);

private:
    Ui::AutoritaDialog *ui;
    xml::Autorita* autorita;

private slots:
    void on_buttonBox_accepted();
};

#endif // AUTORITADIALOG_H
