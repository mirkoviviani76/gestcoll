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
    GenericModel* modelloDoc; ///< modello documenti
    GenericModel* modelloAmbiti; ///< modello ambiti
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
    void on_documenti_customContextMenuRequested(QPoint pos);

    void on_id_customContextMenuRequested(const QPoint &pos);
    void on_itemList_customContextMenuRequested(const QPoint &pos);
    void on_led_clicked();
    void on_documenti_doubleClicked(const QModelIndex &index);
    void on_ambiti_doubleClicked(const QModelIndex &index);
    void on_ambiti_customContextMenuRequested(const QPoint &pos);

    void on_setupCollezione_clicked();

signals:
    void changesOccurred();
    void newIdAdded(MonetaXml* newId);
};

#endif // MONETAFORM_H
