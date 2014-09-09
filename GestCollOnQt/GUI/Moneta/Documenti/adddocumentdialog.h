#ifndef ADDDOCUMENTDIALOG_H
#define ADDDOCUMENTDIALOG_H

#include <QDialog>
#include <QStringList>
#include "commondefs.h"


namespace Ui {
class AddDocumentDialog;
}

class AddDocumentDialog : public QDialog
{
    Q_OBJECT
    
public:
    explicit AddDocumentDialog(QWidget *parent = 0);
    ~AddDocumentDialog();
    QStringList getFilenames();
    QString getDescrizione();
    void setData(xml::Documento* doc);
    
private slots:
    void on_selectFile_clicked();

private:
    Ui::AddDocumentDialog *ui;
    QStringList selectedFiles;
};

#endif // ADDDOCUMENTDIALOG_H
