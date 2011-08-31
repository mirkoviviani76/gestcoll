/*
 * Modifiche:
 * -
 */

package gui.moneta;

import gui.datamodels.XmlDocumentChangeListener;
import XmlData.Moneta.Misura;
import XmlData.Moneta.Nominale;

/**
 *
 * 
 */
public class MisuraControl extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates new form MisuraControl */
	public MisuraControl() {
		initComponents();
	}

	/**
	 * 
	 * @return
	 */
	public String getValore() {
		return this.jTFValore.getText();
	}

	/**
	 * 
	 * @return
	 */
	public String getUnita() {
		return this.jTFUnita.getText();
	}

	/**
	 * 
	 * @param v
	 */
	public void setValore(String v) {
		this.jTFValore.setText(v);
	}

	/**
	 * 
	 * @param u
	 */
	public void setUnita(String u) {
		this.jTFUnita.setText(u);
	}

	/**
	 * 
	 * @param v
	 * @param u
	 */
	public void setMisura(String v, String u) {
		this.setUnita(u);
		this.setValore(v);
	}

	/**
	 * 
	 * @param misura
	 */
	public void setMisura(String[] misura) {
		this.setMisura(misura[0], misura[1]);
	}

	/**
	 * 
	 * @param flag
	 */
	public void setEditable(boolean flag) {
		this.jTFUnita.setEditable(flag);
		this.jTFValore.setEditable(flag);
	}

	  /**
     * Aggiunge il listener per le modifiche ai testi
     * @param myDocumentListener
     */
    void addDocumentListenerForUnit(XmlDocumentChangeListener myDocumentListener) {
        this.jTFUnita.getDocument().addDocumentListener(myDocumentListener);
    }

    /**
     * Aggiunge il listener per le modifiche ai testi
     * @param myDocumentListener
     */
    void addDocumentListenerForValue(XmlDocumentChangeListener myDocumentListener) {
        this.jTFValore.getDocument().addDocumentListener(myDocumentListener);
    }

   void removeDocumentListenerForUnit(XmlDocumentChangeListener myDocumentListener) {
        this.jTFUnita.getDocument().removeDocumentListener(myDocumentListener);
    }

    /**
     * Aggiunge il listener per le modifiche ai testi
     * @param myDocumentListener
     */
    void removeDocumentListenerForValue(XmlDocumentChangeListener myDocumentListener) {
        this.jTFValore.getDocument().removeDocumentListener(myDocumentListener);
    }



	/**
	 * This method is called from within the constructor to initialize the form.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		jTFValore = new javax.swing.JTextField();
		jTFUnita = new javax.swing.JTextField();

		setLayout(new java.awt.GridBagLayout());
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 0.75;
		add(jTFValore, gridBagConstraints);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 0.25;
		add(jTFUnita, gridBagConstraints);
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JTextField jTFUnita;
	private javax.swing.JTextField jTFValore;

	// End of variables declaration//GEN-END:variables
	public void setMisura(Nominale nominale) {
		this.setMisura(nominale.getValore(), nominale.getValuta());
	}

	public void setMisura(Misura m) {

		String val = "";
		String un = "";
		if (m != null) {
			if (m.getValore() != null)
				val = m.getValore().toPlainString();
			if (m.getUnita() != null)
				un = m.getUnita();
		}
		this.setMisura(val, un);
	}

}
