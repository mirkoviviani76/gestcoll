#ifndef FILEREMOVER_H
#define FILEREMOVER_H

#include <worker.h>

/**
 * @brief Offre metodi per la cancellazione di files dal filesystem.
 *
 */
class FileRemover : public Worker
{
    Q_OBJECT
public:

   /**
    * @brief costruttore
    *
    */
    FileRemover();

    /**
      @brief Rimuove i file corrispondenti a fileTypes ricorsivamente a partire dalla directory specificata.
      Emette segnali per tracciare il progresso dell'operazione.
      @return il numero di file rimossi
      */
    static int remove(const QString& reportDir, const QStringList& fileTypes);


};

#endif // FILEREMOVER_H
