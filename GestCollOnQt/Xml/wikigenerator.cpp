#include "wikigenerator.h"
#include "collezionexml.h"

#include <QClipboard>
#include <QMessageBox>
#include <QApplication>

WikiGenerator::WikiGenerator()
{
}

WikiGenerator::~WikiGenerator()
{

}

bool WikiGenerator::convert()
{
    bool ret = true;

    QStringList wikiList;

    foreach (QString id, CollezioneXml::getInstance()->getAllId()) {
        MonetaXml* m = CollezioneXml::getInstance()->getMoneta(id);
        QString item = QString(" * %1 -- %2 %3 %4 ([[%5]])")
                .arg(QString::fromStdWString(m->getDom()->paese()))
                .arg(QString::fromStdWString(m->getDom()->nominale().valore()))
                .arg(QString::fromStdWString(m->getDom()->nominale().valuta()))
                .arg(QString::fromStdWString(m->getDom()->anno()))
                .arg(id);
        wikiList << item;
    }
    qSort(wikiList);


    QClipboard *clipboard = QApplication::clipboard();
    clipboard->setText(wikiList.join("\n"));

    QMessageBox::information(0, "WikiGenerator", "I dati sono negli appunti");

    return ret;

}

