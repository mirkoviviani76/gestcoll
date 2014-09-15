#include "noteform.h"
#include "ui_noteform.h"
#include "notadialog.h"

NoteForm::NoteForm(QWidget *parent) :
    QGroupBox(parent), xmlDom(NULL),
    ui(new Ui::NoteForm)
{
    ui->setupUi(this);
    this->notaModel = new ModelloNota(this);

}

NoteForm::~NoteForm()
{
    delete ui;
}



void NoteForm::setEditable(bool editable) {
    this->editable = editable;
    this->ui->addNota->setVisible(editable);
    this->ui->removeNota->setVisible(editable);
    if (editable) {
        this->ui->noteView->setEditTriggers(QAbstractItemView::DoubleClicked);
    } else {
        this->ui->noteView->setEditTriggers(QAbstractItemView::NoEditTriggers);
    }

}

void NoteForm::clear() {
    this->notaModel->clear();
    this->ui->noteView->reset();
}

void NoteForm::setData(::gestColl::coins::note *note)
{
    this->xmlDom = note;
    this->notaModel->clear();
    this->notaModel->fillData(note);
    this->ui->noteView->setModel(this->notaModel);

}

void NoteForm::on_addNota_clicked()
{
    if (this->editable) {
        ::gestColl::coins::note::nota_type nuovoNome(L"Nuova nota");
        /* modifica/aggiunge il nodo al dom */
        this->xmlDom->nota().push_back(nuovoNome);
        this->notaModel->clear();
        this->notaModel->fillData(this->xmlDom);

        //segnala la modifica
        emit this->changesOccurred();
    }

}

void NoteForm::on_removeNota_clicked()
{
    gestColl::coins::note::nota_sequence noteSeq(this->xmlDom->nota());
    foreach (QModelIndex index, this->ui->noteView->selectionModel()->selectedIndexes()) {
        ::gestColl::coins::note::nota_type selectedNota = this->notaModel->getItem(index);
        for (unsigned int i = 0; i < noteSeq.size(); i++) {
            ::gestColl::coins::note::nota_type curNota = noteSeq.at(i);
            if (selectedNota == curNota) {
                noteSeq.erase(noteSeq.begin()+i);
                break;
            }
        }
    }
    this->notaModel->clear();
    this->xmlDom->nota(noteSeq);
    //aggiorna il modello
    this->notaModel->fillData(this->xmlDom);
    //segnala la modifica
    emit this->changesOccurred();

}


void NoteForm::on_noteView_doubleClicked(const QModelIndex& index)
{
    ::gestColl::coins::note::nota_type vecchia = this->notaModel->getItem(index);
    ::gestColl::coins::note::nota_type nuova;
    NotaDialog notaDialog(this->editable, this);
    notaDialog.setData(vecchia);
    int ret = notaDialog.exec();
    if (ret == QDialog::Accepted && this->editable)
    {
        ::gestColl::coins::note* currentNote = this->xmlDom;
        notaDialog.getData(&nuova);

        for (::gestColl::coins::note::nota_iterator it = currentNote->nota().begin(); it != currentNote->nota().end(); ++it)
        {
            //cerca l'item "giusto"
            QString cur = QString::fromStdWString(*it);
            if (cur == QString::fromStdWString(vecchia))
            {
                /* trovato: effettua le modifiche */
                *it = nuova;
                break;
            }
        }

        //salva le modifiche nel dom
        *this->xmlDom = *currentNote;

        this->notaModel->fillData(this->xmlDom);

        //segnala la modifica
        emit this->changesOccurred();


    }

}
