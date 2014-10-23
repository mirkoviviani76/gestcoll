#include "texgenerator.h"
#include <utils.h>
#include <QTextStream>
#include <commondata.h>
#include "collezionexml.h"
#include <QDebug>
#include "posizioni.h"
#include "gestlog.h"

namespace {
  bool comparePaese(const QString& p1, const QString& p2) {
      bool ret;
      int v = QString::localeAwareCompare(p1, p2);
      if (v == 0)
          ret = false;
      else if (v > 0)
          ret = false;
      else if (v < 0)
         ret = true;
      return ret;
  }
}

TexGenerator::TexGenerator()
{
}

TexGenerator::~TexGenerator() {
}

bool TexGenerator::convert()
{
    bool ret = false;

    QString outDir = CommonData::getInstance()->getTexDir();
    QString xslt = CommonData::getInstance()->getXml2TexXslt();

    /* ottiene una mappa fra nome del paese e id monete associate */
    QMap<QString, QList<QString> > paesiAndId;
    foreach (QString id, CollezioneXml::getInstance()->getAllId()) {
        MonetaXml* moneta = CollezioneXml::getInstance()->getMoneta(id);
        QString paese = QString::fromStdWString(moneta->getDom()->paese());
        paesiAndId[paese].append(id);
    }

    /* per ogni paese costruisce il "capitolo" giusto */
    QString capitoliPaesi;
    QList<QString> paesiOrdinati = paesiAndId.keys();
    //ordina i paesi
    qSort(paesiOrdinati.begin(), paesiOrdinati.end(), comparePaese);
    foreach (QString paese, paesiOrdinati) {
        QString listaId;
        foreach (QString id, paesiAndId.value(paese)) {
            listaId += QString("\\hyperref[%1]{%1}\n").arg(id);
        }

        capitoliPaesi += QString("\\paese{%1}\n \
                                 \\thispagestyle{empty}\n \
                                 \\sezione{Monete associate}\n \
                                 %2\n\n \
                                 ").arg(paese).arg(listaId);
    }

    QString tabellaPaesi;
    QString inizio("\\paesiEId \n \
                   \\begin{longtable}{| r || l | }\n \
                   \\hline \n \
                   \\textbf{Paese} & \\textbf{IDs}\\\\ \n \
                   \\hline \n \
                   \\hline \n \
                   \\endhead \n");
    tabellaPaesi += inizio;
    foreach (QString paese, paesiOrdinati) {
         QString ids;
         foreach (QString id, paesiAndId.value(paese)) {
             ids += QString("\\hyperref[%1]{%1}; ").arg(id);
         }

         tabellaPaesi +=  QString("%1 \\dotfill & %2 \\\\ \n").arg(paese).arg(ids);
    }

    QString fine("\\hline \n \
                 \\caption{Paesi e Monete} \\\\ \n \
                 \\end{longtable} \n \
                 \\cleardoublepage \n ");

    tabellaPaesi += fine;


    /* converte i singoli tex */
    QMap<QString, QString> conversion;
    conversion["&amp;"] = "\\&";
    conversion["$AMPERSAND$"] = "&";
    conversion["__LISTA_PAESI__"] = capitoliPaesi;
    conversion["__TABELLA_PAESI__"] = tabellaPaesi;


    int curIndex = 0;

    /* converte la collezione in tex */
    QString completeOutFile = outDir+"/"+"Collezione.tex";

    if (QFileInfo::exists(outDir+"/SRI.png")) {
        ret = true;
    } else {
        /* try to copy data */
        ret = QFile::copy(":/img/SRI.png", outDir+"/SRI.png");
        if (!ret) {
            Log::Logger::getInstance()->log("Unable to copy SRI.png", Log::ERR);
            return ret;
        }
    }

    if (ret) {
        QFile out(completeOutFile);
        ret = XsltConverter::convert(CommonData::getInstance()->getCollezione(),
                                     xslt,
                                     &out,
                                     conversion,
                                     CommonData::getInstance()->getImgDir());
    }

    if (!ret) {
        Log::Logger::getInstance()->log("Unable to generate Collezione.tex", Log::ERR);
        return ret;
    }


    if (ret)
    {
        QString etichetteA;
        QString etichetteB;
        QString etichetteC;
        QString etichetteD;
        QString qrA;
        QString qrB;
        QString qrC;
        QString qrD;
        curIndex = 0;

        foreach (QString id, CollezioneXml::getInstance()->getAllId())
        {
            curIndex++;
            MonetaXml* mng = CollezioneXml::getInstance()->getMoneta(id);

            /* ottiene la dimensione della casella */
            QString dimensione = this->getDim(*mng);
            /* ottiene l'etichetta singola */
            QString etichetta = this->getEtichetta(mng->getDom().data(), dimensione);
            /* aggiorna l'elenco globale di etichette e codici qr */
            if (dimensione == "A") {
                etichetteA = etichetteA + etichetta + "\n";
                //qrA = qrA + "\\qrA{"+id+"}" + "\n";
            }
            if (dimensione == "B") {
                etichetteB = etichetteB + etichetta + "\n";
                //qrB = qrB + "\\qrB{"+id+"}" + "\n";
            }
            if (dimensione == "C") {
                etichetteC = etichetteC + etichetta + "\n";
                //qrC = qrC + "\\qrC{"+id+"}" + "\n";
            }
            if (dimensione == "D") {
                etichetteD = etichetteD + etichetta + "\n";
                //qrD = qrD + "\\qrD{"+id+"}" + "\n";
            }

            if (ret == false)
            {
                break;
            }
        }

        /* scrive il file etichette.tex */
        QString etichetteTemplate = CommonData::getInstance()->getEtichetteTemplate();
        QString outFile = QString("%1/%2")
                .arg(outDir)
                .arg("etichette.tex");
        QMap<QString, QString> conv;
        conv["%ETICHETTEA"] = etichetteA;
        conv["%ETICHETTEB"] = etichetteB;
        conv["%ETICHETTEC"] = etichetteC;
        conv["%ETICHETTED"] = etichetteD;
        conv["%QRA"] = qrA;
        conv["%QRB"] = qrB;
        conv["%QRC"] = qrC;
        conv["%QRD"] = qrD;

        ret = Utils::replaceInFile(etichetteTemplate, outFile, conv);
        if (!ret) {
            Log::Logger::getInstance()->log("TexGenerator: Errore nella generazione di Etichette.tex", Log::ERR);
        }
    }

    if (!ret) {
        Log::Logger::getInstance()->log("Unable to generate etichette.tex", Log::ERR);
        return ret;
    }


    return ret;

}

