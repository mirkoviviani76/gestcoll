#include "visualizzaimmagine.h"
#include "ui_visualizzaimmagine.h"


#include <QDebug>

VisualizzaImmagine::VisualizzaImmagine(QString img, QWidget *parent) :
    QDialog(parent),
    ui(new Ui::VisualizzaImmagine)
{
    ui->setupUi(this);
    this->scene = new QGraphicsScene(this);
    item = NULL;

    this->showImage(img);
}

VisualizzaImmagine::~VisualizzaImmagine()
{
    if (this->scene != NULL)
    {
        if (this->item != NULL) {
            this->scene->removeItem(this->item);
            delete this->item;
            this->item = NULL;
        }
        delete this->scene;
        this->scene = NULL;
    }
    delete ui;
}

void VisualizzaImmagine::changeEvent(QEvent *e)
{
    QDialog::changeEvent(e);
    switch (e->type()) {
    case QEvent::LanguageChange:
        ui->retranslateUi(this);
        break;
    default:
        break;
    }
}

void VisualizzaImmagine::showImage(QString img)
{
    this->setWindowTitle(img);
    QImage image(img);
    //elimina item precedenti
    if (item != NULL)
    {
        scene->removeItem(item);
        delete this->item;
        item = NULL;
    }
    if (!image.isNull())
    {
        //this->ui->label->setText("");
        item = (QGraphicsItem*) scene->addPixmap(QPixmap::fromImage(image));
        //ripristina la giusta rotazione
        this->on_ripristina_clicked();
    }     else {
        item = (QGraphicsItem*) scene->addText("Immagine mancante");
        //this->ui->label->setText("Immagine mancante");
    }
    this->ui->graphicsView->ensureVisible(item);
    this->ui->graphicsView->setScene(scene);
    this->ui->graphicsView->show();
}

void VisualizzaImmagine::on_antiorario5_clicked()
{
    this->item->rotate(-5);
    this->ui->graphicsView->centerOn(item);
}

void VisualizzaImmagine::on_orario5_clicked()
{
    this->item->rotate(5);
    this->ui->graphicsView->centerOn(item);
}

void VisualizzaImmagine::on_antiorario45_clicked()
{
    this->item->rotate(-45);
    this->ui->graphicsView->centerOn(item);
}

void VisualizzaImmagine::on_orario45_clicked()
{
    this->item->rotate(45);
    this->ui->graphicsView->centerOn(item);
}

void VisualizzaImmagine::on_ripristina_clicked()
{
    this->item->resetTransform();
    this->ui->graphicsView->centerOn(item);
}

void VisualizzaImmagine::on_zoompiu_clicked()
{
    this->item->scale(1.1,1.1);
    this->ui->graphicsView->centerOn(item);
}

void VisualizzaImmagine::on_zoommeno_clicked()
{
    this->item->scale(1.0/1.1,1.0/1.1);
    this->ui->graphicsView->centerOn(item);
}


