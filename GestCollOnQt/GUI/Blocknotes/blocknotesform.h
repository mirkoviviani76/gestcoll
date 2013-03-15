#ifndef BLOCKNOTESFORM_H
#define BLOCKNOTESFORM_H

#include <QWidget>
#include <QFileSystemModel>

namespace Ui {
class BlocknotesForm;
}

/**
  Gestore del blocco note, ovvero dell'insieme di files "liberi".
  */
class BlocknotesForm : public QWidget
{
    Q_OBJECT
    
public:
    /**
      Costruttore
      */
    explicit BlocknotesForm(QWidget *parent = 0);
    /**
      Distruttore
      */
    ~BlocknotesForm();
    /**
      Salva la nota aperta
      */
    void salva();
    /**
      Aggiunge una nuova nota
      */
    void addNote();
    
private slots:
    void on_treeView_activated(const QModelIndex &index);

    void on_textEdit_textChanged();

private:
    Ui::BlocknotesForm *ui; ///< la ui
    QFileSystemModel* blocknotesListModel; ///< il modello
    QString selectedFile; ///< il file attualmente selezionato

signals:
    void changesOccurred(); ///< segnala che si e' verificato un cambiamento nel file corrente

};

#endif // BLOCKNOTESFORM_H
