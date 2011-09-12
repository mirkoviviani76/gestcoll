/*
 * Modifiche:
 * -
 */

package gui.extraPanels.contatti;

import exceptions.XmlException;
import gestXml.ContattiXml;
import gestXml.data.Contatto;
import gui.MainFrame;
import gui.datamodels.GenericListModel;

import java.awt.Desktop;
import java.awt.Font;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;

import main.Common;
import main.GestLog;
import main.History;
import main.Message;

/**
 * Gestione pannello contatti
 * @author intecs
 *
 */
@SuppressWarnings("serial")
public class ContattiPanel extends javax.swing.JPanel {
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jBAggiungi;
	private javax.swing.JButton jBCerca;

	private javax.swing.JList jLContatti;

	private javax.swing.JPanel jPanel1;

	private javax.swing.JScrollPane jScrollPane1;

	private javax.swing.JTextField jTextField1;

	private javax.swing.JToolBar jToolBar1;

	private int lastSearchedIndex;
	private String lastSearchedText;
	
	private ContattiXml contatti;

	/** Creates new form ContattiPanel 
	 * @throws XmlException */
	public ContattiPanel() throws XmlException {
		initComponents();
		this.jLContatti.setModel(new GenericListModel<Contatto>());
		this.lastSearchedIndex = 0;
		this.lastSearchedText = "";
		contatti = new ContattiXml(); 
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jLContatti = new javax.swing.JList();
		jToolBar1 = new javax.swing.JToolBar();
		jBAggiungi = new javax.swing.JButton();
		jBCerca = new javax.swing.JButton();
		jTextField1 = new javax.swing.JTextField();

		jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1,
				javax.swing.BoxLayout.LINE_AXIS));
		jLContatti.setFont(new Font("Tahoma", Font.PLAIN, 11));
		jLContatti
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jLContatti
				.setCellRenderer(new gui.datamodels.GenericCellRenderer<Contatto>());
		jLContatti.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jLContattiMouseClicked(evt);
			}
		});
		jScrollPane1.setViewportView(jLContatti);

		jPanel1.add(jScrollPane1);

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

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE,
						353, Short.MAX_VALUE)
				.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
						353, Short.MAX_VALUE));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jToolBar1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										35,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jPanel1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										162, Short.MAX_VALUE)));
	}

	/**
	 * click sul pulsante aggiungi contatto
	 * 
	 * @param evt
	 */
	private void jBAggiungiMouseClicked(java.awt.event.MouseEvent evt) {
		/* apre il dialog */
		NuovoContattoDialog dialog = new NuovoContattoDialog(null, true);
		dialog.setVisible(true);
		// valuta ritorno con tasto "ok/modifica" premuto
		if (dialog.getReturnStatus() == NuovoContattoDialog.RET_OK) {
			// ottiene i nuovi dati della nota dal form
			Contatto nuovo = dialog.getData();
			this.contatti.add(nuovo);
			// salva il file
			try {
				this.contatti.writeXml(this.contatti.getJaxbObject(), "XmlData.Contatti",
						Common.getCommon().getContattiXml());
				// carica i nuovi valori
				this.jLContatti.setModel(new GenericListModel<Contatto>(contatti.getContatti()));
			} catch (XmlException e) {
				GestLog.Error(this.getClass(), e);
			}
			/* logga in vario modo */
			History.addEvent(History.MODIFY, Common.getCommon().getContattiXml());
			String msg = "MODIFICATO con successo: " + Common.getCommon().getContattiXml();
			MainFrame.setMessage(new Message(msg, Level.INFO));
		}
		
		
	}

	private void jBCercaMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jBCercaMouseClicked
		@SuppressWarnings("unchecked")
		GenericListModel<Contatto> lm = (GenericListModel<Contatto>) (this.jLContatti
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
			this.jLContatti.setSelectedIndex(risultato);
			this.jLContatti.ensureIndexIsVisible(risultato);
		}
	}// GEN-LAST:event_jBCercaMouseClicked

	/**
	 * click sul contatto
	 * 
	 * @param evt
	 */
	private void jLContattiMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLContattiMouseClicked
		if (evt.getClickCount() == 2) {
			try {
				Contatto c = (Contatto) this.jLContatti.getSelectedValue();
				Desktop.getDesktop().mail(new URI("mailto:" + c.email));
				System.out.println("SELEZIONATO per email+ " + c);
			} catch (IOException ex) {
				GestLog.Error(ContattiPanel.class, ex);
			} catch (URISyntaxException ex) {
				GestLog.Error(ContattiPanel.class, ex);
			}
		}
	}

	// End of variables declaration//GEN-END:variables

	/**
     * carica i dati 
     */
	public void loadData() {
		this.jLContatti.setModel(new GenericListModel<Contatto>(contatti.getContatti()));
	}

}
