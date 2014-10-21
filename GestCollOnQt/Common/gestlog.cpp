#include "gestlog.h"

#include <QMessageBox>
#include <QDateTime>
#include <commondata.h>
#include <iostream>

#include <QDebug>

using namespace Log;

/**
  inizializzazione membri statici
  */
Logger* Logger::currInstance = 0;
QMutex* Logger::mutex = 0;
QFile Logger::logFile("log.log");

Logger* Logger::getInstance()
{
    if (currInstance == 0)
    {
        currInstance = new Logger();
    }
    return currInstance;
}

Logger::Logger():
        QThread()
{
    this->stopThread = false;
    //crea il mutex
    this->mutex = new QMutex();
    //mette un default al log level
    this->curLogLevel = Log::DEBUG;

    //prepara il file di log (append + modo testo)
    if (!Logger::logFile.open(QIODevice::ReadWrite | QIODevice::Append | QIODevice::Text))
    {
        QMessageBox::critical(NULL, "GestColl", "Errore durante l'apertura del file di log");
    }

    this->start();

}


LogLevel Logger::getLivelloLog()
{
    return this->curLogLevel;
}

LogLevel Logger::getLivelloLog(QString livello)
{
    LogLevel logLevel = Log::UNKNOWN;
    if (livello == "TRACE")
        logLevel = Log::TRACE;
    else if (livello == "DEBUG")
        logLevel = Log::DEBUG;
    else if (livello == "INFO")
        logLevel = Log::INFO;
    else if (livello == "WARN")
        logLevel = Log::WARN;
    else if (livello == "ERR")
        logLevel = Log::ERR;
    else if (livello == "FATAL")
        logLevel = Log::FATAL;
    return logLevel;
}



QString Logger::getLivelloLogAsString(LogLevel level)
{
    QString ret = "UNKNOWN";
    switch (level) {
    case 0: ret =  "TRACE  "; break;
    case 1: ret =  "DEBUG  "; break;
    case 2: ret =  "INFO   "; break;
    case 3: ret =  "WARN   "; break;
    case 4: ret =  "ERR    "; break;
    case 5: ret =  "FATAL  "; break;
    default: ret = "UNKNOWN"; break;
    }

    return ret;
}



/**
  Ottiene il livello di logging corrente come stringa

  @return il livello di logging
*/
QString Logger::getLivelloLogAsString()
{
    return this->getLivelloLogAsString(this->getLivelloLog());
}


/**
  Setta il livello di logging

  @param loglevel il livello di log
  */
void Logger::setLivelloLog(LogLevel loglevel)
{
    this->curLogLevel = loglevel;
}

/**
  logga un messaggio

  @param msg il messaggio da loggare
  @param loglevel il livello di logging da assegnare al messaggio
  */
void Logger::log(QString msg, LogLevel loglevel)
{
    /* se il livello di logging e' adeguato mette msg in coda */
    if (this->curLogLevel <= loglevel)
    {
        //ottiene il lock
        QMutexLocker lock(this->mutex);

        QString ts = QDateTime::currentDateTime().toString("dd-MM-yy HH:mm:ss");
        QString toLog = QString("%2    [%1]:  %3\n")
                .arg(this->getLivelloLogAsString(loglevel))
                .arg(ts)
                .arg(msg);
        //mette il messaggio in coda
        this->codaMsg.enqueue(toLog);
    }
}

/**
  Imposta il file di log.

  @param filename il nome del file di log
*/
void Logger::setLogFilename(QString filename)
{
    Logger::logFile.setFileName(filename);
}

/**
  chiude il logger
  */
void Logger::chiude()
{
    //stop al thread
    this->stopThread = true;
    this->msleep(200);


    /* flush dei messaggi */
    while (!this->codaMsg.empty())
    {
        //gestisce il messaggio
        this->setupMsg();

        this->msleep(1);
    }

    //chiude logfile
    this->logFile.close();

}

/**
  Esegue il logging
  */
void Logger::run()
{
    while ((this->isRunning()) && (this->stopThread == false))
    {
        //se c'e' qualcosa in coda...
        if (!this->codaMsg.empty())
        {
            //gestisce il messaggio
            this->setupMsg();
        }
        this->msleep(1);
    }

}

/**
  Prepara il messaggio per la scrittura: aggiunge l'ora e la data
  e le ulteriori informazioni opportune per il logging.
  */
void Logger::setupMsg()
{
    QMutexLocker locker(this->mutex);

    //ottiene il messaggio
    QString msg = this->codaMsg.dequeue();


    try
    {
        //scrive msg
        this->logFile.write(msg.toLatin1());
    }
    catch (...)
    {
        std::cerr << "Non posso scrivere su disco! Il file e' in uso da un altro programma." << std::endl;
    }

    //forza la scrittura
    this->logFile.flush();

}

