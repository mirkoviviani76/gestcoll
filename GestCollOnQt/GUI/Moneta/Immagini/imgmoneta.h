#ifndef IMGMONETA_H
#define IMGMONETA_H

#include <QLabel>

#include "visualizzaimmagine.h"


class ImgMoneta : public QLabel
{
    Q_OBJECT
public:
    explicit ImgMoneta(QWidget *parent = 0);
    void setupImg(const QString& file);
    void mouseDoubleClickEvent (QMouseEvent * event);
    virtual ~ImgMoneta();
private:
    QString fileImg;
signals:

public slots:

};

#endif // IMGMONETA_H
