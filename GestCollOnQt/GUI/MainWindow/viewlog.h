#ifndef VIEWLOG_H
#define VIEWLOG_H

#include <QDialog>

namespace Ui {
    class ViewLog;
}

class ViewLog : public QDialog
{
    Q_OBJECT

public:
    explicit ViewLog(QString file, QWidget *parent = 0);
    ~ViewLog();

protected:
    void changeEvent(QEvent *e);

private:
    Ui::ViewLog *ui;
};

#endif // VIEWLOG_H
