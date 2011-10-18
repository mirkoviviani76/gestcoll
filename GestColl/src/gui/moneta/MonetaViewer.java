/*
 * Modifiche:
 * 28.01.11 sistemato il caso in cui la data non c'e'
 */

package gui.moneta;

import exceptions.XmlException;
import gestXml.CollezioneXml;
import gestXml.MonetaXml;
import gui.MainFrame;
import gui.datamodels.GenericCellRenderer;
import gui.datamodels.GenericListModel;
import gui.datamodels.XmlDocumentChangeListener;
import gui.moneta.forms.AutoritaForm;
import gui.moneta.forms.DocumentoForm;
import gui.moneta.forms.LetteraturaForm;
import gui.moneta.forms.NotaForm;
import gui.moneta.forms.ZecchiereForm;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;

import javax.swing.JTextField;
import javax.xml.transform.TransformerException;

import main.Common.Lato;
import main.GenericUtil;
import main.GestLog;
import main.History;
import main.Message;

import org.xml.sax.SAXException;

import Resources.i18n.Messages;
import XmlData.Moneta.Libro;
import XmlData.Moneta.Zecchiere;

/**
 * 
 */
public class MonetaViewer extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private XmlDocumentChangeListener chAnno;
	private XmlDocumentChangeListener chData;
	private XmlDocumentChangeListener chDescrizione_d;
	private XmlDocumentChangeListener chDescrizione_r;
	private XmlDocumentChangeListener chDescrizione_t;
	private XmlDocumentChangeListener chDiametro_m;
	private XmlDocumentChangeListener chDiametro_v;
	private XmlDocumentChangeListener chForma;
	private XmlDocumentChangeListener chLuogo;
	private XmlDocumentChangeListener chMetallo;
	private XmlDocumentChangeListener chPaese;
	private XmlDocumentChangeListener chPeso_m;
	private XmlDocumentChangeListener chPeso_v;
	private XmlDocumentChangeListener chPrezzo_m;
	private XmlDocumentChangeListener chPrezzo_v;
	private XmlDocumentChangeListener chValore;
	private XmlDocumentChangeListener chValuta;
	private XmlDocumentChangeListener chZecca_n;
	private XmlDocumentChangeListener chZecca_s;
	private boolean isEditingMode;
	private javax.swing.JButton jBAddAutorita;

	private javax.swing.JButton jBAddDoc;

	private javax.swing.JButton jBAddLetteratura;

	private javax.swing.JButton jBAddNote;

	private javax.swing.JButton jBAddZecchiere;

	private com.toedter.calendar.JDateChooser jDateChooser1;

	private javax.swing.JLabel jLabel1;

	private javax.swing.JLabel jLabel10;

	private javax.swing.JLabel jLabel11;

	private javax.swing.JLabel jLabel12;

	private javax.swing.JLabel jLabel13;

	private javax.swing.JLabel jLabel15;

	private javax.swing.JLabel jLabel16;

	private javax.swing.JLabel jLabel17;

	private javax.swing.JLabel jLabel2;

	private javax.swing.JLabel jLabel3;

	private javax.swing.JLabel jLabel4;

	private javax.swing.JLabel jLabel5;

	private javax.swing.JLabel jLabel6;

	private javax.swing.JLabel jLabel9;

	private javax.swing.JLabel jLabelAutorita;
	private javax.swing.JLabel jLabelLetteratura;
	private javax.swing.JLabel jLabelZecca;
	private javax.swing.JLabel jLabelZecchiere;
	private javax.swing.JList jLAutorita;
	private javax.swing.JList jLDocumenti;
	private javax.swing.JList jLLetteratura;
	private javax.swing.JList jLNote;
	private javax.swing.JList jLZecchiere;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JPanel jPanel5;
	private javax.swing.JPanel jPanelDritto;
	private javax.swing.JPanel jPanelRovescio;
	private javax.swing.JPanel jPanelTaglio;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JScrollPane jScrollPane7;
	private javax.swing.JTextField jTFAnno;
	private gui.moneta.MisuraControl jTFDiametro;
	private javax.swing.JTextField jTFForma;
	private javax.swing.JTextField jTFId;
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JTextField JTFLuogo;
	private javax.swing.JTextField jTFMetallo;
	private gui.moneta.MisuraControl jTFNominale;
	private javax.swing.JTextField jTFPaese;
	private gui.moneta.MisuraControl jTFPeso;
	public javax.swing.JTextField jTFPosizione;
	private gui.moneta.MisuraControl jTFPrezzo;
	private gui.moneta.ZeccaControl jTFZecca;
	private MonetaXml mng;
	private gui.moneta.MonetaDescrizione monetaDescrizioneDritto;
	private gui.moneta.MonetaDescrizione monetaDescrizioneRovescio;
	private gui.moneta.MonetaDescrizione monetaDescrizioneTaglio;

	// End of variables declaration//GEN-END:variables
	/** Creates new form MonetaViewer */
	public MonetaViewer() {
		initComponents();
	}

	/**
	 * Listener per i cambi nel testo e nella data. I cambi nelle liste sono
	 * gestiti a parte.
	 */
	public void addDocumentListener() {
		this.jTFPaese.getDocument().addDocumentListener(this.chPaese);
		this.jTFAnno.getDocument().addDocumentListener(this.chAnno);
		this.JTFLuogo.getDocument().addDocumentListener(this.chLuogo);
		this.jTFMetallo.getDocument().addDocumentListener(this.chMetallo);
		this.jTFForma.getDocument().addDocumentListener(this.chForma);
		this.monetaDescrizioneDritto.addDocumentListener(this.chDescrizione_d);
		this.monetaDescrizioneRovescio
				.addDocumentListener(this.chDescrizione_r);
		this.monetaDescrizioneTaglio.addDocumentListener(this.chDescrizione_t);
		this.jTFPeso.addDocumentListener(this.chPeso_v, this.chPeso_m);
		this.jTFDiametro.addDocumentListener(this.chDiametro_v,
				this.chDiametro_m);
		this.jTFPrezzo.addDocumentListener(this.chPrezzo_v, this.chPrezzo_m);
		this.jTFNominale.addDocumentListener(this.chValore, this.chValuta);
		this.jTFZecca.addDocumentListener(this.chZecca_n, this.chZecca_s);
		this.jDateChooser1.addPropertyChangeListener(this.chData);

	}

	/**
	 * Riempie la gui con i dati estratti dall'xml della moneta
	 * 
	 * @param mng
	 *            il gestore dell'xml della moneta
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 * @throws ParseException
	 */
	public void fillData(MonetaXml mng) throws SAXException, IOException,
			TransformerException, ParseException {
		this.mng = mng;
		String id = mng.getId();
		this.jTFId.setText(id);
		this.jTFAnno.setText(mng.getAnno());
		this.jTFPaese.setText(mng.getPaese());
		this.jTFNominale.setMisura(mng.getNominale());
		this.jLAutorita.removeAll();
		this.jLAutorita.setListData(mng.getAutorita().getNome().toArray());
		this.jTFZecca.setZecca(mng.getZecca());
		this.jTFDiametro.setMisura(mng.getDiametro());
		this.jTFPeso.setMisura(mng.getPeso());
		this.jTFMetallo.setText(mng.getMetallo());
		this.jTFForma.setText(mng.getForma());
		this.JTFLuogo.setText(mng.getLuogo());
		String data = mng.getData();
		if (!data.equals("")) { //$NON-NLS-1$
			this.jDateChooser1.setDateFormatString("dd/MM/yyyy"); //$NON-NLS-1$
			DateFormat in = new SimpleDateFormat("yyyy-MM-dd"); //$NON-NLS-1$
			this.jDateChooser1.setDate(in.parse(data));
		}
		this.jTFPrezzo.setMisura(mng.getPrezzo());

		this.monetaDescrizioneDritto.fillData(mng);
		this.monetaDescrizioneRovescio.fillData(mng);
		this.monetaDescrizioneTaglio.fillData(mng);

		this.jTFPosizione.setText(mng.getPosizione().getContenitore() + " " //$NON-NLS-1$
				+ mng.getPosizione().getVassoio() + " " //$NON-NLS-1$
				+ mng.getPosizione().getRiga() + " " //$NON-NLS-1$
				+ mng.getPosizione().getColonna());
		this.jTFPosizione.setToolTipText(Messages.getString("MonetaViewer.0") //$NON-NLS-1$
				+ mng.getPosizione().getContenitore() + "."
				+ mng.getPosizione().getVassoio() + "."
				+ mng.getPosizione().getRiga() + "."
				+ mng.getPosizione().getColonna());

		this.jLNote.setModel(new GenericListModel<String>(mng.getNote()));

		this.jLDocumenti
				.setModel(new GenericListModel<XmlData.Moneta.DocumentoAddizionale>(
						mng.getItemAddizionali()));
		this.jLLetteratura.setModel(new GenericListModel<XmlData.Moneta.Libro>(
				mng.getLetteratura()));
		this.jLZecchiere.setModel(new GenericListModel<Zecchiere>(mng
				.getZecchieri()));

		this.setEditable(false);

		chPaese = new XmlDocumentChangeListener(mng, MonetaXml.Fields.PAESE);
		chAnno = new XmlDocumentChangeListener(mng, MonetaXml.Fields.ANNO);
		chLuogo = new XmlDocumentChangeListener(mng, MonetaXml.Fields.LUOGO);
		chMetallo = new XmlDocumentChangeListener(mng, MonetaXml.Fields.METALLO);
		chForma = new XmlDocumentChangeListener(mng, MonetaXml.Fields.FORMA);
		chDescrizione_d = new XmlDocumentChangeListener(mng,
				MonetaXml.Fields.DESCRIZIONE_D);
		chDescrizione_r = new XmlDocumentChangeListener(mng,
				MonetaXml.Fields.DESCRIZIONE_R);
		chDescrizione_t = new XmlDocumentChangeListener(mng,
				MonetaXml.Fields.DESCRIZIONE_T);
		chPeso_v = new XmlDocumentChangeListener(mng, MonetaXml.Fields.PESO_V);
		chPeso_m = new XmlDocumentChangeListener(mng, MonetaXml.Fields.PESO_M);
		chDiametro_v = new XmlDocumentChangeListener(mng,
				MonetaXml.Fields.DIAMETRO_V);
		chDiametro_m = new XmlDocumentChangeListener(mng,
				MonetaXml.Fields.DIAMETRO_M);
		chPrezzo_v = new XmlDocumentChangeListener(mng,
				MonetaXml.Fields.PREZZO_V);
		chPrezzo_m = new XmlDocumentChangeListener(mng,
				MonetaXml.Fields.PREZZO_M);
		chValore = new XmlDocumentChangeListener(mng, MonetaXml.Fields.VALORE);
		chValuta = new XmlDocumentChangeListener(mng, MonetaXml.Fields.VALUTA);
		chZecca_n = new XmlDocumentChangeListener(mng, MonetaXml.Fields.ZECCA_N);
		chZecca_s = new XmlDocumentChangeListener(mng, MonetaXml.Fields.ZECCA_S);
		chData = new XmlDocumentChangeListener(mng, MonetaXml.Fields.DATA);

		this.addDocumentListener();

	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		jPanel4 = new javax.swing.JPanel();
		jPanel1 = new javax.swing.JPanel();
		jLabel2 = new javax.swing.JLabel();
		jTFId = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		jTFPaese = new javax.swing.JTextField();
		jLabel5 = new javax.swing.JLabel();
		jTFNominale = new gui.moneta.MisuraControl();
		jLabel6 = new javax.swing.JLabel();
		jTFAnno = new javax.swing.JTextField();
		jLabelAutorita = new javax.swing.JLabel();
		jScrollPane4 = new javax.swing.JScrollPane();
		jLAutorita = new javax.swing.JList();
		jLabel13 = new javax.swing.JLabel();
		jTFPeso = new gui.moneta.MisuraControl();
		jLabel17 = new javax.swing.JLabel();
		jTFDiametro = new gui.moneta.MisuraControl();
		jLabel15 = new javax.swing.JLabel();
		jTFForma = new javax.swing.JTextField();
		jLabel16 = new javax.swing.JLabel();
		jTFMetallo = new javax.swing.JTextField();
		jBAddAutorita = new javax.swing.JButton();
		jPanel2 = new javax.swing.JPanel();
		jLabelZecca = new javax.swing.JLabel();
		jLabelZecchiere = new javax.swing.JLabel();
		jScrollPane7 = new javax.swing.JScrollPane();
		jLZecchiere = new javax.swing.JList();
		jScrollPane1 = new javax.swing.JScrollPane();
		jLLetteratura = new javax.swing.JList();
		jLabelLetteratura = new javax.swing.JLabel();
		jTFZecca = new gui.moneta.ZeccaControl();
		jBAddZecchiere = new javax.swing.JButton();
		jBAddLetteratura = new javax.swing.JButton();
		jPanel3 = new javax.swing.JPanel();
		jLabel12 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		jLabel10 = new javax.swing.JLabel();
		jTFPosizione = new javax.swing.JTextField();
		JTFLuogo = new javax.swing.JTextField();
		jDateChooser1 = new com.toedter.calendar.JDateChooser();
		jTFPrezzo = new gui.moneta.MisuraControl();
		jScrollPane2 = new javax.swing.JScrollPane();
		jLNote = new javax.swing.JList();
		jBAddNote = new javax.swing.JButton();
		jScrollPane3 = new javax.swing.JScrollPane();
		jLDocumenti = new javax.swing.JList();
		jLabel4 = new javax.swing.JLabel();
		jBAddDoc = new javax.swing.JButton();
		jPanel5 = new javax.swing.JPanel();
		jPanelDritto = new javax.swing.JPanel();
		monetaDescrizioneDritto = new gui.moneta.MonetaDescrizione(Lato.DRITTO);
		jPanelRovescio = new javax.swing.JPanel();
		monetaDescrizioneRovescio = new gui.moneta.MonetaDescrizione(
				Lato.ROVESCIO);
		jPanelTaglio = new javax.swing.JPanel();
		monetaDescrizioneTaglio = new gui.moneta.MonetaDescrizione(Lato.TAGLIO);

		jPanel4.setInheritsPopupMenu(true);

		jPanel1.setAutoscrolls(true);
		jPanel1.setInheritsPopupMenu(true);
		jPanel1.setMinimumSize(new java.awt.Dimension(91, 94));
		jPanel1.setLayout(new java.awt.GridBagLayout());

		jLabel2.setLabelFor(jTFId);
		jLabel2.setText(Messages.getString("MonetaViewer.4")); // NOI18N //$NON-NLS-1$
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel1.add(jLabel2, gridBagConstraints);

		jTFId.setInheritsPopupMenu(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		jPanel1.add(jTFId, gridBagConstraints);

		jLabel3.setLabelFor(jTFPaese);
		jLabel3.setText(Messages.getString("MonetaViewer.5")); // NOI18N //$NON-NLS-1$
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel1.add(jLabel3, gridBagConstraints);

		jTFPaese.setInheritsPopupMenu(true);
		jTFPaese.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jTFPaeseActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		jPanel1.add(jTFPaese, gridBagConstraints);

		jLabel5.setLabelFor(jTFNominale);
		jLabel5.setText(Messages.getString("Generic.26")); // NOI18N //$NON-NLS-1$
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel1.add(jLabel5, gridBagConstraints);

		jTFNominale.setInheritsPopupMenu(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		jPanel1.add(jTFNominale, gridBagConstraints);

		jLabel6.setLabelFor(jTFAnno);
		jLabel6.setText(Messages.getString("Generic.1")); // NOI18N //$NON-NLS-1$
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel1.add(jLabel6, gridBagConstraints);

		jTFAnno.setInheritsPopupMenu(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		jPanel1.add(jTFAnno, gridBagConstraints);

		jLabelAutorita.setLabelFor(jLAutorita);
		jLabelAutorita.setText(Messages.getString("MonetaViewer.8")); // NOI18N //$NON-NLS-1$
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel1.add(jLabelAutorita, gridBagConstraints);

		jScrollPane4.setInheritsPopupMenu(true);

		jLAutorita.setFont(new java.awt.Font("Dialog", 0, 12));
		jLAutorita
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jLAutorita.setInheritsPopupMenu(true);
		jLAutorita.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jLAutoritaMouseClicked(evt);
			}
		});
		jScrollPane4.setViewportView(jLAutorita);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		jPanel1.add(jScrollPane4, gridBagConstraints);

		jLabel13.setLabelFor(jTFPeso);
		jLabel13.setText(Messages.getString("MonetaViewer.10")); // NOI18N //$NON-NLS-1$
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel1.add(jLabel13, gridBagConstraints);

		jTFPeso.setInheritsPopupMenu(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		jPanel1.add(jTFPeso, gridBagConstraints);

		jLabel17.setLabelFor(jTFDiametro);
		jLabel17.setText(Messages.getString("MonetaViewer.11")); // NOI18N //$NON-NLS-1$
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel1.add(jLabel17, gridBagConstraints);

		jTFDiametro.setInheritsPopupMenu(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		jPanel1.add(jTFDiametro, gridBagConstraints);

		jLabel15.setLabelFor(jTFForma);
		jLabel15.setText(Messages.getString("MonetaViewer.12")); // NOI18N //$NON-NLS-1$
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel1.add(jLabel15, gridBagConstraints);

		jTFForma.setInheritsPopupMenu(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		jPanel1.add(jTFForma, gridBagConstraints);

		jLabel16.setLabelFor(jTFMetallo);
		jLabel16.setText(Messages.getString("MonetaViewer.13")); // NOI18N //$NON-NLS-1$
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 9;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel1.add(jLabel16, gridBagConstraints);

		jTFMetallo.setInheritsPopupMenu(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 9;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		jPanel1.add(jTFMetallo, gridBagConstraints);

		jBAddAutorita.setFont(new java.awt.Font("Dialog", 0, 10)); //$NON-NLS-1$
		jBAddAutorita.setText("+"); //$NON-NLS-1$
		jBAddAutorita.setInheritsPopupMenu(true);
		jBAddAutorita.setMargin(new java.awt.Insets(0, 0, 0, 0));
		jBAddAutorita.setMaximumSize(new java.awt.Dimension(25, 25));
		jBAddAutorita.setMinimumSize(new java.awt.Dimension(25, 25));
		jBAddAutorita.setPreferredSize(new java.awt.Dimension(25, 25));
		jBAddAutorita.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jBAddAutoritaActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
		jPanel1.add(jBAddAutorita, gridBagConstraints);

		jPanel2.setAutoscrolls(true);
		jPanel2.setInheritsPopupMenu(true);
		jPanel2.setLayout(new java.awt.GridBagLayout());

		jLabelZecca.setLabelFor(jTFZecca);
		jLabelZecca.setText(Messages.getString("MonetaViewer.16")); // NOI18N //$NON-NLS-1$
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel2.add(jLabelZecca, gridBagConstraints);

		jLabelZecchiere.setLabelFor(jLZecchiere);
		jLabelZecchiere.setText(Messages.getString("MonetaViewer.17")); // NOI18N //$NON-NLS-1$
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel2.add(jLabelZecchiere, gridBagConstraints);

		jScrollPane7.setInheritsPopupMenu(true);

		jLZecchiere.setFont(new java.awt.Font("Dialog", 0, 12)); //$NON-NLS-1$
		jLZecchiere
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jLZecchiere.setCellRenderer(new GenericCellRenderer<Zecchiere>());
		jLZecchiere.setInheritsPopupMenu(true);
		jLZecchiere.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jLZecchiereMouseClicked(evt);
			}
		});
		jScrollPane7.setViewportView(jLZecchiere);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		jPanel2.add(jScrollPane7, gridBagConstraints);

		jScrollPane1.setInheritsPopupMenu(true);

		jLLetteratura.setFont(new java.awt.Font("Dialog", 0, 11)); //$NON-NLS-1$
		jLLetteratura
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jLLetteratura
				.setCellRenderer(new GenericCellRenderer<XmlData.Moneta.Libro>());
		jLLetteratura.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jLLetteraturaMouseClicked(evt);
			}
		});
		jScrollPane1.setViewportView(jLLetteratura);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		jPanel2.add(jScrollPane1, gridBagConstraints);

		jLabelLetteratura.setText(Messages.getString("MonetaViewer.14")); //$NON-NLS-1$
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel2.add(jLabelLetteratura, gridBagConstraints);

		jTFZecca.setInheritsPopupMenu(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		jPanel2.add(jTFZecca, gridBagConstraints);

		jBAddZecchiere.setFont(new java.awt.Font("Dialog", 0, 10)); //$NON-NLS-1$
		jBAddZecchiere.setText("+"); //$NON-NLS-1$
		jBAddZecchiere.setMargin(new java.awt.Insets(0, 0, 0, 0));
		jBAddZecchiere.setMaximumSize(new java.awt.Dimension(25, 25));
		jBAddZecchiere.setMinimumSize(new java.awt.Dimension(25, 25));
		jBAddZecchiere.setPreferredSize(new java.awt.Dimension(25, 25));
		jBAddZecchiere.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jBAddZecchiereActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
		jPanel2.add(jBAddZecchiere, gridBagConstraints);

		jBAddLetteratura.setFont(new java.awt.Font("Dialog", 0, 10)); //$NON-NLS-1$
		jBAddLetteratura.setText("+"); //$NON-NLS-1$
		jBAddLetteratura.setMargin(new java.awt.Insets(0, 0, 0, 0));
		jBAddLetteratura.setMaximumSize(new java.awt.Dimension(25, 25));
		jBAddLetteratura.setMinimumSize(new java.awt.Dimension(25, 25));
		jBAddLetteratura.setPreferredSize(new java.awt.Dimension(25, 25));
		jBAddLetteratura.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jBAddLetteraturaActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
		jPanel2.add(jBAddLetteratura, gridBagConstraints);

		jPanel3.setAutoscrolls(true);
		jPanel3.setInheritsPopupMenu(true);
		jPanel3.setLayout(new java.awt.GridBagLayout());

		jLabel12.setText(Messages.getString("Generic.20")); //$NON-NLS-1$
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel3.add(jLabel12, gridBagConstraints);

		jLabel11.setLabelFor(jTFPosizione);
		jLabel11.setText(Messages.getString("MonetaViewer.18")); // NOI18N //$NON-NLS-1$
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
		jPanel3.add(jLabel11, gridBagConstraints);

		jLabel1.setLabelFor(JTFLuogo);
		jLabel1.setText(Messages.getString("MonetaViewer.19")); // NOI18N //$NON-NLS-1$
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
		jPanel3.add(jLabel1, gridBagConstraints);

		jLabel9.setText(Messages.getString("MonetaViewer.20")); // NOI18N //$NON-NLS-1$
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
		jPanel3.add(jLabel9, gridBagConstraints);

		jLabel10.setLabelFor(jTFPrezzo);
		jLabel10.setText(Messages.getString("MonetaViewer.21")); // NOI18N //$NON-NLS-1$
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
		jPanel3.add(jLabel10, gridBagConstraints);

		jTFPosizione.setInheritsPopupMenu(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
		gridBagConstraints.weightx = 1.0;
		jPanel3.add(jTFPosizione, gridBagConstraints);

		JTFLuogo.setInheritsPopupMenu(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
		gridBagConstraints.weightx = 1.0;
		jPanel3.add(JTFLuogo, gridBagConstraints);

		jDateChooser1.setDateFormatString("dd-MM-yyyy"); //$NON-NLS-1$
		jDateChooser1.setInheritsPopupMenu(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		jPanel3.add(jDateChooser1, gridBagConstraints);

		jTFPrezzo.setInheritsPopupMenu(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
		gridBagConstraints.weightx = 1.0;
		jPanel3.add(jTFPrezzo, gridBagConstraints);

		jScrollPane2.setInheritsPopupMenu(true);

		jLNote.setFont(new java.awt.Font("Dialog", 0, 11)); //$NON-NLS-1$
		jLNote.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jLNote.setCellRenderer(new GenericCellRenderer<String>());
		jLNote.setInheritsPopupMenu(true);
		jLNote.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jLNoteMouseClicked(evt);
			}
		});
		jScrollPane2.setViewportView(jLNote);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 0.5;
		jPanel3.add(jScrollPane2, gridBagConstraints);

		jBAddNote.setFont(new java.awt.Font("Dialog", 0, 10)); //$NON-NLS-1$
		jBAddNote.setText("+"); //$NON-NLS-1$
		jBAddNote.setMargin(new java.awt.Insets(0, 0, 0, 0));
		jBAddNote.setMaximumSize(new java.awt.Dimension(25, 25));
		jBAddNote.setMinimumSize(new java.awt.Dimension(25, 25));
		jBAddNote.setPreferredSize(new java.awt.Dimension(25, 25));
		jBAddNote.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jBAddNoteActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
		jPanel3.add(jBAddNote, gridBagConstraints);

		jScrollPane3.setInheritsPopupMenu(true);

		jLDocumenti
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jLDocumenti.setInheritsPopupMenu(true);
		jLDocumenti.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jLDocumentiMouseClicked(evt);
			}
		});
		jScrollPane3.setViewportView(jLDocumenti);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 0.5;
		jPanel3.add(jScrollPane3, gridBagConstraints);

		jLabel4.setText(Messages.getString("MonetaViewer.22")); //$NON-NLS-1$
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel3.add(jLabel4, gridBagConstraints);

		jBAddDoc.setFont(new java.awt.Font("Dialog", 0, 10)); //$NON-NLS-1$
		jBAddDoc.setText("+"); //$NON-NLS-1$
		jBAddDoc.setMargin(new java.awt.Insets(0, 0, 0, 0));
		jBAddDoc.setMaximumSize(new java.awt.Dimension(25, 25));
		jBAddDoc.setMinimumSize(new java.awt.Dimension(25, 25));
		jBAddDoc.setPreferredSize(new java.awt.Dimension(25, 25));
		jBAddDoc.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jBAddDocActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
		jPanel3.add(jBAddDoc, gridBagConstraints);

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(
				jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout
				.setHorizontalGroup(jPanel4Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel4Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												jPanel1,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												337, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jPanel2,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												339, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jPanel3,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												339, Short.MAX_VALUE)
										.addGap(19, 19, 19)));
		jPanel4Layout
				.setVerticalGroup(jPanel4Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel4Layout
										.createSequentialGroup()
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												jPanel4Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																false)
														.addComponent(
																jPanel3,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jPanel2,
																javax.swing.GroupLayout.Alignment.LEADING,
																0, 0,
																Short.MAX_VALUE)
														.addComponent(
																jPanel1,
																javax.swing.GroupLayout.Alignment.LEADING,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																254,
																Short.MAX_VALUE))
										.addContainerGap()));

		jPanelDritto.setBorder(javax.swing.BorderFactory
				.createTitledBorder(Messages.getString("MonetaViewer.23"))); //$NON-NLS-1$
		jPanelDritto.setInheritsPopupMenu(true);
		jPanelDritto.setLayout(new java.awt.BorderLayout());

		monetaDescrizioneDritto.setInheritsPopupMenu(true);
		jPanelDritto.add(monetaDescrizioneDritto,
				java.awt.BorderLayout.PAGE_START);

		jPanelRovescio.setBorder(javax.swing.BorderFactory
				.createTitledBorder(Messages.getString("MonetaViewer.24"))); //$NON-NLS-1$
		jPanelRovescio.setInheritsPopupMenu(true);
		jPanelRovescio.setLayout(new java.awt.BorderLayout());

		monetaDescrizioneRovescio.setInheritsPopupMenu(true);
		jPanelRovescio.add(monetaDescrizioneRovescio,
				java.awt.BorderLayout.CENTER);

		jPanelTaglio.setBorder(javax.swing.BorderFactory
				.createTitledBorder(Messages.getString("MonetaViewer.25"))); //$NON-NLS-1$
		jPanelTaglio.setInheritsPopupMenu(true);
		jPanelTaglio.setLayout(new java.awt.BorderLayout());

		monetaDescrizioneTaglio.setInheritsPopupMenu(true);
		jPanelTaglio.add(monetaDescrizioneTaglio,
				java.awt.BorderLayout.PAGE_START);

		javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(
				jPanel5);
		jPanel5.setLayout(jPanel5Layout);
		jPanel5Layout
				.setHorizontalGroup(jPanel5Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel5Layout
										.createSequentialGroup()
										.addComponent(
												jPanelDritto,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												336,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												25, Short.MAX_VALUE)
										.addComponent(
												jPanelRovescio,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												336,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jPanelTaglio,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));

		jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { jPanelDritto, jPanelRovescio });

		jPanel5Layout
				.setVerticalGroup(jPanel5Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel5Layout
										.createSequentialGroup()
										.addGroup(
												jPanel5Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																jPanelDritto,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jPanelTaglio,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																jPanelRovescio,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														jPanel4,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														jPanel5,
														javax.swing.GroupLayout.Alignment.TRAILING,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jPanel4,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jPanel5,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)));
	}// </editor-fold>//GEN-END:initComponents

	private void jBAddAutoritaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBAddAutoritaActionPerformed
		AutoritaForm nf = new AutoritaForm(null, true);
		nf.setEditable(isEditingMode);
		nf.setVisible(true);
		// valuta ritorno con tasto "ok/modifica" premuto
		if (nf.getReturnStatus() == AutoritaForm.RET_OK) {
			// ottiene i nuovi dati della nota dal form
			String nuovaAutorita = nf.getData();
			mng.getAutorita().getNome().add(nuovaAutorita);
			// carica i nuovi valori
			this.jLAutorita.setListData(mng.getAutorita().getNome().toArray());
		}

	}// GEN-LAST:event_jBAddAutoritaActionPerformed

	private void jBAddDocActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBAddDocActionPerformed
		DocumentoForm zf = new DocumentoForm(null, true);
		zf.setVisible(true);
		// valuta ritorno con tasto "ok/modifica" premuto
		if (zf.getReturnStatus() == DocumentoForm.RET_OK) {
			// ottiene i nuovi dati della nota dal form
			XmlData.Moneta.DocumentoAddizionale nuovoDoc = zf.getData();
			mng.getItemAddizionali().add(nuovoDoc);
			this.jLDocumenti
					.setModel(new GenericListModel<XmlData.Moneta.DocumentoAddizionale>(
							mng.getItemAddizionali()));
		}

	}// GEN-LAST:event_jBAddDocActionPerformed

	private void jBAddLetteraturaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBAddLetteraturaActionPerformed
		LetteraturaForm zf = new LetteraturaForm(null, true);
		zf.setVisible(true);
		// valuta ritorno con tasto "ok/modifica" premuto
		if (zf.getReturnStatus() == LetteraturaForm.RET_OK) {
			// ottiene i nuovi dati della nota dal form
			XmlData.Moneta.Libro nuovoLibro = zf.getData();
			mng.getLetteratura().add(nuovoLibro);
			this.jLLetteratura
					.setModel(new GenericListModel<XmlData.Moneta.Libro>(mng
							.getLetteratura()));
		}
	}// GEN-LAST:event_jBAddLetteraturaActionPerformed

	private void jBAddNoteActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBAddNoteActionPerformed
		NotaForm nf = new NotaForm(null, true);
		nf.setEditable(isEditingMode);
		nf.setVisible(true);
		// valuta ritorno con tasto "ok/modifica" premuto
		if (nf.getReturnStatus() == NotaForm.RET_OK) {
			// ottiene i nuovi dati della nota dal form
			String nuovaNota = nf.getData();
			mng.addNota(nuovaNota);
			// carica i nuovi valori
			this.jLNote.setModel(new GenericListModel<String>(mng.getNote()));
		}
	}// GEN-LAST:event_jBAddNoteActionPerformed

	private void jBAddZecchiereActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jBAddZecchiereActionPerformed
		ZecchiereForm zf = new ZecchiereForm(null, true);
		zf.setVisible(true);
		// valuta ritorno con tasto "ok/modifica" premuto
		if (zf.getReturnStatus() == ZecchiereForm.RET_OK) {
			// ottiene i nuovi dati della nota dal form
			Zecchiere nuovoZecchiere = zf.getData();
			mng.getZecchieri().add(nuovoZecchiere);
			// carica i nuovi valori
			this.jLZecchiere.setModel(new GenericListModel<Zecchiere>(mng
					.getZecchieri()));
		}

	}// GEN-LAST:event_jBAddZecchiereActionPerformed

	private void jLAutoritaMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLAutoritaMouseClicked
		if (this.isEditingMode && !evt.isPopupTrigger()) {
			/* apre il dialog */
			String autorita = (String) (jLAutorita.getSelectedValue());
			// attiva la finestra di modifica (deve essere modale)
			AutoritaForm nf = new AutoritaForm(null, true);
			nf.setEditable(isEditingMode);
			nf.setNome(autorita);
			nf.setVisible(true);

			// valuta ritorno con tasto "ok/modifica" premuto
			if (nf.getReturnStatus() == AutoritaForm.RET_OK) {
				// ottiene i nuovi dati dell'autorita
				String nuovaAutorita = nf.getData();
				// salva nel DOM
				mng.modifyAutorita(autorita, nuovaAutorita);
				// carica i nuovi valori
				this.jLAutorita.setListData(mng.getAutorita().getNome()
						.toArray());

			}
		} else if (!evt.isPopupTrigger()) {
			// genera una stringa per cercare l'autorita concatenandola con il
			// Paese
			String s = (jLAutorita.getSelectedValue() + " " + jTFPaese //$NON-NLS-1$
					.getText());
			this.openBrowser(s);
		}

	}// GEN-LAST:event_jLAutoritaMouseClicked

	private void jLDocumentiMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLDocumentiMouseClicked
		if (this.isEditingMode && !evt.isPopupTrigger()) {
			XmlData.Moneta.DocumentoAddizionale doc = (XmlData.Moneta.DocumentoAddizionale) (jLDocumenti
					.getSelectedValue());
			// attiva la finestra di modifica (deve essere modale)
			DocumentoForm df = new DocumentoForm(null, true);
			df.setData(doc);
			df.setVisible(true);

			if (df.getReturnStatus() == DocumentoForm.RET_OK) {
				// ottiene i nuovi dati del documento
				XmlData.Moneta.DocumentoAddizionale nuovoDoc = df.getData();
				// salva nel DOM
				mng.modifyDocumento(doc, nuovoDoc);
				// carica i nuovi valori
				this.jLDocumenti
						.setModel(new GenericListModel<XmlData.Moneta.DocumentoAddizionale>(
								mng.getItemAddizionali()));
			}
		}
	}// GEN-LAST:event_jLDocumentiMouseClicked

	private void jLLetteraturaMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLLetteraturaMouseClicked
		if (this.isEditingMode && !evt.isPopupTrigger()) {
			XmlData.Moneta.Libro libro = (XmlData.Moneta.Libro) (jLLetteratura
					.getSelectedValue());
			// attiva la finestra di modifica (deve essere modale)
			LetteraturaForm zf = new LetteraturaForm(null, true);
			// riempie i campi
			zf.setData(libro);
			zf.setVisible(true);
			// valuta ritorno con tasto "ok/modifica" premuto
			if (zf.getReturnStatus() == LetteraturaForm.RET_OK) {
				XmlData.Moneta.Libro nuovoLibro = null;
				// ottiene i nuovi dati del libro
				nuovoLibro = zf.getData();
				// salva nel DOM
				mng.modifyLibro(libro, nuovoLibro);
				// carica i nuovi valori
				this.jLLetteratura.setModel(new GenericListModel<Libro>(mng
						.getLetteratura()));
			}
		}
	}// GEN-LAST:event_jLLetteraturaMouseClicked

	private void jLNoteMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLNoteMouseClicked
		if (this.isEditingMode && !evt.isPopupTrigger()) {
			/* apre il dialog */
			String nota = (String) (jLNote.getSelectedValue());
			// attiva la finestra di modifica (deve essere modale)
			NotaForm nf = new NotaForm(null, true);
			nf.setEditable(isEditingMode);
			nf.setNota(nota);
			nf.setVisible(true);

			// valuta ritorno con tasto "ok/modifica" premuto
			if (nf.getReturnStatus() == NotaForm.RET_OK) {
				// ottiene i nuovi dati della nota
				String nuovaNota = nf.getData();
				// salva nel DOM
				mng.modifyNota(nota, nuovaNota);
				// carica i nuovi valori
				this.jLNote
						.setModel(new GenericListModel<String>(mng.getNote()));
			}
		} else if (!evt.isPopupTrigger()) {
			/* mostra semplicemente la nota */
			NotaForm nf = new NotaForm(null, true);
			nf.setEditable(isEditingMode);
			nf.setNota((String) this.jLNote.getSelectedValue());
			nf.setVisible(true);
		}

	}// GEN-LAST:event_jLNoteMouseClicked

	/**
	 * Gestisce il click su uno zecchiere per permetterne la modifica in
	 * editmode
	 * 
	 * @param evt
	 */
	private void jLZecchiereMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jLZecchiereMouseClicked
		if (this.isEditingMode && !evt.isPopupTrigger()) {
			Zecchiere zecchiere = (Zecchiere) (jLZecchiere.getSelectedValue());
			// attiva la finestra di modifica (deve essere modale)
			ZecchiereForm zf = new ZecchiereForm(null, true);
			// riempie i campi
			zf.setData(zecchiere);
			zf.setVisible(true);
			// valuta ritorno con tasto "ok/modifica" premuto
			if (zf.getReturnStatus() == ZecchiereForm.RET_OK) {
				// ottiene i nuovi dati dello zecchiere
				Zecchiere nuovoZecchiere = zf.getData();
				// salva nel DOM
				mng.modifyZecchiere(zecchiere, nuovoZecchiere);
				// carica i nuovi valori
				this.jLZecchiere.setModel(new GenericListModel<Zecchiere>(mng
						.getZecchieri()));
			}
		}
	}// GEN-LAST:event_jLZecchiereMouseClicked

	private void jTFPaeseActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTFPaeseActionPerformed
	}// GEN-LAST:event_jTFPaeseActionPerformed

	/**
	 * gestisce il click su un item
	 * 
	 * @param evt
	 */
	/**
	 * apre il browser di default per cercare una stringa con google
	 * 
	 * @param itemToSearch
	 */
	private void openBrowser(String itemToSearch) {
		String search = itemToSearch.replace(" ", "+"); //$NON-NLS-1$ //$NON-NLS-2$
		// ottiene l'uri per la ricerca con google
		String uri = String.format(
				"http://www.google.it/#sclient=psy&&q=%s&fp=1", search); //$NON-NLS-1$
		try {
			GenericUtil.openBrowser(new URI(uri));
		} catch (URISyntaxException e) {
			GestLog.Error(this.getClass(), e);
		} catch (IOException e) {
			GestLog.Error(this.getClass(), e);
		}
	}

	/**
     *
     */
	public void removeDocumentListener() {
		this.jTFPaese.getDocument().removeDocumentListener(this.chPaese);
		this.jTFAnno.getDocument().removeDocumentListener(this.chAnno);
		this.JTFLuogo.getDocument().removeDocumentListener(this.chLuogo);
		this.jTFMetallo.getDocument().removeDocumentListener(this.chMetallo);
		this.jTFForma.getDocument().removeDocumentListener(this.chForma);
		this.monetaDescrizioneDritto
				.removeDocumentListener(this.chDescrizione_d);
		this.monetaDescrizioneRovescio
				.removeDocumentListener(this.chDescrizione_r);
		this.monetaDescrizioneTaglio
				.removeDocumentListener(this.chDescrizione_t);
		this.jTFPeso.removeDocumentListener(this.chPeso_v, this.chPeso_m);
		this.jTFDiametro.removeDocumentListener(this.chDiametro_v,
				this.chDiametro_m);
		this.jTFPrezzo.removeDocumentListener(this.chPrezzo_v, this.chPrezzo_m);
		this.jTFNominale.removeDocumentListener(this.chValore, this.chValuta);
		this.jTFZecca.removeDocumentListener(this.chZecca_n, this.chZecca_s);
		this.jDateChooser1.removePropertyChangeListener(this.chData);
	}

	/**
	 * Esegue il baclup
	 * 
	 * @throws XmlException
	 * @throws IOException 
	 * 
	 */
	public void salvaDati() throws XmlException, IOException {
		//esegue il backup
		String backup = CollezioneXml.getCollezione().salva();
		// Log
		History.addEvent(History.MODIFY);
		String msg = String.format(
				Messages.getString("MonetaViewer.26"), //$NON-NLS-1$
				backup);
		MainFrame.setMessage(new Message(msg, Level.INFO));
	}

	/**
	 * gestore del clic su posizione
	 * 
	 * @param evt
	 */
	/**
	 * setta la proprieta' editable per tutti i componenti editabili
	 * 
	 * @param flag
	 */
	public void setEditable(boolean flag) {
		this.isEditingMode = flag;

		this.JTFLuogo.setEditable(flag);
		this.jTFAnno.setEditable(flag);

		/* setta l'editable per il jdatechooser */
		this.jDateChooser1.getCalendarButton().setEnabled(flag);
		JTextField dateField = ((JTextField) this.jDateChooser1.getDateEditor()
				.getUiComponent());
		dateField.setEditable(flag);

		this.jTFDiametro.setEditable(flag);
		this.jTFForma.setEditable(flag);
		// jTFId attualmente non e' editabile
		this.jTFId.setEditable(false);
		this.jTFMetallo.setEditable(flag);
		this.jTFNominale.setEditable(flag);
		this.jTFPaese.setEditable(flag);
		this.jTFPeso.setEditable(flag);
		this.jTFPosizione.setEditable(flag);
		this.jTFZecca.setEditable(flag);
		this.jTFPrezzo.setEditable(flag);
		this.monetaDescrizioneDritto.setEditable(flag);
		this.monetaDescrizioneRovescio.setEditable(flag);
		this.monetaDescrizioneTaglio.setEditable(flag);

		this.jBAddAutorita.setVisible(flag);
		this.jBAddLetteratura.setVisible(flag);
		this.jBAddNote.setVisible(flag);
		this.jBAddZecchiere.setVisible(flag);
		this.jBAddDoc.setVisible(flag);

	}
}
