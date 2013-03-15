#ifndef COMMONDATA_H
#define COMMONDATA_H

#include <QSettings>
#include <commondefs.h>
#include "gestlog.h"

#define INI_FILE ("GestColl.ini")
#define VERSIONE_GESTCOLL ("8.1")


enum Errors {
    INIFILE_NOERR    = 0,
    INIFILE_NOTFOUND = -1,
    INIFILE_WRONG    = -2
};

/**
 * @brief singleton per la gestione dei dati comuni al progetto.
 *
 * contiene i dati comuni al progetto e i metodi necessari
 * per la lettura del file di configurazione
 *
 */
class CommonData
{
    public:
        /**
         * @brief ottiene l'istanza del singleton
         *
         * @return CommonData *
         */
        static CommonData* getInstance();

        void setValue(QString key, QString val);


        /**
         * @brief ottiene l'id dell'applicazione (nome e versione)
         *
         * @return QString l'id
         */
        inline QString getAppId() {return QString("%1 %2")
                    .arg(getAppName())
                    .arg(getVersion());
                                  }

        /**
         * @brief ottiene il nome dell'applicazione
         *
         * @return QString il nome
         */
        inline QString getAppName() {return "GestColl";}

        /**
         * @brief ottiene la versione dell'applicazione
         *
         * @return QString la versione
         */
        inline QString getVersion() {return VERSIONE_GESTCOLL;}

        /**
         * @brief ottiene la directory bin
         *
         * @return QString
         */
        inline QString getBinDir() { return this->getValue("Dirs/binDir");}


        /**
         * @brief ottiene la directory delle immagini
         *
         * @return QString
         */
        inline QString getImgDir() {
            return this->getBinDir()+"/data/img";
        }

        /**
         * @brief ottiene la directory delle immagini
         *
         * @return QString
         */
        inline QString getDocDir() {
            return this->getBinDir()+"/data/documents";
        }

        /**
         * @brief ottiene la directory di report
         *
         * @return QString
         */
        inline QString getReportDir() {
            return this->getValue("Dirs/reportDir");
        }

        /**
         * @brief ottiene la directory delle immagini QR
         *
         * @return QString
         */
        inline QString getQrDir() {
            return this->getReportDir() + "/QR";
        }

        /**
         * @brief ottiene la directory della biblioteca
         *
         * @return QString
         */
        inline QString getBiblioDir() {
            return this->getDocDir()+"/biblioteca";
        }

        /**
         * @brief ottiene la directory del report html
         *
         * @return QString
         */
        inline QString getHtmlDir() {
            return this->getReportDir() + "/Html";
        }

        /**
         * @brief ottiene la directory del blocknotes
         *
         * @return QString
         */
        inline QString getBlocknotesDir() {
            return this->getBinDir() + "/blocknotes";
        }

        /**
         * @brief ottiene la directory del report tex-pdf
         *
         * @return QString
         */
        inline QString getTexDir() {
            return this->getReportDir() + "/LaTeX";
        }

        /**
         * @brief ottiene la posizione del file xml della collezione
         *
         * @return QString
         */
        inline QString getCollezione() {
            return this->getBinDir() + "/data/" + this->getValue("Files/collezione");
        }

        /**
         * @brief ottiene la posizione del file xml dei contenitori
         *
         * @return QString
         */
        inline QString getContenitori() {
            return this->getBinDir() + "/data/" + this->getValue("Files/contenitori");
        }

        /**
         * @brief ottiene la posizione del file xml dei contatti
         *
         * @return QString
         */
        inline QString getContatti() {
            return this->getBinDir() + "/data/" + this->getValue("Files/contatti");
        }


        /**
         * @brief ottiene l'xml della biblioteca
         *
         * @return QString
         */
        inline QString getBiblioteca() {
            return this->getBinDir() + "/data/" + this->getValue("Files/biblioteca");
        }

        /**
         * @brief ottiene l'xml dei link
         *
         * @return QString
         */
        inline QString getLinks() {
            return this->getBinDir() + "/data/" + this->getValue("Files/links");
        }

        /**
         * @brief ottiene il nome del file di log
         *
         * @return QString
         */
        inline QString getLogName(QString defaultValue) {
            return this->getValue("Log/LogName", defaultValue);
        }

        /**
         * @brief ottiene il livello richiesto per il log
         *
         * @return QString
         */
        inline QString getLogLevel(QString defaultValue) {
            return this->getValue("Log/Livello", defaultValue);
        }

        /**
         * @brief ottiene il comando per generare il pdf da tex
         *
         * @return QString
         */
        inline QString getCommandToPdf() {
            return this->getValue("Commands/toPdf", "xelatex");
        }

        /**
         * @brief ottiene la dir per i backup
         *
         * @return QString
         */
        inline QString getBackupDir() {
            return this->getValue("Dirs/backupDir");
        }

        /**
         * @brief ottiene la risorsa dell'xsl di conversione a tex
         *
         * @return QString
         */
        inline QString getXml2TexXslt() { return "qrc:/Xml_transform/collezioneLaTeX.xsl";}


        /**
         * @brief ottiene la risorsa dell'xsl di conversione a html
         *
         * @return QString
         */
        inline QString getXml2HtmlXslt() { return "qrc:/Xml_transform/schedaHtml.xsl";}

        /**
         * @brief ottiene la risorsa dell'xsl di conversione a qr
         *
         * @return QString
         */
        inline QString getXml2QrXslt() { return "qrc:/Xml_transform/schedaTxt.xsl";}

        /**
         * @brief ottiene la risorsa del template txt relativo al blocknote nuova moneta
         *
         * @return QString
         */
        inline QString getBlocknoteTemplate() {return ":/Templates/BlocknoteNuovaMoneta.txt.template";}


        /**
         * @brief ottiene la risorsa del template tex relativo alle etichette
         *
         * @return QString
         */
        inline QString getEtichetteTemplate() {return ":/Templates/etichette.tex.template";}


        /**
         * @brief ottiene la risorsa del template xml relativo alla nuova moneta
         *
         * @return QString
         */
        inline QString getMonetaTemplate() {return ":/Templates/instance.xml.template";}

        /**
         * @brief legge il file di configurazione
         *
         * @param ini il nome del file di configurazione
         */
        Errors readIni();



    private:

        /**
         * @brief controlla il file ini
         * @return l'esito del controllo
         */
        bool checkIni();

        /**
         * @brief costruttore (privato)
         *
         */
        CommonData();
        /**
         * @brief estrae il valore dal file ini data la chiave.
         * Esce nel caso la chiave non esista.
         *
         * @param key la chiave
         * @return QString il valore
         */
        QString getValue(QString key);

        /**
         * @brief estrae il valore dal file ini data la chiave.
         * Assegna defaultValue nel caso la chiave non esista.
         *
         * @param key la chiave
         * @param defaultValue il valore di default da assegnare
         * @return QString il valore
         */
        QString getValue(QString key, QString defaultValue);

        /**
         * @brief valuta l'esistenza della chiave
         *
         * @param key
         * @return bool
         */
        inline bool contains(QString key) {
            return this->iniSettings.contains(key);
        }


        /**
         * @brief controlla l'esistenza della dir/file
         * @return l'esito del controllo
         */
        bool checkFileExists(QString file);

        QMap<QString,QVariant> iniSettings; ///< parametri di ini

        static CommonData* instance_ptr; ///< puntatore all'istanza corrente



};

#endif // COMMONDATA_H
