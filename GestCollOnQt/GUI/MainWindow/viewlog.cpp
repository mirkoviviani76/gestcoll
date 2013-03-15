#include "viewlog.h"
#include "ui_viewlog.h"
#include <QFile>

ViewLog::ViewLog(QString file, QWidget *parent) :
    QDialog(parent),
    ui(new Ui::ViewLog)
{
    ui->setupUi(this);
    QFile f(file);
    if (f.exists() && f.open(QIODevice::ReadOnly))
    {
        this->ui->textBrowser->setPlainText(f.readAll());
        f.close();
    }

}

ViewLog::~ViewLog()
{
    delete ui;
}

void ViewLog::changeEvent(QEvent *e)
{
    QDialog::changeEvent(e);
    switch (e->type()) {
    case QEvent::LanguageChange:
        ui->retranslateUi(this);
        break;
    default:
        break;
    }
}
