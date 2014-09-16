#ifndef AUTORITAFORM_H
#define AUTORITAFORM_H

#include <QWidget>
#include "modelloautorita.h"
#include "scheda.hxx"

namespace Ui {
class AutoritaForm;
}

class AutoritaForm : public QWidget
{
    Q_OBJECT

public:
    explicit AutoritaForm(QWidget *parent = 0);
    ~AutoritaForm();
    void setData(gestColl::coins::autorita *autorita, const QString &_paese);
    void clear();
    void setEditable(bool editable);
private slots:
    void on_autoritaListView_doubleClicked(const QModelIndex &index);

    void on_addAutorita_clicked();

    void on_removeAutorita_clicked();

private:
    Ui::AutoritaForm *ui;
    ModelloAutorita* modelloAutorita; ///< modello autorita
    ::gestColl::coins::autorita* xmlDom;

    bool editable;
    QString paese;

    void updateAutorita(const gestColl::coins::autorita::nome_type &vecchio, const QString &nuovo);
signals:
    void changesOccurred();
};

#endif // AUTORITAFORM_H
