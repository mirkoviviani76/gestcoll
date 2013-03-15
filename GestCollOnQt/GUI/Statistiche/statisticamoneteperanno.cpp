#include "statisticamoneteperanno.h"
#include "ui_statisticamoneteperanno.h"

#include <QPainter>
#include <QFont>
#include <QMap>
#include <QString>
#include <QList>

#include "nightcharts.h"
#include "collezionexml.h"

StatisticaMonetePerAnno::StatisticaMonetePerAnno(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::StatisticaMonetePerAnno)
{
    ui->setupUi(this);
}

StatisticaMonetePerAnno::~StatisticaMonetePerAnno()
{
    delete ui;
}

void StatisticaMonetePerAnno::paintEvent(QPaintEvent* e)
{
    Q_UNUSED(e);

    QPainter painter;
    QFont font;
    painter.begin(this);
    Nightcharts pieChart;
    pieChart.setType(Nightcharts::Dpie);//{Histogramm,Pie,DPie};
    pieChart.setLegendType(Nightcharts::Round);//{Round,Vertical}
    pieChart.setCords(100,100,this->width()/2.0,this->height()/2.0);

    QMap<QString, float> dati;
    int totale = 0;

    QList<QString> idmonete = CollezioneXml::getInstance()->getAllId();
    foreach (QString id, idmonete) {
        MonetaXml* m = CollezioneXml::getInstance()->getMoneta(id);
        QString anno = m->getAnno();
        if (dati.contains(anno))
            dati[anno]+=1.0;
        else
            dati[anno] = 1.0;
        totale++;
    }

    //qsrand(789546);

    /* sistema il problema del rounding, sottraendo il "troppo" al primo valore */
    float totalSum = 0.0;
    foreach (QString key, dati.keys()) {
        totalSum += (100.0 /totale) * dati[key];
    }
    QString firstKey = dati.keys().at(0);
    dati[firstKey] -= (100.0 - totalSum);

    foreach (QString key, dati.keys()) {
        float percent = (100.0 /totale) * dati[key];
        int r = qrand() % 255;
        int g = qrand() % 255;
        int b = qrand() % 255;
        QColor randomColor(r, g, b);

        //qDebug() << key << '\t' << percent;

        pieChart.addPiece(key, randomColor, percent);
    }

    pieChart.draw(&painter);
    pieChart.drawLegend(&painter);
}

