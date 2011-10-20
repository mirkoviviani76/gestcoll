/*
 * Modifiche:
 * -
 */
package gui.moneta;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.ProgressMonitor;
import javax.xml.transform.TransformerException;

import main.Common;
import main.GestLog;
import main.History;
import main.Message;
import main.Progress;

import org.xml.sax.SAXException;

import works.CollectionWorker;
import works.MoneteXml2Etichette;
import works.MoneteXml2Html;
import works.MoneteXml2QR;
import works.MoneteXml2Tex;
import works.XelatexPdfCreator;
import Resources.i18n.Messages;
import exceptions.InternalGestCollError;
import exceptions.XmlException;
import gestXml.CollezioneXml;
import gestXml.ContenitoriXml;
import gestXml.MonetaXml;
import gestXml.data.Armadio;
import gestXml.data.Contenitore;
import gestXml.data.Vassoio;
import gui.MainFrame;
import gui.datamodels.MonetaListModel;
import gui.datamodels.CellRenderer.MonetaXmlCellRenderer;
import gui.extraPanels.vassoi.VassoioPanel;
import gui.moneta.forms.AddMonetaForm;

/**
 * 
 */
public final class MonetePanel extends javax.swing.JPanel implements Observer,
		ActionListener {

	private final static String ORDER_BY_ID = Messages.getString("MonetePanel.1"); //$NON-NLS-1$
	private final static String ORDER_BY_PAESE = Messages.getString("MonetePanel.2"); //$NON-NLS-1$
	private final static String ORDER_BY_REVISIONE = Messages.getString("MonetePanel.3"); //$NON-NLS-1$
	private static final long serialVersionUID = 1L;
	private javax.swing.JButton jBAdd;
	private javax.swing.JButton jBCerca;
	private javax.swing.JButton jBSalva;
	private javax.swing.JButton jBToClipboard;
	private javax.swing.JButton jButtonQR;
	private javax.swing.JButton jButtonTex2Pdf;
	private javax.swing.JButton jButtonVerify;
	private javax.swing.JButton jButtonWiki;
	private javax.swing.JButton jButtonXml2Html;
	private javax.swing.JButton jButtonXml2Tex;
	private javax.swing.JList jListMonete;
	private javax.swing.JPopupMenu jPopupMenu1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JToolBar.Separator jSeparator1;
	private javax.swing.JToolBar.Separator jSeparator2;
	private javax.swing.JToolBar.Separator jSeparator3;
	private javax.swing.JToolBar.Separator jSeparator4;
	private javax.swing.JSplitPane jSplitPane1;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JToggleButton jTBEdit;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JToolBar jToolBar1;
	private int lastSearchedIndex;
	private String lastSearchedText;
	private gui.moneta.MonetaViewer monetaViewer1;
	private ProgressMonitor pm;
	private ButtonGroup popupGroup1;
	private JRadioButtonMenuItem popupRadio[];
	private MoneteXml2QR qrc;
	private Thread thread;
	private MoneteXml2Etichette xml2et;
	private MoneteXml2Html xml2html;
	private MoneteXml2Tex xml2tex;
	private XelatexPdfCreator xpc;
	// private Verify vrf;
	
	/**
	 * Creates new form MonetePanel
	 * 
	 * @throws XmlException
	 */
	public MonetePanel() throws XmlException {
		initComponents();

		/* aggiunge il radio group al popup menu (l'ide non lo fa) */
		String popupRadioItems[] = { MonetePanel.ORDER_BY_ID,
				MonetePanel.ORDER_BY_PAESE, MonetePanel.ORDER_BY_REVISIONE };
		popupRadio = new JRadioButtonMenuItem[popupRadioItems.length];
		popupGroup1 = new ButtonGroup();

		/* cicla su tutti i radio buttons previsti */
		for (int count = 0; count < popupRadio.length; count++) {
			// crea il radio button
			popupRadio[count] = new JRadioButtonMenuItem(popupRadioItems[count]);
			// aggiunge il radio button al popup
			this.jPopupMenu1.add(popupRadio[count]);
			// aggiunge il radio button al gruppo
			popupGroup1.add(popupRadio[count]);
			// aggiunge il listener
			popupRadio[count]
					.addActionListener(new java.awt.event.ActionListener() {

						@Override
						public void actionPerformed(ActionEvent evt) {
							try {
								jPopupMenuRadioButtonsActionEventListener(evt);
							} catch (XmlException e) {
								GestLog.Error(this.getClass(), e);
							}
						}
					});
		}
		this.popupRadio[0].setSelected(true);
		// nascondel il pannello dati finche' non si seleziona qualcosa
		this.monetaViewer1.setVisible(false);
		this.addActionListener();
		this.lastSearchedIndex = 0;
		this.lastSearchedText = ""; //$NON-NLS-1$
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == jButtonXml2Tex) {
			ArrayList<CollectionWorker> works = new ArrayList<CollectionWorker>();
			works.add(xml2tex);
			works.add(xml2et);
			this.runInThread(works,
					new File(Common.getCommon().getLatexDir()), null);
		} else if (ae.getSource() == jButtonWiki) {
			//mostra un messaggio
			JOptionPane.showMessageDialog(null, Messages.getString("MonetePanel.5"), Common.APPNAME, //$NON-NLS-1$
					JOptionPane.INFORMATION_MESSAGE);
		} else if (ae.getSource() == jBAdd) {
			try {
				this.addMoneta();
			} catch (XmlException e) {
				GestLog.Error(this.getClass(), e);
			} catch (InternalGestCollError e) {
				GestLog.Error(this.getClass(), e);
			}
		} else if (ae.getSource() == jButtonTex2Pdf) {
			this.runInThread(xpc, new File(Common.getCommon().getLatexDir()),
					new File(Common.getCommon().getLatexDir()), null);
		} else if (ae.getSource() == jButtonXml2Html) {
			this.runInThread(xml2html, 
					new File(Common.getCommon().getHtmlDir()), null);
		} else if (ae.getSource() == jButtonVerify) {
			// this.runInThread(vrf, new File(Common.MONETE_DIR), new
			// File(Common.HTML_DIR), null);
		} else if (ae.getSource() == jButtonQR) {
			this.runInThread(qrc, 
					new File(Common.getCommon().getQrDir()), null);
		} else if (ae.getSource() == jTBEdit) {
			this.monetaViewer1.setEditable(this.jTBEdit.isSelected());
		} else if (ae.getSource() == jBToClipboard) {
			MonetaXml mng = (MonetaXml) this.jListMonete.getSelectedValue();
			String testo = mng.toFullText();
			StringSelection stringSelection = new StringSelection(testo);
			// copia negli appunti
			Clipboard clipboard = Toolkit.getDefaultToolkit()
					.getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		} else if (ae.getSource() == jBSalva) {
			try {
				this.monetaViewer1.salvaDati();
			} catch (XmlException e) {
				GestLog.Error(this.getClass(), e);
			} catch (IOException e) {
				GestLog.Error(this.getClass(), e);
			}
			// disabilita il pulsante edit
			this.jTBEdit.setSelected(false);
			// disabilita l'editing della moneta
			this.monetaViewer1.setEditable(false);
		} else if (ae.getSource() == jBCerca) {
			MonetaListModel lm = (MonetaListModel) (this.jListMonete.getModel());
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
				this.jListMonete.setSelectedIndex(risultato);
				this.jListMonete.ensureIndexIsVisible(risultato);
			}
		} else {
			// azione non gestita
			GestLog.Error(MonetePanel.class, "ActionListener", Messages.getString("Generic.11") //$NON-NLS-1$ //$NON-NLS-2$
					+ ae.getActionCommand());
		}
	}

	private void addActionListener() {
		jButtonXml2Tex.addActionListener(this);
		jButtonTex2Pdf.addActionListener(this);
		jButtonXml2Html.addActionListener(this);
		jButtonVerify.addActionListener(this);
		jButtonQR.addActionListener(this);
		jButtonWiki.addActionListener(this);
		jTBEdit.addActionListener(this);
		jBSalva.addActionListener(this);
		jBAdd.addActionListener(this);
		jBCerca.addActionListener(this);
		jBToClipboard.addActionListener(this);
		/* aggiunge il mouse listener per posizione */
		this.monetaViewer1.jTFPosizione
				.addMouseListener(new java.awt.event.MouseAdapter() {

					@Override
					/**
					 * Risponde al doppio clic del mouse sulla casella
					 */
					public void mouseClicked(java.awt.event.MouseEvent evt) {
						if (evt.getClickCount() == 2) {
							String valore = monetaViewer1.jTFPosizione
									.getText();
							String[] posizione = valore.split(" "); //$NON-NLS-1$
							String cont = posizione[0];
							String vass = posizione[1];
							String r = posizione[2];
							String c = posizione[3];
							// rende visibile il tab vassoi
							jTabbedPane1.setSelectedIndex(1);
							//
							JTabbedPane vassoi = (JTabbedPane) (jTabbedPane1
									.getSelectedComponent());
							/* cerca la tab che contiene la moneta */
							// costruisce il nome: lo spazio distingue 6/1 da
							// 6/10
							String nomeVassoio = String.format("%s/%s", cont, //$NON-NLS-1$
									vass);
							vassoi.setSelectedIndex(vassoi
									.indexOfTab(nomeVassoio));
							VassoioPanel vp = (VassoioPanel) (vassoi
									.getSelectedComponent());
							vp.select(Integer.parseInt(r), Integer.parseInt(c));

						}
					}
				});

	}

	/**
	 * Aggiunge una moneta, creando una directory e un file xml pronto per
	 * l'editing 
	 * @throws XmlException
	 * @throws InternalGestCollError 
	 */
	private void addMoneta() throws XmlException, InternalGestCollError {
		AddMonetaForm am = new AddMonetaForm(null, true);
		am.setVisible(true);
		if (am.getReturnStatus() == AddMonetaForm.RET_OK) {
			String id = am.getId();
			// copia file template di istanza e sistema l'attributo e le immagini
			CollezioneXml.getCollezione().addMoneta(am.getAnno(), id);
			// ricarica la lista
			this.jListMonete
			.setModel(new gui.datamodels.MonetaListModel(
					MonetaXml.Ordering.BY_ID));
			// messaggio di conferma
			History.addEvent(History.ADD, id);
			String msg = Messages.getString("MonetePanel.27") + id; //$NON-NLS-1$
			MainFrame.setMessage(new Message(msg, Level.INFO));
		}
	}
	
	
	/**
	 * @throws XmlException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public void disegnaVassoi() throws XmlException {
		ContenitoriXml disp = new ContenitoriXml();
		JTabbedPane vassoiContainer = new JTabbedPane();
		this.jTabbedPane1.add(Messages.getString("MonetePanel.31"), vassoiContainer); //$NON-NLS-1$
		HashMap<String, String> posizioni;
		try {
			posizioni = disp.getMapPosizioniId();
			Armadio a = disp.getArmadio("SRI"); //$NON-NLS-1$
			List<Integer> contenitori = new ArrayList<Integer>(
					a.armadio.keySet());
			/* ordina i contenitori */
			Collections.sort(contenitori);
			/* cicla su tutti i contenitori */
			for (int cont : contenitori) {
				Contenitore c = a.armadio.get(cont);
				/* ordina i vassoi */
				List<Integer> vassoi = new ArrayList<Integer>(
						c.contenitore.keySet());
				Collections.sort(vassoi);
				/* cicla su tutti i vassoi */
				for (int vass : vassoi) {
					Vassoio v = c.contenitore.get(vass);
					// disegna il vassoio
					VassoioPanel curVassPanel = new VassoioPanel(
							this.jListMonete);
					// estrae i dati per il vassoio corrente
					String[][] s = this.displayVassoio(a, c, v, posizioni);
					// riempie la tabella
					curVassPanel.riempieDati(v.righe, v.colonne, s);
					// aggiunge la tab
					String nomeTab = String.format("%d/%d", cont, vass); //$NON-NLS-1$
					vassoiContainer.add(nomeTab, curVassPanel);
				}
			}
		} catch (FileNotFoundException e) {
			GestLog.Error(this.getClass(), e);
		}

	}

	/**
	 * ottiene il contenuto di un vassoio
	 * 
	 * @param a l'armadio
	 * @param c il contenitore
	 * @param v il vassoio
	 * @param posizioni le posizioni
	 * @return la rappresentazione tabellare del vassoio 
	 */
	public String[][] displayVassoio(Armadio a, Contenitore c, Vassoio v,
			HashMap<String, String> posizioni) {
		String[][] matrice = new String[v.righe][v.colonne];
		for (int i = 1; i <= v.righe; i++) {
			for (int j = 1; j <= v.colonne; j++) {
				// costruisce la presunta chiave
				String pos = a.nome + "-" + c.id + "-" + v.id + "-" + i + "-" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
						+ j;
				// le righe sono invertite
				if (posizioni.containsKey(pos)) {
					// se la chiave esiste, scrive il valore
					matrice[v.righe - i][j - 1] = posizioni.get(pos);
				} else {
					// se la chiave non esite stampa un po' di puntini
					matrice[v.righe - i][j - 1] = ""; //$NON-NLS-1$
				}
			}
		}
		return matrice;

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * 
	 * @throws XmlException
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() throws XmlException {

		jPopupMenu1 = new javax.swing.JPopupMenu();
		jToolBar1 = new javax.swing.JToolBar();
		jButtonXml2Tex = new javax.swing.JButton();
		jButtonTex2Pdf = new javax.swing.JButton();
		jButtonXml2Html = new javax.swing.JButton();
		jButtonQR = new javax.swing.JButton();
		jButtonWiki = new javax.swing.JButton();
		jBToClipboard = new javax.swing.JButton();
		jSeparator3 = new javax.swing.JToolBar.Separator();
		jButtonVerify = new javax.swing.JButton();
		jSeparator1 = new javax.swing.JToolBar.Separator();
		jSeparator2 = new javax.swing.JToolBar.Separator();
		jTBEdit = new javax.swing.JToggleButton();
		jBSalva = new javax.swing.JButton();
		jSeparator4 = new javax.swing.JToolBar.Separator();
		jBAdd = new javax.swing.JButton();
		jBCerca = new javax.swing.JButton();
		jTextField1 = new javax.swing.JTextField();
		jSplitPane1 = new javax.swing.JSplitPane();
		jScrollPane1 = new javax.swing.JScrollPane();
		jListMonete = new javax.swing.JList();
		jTabbedPane1 = new javax.swing.JTabbedPane();
		jScrollPane2 = new javax.swing.JScrollPane();
		monetaViewer1 = new gui.moneta.MonetaViewer();

		jToolBar1.setFloatable(false);
		jToolBar1.setRollover(true);

		jButtonXml2Tex.setMnemonic('X');
		jButtonXml2Tex.setText(Messages.getString("MonetePanel.39")); //$NON-NLS-1$
		jButtonXml2Tex.setFocusable(false);
		jButtonXml2Tex
				.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButtonXml2Tex
				.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(jButtonXml2Tex);

		jButtonTex2Pdf.setMnemonic('P');
		jButtonTex2Pdf.setText(Messages.getString("MonetePanel.40")); //$NON-NLS-1$
		jButtonTex2Pdf.setFocusable(false);
		jButtonTex2Pdf
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		jButtonTex2Pdf
				.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(jButtonTex2Pdf);

		jButtonXml2Html.setMnemonic('H');
		jButtonXml2Html.setText(Messages.getString("MonetePanel.41")); //$NON-NLS-1$
		jButtonXml2Html.setFocusable(false);
		jButtonXml2Html
				.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButtonXml2Html
				.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(jButtonXml2Html);

		jButtonQR.setMnemonic('Q');
		jButtonQR.setText(Messages.getString("MonetePanel.42")); //$NON-NLS-1$
		jButtonQR.setFocusable(false);
		jButtonQR.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButtonQR.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(jButtonQR);

		jButtonWiki.setText(Messages.getString("MonetePanel.43")); //$NON-NLS-1$
		jButtonWiki.setFocusable(false);
		jButtonWiki
				.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButtonWiki.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(jButtonWiki);

		jBToClipboard.setText(Messages.getString("MonetePanel.44")); //$NON-NLS-1$
		jBToClipboard.setFocusable(false);
		jBToClipboard
				.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jBToClipboard
				.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(jBToClipboard);
		jToolBar1.add(jSeparator3);

		jButtonVerify.setMnemonic('V');
		jButtonVerify.setText(Messages.getString("MonetePanel.45")); //$NON-NLS-1$
		jButtonVerify
				.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jToolBar1.add(jButtonVerify);
		jToolBar1.add(jSeparator1);
		jToolBar1.add(jSeparator2);

		jTBEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/Resources/img/EditIcon.png"))); // NOI18N //$NON-NLS-1$
		jTBEdit.setMnemonic('E');
		jTBEdit.setText(Messages.getString("MonetePanel.47")); //$NON-NLS-1$
		jTBEdit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jTBEdit.setMaximumSize(new java.awt.Dimension(55, 25));
		jTBEdit.setMinimumSize(new java.awt.Dimension(55, 25));
		jTBEdit.setPreferredSize(new java.awt.Dimension(55, 25));
		jToolBar1.add(jTBEdit);

		jBSalva.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/Resources/img/SaveIcon.png"))); // NOI18N //$NON-NLS-1$
		jBSalva.setMnemonic('S');
		jBSalva.setText(Messages.getString("MonetePanel.49")); //$NON-NLS-1$
		jBSalva.setEnabled(true);
		jBSalva.setFocusable(false);
		jBSalva.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jBSalva.setMaximumSize(new java.awt.Dimension(55, 25));
		jBSalva.setMinimumSize(new java.awt.Dimension(55, 25));
		jBSalva.setPreferredSize(new java.awt.Dimension(55, 25));
		jToolBar1.add(jBSalva);
		jToolBar1.add(jSeparator4);

		jBAdd.setText(Messages.getString("Generic.0")); //$NON-NLS-1$
		jBAdd.setFocusable(false);
		jBAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jBAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(jBAdd);

		jBCerca.setText(Messages.getString("Generic.18")); //$NON-NLS-1$
		jBCerca.setFocusable(false);
		jBCerca.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jBCerca.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jToolBar1.add(jBCerca);
		jToolBar1.add(jTextField1);

		jSplitPane1.setAutoscrolls(true);
		jSplitPane1.setMinimumSize(new java.awt.Dimension(120, 26));

		jScrollPane1.setMinimumSize(new java.awt.Dimension(200, 24));

		jListMonete.setFont(new java.awt.Font("Tahoma", 0, 10)); //$NON-NLS-1$
		jListMonete.setModel(new gui.datamodels.MonetaListModel(
				MonetaXml.Ordering.BY_ID));
		jListMonete
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jListMonete
				.setCellRenderer(new MonetaXmlCellRenderer());
		jListMonete.setComponentPopupMenu(jPopupMenu1);
		jListMonete
				.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
					@Override
					public void valueChanged(
							javax.swing.event.ListSelectionEvent evt) {
						jListMoneteValueChanged(evt);
					}
				});
		jScrollPane1.setViewportView(jListMonete);

		jSplitPane1.setLeftComponent(jScrollPane1);

		jScrollPane2.setViewportView(monetaViewer1);

		jTabbedPane1.addTab(Messages.getString("Generic.24"), jScrollPane2); //$NON-NLS-1$

		jSplitPane1.setRightComponent(jTabbedPane1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jSplitPane1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										1383, Short.MAX_VALUE)
								.addGap(10, 10, 10))
				.addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE,
						1393, Short.MAX_VALUE));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jToolBar1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										35,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(jSplitPane1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										455, Short.MAX_VALUE)));
	}// </editor-fold>//GEN-END:initComponents

	private void jListMoneteValueChanged(
			javax.swing.event.ListSelectionEvent evt) {// GEN-FIRST:event_jListMoneteValueChanged
		this.monetaViewer1.removeDocumentListener();
		MonetaXml content = (MonetaXml) this.jListMonete.getSelectedValue();
		try {
			// mostra la tab delle monete
			this.jTabbedPane1.setSelectedIndex(0);
			// riempie i dati
			if (content != null) {
				this.monetaViewer1.fillData(content);
			}
			// visualizza il pannello
			this.monetaViewer1.setVisible(true);
		} catch (SAXException ex) {
			GestLog.Error(MonetePanel.class, ex);
		} catch (IOException ex) {
			GestLog.Error(MonetePanel.class, ex);
		} catch (TransformerException ex) {
			GestLog.Error(MonetePanel.class, ex);
		} catch (ParseException ex) {
			GestLog.Error(MonetePanel.class, ex);
		}
	}// GEN-LAST:event_jListMoneteValueChanged

	private void jPopupMenuRadioButtonsActionEventListener(ActionEvent evt)
			throws XmlException {
		/* cicla su tutti i radio buttons */
		for (int i = 0; i < this.popupRadio.length; i++) {
			/* cerca il radio button attivo che ha causato il cambio */
			if ((evt.getSource() == this.popupRadio[i])
					&& (this.popupRadio[i].isSelected())) {
				switch (i) {
				case 0:
					this.sortListMonete(MonetaXml.Ordering.BY_ID);
					break;
				case 1:
					this.sortListMonete(MonetaXml.Ordering.BY_PAESE);
					break;
				case 2:
					this.sortListMonete(MonetaXml.Ordering.BY_REVISION);
					break;
				}
			}
		}
	}

	// End of variables declaration//GEN-END:variables

	/**
	 * Esegue il CollectionWorker in un thread separato per non bloccare l'EDT.
	 * 
	 * @param arrayCw elenco di collection worker
	 * @param outDir la directory di output
	 * @param params i parametri
	 */
	public void runInThread(final ArrayList<CollectionWorker> arrayCw,
			final File outDir, final Object[] params) {
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (CollectionWorker cw : arrayCw) {
					try {
						cw.doWork(outDir, params);
					} catch (Exception ex) {
						GestLog.Error(MonetePanel.class, ex);
					}
				}
			}
		});
		thread.start();
	}

	/**
	 * Esegue il CollectionWorker in un thread separato per non bloccare l'EDT.
	 * 
	 * @param arrayCw elenco di collection worker
	 * @param outDir la directory di output
	 * @param params i parametri
	 */
	public void runInThread(final CollectionWorker cw,
			final File inDir, final File outDir, final Object[] params) {
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					cw.doWork(inDir, outDir, params);
				} catch (Exception ex) {
					GestLog.Error(MonetePanel.class, ex);
				}
			}
		});
		thread.start();
	}
	
	/**
	 * Esegue il CollectionWorker in un thread separato per non bloccare l'EDT.
	 * 
	 * @param cw
	 * @param startDir
	 * @param outDir
	 * @param params
	 */
	public void runInThread(final CollectionWorker cw, 
			final File outDir, final Object[] params) {
		ArrayList<CollectionWorker> works = new ArrayList<CollectionWorker>();
		works.add(cw);
		this.runInThread(works, outDir, params);
	}

	/**
	 * Sistema i vari "workers"
	 * 
	 * @throws XmlException
	 */
	public void setupWorks() throws XmlException {
		pm = new ProgressMonitor(this, null, null, 0, 100);
		// vrf = new Verify("Verifica", "Verifica XML con XSD");
		// vrf.addObserver(this);
		xml2et = new MoneteXml2Etichette(Messages.getString("MonetePanel.54"), //$NON-NLS-1$
				Messages.getString("MonetePanel.55")); //$NON-NLS-1$
		xml2et.addObserver(this);
		xml2html = new MoneteXml2Html(Messages.getString("MonetePanel.56"), //$NON-NLS-1$
				Messages.getString("MonetePanel.57")); //$NON-NLS-1$
		xml2html.addObserver(this);
		xml2tex = new MoneteXml2Tex(Messages.getString("MonetePanel.58"), //$NON-NLS-1$
				Messages.getString("MonetePanel.59")); //$NON-NLS-1$
		xml2tex.addObserver(this);
		qrc = new MoneteXml2QR(Messages.getString("MonetePanel.60"), Messages.getString("MonetePanel.61")); //$NON-NLS-1$ //$NON-NLS-2$
		qrc.addObserver(this);
		xpc = new XelatexPdfCreator(Messages.getString("MonetePanel.62"), Messages.getString("MonetePanel.63")); //$NON-NLS-1$ //$NON-NLS-2$
		xpc.addObserver(this);
		disegnaVassoi();
	}

	/**
	 * Esegue il sorting della lista per paese o per id
	 * 
	 * @param ordering l'ordinamento scelto
	 * @throws XmlException
	 */
	private void sortListMonete(MonetaXml.Ordering ordering)
			throws XmlException {
		MonetaXml sel = (MonetaXml) this.jListMonete.getSelectedValue();
		MonetaListModel mlm = new MonetaListModel(ordering);
		this.jListMonete.setModel(mlm);
		int index = 0;
		if (sel != null) {
			String id = ""; //$NON-NLS-1$
			id = sel.getId();
			/* cerca l'indice */
			for (int i = 0; i < mlm.getSize(); i++) {
				MonetaXml curIndex = (MonetaXml) mlm.getElementAt(i);
				if (id.equals(curIndex.getId())) {
					index = i;
					curIndex = null;
					break;
				}
			}

		}
		this.jListMonete.setSelectedIndex(index);
		this.jListMonete.ensureIndexIsVisible(index);
	}

	/**
	 * Implementa l'interfaccia Observer
	 * 
	 * @param o
	 * @param arg
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			// mette il messaggio come stringa
			MainFrame.setMessage(new Message((String) arg, Level.INFO));
		} else if (arg instanceof Progress) {
			Progress p = (Progress) (arg);
			// la nota va messa per prima
			this.pm.setNote(p.getMsg());
			// mette il massimo
			this.pm.setMaximum(p.getMax());
			// setta la progress bar
			this.pm.setProgress(p.getCurrent());
		} else if (arg instanceof Message) {
			// mette il messaggio
			MainFrame.setMessage((Message) (arg));
		} else {
			GestLog.Error(MonetePanel.class, "update", //$NON-NLS-1$
					Messages.getString("Generic.9")); //$NON-NLS-1$
		}
	}
}
