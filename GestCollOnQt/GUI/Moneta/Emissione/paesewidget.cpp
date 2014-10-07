#include "paesewidget.h"
#include "utils.h"
#include <QDebug>

PaeseWidget::PaeseWidget(QWidget *parent) :
    QTextBrowser(parent), editingEnabled(false), simpleText("")
{
    this->setOpenExternalLinks(true);
    connect(this->document(), SIGNAL(contentsChanged()), this, SLOT(changed()));
}

QString PaeseWidget::getPaese() const {
    return this->simpleText;
}

void PaeseWidget::setEditingEnabled(bool enabled)
{
    this->editingEnabled = enabled;
    this->setReadOnly(!enabled);
    this->setText(this->simpleText);
    if (enabled == false)
        this->setFrameShape(QFrame::NoFrame);
    else
        this->setFrameShape(QFrame::StyledPanel);
}

void PaeseWidget::setText(const QString &text)
{
    this->simpleText = text;
    if (this->editingEnabled == false) {
        if (text.isEmpty()) {
            return;
        }
        QString link = QString("<a href=\"%1\">%2</a>").arg(Utils::getSearchUrl(text).toString()).arg(text);
        QTextEdit::setText(link);
    } else {
        this->setPlainText(text);
    }
}

void PaeseWidget::changed() {
    if (this->simpleText != this->toPlainText()) {
        this->simpleText = this->toPlainText();
        emit contentsChanged();
    }
}
