#ifndef COLLEZIONEXML_H
#define COLLEZIONEXML_H

#include "scheda.hxx"

#include <QFileInfo>
#include <QList>
#include <QMap>
#include "monetaxml.h"

using namespace ::gestColl::coins;


/**
 * @brief classe singleton per gestire il file contenente la collezione
 *
 */
class CollezioneXml
{
private:
    ::std::auto_ptr< ::gestColl::coins::monete > collezione; /**< istanza ritornata dal gestore dell'xml */
    static CollezioneXml* instance_ptr; /**< istanza della classe */
    /**
     * @brief legge l'xml della collezione
     *
     * @param f il file xml
     */
    void readXml(QFileInfo f);

/**
 * @brief costruttore. Legge il file xml e carica i dati.
 *
 */
    CollezioneXml();

    /**
     *   elenco delle monete: chiave id e valore il puntatore alla moneta
     *   (@see MonetaXml)
     */
    QMap<QString, MonetaXml*> moneteInCollezione;

    xml::InfoCollezione* infoCollezione;


public:

    /**
     * @brief ottiene l'istanza del singleton
     *
     * @return CollezioneXml * il puntatore all'istanza
     */
    static CollezioneXml* getInstance();

    void readData(QString xmlFile);

    /**
     * @brief ottiene tutti gli id delle monete
     *
     * @return QList<QString> la lista degli id
     */
    QList<QString> getAllId() { return this->moneteInCollezione.keys(); }

    /**
     * @brief conta il numero delle monete in collezione
     *
     * @return int il numero di monete
     */
    int size() {return this->moneteInCollezione.count();}

    /**
     * @brief ottiene il puntatore alla moneta a partire dal suo id
     *
     * @param id id della moneta
     * @return MonetaXml * il puntatore
     */
    MonetaXml* getMoneta(QString id);
    /**
     * @brief aggiunge una moneta in collezione
     *
     * @param id l'id della moneta
     * @param anno l'anno
     * @param contenitore il contenitore
     * @param vassoio il vassoio
     * @param riga la riga
     * @param colonna la colonna
     * @return MonetaXml * il puntatore alla moneta
     */
    MonetaXml* addMoneta(QString id, QString anno, int contenitore, int vassoio, int riga, int colonna);
    /**
     * @brief distruttore
     *
     */
    virtual ~CollezioneXml();


    /**
     * @brief ottiene le info
     *
     * @return xml::Info le info
     */
    xml::InfoCollezione *getInfo();


    /**
     * @brief salva le info
     *
     * @param info le info
     */
    void setInfo(xml::InfoCollezione *infoCollezione);

    void addAmbito(xml::Ambito* a);

    void updateAmbitiInCoins(const xml::Ambito& vecchio, const xml::Ambito& nuovo);

public slots :
    /**
     * @brief salva i dati in un nuovo xml, salvando i dati precedenti in un file di backup.
     *
     * @return bool l'esito del salvataggio
     */
    bool save();

};

#endif // COLLEZIONEXML_H
