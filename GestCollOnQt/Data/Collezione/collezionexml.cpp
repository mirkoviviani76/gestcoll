#include "collezionexml.h"

#include <QString>
#include <QSplashScreen>
#include <QDebug>
#include "commondata.h"
#include <iostream>
#include "gestlog.h"
#include <fstream>
#include <QUrl>
#include <QTextStream>
#include <QApplication>
#include <QResource>

// initialize pointer
CollezioneXml* CollezioneXml::instance_ptr = NULL;

extern QSplashScreen* splash;

CollezioneXml::CollezioneXml()
{
    QString xmlFile = CommonData::getInstance()->getCollezione();
    this->readData(xmlFile);
}

CollezioneXml::~CollezioneXml() {
    foreach (QString key, this->moneteInCollezione.keys()) {
        if (this->moneteInCollezione[key] != NULL) {
            delete this->moneteInCollezione[key];
            this->moneteInCollezione[key] = NULL;
        }
    }

    this->moneteInCollezione.clear();
    if (this->info != NULL) {
        delete this->info;
        this->info = NULL;
    }

}

void CollezioneXml::readData(QString xmlFile) {
    QFileInfo fi(xmlFile);
    if (fi.isFile()) {
        //legge l'xml
        this->readXml(xmlFile);
        /* carica i dati delle monete */
        foreach (moneta m, this->collezione->moneta()) {
            MonetaXml* curr = new MonetaXml(m);
            Log::Logger::getInstance()->log(QString("Letto %1").arg(curr->getId()), Log::TRACE);
            this->moneteInCollezione[QString::fromStdWString(m.id())] = curr;
        }

        /* carica i dati delle info */
        this->info = new xml::Info;
        QString proprietario = QString::fromStdWString(this->collezione->info().proprietario());
        this->info->proprietario = proprietario;

        QString titolo = QString::fromStdWString(this->collezione->info().titolo());
        this->info->titolo = titolo;

        ::info::inizio_type inizioXml = this->collezione->info().inizio();
        QDate inizio(inizioXml.year(), inizioXml.month(), inizioXml.day());
        this->info->inizio = inizio;

        foreach (gestColl::coins::ambito a, this->collezione->info().ambiti().ambito()) {
            QString titolo = QString::fromStdWString(a.titolo());
            QString icon = "";
            if (a.icon().present()) {
                icon = QString::fromStdWString(a.icon().get());
            }

            xml::Ambito* ambito = new xml::Ambito(titolo, icon);
            this->info->add(ambito);
        }
    }
}


MonetaXml* CollezioneXml::getMoneta(QString id) {
    return this->moneteInCollezione[id];
}

CollezioneXml* CollezioneXml::getInstance() {
    if (instance_ptr == NULL) {
        instance_ptr = new CollezioneXml;
    }
    return instance_ptr;
}


