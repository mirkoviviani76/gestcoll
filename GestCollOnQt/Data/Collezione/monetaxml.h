#ifndef MONETAXML_H
#define MONETAXML_H

#include <commondefs.h>
#include <QString>
#include <QFile>
#include <QList>
#include <QDate>
#include <QFileInfo>
#include <scheda.hxx>
#include <QSharedPointer>
#include <QColor>
#include "vassoioxml.h"

using namespace gestColl::coins;

class Casella {
public:
    Casella(int r, int c);
    int getRiga() const;
    int getColonna() const;
    QString toString(const QString& separator);

private:
    int riga;
    int colonna;
};

class Posizione : moneta::posizione_type {
public:
    Posizione(const moneta::posizione_type& pos);
    QString getIdVassoio() const;
    Casella getCasella() const;
    int getContenitore() const;
    int getVassoio() const;
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
    QString toTooltip();
    QString getId() const;
    xml::Stato getStato();
    QList<xml::Ambito*> getAmbiti();
    Posizione getPosizione() const;

    void updateRevision();
    bool updateAmbiti(const xml::Ambito& vecchio, const xml::Ambito& nuovo);

    void setStato(xml::Stato& nuovo);
    void setPosizione(int cont, int vass, int r, int c);
    void setAmbiti(QList<xml::Ambito*> ambiti);

    QImage* getIcona() {return icona;}
#if 0
    QImage getImmagineComposita();
#endif

    QSharedPointer<moneta> getDom() {return this->mon;}

private:
    //Moneta::MonetaOrdering sortingType;
    QSharedPointer<moneta> mon;
    QImage* icona;
    void aggiornaIcona();
    QList<xml::Ambito*> xmlAmbiti;

    void deleteAmbitiList();

    void fillAmbiti();

};

#endif // MONETAXML_H
