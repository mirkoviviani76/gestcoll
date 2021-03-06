/*
 * Modifiche:
 * -
 */

package gui.extraPanels.vassoi;

import java.awt.Color;

/**
 * Implementa la singola cella del vassoio (casella)
 * 
 * @author furetto76
 */
public class VassoioCell extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JScrollPane jScrollPane1;

	private javax.swing.JTextPane jTextPane1;
	// End of variables declaration//GEN-END:variables

	/** Creates new form VassoioCell */
	public VassoioCell() {
		initComponents();
	}

	/**
	 * 
	 * @param c
	 */
	public void evidenzia(Color c) {
		this.jTextPane1.setBackground(c);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		jScrollPane1 = new javax.swing.JScrollPane();
		jTextPane1 = new javax.swing.JTextPane();

		setLayout(new java.awt.GridBagLayout());

		jTextPane1.setContentType("text/html"); //$NON-NLS-1$
		jScrollPane1.setViewportView(jTextPane1);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipady = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		add(jScrollPane1, gridBagConstraints);
	}// </editor-fold>//GEN-END:initComponents
	/**
	 * scrive il testo html
	 * 
	 * @param s
	 */
	public void setText(String s) {
		this.jTextPane1.setText(s);
	}

}
