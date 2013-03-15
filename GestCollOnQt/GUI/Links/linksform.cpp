#include "linksform.h"
#include "ui_linksform.h"
#include <link.h>
#include <commondata.h>

#include <QFile>
#include <QSplashScreen>
#include <QDomElement>
#include <QDomNode>

#include <QList>
#include <QFileInfo>
#include <iostream>
#include "gestlog.h"
#include "newlinkdialog.h"

#include <QDebug>
#include <QtAlgorithms>

extern QSplashScreen* splash;


#define ACTION_DELETE_LINK ("Cancellazione link")

/**
  Algoritmo di ordinamento per i links
  */
int compareLinkPointers(const void *p1, const void *p2)
{
    const xml::Link* a1 = ( const xml::Link * ) p1 ;
    const xml::Link* a2 = ( const xml::Link * ) p2 ;
    return a1->getNome() < a2->getNome();

}

LinksForm::LinksForm(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::LinksForm)
{
    ui->setupUi(this);

    this->ui->textBrowser->setOpenExternalLinks(true);
    this->ui->textBrowser->setOpenLinks(true);
    this->ui->textBrowser->setReadOnly(true);

    this->contextMenu.addAction(ACTION_DELETE_LINK);
    this->ui->treeWidget->setContextMenuPolicy(Qt::CustomContextMenu);


    /* visualizza i dati */
    this->setText();

}

LinksForm::~LinksForm()
{
    delete ui;
}

void LinksForm::changeEvent(QEvent *e)
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

/**
  Salva il documento corrente
  */
void LinksForm::salva()
{
    this->linksXml.save();
}

void LinksForm::addItem() {
    NewLinkDialog ncd(this->linksXml.getCategorie(), this);
    int ret = ncd.exec();
    if (ret == QDialog::Accepted) {
        this->linksXml.addItem(ncd.getCategoria(), ncd.getNome(), ncd.getUrl(), ncd.getNote());
        /* rebuild tree */
        this->ui->treeWidget->clear();
        this->setText();

        //segnala l'esistenza di modifiche non ancora salvate
        emit this->changesOccurred();
    }
}


/**
  carica i dati nell'albero
  */
void LinksForm::setText()
{
     QList<QTreeWidgetItem *> topItems;
     foreach (QString categoria, this->linksXml.getCategorie())
     {
         TreeItem* currItem = new TreeItem(NULL,
                 this->ui->treeWidget,
                 QStringList() << QString("%1").arg(categoria)
                 );

         //ordina i dati della categoria
         QList<xml::Link*> itemsPerCategoria = this->linksXml.getLinks(categoria);
         qSort(itemsPerCategoria.begin(),
               itemsPerCategoria.end(),
               compareLinkPointers);

         /* aggiunge all'albero i dati della categoria corrente */
         foreach (xml::Link* item, itemsPerCategoria)
         {
             QString riga = QString("%1")
                            .arg(item->getNome());
             TreeItem* current = new TreeItem(item, QStringList() << riga);
             currItem->addChild(current);
         }
         topItems.append(currItem);
     }
     this->ui->treeWidget->insertTopLevelItems(0, topItems);


}


/**
  gestisce il doppio click su un item della lista
  */
void LinksForm::on_treeWidget_itemDoubleClicked(QTreeWidgetItem *item, int column)
{
    Q_UNUSED(column);

    xml::Link* foundLink = ((TreeItem* )item)->getData();

    if (foundLink != NULL) {
        if (!this->isEditMode()) {
            QString riga = QString("<h1>%1</h1> <h2>Link:</h2>(<a href=\"%2\">%2</a>) <h2>Note</h2>%3")
                    .arg(foundLink->getNome())
                    .arg(foundLink->getUrl().toString())
                    .arg(foundLink->getNote());
            this->ui->textBrowser->setText(riga);
        } else {
            NewLinkDialog dialog(this->linksXml.getCategorie(), this);
            dialog.setData(foundLink);
            dialog.setCategoria(item->parent()->text(0));
            int ret = dialog.exec();
            if (ret == QDialog::Accepted)
            {
                xml::Link nuovo(dialog.getNome(), dialog.getUrl(), dialog.getNote());
                /* modifica/aggiunge il nodo al dom */
                this->linksXml.setLink(item->parent()->text(0), *foundLink, nuovo);

                /* rebuild tree */
                this->ui->treeWidget->clear();
                this->setText();
                //segnala l'esistenza di modifiche non ancora salvate
                emit this->changesOccurred();
            }

        }
    }

}


void LinksForm::enableEdit(bool editmode) {
    this->editMode = editmode;
}

bool LinksForm::isEditMode() {
    return (this->editMode == true);
}



void LinksForm::on_treeWidget_customContextMenuRequested(const QPoint &pos)
{
    // for most widgets
    QPoint globalPos = this->ui->treeWidget->mapToGlobal(pos);
    // for QAbstractScrollArea and derived classes you would use:
    // QPoint globalPos = myWidget->viewport()->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenu.exec(globalPos);
    if (selectedItem)
    {
        if (selectedItem->text() == ACTION_DELETE_LINK)
        {
            if (this->ui->treeWidget->selectedItems().count() > 0) {
                TreeItem* item = (TreeItem*) this->ui->treeWidget->selectedItems().at(0);
                xml::Link* link = item->getData();
                //TODO chiedere conferma
                this->linksXml.deleteLink(item->parent()->text(0), link);
                /* rebuild tree */
                this->ui->treeWidget->clear();
                this->setText();
                //segnala l'esistenza di modifiche non ancora salvate
                emit this->changesOccurred();

            }
        }
    }

}
