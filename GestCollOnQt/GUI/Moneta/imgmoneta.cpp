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

void ImgMoneta::setupImg(const QString& file)
{
    QImage image(file);
    if (!image.isNull())
    {
        this->setText("");
        this->setPixmap(QPixmap::fromImage(image));
        this->fileImg = file;
        this->setToolTip("Doppio click per ingrandire");
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
    if (this->fileImg != "")
    {
        /* mostra la finestra grande dell'immagine */
        VisualizzaImmagine bigImg(this->fileImg, this);
        bigImg.setModal(true);
        bigImg.showMaximized();
        bigImg.exec();

    }
}

