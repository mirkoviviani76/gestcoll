#ifndef CONTATTIXML_H
#define CONTATTIXML_H

#include "contatti.hxx"
#include "commondefs.h"
#include <QList>
#include <QFileInfo>

class ContattiXml
{
    private:
        ::std::auto_ptr< ::gestColl::contatti::contatti > contattiXml;
        QList<xml::Contatto*> contatti;

        void readXml(QFileInfo f);

    public:
        ContattiXml();
        bool save();
        void addItem(QString nome, QString email, QString note);
        virtual ~ContattiXml();
        inline QList<xml::Contatto*> getContatti() { return this->contatti; }
        void setContatto(const xml::Contatto& vecchio, const xml::Contatto& nuovo);
        void deleteContatto(xml::Contatto* l);

};

#endif // CONTATTIXML_H
