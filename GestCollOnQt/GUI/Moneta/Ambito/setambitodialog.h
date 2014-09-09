#ifndef SETAMBITODIALOG_H
#define SETAMBITODIALOG_H

#include <QDialog>
#include "commondefs.h"
#include <QList>
#include <QMap>
#include <QCheckBox>

namespace Ui {
class SetAmbitoDialog;
}

class SetAmbitoDialog : public QDialog
{
    Q_OBJECT
    
public:
    explicit SetAmbitoDialog(QList<xml::Ambito*> ambiti, QList<xml::Ambito*> ambitiPresenti, QWidget *parent = 0);
    ~SetAmbitoDialog();
    QList<xml::Ambito*> getAmbitiSelezionati();
    
private:
    Ui::SetAmbitoDialog *ui;
    QMap<QCheckBox*, xml::Ambito*> mappa;
};

#endif // SETAMBITODIALOG_H
