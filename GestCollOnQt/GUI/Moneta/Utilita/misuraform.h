#ifndef MISURAFORM_H
#define MISURAFORM_H

#include <QWidget>

#include <commondefs.h>


namespace Ui {
    class MisuraForm;
}

class MisuraForm : public QWidget
{
    Q_OBJECT

public:
    explicit MisuraForm(QWidget *parent = 0);
    ~MisuraForm();
    void setValore(double valore);
    void setValore(QString valore);
    void setUnita(QString unita);
    void setData(QString valore, QString unita);
    void setData(double valore, QString unita);
    void setData(xml::Misura misura);
    void enableEdit(bool editable);

protected:
    void changeEvent(QEvent *e);

private:
    bool changedValore;
    bool changedUnita;
    Ui::MisuraForm *ui;

signals:
    void textChanged(QString valore, QString unita);

private slots:
    void on_unita_textChanged(QString );
    void on_valore_textChanged(QString );
};

#endif // MISURAFORM_H
