#ifndef VASSOIOXML_H
#define VASSOIOXML_H

#include <QString>


class VassoioXml
{
private:
    QString idArmadio;
    QString idContenitore;
    QString idVassoio;
    QString dimensione;
    int righe;
    int colonne;

public:
    VassoioXml(QString a, QString c, QString v, QString d, int row, int col);
    virtual ~VassoioXml() {}

    inline QString getIdArmadio() { return this->idArmadio; }
    inline QString getIdContenitore() { return this->idContenitore; }
    inline QString getIdVassoio() { return this->idVassoio; }

    inline QString getDimensione() { return this->dimensione; }
    inline int getRighe() { return this->righe; }
    inline int getColonne() { return this->colonne; }
};

#endif // VASSOIOXML_H
