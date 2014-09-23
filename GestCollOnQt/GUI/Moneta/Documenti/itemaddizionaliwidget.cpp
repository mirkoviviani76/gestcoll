#include "itemaddizionaliwidget.h"
#include "ui_itemaddizionaliwidget.h"
#include "documentidelegate.h"
#include "commondata.h"
#include <QUrl>
#include <QDesktopServices>
#include <QMessageBox>
#include "adddocumentdialog.h"

ItemAddizionaliWidget::ItemAddizionaliWidget(QWidget *parent) :
    QGroupBox(parent),
    ui(new Ui::ItemAddizionaliWidget)
{
    ui->setupUi(this);

    documentiModel = new DocumentiModel(this);
    DocumentiDelegate* documentiDelegate = new DocumentiDelegate(this);
    connect(documentiDelegate, SIGNAL(closeEditor(QWidget*)), this, SIGNAL(changesOccurred()));

    this->ui->itemsView->setItemDelegate(documentiDelegate);

}

ItemAddizionaliWidget::~ItemAddizionaliWidget()
{
    this->documentiModel->clear();
    delete ui;
}



void ItemAddizionaliWidget::setEditable(bool editable) {
    this->editingEnabled = editable;
    this->ui->addItem->setVisible(editable);
    this->ui->removeItem->setVisible(editable);
    if (editable) {
        this->ui->itemsView->setEditTriggers(QAbstractItemView::DoubleClicked);
    } else {
        this->ui->itemsView->setEditTriggers(QAbstractItemView::NoEditTriggers);
    }

}

void ItemAddizionaliWidget::clear() {
    this->documentiModel->clear();
    this->ui->itemsView->reset();
}

void ItemAddizionaliWidget::setData(::gestColl::coins::moneta::itemAddizionali_type* items)
{
    this->documentiModel->clear();
    for ( gestColl::coins::moneta::itemAddizionali_type::documento_iterator it = items->documento().begin();
          it != items->documento().end(); ++it) {
        this->documentiModel->appendRow(*it);
    }
    this->xmlDom = items;
    this->ui->itemsView->setModel(this->documentiModel);

}



void ItemAddizionaliWidget::updateDocumento(const ::gestColl::coins::documentoAddizionale& vecchio,
                                            const ::gestColl::coins::documentoAddizionale& nuovo)
{
    ::gestColl::coins::moneta::itemAddizionali_type* currentDocs = this->xmlDom;

    for (::gestColl::coins::moneta::itemAddizionali_type::documento_iterator it = currentDocs->documento().begin();
         it != currentDocs->documento().end(); ++it)
    {
        //cerca l'item "giusto"
        ::gestColl::coins::documentoAddizionale cur = *it;
        if ((cur.descrizione() == vecchio.descrizione()) && (cur.filename() == vecchio.filename()))
        {
            /* trovato: effettua le modifiche */
            *it = nuovo;
            break;
        }
    }

    //salva le modifiche nel dom
    *this->xmlDom = *currentDocs;

    this->setData(this->xmlDom);

    //segnala la modifica
    emit this->changesOccurred();


}


void ItemAddizionaliWidget::on_itemsView_doubleClicked(const QModelIndex &index)
{
    if (!index.isValid())
        return;

    ::gestColl::coins::documentoAddizionale doc = this->documentiModel->getItem(index);

    if (this->editingEnabled) {
        AddDocumentDialog dialog(this);
        dialog.setData(doc);
        int ret = dialog.exec();
        if (ret == QDialog::Accepted)
        {
            QString nuovoFilename = dialog.getFilenames().at(0);
            QString nuovaDescrizione = dialog.getDescrizione();
            ::gestColl::coins::documentoAddizionale nuovo(nuovoFilename.toStdWString(), nuovaDescrizione.toStdWString());
            /* modifica/aggiunge il nodo al dom */
            this->updateDocumento(doc, nuovo);
            //segnala la modifica
            emit this->changesOccurred();

        }
    } else {

        QString filename = QString("%1/%2").arg(CommonData::getInstance()->getBiblioDir()).arg(QString::fromStdWString(doc.filename()));
        QUrl url = QUrl::fromLocalFile(filename);
        if (url.isValid() && QFile(filename).exists()) {
            QDesktopServices::openUrl(url);
        } else {
            QMessageBox::warning(this, CommonData::getInstance()->getAppName(), QString("Impossible aprire %1")
                                 .arg(filename));
        }
    }
}


void ItemAddizionaliWidget::on_addItem_clicked()
{
    if (!this->editingEnabled)
        return;

    AddDocumentDialog addDoc(this);
    int ret = addDoc.exec();
    if (ret == QDialog::Accepted) {
        QStringList selection = addDoc.getFilenames();
        foreach (QString file, selection) {
            QString descrizione = addDoc.getDescrizione();
            ::gestColl::coins::documentoAddizionale nuovo(file.toStdWString(), descrizione.toStdWString());
            this->xmlDom->documento().push_back(nuovo);
        }
        this->setData(this->xmlDom);
        //segnala la modifica
        emit this->changesOccurred();
    }


}

void ItemAddizionaliWidget::on_removeItem_clicked()
{
    if (!this->editingEnabled)
        return;

    gestColl::coins::documentiAggiuntivi::documento_sequence leg(this->xmlDom->documento());
    foreach (QModelIndex index, this->ui->itemsView->selectionModel()->selectedIndexes()) {
        ::gestColl::coins::documentoAddizionale sel = this->documentiModel->getItem(index);
        for (unsigned int i = 0; i < leg.size(); i++) {
            ::gestColl::coins::documentoAddizionale cur = leg.at(i);

            if ((sel.filename() == cur.filename()) && (sel.descrizione() == cur.descrizione())) {
                leg.erase(leg.begin()+i);
                break;
            }
        }
    }
    this->xmlDom->documento(leg);
    //aggiorna il modello
    this->setData(this->xmlDom);
    //segnala la modifica
    emit this->changesOccurred();

}
