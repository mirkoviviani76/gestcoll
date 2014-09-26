#include "contattiform.h"
#include "ui_contattiform.h"
#include "commondata.h"

#include "newcontattodialog.h"
#include "commondata.h"
#include <iostream>
#include "gestlog.h"
#include <QDebug>
#include <fstream>
#include "contatti.hxx"
#include "utils.h"
#include <QUrl>
#include <QDesktopServices>

#define ACTION_CONTATTO_DELETE ("Cancella contatto")


ContattiForm::ContattiForm(QWidget *parent) :
    QWidget(parent), editable(false),
    ui(new Ui::ContattiForm)
{
    ui->setupUi(this);
    this->enableEdit(this->editable);
    this->contattiModel = new ContattoModel(this);
    connect(this->contattiModel, SIGNAL(dataChanged(QModelIndex,QModelIndex)), this, SIGNAL(changesOccurred()));
    loadData();

    this->ui->contattiView->setItemDelegate(new ContattiDelegate(this->ui->contattiView, this));

}

void ContattiForm::loadData() {
    this->readXml(CommonData::getInstance()->getContatti());
    this->contattiModel->clear();
    this->contattiModel->fillData(&(this->contattiData->contatto()));
    this->ui->contattiView->setModel(this->contattiModel);
    this->ui->contattiView->resizeColumnsToContents();
    this->ui->contattiView->resizeRowsToContents();
}

ContattiForm::~ContattiForm()
{
    delete ui;
}


void ContattiForm::addItem() {
#if 0
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
#endif

}


bool ContattiForm::save() {

    bool ret = false;
    /* scrive su file */
    xml_schema::namespace_infomap xmlmap;
    xmlmap[L""].name = L"http://gestColl/contatti";
    xmlmap[L""].schema = L"contatti.xsd";
    std::ofstream myfile;
    myfile.open (CommonData::getInstance()->getContatti().toLatin1());
    if (myfile.is_open())
    {
        ::gestColl::contatti::contatti_(myfile, *(this->contattiData), xmlmap);
        myfile.close();
        ret = true;
    }
    else
    {
        ret = false;
    }
    return ret;
}

void ContattiForm::enableEdit(bool editable)
{
    if (editable) {
        this->ui->contattiView->setEditTriggers(QAbstractItemView::DoubleClicked);
        this->ui->contattiView->setSelectionBehavior(QAbstractItemView::SelectItems);
        this->ui->contattiView->setSelectionMode(QAbstractItemView::SingleSelection);
    } else {
        this->ui->contattiView->setEditTriggers(QAbstractItemView::NoEditTriggers);
        this->ui->contattiView->setSelectionBehavior(QAbstractItemView::SelectRows);
        this->ui->contattiView->setSelectionMode(QAbstractItemView::NoSelection);
    }
    //this->ui->addLetteratura->setVisible(editable);
    //this->ui->removeLetteratura->setVisible(editable);
    this->editable = editable;

}

void ContattiForm::readXml(const QFileInfo &file) {
    QString filename = file.canonicalFilePath();
    try {
        this->contattiData = (::gestColl::contatti::contatti_(file.canonicalFilePath().toStdWString(), xml_schema::flags::dont_validate));
    }
    catch (const xml_schema::exception& e)
    {
        QString msg = QString("%1 - %2").arg(filename).arg(e.what());
        std::wcerr << e;
        Log::Logger::getInstance()->log(msg, Log::FATAL);
        exit(-1);
    }
    catch (const xml_schema::properties::argument&)
    {
        QString msg = QString("%1 - %2").arg(filename).arg("invalid property argument (empty namespace or location)");
        Log::Logger::getInstance()->log(msg, Log::FATAL);
        exit(-1);
    }
    catch (const xsd::cxx::xml::invalid_utf16_string&)
    {
        QString msg = QString("%1 - %2").arg(filename).arg("invalid UTF-16 text in DOM model");
        Log::Logger::getInstance()->log(msg, Log::FATAL);
        exit(-1);
    }
    catch (const xsd::cxx::xml::invalid_utf8_string&)
    {
        QString msg = QString("%1 - %2").arg(filename).arg("invalid UTF-8 text in object model");
        Log::Logger::getInstance()->log(msg, Log::FATAL);
        exit(-1);
    }
    catch (...)
    {
        QString msg = QString("%1 - %2").arg(filename).arg("Unknown exception");
        Log::Logger::getInstance()->log(msg, Log::FATAL);
        exit(-1);
    }

}


