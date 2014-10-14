#include "elencomonetedelegate.h"

#include "monetaxml.h"


ElencoMoneteDelegate::ElencoMoneteDelegate(QObject *parent) :
    QStyledItemDelegate(parent)
{
}

void ElencoMoneteDelegate::paint(QPainter *painter, const QStyleOptionViewItem &option, const QModelIndex &index) const {
    // Paint the line
    painter->save();
    painter->setPen(Qt::gray);
    painter->drawLine(option.rect.bottomLeft(), option.rect.bottomRight());
    painter->restore();

    // Now paint the normal cell contents
    this->QStyledItemDelegate::paint(painter, option, index);

}


