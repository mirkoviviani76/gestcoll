/*
 * Modifiche:
 * -
 */

package gui.moneta;

import gui.datamodels.XmlDocumentChangeListener;

/**
 *
 * 
 */
public class ZeccaControl extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private javax.swing.JTextField jTFNome;

	private javax.swing.JTextField jTFSegno;

	/** Creates new form ZeccaControl */
	public ZeccaControl() {
		initComponents();
	}

	/**
	 * aggiunge il listener per le modifiche ai testi
	 * 
	 * @param myDocumentListenerForName
	 * @param myDocumentListenerForSign
	 */
	void addDocumentListener(
			XmlDocumentChangeListener myDocumentListenerForName,
			XmlDocumentChangeListener myDocumentListenerForSign) {
		this.jTFNome.getDocument().addDocumentListener(
				myDocumentListenerForName);
		this.jTFSegno.getDocument().addDocumentListener(
				myDocumentListenerForSign);
	}

	/**
	 * 
	 * @return
	 */
	public String getNome() {
		return this.jTFNome.getText();
	}

	/**
	 * 
	 * @return
	 */
	public String getSegno() {
		return this.jTFSegno.getText();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		jTFNome = new javax.swing.JTextField();
		jTFSegno = new javax.swing.JTextField();

		setLayout(new java.awt.GridBagLayout());
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 0.75;
		add(jTFNome, gridBagConstraints);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 0.25;
		add(jTFSegno, gridBagConstraints);
	}

	/**
	 * rimuove il listener per le modifiche ai testi
	 * 
	 * @param myDocumentListenerForName
	 * @param myDocumentListenerForSign
	 */
	void removeDocumentListener(
			XmlDocumentChangeListener myDocumentListenerForName,
			XmlDocumentChangeListener myDocumentListenerForSign) {
		this.jTFNome.getDocument().removeDocumentListener(
				myDocumentListenerForName);
		this.jTFSegno.getDocument().removeDocumentListener(
				myDocumentListenerForSign);
	}

	/**
	 * 
	 * @param flag
	 */
	public void setEditable(boolean flag) {
		this.jTFNome.setEditable(flag);
		this.jTFSegno.setEditable(flag);
	}

	/**
	 * 
	 * @param v
	 */
	public void setNome(String v) {
		this.jTFNome.setText(v);
	}

	/**
	 * 
	 * @param u
	 */
	public void setSegno(String u) {
		this.jTFSegno.setText(u);
	}
	/**
	 * 
	 * @param zecca
	 */
	public void setZecca(XmlData.Moneta.Zecca zecca) {
		if (zecca == null)
			return;
		if (zecca.getNome() != null) {
			this.setNome(zecca.getNome());
		}
		if (zecca.getSegno() != null) {
			this.setSegno(zecca.getSegno());
		}
	}

}
