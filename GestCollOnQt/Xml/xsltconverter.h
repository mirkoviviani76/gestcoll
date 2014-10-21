#ifndef XSLTCONVERTER_H
#define XSLTCONVERTER_H

#include <QString>
#include <QFile>
#include <QDir>
#include <QColor>
#include <QImage>

#include <qrencode.h>

#include <worker.h>

class XsltConverter : public Worker
{
    Q_OBJECT
public:
    virtual ~XsltConverter() {}
    bool convert(const QString& xml, const QString& xslt, QString* out, const QMap<QString, QString>& conversion, const QString &imgDir, const QString& docsDir = "", const QString& id = "ALL_ID");
    bool convert(const QString& xml, const QString& xslt, QFile* out, const QMap<QString, QString>& conversion, const QString &imgDir, const QString& docsDir = "", const QString& id = "ALL_ID" );
    bool convertToQr(const QString& id, const QString& xml, const QString& outDir, const QString& xslt, const QMap<QString, QString>& conversion);
    QString convertToText(const QString& id, const QString& xml, const QString& xslt);

private:
    QImage qrcodeToQImage(QRcode *qrcode, int size = 3, int margin = 4, QColor background = Qt::white, QColor foreground = Qt::black);


};

#endif // XSLTCONVERTER_H
