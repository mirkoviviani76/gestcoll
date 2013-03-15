#ifndef NEWLINKDIALOG_H
#define NEWLINKDIALOG_H

#include <QDialog>
#include "link.h"

namespace Ui {
class NewLinkDialog;
}

class NewLinkDialog : public QDialog
{
    Q_OBJECT
    
public:
    explicit NewLinkDialog(const QStringList &cat, QWidget *parent = 0);
    ~NewLinkDialog();

    void setCategoria(const QString& cat);
    QString getCategoria();
    QString getNome();
    QUrl getUrl();
    QString getNote();
    void setData(xml::Link* link);

    
private:
    Ui::NewLinkDialog *ui;
};

#endif // NEWLINKDIALOG_H
