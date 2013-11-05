#-------------------------------------------------
#
# Project created by QtCreator 2011-03-29T21:54:33
#
#-------------------------------------------------
MAINDEST = ../bin/

unix {
 OS = lin
}
win32 {
 OS = win
}

TARGET  = GestColl

CONFIG(debug, debug|release) {
    DESTDIR = $${MAINDEST}bin_$${OS}_Debug
    OBJECTS_DIR = ../build_temp/debug/obj
    MOC_DIR     = ../build_temp/debug/moc
    UI_DIR      = ../build_temp/debug/ui
    RCC_DIR     = ../build_temp/debug/rcc
}

CONFIG(release, debug|release) {
    DESTDIR = $${MAINDEST}bin_$${OS}
    OBJECTS_DIR = ../build_temp/release/obj
    MOC_DIR     = ../build_temp/release/moc
    UI_DIR      = ../build_temp/release/ui
    RCC_DIR     = ../build_temp/release/rcc

}

#message($${QMAKE_HOST.arch} $${QMAKE_HOST.name} $${QMAKE_HOST.host} $${QMAKE_HOST.os} $${QMAKE_HOST.version})

unix {
LIBS +=   -Lother_libs/$${OS} -lxerces-c_3
#-lqrencode \
#-lbz2
}

win32 {
LIBS +=   -Lother_libs/$${OS} -lxerces-c_3 \
          -Lother_libs/$${OS} -llibbz2
}


QT     += core gui xmlpatterns xml
greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TEMPLATE = app





# Copia i file delle dll nella cartella giusta.
# Viene invocato dal comando make install (cfr. Projects)
# Il link al comando "install" viene fatto con INSTALLS+=...
# NB viene copiato tutto, indipendentemente dalle dll debug/release (da modificare)
#copydll.files += $${MAINDEST}libs_$${OS}/*.*
#copydll.files += other_libs/qrencode-3.1.1/.libs/*
#copydll.path += $${DESTDIR}
#INSTALLS += copydll

INCLUDEPATH += other_libs \
               Xml \
               Utils \
               Common \
               Data \
               Data/Biblioteca \
               Data/Collezione \
               Data/Link \
               Data/Vassoi \
               Data/Contatti \
               GUI/MainWindow \
               GUI/Widget \
               GUI/Models \
               GUI/Biblioteca \
               GUI/Links \
               GUI/Contatti \
               GUI/Moneta \
               GUI/Statistiche \
               GUI/Blocknotes \
               GUI/Collezione


unix {
INCLUDEPATH += /dati/develop/Qt/xerces-c-3.1.1/src \
               /dati/develop/Qt/xsd-3.3.0-i686-linux-gnu/libxsd
}
win32 {
INCLUDEPATH += "other_libs/win/xercesc/src" \
               "other_libs/win"
}

