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
               GUI/Moneta/DatiFisici \
               GUI/Moneta/DatiAcquisto \
               GUI/Moneta/Descrizione \
               GUI/Moneta/Documenti \
               GUI/Moneta/Immagini \
               GUI/Moneta/Emissione \
               GUI/Moneta/Letteratura \
               GUI/Moneta/Moneta \
               GUI/Moneta/Note \
               GUI/Moneta/Posizione \
               GUI/Moneta/Utilita \
               GUI/Moneta/Vassoi \
               GUI/Moneta/Zecca \
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
               GUI/Moneta/Ambito/setambitodialog.cpp \
               GUI/Moneta/Descrizione/descrizionedialog.cpp \
               GUI/Moneta/Immagini/imgmoneta.cpp \
               GUI/Moneta/Immagini/setimmaginemonetadialog.cpp \
               GUI/Moneta/Utilita/visualizzaimmagine.cpp \
               GUI/Moneta/Descrizione/legendadialog.cpp \
               GUI/Moneta/Moneta/monetaform.cpp \
               GUI/Moneta/Moneta/nuovamonetadialog.cpp \
               GUI/Moneta/Note/notadialog.cpp \
               GUI/Moneta/Posizione/posizionedialog.cpp \
               GUI/Moneta/Utilita/charselector.cpp \
               GUI/Moneta/Utilita/misuraform.cpp \
               GUI/Moneta/Vassoi/vassoioform.cpp \
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
    GUI/Moneta/Descrizione/descrizioneform.cpp \
    GUI/Moneta/Descrizione/modellolegenda.cpp \
    GUI/Moneta/Emissione/modelloautorita.cpp \
    GUI/Moneta/Emissione/autoritaform.cpp \
    GUI/Moneta/DatiAcquisto/datiacquistowidget.cpp \
    GUI/Moneta/DatiAcquisto/datiacquistomodel.cpp \
    GUI/Moneta/DatiFisici/datifisiciwidget.cpp \
    GUI/Moneta/Zecca/zeccawidget.cpp \
    GUI/Moneta/Zecca/zecchieremodel.cpp \
    GUI/Moneta/Note/noteform.cpp \
    GUI/Moneta/Note/notamodel.cpp \
    GUI/Moneta/Emissione/emissioneform.cpp \
    GUI/Moneta/Emissione/emissionemodel.cpp \
    GUI/Moneta/Letteratura/letteraturaform.cpp \
    GUI/Moneta/Letteratura/letteraturamodel.cpp \
    GUI/Biblioteca/bibliotecamodel.cpp \
    GUI/Biblioteca/bibliotecadelegate.cpp \
    GUI/Moneta/Documenti/itemaddizionaliwidget.cpp \
    GUI/Moneta/Documenti/documentimodel.cpp \
    GUI/Moneta/Documenti/adddocumentdialog.cpp \
    GUI/Contatti/contattomodel.cpp \
    GUI/Moneta/Vassoi/vassoiomodel.cpp \
    GUI/Collezione/collezionemodel.cpp

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
               GUI/Moneta/Ambito/setambitodialog.h \
               GUI/Moneta/Descrizione/descrizionedialog.h \
               GUI/Moneta/Immagini/imgmoneta.h \
               GUI/Moneta/Immagini/setimmaginemonetadialog.h \
               GUI/Moneta/Utilita/visualizzaimmagine.h \
               GUI/Moneta/Descrizione/legendadialog.h \
               GUI/Moneta/Moneta/monetaform.h \
               GUI/Moneta/Moneta/nuovamonetadialog.h \
               GUI/Moneta/Note/notadialog.h \
               GUI/Moneta/Posizione/posizionedialog.h \
               GUI/Moneta/Utilita/charselector.h \
               GUI/Moneta/Utilita/misuraform.h \
               GUI/Moneta/Vassoi/vassoioform.h \
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
    GUI/Moneta/Descrizione/descrizioneform.h \
    GUI/Moneta/Descrizione/modellolegenda.h \
    GUI/Moneta/Emissione/modelloautorita.h \
    GUI/Moneta/Emissione/autoritaform.h \
    GUI/Moneta/DatiAcquisto/datiacquistowidget.h \
    GUI/Moneta/DatiAcquisto/datiacquistomodel.h \
    GUI/Moneta/DatiFisici/datifisiciwidget.h \
    GUI/Moneta/Zecca/zeccawidget.h \
    GUI/Moneta/Zecca/zecchieremodel.h \
    GUI/Moneta/Note/noteform.h \
    GUI/Moneta/Note/notamodel.h \
    GUI/Moneta/Emissione/emissioneform.h \
    GUI/Moneta/Emissione/emissionemodel.h \
    GUI/Moneta/Letteratura/letteraturaform.h \
    GUI/Moneta/Letteratura/letteraturamodel.h \
    GUI/Biblioteca/bibliotecamodel.h \
    GUI/Biblioteca/bibliotecadelegate.h \
    GUI/Moneta/Documenti/itemaddizionaliwidget.h \
    GUI/Moneta/Documenti/documentimodel.h \
    GUI/Moneta/Documenti/adddocumentdialog.h \
    GUI/Contatti/contattomodel.h \
    GUI/Moneta/Vassoi/vassoiomodel.h \
    GUI/Collezione/collezionemodel.h




FORMS    += GUI/MainWindow/mainwindow.ui \
            GUI/Biblioteca/bibliotecaform.ui \
            GUI/MainWindow/aboutdialog.ui \
            GUI/MainWindow/viewlog.ui \
            GUI/Links/linksform.ui \
               GUI/Moneta/Ambito\setambitodialog.ui \
               GUI/Moneta/Descrizione\descrizionedialog.ui \
               GUI/Moneta/Immagini\setimmaginemonetadialog.ui \
               GUI/Moneta/Utilita\visualizzaimmagine.ui \
               GUI/Moneta/Descrizione\legendadialog.ui \
               GUI/Moneta/Moneta\monetaform.ui \
               GUI/Moneta/Moneta\nuovamonetadialog.ui \
               GUI/Moneta/Note\notadialog.ui \
               GUI/Moneta/Posizione\posizionedialog.ui \
               GUI/Moneta/Utilita\charselector.ui \
               GUI/Moneta/Utilita\misuraform.ui \
               GUI/Moneta/Vassoi\vassoioform.ui \
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
    GUI/Moneta/Emissione/autoritaform.ui \
    GUI/Moneta/DatiAcquisto/datiacquistowidget.ui \
    GUI/Moneta/DatiFisici/datifisiciwidget.ui \
    GUI/Moneta/Zecca/zeccawidget.ui \
    GUI/Moneta/Note/noteform.ui \
    GUI/Moneta/Emissione/emissioneform.ui \
    GUI/Moneta/Letteratura/letteraturaform.ui \
    GUI/Moneta/Documenti/itemaddizionaliwidget.ui \
    GUI/Moneta/Documenti/adddocumentdialog.ui


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
