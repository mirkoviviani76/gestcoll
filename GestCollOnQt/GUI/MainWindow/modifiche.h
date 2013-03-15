#ifndef MODIFICHE_H
#define MODIFICHE_H

#include <QMap>

enum Modificatore {
    COLLEZIONE = 0,
    CONTATTI,
    BLOCKNOTES,
    LINKS,
    BIBLIOTECA,
    LAST
};


class Modifiche
{
public:
    Modifiche();
    bool hasModifiche();
    bool hasModifiche(Modificatore m);
    qint16 getModifiche(Modificatore m);
    qint16 getModifiche();
    void addModifica(Modificatore m);
    void resetModifica(Modificatore m);

private:
    QMap<Modificatore, qint16> modifiche;
    void delModifica(Modificatore m);

};

#endif // MODIFICHE_H
