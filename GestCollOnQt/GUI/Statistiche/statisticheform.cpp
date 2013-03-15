#include "statisticheform.h"
#include "ui_statisticheform.h"

#include <QPaintEngine>
#include <QPainter>

#include "nightcharts.h"

#include <QDebug>

StatisticheForm::StatisticheForm(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::StatisticheForm)
{
    ui->setupUi(this);
    connect(this->ui->widgetAnomalieMonete, SIGNAL(itemSelected(QString)), SLOT(onitemSelected(QString)));
    connect(this->ui->widgetSommarioMonete, SIGNAL(itemSelected(QString)), SLOT(onitemSelected(QString)));
}

StatisticheForm::~StatisticheForm()
{
    delete ui;
}

/**
  Rimanda il segnale di cambio item
  */
void StatisticheForm::onitemSelected(QString id) {
    emit this->itemSelected(id);
}
