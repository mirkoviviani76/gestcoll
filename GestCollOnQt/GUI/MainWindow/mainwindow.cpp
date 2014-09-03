#include "mainwindow.h"
#include "ui_mainwindow.h"
#include <gestlog.h>
#include <commondata.h>
#include <xsltconverter.h>
#include <utils.h>
#include <fileremover.h>
#include <QProcess>
#include <QMessageBox>
#include <QDebug>
#include <QUrl>
#include <QDesktopServices>
#include <QProgressDialog>

#include "monetaxml.h"
#include "nuovamonetadialog.h"

#include <viewlog.h>
#include "opzionidialog.h"

#include "collezionexml.h"

const QString indicazione = " (non salvato)";

namespace Gui
{
    enum StackedWindows
    {
        Stacked_Collezione    = 0,
        Stacked_Biblioteca,
        Stacked_Contatti,
        Stacked_Links,
        Stacked_Statistiche,
        Stacked_Blocknotes
    };
}

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    //sistema la statusbar
    this->setupStatusBar();
    /* mette le azioni in un gruppo per selezionarle solo singolarmente */
    toolsMenu = new QActionGroup(this);
    this->ui->actionCollezione->setActionGroup(this->toolsMenu);
    this->ui->actionBiblioteca->setActionGroup(this->toolsMenu);
    this->ui->actionContatti->setActionGroup(this->toolsMenu);
    this->ui->actionLinks->setActionGroup(this->toolsMenu);
    this->ui->actionStatistiche->setActionGroup(this->toolsMenu);
    this->ui->actionBlocknotes->setActionGroup(this->toolsMenu);
    connect(this->toolsMenu, SIGNAL(triggered(QAction*)), this, SLOT(toolsMenu_triggered(QAction*)));
    //abilita la collezione
    this->ui->actionCollezione->setChecked(true);
    //fa partire il timer per i conteggi e i controlli
    this->checkTimer.start(10000);
    //esegue subito, poi attende il timer
    this->timerTriggered();

    connect(&this->checkTimer, SIGNAL(timeout()), this, SLOT(timerTriggered()));

    connect(this->ui->widgetStatistiche, SIGNAL(itemSelected(QString)), this, SLOT(onItemSelected(QString)));

    /* sistema i segnali relativi alle modifiche non salvate */
    connect(this->ui->widgetCollezione, SIGNAL(changesOccurred()), this, SLOT(onChangesOccurredByCollezione()));
    connect(this->ui->widgetContatti, SIGNAL(changesOccurred()), this, SLOT(onChangesOccurredByContatti()));
    connect(this->ui->widgetBlocknotes, SIGNAL(changesOccurred()), this, SLOT(onChangesOccurredByBlocknotes()));
    connect(this->ui->widgetLinks, SIGNAL(changesOccurred()), this, SLOT(onChangesOccurredByLinks()));

    this->ui->actionSalva->setEnabled(false);
}


void MainWindow::closeEvent(QCloseEvent *e) {
    Q_UNUSED(e);

    this->on_actionEsci_triggered();
}

void MainWindow::setupStatusBar()
{
}

MainWindow::~MainWindow()
{
    delete ui;

    //chiude il log
    Log::Logger::getInstance()->chiude();
}

/**
  Gestore del click sul menu item esci
  */
void MainWindow::on_actionEsci_triggered()
{
    if (this->modifiche.hasModifiche()) {
        //mostra la possibilità di salvare
        this->on_actionSalva_triggered();
    }
    this->close();
}


/**
  Gestore del click sul pulsante xml2tex.
  Converte le monete in files tex
  */
void MainWindow::on_actionXml_Tex_triggered()
{
    TexGenerator converter;
    //this->progress->setVisible(true);
    bool ret = converter.convert();
    QString msg;
    if (ret == true)
    {
        msg = "Conversione in Tex: ok";
        Log::Logger::getInstance()->log(msg, Log::DEBUG);
    }
    else
    {
        msg = "Conversione in Tex: FALLITA";
        Log::Logger::getInstance()->log(msg, Log::ERR);
    }
    this->statusBar()->showMessage(msg, 5000);

}


