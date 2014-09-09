#include "setambitodialog.h"
#include "ui_setambitodialog.h"

SetAmbitoDialog::SetAmbitoDialog(QList<xml::Ambito*> ambiti, QList<xml::Ambito*> ambitiPresenti, QWidget *parent) :
    QDialog(parent),
    ui(new Ui::SetAmbitoDialog)
{
    ui->setupUi(this);

    foreach(xml::Ambito* a, ambiti) {
        QCheckBox* cb = new QCheckBox(a->titolo, this->ui->ambitiContainer);
        this->ui->ambitiContainer->layout()->addWidget(cb);
        this->mappa[cb] = a;
        /* setta se presente */
        foreach(xml::Ambito* b, ambitiPresenti) {
            if (b->titolo == a->titolo) {
                cb->setChecked(true);
                this->mappa[cb] = b;
                break;
            }
        }
    }
}

SetAmbitoDialog::~SetAmbitoDialog()
{
    delete ui;
}

QList<xml::Ambito*> SetAmbitoDialog::getAmbitiSelezionati() {
    QList<xml::Ambito*>  ret;
    foreach(QCheckBox* cb, this->mappa.keys()) {
        if (cb->isChecked()) {
            ret.append(this->mappa[cb]);
        }
    }
    return ret;
}

