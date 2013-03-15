#include "modifiche.h"

Modifiche::Modifiche()
{
    for (int i = 0; i != LAST; i++) {
        Modificatore m = static_cast<Modificatore>(i);
        this->modifiche[m] = 0;
    }

}

bool Modifiche::hasModifiche() {
    for (int i = 0; i != LAST; i++) {
        Modificatore m = static_cast<Modificatore>(i);
        if (this->modifiche[m] != 0)
            return true;
    }
    return false;

}

bool Modifiche::hasModifiche(Modificatore m) {
    return (this->modifiche[m] > 0);
}

qint16 Modifiche::getModifiche(Modificatore m) {
    return this->modifiche[m];
}

qint16 Modifiche::getModifiche() {
    qint16 conta = 0;
    for (int i = 0; i != LAST; i++) {
        Modificatore m = static_cast<Modificatore>(i);
        conta += this->modifiche[m];
    }
    return conta;
}

void Modifiche::addModifica(Modificatore m) {
    this->modifiche[m]++;
}

void Modifiche::delModifica(Modificatore m) {
    if (this->modifiche[m] > 0)
        this->modifiche[m]--;
    else
        this->modifiche[m] = 0;
}

void Modifiche::resetModifica(Modificatore m) {
    this->modifiche[m] = 0;
}