/**
  Gestore del click su cancella temporanei
  */
void MainWindow::on_actionCancella_temporanei_triggered()
{
    QString report = CommonData::getInstance()->getReportDir();
    QStringList fileTypes;
    fileTypes << "aux" << "log" << "out" << "toc";
    int fileRimossi = FileRemover::remove(report, fileTypes);
    //this->progress->setVisible(false);
    this->statusBar()->showMessage(QString("Aux, log, out e toc: %1 rimossi").arg(fileRimossi), 5000);
}

/**
  Gestore del click su cancella tutti (tex, pdf, qr, html)
  */
void MainWindow::on_actionCancella_pdf_qr_triggered()
{
    QString report = CommonData::getInstance()->getReportDir();
    QStringList fileTypes;
    fileTypes << "tex" << "pdf" << "png" << "html" << "aux" << "log" << "out" << "toc";
    int fileRimossi = FileRemover::remove(report, fileTypes);
    this->statusBar()->showMessage(QString("Tex, pdf, png, html, aux, log, out e toc: %1 rimossi").arg(fileRimossi), 5000);

}

/**
  Gestore del click su Xml -> Qr
  */
void MainWindow::on_actionXml_Qr_triggered()
{
    QString xslt = CommonData::getInstance()->getXml2QrXslt();
    QMap<QString, QString> conversion;

    XsltConverter converter;

    QProgressDialog progress(this);
    progress.setWindowTitle(CommonData::getInstance()->getAppId());
    progress.setWindowModality(Qt::WindowModal);
    progress.setAutoClose(true);
    progress.setRange(0, CollezioneXml::getInstance()->size());
    progress.setLabelText("Xml->QR");

    bool ret = true;
    int curIndex = 0;
    foreach (QString id, CollezioneXml::getInstance()->getAllId()) {
        curIndex++;
        ret = converter.convertToQr(id,
                                    CommonData::getInstance()->getCollezione(),
                                    CommonData::getInstance()->getQrDir(),
                                    xslt,
                                    conversion);
        //segnala il nuovo indice
        progress.setValue(curIndex);
        if (!ret)
            break;
    }

    progress.close(); //per sicurezza

    QString status;
    if (ret == true) {
        status = "Xml2Qr: OK";
    } else {
        status = "Xml2Qr: FALLITO";
    }

    this->statusBar()->showMessage(status, 5000);


}




void MainWindow::on_actionXml_Html_triggered()
{
    QString xslt = CommonData::getInstance()->getXml2HtmlXslt();
    QMap<QString, QString> conversion;
    XsltConverter converter;

    QProgressDialog progress(this);
    progress.setWindowTitle(CommonData::getInstance()->getAppId());
    progress.setWindowModality(Qt::WindowModal);
    progress.setAutoClose(true);
    progress.setRange(0, CollezioneXml::getInstance()->size());
    progress.setLabelText("Xml->HTML");

    /* copy images */
    /* create directory img under html */
    QDir targetDir(CommonData::getInstance()->getHtmlDir());
    targetDir.mkdir("img");
    /* copy files */
    QDir realDir(QApplication::applicationDirPath()+"/"+CommonData::getInstance()->getImgDir());
    foreach (QString file, realDir.entryList()) {
       QFile::copy(realDir.absolutePath()+"/"+file, targetDir.absolutePath()+"/img/"+file);
    }


    bool ret = true;
    int curIndex = 0;
    foreach (QString id, CollezioneXml::getInstance()->getAllId()) {
        curIndex++;
        QString outfile = CommonData::getInstance()->getHtmlDir()+"/"+id+".html";
        QFile out(outfile);
        ret = converter.convert(id,  CommonData::getInstance()->getCollezione(),
                                     xslt, &out, conversion);
        //segnala il nuovo indice
        progress.setValue(curIndex);
        if (!ret)
            break;
    }

    progress.close(); //per sicurezza

    if (ret) {
        //copia il css
        QFile css(":/Xml_transform/report.css");
        ret = css.copy(CommonData::getInstance()->getHtmlDir()+"/"+"report.css");
    }
    QString status;
    if (ret == true)
    {
        status = "Xml2Html: OK";
    }
    else
    {
        status = "Xml2Html: FALLITO";
    }
    //TODO generare index.html e moneta.html

    //this->progress->setVisible(false);
    this->statusBar()->showMessage(status, 5000);
}


