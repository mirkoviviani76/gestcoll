#ifndef GESTLOG_H
#define GESTLOG_H

#include <QMap>
#include <QQueue>
#include <QMutex>
#include <QThread>
#include <QFile>

namespace Log
{
/**
      mappa dei livelli di logging
      */
enum LogLevel
{
    TRACE = 0,  ///< livello TRACE
    DEBUG,      ///< livello DEBUG
    INFO,       ///< livello INFO
    WARN,       ///< livello WARN
    ERR,        ///< livello ERROR
    FATAL,      ///< livello FATAL
    UNKNOWN     ///< livello sconosciuto
};


/**
      Contiene le funzionalità relative al logging degli eventi.
      */
class Logger : public QThread
{
        Q_OBJECT
    public:
        void setLivelloLog(LogLevel loglevel);
        static void setLogFilename(QString filename);
        static inline QString getLogFilename() { return Logger::logFile.fileName(); }
        void log(QString msg, LogLevel loglevel = DEBUG);
        /**
          Ottiene il livello di logging dalla stringa

          @return il livello di logging
        */
        static LogLevel getLivelloLog(QString level);
        /**
          @brief Ottiene l'istanza corrente
          */
        static Logger* getInstance();

    public slots:
        void chiude();

    private:
        static QFile logFile;
        QQueue<QString> codaMsg;     ///< la coda dei messaggi
        LogLevel curLogLevel;        ///< il livello di logging
        static QMutex* mutex;        ///< il mutex
        static Logger* currInstance; ///< instanza del logger

        LogLevel getLivelloLog();
        /**
          Ottiene il livello di logging corrente

          @return il livello di logging
        */
        QString getLivelloLogAsString();
        /**
          Ottiene il livello di logging come stringa

          @return il livello di logging
        */
        QString getLivelloLogAsString(LogLevel level);
        void setupMsg();             ///< gestore dei messaggi
        Logger();                    ///< costruttore (privato)
        bool stopThread;             ///< flag per stop del thread

    protected:
        void run();

};
} //namespace

#endif // GESTLOG_H
