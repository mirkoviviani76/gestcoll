#ifndef BIBLIOTECAITEM_H
#define BIBLIOTECAITEM_H

#include <QString>
#include <QStringList>
#include <QImage>
#include <QColor>

class BibliotecaItem
{
public:
    BibliotecaItem(QString _id, QString _titolo, QString _filename, QStringList _listaAutori, QStringList _listaSupporti, QStringList _listaArgomenti);
    QString toString(int column=-1);
    QString getId() const {return id;}
    QString getTitolo() const {return titolo;}
    QString getFilename() const {return filename;}
    QStringList getAutori() {return this->listaAutori;}
    QStringList getSupporti() const {return listaSupporti;}
    QStringList getArgomenti() const {return listaArgomenti;}

    QString toTooltip();
    bool hasElectronicForm();
    virtual bool lessThan(BibliotecaItem* due);
    virtual bool operator<(BibliotecaItem* due) {return this->lessThan(due);}


private:
    QString id;
    QString titolo;
    QString filename;
    QStringList listaAutori;
    QStringList listaSupporti;
    QStringList listaArgomenti;
};

#endif // BIBLIOTECAITEM_H
