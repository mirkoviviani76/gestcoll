#include "imgmoneta.h"

ImgMoneta::ImgMoneta(QWidget *parent) :
    QLabel(parent)
{
    this->setBaseSize(150, 150);
    this->setMaximumSize(150, 150);
    this->setMinimumSize(150, 150);
    this->setFrameShape(QFrame::Box);
    this->setScaledContents(true);
    this->fileImg = "";
}

ImgMoneta::~ImgMoneta()
{
}

QString ImgMoneta::getFilename() const
{
    return this->fileImg;
}

void ImgMoneta::setupImg(const QString& file)
{
    if (!file.isEmpty())
    {
        QPixmap image(file);
        if (image.isNull()) {
            this->setText("Immagine non valida");
            this->fileImg = "";
            this->setToolTip("");
        } else {
            this->setText("");
            this->setPixmap(image);
            this->fileImg = file;
            this->setToolTip("Doppio click per ingrandire");
        }
    }
    else
    {
        this->setText("Immagine mancante");
        this->fileImg = "";
        this->setToolTip("");
    }
}

/**
  Gestore del doppio click sull'immagine
  */
void ImgMoneta::mouseDoubleClickEvent (QMouseEvent * event)
{
    Q_UNUSED(event);
    emit doubleClicked();
}

