#include "statisticamonetepermetallo.h"
#include "ui_statisticamonetepermetallo.h"

#include <QPainter>
#include <QFont>
#include <QMap>
#include <QString>
#include <QList>

#include "nightcharts.h"
#include "collezionexml.h"

StatisticaMonetePerMetallo::StatisticaMonetePerMetallo(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::StatisticaMonetePerMetallo)
{
    ui->setupUi(this);
}

StatisticaMonetePerMetallo::~StatisticaMonetePerMetallo()
{
    delete ui;
}


void StatisticaMonetePerMetallo::paintEvent(QPaintEvent* e)
{
    Q_UNUSED(e);

    QPainter painter;
    painter.begin(this);
    Nightcharts pieChart;
    pieChart.setType(Nightcharts::Dpie);//{Histogramm,Pie,DPie};
    pieChart.setLegendType(Nightcharts::Vertical);//{Round,Vertical}
    pieChart.setCords(100,100,this->width()/1.5,this->height()/1.5);

    QMap<QString, int> dati;
    float totale = 0.0;

    QList<QString> idmonete = CollezioneXml::getInstance()->getAllId();
    foreach (QString id, idmonete) {
        MonetaXml* m = CollezioneXml::getInstance()->getMoneta(id);
        QString key = QString::fromStdWString(m->getDom()->datiFisici().metallo());
        if (dati.contains(key))
            dati[key]++;
        else
            dati[key] = 1;
        totale++;
    }

    //qsrand(1324);

    foreach (QString key, dati.keys()) {
        float percent = 100.0 * dati[key] / totale;
        QColor randomColor;
        randomColor.setRgb(qrand() % 255, qrand() % 255, qrand() % 255);
        //QColor randomColor((Qt::GlobalColor)(qrand()%Qt::transparent));
        pieChart.addPiece(key, randomColor, percent);
    }

    pieChart.draw(&painter);
    pieChart.drawLegend(&painter);
}
