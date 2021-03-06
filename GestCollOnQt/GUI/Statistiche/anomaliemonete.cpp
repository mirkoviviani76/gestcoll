#include "anomaliemonete.h"
#include "ui_anomaliemonete.h"
#include "collezionexml.h"

#include <QSet>
#include <QDebug>

AnomalieMonete::AnomalieMonete(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::ListeMonete)
{
    ui->setupUi(this);
    this->ui->treeWidget->setColumnWidth(0, 300);
    this->ui->treeWidget->setColumnWidth(1, 300);
    this->fillData();
}

AnomalieMonete::~AnomalieMonete()
{
    delete ui;
}

/**
  Riempie i dati relativi alle anomalie
  */
void AnomalieMonete::fillData() {

    this->checkLetteratura();
    this->checkAutorita();
    this->checkPeso();
}



/**
  Controlla la presenza della letteratura in ogni moneta
  */
void AnomalieMonete::checkLetteratura() {
    QTreeWidgetItem* node = new QTreeWidgetItem(QStringList() << "Letteratura mancante");

    this->ui->treeWidget->addTopLevelItem(node);
    QList<QTreeWidgetItem *> items;

    QList<QString> allid = CollezioneXml::getInstance()->getAllId();
    foreach (QString id, allid) {
        MonetaXml* m = CollezioneXml::getInstance()->getMoneta(id);
        bool ok = (m->getDom()->letteratura().libro().size() > 0 ? true : false);
        if (!ok) {
            items.append(new QTreeWidgetItem((QTreeWidget*)0, QStringList(QString("%1").arg(id))));
        }
    }
    node->addChildren(items);

    node = new QTreeWidgetItem(QStringList() << "Letteratura senza corrispondenza in Biblioteca");
    this->ui->treeWidget->addTopLevelItem(node);

    foreach (QString id, allid) {
        MonetaXml* m = CollezioneXml::getInstance()->getMoneta(id);

        QStringList sl;
        sl.append(id);
        QString libri = "";
        bool insert = false;

        for ( ::gestColl::coins::letteratura::libro_iterator it = m->getDom()->letteratura().libro().begin();
              it != m->getDom()->letteratura().libro().end(); ++it) {
            QString sigla = QString::fromStdWString((*it).sigla());
            BibliotecaItem* item = BibliotecaXml::getInstance()->getItem(sigla);
            if (item == NULL) {
                libri = libri + " | " + sigla;
                insert = true;
            }
        }
        if (insert == true) {
            sl.append(libri);
            items.append(new QTreeWidgetItem((QTreeWidget*)0, sl));
        }
    }
    node->addChildren(items);

#if 0
    QSet<QString> listaLibri;

    foreach (QString id, allid) {
        MonetaXml* m = CollezioneXml::getInstance()->getMoneta(id);
        QList<xml::Libro*> letteratura = m->getLetteratura();
        foreach (xml::Libro* l, letteratura) {
            if (l->toTooltip() != "") {
                listaLibri.insert(l->toTooltip());
            }
        }
    }
    foreach(QString l, listaLibri) {
        qDebug() << l;
    }
#endif

}

/**
  Controlla la presenza delle autorita in ogni moneta
  */
void AnomalieMonete::checkAutorita() {
    QTreeWidgetItem* node = new QTreeWidgetItem(QStringList() << "Autorita mancante");
    this->ui->treeWidget->addTopLevelItem(node);
    QList<QTreeWidgetItem *> items;

    QList<QString> allid = CollezioneXml::getInstance()->getAllId();
    foreach (QString id, allid) {
        MonetaXml* m = CollezioneXml::getInstance()->getMoneta(id);
        bool ok = false;
        ok = ((m->getDom()->autorita().nome().size() > 0) ? true : false);
        if (!ok) {
            items.append(new QTreeWidgetItem((QTreeWidget*)0, QStringList(QString("%1").arg(id))));
        }
    }
    node->addChildren(items);
}

/**
  Controlla la presenza del peso in ogni moneta
  */
void AnomalieMonete::checkPeso() {
    QTreeWidgetItem* node = new QTreeWidgetItem(QStringList() << "Peso mancante");

    this->ui->treeWidget->addTopLevelItem(node);
    QList<QTreeWidgetItem *> items;

    QList<QString> allid = CollezioneXml::getInstance()->getAllId();
    foreach (QString id, allid) {
        MonetaXml* m = CollezioneXml::getInstance()->getMoneta(id);
        bool ok = ((m->getDom()->datiFisici().peso().valore()) > 0 ? true : false);
        if (!ok) {
            items.append(new QTreeWidgetItem((QTreeWidget*)0, QStringList(QString("%1").arg(id))));
        }
    }
    node->addChildren(items);
}


void AnomalieMonete::on_treeWidget_itemDoubleClicked(QTreeWidgetItem *item, int column)
{
    Q_UNUSED(column);
    emit this->itemSelected(item->text(0));
}

void AnomalieMonete::on_reloadButton_clicked()
{
    this->ui->treeWidget->clear();
    this->fillData();
}


