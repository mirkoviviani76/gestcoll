#ifndef POSIZIONEDIALOG_H
#define POSIZIONEDIALOG_H

#include <QDialog>

namespace Ui {
    class posizioneDialog;
}

class posizioneDialog : public QDialog
{
    Q_OBJECT

public:
    explicit posizioneDialog(QWidget *parent = 0);
    ~posizioneDialog();
    void setData(QString cont, QString vass, QString r, QString c);
    void getData(QString& cont, QString& vass, QString& r, QString& c);

protected:
    void changeEvent(QEvent *e);

private:
    Ui::posizioneDialog *ui;
};

#endif // POSIZIONEDIALOG_H
