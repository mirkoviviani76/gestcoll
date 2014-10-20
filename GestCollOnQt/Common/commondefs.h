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
    Ambito(QString _titolo, QString _icona);
    virtual ~Ambito() {}
    inline QString getTitolo() const {return titolo;}
    inline QString getIcona() const {return icona;}
    inline void setTitolo(const QString& _t) {titolo = _t;}
    inline void setIcona(const QString& _i) {icona = _i;}
private:
    QString titolo;
    QString icona;

};

class InfoCollezione {
public:
    QString titolo;
    QString proprietario;
    QDate inizio;
    QList<Ambito*> ambiti;
    virtual ~InfoCollezione() {
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


#endif // COMMONDEFS_H
