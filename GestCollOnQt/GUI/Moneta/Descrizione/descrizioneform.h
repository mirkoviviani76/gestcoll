#ifndef DESCRIZIONEFORM_H
#define DESCRIZIONEFORM_H

#include <QWidget>

namespace Ui {
class DescrizioneForm;
}

class DescrizioneForm : public QWidget
{
    Q_OBJECT

public:
    explicit DescrizioneForm(QWidget *parent = 0);
    ~DescrizioneForm();

private:
    Ui::DescrizioneForm *ui;
};

#endif // DESCRIZIONEFORM_H
