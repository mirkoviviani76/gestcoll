#ifndef SETCOLLEZIONEINFODIALOG_H
#define SETCOLLEZIONEINFODIALOG_H

#include <QDialog>
#include "ambitimodel.h"
#include "commondefs.h"
#include <QMenu>

namespace Ui {
class SetCollezioneInfoDialog;
}

class SetCollezioneInfoDialog : public QDialog
{
    Q_OBJECT
    
public:
    explicit SetCollezioneInfoDialog(QWidget *parent = 0);
    ~SetCollezioneInfoDialog();
    
private slots:
    void on_buttonBox_accepted();

    void on_ambiti_doubleClicked(const QModelIndex &index);

    void on_ambiti_customContextMenuRequested(const QPoint &pos);

private:
    Ui::SetCollezioneInfoDialog *ui;
    AmbitiModel* ambitiModel;
    xml::Info* info;
    QMenu contextMenu;


};

#endif // SETCOLLEZIONEINFODIALOG_H
