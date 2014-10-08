#ifndef PAESEWIDGET_H
#define PAESEWIDGET_H

#include <QLineEdit>
#include <QLabel>
#include <QStackedWidget>

class PaeseWidget : public QStackedWidget
{
    Q_OBJECT
public:
    explicit PaeseWidget(QWidget *parent = 0);
    QString getText() const;
    void setEditingEnabled(bool enabled);
    void setText(const QString &text);

signals:
    void contentsChanged();

public slots:

private slots:
    void changed(const QString& newText);
private:
    QLineEdit* textEditor;
    QLabel* textViewer;
    bool editingEnabled;

    QString internalText;

};

#endif // PAESEWIDGET_H
