/*
 * Modifiche:
 * -
 */

package gui.moneta;

import gestXml.MonetaXml;
import gui.datamodels.GenericListModel;
import gui.datamodels.XmlDocumentChangeListener;
import gui.datamodels.CellRenderer.LegendaCellRenderer;
import gui.moneta.forms.DescrizioneForm;
import gui.moneta.forms.LegendaForm;

import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import main.Common;
import Resources.i18n.Messages;
import XmlData.Moneta.Legenda;

/**
 *
 * 
 */
@SuppressWarnings("serial")
public class MonetaDescrizione extends javax.swing.JPanel {

	private boolean isEditingMode;
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButton1;
	private javax.swing.JLabel jImmagine;
	private javax.swing.JLabel jLabel2;

	private javax.swing.JLabel jLabel3;

	private javax.swing.JList jLLegende;

	private javax.swing.JScrollPane jScrollPane1;

	private javax.swing.JScrollPane jScrollPane2;

	private javax.swing.JTextArea jTADescrizione;

	private Common.Lato lato;

	MonetaXml mng;

	private String origImgFilename;

	/** costruttore fittizio per gui creator */
	public MonetaDescrizione() {
		initComponents();
		origImgFilename = ""; //$NON-NLS-1$
		lato = null;
		mng = null;
	}

