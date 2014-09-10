#ifndef AUTORITADIALOG_H
#define AUTORITADIALOG_H

#include <QDialog>
#include "scheda.hxx"


namespace Ui {
    class AutoritaDialog;
}

class AutoritaDialog : public QDialog
{
    Q_OBJECT

public:
    explicit AutoritaDialog(QWidget *parent = 0);
    ~AutoritaDialog();
    void setData(::gestColl::coins::autorita::nome_type autorita);
    void getData(::gestColl::coins::autorita::nome_type* nome);

protected:
    void changeEvent(QEvent *e);

private:
    Ui::AutoritaDialog *ui;

};

#endif // AUTORITADIALOG_H
