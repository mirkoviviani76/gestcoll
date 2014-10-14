#include "bibliotecaform.h"
#include "ui_bibliotecaform.h"

#include <QFile>
#include <QSplashScreen>
#include <QDesktopServices>
#include <QMessageBox>
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
    assert (item != NULL);

    QString autori = "";
    QString supporti = "";
    QString argomenti = "";
    QString filename = "";
    foreach (QString a, item->getAutori()) {
        autori.append(QString("<li>%1").arg(a));
    }
    foreach (QString a, item->getArgomenti()) {
        argomenti.append(QString("<li>%1").arg(a));
    }
    foreach (QString s, item->getSupporti()) {
        supporti.append(s);
    }

    if (item->getFilename() != "")
        filename = QString("%1/%2")
                .arg(CommonData::getInstance()->getBiblioDir())
                .arg(item->getFilename());

    QString html = QString("<h2>%1 [%2]</h2><h3>Autori</h3><ul>%3</ul><h3>Supporti</h3>%4<br /><a href=\"%5\">%5</a><h4>Argomenti</h4>%6")
            .arg(item->getTitolo())
            .arg(item->getId())
            .arg(autori)
            .arg(supporti)
            .arg(filename)
            .arg(argomenti)
            ;

    this->ui->textBrowser->setHtml(html);
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

bool BibliotecaForm::selectItem(const BibliotecaItem *item) {
    if (this->editable == true)
        return false;
    QModelIndex ret = this->model->getIndex(item);
    if (ret.isValid()) {
        //attiva l'item
        this->on_listView_activated(ret);
        //seleziona la riga corrispondente
        this->ui->listView->selectRow(ret.row());
        return true;
    }
    return false;

}


void BibliotecaForm::on_filter_textChanged(const QString &filterText)
{
    QRegExp regexp;
    if (!filterText.isEmpty()) {
        regexp = QRegExp(QString("^.*%1.*$").arg(filterText), Qt::CaseInsensitive);
    }
    this->model->setFilterRegExp(regexp);

}