void MainWindow::on_actionSalva_triggered()
{
    if (!this->modifiche.hasModifiche()) {
        QMessageBox msgBox;
        msgBox.information(this, "Salvataggio documenti", "Nessuna modifica da salvare");
        this->ui->actionSalva->setEnabled(false);
        return; //nessuna modifica
    }

    QMessageBox msgBox;
    msgBox.setModal(true);
    msgBox.setIcon(QMessageBox::Question);
    msgBox.setText("Salvataggio documenti");
    bool modificheCollezione = (this->modifiche.getModifiche(COLLEZIONE) > 0 ? true : false);
    bool modificheContatti = (this->modifiche.getModifiche(CONTATTI) > 0 ? true : false);
    bool modificheBlocknotes = (this->modifiche.getModifiche(BLOCKNOTES) > 0 ? true : false);
    bool modificheLinks = (this->modifiche.getModifiche(LINKS) > 0 ? true : false);
    bool modificheBiblioteca = (this->modifiche.getModifiche(BIBLIOTECA) > 0 ? true : false);
    QStringList dove;
    if (modificheCollezione)
        dove << "Collezione";
    if (modificheContatti)
        dove << "Contatti";
    if (modificheBlocknotes)
        dove << "Blocknotes";
    if (modificheLinks)
        dove << "Links";
    if (modificheBiblioteca)
        dove << "Biblioteca";
    msgBox.setInformativeText(QString("Ci sono modifiche non salvate in %1.<br/>Salvo?").arg(dove.join(", ")));
    msgBox.setStandardButtons(QMessageBox::Save | QMessageBox::Discard | QMessageBox::Cancel);
    msgBox.setDefaultButton(QMessageBox::Save);
    int ret = msgBox.exec();
    switch (ret) {
    case QMessageBox::Save:
        // salva tutte le modifiche
        if (this->modifiche.hasModifiche(COLLEZIONE)) {
            this->ui->widgetCollezione->salva();
            this->modifiche.resetModifica(COLLEZIONE);
        }

        if (this->modifiche.hasModifiche(BLOCKNOTES)) {
            this->ui->widgetBlocknotes->salva();
            this->modifiche.resetModifica(BLOCKNOTES);
        }

        if (this->modifiche.hasModifiche(CONTATTI)) {
            this->ui->widgetContatti->salva();
            this->modifiche.resetModifica(CONTATTI);
        }

        if (this->modifiche.hasModifiche(LINKS)) {
            this->ui->widgetLinks->salva();
            this->modifiche.resetModifica(LINKS);
        }
        // resetta lo stato a "nessuna modifica"
        this->setWindowTitle(this->windowTitle().remove(indicazione));
        //disabilita il pulsante salva
        this->ui->actionSalva->setEnabled(false);
        //toggle edit button (if checked)
        if (this->ui->actionEdit->isChecked())
            this->ui->actionEdit->toggle();

        break;
    case QMessageBox::Discard:
        //rilegge il documento, scartando le modifiche
        this->ui->widgetCollezione->reload();
        this->ui->actionSalva->setEnabled(false);
        break;
    case QMessageBox::Cancel:
        //non fa niente: chiude solo il dialogo
        break;
    default:
        // should never be reached
        break;
    }

}

void MainWindow::on_actionEdit_toggled(bool val)
{
    /* il click sul pulsante edit, abilita/disabilita i controlli della form */
    this->ui->widgetCollezione->enableEdit(val);

    this->ui->widgetLinks->enableEdit(val);

}




