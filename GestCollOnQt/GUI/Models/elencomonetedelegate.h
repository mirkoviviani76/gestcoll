#ifndef ELENCOMONETEDELEGATE_H
#define ELENCOMONETEDELEGATE_H

#include <QStyledItemDelegate>
#include <QPainter>

class ElencoMoneteDelegate : public QStyledItemDelegate
{
    Q_OBJECT
public:
    explicit ElencoMoneteDelegate(QObject *parent = 0);
    void paint(QPainter *painter, const QStyleOptionViewItem &option, const QModelIndex &index) const;
signals:
    
public slots:
    
};

#endif // ELENCOMONETEDELEGATE_H
