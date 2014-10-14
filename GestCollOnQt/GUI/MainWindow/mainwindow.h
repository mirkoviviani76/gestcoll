#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>

#include <bibliotecaitem.h>
#include <aboutdialog.h>
#include <texgenerator.h>
#include <QActionGroup>
#include <QTimer>
#include <QProgressDialog>
#include "modifiche.h"

namespace Ui {
    class MainWindow;
}

/**
 * @brief la classe che contiene la finestra principale
 *
 */
class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    /**
     * @brief costruttore
     *
     * @param parent
     */
    explicit MainWindow(QWidget *parent = 0);
    /**
     * @brief distruttore
     *
     */
    ~MainWindow();

private:
    QActionGroup* toolsMenu; /**< il menu delle azioni */
    QTimer checkTimer; /**< il timer per i conteggi */
    Modifiche modifiche;
    QProgressDialog* progress;
    Ui::MainWindow *ui; /**< la ui */
    /**
     * @brief sistema la barra di stato
     *
     */
    void setupStatusBar();
    void closeEvent(QCloseEvent * e);

public slots:
    /**
     * @brief click sul menu opzioni
     *
     */
    void on_actionOpzioni_triggered();

private slots:
    /**
     * @brief chiamata quando viene cliccata un'azione
     *
     * @param action l'azione
     */
    void toolsMenu_triggered(QAction* action);
    /**
     * @brief clic sul pulsante vedi log
     *
     */
    void on_actionVedi_Log_triggered();
    /**
     * @brief clic sul pulsante tex-qr-pdf
     *
     */
    void on_actionTex_Qr_Pdf_triggered();
    /**
     * @brief clic sul pulsante about
     *
     */
    void on_actionAbout_triggered();
    /**
     * @brief clic sul pulsante xml->html
     *
     */
    void on_actionXml_Html_triggered();
    /**
     * @brief clic sul pulsante xml->qr
     *
     */
    void on_actionXml_Qr_triggered();
    /**
     * @brief clic sul pulsante cancella pdf e qr
     *
     */
    void on_actionCancella_pdf_qr_triggered();
    /**
     * @brief clic sul pulsante cancella temporanei
     *
     */
    void on_actionCancella_temporanei_triggered();
    /**
     * @brief clic sul pulsante vedi xml->tex
     *
     */
    void on_actionXml_Tex_triggered();
    /**
     * @brief clic sul pulsante esci
     *
     */
    void on_actionEsci_triggered();

    /**
     * @brief clic sul pulsante salva
     *
     */
    void on_actionSalva_triggered();
    /**
     * @brief clic sul pulsante edit
     *
     * @param val il valore (attivato, non attivato)
     */
    void on_actionEdit_toggled(bool val);
    /**
     * @brief clic su un item moneta
     *
     * @param id l'id della moneta
     */
    void onItemSelected(QString id);
    /**
     * @brief si sono verificati cambiamenti in una moneta
     *
     */
    void onChangesOccurredByCollezione();
    void onChangesOccurredByLinks();
    void onChangesOccurredByBlocknotes();
    void onChangesOccurredByContatti();
    /**
     * @brief click sul pulsante "nuovo" item
     *
     */
    void on_actionAddItem_triggered();

    /**
     * @brief il gestore dello scatto del timer
     *
     */
    void timerTriggered();
    void on_actionVedi_Collezione_pdf_triggered();
    void on_actionVedi_Etichette_pdf_triggered();
    void on_actionAbout_Qt_triggered();

signals:
    void updateRange(int min, int max);
    void updateValue(int val);
    void updateLabel(QString label);

};

#endif // MAINWINDOW_H