void MainWindow::on_actionAbout_triggered()
{
    AboutDialog about(this);
    about.exec();
}

/**
  Genera pdf a partire da tex e qr
  */
void MainWindow::on_actionTex_Qr_Pdf_triggered()
{
    int max = 3;

    QProgressDialog progress(this);
    progress.setWindowTitle(CommonData::getInstance()->getAppId());
    progress.setWindowModality(Qt::WindowModal);
    progress.setAutoClose(true);
    progress.setRange(0, max+1);
    progress.setMinimumDuration(500);
    progress.setLabelText("Genero Collezione.pdf... attendi");

    //this->statusBar()->showMessage("Genero Collezione.pdf... attendi");
    QString commandLine = CommonData::getInstance()->getCommandToPdf();

    qDebug() << commandLine;
    //genera Collezione.pdf
    for (int i = 0; i < max; i++)
    {
        progress.setValue(i+1);
        Utils::doWork(CommonData::getInstance()->getTexDir(), commandLine, QStringList() << "Collezione.tex");
    }

    progress.close(); //per sicurezza

    //this->statusBar()->showMessage("Genero Etichette.pdf... attendi");

    progress.setWindowTitle(CommonData::getInstance()->getAppId());
    progress.setWindowModality(Qt::WindowModal);
    progress.setAutoClose(true);
    progress.setRange(0, max+1);
    progress.setValue(0);
    progress.show();
    progress.setLabelText("Genero Etichette.pdf... attendi");

    //genera etichette.pdf
    for (int i = 0; i < max; i++)
    {
        progress.setValue(i+1);
        Utils::doWork(CommonData::getInstance()->getTexDir(), commandLine, QStringList() << "Etichette.tex");
    }


    progress.close(); //per sicurezza

    /* elimina i temporanei creati */
    //this->statusBar()->showMessage("Elimino temporanei... attendi");
    QString report = CommonData::getInstance()->getReportDir();
    QStringList fileTypes;
    fileTypes << "aux" << "toc" << "out" << "log";
    FileRemover::remove(report, fileTypes);
    this->statusBar()->showMessage("Pdf generati.", 5000);
}

void MainWindow::on_actionVedi_Log_triggered()
{
    ViewLog logViewer(Log::Logger::getLogFilename(), this);

    logViewer.exec();
}

/**
  Gestore del click su un'opzione di attivita' principale.
  */
void MainWindow::toolsMenu_triggered(QAction* action)
{
    if (action == this->ui->actionCollezione)  {
        this->ui->stackedWidget->setCurrentIndex(Gui::Stacked_Collezione);
    } else if (action == this->ui->actionBiblioteca) {
        this->ui->stackedWidget->setCurrentIndex(Gui::Stacked_Biblioteca);
    } else if (action == this->ui->actionContatti) {
        this->ui->stackedWidget->setCurrentIndex(Gui::Stacked_Contatti);
    } else if (action == this->ui->actionLinks) {
        this->ui->stackedWidget->setCurrentIndex(Gui::Stacked_Links);
    } else if (action == this->ui->actionBlocknotes) {
        this->ui->stackedWidget->setCurrentIndex(Gui::Stacked_Blocknotes);
    } else if (action == this->ui->actionStatistiche) {
        this->ui->stackedWidget->setCurrentIndex(Gui::Stacked_Statistiche);
    } else {
        Log::Logger::getInstance()->log("ToolsMenu_triggered: Evento non riconosciuto", Log::ERR);
    }

}


/**
  Qualcuno ha selezionato un id di moneta (da statistiche). Mostra la moneta.
  */
void MainWindow::onItemSelected(QString id) {
    this->ui->actionCollezione->setChecked(true);
    this->ui->stackedWidget->setCurrentIndex(Gui::Stacked_Collezione);
    this->ui->widgetCollezione->idChanged(id);
}

/**
  si sono verificate modifiche per una moneta
  */
