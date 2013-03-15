#include "treeitem.h"

TreeItem::TreeItem(xml::Link *data)
{
    this->data = data;
}

TreeItem::TreeItem(xml::Link *data, QTreeWidget *view, const QStringList &strings, int type)
    : QTreeWidgetItem(view, strings, type) {
    this->data = data;
}

TreeItem::TreeItem(xml::Link* data, const QStringList &strings, int type)
    : QTreeWidgetItem(strings, type) {
    this->data = data;
}

TreeItem::~TreeItem() {

}

xml::Link* TreeItem::getData() {
    return this->data;
}

