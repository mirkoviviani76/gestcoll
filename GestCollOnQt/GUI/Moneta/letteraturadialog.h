#ifndef LETTERATURADIALOG_H
#define LETTERATURADIALOG_H

#include <QDialog>
#include <commondefs.h>

namespace Ui {
    class LetteraturaDialog;
}

class LetteraturaDialog : public QDialog
{
    Q_OBJECT

public:
    explicit LetteraturaDialog(QWidget *parent = 0);
    ~LetteraturaDialog();
    void setData(xml::Libro* libro);
    void getData(QString* sigla, QString* numero);

protected:
    void changeEvent(QEvent *e);

private:
    Ui::LetteraturaDialog *ui;
    xml::Libro* libro;

private slots:
    void on_buttonBox_accepted();
};

#endif // LETTERATURADIALOG_H
