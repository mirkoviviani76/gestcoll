#include "linksxml.h"

#include <QDebug>
#include <iostream>
#include <fstream>

#include "commondata.h"

LinksXml::LinksXml() {
    this->readXml(CommonData::getInstance()->getLinks());
    foreach (::gestColl::links::links::categoria_type cat, this->linksXml->categoria()) {
        QString categoria = QString::fromStdWString(cat.id());
        foreach (::gestColl::links::linklist::link_type l, cat.link()) {
            QString nome = "";
            if (l.nome().present()) {
                nome = QString::fromStdWString(l.nome().get());
            }
            QUrl url(QString::fromStdWString(l.url()));

            QString note = "";
            if (l.note().present()) {
                note = QString::fromStdWString(l.note().get());
            }

            xml::Link* link = new xml::Link(nome, url, note);
            this->links[categoria].append(link);
        }
    }
}

LinksXml::~LinksXml() {
    foreach (QString c, this->links.keys()) {
        foreach (xml::Link* m, this->links[c]) {
            if (m != NULL) {
                delete m;
                m = NULL;
            }
        }
    }
    this->links.clear();
}

bool LinksXml::save() {
    bool ret = false;
    /* scrive su file */
    xml_schema::namespace_infomap xmlmap;
    xmlmap[L""].name = L"http://gestColl/links";
    xmlmap[L""].schema = L"links.xsd";
    std::ofstream myfile;
    myfile.open (CommonData::getInstance()->getLinks().toLatin1());
    if (myfile.is_open())
    {
        ::gestColl::links::links_(myfile, *(this->linksXml), xmlmap);
        myfile.close();
        ret = true;
    }
    else
    {
        ret = false;
    }

    return ret;


}

void LinksXml::addItem(QString categoria, QString nome, QUrl url, QString note) {
    xml::Link* link = new xml::Link(nome, url, note);
    this->links[categoria].append(link);

    ::gestColl::links::links::categoria_sequence categorie = this->linksXml->categoria();
    for (::gestColl::links::links::categoria_iterator it = categorie.begin();
         it != categorie.end(); it++) {
        if (QString::fromStdWString(it->id()) == categoria) {
            ::gestColl::links::link l(url.toString().toStdWString());
            l.nome(nome.toStdWString());
            l.note(note.toStdWString());
            it->link().push_back(l);
        }
    }
    this->linksXml->categoria(categorie);

}


QStringList LinksXml::getCategorie() {
    return QStringList(this->links.keys());
}

void LinksXml::setLink(const QString &categoria, const xml::Link& vecchio, const xml::Link &nuovo) {


    bool done = false;

    ::gestColl::links::links::categoria_sequence categorie = this->linksXml->categoria();

    for (::gestColl::links::links::categoria_iterator it = categorie.begin();
         it != categorie.end(); it++) {

        if (QString::fromStdWString(it->id()) != categoria)
            continue;

        ::gestColl::links::linklist::link_iterator linkit;
        for (linkit = it->link().begin();
             linkit != it->link().end(); linkit++) {

            QString currUrl = QString::fromStdWString(linkit->url());

            QString currNome = "";
            QString currNote = "";

            if (linkit->nome().present()) {
                currNome = QString::fromStdWString(linkit->nome().get());
            }

            if (linkit->note().present()) {
                currNote = QString::fromStdWString(linkit->note().get());
            }

            if (vecchio.getNome() == currNome &&
                vecchio.getUrl().toString() == currUrl &&
                vecchio.getNote() == currNote) {
                done = true;
                linkit->url(nuovo.getUrl().toString().toStdWString());
                linkit->nome(nuovo.getNome().toStdWString());
                linkit->note(nuovo.getNote().toStdWString());

                break;
            }


        }

        if (done == true) {
            break;
        }

    }

    this->linksXml->categoria(categorie);

    foreach (xml::Link* currLink, this->links[categoria]) {
        if (currLink->getNome() == vecchio.getNome() &&
                currLink->getUrl().toString() == vecchio.getUrl().toString() &&
                currLink->getNote() == vecchio.getNote()) {
            currLink->setNome(nuovo.getNome());
            currLink->setUrl(nuovo.getUrl());
            currLink->setNote(nuovo.getNote());
        }
    }


}

void LinksXml::deleteLink(const QString &categoria, xml::Link *vecchio) {
    bool done = false;

    ::gestColl::links::links::categoria_sequence categorie = this->linksXml->categoria();

    for (::gestColl::links::links::categoria_iterator it = categorie.begin();
         it != categorie.end(); it++) {

        if (QString::fromStdWString(it->id()) != categoria)
            continue;

        ::gestColl::links::linklist::link_sequence links = it->link();

        for (unsigned int i = 0; i < links.size(); i++) {
            QString currUrl = QString::fromStdWString(links.at(i).url());
            QString currNome = "";
            QString currNote = "";

            if (links.at(i).nome().present()) {
                currNome = QString::fromStdWString(links.at(i).nome().get());
            }

            if (links.at(i).note().present()) {
                currNote = QString::fromStdWString(links.at(i).note().get());
            }

            if (vecchio->getNome() == currNome &&
                vecchio->getUrl().toString() == currUrl &&
                vecchio->getNote() == currNote) {
                done = true;
                links.erase(links.begin()+i);
                break;
            }
        }
        it->link(links);



        if (done == true) {
            break;
        }

    }

    this->linksXml->categoria(categorie);

    this->links[categoria].removeOne(vecchio);


}

void LinksXml::readXml(QFileInfo file) {
    QString filename = file.canonicalFilePath();
    try {
        this->linksXml = (::gestColl::links::links_(file.canonicalFilePath().toStdWString(), xml_schema::flags::dont_validate));
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

