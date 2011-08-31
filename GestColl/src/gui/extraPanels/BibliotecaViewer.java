/*
 * Modifiche:
 * -
 */

package gui.extraPanels;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import main.Common;
import main.GestLog;

/**
 *
 * 
 */
@SuppressWarnings("serial")
public class BibliotecaViewer extends javax.swing.JPanel {

	private File filename;

	/** Creates new form BibliotecaViewer */
	public BibliotecaViewer() {
		initComponents();
		filename = null;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		jScrollPane1 = new javax.swing.JScrollPane();
		jTPDati = new javax.swing.JTextPane();
		jBApriCartella = new javax.swing.JButton();

		setLayout(new java.awt.GridBagLayout());

		jTPDati.setContentType("text/html");
		jTPDati.setEditable(false);
		jScrollPane1.setViewportView(jTPDati);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		add(jScrollPane1, gridBagConstraints);

		jBApriCartella.setText("Apri cartella");
		jBApriCartella.setEnabled(false);
		jBApriCartella.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jBApriCartellaMouseClicked(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		add(jBApriCartella, gridBagConstraints);
	}// </editor-fold>//GEN-END:initComponents

	private void jBApriCartellaMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jBApriCartellaMouseClicked
		// apre la cartella
		try {
			File cartella = new File(filename.getCanonicalFile().getParent());
			// TODO sembra che su xp non funzioni.... provare su linux
			Desktop.getDesktop().open(cartella);
		} catch (IOException ex) {
			GestLog.Error(BibliotecaViewer.class, ex);
		}
	}// GEN-LAST:event_jBApriCartellaMouseClicked

	/**
	 * 
	 * @param dati
	 * @param url
	 */
	public void setDati(String dati, String url) {
		this.jTPDati.setText(dati);
		// compone l'url completo
		if (url != null && !url.equals("")) {
			filename = new File(Common.getCommon().getBibliotecaDir() + "/" + url);
		} else {
			filename = null;
		}
		enableApriCartella();
	}

	private void enableApriCartella() {
		// se esiste l'url, abilita il pulsante
		boolean flag = false;
		if (filename != null) {
			if (filename.exists()) {
				flag = true;
			}
		}
		this.jBApriCartella.setEnabled(flag);
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jBApriCartella;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextPane jTPDati;
	// End of variables declaration//GEN-END:variables
}
