#include "texgenerator.h"
#include <utils.h>
#include <QTextStream>
#include <commondata.h>
#include "collezionexml.h"
#include <QDebug>
#include "posizioni.h"
#include "gestlog.h"

TexGenerator::TexGenerator()
{
    posizioni = new Posizioni();
}

TexGenerator::~TexGenerator() {
    if (this->posizioni != NULL) {
        delete this->posizioni;
        this->posizioni = NULL;
    }
}

bool TexGenerator::convert()
{
    bool ret = false;

    QString outDir = CommonData::getInstance()->getTexDir();
    QString xslt = CommonData::getInstance()->getXml2TexXslt();

    /* converte i singoli tex */
    QMap<QString, QString> conversion;
    conversion["&amp;"] = "\\&";
    conversion["$AMPERSAND$"] = "&";


    int curIndex = 0;

    /* converte la collezione in tex */
    QString completeOutFile = outDir+"/"+"Collezione.tex";

    ret = QFile::copy(":/img/SRI.png", outDir+"/SRI.png");
    if (!ret) {
        Log::Logger::getInstance()->log("Unable to copy SRI.png", Log::ERR);
        return ret;
    }

    if (ret) {
        QFile out(completeOutFile);
        ret = XsltConverter::convertAll(CommonData::getInstance()->getCollezione(), xslt, &out, conversion);
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
            QString etichetta = this->getEtichetta(mng, dimensione);
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
    //ottiene il vassoio in cui si trova la moneta
    VassoioXml* vassoio = this->posizioni->getVassoio("SRI", QString("%1").arg(item.getContenitore()),
                 QString("%1").arg(item.getVassoio())
                 );
    //ritorna la dimensione
    return vassoio->getDimensione();
}


/**
 * Ottiene l'etichetta della singola moneta
 * @param xml il file di moneta
 * @param dimensione la dimensione della casella
 */
QString TexGenerator::getEtichetta(MonetaXml* xml, QString dimensione)
{
    QString out = "";

    QString id = xml->getId();
    QString paese = xml->getPaese();
    QString autorita;

    for (int i = 0; i < xml->getAutorita().count(); i++)
    {
        if (i!=0)
        {
            autorita = autorita.append("; ");
        }
        xml->getAutorita().at(i);

        autorita = autorita.append(xml->getAutorita().at(i)->nome);
    }

    QString zecca = QString("%1 %2")
            .arg(xml->getZecca().nome)
            .arg(xml->getZecca().segno);

    //serve almeno uno spazio per il latex
    if (autorita == "")
        autorita = " ";

    if (zecca != " " && autorita != " ") {
        zecca = "\\\\" + zecca;
    }

    QString valore = QString("%1 %2")
            .arg(xml->getNominale().valore)
            .arg(xml->getNominale().valuta);
    QString anno = xml->getAnno();
    QString nominale = valore + " " + anno;
    /* compone l'etichetta */
    out = "\\casella" + dimensione + "{" + paese + "}{" + autorita + "}{" + zecca + "}{" + nominale + "}{" + id + "}";

    return out;

}


