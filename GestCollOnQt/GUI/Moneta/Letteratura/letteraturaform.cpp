#include "letteraturaform.h"
#include "ui_letteraturaform.h"

LetteraturaForm::LetteraturaForm(QWidget *parent) :
    QGroupBox(parent),
    ui(new Ui::LetteraturaForm), letteratura(NULL)
{
    ui->setupUi(this);

    this->modelloLetteratura = new LetteraturaModel(this);
    this->ui->letteraturaView->setModel(this->modelloLetteratura);
    connect(this->modelloLetteratura, SIGNAL(dataChanged(QModelIndex,QModelIndex)), this, SIGNAL(changesOccurred()));
    this->ui->letteraturaView->setItemDelegate(new LetteraturaDelegate(this));

}

LetteraturaForm::~LetteraturaForm()
{
    delete ui;
}

void LetteraturaForm::setData(gestColl::coins::letteratura *_letteratura)
{
    this->modelloLetteratura->clear();
    for ( ::gestColl::coins::letteratura::libro_iterator it = _letteratura->libro().begin();
          it != _letteratura->libro().end(); ++it) {
        this->modelloLetteratura->appendRow(&(*it));
    }
    this->letteratura = _letteratura;


    this->ui->letteraturaView->resizeColumnsToContents();
}

void LetteraturaForm::setEditable(bool editable)
{
    if (editable) {
        this->ui->letteraturaView->setEditTriggers(QAbstractItemView::DoubleClicked);
        this->ui->letteraturaView->setSelectionBehavior(QAbstractItemView::SelectItems);
        this->ui->letteraturaView->setSelectionMode(QAbstractItemView::SingleSelection);
    } else {
        this->ui->letteraturaView->setEditTriggers(QAbstractItemView::NoEditTriggers);
        this->ui->letteraturaView->setSelectionBehavior(QAbstractItemView::SelectRows);
        this->ui->letteraturaView->setSelectionMode(QAbstractItemView::SingleSelection);
    }
    this->ui->addLetteratura->setVisible(editable);
    this->ui->removeLetteratura->setVisible(editable);
    this->editable = editable;
}

void LetteraturaForm::on_addLetteratura_clicked()
{
    if (!this->editable)
        return;

    ::gestColl::coins::letteratura::libro_type nuovoLibro(L"Nuova letteratura",L"0");
    /* modifica/aggiunge il nodo al dom */
    this->letteratura->libro().push_back(nuovoLibro);
    this->setData(this->letteratura);

    //segnala la modifica
    emit this->changesOccurred();
}

void LetteraturaForm::on_removeLetteratura_clicked()
{
    if (!this->editable)
        return;

    ::gestColl::coins::letteratura* seq = this->letteratura;

    foreach (QModelIndex index, this->ui->letteraturaView->selectionModel()->selectedIndexes()) {
        int i = 0;
        for ( ::gestColl::coins::letteratura::libro_iterator it = this->letteratura->libro().begin();
              it != this->letteratura->libro().end(); ++it) {
            ::gestColl::coins::letteratura::libro_type sel = *(this->modelloLetteratura->getItem(index.row()));
            ::gestColl::coins::letteratura::libro_type curLibro = (*it);

            if ((sel.sigla() == curLibro.sigla()) && (sel.numero() == curLibro.numero())) {
                seq->libro().erase(seq->libro().begin()+i);
                break;
            }
            i++;
        }
    }
    //aggiorna il modello
    this->setData(seq);
    //segnala la modifica
    emit this->changesOccurred();

}
