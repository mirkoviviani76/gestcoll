#ifndef MONETAXML_H
#define MONETAXML_H

#include <commondefs.h>
#include <genericitem.h>
#include <QString>
#include <QFile>
#include <QList>
#include <QDate>
#include <QFileInfo>
#include <scheda.hxx>

using namespace gestColl::coins;

namespace Moneta
{

enum Lato
{
    DRITTO,
    ROVESCIO,
    TAGLIO
};

} //namespace

class MonetaXml: public GenericItem
{
    Q_OBJECT
public:
    MonetaXml(const moneta& m);
    MonetaXml(moneta* m);
    virtual ~MonetaXml();
    inline QColor getColor() { return QColor::Invalid;}
    QString getIdVassoio() const;
    int getContenitore() const;
    int getVassoio() const;
    int getRiga() const;
    int getColonna() const;

    QString toString(int column=-1);
    QString toTooltip();
    QImage toImg();
    QString getId() const;
    QString getPaese();
    QString getAnno();
    QString getForma();
    QString getMetallo();
    xml::Misura getDiametro();
    xml::Misura getPeso();
    xml::Zecca getZecca();
    xml::Nominale getNominale();
    QString getLuogo();
    QDate getData();
    xml::Misura getPrezzo();
    xml::Stato getStato();
    QList<xml::Nota*> getNote();
    QList<xml::Autorita*> getAutorita();
    QList<xml::Zecchiere*> getZecchieri();
    QList<xml::Libro*> getLetteratura();
    QList<xml::Documento*> getItemAddizionali();
    QList<xml::Ambito*> getAmbiti();

    void updateRevision();
    bool updateAmbiti(const xml::Ambito& vecchio, const xml::Ambito& nuovo);

    void setStato(xml::Stato& nuovo);
    void setPaese(QString p);
    void setLuogo(QString p);
    void setAnno(QString p);
    void setNominale(QString valore, QString unita);
    void setPrezzo(qreal valore, QString unita);
    void setZecca(QString nome, QString segno);
    void setLibro(const xml::Libro& vecchio, const xml::Libro& nuovo);
    void setNota(const xml::Nota& vecchio, const xml::Nota& nuovo);
    void setZecchiere(const xml::Zecchiere& vecchio, const xml::Zecchiere& nuovo);
    void setDocumento(const xml::Documento& vecchio, const xml::Documento& nuovo);
    void setData(QDate date);
    void setPosizione(int cont, int vass, int r, int c);
    void setAmbiti(QList<xml::Ambito*> ambiti);
    void addLibro(const xml::Libro& l);
    void addNota(const xml::Nota& l);
    void addAutorita(const xml::Autorita& l);
    void addZecchiere(const xml::Zecchiere& l);
    void addDocumento(const xml::Documento& l);


    void deleteAutorita(xml::Autorita *l);
    void deleteNota(xml::Nota* l);
    void deleteLetteratura(xml::Libro* l);
    void deleteDocumento(xml::Documento* l);

    moneta* getDom() {return this->mon;}

    void clear();

private:
    //Moneta::MonetaOrdering sortingType;
    moneta* mon;
    QImage* image;
    void updateImage();
    QList<xml::Nota*> xmlNote;
    QList<xml::Autorita*> xmlAutorita;
    QList<xml::Zecchiere*> xmlZecchieri;
    QList<xml::Libro*> xmlLetteratura;
    QList<xml::Documento*> xmlItemAddizionali;
    QList<xml::Ambito*> xmlAmbiti;

    void deleteNoteList();
    void deleteLegendeList(Moneta::Lato l);
    void deleteAutoritaList();
    void deleteZecchieriList();
    void deleteLetteraturaList();
    void deleteItemAddizionaliList();
    void deleteAmbitiList();

    void fillNote();
    void fillAutorita();
    void fillZecchieri();
    void fillLetteratura();
    void fillItemAddizionali();
    void fillAmbiti();

};

#endif // MONETAXML_H
