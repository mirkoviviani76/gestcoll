#ifndef DESCRIZIONEFORM_H
#define DESCRIZIONEFORM_H

#include <QGroupBox>
#include <QList>
#include <QMenu>
#include "modellolegenda.h"

#include "scheda.hxx"

namespace Ui {
class DescrizioneForm;
}

class DescrizioneForm : public QGroupBox
{
    Q_OBJECT

public:
    explicit DescrizioneForm(QWidget *parent = 0);
    ~DescrizioneForm();
    void setDati(const QString &monetaId, const QString &latoId, ::gestColl::coins::descrizioni* _xmlDom);

    void setDescrizione(const QString& text);
    QString getDescrizione();
    void setImage(const QString& filename);
    void setTitolo(const QString& titolo);

private:

    Ui::DescrizioneForm *ui;
    ModelloLegenda* modelloLegende;
    bool editingEnabled;
    ::gestColl::coins::descrizioni* xmlDom;
    QString monetaId;
    QString latoId;


    QMenu contextMenuForImg;


    void updateLegenda(const gestColl::coins::legenda &vecchio, const QString &nuovoTesto, const QString &nuovoScioglimento);
    void gestLegendaModifica(QModelIndex index);
    QList< ::gestColl::coins::legenda > fillLegende();
    void setLegende();
public slots:
    void setReadOnly(bool readonly);

private slots:
    void on_legende_doubleClicked(const QModelIndex &index);
    void imageDoubleClicked();

    void on_specialEdit_clicked();

    void on_descrizione_textChanged();

    void on_addLegenda_clicked();

    void on_deleteLegenda_clicked();

signals:
    void changesOccurred();
};

#endif // DESCRIZIONEFORM_H
