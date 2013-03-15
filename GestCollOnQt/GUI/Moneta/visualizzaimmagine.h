#ifndef VISUALIZZAIMMAGINE_H
#define VISUALIZZAIMMAGINE_H

#include <QDialog>
#include <QGraphicsScene>
#include <QGraphicsItem>

namespace Ui {
    class VisualizzaImmagine;
}

class VisualizzaImmagine : public QDialog
{
    Q_OBJECT

public:
    explicit VisualizzaImmagine(QString img, QWidget *parent = 0);
    ~VisualizzaImmagine();

protected:
    void changeEvent(QEvent *e);

private:
    QGraphicsItem* item;
    QGraphicsScene* scene;
    Ui::VisualizzaImmagine *ui;
    void showImage(QString img);

private slots:

private slots:
    void on_zoommeno_clicked();
    void on_zoompiu_clicked();
    void on_ripristina_clicked();
    void on_orario45_clicked();
    void on_antiorario45_clicked();
    void on_orario5_clicked();
    void on_antiorario5_clicked();
};

#endif // VISUALIZZAIMMAGINE_H
