/*
 * Modifiche:
 * -
 */

package gui;

import gui.extraPanels.HistoryViewer;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import main.Common;
import main.GenericUtil;
import main.GestLog;
import main.Message;
import main.Splash;
import works.CollectionWorker;

/**
 * Finestra principale per la gestione della GUI
 * 
 */
public class MainFrame extends javax.swing.JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates new form MainFrame */
	public MainFrame() {
		Splash.getInstance().splashProgress("Start...");
		// inizializza i componenti grafici
		initComponents();
		// sistema i listener
		this.addActionListener();
		// mostra la versione come primo messaggio
		setMessage(new Message(Common.APPNAME + " versione " + Common.VERSION,
				Level.INFO));
		// carica i vari dati dei pannelli
		this.loadAllData();
		Splash.getInstance().splashProgress("Fine...");
		// mostra il pannello delle monete
		this.gestSelectMonete();
	}

	private void addActionListener() {
		/*
		 * sistema i listener (il mouseclic non basta: si possono scegliere
		 * anche con la tastiera)
		 */
		this.jBGestLibri.addActionListener(this);
		this.jBGestMonete.addActionListener(this);
		this.jMIAbout.addActionListener(this);
		this.jMIExit.addActionListener(this);
		this.jBGestContatti.addActionListener(this);
		this.jBGestLinks.addActionListener(this);
		this.jMIDelAll.addActionListener(this);
		this.jMIDelTemp.addActionListener(this);
		this.jMIShowStorico.addActionListener(this);
		this.jBStatistiche.addActionListener(this);
	}

	private void gestAbout() throws HeadlessException {
		JOptionPane.showMessageDialog(this, String.format("%s\nVersione: %s",
				Common.APPNAME, Common.VERSION), Common.APPNAME,
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void gestExit() {
		// esce
		System.exit(0);
	}

	private void gestRemoveAll() throws HeadlessException {
		try {
			String[] ddd = { Common.getCommon().getHtmlDir(), Common.getCommon().getLatexDir(), Common.getCommon().getQrDir() };
			int count = CollectionWorker.removeAll(ddd);
			setMessage(new Message(String.format("Rimossi %d oggetti.", count),
					Level.INFO));
		} catch (FileNotFoundException ex) {
			GestLog.Error(MainFrame.class, ex);
		} catch (IOException ex) {
			GestLog.Error(MainFrame.class, ex);
		}
	}

	private void gestRemoveTemp() throws HeadlessException {
		String[] ddd = { Common.getCommon().getHtmlDir(), Common.getCommon().getLatexDir() };
		int count = 0;
		try {
			count = CollectionWorker.removeTemp(ddd);
		} catch (FileNotFoundException ex) {
			GestLog.Error(MainFrame.class, ex);
		} catch (IOException ex) {
			GestLog.Error(MainFrame.class, ex);
		}
		setMessage(new Message(String.format("Rimossi %d oggetti.", count),
				Level.INFO));
	}

	private void gestSelectLibri() {
		this.libriPanel1.setVisible(true);
		this.monetePanel1.setVisible(false);
		// this.contattiPanel1.setVisible(false);
		this.linksPanel1.setVisible(false);
		this.statistichePanel1.setVisible(false);
	}

	private void gestSelectMonete() {
		this.libriPanel1.setVisible(false);
		this.monetePanel1.setVisible(true);
		// this.contattiPanel1.setVisible(false);
		this.linksPanel1.setVisible(false);
		this.statistichePanel1.setVisible(false);
	}

	private void gestContatti() {
		this.libriPanel1.setVisible(false);
		this.monetePanel1.setVisible(false);
		// this.contattiPanel1.setVisible(true);
		this.linksPanel1.setVisible(false);
		this.statistichePanel1.setVisible(false);
	}

	private void gestLinks() {
		this.libriPanel1.setVisible(false);
		this.monetePanel1.setVisible(false);
		// this.contattiPanel1.setVisible(false);
		this.linksPanel1.setVisible(true);
		this.statistichePanel1.setVisible(false);
	}

	private void gestSelectStatistiche() {
		this.libriPanel1.setVisible(false);
		this.monetePanel1.setVisible(false);
		// this.contattiPanel1.setVisible(false);
		this.linksPanel1.setVisible(false);
		this.statistichePanel1.setVisible(true);
	}

	private void gestStorico() {
		HistoryViewer hw = new HistoryViewer(this, true);
		hw.showFile(new File(Common.getCommon().getHistoryLog()));
		hw.setVisible(true);
	}

	/**
	 * Il gestore dell'action listener
	 * 
	 * @param ae
	 *            indica l'ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == this.jMIAbout) {
			gestAbout();
		} else if (ae.getSource() == this.jMIExit) {
			gestExit();
		} else if (ae.getSource() == this.jBGestMonete) {
			gestSelectMonete();
		} else if (ae.getSource() == this.jBGestLibri) {
			gestSelectLibri();
		} else if (ae.getSource() == this.jBGestContatti) {
			gestContatti();
		} else if (ae.getSource() == this.jBGestLinks) {
			gestLinks();
		} else if (ae.getSource() == this.jMIDelTemp) {
			gestRemoveTemp();
		} else if (ae.getSource() == this.jMIDelAll) {
			gestRemoveAll();
		} else if (ae.getSource() == this.jBStatistiche) {
			gestSelectStatistiche();
		} else if (ae.getSource() == this.jMIShowStorico) {
			gestStorico();
		} else {
			// azione non gestita
			GestLog.Error(MainFrame.class, "actionPerformed", "Unhandled: "
					+ ae.getActionCommand());
		}
	}

	/**
	 * 
	 * @param m
	 */
	public static void setMessage(Message m) {
		Color foreground = Color.BLUE;
		if (m.isSevere()) {
			foreground = Color.RED;
		}
		jTFMessages.setForeground(foreground);
		String ora = GenericUtil.getDateTime("HH:mm:ss");
		jTFMessages.setText(ora + "\t" + m.toString());
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jToolBar1 = new javax.swing.JToolBar();
		jBGestMonete = new javax.swing.JButton();
		jBGestLibri = new javax.swing.JButton();
		jBGestContatti = new javax.swing.JButton();
		jBGestLinks = new javax.swing.JButton();
		jBStatistiche = new javax.swing.JButton();
		jSeparator3 = new javax.swing.JToolBar.Separator();
		jSeparator1 = new javax.swing.JToolBar.Separator();
		jSeparator2 = new javax.swing.JToolBar.Separator();
		jLabel1 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTFMessages = new javax.swing.JTextArea();
		jPanel1 = new javax.swing.JPanel();
		monetePanel1 = new gui.moneta.MonetePanel();
		libriPanel1 = new gui.extraPanels.BibliotecaPanel();
		contattiPanel1 = new gui.extraPanels.ContattiPanel();
		linksPanel1 = new gui.extraPanels.LinksPanel();
		statistichePanel1 = new gui.extraPanels.StatistichePanel();
		jMRimuoviTemp = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMIExit = new javax.swing.JMenuItem();
		jMStrumenti = new javax.swing.JMenu();
		jMIShowStorico = new javax.swing.JMenuItem();
		jSeparator4 = new javax.swing.JPopupMenu.Separator();
		jMIDelTemp = new javax.swing.JMenuItem();
		jMIDelAll = new javax.swing.JMenuItem();
		jMenu3 = new javax.swing.JMenu();
		jMIAbout = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("GestColl"); // NOI18N

		jToolBar1.setFloatable(false);
		jToolBar1.setRollover(true);

		jBGestMonete.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/img/CoinIcon.png"))); // NOI18N
		jBGestMonete.setMnemonic('M');
		jBGestMonete.setText("Monete"); // NOI18N
		jBGestMonete.setToolTipText("Gestione monete");
		jBGestMonete.setFocusable(false);
		jToolBar1.add(jBGestMonete);

		jBGestLibri.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/img/BookIcon.png"))); // NOI18N
		jBGestLibri.setMnemonic('B');
		jBGestLibri.setText("Biblioteca"); // NOI18N
		jBGestLibri.setFocusable(false);
		jBGestLibri.setMaximumSize(new java.awt.Dimension(85, 37));
		jToolBar1.add(jBGestLibri);

		jBGestContatti.setIcon(new javax.swing.ImageIcon(getClass()
				.getResource("/img/Mail.png"))); // NOI18N
		jBGestContatti.setText("Contatti");
		jBGestContatti.setFocusable(false);
		jBGestContatti.setMaximumSize(new java.awt.Dimension(85, 37));
		jBGestContatti.setMinimumSize(new java.awt.Dimension(85, 37));
		jBGestContatti.setPreferredSize(new java.awt.Dimension(85, 37));
		jToolBar1.add(jBGestContatti);

		jBGestLinks.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/img/internet.png"))); // NOI18N
		jBGestLinks.setText("Link");
		jBGestLinks.setFocusable(false);
		jBGestLinks.setMaximumSize(new java.awt.Dimension(85, 37));
		jBGestLinks.setMinimumSize(new java.awt.Dimension(85, 37));
		jBGestLinks.setPreferredSize(new java.awt.Dimension(85, 37));
		jToolBar1.add(jBGestLinks);

		jBStatistiche.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/img/statistiche.png"))); // NOI18N
		jBStatistiche.setText("Statistiche");
		jBStatistiche.setFocusable(false);
		jBStatistiche
				.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
		jBStatistiche.setMaximumSize(new java.awt.Dimension(85, 37));
		jBStatistiche.setMinimumSize(new java.awt.Dimension(85, 37));
		jToolBar1.add(jBStatistiche);
		jToolBar1.add(jSeparator3);
		jToolBar1.add(jSeparator1);
		jToolBar1.add(jSeparator2);

		jLabel1.setLabelFor(jTFMessages);
		jLabel1.setText("Ultimo messaggio:");
		jToolBar1.add(jLabel1);

		jTFMessages.setColumns(20);
		jTFMessages.setEditable(false);
		jTFMessages.setFont(new java.awt.Font("Monospaced", 0, 12));
		jTFMessages.setLineWrap(true);
		jTFMessages.setRows(2);
		jTFMessages.setTabSize(4);
		jScrollPane1.setViewportView(jTFMessages);

		jToolBar1.add(jScrollPane1);

		jPanel1.setLayout(new java.awt.CardLayout());
		jPanel1.add(monetePanel1, "card2");
		jPanel1.add(libriPanel1, "card3");
		jPanel1.add(contattiPanel1, "card4");
		jPanel1.add(linksPanel1, "card5");
		jPanel1.add(statistichePanel1, "card6");

		jMenu1.setText("File"); // NOI18N

		jMIExit.setText("Esci");
		jMenu1.add(jMIExit);

		jMRimuoviTemp.add(jMenu1);

		jMStrumenti.setText("Strumenti");

		jMIShowStorico.setText("Mostra Storico");
		jMStrumenti.add(jMIShowStorico);
		jMStrumenti.add(jSeparator4);

		jMIDelTemp.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/img/GreenTrashbinIcon.png"))); // NOI18N
		jMIDelTemp.setText("Rimuovi temporanei");
		jMStrumenti.add(jMIDelTemp);

		jMIDelAll.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/img/YellowTrashbinIcon.png"))); // NOI18N
		jMIDelAll.setText("Rimuovi pdf e qr");
		jMStrumenti.add(jMIDelAll);

		jMRimuoviTemp.add(jMStrumenti);

		jMenu3.setText("About..."); // NOI18N

		jMIAbout.setText("Info");
		jMenu3.add(jMIAbout);

		jMRimuoviTemp.add(jMenu3);

		setJMenuBar(jMRimuoviTemp);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
						685, Short.MAX_VALUE)
				.addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE,
						685, Short.MAX_VALUE));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(jToolBar1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jPanel1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										341, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents
		// Variables declaration - do not modify//GEN-BEGIN:variables

	private gui.extraPanels.ContattiPanel contattiPanel1;
	private javax.swing.JButton jBGestContatti;
	private javax.swing.JButton jBGestLibri;
	private javax.swing.JButton jBGestLinks;
	private javax.swing.JButton jBGestMonete;
	private javax.swing.JButton jBStatistiche;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JMenuItem jMIAbout;
	private javax.swing.JMenuItem jMIDelAll;
	private javax.swing.JMenuItem jMIDelTemp;
	private javax.swing.JMenuItem jMIExit;
	private javax.swing.JMenuItem jMIShowStorico;
	private javax.swing.JMenuBar jMRimuoviTemp;
	private javax.swing.JMenu jMStrumenti;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu3;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JToolBar.Separator jSeparator1;
	private javax.swing.JToolBar.Separator jSeparator2;
	private javax.swing.JToolBar.Separator jSeparator3;
	private javax.swing.JPopupMenu.Separator jSeparator4;
	private static javax.swing.JTextArea jTFMessages;
	private javax.swing.JToolBar jToolBar1;
	private gui.extraPanels.BibliotecaPanel libriPanel1;
	private gui.extraPanels.LinksPanel linksPanel1;
	private gui.moneta.MonetePanel monetePanel1;
	private gui.extraPanels.StatistichePanel statistichePanel1;

	// End of variables declaration//GEN-END:variables

	private void loadAllData() {
		// serve la chiamata se no netbeans si incasina...

		Splash.getInstance().splashProgress("Load monete...");
		this.monetePanel1.setupWorks();
		Splash.getInstance().splashProgress("Load monete eseguito");

		Splash.getInstance().splashProgress("Load libri...");
		this.libriPanel1.loadData();
		Splash.getInstance().splashProgress("Load libri eseguito");

		Splash.getInstance().splashProgress("Load links...");
		this.linksPanel1.loadData();
		Splash.getInstance().splashProgress("Load links eseguito");

		Splash.getInstance().splashProgress("Load contatti...");
		// this.contattiPanel1.loadData();
		Splash.getInstance().splashProgress("Load contatti eseguito.");
	}

}
