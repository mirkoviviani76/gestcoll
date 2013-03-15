#include "link.h"

using namespace xml;

Link::Link(QString nome, QUrl url, QString note)
{
    this->nome = nome;
    this->url = url;
    this->note = note;
}

QString Link::toString(int column)
{
    Q_UNUSED(column);
    return nome;
}

QString Link::toTooltip()
{
    return this->url.toString();
}
