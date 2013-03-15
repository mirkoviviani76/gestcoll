#include "commondata.h"
#include <gestlog.h>
#include <QStringList>
#include <QFile>
#include <QApplication>
#include <QDebug>


CommonData* CommonData::instance_ptr = NULL;

CommonData::CommonData()
{
}

CommonData* CommonData::getInstance()
{
    if (CommonData::instance_ptr == NULL)
        CommonData::instance_ptr = new CommonData();
    return CommonData::instance_ptr;
}


bool CommonData::checkFileExists(QString file) {
    bool ret = true;
    if (!QFile(file).exists()) {
        if (file != "") {
            QString msg = QString("Ini file: %1 non esiste").arg(file);
            qCritical() << msg;
        } else {
            QString msg = QString("Ini file: è stato indicato un valore vuoto");
            qCritical() << msg;
        }
        ret = false;
    }
    return ret;
}

bool CommonData::checkIni() {
    bool ret = true;

    if (ret)
        ret = this->checkFileExists(getBinDir());
    if (!ret) {
        exit(INIFILE_WRONG); //questo errore e' fatale
    }
    if (ret)
        ret = this->checkFileExists(getImgDir());
    if (!ret) {
        exit(INIFILE_WRONG); //questo errore e' fatale
    }
    if (ret)
        ret = this->checkFileExists(getDocDir());
    if (!ret) {
        exit(INIFILE_WRONG); //questo errore e' fatale
    }
    if (ret)
        ret = this->checkFileExists(getReportDir());
    if (!ret) {
        exit(INIFILE_WRONG); //questo errore e' fatale
    }
    if (ret)
        ret = this->checkFileExists(getQrDir());
    if (ret)
        ret = this->checkFileExists(getBiblioDir());
    if (ret)
        ret = this->checkFileExists(getHtmlDir());
    if (ret)
        ret = this->checkFileExists(getBlocknotesDir());
    if (ret)
        ret = this->checkFileExists(getTexDir());
    if (ret)
        ret = this->checkFileExists(getCollezione());
    if (ret)
        ret = this->checkFileExists(getContenitori());
    if (ret)
        ret = this->checkFileExists(getContatti());
    if (ret)
        ret = this->checkFileExists(getBiblioteca());
    if (ret)
        ret = this->checkFileExists(getLinks());
    if (ret)
        ret = this->checkFileExists(getBackupDir());

    return ret;
}

Errors CommonData::readIni()
{
    Errors ret = INIFILE_NOERR;
    if (QFile::exists(INI_FILE))
    {
        //legge il file di configurazione
        QSettings configurazione(INI_FILE, QSettings::IniFormat);

        //legge tutte le chiavi
        QStringList chiavi = configurazione.allKeys();

        //cicla su ogni chiave
        foreach (QString chiave, chiavi)
        {
            //ottiene il valore
            QVariant valore = configurazione.value(chiave, "");
            //memorizza il valore
            this->iniSettings.insert(chiave, valore);
        }
        /* controlla il file ini e in caso di errore esce */
        if (!checkIni()) {
            qCritical("File ini non corretto.") ;
            ret = INIFILE_WRONG;
        }
    } else {
        qCritical("INI non trovato");
        ret = INIFILE_NOTFOUND;
    }


    return ret;
}

QString CommonData::getValue(QString key) {
    QString ret = "";
    if (this->iniSettings.contains(key)) {
        //estrae il valore come stringa
        ret = this->iniSettings[key].toString();
    } else {
        qCritical() << QString("Non trovo la chiave %1 nel file ini.").arg(key);
    }
    return ret;
}

QString CommonData::getValue(QString key, QString defaultValue) {
    QString ret = defaultValue;
    if (this->iniSettings.contains(key)) {
        //estrae il valore come stringa
        ret = this->iniSettings[key].toString();
    } else {
        qCritical() << QString("Non trovo la chiave %1 nel file ini.").arg(key);
    }
    return ret;
}

void CommonData::setValue(QString key, QString val) {
    this->iniSettings[key] = val;

    QSettings configurazione(INI_FILE, QSettings::IniFormat);
    configurazione.setValue(key, val);
}


