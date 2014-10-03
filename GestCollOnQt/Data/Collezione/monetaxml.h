#ifndef MONETAXML_H
#define MONETAXML_H

#include <commondefs.h>
#include <genericitem.h>
#include <QString>
#include <QFile>
#include <QList>
#include <QDate>
#include <QFileInfo>
#include <scheda.hxx>
#include "vassoioxml.h"

using namespace gestColl::coins;


class Posizione : moneta::posizione_type {
public:
    Posizione(const moneta::posizione_type& pos);
    QString getIdVassoio() const;
    int getContenitore() const;
    int getVassoio() const;
    int getRiga() const;
    int getColonna() const;
    QString toString(const QString& separator);
};


class MonetaXml: public QObject
{
    Q_OBJECT
public:
    MonetaXml(const moneta& m, QObject* parent = 0);
    MonetaXml(moneta* m, QObject* parent = 0);
    virtual ~MonetaXml();
    inline QColor getColor() { return QColor::Invalid;}
    QString toString();
    QString toTooltip();
    QImage toImg();
    QString getId() const;
    xml::Stato getStato();
    QList<xml::Ambito*> getAmbiti();
    Posizione getPosizione() const;

    void updateRevision();
    bool updateAmbiti(const xml::Ambito& vecchio, const xml::Ambito& nuovo);

    void setStato(xml::Stato& nuovo);
    void setPosizione(int cont, int vass, int r, int c);
    void setAmbiti(QList<xml::Ambito*> ambiti);

    moneta* getDom() {return this->mon;}

    void clear();

private:
    //Moneta::MonetaOrdering sortingType;
    moneta* mon;
    QImage* image;
    void updateImage();
    QList<xml::Ambito*> xmlAmbiti;

    void deleteAmbitiList();

    void fillAmbiti();

};

#endif // MONETAXML_H
