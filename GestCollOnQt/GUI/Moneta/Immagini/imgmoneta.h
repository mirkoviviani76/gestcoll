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
    QString getFilename() const;
private:
    QString fileImg;
signals:
    void doubleClicked();
public slots:

};

#endif // IMGMONETA_H
