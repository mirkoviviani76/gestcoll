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
    QMenu contextMenuForImg;
    void updateModel(QString newId);
    void reload();
    /**
      Apre il dialogo per aggiungere una nuova moneta
      */
    void addMoneta();

    void setupModelMonete();

protected:
    void changeEvent(QEvent *e);

private:
    bool editingEnabled; ///< editing abilitato
    QMap<QString, int> tabVassoi; ///< mappa fra id vassoio e id tab
    Ui::MonetaForm *ui; ///< la form principale
    CollezioneSortFilterProxyModel* collezioneModel; ///< modello collezione
    GenericModel* modelloNote; ///< modello collezione
    GenericModel* modelloZecchieri; ///< modello zecchieri
    GenericModel* modelloAutorita; ///< modello autorita
    GenericModel* modelloLetteratura; ///< modello letteratura
    GenericModel* modelloDoc; ///< modello documenti
    GenericModel* modelloLegendaD; ///< modello legenda dritto
    GenericModel* modelloLegendaR; ///< modello legenda rovescio
    GenericModel* modelloLegendaT; ///< modello legenda taglio
    GenericModel* modelloAmbiti; ///< modello ambiti
    MonetaXml* item; ///< la moneta correntemente visualizzata
    Posizioni* vassoi; ///< i vassoi
    void setupModels();
    void loadData();
    void addLegenda(Moneta::Lato lato);
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
    void on_descrizioneTaglio_customContextMenuRequested(QPoint pos);
    void on_descrizioneRovescio_customContextMenuRequested(QPoint pos);
    void on_descrizioneDritto_customContextMenuRequested(QPoint pos);
    void on_posizione_clicked();
    void on_itemList_activated(QModelIndex index);
    void on_data_dateChanged(QDate date);
    void on_autorita_customContextMenuRequested(QPoint pos);
    void on_documenti_customContextMenuRequested(QPoint pos);
    void on_note_customContextMenuRequested(QPoint pos);
    void on_legendeDritto_customContextMenuRequested(QPoint pos);
    void on_legendeRovescio_customContextMenuRequested(QPoint pos);
    void on_legendeTaglio_customContextMenuRequested(QPoint pos);
    void on_zecchieri_customContextMenuRequested(QPoint pos);
    void on_letteratura_customContextMenuRequested(QPoint pos);

    void on_zecchieri_doubleClicked(QModelIndex index);
    void on_legendeTaglio_doubleClicked(QModelIndex index);
    void on_legendeRovescio_doubleClicked(QModelIndex index);
    void on_legendeDritto_doubleClicked(QModelIndex index);
    void on_letteratura_doubleClicked(QModelIndex index);
    void on_note_doubleClicked(QModelIndex index);
    void on_autorita_doubleClicked(QModelIndex index);
    void gestLegendaModifica(Moneta::Lato lato, QModelIndex index);
    void on_descrizioneTaglio_textChanged();
    void on_descrizioneRovescio_textChanged();
    void on_descrizioneDritto_textChanged();
    void on_luogo_textChanged(QString );
    void on_metallo_textChanged(QString );
    void on_forma_textChanged(QString );
    void on_anno_textChanged(QString );
    void on_paese_textChanged(QString );
    void on_dimensione_textChanged(QString valore, QString unita);
    void on_nominale_textChanged(QString valore, QString unita);
    void on_peso_textChanged(QString valore, QString unita);
    void on_prezzo_textChanged(QString valore, QString unita);
    void on_zecca_textChanged(QString nome, QString segno);
    void on_id_customContextMenuRequested(const QPoint &pos);
    void on_itemList_customContextMenuRequested(const QPoint &pos);
    void on_led_clicked();
    void on_documenti_doubleClicked(const QModelIndex &index);
    void on_ambiti_doubleClicked(const QModelIndex &index);
    void on_ambiti_customContextMenuRequested(const QPoint &pos);

    void on_setupCollezione_clicked();

    void on_imgDritto_customContextMenuRequested(const QPoint &pos);
    void on_imgRovescio_customContextMenuRequested(const QPoint &pos);
    void on_imgTaglio_customContextMenuRequested(const QPoint &pos);

signals:
    void changesOccurred();
    void newIdAdded(MonetaXml* newId);
};

#endif // MONETAFORM_H
