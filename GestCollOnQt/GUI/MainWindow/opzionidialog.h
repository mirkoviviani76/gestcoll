#ifndef OPZIONIDIALOG_H
#define OPZIONIDIALOG_H

#include <QDialog>

namespace Ui {
class OpzioniDialog;
}

class OpzioniDialog : public QDialog
{
    Q_OBJECT
    
public:
    explicit OpzioniDialog(QWidget *parent = 0);
    ~OpzioniDialog();
    
private:
    Ui::OpzioniDialog *ui;

private slots:
    void accept();
    void reject();


};

#endif // OPZIONIDIALOG_H
