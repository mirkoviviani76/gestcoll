#ifndef POSIZIONI_H
#define POSIZIONI_H

#include <QString>
#include <QList>
#include <QMap>
#include <vassoioxml.h>
#include "contenitori.hxx"

using namespace ::gestColl::contenitori;

class Posizioni
{
private:
    ::std::auto_ptr< contenitori > dati;
    QMap<QString, VassoioXml*> vassoi;

    void fillData();

public:
    Posizioni();
    virtual ~Posizioni();
    VassoioXml* getVassoio(QString idArmadio, QString idContenitore, QString idVassoio);
    VassoioXml* getVassoio(QString id, QString idArmadio = "SRI");
};

#endif // POSIZIONI_H
