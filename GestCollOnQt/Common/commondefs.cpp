#include "commondefs.h"
#include "gestlog.h"
#include "bibliotecaitem.h"

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

QString Libro::toTooltip()
{
    BibliotecaItem* item = BibliotecaXml::getInstance()->getItem(sigla);
    if (item != NULL) {
        return item->toTooltip();
    }
    else {
        return "";
    }

}

bool Libro::lessThan(Libro *due)
{
    if (this->sigla == due->sigla)
        return this->numero < due->numero;
    else
        return this->sigla < due->sigla;
}



Ambito::Ambito(QString _titolo, QString _icona)
{
    titolo = _titolo;
    icona = _icona;
}



