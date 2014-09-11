#ifndef TEXGENERATOR_H
#define TEXGENERATOR_H

#include <xsltconverter.h>
#include <monetaxml.h>
#include "posizioni.h"


class TexGenerator : public XsltConverter
{
public:
    TexGenerator();
    virtual ~TexGenerator();
    bool convert();
    bool toPosizioni(const QString& inDir, const QString& outDir, const QString& posizioniTemplate, const QMap<QString, QString>& conversion);
private:
    Posizioni* posizioni;
    QString getDim(const MonetaXml& item);
    QString getEtichetta(moneta *xml, QString dimensione);
};

#endif // TEXGENERATOR_H
