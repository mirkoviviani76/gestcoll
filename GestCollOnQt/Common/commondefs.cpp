#include "commondefs.h"
#include "gestlog.h"

using namespace xml;


QString Libro::toString(int column)
{
    QString ret;
    switch (column) {
    case -1:     ret = QString("%1: %2").arg(sigla).arg(numero);
        break;
    case 0: ret = sigla;
        break;
    case 1: ret = numero;
        break;
    default:
            Log::Logger::getInstance()->log(
                        QString("Libro()::toString: Richiesta colonna non gestita %1").arg(column),
                        Log::ERR);
            break;
    }
    return ret;
}



Ambito::Ambito(QString _titolo, QString _icona) {
    titolo = _titolo;
    icona = _icona;
}

QString Ambito::toString(int column) {
    QString ret;
    switch (column) {
    case -1:
        ret = QString("%1\t%2").arg(titolo).arg(icona);
        break;
    case 0:
        ret = titolo;
        break;
    case 1:
        ret = icona;
        break;
    default:
            Log::Logger::getInstance()->log(
                        QString("Ambito()::toString: Richiesta colonna non gestita %1").arg(column),
                        Log::ERR);
            break;
    }
    return ret;
}



