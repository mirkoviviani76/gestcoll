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
    this->fillLetteratura();
    this->fillNote();
    this->fillZecchieri();
}

MonetaXml::MonetaXml(moneta *m) {
    this->mon = m;
    //this->setOrdering(Moneta::BY_ID);
    this->updateImage();
    this->fillAmbiti();
    this->fillItemAddizionali();
    this->fillLetteratura();
    this->fillNote();
    this->fillZecchieri();
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
    deleteNoteList();
    deleteZecchieriList();
    deleteLetteraturaList();
    deleteItemAddizionaliList();
    deleteAmbitiList();

}


void MonetaXml::deleteNoteList() {
    foreach (xml::Nota* a, xmlNote) {
        if (a != NULL) {
            delete a;
            a = NULL;
        }
    }
    this->xmlNote.clear();
}


void MonetaXml::deleteZecchieriList() {
    foreach (xml::Zecchiere* a, xmlZecchieri) {
        if (a != NULL) {
            delete a;
            a = NULL;
        }
    }
    this->xmlZecchieri.clear();

}
void MonetaXml::deleteLetteraturaList() {
    foreach (xml::Libro* a, xmlLetteratura) {
        if (a != NULL) {
            delete a;
            a = NULL;
        }
    }
    this->xmlLetteratura.clear();

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


QList<xml::Nota*> MonetaXml::getNote() {
    return this->xmlNote;
}

QList<xml::Zecchiere*> MonetaXml::getZecchieri() {
    return this->xmlZecchieri;
}

QList<xml::Libro*> MonetaXml::getLetteratura() {
    return this->xmlLetteratura;
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
            return this->getPaese();
        }
        break;
    case 2:
        {
            xml::Nominale nominale = this->getNominale();
            return QString("%1 %2").arg(nominale.valore).arg(nominale.valuta);
        }
        break;
    case 3:
        {
            return this->getAnno();
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
            xml::Nominale nominale = this->getNominale();
            QString label = QString("%1 - %2 %3")
                            .arg(this->getPaese())
                            .arg(nominale.valore)
                            .arg(nominale.valuta);

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

QString MonetaXml::getPaese()
{
    return QString::fromStdWString(this->mon->paese());
}

QString MonetaXml::getAnno()
{
    moneta::anno_optional ppp = this->mon->anno();
    QString val = "";
    if (ppp.present())
    {
        moneta::anno_type anno = ppp.get();
        val = QString::fromStdWString(anno);
    }
    return val;
}

xml::Nominale MonetaXml::getNominale()
{
    xml::Nominale n;
    n.valore = QString::fromStdWString(this->mon->nominale().valore());
    n.valuta = QString::fromStdWString(this->mon->nominale().valuta());
    return n;
}

xml::Zecca MonetaXml::getZecca()
{
    moneta::zecca_optional ppp = this->mon->zecca();
    moneta::zecca_type z;
    xml::Zecca zec;
    if (ppp.present())
    {
        z = ppp.get();
        moneta::zecca_type::nome_optional nomeopt = z.nome();
        moneta::zecca_type::segno_optional segnoopt = z.segno();
        if (nomeopt.present())
            zec.nome = QString::fromStdWString(nomeopt.get());
        if (segnoopt.present())
            zec.segno = QString::fromStdWString(segnoopt.get());
    }
    return zec;

}


xml::Misura MonetaXml::getPeso()
{
    xml::Misura m;
    m.unita = QString::fromStdWString(this->mon->datiFisici().peso().unita());
    m.valore = this->mon->datiFisici().peso().valore();
    return m;
}


QString MonetaXml::getMetallo()
{
    return QString::fromStdWString(this->mon->datiFisici().metallo());
}

void MonetaXml::fillNote()
{
    this->deleteNoteList();
    moneta::note_optional nop = this->mon->note();
    if (nop.present())
    {
        moneta::note_type nt = nop.get();
        for (moneta::note_type::nota_iterator it(nt.nota().begin());
            it != nt.nota().end();
            ++it
            )
        {
            xml::Nota* n = new xml::Nota(QString::fromStdWString((*it)));
            this->xmlNote.append(n);
        }
    }

}


void MonetaXml::fillZecchieri()
{
    this->deleteZecchieriList();
    moneta::zecchieri_optional zopt = this->mon->zecchieri();
    if (zopt.present())
    {
        moneta::zecchieri_type ztt = zopt.get();
        moneta::zecchieri_type::zecchiere_sequence seq = ztt.zecchiere();
        for (moneta::zecchieri_type::zecchiere_iterator it(seq.begin());
        it != seq.end(); ++it
                )
        {
            ::zecchieri::zecchiere_type curZec = (*it);
            xml::Zecchiere* z = new xml::Zecchiere();
            if (curZec.nome().present())
                z->nome = QString::fromStdWString(curZec.nome().get());
            if (curZec.ruolo().present())
                z->ruolo = QString::fromStdWString(curZec.ruolo().get());
            if (curZec.segno().present())
                z->segno = QString::fromStdWString(curZec.segno().get());

            this->xmlZecchieri.append(z);
        }

    }

}


void MonetaXml::fillLetteratura()
{
    this->deleteLetteraturaList();
    moneta::letteratura_optional zopt = this->mon->letteratura();
    if (zopt.present())
    {
        moneta::letteratura_type ztt = zopt.get();
        ::letteratura::libro_sequence seq = ztt.libro();
        for (::letteratura::libro_iterator it(seq.begin());
        it != seq.end(); ++it
                )
        {
            ::letteratura::libro_type curLibro = (*it);
            xml::Libro* z = new xml::Libro(QString::fromStdWString(curLibro.sigla()),
                                           QString::fromStdWString(curLibro.numero())
                                           );
            this->xmlLetteratura.append(z);
        }

    }

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

void MonetaXml::setPaese(QString p)
{
    this->mon->paese(p.toStdWString());
}

void MonetaXml::setAnno(QString p)
{
    mon->anno(p.toStdWString());
}

void MonetaXml::setNominale(QString valore, QString unita)
{
    this->mon->nominale().valuta(unita.toStdWString());
    this->mon->nominale().valore(valore.toStdWString());
}

void MonetaXml::setZecca(QString nome, QString segno)
{
    moneta::zecca_type zec;
    zec.nome(nome.toStdWString());
    zec.segno(segno.toStdWString());
    this->mon->zecca().set(zec);
}

void MonetaXml::setLibro(const xml::Libro& vecchio, const xml::Libro& nuovo)
{
    bool done = false;
    moneta::letteratura_type let = mon->letteratura().get();
    for (::letteratura::libro_iterator it = let.libro().begin();
         it != let.libro().end() && !done;
         ++it)
    {
        //cerca l'item "giusto"
        QString curNum = QString::fromStdWString(it->numero());
        QString curSig = QString::fromStdWString(it->sigla());
        if ((curNum == vecchio.numero) && (curSig == vecchio.sigla))
        {
            /* trovato: effettua le modifiche */
            it->numero(nuovo.numero.toStdWString());
            it->sigla(nuovo.sigla.toStdWString());
            done = true;
        }
    }

    //salva le modifiche nel dom
    mon->letteratura(let);
    this->fillLetteratura();
}

void MonetaXml::setNota(const xml::Nota& vecchio, const xml::Nota& nuovo)
{
    bool done = false;
    moneta::note_type let = mon->note().get();
    for (::note::nota_iterator it = let.nota().begin();
         it != let.nota().end() && !done;
         ++it)
    {
        //cerca l'item "giusto"
        QString cur = QString::fromStdWString(*it);
        if (cur == vecchio.testo)
        {
            /* trovato: effettua le modifiche */
            *it = nuovo.testo.toStdWString();
            done = true;
        }
    }

    //salva le modifiche nel dom
    mon->note(let);
    this->fillNote();

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


void MonetaXml::setZecchiere(const xml::Zecchiere& vecchio, const xml::Zecchiere& nuovo)
{

    bool done = false;
    moneta::zecchieri_type let = mon->zecchieri().get();

    for (::zecchieri::zecchiere_iterator it = let.zecchiere().begin();
         it != let.zecchiere().end() && !done;
         ++it)
    {
        //cerca l'item "giusto"
        QString curNome = "";
        QString curSegno = "";
        QString curRuolo = "";
        if (it->nome().present())
            curNome = QString::fromStdWString(it->nome().get());
        if (it->segno().present())
            curSegno = QString::fromStdWString(it->segno().get());
        if (it->ruolo().present())
            curRuolo = QString::fromStdWString(it->ruolo().get());

        if ((curNome == vecchio.nome) && (curSegno == vecchio.segno) && (curRuolo == vecchio.ruolo))
        {
            /* trovato: effettua le modifiche */
            it->nome(nuovo.nome.toStdWString());
            it->segno(nuovo.segno.toStdWString());
            it->ruolo(nuovo.ruolo.toStdWString());
            done = true;
        }
    }

    //salva le modifiche nel dom
    mon->zecchieri(let);
    this->fillZecchieri();
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

void MonetaXml::addLibro(const xml::Libro& l)
{
    moneta::letteratura_type::libro_type libro(l.sigla.toStdWString(), l.numero.toStdWString());
    moneta::letteratura_type let = mon->letteratura().get();
    let.libro().push_back(libro);
    this->mon->letteratura().set(let);
    this->fillLetteratura();
}

void MonetaXml::addNota(const xml::Nota& l)
{
    moneta::note_type::nota_type nota(l.testo.toStdWString());
    moneta::note_type let = mon->note().get();
    let.nota().push_back(nota);
    this->mon->note().set(let);
    this->fillNote();
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


void MonetaXml::deleteNota(xml::Nota* l)
{
    moneta::note_type::nota_type nota(l->testo.toStdWString());
    moneta::note_type let = mon->note().get();

    for (unsigned int i = 0; i < let.nota().size(); i++) {
        if (let.nota().at(i) == nota) {
            let.nota().erase(let.nota().begin()+i);
            break;
        }
    }

    this->mon->note().set(let);
    this->fillNote();

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


void MonetaXml::deleteLetteratura(xml::Libro* l)
{
    if (mon->letteratura().present()) {
        moneta::letteratura_type::libro_sequence letteratura = mon->letteratura().get().libro();

        for (unsigned int i = 0; i < letteratura.size(); i++) {
            moneta::letteratura_type::libro_type curLibro = letteratura.at(i);
            QString curSigla = QString::fromStdWString(curLibro.sigla());
            QString curNum = QString::fromStdWString(curLibro.numero());
            if (curSigla == l->sigla && curNum == l->numero) {
                letteratura.erase(letteratura.begin()+i);
                break;
            }
        }

        mon->letteratura().get().libro(letteratura);
        this->fillLetteratura();
    }

}





void MonetaXml::addZecchiere(const xml::Zecchiere& l)
{
    ::zecchieri::zecchiere_type zec;
    zec.nome(l.nome.toStdWString());
    zec.ruolo(l.ruolo.toStdWString());
    zec.segno(l.segno.toStdWString());

    moneta::zecchieri_type let;
    if (this->mon->zecchieri().present())
        let = mon->zecchieri().get();
    let.zecchiere().push_back(zec);
    this->mon->zecchieri().set(let);
    this->fillZecchieri();
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