	/**
	 * Creates new form MonetaDescrizione
	 * 
	 * @param _lato
	 */
	public MonetaDescrizione(Common.Lato _lato) {
		initComponents();
		origImgFilename = ""; //$NON-NLS-1$
		lato = _lato;
		isEditingMode = false;
	}
	/**
	 * Aggiunge il listener per le modifiche ai testi
	 * 
	 * @param myDocumentListener
	 */
	void addDocumentListener(XmlDocumentChangeListener myDocumentListener) {
		this.jTADescrizione.getDocument().addDocumentListener(
				myDocumentListener);
	}
	/**
	 * 
	 * @param mng
	 */
	public void fillData(MonetaXml mng) {
		this.mng = mng;
		this.setDescrizione(mng.getDescrizione(this.lato));
		this.setLegenda(mng.getLegende(this.lato));
		this.setImg(mng.getFileImmagine(this.lato));
	}
	/**
	 * This method is called from within the constructor to initialize the form.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jImmagine = new javax.swing.JLabel();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTADescrizione = new javax.swing.JTextArea();
		jScrollPane1 = new javax.swing.JScrollPane();
		jLLegende = new javax.swing.JList();
		jButton1 = new javax.swing.JButton();

		jLabel2.setText(Messages.getString("Generic.19")); // NOI18N //$NON-NLS-1$

		jLabel3.setText(Messages.getString("Generic.22")); // NOI18N //$NON-NLS-1$

		jImmagine.setBackground(new java.awt.Color(255, 255, 255));
		jImmagine.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jImmagine.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jImmagineMouseClicked(evt);
			}
		});

		jTADescrizione.setColumns(20);
		jTADescrizione.setEditable(false);
		jTADescrizione.setLineWrap(true);
		jTADescrizione.setRows(5);
		jTADescrizione.setWrapStyleWord(true);
		jTADescrizione.setMaximumSize(new java.awt.Dimension(162, 92));
		jTADescrizione.setMinimumSize(new java.awt.Dimension(162, 92));
		jTADescrizione.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jTADescrizioneMouseClicked(evt);
			}
		});
		jScrollPane2.setViewportView(jTADescrizione);

		jLLegende.setFont(new java.awt.Font("Dialog", 0, 11)); //$NON-NLS-1$
		jLLegende
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jLLegende.setCellRenderer(new LegendaCellRenderer());
		jLLegende.setMaximumSize(new java.awt.Dimension(162, 92));
		jLLegende.setMinimumSize(new java.awt.Dimension(162, 92));
		jLLegende.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jLLegendeMouseClicked(evt);
			}
		});
		jScrollPane1.setViewportView(jLLegende);

		jButton1.setText("+"); //$NON-NLS-1$
		jButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));
		jButton1.setMaximumSize(new java.awt.Dimension(25, 25));
		jButton1.setMinimumSize(new java.awt.Dimension(25, 25));
		jButton1.setPreferredSize(new java.awt.Dimension(25, 25));

		jButton1.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jBAddLegendaActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(12, 12, 12)
								.addComponent(jLabel2,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										303, Short.MAX_VALUE)
								.addContainerGap(14, Short.MAX_VALUE))
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(
														jScrollPane2,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														309, Short.MAX_VALUE)
												.addComponent(
														jScrollPane1,
														javax.swing.GroupLayout.Alignment.CENTER,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														309, Short.MAX_VALUE)
												.addComponent(
														jImmagine,
														javax.swing.GroupLayout.Alignment.CENTER,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														150,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														jButton1,
														javax.swing.GroupLayout.Alignment.CENTER,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap())
				.addGroup(
						layout.createSequentialGroup()
								.addGap(12, 12, 12)
								.addComponent(jLabel3,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										66,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(251, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jLabel2)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane2,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(2, 2, 2)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jLabel3)
												.addComponent(
														jButton1,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jScrollPane1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										67,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jImmagine,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										150,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
	}// </editor-fold>//GEN-END:initComponents
	private void jBAddLegendaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBAddLegendaActionPerformed
		LegendaForm zf = new LegendaForm(null, true);
		zf.setVisible(true);
		// valuta ritorno con tasto "ok/modifica" premuto
		if (zf.getReturnStatus() == LegendaForm.RET_OK) {
			// ottiene i nuovi dati della nota dal form
			Legenda nuovaLegenda = zf.getData();
			mng.getLegende(lato).add(nuovaLegenda);
			this.jLLegende.setModel(new GenericListModel<Legenda>(mng
					.getLegende(lato)));
		}

	}// GEN-LAST:event_jBAddLegendaActionPerformed
	/**
	 * gestisce il clic sull'immagine. Apre una finestra con l'immagine
	 * ingrandita
	 * 
	 * @param evt
	 */
	private void jImmagineMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jImmagineMouseClicked
		if (!this.origImgFilename.equals("")) { //$NON-NLS-1$
			// ingrandisce l'immagine in un dialogo esterno
			MonetaImgViewer miv = new MonetaImgViewer(
					(JFrame) SwingUtilities.getRoot(this), false);
			miv.setImg(this.origImgFilename);
			miv.setVisible(true);
		}
	}// GEN-LAST:event_jImmagineMouseClicked
	private void jLLegendeMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLLegendeMouseClicked
		if (this.isEditingMode) {
			/* apre il dialog */
			Legenda legenda = (Legenda) (jLLegende.getSelectedValue());
			// attiva la finestra di modifica (deve essere modale)
			LegendaForm nf = new LegendaForm(null, true);
			nf.setEditable(isEditingMode);
			nf.setData(legenda);
			nf.setVisible(true);

			// valuta ritorno con tasto "ok/modifica" premuto
			if (nf.getReturnStatus() == LegendaForm.RET_OK) {
				Legenda nuovaLegenda = null;
				//ottiene i nuovi dati del libro
				nuovaLegenda = nf.getData();
				// salva nel DOM
				mng.modifyLegenda(this.lato, legenda, nuovaLegenda);
				// carica i nuovi valori
				this.jLLegende.setListData(mng.getLegende(lato).toArray());

			}
		} else {
			/* mostra semplicemente la legenda */
			Legenda l = (Legenda) this.jLLegende.getSelectedValue();
			if (l != null) {
				LegendaForm nf = new LegendaForm(null, true);
				nf.setEditable(isEditingMode);
				nf.setData(l);
				nf.setVisible(true);
			}
			l = null;
		}
	}// GEN-LAST:event_jLLegendeMouseClicked
	private void jTADescrizioneMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jTADescrizioneMouseClicked
		if (this.isEditingMode) {
			/* apre il dialog */
			DescrizioneForm df = new DescrizioneForm(null, true);
			df.setData(this.jTADescrizione.getText());
			df.setImg(this.origImgFilename);
			df.setVisible(true);
			// valuta ritorno con tasto "ok/modifica" premuto
			if (df.getReturnStatus() == DescrizioneForm.RET_OK) {
				this.jTADescrizione.setText(df.getData());
			}
		}
	}// GEN-LAST:event_jTADescrizioneMouseClicked

	// End of variables declaration//GEN-END:variables

	void removeDocumentListener(XmlDocumentChangeListener myDocumentListener) {
		this.jTADescrizione.getDocument().removeDocumentListener(
				myDocumentListener);
	}

	/**
	 * carica la descrizione nella text area
	 * 
	 * @param text
	 */
	public void setDescrizione(String text) {
		if (text != null) {
			this.jTADescrizione.setText(text);
		}
	}

	/**
	 * 
	 * @param flag
	 */
	public void setEditable(boolean flag) {
		// NB jTADescrizione vene editato tramite DescrizioneForm
		// this.jTADescrizione.setEditable(flag);
		this.jButton1.setVisible(flag);
		this.isEditingMode = flag;
	}

	/**
	 * carica un thumbnail dell'immagine
	 * 
	 * @param filename
	 */
	public void setImg(String filename) {
		this.origImgFilename = filename;
		this.jImmagine.setIcon(null);
		/* i null servono per accelerare il garbage collection (verificare) */
		Image imgD = null;
		ImageIcon imgD_little = null;
		if (filename != null) {
			imgD = new ImageIcon(filename).getImage();
			// si da priorita' alla velocita'
			imgD_little = new ImageIcon(imgD.getScaledInstance(
					this.jImmagine.getWidth(), this.jImmagine.getHeight(),
					Image.SCALE_FAST));
			this.jImmagine.setIcon(imgD_little);
		}
	}

	/**
	 * Sistema il modello caricando i dati
	 * 
	 * @param list
	 */
	public void setLegenda(List<Legenda> list) {
		this.jLLegende.setModel(new GenericListModel<Legenda>(list));
	}

}
