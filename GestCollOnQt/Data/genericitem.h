#ifndef GENERICITEM_H
#define GENERICITEM_H

#include <QObject>
#include <QImage>
#include <QColor>

class GenericItem : public QObject
{
    Q_OBJECT
public:
    explicit GenericItem(QObject *parent = 0) : QObject(parent) {}
    /* classe virtuale pura (= interface)! */
    virtual QString toString(int column=-1)  = 0;
    virtual QString toTooltip()  = 0;
    virtual QImage toImg()  = 0;
    virtual QColor getColor() = 0;


    virtual bool lessThan(GenericItem* due) {Q_UNUSED(due); return true;}
    virtual bool operator<(GenericItem* due) {Q_UNUSED(due); return true;}

signals:

public slots:

};

#endif // GENERICITEM_H
