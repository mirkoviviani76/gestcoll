#ifndef LINK_H
#define LINK_H

#include <QString>
#include <QUrl>

namespace xml {

class Link
{
public:
    Link(QString nome, QUrl url, QString note);
    virtual ~Link() {}
    inline QString getNome() const {return this->nome;}
    inline QUrl getUrl() const {return this->url;}
    inline QString getNote() const {return this->note;}

    inline void setNome(const QString& v) {this->nome = v;}
    inline void setUrl(const QUrl& v) {this->url = v;}
    inline void setNote(const QString& v) {this->note = v;}

private:
    QString nome;
    QUrl url;
    QString note;
};
}

#endif // LINK_H
