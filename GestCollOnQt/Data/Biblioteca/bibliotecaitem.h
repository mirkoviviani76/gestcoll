#ifndef BIBLIOTECAITEM_H
#define BIBLIOTECAITEM_H

#include <genericitem.h>
#include <QString>
#include <QStringList>

class BibliotecaItem: public GenericItem
{
    Q_OBJECT
public:
    BibliotecaItem(QString id, QString titolo, QString filename, QStringList listaAutori, QStringList listaSupporti, QStringList listaArgomenti);
    QString toString(int column=-1);
    QString toTooltip();
    QString toHtml();
    bool hasElectronicForm();
    inline QImage toImg() { return QImage(); }
    virtual bool lessThan(GenericItem* due);
    virtual bool operator<(GenericItem* due) {return this->lessThan(due);}
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
