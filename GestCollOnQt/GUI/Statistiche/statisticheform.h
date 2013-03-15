#ifndef STATISTICHEFORM_H
#define STATISTICHEFORM_H

#include <QWidget>

namespace Ui {
    class StatisticheForm;
}

class StatisticheForm : public QWidget
{
    Q_OBJECT

public:
    explicit StatisticheForm(QWidget *parent = 0);
    ~StatisticheForm();

private:
    Ui::StatisticheForm *ui;

private slots:
    void onitemSelected(QString id);

signals:
    void itemSelected(QString id);
};

#endif // STATISTICHEFORM_H
