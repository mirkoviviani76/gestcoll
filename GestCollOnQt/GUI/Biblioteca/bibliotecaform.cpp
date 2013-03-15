#include "bibliotecaform.h"
#include "ui_bibliotecaform.h"

#include <QFile>
#include <QSplashScreen>
#include <QDesktopServices>
#include <QMessageBox>
#include <genericmodel.h>
#include <commondata.h>
#include <QFileInfo>
#include <QDebug>
#include <iostream>
#include "gestlog.h"

#include <QMetaObject>

extern QSplashScreen* splash;


BibliotecaForm::BibliotecaForm(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::BibliotecaForm)
{
    ui->setupUi(this);

    this->model = new BibliotecaSortFilterProxyModel();
    this->fillData();
    this->model->sort(0);
    this->ui->listView->setModel(model);
    //qDebug() << "Nome classe:" << this->metaObject()->className() << this->metaObject()->classInfoCount();



}

BibliotecaForm::~BibliotecaForm()
{
    delete ui;

    if (this->model != NULL) {
        this->model->clear();
        delete this->model;
        this->model = NULL;
    }

}

void BibliotecaForm::changeEvent(QEvent *e)
{
    QWidget::changeEvent(e);
    switch (e->type()) {
    case QEvent::LanguageChange:
        ui->retranslateUi(this);
        break;
    default:
        break;
    }
}



void BibliotecaForm::fillData()
{
    foreach (BibliotecaItem* item, BibliotecaXml::getInstance()->getItems()) {
        this->model->appendRow(item);
    }
}



void BibliotecaForm::on_listView_activated(QModelIndex index)
{
    BibliotecaItem* item = this->model->getItem(index);
    this->ui->textBrowser->setText(item->toHtml());
}



void BibliotecaForm::on_textBrowser_anchorClicked(const QUrl &arg1)
{
    if (QFile(arg1.toString()).exists()) {
        QDesktopServices::openUrl(arg1);
    } else {
        QMessageBox::warning(this, CommonData::getInstance()->getAppName(), QString("Impossible aprire %1")
                             .arg(arg1.toString()));
    }
}
