#include "contattiform.h"
#include "ui_contattiform.h"
#include "contattixml.h"
#include "commondata.h"

#include <QDebug>
#include "newcontattodialog.h"

#define ACTION_CONTATTO_DELETE ("Cancella contatto")

ContattiForm::ContattiForm(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::ContattiForm)
{
    ui->setupUi(this);
    this->contattoSelezionato = NULL;
    this->contextMenu.addAction(ACTION_CONTATTO_DELETE);


    this->ui->listView->setContextMenuPolicy(Qt::CustomContextMenu);
    this->contattiModel = new GenericModel();
    this->loadData();

}

void ContattiForm::loadData() {
    this->contattiModel->clear();
    /* riempie il modello */
    foreach (xml::Contatto* cont, contattiXml.getContatti()) {
        this->contattiModel->appendRow(cont);
    }
    this->ui->listView->setModel(this->contattiModel);

}

ContattiForm::~ContattiForm()
{
    delete ui;
    if (this->contattiModel != NULL) {
        this->contattiModel->clear();
        delete this->contattiModel;
        this->contattiModel = NULL;
    }

}

void ContattiForm::on_listView_customContextMenuRequested(const QPoint &pos)
{
    // for most widgets
    QPoint globalPos = this->ui->listView->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenu.exec(globalPos);
    if (selectedItem && (this->contattoSelezionato != NULL))
    {
        if (selectedItem->text() == ACTION_CONTATTO_DELETE) {
            //ottiene l'indice selezionato
            int index = this->ui->listView->currentIndex().row();
            //ottiene l'item
            xml::Contatto* l = (xml::Contatto*) this->contattiModel->getItem(index);
            //cancella dalla lista
            this->contattiXml.deleteContatto(l);
            //ricarica la vista
            this->loadData();
        }
    }

}

void ContattiForm::on_listView_activated(const QModelIndex &index)
{
    xml::Contatto* contatto = (xml::Contatto*) this->contattiModel->getItem(index);
    this->contattoSelezionato = contatto;
}

void ContattiForm::addItem() {
    NewContattoDialog ncd(this);
    int ret = ncd.exec();
    if (ret == QDialog::Accepted) {
        this->contattiXml.addItem(ncd.getNome(), ncd.getEmail(), ncd.getNote());
        //svuota il modello
        this->contattiModel->clear();
        /* riempie di nuovo il modello */
        foreach (xml::Contatto* cont, contattiXml.getContatti()) {
            this->contattiModel->appendRow(cont);
        }
        //segnala l'esistenza di modifiche non ancora salvate
        emit this->changesOccurred();

    }


}


/**
  Salva il documento corrente
  */
void ContattiForm::salva()
{
    this->contattiXml.save();
}



void ContattiForm::on_listView_doubleClicked(const QModelIndex &index)
{
    GenericModel* model = (GenericModel*)index.model();
    xml::Contatto* cont = (xml::Contatto*) model->getItem(index);
    NewContattoDialog dialog(this);
    dialog.setData(*cont);
    int ret = dialog.exec();
    if (ret == QDialog::Accepted)
    {
        xml::Contatto nuovo(dialog.getNome(), dialog.getEmail(), dialog.getNote());
        /* modifica/aggiunge il nodo al dom */
        this->contattiXml.setContatto(*cont, nuovo);
        /* aggiorna la vista */
        cont->nome = nuovo.nome;
        cont->email = nuovo.email;
        cont->note = nuovo.note;
        //segnala l'esistenza di modifiche non ancora salvate
        emit this->changesOccurred();
    }
}

void ContattiForm::on_listView_clicked(const QModelIndex &index)
{
    this->on_listView_activated(index);
}
