#include "vassoioxml.h"

VassoioXml::VassoioXml(QString a, QString c, QString v, QString d, int row, int col)
{
    this->idArmadio = a;
    this->idContenitore = c;
    this->idVassoio = v;
    this->dimensione = d;
    this->righe = row;
    this->colonne = col;
}


