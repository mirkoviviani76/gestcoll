#-------------------------------------------------
#
# Project created by QtCreator 2011-03-29T21:54:33
#
#-------------------------------------------------
MAINDEST = ../bin/Qt/

unix {
 OS = lin
}
win32 {
 OS = win
}

TARGET  = GestColl

CONFIG(debug, debug|release) {
    DESTDIR = $${MAINDEST}$${OS}_Debug
    OBJECTS_DIR = ../build_temp/debug/obj
    MOC_DIR     = ../build_temp/debug/moc
    UI_DIR      = ../build_temp/debug/ui
    RCC_DIR     = ../build_temp/debug/rcc
}

CONFIG(release, debug|release) {
    DESTDIR = $${MAINDEST}$${OS}
    OBJECTS_DIR = ../build_temp/release/obj
    MOC_DIR     = ../build_temp/release/moc
    UI_DIR      = ../build_temp/release/ui
    RCC_DIR     = ../build_temp/release/rcc

}

#message($${QMAKE_HOST.arch} $${QMAKE_HOST.name} $${QMAKE_HOST.host} $${QMAKE_HOST.os} $${QMAKE_HOST.version})

unix {
LIBS +=   -Lother_libs/$${OS} -lxerces-c \
-lqrencode \
-lbz2
}

