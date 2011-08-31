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

	/** Creates new form ZeccaControl */
	public ZeccaControl() {
		initComponents();
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
		if (zecca.getNome() != null)
			this.setNome(zecca.getNome());
		if (zecca.getSegno() != null)
			this.setSegno(zecca.getSegno());
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
     * Aggiunge il listener per le modifiche ai testi
     * @param myDocumentListener
     */
    void addDocumentListenerForNome(XmlDocumentChangeListener myDocumentListener) {
        this.jTFNome.getDocument().addDocumentListener(myDocumentListener);
    }

    /**
     * Aggiunge il listener per le modifiche ai testi
     * @param myDocumentListener
     */
    void addDocumentListenerForSegno(XmlDocumentChangeListener myDocumentListener) {
        this.jTFSegno.getDocument().addDocumentListener(myDocumentListener);
    }


    /**
     * Aggiunge il listener per le modifiche ai testi
     * @param myDocumentListener
     */
    void removeDocumentListenerForNome(XmlDocumentChangeListener myDocumentListener) {
        this.jTFNome.getDocument().removeDocumentListener(myDocumentListener);
    }

    /**
     * Aggiunge il listener per le modifiche ai testi
     * @param myDocumentListener
     */
    void removeDocumentListenerForSegno(XmlDocumentChangeListener myDocumentListener) {
        this.jTFSegno.getDocument().removeDocumentListener(myDocumentListener);
    }
	

	/**
	 * This method is called from within the constructor to initialize the form.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
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
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JTextField jTFNome;
	private javax.swing.JTextField jTFSegno;
	// End of variables declaration//GEN-END:variables

}
