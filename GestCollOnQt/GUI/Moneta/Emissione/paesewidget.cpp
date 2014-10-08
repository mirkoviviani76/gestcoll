#include "paesewidget.h"
#include "utils.h"
#include <QDebug>
#include <QLayout>
#include <QAbstractTextDocumentLayout>
#include <QScrollBar>

PaeseWidget::PaeseWidget(QWidget *parent) :
    QStackedWidget(parent), editingEnabled(false), internalText("")
{
    this->textViewer = new QLabel(this);
    this->textEditor = new QLineEdit(this);
    this->textViewer->setOpenExternalLinks(true);
    this->textViewer->setWordWrap(true);

    this->addWidget(this->textViewer);
    this->addWidget(this->textEditor);

    connect(this->textEditor, SIGNAL(textChanged(QString)), this, SLOT(changed(QString)));
}

QString PaeseWidget::getText() const {
    return this->internalText;
}

void PaeseWidget::setEditingEnabled(bool enabled)
{
    this->editingEnabled = enabled;

    if (this->editingEnabled) {
        this->setCurrentWidget(this->textEditor);
    } else {
        this->setCurrentWidget(this->textViewer);
    }

    this->setText(this->internalText);
}

void PaeseWidget::setText(const QString &text)
{
    this->internalText = text;
    if (this->editingEnabled == false) {
        QUrl url = Utils::getSearchUrl(internalText).toString();
        if (url.isValid()) {
            QString link = QString("<a href=\"%1\">%2</a>").arg(url.toString()).arg(internalText);
            this->textViewer->setText(link);
        }
    }
    this->textEditor->setText(this->internalText);

}

void PaeseWidget::changed(const QString &newText) {
    if (this->internalText != newText) {
        this->setText(newText);
        emit contentsChanged();
    }
}

