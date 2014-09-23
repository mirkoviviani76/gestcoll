#ifndef ADDDOCUMENTDIALOG_H
#define ADDDOCUMENTDIALOG_H

#include <QDialog>
#include <QStringList>
#include "scheda.hxx"


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
    void setData(const  ::gestColl::coins::documentoAddizionale& doc);
    
private slots:
    void on_selectFile_clicked();

private:
    Ui::AddDocumentDialog *ui;
    QStringList selectedFiles;
};

#endif // ADDDOCUMENTDIALOG_H
