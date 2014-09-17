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


/**
 * @brief Costruttore.
 * Legge il file xml e lo carica in memoria
 *
 * @param _file il file xml
*/
MonetaXml::MonetaXml(const moneta& m)
{
    this->mon = new moneta(m);
    //this->setOrdering(Moneta::BY_ID);
    this->updateImage();
    this->fillAmbiti();
    this->fillItemAddizionali();
}

MonetaXml::MonetaXml(moneta *m) {
    this->mon = m;
    //this->setOrdering(Moneta::BY_ID);
    this->updateImage();
    this->fillAmbiti();
    this->fillItemAddizionali();
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
    deleteItemAddizionaliList();
    deleteAmbitiList();

}


void MonetaXml::deleteItemAddizionaliList() {
    foreach (xml::Documento* a, xmlItemAddizionali) {
        if (a != NULL) {
            delete a;
            a = NULL;
        }
    }
    this->xmlItemAddizionali.clear();

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


QList<xml::Documento*> MonetaXml::getItemAddizionali() {
    return this->xmlItemAddizionali;
}

QList<xml::Ambito*> MonetaXml::getAmbiti() {
    return this->xmlAmbiti;
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
QString MonetaXml::getIdVassoio() const
{
   return QString("%1-%2")
           .arg(this->getContenitore())
           .arg(this->getVassoio());
}

/**
  Restituisce una stringa rappresentativa della moneta.
*/
QString MonetaXml::toString(int column)
{
    switch (column) {
    case 0:
        {
            return this->getId();
        }
        break;
    case 1:
        {
            return QString::fromStdWString(this->getDom()->paese());
        }
        break;
    case 2:
        {
            return QString("%1 %2").arg(QString::fromStdWString(this->getDom()->nominale().valore()))
                    .arg(QString::fromStdWString(this->getDom()->nominale().valuta()));
        }
        break;
    case 3:
        {
            return QString::fromStdWString(this->getDom()->anno());
        }
        break;
    case 4:
        {
            QStringList myambiti;
            foreach (xml::Ambito* a, this->getAmbiti()) {
                myambiti << a->titolo;
            }
            return myambiti.join(" | ");
        }
        break;
    default:
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
        break;

    }

    return "";

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



int MonetaXml::getContenitore()  const
{
    moneta::posizione_optional ppp = this->mon->posizione();
    int val = -1;
    if (ppp.present())
    {
        moneta::posizione_type posizione = ppp.get();
        val = posizione.contenitore();
    }
    return val;
}

int MonetaXml::getVassoio() const
{
    moneta::posizione_optional ppp = this->mon->posizione();
    int val = -1;
    if (ppp.present())
    {
        moneta::posizione_type posizione = ppp.get();
        val = posizione.vassoio();
    }
    return val;
}

int MonetaXml::getRiga() const
{
    moneta::posizione_optional ppp = this->mon->posizione();
    int val = -1;
    if (ppp.present())
    {
        moneta::posizione_type posizione = ppp.get();
        val = posizione.riga();
    }
    return val;
}

int MonetaXml::getColonna() const
{
    moneta::posizione_optional ppp = this->mon->posizione();
    int val = -1;
    if (ppp.present())
    {
        moneta::posizione_type posizione = ppp.get();
        val = posizione.colonna();
    }
    return val;

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


void MonetaXml::fillItemAddizionali()
{
    this->deleteItemAddizionaliList();
    moneta::itemAddizionali_optional zopt = this->mon->itemAddizionali();
    if (zopt.present())
    {
        moneta::itemAddizionali_type ztt = zopt.get();

        ::documentiAggiuntivi::documento_sequence seq = ztt.documento();
        for (::documentiAggiuntivi::documento_iterator it(seq.begin());
        it != seq.end(); ++it
                )
        {
            ::documentiAggiuntivi::documento_type cur = (*it);

            xml::Documento* z = new xml::Documento(QString::fromStdWString(cur.filename()),
                                           QString::fromStdWString(cur.descrizione())
                                           );
            this->xmlItemAddizionali.append(z);
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


void MonetaXml::setDocumento(const xml::Documento& vecchio, const xml::Documento& nuovo)
{
    bool done = false;
    moneta::itemAddizionali_type let = mon->itemAddizionali().get();

    for (moneta::itemAddizionali_type::documento_iterator it = let.documento().begin();
         it != let.documento().end() && !done;
         ++it)
    {
        //cerca l'item "giusto"
        moneta::itemAddizionali_type::documento_type cur = *it;
        QString curFilename = QString::fromStdWString(cur.filename());
        QString curDescrizione = QString::fromStdWString(cur.descrizione());
        if ((curFilename == vecchio.filename) &&
                (curDescrizione == vecchio.descrizione))
        {
            /* trovato: effettua le modifiche */
            (*it).filename(nuovo.filename.toStdWString());
            (*it).descrizione(nuovo.descrizione.toStdWString());
            done = true;
        }
    }

    //salva le modifiche nel dom
    mon->itemAddizionali(let);
    this->fillItemAddizionali();

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

void MonetaXml::addDocumento(const xml::Documento& l)
{
    moneta::itemAddizionali_type::documento_type doc(l.filename.toStdWString(),
                                                     l.descrizione.toStdWString());
    moneta::itemAddizionali_type let;
    if (this->mon->itemAddizionali().present())
        let = mon->itemAddizionali().get();
    let.documento().push_back(doc);
    this->mon->itemAddizionali().set(let);
    this->fillItemAddizionali();

}


void MonetaXml::deleteDocumento(xml::Documento* l)
{
    moneta::itemAddizionali_type::documento_type doc(
                l->filename.toStdWString(),
                l->descrizione.toStdWString());
    moneta::itemAddizionali_type let = mon->itemAddizionali().get();

    for (unsigned int i = 0; i < let.documento().size(); i++) {
        moneta::itemAddizionali_type::documento_type cur = let.documento().at(i);
        QString curFilename = QString::fromStdWString(cur.filename());
        QString curDescrizione = QString::fromStdWString(cur.descrizione());
        if ((curFilename == l->filename) &&
            (curDescrizione == l->descrizione))
        {
            let.documento().erase(let.documento().begin()+i);
            break;
        }
    }

    this->mon->itemAddizionali().set(let);
    this->fillItemAddizionali();

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

