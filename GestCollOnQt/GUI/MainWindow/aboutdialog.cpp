#include "aboutdialog.h"
#include "ui_aboutdialog.h"
#include "bzlib.h"
#include "commondata.h"

AboutDialog::AboutDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::AboutDialog)
{
    ui->setupUi(this);
    this->ui->title->setText(QString("<h1>%1</h1>").arg(CommonData::getInstance()->getAppId()));
    QString text = QString("<ul><li>QT v.%2<li>bzlib v.%3</ul>")
            .arg(qVersion())
            .arg(BZ2_bzlibVersion());
    this->ui->text->setText(text);
}

AboutDialog::~AboutDialog()
{
    delete ui;
}

void AboutDialog::changeEvent(QEvent *e)
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
