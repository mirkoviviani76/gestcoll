/*
 * Modifiche:
 * -
 */

package gui.extraPanels;

import gestXml.LinksXml;
import gestXml.data.Link;
import gui.datamodels.GenericListModel;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;

import main.GestLog;

/**
 *
 * TODO migliorare la visualizzazione dei link, introducendo magari una visualizzazione ad albero
 */
public class LinksPanel extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;
	private int lastSearchedIndex;
	private String lastSearchedText;

	/** Creates new form LinksPanel */
	public LinksPanel() {
		initComponents();
		this.lastSearchedIndex = 0;
		this.lastSearchedText = "";

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	@SuppressWarnings({ "serial" })
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		jPanel1 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jLLinks = new javax.swing.JList();
		jToolBar1 = new javax.swing.JToolBar();
		jBAggiungi = new javax.swing.JButton();
		jBCerca = new javax.swing.JButton();
		jTextField1 = new javax.swing.JTextField();

		setLayout(new java.awt.GridBagLayout());

		jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1,
				javax.swing.BoxLayout.LINE_AXIS));

		jLLinks.setFont(new java.awt.Font("Dialog", 0, 11));
		jLLinks.setModel(new javax.swing.AbstractListModel() {
			String[] strings = { "a" };

			@Override
			public int getSize() {
				return strings.length;
			}

			@Override
			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		jLLinks.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jLLinks.setCellRenderer(new gui.datamodels.GenericCellRenderer<Link>());
		jLLinks.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jLLinksMouseClicked(evt);
			}
		});
		jScrollPane1.setViewportView(jLLinks);

		jPanel1.add(jScrollPane1);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 386;
		gridBagConstraints.ipady = 203;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		add(jPanel1, gridBagConstraints);

		jToolBar1.setFloatable(false);
		jToolBar1.setRollover(true);
		jToolBar1.setMaximumSize(new java.awt.Dimension(63, 25));
		jToolBar1.setMinimumSize(new java.awt.Dimension(63, 25));

		jBAggiungi.setText("Aggiungi");
		jBAggiungi.setFocusable(false);
		jBAggiungi.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jBAggiungi.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jBAggiungi.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jBAggiungiMouseClicked(evt);
			}
		});
		jToolBar1.add(jBAggiungi);

		jBCerca.setText("Cerca");
		jBCerca.setFocusable(false);
		jBCerca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jBCerca.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jBCerca.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jBCercaMouseClicked(evt);
			}
		});
		jToolBar1.add(jBCerca);
		jToolBar1.add(jTextField1);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = 347;
		gridBagConstraints.ipady = 10;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		add(jToolBar1, gridBagConstraints);
	}// </editor-fold>//GEN-END:initComponents

	private void jLLinksMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLLinksMouseClicked
		if (evt.getClickCount() == 2) {
			try {
				Link c = (Link) this.jLLinks.getSelectedValue();
				Desktop.getDesktop().browse(c.url.toURI());
			} catch (IOException ex) {
				GestLog.Error(LinksPanel.class, ex);
			} catch (URISyntaxException ex) {
				GestLog.Error(LinksPanel.class, ex);
			}
		}
	}// GEN-LAST:event_jLLinksMouseClicked

	private void jBAggiungiMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jBAggiungiMouseClicked
		// TODO aggiungere gestione aggiungi Link
	}// GEN-LAST:event_jBAggiungiMouseClicked

	private void jBCercaMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jBCercaMouseClicked
		@SuppressWarnings("unchecked")
		GenericListModel<Link> lm = (GenericListModel<Link>) (this.jLLinks
				.getModel());
		String testoDaCercare = this.jTextField1.getText();

		/* cerca dall'inizio in caso di una nuova ricerca */
		if (!testoDaCercare.equals(this.lastSearchedText)) {
			this.lastSearchedIndex = 0;
			this.lastSearchedText = testoDaCercare;
		}
		int risultato = lm.search(testoDaCercare, this.lastSearchedIndex);
		// continua a cercare dall'indice trovato
		this.lastSearchedIndex = risultato + 1;

		if (risultato != -1) {
			this.jLLinks.setSelectedIndex(risultato);
			this.jLLinks.ensureIndexIsVisible(risultato);
		}
	}// GEN-LAST:event_jBCercaMouseClicked
		// Variables declaration - do not modify//GEN-BEGIN:variables

	private javax.swing.JButton jBAggiungi;
	private javax.swing.JButton jBCerca;
	private javax.swing.JList jLLinks;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JToolBar jToolBar1;

	// End of variables declaration//GEN-END:variables

	/**
     * 
     */
	public void loadData() {
		LinksXml links = new LinksXml();
		this.jLLinks.setModel(new GenericListModel<Link>(links.getLinks()));
	}
}
