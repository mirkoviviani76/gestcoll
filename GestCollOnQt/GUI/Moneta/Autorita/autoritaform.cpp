#include "autoritaform.h"
#include "ui_autoritaform.h"
#include "autoritadialog.h"
#include "utils.h"
#include <QDesktopServices>
#include <QUrl>

AutoritaForm::AutoritaForm(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::AutoritaForm)
{
    ui->setupUi(this);
    modelloAutorita = new ModelloAutorita(this);
}

AutoritaForm::~AutoritaForm()
{
    this->modelloAutorita->clear();
    delete ui;
}

void AutoritaForm::setEditable(bool editable) {
    this->editable = editable;
    this->ui->addAutorita->setVisible(editable);
    this->ui->removeAutorita->setVisible(editable);
    if (editable) {
        this->ui->autoritaListView->setEditTriggers(QAbstractItemView::DoubleClicked);
    } else {
        this->ui->autoritaListView->setEditTriggers(QAbstractItemView::NoEditTriggers);
    }

}

void AutoritaForm::clear() {
    this->modelloAutorita->clear();
    this->ui->autoritaListView->reset();
}

void AutoritaForm::setData(gestColl::coins::autorita *autorita, const QString& _paese)
{
    this->modelloAutorita->fillData(autorita);
    this->xmlDom = autorita;
    this->ui->autoritaListView->setModel(this->modelloAutorita);
    this->paese = _paese;

}


void AutoritaForm::updateAutorita(const ::gestColl::coins::autorita::nome_type& vecchio, const QString& nuovo)
{
    ::gestColl::coins::autorita* currentAutorita = this->xmlDom;

    for (::gestColl::coins::autorita::nome_iterator it = currentAutorita->nome().begin(); it != currentAutorita->nome().end(); ++it)
    {
        //cerca l'item "giusto"
        QString cur = QString::fromStdWString(*it);
        if (cur == QString::fromStdWString(vecchio))
        {
            /* trovato: effettua le modifiche */
            *it = nuovo.toStdWString();
            break;
        }
    }

    //salva le modifiche nel dom
    *this->xmlDom = *currentAutorita;

    this->modelloAutorita->fillData(currentAutorita);

    //segnala la modifica
    emit this->changesOccurred();


}


void AutoritaForm::on_autoritaListView_doubleClicked(const QModelIndex &index)
{
    if (!index.isValid()) {
        return;
    }

    if (this->editable)
    {
        ::gestColl::coins::autorita::nome_type vecchio = this->modelloAutorita->getItem(index);
        AutoritaDialog autoritaDialog(this);
        autoritaDialog.setData(vecchio);
        int ret = autoritaDialog.exec();
        if (ret == QDialog::Accepted)
        {
            ::gestColl::coins::autorita::nome_type nuovoNome;
            autoritaDialog.getData(&nuovoNome);
            /* modifica/aggiunge il nodo al dom */
            this->updateAutorita(vecchio, QString::fromStdWString(nuovoNome));
            //segnala la modifica
            emit this->changesOccurred();
        }
    } else {
        ::gestColl::coins::autorita::nome_type autorita = this->modelloAutorita->getItem(index);
        QString searchData = QString("%1 (%2)")
                .arg(QString::fromStdWString(autorita))
                .arg(this->paese);
        QUrl searchUrl = Utils::getSearchUrl(searchData);
        QDesktopServices::openUrl(searchUrl);
    }

}

void AutoritaForm::on_addAutorita_clicked()
{
    /* attiva la vera gestione */
    AutoritaDialog autoritaDialog(this);
    //autoritaDialog.setData(a);
    int ret = autoritaDialog.exec();
    if (ret == QDialog::Accepted)
    {
        this->modelloAutorita->clear();
        ::gestColl::coins::autorita::nome_type nuovoNome;
        autoritaDialog.getData(&nuovoNome);
        /* modifica/aggiunge il nodo al dom */
        this->xmlDom->nome().push_back(nuovoNome);
        this->modelloAutorita->fillData(this->xmlDom);
        //segnala la modifica
        emit this->changesOccurred();
    }

}

void AutoritaForm::on_removeAutorita_clicked()
{
    gestColl::coins::autorita::nome_sequence leg(this->xmlDom->nome());
    foreach (QModelIndex index, this->ui->autoritaListView->selectionModel()->selectedIndexes()) {
        ::gestColl::coins::autorita::nome_type selectedName = this->modelloAutorita->getItem(index);
        for (unsigned int i = 0; i < leg.size(); i++) {
            ::gestColl::coins::autorita::nome_type curName = leg.at(i);

            if (selectedName == curName) {
                leg.erase(leg.begin()+i);
                break;
            }
        }
    }
    this->modelloAutorita->clear();
    this->xmlDom->nome(leg);
    //aggiorna il modello
    this->modelloAutorita->fillData(this->xmlDom);

    //segnala la modifica
    emit this->changesOccurred();

}
