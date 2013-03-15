#ifndef UTILS_H
#define UTILS_H

#include <QFileInfoList>
#include <QMap>

class Utils
{
public:
    /**
     * @brief trova file a partire da un path, in maniera ricorsiva
     *
     * @param paths il path da cui partire
     * @param fileTypes i tipi di file da cercare
     * @param out lista dei file trovati
     */
    static void findFilesRecursively ( const QString& paths, const QStringList& fileTypes, QFileInfoList* out );
    /**
     * @brief modifica il contenuto di un file, cambiando ogni entry "source" con "target"
     *
     * @param infile il file in ingresso
     * @param outfile il file in uscita
     * @param source la stringa da cercare
     * @param target la stringa da sostituire
     * @return bool l'esito
     */
    static bool replaceInFile(QString infile, QString outfile, QString source, QString target);
    /**
     * @brief modifica il contenuto di un file, cambiando ogni entry "source" con "target"
     *
     * @param infile il file in ingresso
     * @param outfile il file in uscita
     * @param conv la mappa di conversione source-target
     * @return bool l'esito
     */
    static bool replaceInFile(QString infile, QString outfile, QMap<QString, QString> conv);
    /**
     * @brief eseguie un comando in un processo a parte
     *
     * @param workDir la directory di lavoro
     * @param command il comando da eseguire
     * @param args gli argomenti del comando
     */
    static void doWork(QString workDir, QString command, QStringList args);
    /**
     * @brief esegue il backup compresso come bz
     *
     * @param source il file sorgente
     * @param dest il file destinazione
     * @return bool l'esito
     */
    static bool saveAndBackup(QString source, QString dest);
    /**
     * @brief Calcola l'md5 del contenuto del file specificato
     *
     * @param filename il nome del file del cui contenuto va calcolato l'md5
     * @return QString la stringa con l'md5 o la stringa vuota in caso di errore
     */
    static QString checksum(QString filename);
};

#endif // UTILS_H