QString TexGenerator::getDim(const MonetaXml& item)
{
    Posizioni posizioni;
    //ottiene il vassoio in cui si trova la moneta
    VassoioXml* vassoio = posizioni.getVassoio("SRI", QString("%1").arg(item.getPosizione().getContenitore()),
                 QString("%1").arg(item.getPosizione().getContenitore())
                 );
    //ritorna la dimensione
    return vassoio->getDimensione();
}


/**
 * Ottiene l'etichetta della singola moneta
 * @param xml il file di moneta
 * @param dimensione la dimensione della casella
 */
QString TexGenerator::getEtichetta(::gestColl::coins::moneta* xml, QString dimensione)
{
    QString out = "";

    QString id = QString::fromStdWString(xml->id());
    QString paese = QString::fromStdWString(xml->paese());
    QStringList elencoAutorita;
    for (::gestColl::coins::moneta::autorita_type::nome_iterator it = xml->autorita().nome().begin();
         it != xml->autorita().nome().end(); ++it)
    {
        elencoAutorita.append(QString::fromStdWString(*it));
    }
    QString autorita = elencoAutorita.join("; ");

    QString zeccaNome = "";
    QString zeccaSegno = "";
    if (xml->zecca().nome().present()) {
        zeccaNome = QString::fromStdWString(xml->zecca().nome().get());
    }
    if (xml->zecca().segno().present()) {
        zeccaSegno = QString::fromStdWString(xml->zecca().segno().get());
    }

    QString zecca = QString("%1 %2")
                     .arg(zeccaNome)
                     .arg(zeccaSegno);

    //serve almeno uno spazio per il latex
    if (autorita == "")
        autorita = " ";

    if (zecca != " " && autorita != " ") {
        zecca = "\\\\" + zecca;
    }

    QString valore = QString("%1 %2")
            .arg(QString::fromStdWString(xml->nominale().valore()))
            .arg(QString::fromStdWString(xml->nominale().valuta()));
    QString anno = QString::fromStdWString(xml->anno());

    QString nominale = valore + " " + anno;
    /* compone l'etichetta */
    out = "\\casella" + dimensione + "{" + paese + "}{" + autorita + "}{" + zecca + "}{" + nominale + "}{" + id + "}";

    return out;

}


