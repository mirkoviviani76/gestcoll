#include "monetaxml.h"

#include <QTextStream>
#include <QStringList>
#include <QSplashScreen>
#include <QApplication>
#include <QDebug>
#include <QFileInfo>
#include <scheda.hxx>
#include <commondata.h>
#include <iostream>
#include <fstream>
#include <QMutex>
#include <QDate>
#include <QPainter>
#include <QDateTime>
#include "gestlog.h"
#include "collezionexml.h"
extern QSplashScreen* splash;

Posizione::Posizione(const moneta::posizione_type &pos)
    : moneta::posizione_type(pos)
{
}

QString Posizione::getIdVassoio() const
{
   return QString("%1-%2")
           .arg(this->getContenitore())
           .arg(this->getVassoio());
}

int Posizione::getContenitore()  const
{
    return this->contenitore();
}

int Posizione::getVassoio() const
{
    return this->vassoio();
}

Casella Posizione::getCasella() const {
    return Casella(this->riga(), this->colonna());
}

Casella::Casella(int r, int c) :
    riga(r), colonna(c)
{
}

QString Casella::toString(const QString& separator) {
    return QString("%1%2%3")
            .arg(this->getRiga())
            .arg(separator)
            .arg(this->getColonna());
}

int Casella::getRiga() const
{
    return this->riga;
}

int Casella::getColonna() const
{
    return this->colonna;
}

QString Posizione::toString(const QString &separator)
{
    QStringList s;
    s << QString::number(getContenitore())
      << QString::number(getVassoio())
      << this->getCasella().toString(separator);
    return s.join(separator);

}




/**
 * @brief Costruttore.
 * Legge il file xml e lo carica in memoria
 *
 * @param _file il file xml
*/
MonetaXml::MonetaXml(const moneta& m, QObject* parent) :
    QObject(parent)
{
    this->mon = new moneta(m);
    //this->setOrdering(Moneta::BY_ID);
    this->updateImage();
    this->fillAmbiti();
}

MonetaXml::MonetaXml(moneta *m, QObject *parent) :
    QObject(parent), mon(m)
{
    //this->setOrdering(Moneta::BY_ID);
    this->updateImage();
    this->fillAmbiti();
}


/**
 * @brief Distruttore
 *
*/
MonetaXml::~MonetaXml()
{
    this->clear();
    if (this->image != NULL) {
        delete this->image;
        this->image = NULL;
    }
    deleteAmbitiList();

}



void MonetaXml::deleteAmbitiList() {
    foreach (xml::Ambito* a, xmlAmbiti) {
        if (a != NULL) {
            delete a;
            a = NULL;
        }
    }
    this->xmlAmbiti.clear();
}

QList<xml::Ambito*> MonetaXml::getAmbiti() {
    return this->xmlAmbiti;
}

Posizione MonetaXml::getPosizione() const
{
    if (this->mon->posizione().present()) {
        return Posizione(this->mon->posizione().get());
    } else {
        ::gestColl::coins::moneta::posizione_type t(0, 0, 0, 0);
        return Posizione(t);
    }
}



/**
 * @brief non fa niente...
 *
*/
void MonetaXml::clear()
{
}

/**
  Restituisce l'id del vassoio, come stringa composta da "idcontenitore-idvassoio"
*/

/**
  Restituisce una stringa rappresentativa della moneta.
*/
QString MonetaXml::toString()
{
    QString valore = QString::fromStdWString(this->getDom()->nominale().valore());
    QString valuta = QString::fromStdWString(this->getDom()->nominale().valuta());
    QString label = QString("%1 - %2 %3")
            .arg(QString::fromStdWString(this->getDom()->paese()))
            .arg(valore)
            .arg(valuta);

    QString completeLabel = QString("%1: %2")
            .arg(this->getId())
            .arg(label);

    return completeLabel;

}


/**
  Restituisce un tooltip per la moneta
  */
