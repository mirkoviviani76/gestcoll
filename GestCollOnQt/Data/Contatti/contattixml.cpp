#include "contattixml.h"
#include "commondata.h"
#include <iostream>
#include "gestlog.h"
#include <QDebug>
#include <fstream>

using namespace ::gestColl::contatti;

ContattiXml::ContattiXml()
{
    this->readXml(CommonData::getInstance()->getContatti());
    foreach (::gestColl::contatti::contatto c, this->contattiXml->contatto()) {
        QString nome = QString::fromStdWString(c.nome());
        QString email = QString::fromStdWString(c.email());
        QString note = QString::fromStdWString(c.note());
        xml::Contatto* cont = new xml::Contatto(nome, email, note);
        this->contatti.append(cont);
    }
}

ContattiXml::~ContattiXml() {
    foreach (xml::Contatto* m, this->contatti) {
        if (m != NULL) {
            delete m;
            m = NULL;
        }
    }
    this->contatti.clear();
}

void ContattiXml::addItem(QString nome, QString email, QString note) {
    xml::Contatto* cont = new xml::Contatto(nome, email, note);
    this->contatti.append(cont);
    ::gestColl::contatti::contatto c(nome.toStdWString(), email.toStdWString(), note.toStdWString());
    ::gestColl::contatti::contatti::contatto_sequence elenco = this->contattiXml->contatto();
    elenco.push_back(c);
    this->contattiXml->contatto(elenco);
}

bool ContattiXml::save() {
    bool ret = false;
    /* scrive su file */
    xml_schema::namespace_infomap xmlmap;
    xmlmap[L""].name = L"http://gestColl/contatti";
    xmlmap[L""].schema = L"contatti.xsd";
    std::ofstream myfile;
    myfile.open (CommonData::getInstance()->getContatti().toLatin1());
    if (myfile.is_open())
    {
        ::gestColl::contatti::contatti_(myfile, *(this->contattiXml), xmlmap);
        myfile.close();
        ret = true;
    }
    else
    {
        ret = false;
    }
    return ret;


    return ret;
}

void ContattiXml::readXml(QFileInfo file) {
    QString filename = file.canonicalFilePath();
    try {
        this->contattiXml = (contatti_(file.canonicalFilePath().toStdWString(), xml_schema::flags::dont_validate));
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


void ContattiXml::setContatto(const xml::Contatto& vecchio, const xml::Contatto& nuovo) {
    bool done = false;

    gestColl::contatti::contatti::contatto_sequence let = this->contattiXml->contatto();

    for (gestColl::contatti::contatti::contatto_iterator it = let.begin();
         it != let.end() && !done;
         ++it)
    {
        //cerca l'item "giusto"
        gestColl::contatti::contatti::contatto_type cur = *it;
        QString curNome = QString::fromStdWString(cur.nome());
        QString curEmail = QString::fromStdWString(cur.email());
        QString curNote = QString::fromStdWString(cur.note());
        if ((curNome  == vecchio.nome)  &&
            (curEmail == vecchio.email) &&
            (curNote  == vecchio.note)
           )
        {
            /* trovato: effettua le modifiche */
            (*it).nome(nuovo.nome.toStdWString().c_str());
            (*it).email(nuovo.email.toStdWString().c_str());
            (*it).note(nuovo.note.toStdWString().c_str());
            done = true;
        }
    }

    //salva le modifiche nel dom
    this->contattiXml->contatto(let);
    this->contatti.clear();
    foreach (::gestColl::contatti::contatto c, this->contattiXml->contatto()) {
        QString nome = QString::fromStdWString(c.nome());
        QString email = QString::fromStdWString(c.email());
        QString note = QString::fromStdWString(c.note());
        xml::Contatto* cont = new xml::Contatto(nome, email, note);
        this->contatti.append(cont);
        qDebug() << "Aggiunto: " << nome << email << note << " size: " << this->contatti.size();
    }

}


void ContattiXml::deleteContatto(xml::Contatto *l) {

    gestColl::contatti::contatti::contatto_sequence let = this->contattiXml->contatto();

    for (unsigned int i = 0; i < let.size(); i++) {
        QString curNome = QString::fromStdWString(let.at(i).nome());
        QString curEmail = QString::fromStdWString(let.at(i).email());
        QString curNote = QString::fromStdWString(let.at(i).note());
        if ((curNome  == l->nome)  &&
            (curEmail == l->email) &&
            (curNote  == l->note)
           ) {
            let.erase(let.begin()+i);
        }
    }
    this->contattiXml->contatto(let);
    this->contatti.removeOne(l);


}


