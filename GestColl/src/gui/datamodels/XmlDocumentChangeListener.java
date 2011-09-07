/*
 * Modifiche:
 * -
 */

package gui.datamodels;

import gestXml.MonetaXml;
import gestXml.MonetaXml.Fields;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import main.Common;
import main.GestLog;

/**
 * Gestisce le modifiche ai singoli elementi, aggiornando il Documento DOM
 */
public class XmlDocumentChangeListener implements DocumentListener, PropertyChangeListener {

    private Fields field;
    private MonetaXml mng;

    /**
     * 
     * @param _mng
     * @param _field
     */
    public XmlDocumentChangeListener(MonetaXml _mng, Fields _field) {
        this.field = _field;
        this.mng = _mng;
    }


    /**
     * Gestisce la modifica, cambiando il valore
     * @param value
     */
    public void gestChange(String value) {
    	mng.setValue(this.field, value);
    }

    /**
     *
     * @param e
     */
    public void changedUpdate(DocumentEvent e) {
        try {
            gestChange(e.getDocument().getText(0, e.getDocument().getLength()));
        } catch (BadLocationException ex) {
            GestLog.Error(XmlDocumentChangeListener.class, ex);
        }
    }

    /**
     *
     * @param e
     */
    public void removeUpdate(DocumentEvent e) {
        try {
            gestChange(e.getDocument().getText(0, e.getDocument().getLength()));
        } catch (BadLocationException ex) {
            GestLog.Error(XmlDocumentChangeListener.class, ex);
        }
    }

    /**
     *
     * @param e
     */
    public void insertUpdate(DocumentEvent e) {
        try {
            gestChange(e.getDocument().getText(0, e.getDocument().getLength()));
        } catch (BadLocationException ex) {
            GestLog.Error(XmlDocumentChangeListener.class, ex);
        }
    }

    /**
     * Gestisce la modifica alla data (bean JDataChooser)
     *
     * @param evt
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("date")) {
            //la data va convertita nel formato xml normale
            Date d = (Date) (evt.getNewValue());
            DateFormat out = new SimpleDateFormat(Common.DATE_XML_FORMAT);
            String modifica = out.format(d);
            //gestisce la modifica
            this.gestChange(modifica);
        }
    }
};

