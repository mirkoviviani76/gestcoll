#ifndef COMMONDEFS_H
#define COMMONDEFS_H

#include <QString>
#include <genericitem.h>
#include "bibliotecaxml.h"
#include "commondata.h"
#include <QDate>
#include <QDebug>

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
struct Nominale
{
        QString valuta;
        QString valore;
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

class Nota : public GenericItem
{
    public:
        QString testo;
        virtual ~Nota() {}
        Nota(QString t="") {this->testo = t;}
        inline QString toString(int column=-1)  {Q_UNUSED(column); return testo; }
        inline QString toTooltip() {return ""; }
        inline QImage toImg() { return QImage(); }
        inline QColor getColor() { return QColor(QColor::Invalid);}
};

class Libro : public GenericItem
{
    public:
        QString sigla;
        QString numero;
        virtual ~Libro() {}
        Libro(QString a = "", QString b = "") {sigla = a; numero = b;}
        QString toString(int column=-1);
        inline QString toTooltip() {
            BibliotecaItem* item = BibliotecaXml::getInstance()->getItem(sigla);
            if (item != NULL) {
                return item->toTooltip();
            }
            else {
                return "";
            }
        }
        inline QImage toImg() { return QImage(); }
        inline QColor getColor() { return QColor(QColor::Invalid);}
};
class Documento : public GenericItem
{
    public:
        QString filename;
        QString descrizione;
        virtual ~Documento() {}
        Documento(QString _filename = "", QString _descrizione = "");
        QString toString(int column=-1);
        QString toTooltip();
        inline QImage toImg() { return QImage(); }
        inline QColor getColor() { return QColor(QColor::Invalid);}
};
class Contatto : public GenericItem
{
    public:
        QString nome;
        QString email;
        QString note;
        virtual ~Contatto() {}
        Contatto(QString _nome, QString _email, QString _note = "") {nome = _nome; email = _email; note = _note;}
        inline QString toString(int column=-1) {Q_UNUSED(column); return QString("%1\t%2\t%3").arg(nome).arg(email).arg(note);}
        inline QString toTooltip() {return QString("%1").arg(note);}
        inline QImage toImg() { return QImage(); }
        inline QColor getColor() { return QColor(QColor::Invalid);}
};


class Ambito  : public GenericItem {
public:
    QString titolo;
    QString icona;
    virtual ~Ambito() {}
    Ambito(QString _titolo, QString _icona);
    QString toString(int column=-1);
    inline QString toTooltip() {return QString("%1").arg(titolo);}
    inline QImage toImg() { return QImage(); }
    inline QColor getColor() { return QColor(QColor::Invalid);}
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


Q_DECLARE_METATYPE(xml::Misura)

}


#endif // COMMONDEFS_H
