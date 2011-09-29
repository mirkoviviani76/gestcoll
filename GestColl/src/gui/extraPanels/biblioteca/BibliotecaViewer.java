/*
 * Modifiche:
 * -
 */

package gui.extraPanels.biblioteca;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import main.Common;
import main.GenericUtil;
import main.GestLog;

/**
 *
 * 
 */
@SuppressWarnings("serial")
public class BibliotecaViewer extends javax.swing.JPanel {

	private javax.swing.JScrollPane jScrollPane1;

	private javax.swing.JTextPane jTPDati;

	// End of variables declaration//GEN-END:variables

	/** Creates new form BibliotecaViewer */
	public BibliotecaViewer() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		jScrollPane1 = new javax.swing.JScrollPane();
		jTPDati = new javax.swing.JTextPane();

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

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;

		jTPDati.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				followLink(e);
			}
		});
		
	}

	/**
	 * 
	 * @param dati
	 * @param url
	 */
	public void setDati(String dati, String url) {
		this.jTPDati.setText(dati);
	}
	
	
	/**
	 * segue il link cliccato sul text panel
	 * 
	 * @param event l'evento
	 */
	protected void followLink(HyperlinkEvent event) {
		if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			try {
				GenericUtil.openBrowser(event.getURL().toURI());
			} catch (URISyntaxException e) {
				GestLog.Error(this.getClass(), e);
			} catch (IOException e) {
				GestLog.Error(this.getClass(), e);
			}
		}
	}
	
}
