#ifndef STATISTICAMONETEPERMETALLO_H
#define STATISTICAMONETEPERMETALLO_H

#include <QWidget>

namespace Ui {
    class StatisticaMonetePerMetallo;
}

class StatisticaMonetePerMetallo : public QWidget
{
    Q_OBJECT

public:
    explicit StatisticaMonetePerMetallo(QWidget *parent = 0);
    ~StatisticaMonetePerMetallo();

private:
    Ui::StatisticaMonetePerMetallo *ui;
    void paintEvent(QPaintEvent* e);
};

#endif // STATISTICAMONETEPERMETALLO_H
