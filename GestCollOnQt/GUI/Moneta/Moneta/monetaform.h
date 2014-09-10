#ifndef MONETAFORM_H
#define MONETAFORM_H

#include <QWidget>
#include <QLabel>
#include <genericmodel.h>
#include <monetaxml.h>
#include "collezionesortfilterproxymodel.h"
#include <QMenu>
#include <QDate>
#include "posizioni.h"
#include "vassoioform.h"
#include "datifisicimodel.h"

namespace Ui {
    class MonetaForm;
}

class MonetaForm : public QWidget
{
    Q_OBJECT

public:
    explicit MonetaForm(QWidget *parent = 0);
    ~MonetaForm();

    void setModel(GenericModel* model);
    QMenu contextMenu;
    QMenu contextMenuForMoneteList;
    QMenu contextMenuForAmbiti;
    void updateModel(QString newId);
    void reload();
    /**
      Apre il dialogo per aggiungere una nuova moneta
      */
    void addMoneta();

    void setupModelMonete();

    void legende_customContextMenuRequested(const QPoint &pos, const Moneta::Lato &lato);

protected:
    void changeEvent(QEvent *e);

private:
    void showQr();
    bool editingEnabled; ///< editing abilitato
    QMap<QString, int> tabVassoi; ///< mappa fra id vassoio e id tab
    Ui::MonetaForm *ui; ///< la form principale
    CollezioneSortFilterProxyModel* collezioneModel; ///< modello collezione
    GenericModel* modelloNote; ///< modello collezione
    GenericModel* modelloZecchieri; ///< modello zecchieri
    GenericModel* modelloLetteratura; ///< modello letteratura
    GenericModel* modelloDoc; ///< modello documenti
    GenericModel* modelloAmbiti; ///< modello ambiti
    DatiFisiciModel* modelloDatiFisici; ///< modello dati fisici
    MonetaXml* item; ///< la moneta correntemente visualizzata
    Posizioni* vassoi; ///< i vassoi
    void setupModels();
    void loadData();
    void setupTabVassoi(MonetaXml* moneta);
    void tabVassoiRemoveItem(MonetaXml* moneta);
    void contextMenuEnableAction(QString actionText, bool enable);

    void gestClipboardCopyId(const QString& id);
    void gestClipboardCopy(const QString& id);

    void setupAmbiti();


public slots:
    void enableEdit(bool editable);
    void addItem(MonetaXml* newMoneta);
    void idChanged(QString id);
    void salva();

private slots:
    void customContextMenuRequested(QPoint pos);
    void on_posizione_clicked();
    void on_itemList_activated(QModelIndex index);
    void on_data_dateChanged(QDate date);
    void on_documenti_customContextMenuRequested(QPoint pos);
    void on_note_customContextMenuRequested(QPoint pos);
    void on_zecchieri_customContextMenuRequested(QPoint pos);
    void on_letteratura_customContextMenuRequested(QPoint pos);

    void on_zecchieri_doubleClicked(QModelIndex index);
    void on_letteratura_doubleClicked(QModelIndex index);
    void on_note_doubleClicked(QModelIndex index);
    void on_luogo_textChanged(QString );
    void on_anno_textChanged(QString );
    void on_paese_textChanged(QString );
    void on_nominale_textChanged(QString valore, QString unita);
    void on_prezzo_textChanged(QString valore, QString unita);
    void on_zecca_textChanged(QString nome, QString segno);
    void on_id_customContextMenuRequested(const QPoint &pos);
    void on_itemList_customContextMenuRequested(const QPoint &pos);
    void on_led_clicked();
    void on_documenti_doubleClicked(const QModelIndex &index);
    void on_ambiti_doubleClicked(const QModelIndex &index);
    void on_ambiti_customContextMenuRequested(const QPoint &pos);

    void on_setupCollezione_clicked();

    void datiFisiciChanged();

    void on_openPaeseUrl_clicked();

signals:
    void changesOccurred();
    void newIdAdded(MonetaXml* newId);
};

#endif // MONETAFORM_H
