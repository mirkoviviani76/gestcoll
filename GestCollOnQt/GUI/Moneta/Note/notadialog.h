#ifndef NOTADIALOG_H
#define NOTADIALOG_H

#include <QDialog>
#include "scheda.hxx"

namespace Ui {
    class NotaDialog;
}

class NotaDialog : public QDialog
{
    Q_OBJECT

public:
    explicit NotaDialog(bool editable, QWidget *parent = 0);
    ~NotaDialog();
    void setData(gestColl::coins::note::nota_type nota);
    void getData(gestColl::coins::note::nota_type *testo);

protected:
    void changeEvent(QEvent *e);

private:
    Ui::NotaDialog *ui;

private slots:
    void on_buttonBox_accepted();
};

#endif // NOTADIALOG_H
