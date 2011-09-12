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

	private javax.swing.JTextField jTFUnita;

	private javax.swing.JTextField jTFValore;

	/** Creates new form MisuraControl */
	public MisuraControl() {
		initComponents();
	}

	/**
	 * aggiunge il listener per le modifiche
	 * 
	 * @param myDocumentListenerForValue
	 * @param myDocumentListenerForUnit
	 */
	void addDocumentListener(
			XmlDocumentChangeListener myDocumentListenerForValue,
			XmlDocumentChangeListener myDocumentListenerForUnit) {
		this.jTFValore.getDocument().addDocumentListener(
				myDocumentListenerForValue);
		this.jTFUnita.getDocument().addDocumentListener(
				myDocumentListenerForUnit);
	}

	/**
	 * 
	 * @return l'unita'
	 */
	public String getUnita() {
		return this.jTFUnita.getText();
	}

	/**
	 * 
	 * @return il valore
	 */
	public String getValore() {
		return this.jTFValore.getText();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
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
	}

	/**
	 * rimuove il listener per le modifiche
	 * 
	 * @param myDocumentListenerForValue
	 * @param myDocumentListenerForUnit
	 */
	void removeDocumentListener(
			XmlDocumentChangeListener myDocumentListenerForValue,
			XmlDocumentChangeListener myDocumentListenerForUnit) {
		this.jTFValore.getDocument().removeDocumentListener(
				myDocumentListenerForValue);
		this.jTFUnita.getDocument().removeDocumentListener(
				myDocumentListenerForUnit);
	}

	/**
	 * setta l'editabilita' del campo
	 * 
	 * @param flag
	 */
	public void setEditable(boolean flag) {
		this.jTFUnita.setEditable(flag);
		this.jTFValore.setEditable(flag);
	}

	/**
	 * setta la misura con un'altra misura
	 * 
	 * @param m
	 */
	public void setMisura(Misura m) {

		String val = "";
		String un = "";
		if (m != null) {
			if (m.getValore() != null) {
				val = m.getValore().toPlainString();
			}
			if (m.getUnita() != null) {
				un = m.getUnita();
			}
		}
		this.setUnita(un);
		this.setValore(val);
	}

	/**
	 * setta la misura con un nominale
	 * 
	 * @param nominale
	 */
	public void setMisura(Nominale nominale) {
		this.setUnita(nominale.getValuta());
		this.setValore(nominale.getValore());
	}

	/**
	 * setta l'unita'
	 * 
	 * @param u
	 */
	public void setUnita(String u) {
		this.jTFUnita.setText(u);
	}
	/**
	 * setta il valore
	 * 
	 * @param v
	 */
	public void setValore(String v) {
		this.jTFValore.setText(v);
	}

}
