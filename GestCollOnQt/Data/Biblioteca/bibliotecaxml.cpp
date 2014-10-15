#include "bibliotecaxml.h"

#include <QApplication>
#include <QSplashScreen>
#include <QFileInfo>
#include <iostream>
#include "commondata.h"

#include <QDebug>

extern QSplashScreen* splash;


BibliotecaXml* BibliotecaXml::instance_ptr = NULL;


BibliotecaXml::BibliotecaXml()
{
    /* legge xml */
    QString xml = CommonData::getInstance()->getBiblioteca();
    /* scrive lo splash screen */
    QString msg = QString("Leggo: %1").arg(xml);
    splash->showMessage(msg, Qt::AlignBottom|Qt::AlignRight);
    qApp->processEvents();

    /* legge il file xml e riempie le strutture dati */
    if (this->readXml(xml))
    {
        this->readData();
    } else {
        Log::Logger::getInstance()->log("Errore nella lettura del file della biblioteca.", Log::FATAL);
    }
}

BibliotecaXml* BibliotecaXml::getInstance()
{
    if (BibliotecaXml::instance_ptr == NULL)
        BibliotecaXml::instance_ptr = new BibliotecaXml();
    return BibliotecaXml::instance_ptr;
}

BibliotecaItem* BibliotecaXml::getItem(QString id){
    QString arguedId = this->getArguedId(id);
    if (this->items.contains(arguedId)) {
        return this->items[arguedId];
    }
    else {
        return NULL;
    }
}


bool BibliotecaXml::readXml(QString _file)
{
    bool ret = false;
    QFileInfo file(_file);

    try {
        this->biblio = ::gestColl::biblioteca::biblioteca_(file.canonicalFilePath().toStdWString(), xml_schema::flags::dont_validate);
        ret = true;
    }
    catch (const xml_schema::exception& e)
    {
        QString msg = QString("%1 - %2").arg(_file).arg(e.what());
        std::wcerr << e << std::endl;
        Log::Logger::getInstance()->log(msg, Log::FATAL);
        exit(-1);
    }
    catch (const xml_schema::properties::argument&)
    {
        QString msg = QString("%1 - %2").arg(_file).arg("invalid property argument (empty namespace or location)");
        Log::Logger::getInstance()->log(msg, Log::FATAL);
        exit(-1);
    }
    catch (const xsd::cxx::xml::invalid_utf16_string&)
    {
        QString msg = QString("%1 - %2").arg(_file).arg("invalid UTF-16 text in DOM model");
        Log::Logger::getInstance()->log(msg, Log::FATAL);
        exit(-1);
    }
    catch (const xsd::cxx::xml::invalid_utf8_string&)
    {
        QString msg = QString("%1 - %2").arg(_file).arg("invalid UTF-8 text in object model");
        Log::Logger::getInstance()->log(msg, Log::FATAL);
        exit(-1);
    }
    catch (...)
    {
        QString msg = QString("%1 - %2").arg(_file).arg("Unknown exception");
        Log::Logger::getInstance()->log(msg, Log::FATAL);
        exit(-1);
    }
    return ret;
}



void BibliotecaXml::readData()
{
    /* cicla su tutti i libri */
    for (::gestColl::biblioteca::libri::libro_iterator it = biblio->libri().libro().begin();
    it != biblio->libri().libro().end();
    ++it)
    {
        QStringList listaAutori;
        QStringList listaSupporti;

        /* estrae le informazioni principali */
        QString id = QString::fromStdWString(it->id());
        QString titolo = QString::fromStdWString(it->titolo());

        /* estrae gli autori */
        if (it->autori().present())
        {
            ::gestColl::biblioteca::librotype::autori_type autori = it->autori().get();
            for (::gestColl::biblioteca::autori::autore_iterator aut = autori.autore().begin();
            aut != autori.autore().end();
            ++aut)
            {
                listaAutori << QString::fromStdWString(*aut);
            }

        }
        /* estrae i supporti */
        if (it->supporti().present())
        {
            ::gestColl::biblioteca::librotype::supporti_type supporti = it->supporti().get();
            for (::gestColl::biblioteca::supporti::supporto_iterator sup = supporti.supporto().begin();
            sup != supporti.supporto().end();
            ++sup)
            {
                listaSupporti << QString::fromStdWString(*sup);
            }

        }
        QString filename = "";
        if (it->filename().present())
            filename = QString::fromStdWString(it->filename().get());

        /* estrae gli argomenti */
        QStringList listaArgomenti;
        if (it->argomenti().present())
        {
            foreach (::gestColl::biblioteca::argomenti::argomento_type arg, it->argomenti().get().argomento()) {
                listaArgomenti << QString::fromStdWString(arg);
            }

        }


        /* aggiorna la vista */
        BibliotecaItem* item = new BibliotecaItem(id, titolo, filename, listaAutori, listaSupporti, listaArgomenti);
        QString arguedId = this->getArguedId(id);
        if (this->items.contains(arguedId)) {
            Log::Logger::getInstance()->log(QString("Trovato id duplicato: %1").arg(id), Log::ERR);
        } else {
            this->items[arguedId] = item;
        }
    }

    /* cicla su tutti i cataloghi */
    for (::gestColl::biblioteca::cataloghi::catalogo_iterator it = biblio->cataloghi().catalogo().begin();
    it != biblio->cataloghi().catalogo().end();
    ++it)
    {

        QStringList listaAutori;
        QStringList listaSupporti;
        QStringList listaArgomenti;

        /* estrae le informazioni principali */
        QString numero = QString::fromStdWString(it->numero());
        //TODO QString data = QString::fromStdWString(it->data());
        //TODO QString filename = QString::fromStdWString(it->filename());

        /* estrae gli autori */
        ::gestColl::biblioteca::librotype::autori_type autori = it->autori();
        for (::gestColl::biblioteca::autori::autore_iterator aut = autori.autore().begin();
             aut != autori.autore().end();
             ++aut)
        {
            listaAutori << QString::fromStdWString(*aut);
        }
        /* estrae i supporti */
        if (it->supporti().present())
        {
            ::gestColl::biblioteca::librotype::supporti_type supporti = it->supporti().get();
            for (::gestColl::biblioteca::supporti::supporto_iterator sup = supporti.supporto().begin();
            sup != supporti.supporto().end();
            ++sup)
            {
                listaSupporti << QString::fromStdWString(*sup);
            }

        }

        /* estrae gli argomenti */
        if (it->argomenti().present())
        {
            foreach (::gestColl::biblioteca::argomenti::argomento_type arg, it->argomenti().get().argomento()) {
                listaArgomenti << QString::fromStdWString(arg);
            }

        }

        QString filename = "";
        if (it->filename().present())
            filename = QString::fromStdWString(it->filename().get());


        /* aggiorna la vista */
        BibliotecaItem* item = new BibliotecaItem(numero, "", filename, listaAutori, listaSupporti, listaArgomenti);
        QString id = QString("%1-%2").arg(listaAutori.first()).arg(numero);
        if (this->items.contains(id)) {
            Log::Logger::getInstance()->log(QString("Trovato id duplicato: %1").arg(id), Log::ERR);
        } else {
            this->items[id] = item;
        }

    }
}


QString BibliotecaXml::getArguedId(const QString & id) {
    QString arguedId = id.simplified();
    arguedId = arguedId.remove('.');
    arguedId = arguedId.remove('-');
    arguedId = arguedId.remove(',');
    arguedId = arguedId.simplified();
    arguedId = arguedId.toUpper();
    return arguedId;
}

int BibliotecaXml::size() {
    return this->items.count();
}


