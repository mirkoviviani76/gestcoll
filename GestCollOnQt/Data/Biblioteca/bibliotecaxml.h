#ifndef BIBLIOTECA_H
#define BIBLIOTECA_H

#include <biblioteca.hxx>

#include <QMap>

#include "bibliotecaitem.h"


/**
 * @brief classe per gestire il file contenente la biblioteca
 *
 */
class BibliotecaXml
{
    public:

        /**
         * @brief ottiene l'id presunto (per ricercarlo nella mappa)
         *
         * @param id l'id originale
         * @return QString l'id presunto da cercare nella mappa
         */
        QString getArguedId(const QString & id);

        /**
         * @brief ottiene l'istanza del singleton
         *
         * @return CommonData *
         */
        static BibliotecaXml* getInstance();

        /**
     * @brief ottiene l'elenco degli item della biblioteca
     *
     * @return QList<BibliotecaItem *>
     */
        inline QList<BibliotecaItem*> getItems() {return items.values();}

        /**
         * @brief ritorna il numero di libri presenti
         *
         * @return int il numero
         */
        int size();

        BibliotecaItem* getItem(QString id);

    private:
        /**
        * @brief costruttore
        *
        */
        BibliotecaXml();

        /**
        * @brief legge l'xml
        *
        * @param file il file
        * @return bool il risultato true se ok, false altrimenti
        */
        bool readXml(QString file);
        /**
         * @brief carica i dati nelle strutture dati
         *
        */
        void readData();

        static BibliotecaXml* instance_ptr; /**< puntatore all'istanza corrente */
        QMap<QString, BibliotecaItem*> items; /**< gli item della biblioteca */
        std::auto_ptr< ::gestColl::biblioteca::biblioteca > biblio; /**< l'oggetto per leggere dall'xml */

};

#endif // BIBLIOTECA_H
