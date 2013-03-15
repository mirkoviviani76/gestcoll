#ifndef STATISTICAMONETEPERANNO_H
#define STATISTICAMONETEPERANNO_H

#include <QWidget>
#include <QPaintEvent>

namespace Ui {
    class StatisticaMonetePerAnno;
}

class StatisticaMonetePerAnno : public QWidget
{
    Q_OBJECT

public:
    explicit StatisticaMonetePerAnno(QWidget *parent = 0);
    ~StatisticaMonetePerAnno();

private:
    Ui::StatisticaMonetePerAnno *ui;

    void paintEvent(QPaintEvent* e);
};

#endif // STATISTICAMONETEPERANNO_H