void MainWindow::onChangesOccurredByBlocknotes() {
    QString title = this->windowTitle();
    if (!title.endsWith(indicazione)) {
        this->setWindowTitle(title + indicazione);
    }
    this->ui->actionSalva->setEnabled(true);
    this->modifiche.addModifica(BLOCKNOTES);
}

/**
  si sono verificate modifiche per una moneta
  */
void MainWindow::onChangesOccurredByCollezione() {
    QString title = this->windowTitle();
    if (!title.endsWith(indicazione)) {
        this->setWindowTitle(title + indicazione);
    }
    this->ui->actionSalva->setEnabled(true);
    this->modifiche.addModifica(COLLEZIONE);
}
/**
  si sono verificate modifiche per una moneta
  */
void MainWindow::onChangesOccurredByLinks() {
    QString title = this->windowTitle();
    if (!title.endsWith(indicazione)) {
        this->setWindowTitle(title + indicazione);
    }
    this->ui->actionSalva->setEnabled(true);
    this->modifiche.addModifica(LINKS);
}
/**
  si sono verificate modifiche per una moneta
  */
void MainWindow::onChangesOccurredByContatti() {
    QString title = this->windowTitle();
    if (!title.endsWith(indicazione)) {
        this->setWindowTitle(title + indicazione);
    }
    this->ui->actionSalva->setEnabled(true);
    this->modifiche.addModifica(CONTATTI);
}



void MainWindow::on_actionAddItem_triggered()
{
    switch (this->ui->stackedWidget->currentIndex()) {
    case Gui::Stacked_Collezione:
        this->ui->widgetCollezione->addMoneta();
        break;
    case Gui::Stacked_Blocknotes:
        this->ui->widgetBlocknotes->addNote();
        break;
    case Gui::Stacked_Contatti:
        this->ui->widgetContatti->addItem();
        break;
    case Gui::Stacked_Links:
        this->ui->widgetLinks->addItem();
        break;
    default:
        Log::Logger::getInstance()->log("on_actionSalva_triggered : niente da salvare", Log::DEBUG);
        break;
    }
}

void MainWindow::on_actionOpzioni_triggered()
{
    OpzioniDialog opzioni(this);
    int ret = opzioni.exec();
    if (ret == QDialog::Accepted) {
        if (this->modifiche.hasModifiche()) {
            //mostra la possibilità di salvare
            this->on_actionSalva_triggered();
        }

        /* ricarica la collezione */
        QString xml = CommonData::getInstance()->getCollezione();
        CollezioneXml::getInstance()->readData(xml);
        this->ui->widgetCollezione->setupModelMonete();
    }
}


void MainWindow::timerTriggered() {
    int contoMonete = CollezioneXml::getInstance()->size();
    int contoLibri = BibliotecaXml::getInstance()->size();
    QString msg = QString("%1 Monete e %2 Libri")
            .arg(contoMonete)
            .arg(contoLibri);
    this->statusBar()->showMessage(msg);
}


void MainWindow::on_actionVedi_Collezione_pdf_triggered()
{
    QString file = CommonData::getInstance()->getTexDir()+"/"+"Collezione.pdf";
    QUrl url = QUrl::fromLocalFile(file);
    if (url.isValid() && QFile(file).exists()) {
        QDesktopServices::openUrl(url);
    } else {
        QMessageBox::warning(this, CommonData::getInstance()->getAppName(), QString("Impossible aprire %1")
                             .arg(file));
    }

}




void MainWindow::on_actionVedi_Etichette_pdf_triggered()
{
    QString file = CommonData::getInstance()->getTexDir()+"/"+"etichette.pdf";
    QUrl url = QUrl::fromLocalFile(file);
    if (url.isValid() && QFile(file).exists()) {
        QDesktopServices::openUrl(url);
    } else {
        QMessageBox::warning(this, CommonData::getInstance()->getAppName(), QString("Impossible aprire %1")
                             .arg(file));
    }
}

void MainWindow::on_actionAbout_Qt_triggered()
{
    QMessageBox::aboutQt(this);
}
