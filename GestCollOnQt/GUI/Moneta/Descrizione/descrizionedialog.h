#ifndef DESCRIZIONEDIALOG_H
#define DESCRIZIONEDIALOG_H

#include <QDialog>

namespace Ui {
    class DescrizioneDialog;
}

class DescrizioneDialog : public QDialog
{
    Q_OBJECT

public:
    explicit DescrizioneDialog(QWidget *parent = 0);
    ~DescrizioneDialog();
    void setData(const QString& testo);
    void getData(QString* testo);

protected:
    void changeEvent(QEvent *e);

private:
    Ui::DescrizioneDialog *ui;

private slots:
    void specialCharSelected(QString sc);
};

#endif // DESCRIZIONEDIALOG_H
