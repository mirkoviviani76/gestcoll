#ifndef NOTADIALOG_H
#define NOTADIALOG_H

#include <QDialog>
#include <commondefs.h>

namespace Ui {
    class NotaDialog;
}

class NotaDialog : public QDialog
{
    Q_OBJECT

public:
    explicit NotaDialog(bool editable, QWidget *parent = 0);
    ~NotaDialog();
    void setData(xml::Nota* nota);
    void getData(xml::Nota* testo);

protected:
    void changeEvent(QEvent *e);

private:
    Ui::NotaDialog *ui;
    xml::Nota* nota;

private slots:
    void on_buttonBox_accepted();
};

#endif // NOTADIALOG_H
