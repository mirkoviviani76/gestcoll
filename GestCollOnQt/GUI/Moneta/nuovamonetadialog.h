#ifndef NUOVAMONETADIALOG_H
#define NUOVAMONETADIALOG_H

#include <QDialog>
#include <QMap>
#include "monetaxml.h"

namespace Ui {
    class NuovaMonetaDialog;
}

class NuovaMonetaDialog : public QDialog
{
    Q_OBJECT

public:
    explicit NuovaMonetaDialog(QWidget *parent = 0);
    void setParameters(QString cont, QString vass, int r, int c, QString size);
    ~NuovaMonetaDialog();
    inline QString getSuggestedId() {return this->id;}
    MonetaXml* getNuovaMoneta();

protected:
    void changeEvent(QEvent *e);

private:
    void updateId();
    Ui::NuovaMonetaDialog *ui;
    QMap<QString, int> countByYear;
    QString dim;
    QString pos;
    QString anno;
    QString id;
    MonetaXml* added;

private slots:
    void on_anno_textChanged(QString );
    void on_buttonBox_accepted();
    void on_comboBox_currentIndexChanged(QString );
};

#endif // NUOVAMONETADIALOG_H
