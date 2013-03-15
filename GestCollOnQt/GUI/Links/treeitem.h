#ifndef TREEITEM_H
#define TREEITEM_H

#include <QTreeWidgetItem>

#include "link.h"


class TreeItem : public QTreeWidgetItem
{
public:
    TreeItem(xml::Link* data);
    TreeItem(xml::Link* data, QTreeWidget *view, const QStringList &strings, int type = Type);
    TreeItem(xml::Link* data, const QStringList &strings, int type = Type);
    virtual ~TreeItem();
    xml::Link* getData();

private:
    xml::Link* data;
};

#endif // TREEITEM_H