QString MonetaXml::toTooltip()
{
    QStringList ambitiAsStrings;
    foreach(xml::Ambito* a, this->getAmbiti()) {
        ambitiAsStrings << a->titolo;
    }
    ambitiAsStrings.sort();
    QString ambiti = "";
    foreach(QString c, ambitiAsStrings) {
        ambiti += QString("<li>%1</li>").arg(c);
    }

    QString data = QString("<h2>Ultima revisione:</h2> %1/%2/%3 (%4:%5:%6)"\
                           "<h2>Ambiti:</h2><ul>%7</ul>")
            .arg(mon->revisione().day())
            .arg(mon->revisione().month())
            .arg(mon->revisione().year())
            .arg(mon->revisione().hours())
            .arg(mon->revisione().minutes())
            .arg(mon->revisione().seconds())
            .arg(ambiti);
    return data;
}

/**
  Ottiene l'icona della moneta
  */
QImage MonetaXml::toImg() {
    return *(this->image);
}

void MonetaXml::updateImage() {
    image = new QImage(16,16, QImage::Format_ARGB32);
    QPainter painter(image);
    QPen drawPen(Qt::black, 1);
    xml::Stato stato = this->getStato();
    QString color = stato.colore;
    painter.setPen(drawPen);
    painter.setBrush(QColor(color));
    painter.drawEllipse(QPoint(8,8), 6,6);

}

/**
  Ottiene l'id della moneta
*/
QString MonetaXml::getId() const
{
    return QString::fromStdWString(this->mon->id());
}




void MonetaXml::fillAmbiti()
{
    this->deleteAmbitiList();
    moneta::ambiti_optional zopt = this->mon->ambiti();
    if (zopt.present())
    {
        moneta::ambiti_type ztt = zopt.get();
        ::ambiti::ambito_sequence seq = ztt.ambito();
        for (::ambiti::ambito_iterator it(seq.begin());
        it != seq.end(); ++it
                )
        {
            ::ambiti::ambito_type curAmbito = (*it);
            QString titolo = QString::fromStdWString(curAmbito.titolo());
            QString icona = "";
            if (curAmbito.icon().present()) {
                icona = QString::fromStdWString(curAmbito.icon().get());
            }
            xml::Ambito* z = new xml::Ambito(titolo, icona);
            this->xmlAmbiti.append(z);
        }

    }

}

xml::Stato MonetaXml::getStato() {
    gestColl::coins::stato s = this->mon->stato();
    QString c = QString::fromStdWString(s.colore());
    QString m = QString::fromStdWString(s.motivo());
    return xml::Stato(c, m);
}

void MonetaXml::setStato(xml::Stato& nuovo) {
    gestColl::coins::stato s(nuovo.colore.toStdWString(), nuovo.motivo.toStdWString());
    this->mon->stato(s);
    this->updateImage();
}


/**
  Sistema la data della revisione a oggi
  */
void MonetaXml::updateRevision() {
    /* sistema la revisione */
    QDateTime today = QDateTime::currentDateTime();
    ::moneta::revisione_type revisione(today.date().year(),
                                       today.date().month(),
                                       today.date().day(),
                                       today.time().hour(),
                                       today.time().minute(),
                                       today.time().second());
    this->mon->revisione(revisione);
}



void MonetaXml::setPosizione(int cont, int vass, int r, int c)
{
    moneta::posizione_type pos = mon->posizione().get();
    pos.contenitore(cont);
    pos.vassoio(vass);
    pos.riga(r);
    pos.colonna(c);
    mon->posizione(pos);
}


void MonetaXml::setAmbiti(QList<xml::Ambito*> ambiti) {
    gestColl::coins::moneta::ambiti_type let;
    foreach(xml::Ambito* a, ambiti) {
        ::gestColl::coins::ambito curAmbito(a->titolo.toStdWString());
        //TODO verificare se va messa anche l'icona, forse no.
        let.ambito().push_back(curAmbito);
    }
    this->mon->ambiti(let);
    this->fillAmbiti();
}


bool MonetaXml::updateAmbiti(const xml::Ambito& vecchio, const xml::Ambito& nuovo) {

    bool ret = false;
    QList<xml::Ambito*> correnti;
    foreach(xml::Ambito* a, this->getAmbiti()) {
        //viene controllato solo il titolo.
        //TODO verifica
        if ((a->titolo == vecchio.titolo)) {
            a->titolo = nuovo.titolo;
            correnti.append(a);
            ret = true;
        }
    }

    /* aggiorna se c'e' la modifica */
    if (ret == true) {
        this->setAmbiti(correnti);
    }

    return ret;
}

