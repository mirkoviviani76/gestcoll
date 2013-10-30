#include "utils.h"

#include <QDir>
#include <QString>
#include <QTextStream>
#include <QProcess>
#include <QCryptographicHash>
#include <QDebug>
#include <QUrl>
#include "bzlib.h"


void Utils::findFilesRecursively(const QString& directory, const QStringList& fileTypes, QFileInfoList* out)
{
    QDir dir(directory);
    QFileInfoList list = dir.entryInfoList(QDir::Files | QDir::Dirs | QDir::NoDotAndDotDot);

    foreach(QFileInfo finfo, list) {
        if (finfo.isDir()) {
            Utils::findFilesRecursively(finfo.canonicalFilePath(), fileTypes, out);
        }
        else if (fileTypes.contains(finfo.completeSuffix()))
        {
            if (!out->contains(finfo))
            {
                out->append(finfo);
            }
        }
    }
}

bool Utils::replaceInFile(QString infile, QString outfile, QString source, QString target)
{
    bool ret = false;
    /* apre e legge tutto il file posizioni.template */
    QFile f(infile);
    ret = f.open(QIODevice::ReadOnly);
    QString all = f.readAll();
    f.close();

    if (ret)
    {
        /* sostituisce il marker */
        all = all.replace(source, target);

        QFile out(outfile);
        ret = out.open(QIODevice::WriteOnly | QIODevice::Truncate);
        /* scrive nel file come unicode */
        QTextStream outStream(&out);
        outStream.setCodec("UTF-8");
        outStream << all;
        /* flush e chiusura */
        outStream.flush();
        out.close();
    }
    return ret;
}

bool Utils::replaceInFile(QString infile, QString outfile, QMap<QString, QString> conv)
{
    bool ret = false;
    /* apre e legge tutto il file posizioni.template */
    QFile f(infile);
    ret = f.open(QIODevice::ReadOnly);
    QString all = f.readAll();
    f.close();

    if (ret)
    {
        /* sostituisce il marker */
        foreach (QString key, conv.keys()) {
            all = all.replace(key, conv[key]);
        }

        QFile out(outfile);
        ret = out.open(QIODevice::WriteOnly | QIODevice::Truncate);
        /* scrive nel file come unicode */
        QTextStream outStream(&out);
        outStream.setCodec("UTF-8");
        outStream << all;
        /* flush e chiusura */
        outStream.flush();
        out.close();
    }
    return ret;
}

void Utils::doWork(QString workDir, QString command, QStringList args)
{
    QProcess proc;
    proc.setWorkingDirectory(workDir);
    proc.start(command, args);
    if (proc.waitForStarted(10000)) {
        proc.closeWriteChannel();
        if (proc.waitForFinished(60000)) {
            //qDebug(proc.readAllStandardOutput());
        } else {
            proc.close();
            qDebug("Timeout...");
        }
    } else {
        proc.close();
        qDebug("Timeout...");
    }
}

/**
  Salva una copia di backup compressa
  */
bool Utils::saveAndBackup(QString source, QString backupfile) {
    bool ret = false;

    ret = QFile::copy(source, backupfile);
#ifdef BZ2

    /* calcola la checksum md5 del contenuto
       e la usa per comporre il nome del file */
    QString checksum = Utils::checksum(source);
    QString out = QString("%1__%2.bz2").arg(backupfile).arg(checksum);


    FILE *tarFD = fopen(backupfile.toLatin1(), "r");
    FILE *tbz2File = fopen(out.toLatin1(), "wb");

    int bzError;
    const int BLOCK_MULTIPLIER = 7;
    BZFILE *pBz = BZ2_bzWriteOpen(&bzError, tbz2File, BLOCK_MULTIPLIER, 0, 0);

    const int BUF_SIZE = 10000;
    char buf[BUF_SIZE];
    ssize_t bytesRead;

    while((bytesRead = fread(buf, 1, BUF_SIZE, tarFD)) > 0)
    {
        BZ2_bzWrite(&bzError, pBz, buf, bytesRead);
    }
    BZ2_bzWriteClose(&bzError, pBz, 0, NULL, NULL);
    fclose(tarFD);
    fclose(tbz2File);

    QFile removeFile(backupfile);
    removeFile.remove();
#else
    qWarning() << "BZIP Non disponibile";
#endif

    return ret;
}

QString Utils::checksum(QString filename) {
    QFile file(filename);
    QString ret = "";
    //apre il file
    file.open(QIODevice::ReadOnly);
    if (file.isOpen()) {
        //legge tutto
        QString content = file.readAll();
        /* calcola l'hash come stringa esadecimale maiuscola */
        QByteArray hash = QCryptographicHash::hash(content.toUtf8(), QCryptographicHash::Md5);
        ret = hash.toHex().toUpper();
        //chiude il file
        file.close();
    }

    return ret;
}

