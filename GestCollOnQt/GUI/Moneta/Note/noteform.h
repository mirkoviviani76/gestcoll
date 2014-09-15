#ifndef NOTEFORM_H
#define NOTEFORM_H

#include <QGroupBox>
#include "scheda.hxx"
#include "notamodel.h"

namespace Ui {
class NoteForm;
}

class NoteForm : public QGroupBox
{
    Q_OBJECT

public:
    explicit NoteForm(QWidget *parent = 0);
    ~NoteForm();
    void setData(::gestColl::coins::note *xmlDom);
    void clear();
    void setEditable(bool editable);


private:
    ModelloNota* notaModel;
    Ui::NoteForm *ui;
    bool editable;

    ::gestColl::coins::note *xmlDom;

signals:
    void changesOccurred();
private slots:
    void on_addNota_clicked();
    void on_removeNota_clicked();
    void on_noteView_doubleClicked(const QModelIndex &index);
};

#endif // NOTEFORM_H
