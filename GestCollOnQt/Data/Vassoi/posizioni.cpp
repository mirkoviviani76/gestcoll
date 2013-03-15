#include "posizioni.h"
#include "commondata.h"
#include "gestlog.h"
#include <iostream>
#include <QDebug>

Posizioni::Posizioni() {
    QString filename = CommonData::getInstance()->getContenitori();
    try {
        this->dati = (contenitori_(filename.toStdWString(), xml_schema::flags::dont_validate));
        this->fillData();
    }
    catch (const xml_schema::exception& e)
    {
        QString msg = QString("%1 - %2").arg(filename).arg(e.what());
        std::wcerr << e;
        Log::Logger::getInstance()->log(msg, Log::FATAL);
        exit(-1);
    }
    catch (const xml_schema::properties::argument&)
    {
        QString msg = QString("%1 - %2").arg(filename).arg("invalid property argument (empty namespace or location)");
        Log::Logger::getInstance()->log(msg, Log::FATAL);
        exit(-1);
    }
    catch (const xsd::cxx::xml::invalid_utf16_string&)
    {
        QString msg = QString("%1 - %2").arg(filename).arg("invalid UTF-16 text in DOM model");
        Log::Logger::getInstance()->log(msg, Log::FATAL);
        exit(-1);
    }
    catch (const xsd::cxx::xml::invalid_utf8_string&)
    {
        QString msg = QString("%1 - %2").arg(filename).arg("invalid UTF-8 text in object model");
        Log::Logger::getInstance()->log(msg, Log::FATAL);
        exit(-1);
    }
    catch (...)
    {
        QString msg = QString("%1 - %2").arg(filename).arg("Unknown exception");
        Log::Logger::getInstance()->log(msg, Log::FATAL);
        exit(-1);
    }
}


Posizioni::~Posizioni() {
    foreach (QString key, this->vassoi.keys()) {
        if (this->vassoi[key] != NULL) {
            delete this->vassoi[key];
            this->vassoi[key] = NULL;
        }
    }

    this->vassoi.clear();
}

/**
  Ottiene il vassoio
  */
VassoioXml* Posizioni::getVassoio(QString idArmadio, QString idContenitore, QString idVassoio) {
    QString key = QString("%1-%2-%3")
            .arg(idArmadio)
            .arg(idContenitore)
            .arg(idVassoio);
    if (this->vassoi.contains(key))
        return this->vassoi[key];
    else
        return NULL;
}

/**
  Ottiene il vassoio
  */
VassoioXml* Posizioni::getVassoio(QString id, QString idArmadio) {
    QString key = QString("%1-%2")
            .arg(idArmadio)
            .arg(id);
    if (this->vassoi.contains(key))
        return this->vassoi[key];
    else
        return NULL;
}


/**
  Riempie la struttura dati di comodo con i vassoi
  */
void Posizioni::fillData() {
    /* cicla sugli armadi */
    foreach(contenitori::armadio_type a, this->dati->armadio()) {
        /* cicla sui contenitori */
        foreach (armadio::contenitore_type c, a.contenitore()) {
            /* cicla sui vassoi */
            foreach (contenitore::vassoio_type v, c.vassoio()) {
                QString vassoioId = QString("%1-%2-%3")
                        .arg(QString::fromStdWString(a.id()))
                        .arg(QString::fromStdWString(c.id()))
                        .arg(QString::fromStdWString(v.id()));

                VassoioXml* vassoio = new VassoioXml(
                            QString::fromStdWString(a.id()),
                            QString::fromStdWString(c.id()),
                            QString::fromStdWString(v.id()),
                            QString::fromStdWString(v.dimensione()),
                            v.righe(),
                            v.colonne()
                            );
                this->vassoi[vassoioId] = vassoio;
                vassoioId = "";
            }
        }
    }
}

