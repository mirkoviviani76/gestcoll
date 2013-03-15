#include "fileremover.h"
#include <QFileInfoList>
#include <QFileInfo>
#include <utils.h>

FileRemover::FileRemover()
{
}

int FileRemover::remove(const QString& directory, const QStringList& fileTypes)
{
    /* trova i files che corrispondono ai criteri di ricerca */
    QFileInfoList list;
    Utils::findFilesRecursively(directory, fileTypes, &list);
    int countIndex = 0;

    if (list.count() > 0)
    {
        /* per ogni file, cancella e segnala l'avanzamento */
        foreach (QFileInfo finfo, list)
        {
            countIndex++;
            QFile::remove(finfo.canonicalFilePath());
        }
    }
    return countIndex;
}



