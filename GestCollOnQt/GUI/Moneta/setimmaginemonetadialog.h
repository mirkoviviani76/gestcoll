#ifndef SETIMMAGINEMONETADIALOG_H
#define SETIMMAGINEMONETADIALOG_H

#include <QDialog>
#include "monetaxml.h"

namespace Ui {
class SetImmagineMonetaDialog;
}

class SetImmagineMonetaDialog : public QDialog
{
    Q_OBJECT
    
public:
    explicit SetImmagineMonetaDialog(MonetaXml* moneta, Moneta::Lato, QWidget *parent = 0);
    ~SetImmagineMonetaDialog();
    
private slots:
    void on_checkBox_rinomina_stateChanged(int arg1);

    void on_checkBox_noImg_stateChanged(int arg1);

    void on_buttonBox_accepted();

    void on_setFile_clicked();

private:
    Ui::SetImmagineMonetaDialog *ui;
    QString suggestedFilename;
    QString oldFilename;
    MonetaXml* moneta;
    Moneta::Lato lato;
};

#endif // SETIMMAGINEMONETADIALOG_H
