#include "commondefs.h"
#include "gestlog.h"
#include "bibliotecaitem.h"

using namespace xml;

Ambito::Ambito(QString _titolo, QString _icona)
{
    titolo = _titolo;
    icona = _icona;
}





QString Stato::getSpiegazione() const
{
    return spiegazione;
}

void Stato::setSpiegazione(const QString &value)
{
    spiegazione = value;
}
QString Stato::getColore() const
{
    return colore;
}

void Stato::setColore(const QString &value)
{
    colore = value;
}
