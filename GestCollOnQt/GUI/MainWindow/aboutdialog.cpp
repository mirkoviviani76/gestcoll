#include "aboutdialog.h"
#include "ui_aboutdialog.h"
#include "bzlib.h"
#include "commondata.h"

AboutDialog::AboutDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::AboutDialog)
{
    ui->setupUi(this);
#ifdef BZLIB
    QString text = QString("<h1>%1</h1>QT v.%2<br>bzlib v.%3")
            .arg(CommonData::getInstance()->getAppId())
            .arg(qVersion())
            .arg(BZ2_bzlibVersion());
#else
qWarning() << "BZLIB unavailable";
    QString text = QString("<h1>%1</h1>QT v.%2<br>bzlib v.%3")
            .arg(CommonData::getInstance()->getAppId())
            .arg(qVersion())
            .arg("XXX");
#endif
    this->ui->textBrowser->append(text);
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