MonetaXml* CollezioneXml::addMoneta(QString id, QString anno, int contenitore, int vassoio, int riga, int colonna) {

    MonetaXml* ret = NULL;

    /* apre uno stringstream con la stringa xml recuperata dal template */
    QString filename = CommonData::getInstance()->getMonetaTemplate();
    QFile file(filename);
    file.open(QIODevice::ReadOnly);
    QString allXml = file.readAll();
    file.close();

    /* sistema le immagini */
    allXml = allXml.replace("IMG_D", id+"-D.jpg");
    allXml = allXml.replace("IMG_R", id+"-R.jpg");
    //allXml = allXml.replace("IMG_T", id+"-T.jpg");
    allXml = allXml.replace("IMG_T", "");
    /* sistema la revisione */
    QString today = QDate::currentDate().toString("yyyy-MM-dd");
    allXml = allXml.replace("TODAY", today);

    std::istringstream iss(allXml.toStdString());

    try {
        /* estrae la moneta */
        ::std::auto_ptr< ::gestColl::coins::monete > temp = (monete_(iss, xml_schema::flags::dont_validate));
        moneta m = temp->moneta().at(0);
        m.id(id.toStdWString());
        MonetaXml* mon = new MonetaXml(m);
        this->moneteInCollezione[id] = mon;
        mon->setPosizione(contenitore, vassoio, riga, colonna);
        mon->getDom()->anno(anno.toStdWString());

        //verra' salvata al momento richiesto
        this->collezione->moneta().push_back(m);
        ret = mon;

        Log::Logger::getInstance()->log(QString("Aggiunta moneta %1").arg(mon->getId()), Log::TRACE);
    }
    catch (const xml_schema::exception& e)
    {
        QString msg = QString("%1 - %2").arg(filename).arg(e.what());
        std::wcerr << e;
        Log::Logger::getInstance()->log(msg, Log::FATAL);
    }
    catch (const xml_schema::properties::argument&)
    {
        QString msg = QString("%1 - %2").arg(filename).arg("invalid property argument (empty namespace or location)");
        Log::Logger::getInstance()->log(msg, Log::FATAL);
    }
    catch (const xsd::cxx::xml::invalid_utf16_string&)
    {
        QString msg = QString("%1 - %2").arg(filename).arg("invalid UTF-16 text in DOM model");
        Log::Logger::getInstance()->log(msg, Log::FATAL);
    }
    catch (const xsd::cxx::xml::invalid_utf8_string&)
    {
        QString msg = QString("%1 - %2").arg(filename).arg("invalid UTF-8 text in object model");
        Log::Logger::getInstance()->log(msg, Log::FATAL);
    }
    catch (...)
    {
        QString msg = QString("%1 - %2").arg(filename).arg("Unknown exception");
        Log::Logger::getInstance()->log(msg, Log::FATAL);
    }

    return ret;

}

void CollezioneXml::readXml(QFileInfo file)
{
    QString filename = file.canonicalFilePath();
    try {
        this->collezione = (monete_(file.canonicalFilePath().toStdWString(), xml_schema::flags::dont_validate));
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


bool CollezioneXml::save()
{
    bool ret = false;

    //ottiene una sequenza vuota
    ::gestColl::coins::monete::moneta_sequence seq = ::gestColl::coins::monete::moneta_sequence();

    /* cicla su tutte le monete */
    foreach (QString id, this->moneteInCollezione.keys()) {
        /* inserisce la moneta nella sequenza */
        seq.push_back(*(this->getMoneta(id)->getDom()));
    }
    /* modifica la collezione */
    this->collezione.get()->moneta(seq);



    /* scrive su file */
    xml_schema::namespace_infomap xmlmap;
    xmlmap[L""].name = L"http://gestColl/coins";
    xmlmap[L""].schema = L"scheda.xsd";
    std::ofstream myfile;
    myfile.open (CommonData::getInstance()->getCollezione().toLatin1());
    if (myfile.is_open())
    {
        ::gestColl::coins::monete_(myfile, *(this->collezione), xmlmap);
        myfile.close();
        ret = true;
    }
    else
    {
        ret = false;
    }
    return ret;

}


xml::Info* CollezioneXml::getInfo() {
    return this->info;
}

void CollezioneXml::setInfo(xml::Info* _info) {
    this->info = _info;

    /* salva i dati delle info nel dom */
    this->collezione->info().proprietario(this->info->proprietario.toStdWString());
    this->collezione->info().titolo(this->info->titolo.toStdWString());
    gestColl::coins::info::inizio_type xmlInizio(this->info->inizio.year(), this->info->inizio.month(), this->info->inizio.day());
    this->collezione->info().inizio(xmlInizio);

    gestColl::coins::ambiti::ambito_sequence let;
    foreach (xml::Ambito* a, this->info->ambiti) {
        gestColl::coins::ambito curAmbito(a->titolo.toStdWString());
        curAmbito.icon(a->icona.toStdWString());
        let.push_back(curAmbito);
    }
    this->collezione->info().ambiti().ambito(let);
}


void CollezioneXml::addAmbito(xml::Ambito *a) {
    this->info->ambiti.append(a);
}

void CollezioneXml::updateAmbitiInCoins(const xml::Ambito& vecchio, const xml::Ambito& nuovo) {
    foreach (QString id, this->getAllId()) {
        MonetaXml* m = this->moneteInCollezione[id];
        bool updated = m->updateAmbiti(vecchio, nuovo);
        if (updated) {
            qDebug() << "Modificato ambito in " << id;
        }
    }
}


