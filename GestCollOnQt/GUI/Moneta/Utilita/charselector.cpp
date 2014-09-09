#include "charselector.h"
#include "ui_charselector.h"
#include <QStringList>
#include <QFont>
#include <QDebug>

CharSelector::CharSelector(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::CharSelector)
{
    QStringList listaCaratteri;
    listaCaratteri << QString(0xC4) << QString(0xD6) << QString(0xDC)
            << QString(0xE4) << QString(0xF6) << QString(0xFC)
            << QString(0xDF) << QString(0x2D9) << QString(0xB7)
            << QString(0x2022) << QString(0x2042) << QString(0x2043)
            << QString(0x25C6) << QString(0x25C7) << QString(0x25CA)
            << QString(0x25CB) << QString(0x25CC) << QString(0x25CF)
            << QString(0x2716) << QString(0x2719) << QString(0x271A)
            << QString(0x271B) << QString(0x271C) << QString(0x2720)
            << QString(0x2722) << QString(0x2723) << QString(0x2724)
            << QString(0x2725) << QString(0x2726) << QString(0x2727)
            << QString(0x2729) << QString(0x272A) << QString(0x272B)
            << QString(0x272C) << QString(0x272D) << QString(0x272E)
            << QString(0x272F) << QString(0x2730) << QString(0x2731)
            << QString(0x2732) << QString(0x2733) << QString(0x2734)
            << QString(0x2735) << QString(0x2736) << QString(0x2737)
            << QString(0x2738) << QString(0x2739) << QString(0x273A)
            << QString(0x273B) << QString(0x273C) << QString(0x273D)
            << QString(0x273E) << QString(0x273F) << QString(0x2740)
            << QString(0x2741) << QString(0x2742) << QString(0x2743)
            << QString(0x2744) << QString(0x2745) << QString(0x2746)
            << QString(0x2747) << QString(0x2748) << QString(0x2749)
            << QString(0x274A) << QString(0x274B);

    int rows = 8;
    int cols = 8;

    ui->setupUi(this);
    for (int i = 0; i < rows; i++)
    {
        for (int j = 0; j < cols; j++)
        {
            QString curStr = listaCaratteri.at((i*cols+j)%(listaCaratteri.size()));
            QPushButton* curButton = new QPushButton(curStr, this);
            curButton->setFont(QFont());
            buttons.append(curButton);
            this->ui->gridLayout->addWidget(curButton, i, j);
            connect(curButton, SIGNAL(clicked()),
                    this, SLOT(gestClick()));
        }
    }
}

CharSelector::~CharSelector()
{
    delete ui;
    qDeleteAll(this->buttons);
}

void CharSelector::changeEvent(QEvent *e)
{
    QWidget::changeEvent(e);
    switch (e->type()) {
    case QEvent::LanguageChange:
        ui->retranslateUi(this);
        break;
    default:
        break;
    }
}

void CharSelector::gestClick()
{
    QPushButton* senderButton = (QPushButton*)this->sender();
    emit specialCharSelected(senderButton->text());
}

