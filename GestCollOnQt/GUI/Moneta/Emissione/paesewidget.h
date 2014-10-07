#ifndef PAESEWIDGET_H
#define PAESEWIDGET_H

#include <QTextBrowser>

class PaeseWidget : public QTextBrowser
{
    Q_OBJECT
public:
    explicit PaeseWidget(QWidget *parent = 0);
    QString getPaese() const;
    void setEditingEnabled(bool enabled);
    void setText(const QString &text);

signals:
    void contentsChanged();

public slots:

private slots:
    void changed();
private:
    QString simpleText;
    bool editingEnabled;

};

#endif // PAESEWIDGET_H
