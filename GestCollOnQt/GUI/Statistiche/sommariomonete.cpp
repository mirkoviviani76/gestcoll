#include "sommariomonete.h"
#include "ui_sommariomonete.h"

#include "collezionexml.h"

#include <QDebug>
#include <QtAlgorithms>

SommarioMonete::SommarioMonete(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::SommarioMonete)
{
    ui->setupUi(this);
    this->ui->treeWidget->setColumnCount(1);
    this->fillData();
}

SommarioMonete::~SommarioMonete()
{
    delete ui;
}

/**
  Riempie i dati relativi alle anomalie
  */
void SommarioMonete::fillData() {

    this->checkAutorita();
    this->checkPaesi();
    this->checkNominali();
}


/**
  sistema l'elenco delle autorita
  */
void SommarioMonete::checkAutorita() {
    QTreeWidgetItem* node = new QTreeWidgetItem(QStringList() << "Autorita");

    this->ui->treeWidget->addTopLevelItem(node);
    QList<QTreeWidgetItem *> items;

    QList<QString> allid = CollezioneXml::getInstance()->getAllId();
    QList<QString> lista;
    foreach (QString id, allid) {
        MonetaXml* m = CollezioneXml::getInstance()->getMoneta(id);
        foreach (xml::Autorita* a, m->getAutorita()) {
            QString data = m->getPaese()+" | "+a->nome;
            if (!lista.contains(data))
                lista.append(data);
        }
    }
    qSort(lista);

    foreach (QString n, lista) {
        items.append(new QTreeWidgetItem((QTreeWidget*)0, QStringList(QString("%1").arg(n))));
    }

    node->addChildren(items);
}

/**
  sistema l'elenco dei nominali
  */
void SommarioMonete::checkNominali() {
    QTreeWidgetItem* node = new QTreeWidgetItem(QStringList() << "Nominali");

    this->ui->treeWidget->addTopLevelItem(node);
    QList<QTreeWidgetItem *> items;

    QList<QString> allid = CollezioneXml::getInstance()->getAllId();
    QList<QString> lista;
    foreach (QString id, allid) {
        MonetaXml* m = CollezioneXml::getInstance()->getMoneta(id);
        QString data = m->getNominale().valuta + " " + m->getNominale().valore;
        if (!lista.contains(data))
            lista.append(data);
    }
    qSort(lista);

    foreach (QString n, lista) {
        items.append(new QTreeWidgetItem((QTreeWidget*)0, QStringList(QString("%1").arg(n))));
    }

    node->addChildren(items);
}


/**
  sistema l'elenco dei paesi
  */
void SommarioMonete::checkPaesi() {
    QTreeWidgetItem* node = new QTreeWidgetItem(QStringList() << "Paesi");

    this->ui->treeWidget->addTopLevelItem(node);
    QList<QTreeWidgetItem *> items;

    QList<QString> allid = CollezioneXml::getInstance()->getAllId();
    QList<QString> lista;
    foreach (QString id, allid) {
        MonetaXml* m = CollezioneXml::getInstance()->getMoneta(id);
        QString data = m->getPaese();
        if (!lista.contains(data))
            lista.append(data);
    }
    qSort(lista);

    foreach (QString n, lista) {
        items.append(new QTreeWidgetItem((QTreeWidget*)0, QStringList(QString("%1").arg(n))));
    }

    node->addChildren(items);
}


void SommarioMonete::on_treeWidget_itemDoubleClicked(QTreeWidgetItem *item, int column)
{
    Q_UNUSED(column);
    emit this->itemSelected(item->text(0));
}

void SommarioMonete::on_reloadButton_clicked()
{
    this->ui->treeWidget->clear();
    this->fillData();
}

