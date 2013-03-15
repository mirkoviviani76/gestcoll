#ifndef LINKSXML_H
#define LINKSXML_H

#include <QString>
#include <QUrl>
#include <QList>
#include <QMap>
#include <QFileInfo>
#include <QStringList>

#include "link.h"
#include "links.hxx"

class LinksXml
{
    public:
        LinksXml();
        bool save();
        void addItem(QString categoria, QString nome, QUrl url, QString note);
        virtual ~LinksXml();
        QStringList getCategorie();
        inline QList<xml::Link*> getLinks(QString categoria) { return this->links[categoria]; }
        void setLink(const QString& categoria, const xml::Link& vecchio, const xml::Link& nuovo);
        void deleteLink(const QString& categoria, xml::Link* l);

    private:
        ::std::auto_ptr< ::gestColl::links::links > linksXml;
        QMap<QString, QList<xml::Link*> > links; ///< mappa categoria e link

        void readXml(QFileInfo file);

};

#endif // LINKSXML_H
