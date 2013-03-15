#ifndef NEWCONTATTODIALOG_H
#define NEWCONTATTODIALOG_H

#include <QDialog>
#include "contattixml.h"
#include "commondefs.h"

namespace Ui {
class NewContattoDialog;
}

class NewContattoDialog : public QDialog
{
    Q_OBJECT
    
public:
    explicit NewContattoDialog(QWidget *parent = 0);
    ~NewContattoDialog();
    QString getNome();
    QString getEmail();
    QString getNote();
    void setData(const xml::Contatto& cont);
    
private:
    Ui::NewContattoDialog *ui;
};

#endif // NEWCONTATTODIALOG_H
