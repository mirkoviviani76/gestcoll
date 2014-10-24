#include "link.h"

using namespace xml;

Link::Link(const QString& _nome, const QUrl& _url, const QString& _note)
    : nome(_nome),
      url(_url),
      note(_note)
{
}

