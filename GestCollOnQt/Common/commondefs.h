#ifndef COMMONDEFS_H
#define COMMONDEFS_H

#include <QString>
#include "commondata.h"
#include "bibliotecaxml.h"
#include <QDate>
#include <QDebug>
#include "scheda.hxx"

#if 0
struct Asta
{
        QString casa;
        QString idAsta;
        QString data;
        QString lotto;
        Misura stima;
        Misura aggiudicazione;
        int indexXml;
};
#endif

namespace xml
{


struct Emissione {
    gestColl::coins::moneta::nominale_type *nominale;
    gestColl::coins::moneta::paese_type* paese;
    gestColl::coins::moneta::anno_type* anno;
    gestColl::coins::autorita* autorita;

};

class Misura
{
    public:
        QString unita;
        qreal valore;
        virtual ~Misura() {}
        Misura(QString t="", qreal val=0) {this->unita = t; this->valore = val;}
};

class Stato
{
    public:
        QString colore;
        QString motivo;
        virtual ~Stato() {}
        Stato(QString t="", QString val="") {this->colore = t; this->motivo = val;}
};



class Ambito {
public:
    QString titolo;
    QString icona;
    virtual ~Ambito() {}
    Ambito(QString _titolo, QString _icona);
    inline QString toTooltip() {return QString("%1").arg(titolo);}
};

class Info {
public:
    QString titolo;
    QString proprietario;
    QDate inizio;
    QList<Ambito*> ambiti;
    virtual ~Info() {
        foreach(Ambito* a, ambiti) {
            if (a != NULL) {
                delete a;
                a = NULL;
            }
        }
    }
    void add(Ambito* a) {
        this->ambiti.append(a);
    }
};

} //namespace

class Libro
{
    public:
        QString sigla;
        QString numero;
        virtual ~Libro() {}
        Libro(QString a = "", QString b = "") {sigla = a; numero = b;}
        QString toString(int column=-1);
        QString toTooltip();
        //inline QColor getColor() { return QColor(QColor::Invalid);}

        // Pubblicazione interface
public:
        bool lessThan(Libro *due);
};


Q_DECLARE_METATYPE(xml::Misura)

#endif // COMMONDEFS_H
