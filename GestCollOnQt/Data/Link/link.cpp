#include "link.h"

using namespace xml;

Link::Link(QString nome, QUrl url, QString note)
{
    this->nome = nome;
    this->url = url;
    this->note = note;
}

