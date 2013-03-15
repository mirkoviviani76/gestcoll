#include "statisticamonetepernominale.h"
#include "ui_statisticamonetepernominale.h"

#include <QPainter>
#include <QFont>
#include <QMap>
#include <QString>
#include <QList>

#include "nightcharts.h"
#include "collezionexml.h"

StatisticaMonetePerNominale::StatisticaMonetePerNominale(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::StatisticaMonetePerNominale)
{
    ui->setupUi(this);
}

StatisticaMonetePerNominale::~StatisticaMonetePerNominale()
{
    delete ui;
}


void StatisticaMonetePerNominale::paintEvent(QPaintEvent* e)
{
    Q_UNUSED(e);

    QPainter painter;
    QFont font;
    painter.begin(this);
    Nightcharts pieChart;
    pieChart.setType(Nightcharts::Dpie);//{Histogramm,Pie,DPie};
    pieChart.setLegendType(Nightcharts::Round);//{Round,Vertical}
    pieChart.setCords(100,100,this->width()/2.0,this->height()/2.0);

    QMap<QString, int> dati;
    float totale = 0.0;

    QList<QString> idmonete = CollezioneXml::getInstance()->getAllId();
    foreach (QString id, idmonete) {
        MonetaXml* m = CollezioneXml::getInstance()->getMoneta(id);
        QString nominale = QString("%1 %2")
                .arg(m->getNominale().valore)
                .arg(m->getNominale().valuta);
        if (dati.contains(nominale))
            dati[nominale]++;
        else
            dati[nominale] = 1;
        totale++;
    }

    //qsrand(34434);
    foreach (QString key, dati.keys()) {
        float percent = 100.0 * dati[key] / totale;
        int r = qrand() % 255;
        int g = qrand() % 255;
        int b = qrand() % 255;
        QColor randomColor(r, g, b);

        pieChart.addPiece(key, randomColor, percent);
    }

    pieChart.draw(&painter);
    pieChart.drawLegend(&painter);
}