win32 {
LIBS +=   -Lother_libs/$${OS} -lxerces-c_3 \
          -Lother_libs/$${OS} -llibbz2 \
          -Lother_libs/$${OS} -llibqrencode
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
               GUI/Moneta/Ambito \
               GUI/Moneta/Autorita \
			   GUI/Moneta/DatiFisici \
               GUI/Moneta/Descrizione \
               GUI/Moneta/Documenti \
               GUI/Moneta/Immagini \
               GUI/Moneta/Legende \
               GUI/Moneta/Letteratura \
               GUI/Moneta/Moneta \
               GUI/Moneta/Note \
               GUI/Moneta/Posizione \
               GUI/Moneta/Utilita \
               GUI/Moneta/Vassoi \
               GUI/Moneta/Zecchiere \
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
               GUI/Moneta/Ambito/setambitodialog.cpp \
               GUI/Moneta/Autorita/autoritadialog.cpp \
               GUI/Moneta/Descrizione/descrizionedialog.cpp \
               GUI/Moneta/Documenti/adddocumentdialog.cpp \
               GUI/Moneta/Immagini/imgmoneta.cpp \
               GUI/Moneta/Immagini/setimmaginemonetadialog.cpp \
               GUI/Moneta/Utilita/visualizzaimmagine.cpp \
               GUI/Moneta/Legende/legendadialog.cpp \
               GUI/Moneta/Letteratura/letteraturadialog.cpp \
               GUI/Moneta/Moneta/monetaform.cpp \
               GUI/Moneta/Moneta/nuovamonetadialog.cpp \
               GUI/Moneta/Note/notadialog.cpp \
               GUI/Moneta/Posizione/posizionedialog.cpp \
               GUI/Moneta/Utilita/charselector.cpp \
               GUI/Moneta/Utilita/misuraform.cpp \
               GUI/Moneta/Vassoi/vassoioform.cpp \
               GUI/Moneta/Zecchiere/zecchieredialog.cpp \
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
               GUI/Collezione/setcollezioneinfodialog.cpp \
               GUI/Collezione/modifyambitodialog.cpp \
               Data/Link/linksxml.cpp \
               GUI/Links/treeitem.cpp \
               GUI/Links/newlinkdialog.cpp \
               GUI/MainWindow/modifiche.cpp \
    GUI/Collezione/elencomonetedelegate.cpp \
    GUI/Collezione/collezionesortfilterproxymodel.cpp \
    GUI/Biblioteca/bibliotecasortfilterproxymodel.cpp \
    GUI/Moneta/DatiFisici/datifisicimodel.cpp \
    GUI/Moneta/DatiFisici/datifisicidelegate.cpp \
    GUI/Moneta/Descrizione/descrizioneform.cpp \
    GUI/Moneta/Descrizione/modellolegenda.cpp \
    GUI/Moneta/Autorita/modelloautorita.cpp \
    GUI/Moneta/Autorita/autoritaform.cpp

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
               GUI/Moneta/Ambito/setambitodialog.h \
               GUI/Moneta/Autorita/autoritadialog.h \
               GUI/Moneta/Descrizione/descrizionedialog.h \
               GUI/Moneta/Documenti/adddocumentdialog.h \
               GUI/Moneta/Immagini/imgmoneta.h \
               GUI/Moneta/Immagini/setimmaginemonetadialog.h \
               GUI/Moneta/Utilita/visualizzaimmagine.h \
               GUI/Moneta/Legende/legendadialog.h \
               GUI/Moneta/Letteratura/letteraturadialog.h \
               GUI/Moneta/Moneta/monetaform.h \
               GUI/Moneta/Moneta/nuovamonetadialog.h \
               GUI/Moneta/Note/notadialog.h \
               GUI/Moneta/Posizione/posizionedialog.h \
               GUI/Moneta/Utilita/charselector.h \
               GUI/Moneta/Utilita/misuraform.h \
               GUI/Moneta/Vassoi/vassoioform.h \
               GUI/Moneta/Zecchiere/zecchieredialog.h \
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
               GUI/Collezione/setcollezioneinfodialog.h \
               GUI/Collezione/modifyambitodialog.h \
               Data/Link/linksxml.h \
               GUI/Links/treeitem.h \
               GUI/Links/newlinkdialog.h \
               GUI/MainWindow/modifiche.h \
    GUI/Collezione/elencomonetedelegate.h \
    GUI/Collezione/collezionesortfilterproxymodel.h \
    GUI/Biblioteca/bibliotecasortfilterproxymodel.h \
    GUI/Moneta/DatiFisici/datifisicimodel.h \
    GUI/Moneta/DatiFisici/datifisicidelegate.h \
    GUI/Moneta/Descrizione/descrizioneform.h \
    GUI/Moneta/Descrizione/modellolegenda.h \
    GUI/Moneta/Autorita/modelloautorita.h \
    GUI/Moneta/Autorita/autoritaform.h




FORMS    += GUI/MainWindow/mainwindow.ui \
            GUI/Biblioteca/bibliotecaform.ui \
            GUI/MainWindow/aboutdialog.ui \
            GUI/MainWindow/viewlog.ui \
            GUI/Links/linksform.ui \
               GUI/Moneta/Ambito\setambitodialog.ui \
               GUI/Moneta/Autorita\autoritadialog.ui \
               GUI/Moneta/Descrizione\descrizionedialog.ui \
               GUI/Moneta/Documenti\adddocumentdialog.ui \
               GUI/Moneta/Immagini\setimmaginemonetadialog.ui \
               GUI/Moneta/Utilita\visualizzaimmagine.ui \
               GUI/Moneta/Legende\legendadialog.ui \
               GUI/Moneta/Letteratura\letteraturadialog.ui \
               GUI/Moneta/Moneta\monetaform.ui \
               GUI/Moneta/Moneta\nuovamonetadialog.ui \
               GUI/Moneta/Note\notadialog.ui \
               GUI/Moneta/Posizione\posizionedialog.ui \
               GUI/Moneta/Utilita\charselector.ui \
               GUI/Moneta/Utilita\misuraform.ui \
               GUI/Moneta/Vassoi\vassoioform.ui \
               GUI/Moneta/Zecchiere\zecchieredialog.ui \
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
            GUI/Collezione/setcollezioneinfodialog.ui \
            GUI/Collezione/modifyambitodialog.ui \
            GUI/Links/newlinkdialog.ui \
    GUI/Moneta/Descrizione/descrizioneform.ui \
    GUI/Moneta/Autorita/autoritaform.ui


RESOURCES += resources/resources.qrc

RC_FILE = resources/myapp.rc

OTHER_FILES += \
    FUTURE_RELEASE.txt \
    HISTORY.txt

win32 {
QMAKE_CXXFLAGS += -Zc:wchar_t
QMAKE_CFLAGS_DEBUG += -Zc:wchar_t
QMAKE_CFLAGS_RELEASE += -Zc:wchar_t
}
