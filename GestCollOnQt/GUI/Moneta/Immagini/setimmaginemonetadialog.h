#ifndef SETIMMAGINEMONETADIALOG_H
#define SETIMMAGINEMONETADIALOG_H

#include <QDialog>
#include "scheda.hxx"

namespace Ui {
class SetImmagineMonetaDialog;
}

class SetImmagineMonetaDialog : public QDialog
{
    Q_OBJECT
    
public:
    explicit SetImmagineMonetaDialog(const QString& filename, const QString &id, const QString &_latoId, QWidget *parent = 0);
    ~SetImmagineMonetaDialog();
    QString getNewFilename();
    
private slots:
    void on_checkBox_rinomina_stateChanged(int arg1);

    void on_checkBox_noImg_stateChanged(int arg1);

    void on_buttonBox_accepted();

    void on_setFile_clicked();

private:
    Ui::SetImmagineMonetaDialog *ui;
    QString suggestedFilename;
    QString oldFilename;
    QString monetaId;
    QString latoId;

    QString targetFilename;

};

#endif // SETIMMAGINEMONETADIALOG_H
