#include "xsltconverter.h"

#include <QXmlQuery>
#include <QTextStream>
#include <QPainter>
#include <QResource>
#include <QApplication>

#include "commondata.h"
#include "utils.h"

#include <QDebug>
#include "gestlog.h"


/**
  Esegue la conversione di un xml tramite xslt, utilizzando anche una conversione "al volo"
  delle stringe specificate.
  */
bool XsltConverter::convertAll(const QString& xml, const QString& xslt, QFile* out, const QMap<QString, QString>& conversion) {
    bool ret = false;
    QString tempOut;


    QXmlQuery query(QXmlQuery::XSLT20);
    ret = query.setFocus(QUrl::fromLocalFile(xml));
    if (ret) {
        query.bindVariable("dirImg", QVariant(CommonData::getInstance()->getImgDir()));

        query.bindVariable("hyperref", QVariant(1));
        QUrl url(xslt);
        query.setQuery(url);
        ret = query.evaluateTo(&tempOut);
        if (ret)
        {
            //effettua la conversione (se disponibile)
            foreach (QString key, conversion.keys()) {
                tempOut.replace(key, conversion[key], Qt::CaseSensitive);
            }
            Log::Logger::getInstance()->log(QString("XsltConverter::convertAll() Trasformazione xsl eseguita"), Log::TRACE);
        } else {
            Log::Logger::getInstance()->log(QString("XsltConverter::convertAll() Trasformazione xsl fallita"), Log::ERR);
        }
    } else {
        Log::Logger::getInstance()->log(QString("XsltConverter::convertAll() Errore nel caricamento di %1").arg(xml), Log::ERR);
    }


    if (ret)
    {
        //apre il file
        ret = out->open(QIODevice::ReadWrite | QIODevice::Truncate);
        if (ret) {
            /* scrive nel file come unicode */
            QTextStream outStream(out);
            outStream.setCodec("UTF-8");
            outStream << tempOut;
            /* flush e chiusura */
            outStream.flush();
            out->close();
        } else {
            Log::Logger::getInstance()->log(QString("XsltConverter::convert() Errore scrivendo il file %1").arg(out->fileName()), Log::ERR);
        }
    }

    return ret;
}

/**
  Esegue la conversione di un xml tramite xslt, utilizzando anche una conversione "al volo"
  delle stringe specificate.
  */
bool XsltConverter::convert(const QString& id, const QString& xml, const QString& xslt, QString* out, const QMap<QString, QString>& conversion)
{
    bool ret = false;
    QXmlQuery query(QXmlQuery::XSLT20);
    ret = query.setFocus(QUrl::fromLocalFile(xml));
    if (ret) {
        query.bindVariable("monetaId", QVariant(id));

        query.bindVariable("dirImg", QVariant("./img"));
        QUrl url(xslt);
        query.setQuery(url);
        ret = query.evaluateTo(out);
        if (ret)
        {
            //effettua la conversione (se disponibile)
            foreach (QString key, conversion.keys()) {
                out->replace(key, conversion[key], Qt::CaseSensitive);
            }
            Log::Logger::getInstance()->log(QString("XsltConverter::convert() Trasformazione xsl eseguita per %1").arg(id), Log::TRACE);
        } else {
            Log::Logger::getInstance()->log(QString("XsltConverter::convert() Trasformazione xsl fallita per %1").arg(id), Log::ERR);
        }
    } else {
        Log::Logger::getInstance()->log(QString("XsltConverter::convert() Errore nel caricamento di %1").arg(xml), Log::ERR);
    }
    return ret;
}

bool XsltConverter::convert(const QString& id, const QString& xml, const QString& xslt, QFile* out,const QMap<QString, QString>& conversion)
{
    bool ret = false;
    QString tempOut;
    ret = XsltConverter::convert(id, xml, xslt, &tempOut, conversion);
    if (ret)
    {
        //apre il file
        ret = out->open(QIODevice::ReadWrite | QIODevice::Truncate);
        if (ret) {
            /* scrive nel file come unicode */
            QTextStream outStream(out);
            outStream.setCodec("UTF-8");
            outStream << tempOut;
            /* flush e chiusura */
            outStream.flush();
            out->close();
        } else {
            Log::Logger::getInstance()->log(QString("XsltConverter::convert() Errore scrivendo il file %1").arg(out->fileName()), Log::ERR);
        }
    }

    return ret;
}

/**
  Ottiene la rappresentazione testuale della moneta con il foglio stile
  */
QString XsltConverter::convertToText(const QString& id, const QString& xml, const QString& xslt) {
    QXmlQuery query(QXmlQuery::XSLT20);
    query.setFocus(QUrl::fromLocalFile(xml));
    query.bindVariable("monetaId", QVariant(id));
    QUrl url(xslt);
    query.setQuery(url);
    QString out;
    query.evaluateTo(&out);
    return out;
}

bool XsltConverter::convertToQr(const QString& id, const QString& xml, const QString& outDir, const QString& xslt, const QMap<QString, QString>& conversion)
{
    bool ret = false;

    QXmlQuery query(QXmlQuery::XSLT20);
    ret = query.setFocus(QUrl::fromLocalFile(xml));
    query.bindVariable("monetaId", QVariant(id));
    QUrl url(xslt);
    query.setQuery(url);
    QString out;
    ret = query.evaluateTo(&out);
    if (ret)
    {
        //effettua la conversione (se disponibile)
        foreach (QString key, conversion.keys()) {
            out.replace(key, conversion[key], Qt::CaseSensitive);
        }
        QRcode *qrcode = QRcode_encodeString8bit(out.toLocal8Bit(), 0, QR_ECLEVEL_Q );
        QImage image = qrcodeToQImage(qrcode);
        QString completeOutFile = QString("%1/%2.png").arg(outDir).arg(id);
        ret = image.save(completeOutFile, "PNG");
        if (!ret) {
            Log::Logger::getInstance()->log(QString("XsltConverter::convertToQr() Errore scrivendo il file %1").arg(completeOutFile), Log::ERR);
        } else {
            Log::Logger::getInstance()->log(QString("XsltConverter::convert() Trasformazione xsl eseguita per %1").arg(id), Log::TRACE);
        }
        QRcode_free(qrcode);
    } else {
        Log::Logger::getInstance()->log(QString("XsltConverter::convert() Trasformazione xsl fallita per %1").arg(id), Log::ERR);
    }

    return ret;

}




QImage XsltConverter::qrcodeToQImage(QRcode *qrcode, int size, int margin, QColor background, QColor foreground)
{
        int datawidth = qrcode->width;
        int realwidth = datawidth * size + margin * 2;

        QImage image(realwidth,realwidth,QImage::Format_ARGB32);
        memset(image.scanLine(0),0,image.byteCount());

        QPainter painter(&image);
        painter.fillRect(image.rect(),background);
        for(int x=0;x<datawidth;x++) {
                for(int y=0;y<datawidth;y++) {
                        if(1 & qrcode->data[y*datawidth+x]) {
                                painter.fillRect(QRect(x*size + margin, y*size + margin, size, size), foreground);
                        }
                }
        }

        painter.end();

        return image;
}
