#ifndef MONETAFORM_H
#define MONETAFORM_H

#include <QWidget>
#include <QLabel>
#include <genericmodel.h>
#include <monetaxml.h>
#include "collezionesortfilterproxymodel.h"
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
    void showQr();
    bool editingEnabled; ///< editing abilitato
    QMap<QString, VassoioForm* > tabVassoi; ///< mappa fra id vassoio e widget
    Ui::MonetaForm *ui; ///< la form principale
    CollezioneSortFilterProxyModel* collezioneModel; ///< modello collezione
    GenericModel* modelloAmbiti; ///< modello ambiti
    MonetaXml* item; ///< la moneta correntemente visualizzata
    Posizioni* vassoi; ///< i vassoi
    void setupModels();
    void loadData();
    void setupTabVassoi(MonetaXml* moneta);
    void tabVassoiRemoveItem(MonetaXml* moneta);

    void gestClipboardCopyId(const QString& id);
    void gestClipboardCopy(const QString& id);

    void setupAmbiti();


    VassoioForm *createVassoioForm(const QString &idTab, const QString &monetaId);
    void addTabVassoio(const QString &idTab, VassoioForm *vf);
public slots:
    void enableEdit(bool editable);
    void addItem(MonetaXml* newMoneta);
    void idChanged(QString id);
    void salva();

private slots:
    void on_posizione_clicked();
    void on_itemList_activated(QModelIndex index);

    void on_led_clicked();
    void on_ambiti_doubleClicked(const QModelIndex &index);

    void on_setupCollezione_clicked();

signals:
    void changesOccurred();
    void newIdAdded(MonetaXml* newId);
};

#endif // MONETAFORM_H