SOURCES  +=    Common/commondata.cpp \
               Common/gestlog.cpp \
               Common/commondefs.cpp \
               Utils/fileremover.cpp \
               Utils/utils.cpp \
               Utils/worker.cpp \
               Xml/texgenerator.cpp \
               Xml/xsltconverter.cpp \
               Data/Collezione/collezionexml.cpp \
               Data/Collezione/scheda.cxx \
               Data/Collezione/monetaxml.cpp \
               Data/Vassoi/vassoioxml.cpp \
               Data/Contatti/contattixml.cpp \
               Data/Biblioteca/biblioteca.cxx \
               Data/Biblioteca/bibliotecaitem.cpp \
               Data/Biblioteca/bibliotecaxml.cpp \
               Data/Contatti/contatti.cxx \
               Data/Link/link.cpp \
               Data/Link/links.cxx \
               Data/Vassoi/contenitori.cxx \
               Data/Vassoi/posizioni.cpp \
               GUI/Biblioteca/bibliotecaform.cpp \
               GUI/Links/linksform.cpp \
               GUI/MainWindow/aboutdialog.cpp \
               GUI/MainWindow/main.cpp \
               GUI/MainWindow/mainwindow.cpp \
               GUI/MainWindow/viewlog.cpp \
               GUI/Models/genericmodel.cpp \
               GUI/Models/generictabmodel.cpp \
               GUI/Moneta/autoritadialog.cpp \
               GUI/Moneta/charselector.cpp \
               GUI/Moneta/descrizionedialog.cpp \
               GUI/Moneta/imgmoneta.cpp \
               GUI/Moneta/legendadialog.cpp \
               GUI/Moneta/letteraturadialog.cpp \
               GUI/Moneta/misuraform.cpp \
               GUI/Moneta/monetaform.cpp \
               GUI/Moneta/notadialog.cpp \
               GUI/Moneta/nuovamonetadialog.cpp \
               GUI/Moneta/posizionedialog.cpp \
               GUI/Moneta/vassoioform.cpp \
               GUI/Moneta/visualizzaimmagine.cpp \
               GUI/Moneta/zecchieredialog.cpp \
               GUI/Statistiche/statisticheform.cpp \
               GUI/Statistiche/nightcharts.cpp \
               GUI/Statistiche/statisticamoneteperanno.cpp \
               GUI/Statistiche/statisticamonetepermetallo.cpp \
               GUI/Statistiche/statisticamonetepernominale.cpp \
               GUI/Statistiche/anomaliemonete.cpp \
               GUI/Contatti/contattiform.cpp \
               GUI/Statistiche/sommariomonete.cpp \
               GUI/MainWindow/qledindicator.cpp \
               GUI/Blocknotes/blocknotesform.cpp \
               GUI/MainWindow/opzionidialog.cpp \
               GUI/Contatti/newcontattodialog.cpp \
               GUI/Moneta/adddocumentdialog.cpp \
               GUI/Moneta/setambitodialog.cpp \
               GUI/Collezione/setcollezioneinfodialog.cpp \
               GUI/Collezione/modifyambitodialog.cpp \
               GUI/Moneta/setimmaginemonetadialog.cpp \
               Data/Link/linksxml.cpp \
               GUI/Links/treeitem.cpp \
               GUI/Links/newlinkdialog.cpp \
               GUI/MainWindow/modifiche.cpp \
    GUI/Models/elencomonetedelegate.cpp \
    GUI/Models/collezionesortfilterproxymodel.cpp \
    GUI/Models/bibliotecasortfilterproxymodel.cpp

