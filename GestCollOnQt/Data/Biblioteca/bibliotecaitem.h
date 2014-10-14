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
    QString toTooltip();
    QString toHtml();
    bool hasElectronicForm();
    virtual bool lessThan(BibliotecaItem* due);
    virtual bool operator<(BibliotecaItem* due) {return this->lessThan(due);}
    QColor getColor();
    QStringList getAutori() {return this->listaAutori;}

private:
    QString id;
    QString titolo;
    QString filename;
    QStringList listaAutori;
    QStringList listaSupporti;
    QStringList listaArgomenti;
};

#endif // BIBLIOTECAITEM_H
