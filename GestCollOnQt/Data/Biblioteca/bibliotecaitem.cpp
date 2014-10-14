#include "bibliotecaitem.h"
#include "commondata.h"
#include <QDebug>

BibliotecaItem::BibliotecaItem(QString _id, QString _titolo, QString _filename,
                               QStringList _listaAutori, QStringList _listaSupporti, QStringList _listaArgomenti)
 : id(_id), titolo(_titolo), filename(_filename), listaAutori(_listaAutori),
   listaSupporti(_listaSupporti), listaArgomenti(_listaArgomenti)
{
}

/**
  Funzione di ordinamento. Ordina in base al titolo
  */
bool BibliotecaItem::lessThan(BibliotecaItem* d)
{
    return this->titolo < d->titolo;
}

QColor BibliotecaItem::getColor() {
    if (hasElectronicForm())
        return Qt::green;
    else
        return Qt::white;
}


bool BibliotecaItem::hasElectronicForm() {
    return this->listaSupporti.contains("E", Qt::CaseInsensitive);
}


QString BibliotecaItem::toString(int column)
{

    QString ret = "";
    QString autori = "";
    QString supporti = "";
    QString argomenti = "";
    for (int i = 0; i < listaAutori.count(); i++)
        autori.append(listaAutori.at(i));
    for (int i = 0; i < listaSupporti.count(); i++)
        supporti.append(listaSupporti.at(i));
    for (int i = 0; i < listaArgomenti.count(); i++)
        argomenti.append(listaArgomenti.at(i));

    switch (column) {
    case -1:     ret = QString("%1: %2")
                .arg(autori)
                .arg(this->titolo);
        break;
    case 0: ret = this->id;
        break;
    case 1: ret = autori;
        break;
    case 2: ret = titolo;
        break;
    default:
            Log::Logger::getInstance()->log(
                        QString("BibliotecaItem()::toString: Richiesta colonna non gestita %1").arg(column),
                        Log::ERR);
            break;
    }

    return ret;
}

QString BibliotecaItem::toTooltip()
{
    QString ret = "";
    QString autori = "";
    for (int i = 0; i < listaAutori.count(); i++)
        autori.append(listaAutori.at(i));

    ret = QString("%1: %2 (%3)")
          .arg(autori)
          .arg(this->titolo)
          .arg(this->id);
    return ret;
}
