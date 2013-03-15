#ifndef STATISTICAMONETEPERNOMINALE_H
#define STATISTICAMONETEPERNOMINALE_H

#include <QWidget>

namespace Ui {
    class StatisticaMonetePerNominale;
}

class StatisticaMonetePerNominale : public QWidget
{
    Q_OBJECT

public:
    explicit StatisticaMonetePerNominale(QWidget *parent = 0);
    ~StatisticaMonetePerNominale();

private:
    Ui::StatisticaMonetePerNominale *ui;
    void paintEvent(QPaintEvent* e);
};

#endif // STATISTICAMONETEPERNOMINALE_H
