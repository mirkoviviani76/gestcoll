#ifndef MODIFYAMBITODIALOG_H
#define MODIFYAMBITODIALOG_H

#include <QDialog>
#include "commondefs.h"

namespace Ui {
class ModifyAmbitoDialog;
}

class ModifyAmbitoDialog : public QDialog
{
    Q_OBJECT
    
public:
    explicit ModifyAmbitoDialog(xml::Ambito* a, QWidget *parent = 0);
    explicit ModifyAmbitoDialog(QWidget *parent = 0);
    ~ModifyAmbitoDialog();
    xml::Ambito* getData();
private:
    Ui::ModifyAmbitoDialog *ui;
    xml::Ambito* ambito;
};

#endif // MODIFYAMBITODIALOG_H