HEADERS  +=    Common/commondata.h \
               Common/commondefs.h \
               Common/gestlog.h \
               Utils/fileremover.h \
               Utils/utils.h \
               Utils/worker.h \
               Xml/texgenerator.h \
               Xml/xsltconverter.h \
               other_libs/qrencode.h \
               other_libs/bzlib.h \
               Data/Collezione/collezionexml.h \
               Data/Collezione/scheda.hxx \
               Data/Collezione/monetaxml.h \
               Data/Vassoi/vassoioxml.h \
               Data/Contatti/contattixml.h \
               Data/Biblioteca/biblioteca.hxx \
               Data/Biblioteca/bibliotecaitem.h \
               Data/Contatti/contatti.hxx \
               Data/Link/link.h \
               Data/Link/links.hxx \
               Data/Vassoi/contenitori.hxx \
               Data/Vassoi/posizioni.h \
               Data/genericitem.h \
               Data/Biblioteca/bibliotecaxml.h \
               GUI/Biblioteca/bibliotecaform.h \
               GUI/Links/linksform.h \
               GUI/MainWindow/aboutdialog.h \
               GUI/MainWindow/mainwindow.h \
               GUI/MainWindow/viewlog.h \
               GUI/Models/genericmodel.h \
               GUI/Models/generictabmodel.h \
               GUI/Moneta/autoritadialog.h \
               GUI/Moneta/charselector.h \
               GUI/Moneta/descrizionedialog.h \
               GUI/Moneta/imgmoneta.h \
               GUI/Moneta/legendadialog.h \
               GUI/Moneta/letteraturadialog.h \
               GUI/Moneta/misuraform.h \
               GUI/Moneta/monetaform.h \
               GUI/Moneta/notadialog.h \
               GUI/Moneta/nuovamonetadialog.h \
               GUI/Moneta/posizionedialog.h \
               GUI/Moneta/vassoioform.h \
               GUI/Moneta/visualizzaimmagine.h \
               GUI/Moneta/zecchieredialog.h \
               GUI/Statistiche/statisticheform.h \
               GUI/Statistiche/nightcharts.h \
               GUI/Statistiche/statisticamoneteperanno.h \
               GUI/Statistiche/statisticamonetepermetallo.h \
               GUI/Statistiche/statisticamonetepernominale.h \
               GUI/Statistiche/anomaliemonete.h \
               GUI/Contatti/contattiform.h \
               GUI/Statistiche/sommariomonete.h \
               GUI/MainWindow/qledindicator.h \
               GUI/Blocknotes/blocknotesform.h \
               GUI/MainWindow/opzionidialog.h \
               GUI/Contatti/newcontattodialog.h \
               GUI/Moneta/adddocumentdialog.h \
               GUI/Moneta/setambitodialog.h \
               GUI/Collezione/setcollezioneinfodialog.h \
               GUI/Collezione/modifyambitodialog.h \
               GUI/Moneta/setimmaginemonetadialog.h \
               Data/Link/linksxml.h \
               GUI/Links/treeitem.h \
               GUI/Links/newlinkdialog.h \
               GUI/MainWindow/modifiche.h \
    GUI/Models/elencomonetedelegate.h \
    GUI/Models/collezionesortfilterproxymodel.h \
    GUI/Models/bibliotecasortfilterproxymodel.h




FORMS    += GUI/MainWindow/mainwindow.ui \
            GUI/Biblioteca/bibliotecaform.ui \
            GUI/Moneta/monetaform.ui \
            GUI/Moneta/misuraform.ui \
            GUI/Moneta/visualizzaimmagine.ui \
            GUI/MainWindow/aboutdialog.ui \
            GUI/Moneta/letteraturadialog.ui \
            GUI/Moneta/notadialog.ui \
            GUI/Moneta/autoritadialog.ui \
            GUI/Moneta/legendadialog.ui \
            GUI/Moneta/zecchieredialog.ui \
            GUI/Moneta/nuovamonetadialog.ui \
            GUI/Moneta/vassoioform.ui \
            GUI/MainWindow/viewlog.ui \
            GUI/Links/linksform.ui \
            GUI/Moneta/posizionedialog.ui \
            GUI/Moneta/charselector.ui \
            GUI/Moneta/descrizionedialog.ui \
            GUI/Statistiche/statisticheform.ui \
            GUI/Statistiche/statisticamoneteperanno.ui \
            GUI/Statistiche/statisticamonetepermetallo.ui \
            GUI/Statistiche/statisticamonetepernominale.ui \
            GUI/Statistiche/anomaliemonete.ui \
            GUI/Contatti/contattiform.ui \
            GUI/Statistiche/sommariomonete.ui \
            GUI/Blocknotes/blocknotesform.ui \
            GUI/MainWindow/opzionidialog.ui \
            GUI/Contatti/newcontattodialog.ui \
            GUI/Moneta/adddocumentdialog.ui \
            GUI/Moneta/setambitodialog.ui \
            GUI/Collezione/setcollezioneinfodialog.ui \
            GUI/Collezione/modifyambitodialog.ui \
            GUI/Moneta/setimmaginemonetadialog.ui \
            GUI/Links/newlinkdialog.ui


RESOURCES += resources/resources.qrc

RC_FILE = resources/myapp.rc

OTHER_FILES += \
    FUTURE_RELEASE.txt \
    HISTORY.txt


QMAKE_CXXFLAGS += -Zc:wchar_t
QMAKE_CFLAGS_DEBUG += -Zc:wchar_t
QMAKE_CFLAGS_RELEASE += -Zc:wchar_t

