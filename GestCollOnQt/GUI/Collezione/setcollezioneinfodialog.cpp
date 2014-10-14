#include "setcollezioneinfodialog.h"
#include "ui_setcollezioneinfodialog.h"
#include "collezionexml.h"
#include "modifyambitodialog.h"


#define ACTION_ADD_AMBITO ("Aggiungi ambito")

SetCollezioneInfoDialog::SetCollezioneInfoDialog(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::SetCollezioneInfoDialog)
{
    ui->setupUi(this);

    this->ambitiModel = new AmbitiModel(this);
    info = CollezioneXml::getInstance()->getInfo();

    this->ui->titolo->setText(info->titolo);
    this->ui->proprietario->setText(info->proprietario);
    this->ui->inizio->setDisplayFormat("dd/MM/yyyy");
    this->ui->inizio->setDate(info->inizio);

    foreach (xml::Ambito* a, info->ambiti) {
        ambitiModel->appendRow(a);
    }
    this->ui->ambiti->setModel(ambitiModel);
    this->ui->ambiti->resizeRowsToContents();
    this->ui->ambiti->resizeColumnsToContents();

    this->ui->ambiti->setContextMenuPolicy(Qt::CustomContextMenu);
    this->contextMenu.addAction(ACTION_ADD_AMBITO);

}

SetCollezioneInfoDialog::~SetCollezioneInfoDialog()
{
    delete ui;
    if (this->ambitiModel != NULL) {
        this->ambitiModel->clear();
        delete this->ambitiModel;
        this->ambitiModel = NULL;
    }
}

void SetCollezioneInfoDialog::on_buttonBox_accepted()
{
    this->info->proprietario = this->ui->proprietario->text();
    this->info->titolo = this->ui->titolo->text();
    this->info->inizio = this->ui->inizio->date();
    //gli ambiti vengono gestiti a parte (vedi on_ambiti_doubleClicked e on_ambiti_contextMenu)
    CollezioneXml::getInstance()->setInfo(info);
}

void SetCollezioneInfoDialog::on_ambiti_doubleClicked(const QModelIndex &index)
{
    xml::Ambito* a = this->ambitiModel->getItem(index);
    xml::Ambito vecchio(a->titolo, a->icona);
    ModifyAmbitoDialog modifyAmbito(a, this);
    int ret = modifyAmbito.exec();
    if (ret == QDialog::Accepted) {
        a = modifyAmbito.getData();
        //modifica l'ambito nelle monete
        CollezioneXml::getInstance()->updateAmbitiInCoins(vecchio, *a);
    } else {
        a->titolo = vecchio.titolo;
        a->icona = vecchio.icona;
    }
}

void SetCollezioneInfoDialog::on_ambiti_customContextMenuRequested(const QPoint &pos)
{
    // for most widgets
    QPoint globalPos = this->ui->ambiti->mapToGlobal(pos);
    QAction* selectedItem = this->contextMenu.exec(globalPos);
    if (selectedItem)
    {
        if (selectedItem->text() == ACTION_ADD_AMBITO) {
            ModifyAmbitoDialog modifyAmbito(this);
            int ret = modifyAmbito.exec();
            if (ret == QDialog::Accepted) {
                xml::Ambito* nuovo = modifyAmbito.getData();
                //modifica l'ambito nelle monete
                CollezioneXml::getInstance()->addAmbito(nuovo);
                this->ambitiModel->appendRow(nuovo);
            }
        }
    }
    else
    {
        // nothing was chosen
    }

}
