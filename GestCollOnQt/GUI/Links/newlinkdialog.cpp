#include "newlinkdialog.h"
#include "ui_newlinkdialog.h"

#include <QDebug>

NewLinkDialog::NewLinkDialog(const QStringList &cat, QWidget *parent) :
    QDialog(parent),
    ui(new Ui::NewLinkDialog)
{
    ui->setupUi(this);
    this->ui->categoria->addItems(cat);
}

NewLinkDialog::~NewLinkDialog()
{
    delete ui;
}

QString NewLinkDialog::getNome() {
    QString c = this->ui->title->text();
    return c;
}

QUrl NewLinkDialog::getUrl() {
    QString c = this->ui->url->text();
    return QUrl(c);
}

QString NewLinkDialog::getNote() {
    QString c = this->ui->note->text();
    return c;
}

void NewLinkDialog::setData(xml::Link* link) {
    this->ui->title->setText(link->getNome());
    this->ui->url->setText(link->getUrl().toString());
    this->ui->note->setText(link->getNote());
}

QString NewLinkDialog::getCategoria() {
    return this->ui->categoria->currentText();
}

void NewLinkDialog::setCategoria(const QString& cat) {
    int index = this->ui->categoria->findText(cat);
    this->ui->categoria->setCurrentIndex(index);
}

