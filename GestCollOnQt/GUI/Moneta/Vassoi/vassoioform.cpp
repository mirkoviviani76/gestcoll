#include "vassoioform.h"
#include "ui_vassoioform.h"
#include <monetaxml.h>
#include <nuovamonetadialog.h>
#include <commondata.h>
#include <QDebug>

VassoioForm::VassoioForm(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::VassoioForm)
{
    ui->setupUi(this);
    model = new VassoioModel();
}

void VassoioForm::setModel()
{
    this->ui->tableView->setModel(this->model);
}

VassoioForm::~VassoioForm()
{
    if (this->model != NULL)
    {
        delete this->model;
        this->model = NULL;
    }
    delete ui;
}


void VassoioForm::changeEvent(QEvent *e)
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

void VassoioForm::setSize(QString cont, QString vass, int righe, int colonne, QString dim)
{
    this->dim = dim;
    this->contenitore = cont;
    this->vassoio = vass;
    this->model->setSize(righe, colonne);
}

void VassoioForm::setData(const Casella &c, MonetaXml* data)
{
    const int numeroRighe = this->model->rowCount(QModelIndex());
    int rigaCorretta = numeroRighe - c.getRiga();
    int colonnaCorretta = c.getColonna() - 1;
    this->model->setData(rigaCorretta, colonnaCorretta, data);
}

void VassoioForm::resizeRows() {
    this->ui->tableView->resizeRowsToContents();
}


void VassoioForm::addItem(MonetaXml *newId) {
    this->setData(newId->getPosizione().getCasella(), newId);
}

void VassoioForm::setCurrentIndex(const Casella &c)
{
    const int numeroRighe = this->model->rowCount(QModelIndex());
    int rigaCorretta = numeroRighe - c.getRiga();
    int colonnaCorretta = c.getColonna() - 1;
    QModelIndex ind = this->model->getIndex(rigaCorretta, colonnaCorretta);
    this->ui->tableView->setCurrentIndex(ind);
}

void VassoioForm::on_tableView_doubleClicked(QModelIndex index)
{
    MonetaXml* sel = (MonetaXml*)(this->model->getItem(index));

    if (sel != NULL) //click su una moneta
    {
        QString id = sel->getId();
        emit this->idChangeRequest(id);
    }
    else //click su vuoto
    {
        int riga = model->rowCount(index) - index.row();
        int colonna = index.column()+1;

        NuovaMonetaDialog nm(this);
        //apre un dialog preimpostato con la posizione e la dimensione
        nm.setParameters(this->contenitore, this->vassoio, riga, colonna, this->dim);
        int ret = nm.exec();
        if (ret == QDialog::Accepted)
        {
            MonetaXml* added = nm.getNuovaMoneta();
            //ricarica i dati del modello tab
            this->setData(Casella(riga, colonna), added);
            //ricarica i dati del modello lista
            emit this->newIdAdded(added);
        }
    }
}
