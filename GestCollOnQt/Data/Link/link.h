#ifndef LINK_H
#define LINK_H

#include <genericitem.h>
#include <QString>
#include <QStringList>
#include <QUrl>

namespace xml {

class Link : public GenericItem
{
    Q_OBJECT
public:
    Link(QString nome, QUrl url, QString note);
    Link(QString nome) {this->nome = nome; this->url = QUrl(); this->note = "";}
    virtual ~Link() {}
    QString toString(int column=-1);
    QString toTooltip();
    inline QString getNome() const {return this->nome;}
    inline QUrl getUrl() const {return this->url;}
    inline QString getNote() const {return this->note;}
    inline QColor getColor() { return QColor(QColor::Invalid);}
    inline QString toHtml() {
        return QString("<a href=\"%1\">%2</a>")
                .arg(this->url.toString())
                .arg(this->nome);
    }
    inline QImage toImg() { return QImage(); }

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
