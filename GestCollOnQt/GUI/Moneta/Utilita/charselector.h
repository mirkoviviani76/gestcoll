#ifndef CHARSELECTOR_H
#define CHARSELECTOR_H

#include <QWidget>

#include <QPushButton>
#include <QList>

namespace Ui {
    class CharSelector;
}

class CharSelector : public QWidget
{
    Q_OBJECT

public:
    explicit CharSelector(QWidget *parent = 0);
    ~CharSelector();

protected:
    void changeEvent(QEvent *e);

private:
    Ui::CharSelector *ui;
    QList<QPushButton*> buttons;

private slots:
    void gestClick();

signals:
    void specialCharSelected(QString sc);


};

#endif // CHARSELECTOR_H
