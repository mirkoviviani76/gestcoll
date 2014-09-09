#include "descrizioneform.h"
#include "ui_descrizioneform.h"

DescrizioneForm::DescrizioneForm(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::DescrizioneForm)
{
    ui->setupUi(this);
}

DescrizioneForm::~DescrizioneForm()
{
    delete ui;
}
