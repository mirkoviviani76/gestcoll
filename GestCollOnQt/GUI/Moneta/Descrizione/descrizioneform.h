#ifndef DESCRIZIONEFORM_H
#define DESCRIZIONEFORM_H

#include <QGroupBox>
#include <QList>
#include <QMenu>
#include "monetaxml.h"
#include "genericmodel.h"

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
    GenericModel* modelloLegende;
    bool editingEnabled;
    ::gestColl::coins::descrizioni* xmlDom;
    QString monetaId;
    QString latoId;


    QMenu contextMenuForImg;


    void updateLegenda(const xml::Legenda &vecchio, const xml::Legenda &nuovo);
    bool updateLegenda(::descrizioni::legenda_iterator it, const xml::Legenda &vecchio, const xml::Legenda &nuovo);
    void gestLegendaModifica(QModelIndex index);
    QList<xml::Legenda *> fillLegende();
    void addLegenda(const xml::Legenda &l);
    void setLegende();
public slots:
    void setReadOnly(bool readonly);

private slots:
    void on_legende_doubleClicked(const QModelIndex &index);
    void imageDoubleClicked();

    void on_specialEdit_clicked();

    void on_descrizione_textChanged();

signals:
    void changesOccurred();
};

#endif // DESCRIZIONEFORM_H
