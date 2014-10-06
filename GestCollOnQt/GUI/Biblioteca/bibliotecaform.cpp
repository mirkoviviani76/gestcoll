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
#include "bibliotecadelegate.h"

extern QSplashScreen* splash;


BibliotecaForm::BibliotecaForm(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::BibliotecaForm)
{
    ui->setupUi(this);

    this->model = new BibliotecaSortFilterProxyModel();
    this->setEditable(false);
    //this->ui->listView->setItemDelegate(new BibliotecaDelegate(this));
    this->ui->listView->verticalHeader()->setVisible(false);
    this->ui->listView->setSortingEnabled(true);

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

void BibliotecaForm::setEditable(bool editable)
{
    this->editable = editable;
    if (this->editable) {
        this->ui->listView->setEditTriggers(QAbstractItemView::DoubleClicked);
    } else {
        this->ui->listView->setEditTriggers(QAbstractItemView::NoEditTriggers);
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
    this->model->clear();
    foreach (BibliotecaItem* item, BibliotecaXml::getInstance()->getItems()) {
        this->model->appendRow(item);
    }
    this->ui->listView->setModel(model);
    this->model->sort(0);
    //this->ui->listView->resizeColumnsToContents();
    this->ui->listView->resizeRowsToContents();
    this->ui->listView->reset();
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


