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

    inline QString getIdArmadio() const { return this->idArmadio; }
    inline QString getIdContenitore() const { return this->idContenitore; }
    inline QString getIdVassoio() const { return this->idVassoio; }

    inline QString getDimensione() const { return this->dimensione; }
    inline int getRighe() const { return this->righe; }
    inline int getColonne() const { return this->colonne; }
};

#endif // VASSOIOXML_H
