#include <QApplication>
#include "mainwindow.h"

#include <commondata.h>
#include <gestlog.h>
#include <xsltconverter.h>
#include <QSplashScreen>
#include <QPainter>
#include <QDebug>

//TODO fare la documentazione
/** \mainpage
 * GestColl bla bla bla
 *
*/

QSplashScreen* splash;

/**
  inizializza il logger con le informazioni estratte dal file ini o dai default.
  */
void startLog() {
    /* setta il filename (default log.log) */
    QString logfilename = CommonData::getInstance()->getLogName("log.log");
    Log::Logger::setLogFilename(logfilename);

    /* setta il livello (default debug) */
    QString livello = CommonData::getInstance()->getLogLevel("DEBUG");
    Log::LogLevel logLevel = Log::Logger::getLivelloLog(livello);
    Log::Logger::getInstance()->setLivelloLog(logLevel);
    Log::Logger::getInstance()->start();

}

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    Errors ret = CommonData::getInstance()->readIni();
    switch (ret) {
        case INIFILE_NOTFOUND: {
            qWarning() << "File ini non trovato";
        }
            break;
        case INIFILE_WRONG: {
            qWarning() << "File ini errato";
        }
            break;
        case INIFILE_NOERR: {
            //tutto ok
        }
            break;
    }

    //inizia il logging
    startLog();

    //mette nel log (trace) il nome dell'applicazione
    Log::Logger::getInstance()->log(CommonData::getInstance()->getAppId(), Log::TRACE);

    /* splash screen */
    QPixmap pixmap(":/icons/splash.png");
    splash = new QSplashScreen(pixmap);



    splash->show();
    splash->showMessage("Wait...", Qt::AlignBottom|Qt::AlignRight);
    a.processEvents();//This is used to accept a click on the screen so that user can cancel the screen

    MainWindow w;

    QFile styleSheet("style.css");
    if (styleSheet.open(QIODevice::ReadOnly)) {
        w.setStyleSheet(styleSheet.readAll());
    }
    styleSheet.close();
    w.showMaximized();

    if (ret == INIFILE_WRONG) {
        /* todo aprire la finestra opzioni */
        w.on_actionOpzioni_triggered();
    }

    //chiude lo splash screen
    splash->finish(&w);
    int x = a.exec();
    if (splash != NULL)
    {
        delete splash;
        splash = NULL;
    }
    qWarning() << x;
    return x;
}
