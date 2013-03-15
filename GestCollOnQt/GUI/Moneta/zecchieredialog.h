#ifndef ZECCHIEREDIALOG_H
#define ZECCHIEREDIALOG_H

#include <QDialog>
#include <commondefs.h>

namespace Ui {
    class ZecchiereDialog;
}

class ZecchiereDialog : public QDialog
{
    Q_OBJECT

public:
    explicit ZecchiereDialog(bool enableEdit, QWidget *parent = 0);
    ~ZecchiereDialog();
    void setData(xml::Zecchiere* zecchiere);
    void getData(QString* nome, QString* sigla, QString* ruolo);

protected:
    void changeEvent(QEvent *e);

private:
    Ui::ZecchiereDialog *ui;
    xml::Zecchiere* zecchiere;

private slots:
    void on_buttonBox_accepted();
};

#endif // ZECCHIEREDIALOG_H
