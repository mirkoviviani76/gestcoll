/*
 * Modifiche:
 * -
 */

package gui.extraPanels.links;

import exceptions.XmlException;
import gestXml.LinksXml;
import gestXml.data.Contatto;
import gestXml.data.Link;
import gui.MainFrame;
import gui.datamodels.GenericListModel;
import gui.extraPanels.contatti.NuovoContattoDialog;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import main.Common;
import main.GenericUtil;
import main.GestLog;
import main.History;
import main.Message;

/**
 * gestione pannello links
 * 
 * @author intecs
 * 
 */
public class LinksPanel extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	private javax.swing.JButton jBAggiungi;
	private javax.swing.JPanel jPanel1;

	private javax.swing.JToolBar jToolBar1;

	private DefaultMutableTreeNode rootNode;

	private JScrollPane scrollPane;

	private JTextPane textPane;

	private JTree tree;

	private DefaultTreeModel treeModel;
	
	private LinksXml links;


	/** Creates new form LinksPanel 
	 * @throws XmlException */
	public LinksPanel() {
		initComponents();

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

	/**
	 * gestisce la selezione di un nodo link
	 * 
	 * @param e
	 *            l'evento
	 */
	protected void gestSelectedNode(TreeSelectionEvent e) {
		Link currLink = this.getSelection();
		if (currLink != null)
			this.textPane.setText(currLink.toHtml());
		else
			this.textPane.setText("");
	}

	/**
	 * ottiene l'oggetto selezionato o null
	 * @return
	 */
	protected Link getSelection()
	{
		Link ret = null;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();

		if (node == null)
			// Nothing is selected.
			return null;

		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
			ret = (Link) nodeInfo;
		}
		
		return ret;
	}

	/**
	 * ottiene la categoria selezionata (o null se non e' stata selezionata ne'
	 * la categoria ne' un link
	 * @return la stringa con la categoria
	 */
	protected String getCategory() {
		String ret = null;
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
				.getLastSelectedPathComponent();

		if (node == null)
			// Nothing is selected.
			return null;

		Object nodeInfo = node.getUserObject();
		if (node.getLevel() == 1) //le categorie sono a livello 1
		{
			String curr = (String) nodeInfo;
			ret = curr;
		}
		if (node.isLeaf()) //e' un link 
		{
			Link curr = (Link) nodeInfo;
			ret = curr.categoria;
		}
		
		return ret;
		
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		jPanel1 = new javax.swing.JPanel();
		jToolBar1 = new javax.swing.JToolBar();
		jBAggiungi = new javax.swing.JButton();

		setLayout(new java.awt.GridBagLayout());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weighty = 1.0;
		add(jPanel1, gridBagConstraints);
		jPanel1.setLayout(new GridLayout(0, 2, 0, 0));

		scrollPane = new JScrollPane();
		jPanel1.add(scrollPane);

		tree = new JTree();
		scrollPane.setViewportView(tree);
		tree.setSize(new Dimension(1, 1));

		textPane = new JTextPane();
		textPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		textPane.setEditable(false);
		textPane.setContentType("text/html");
		textPane.addHyperlinkListener(new HyperlinkListener() {

			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				followLink(e);
			}
		});

		jPanel1.add(textPane);

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

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = 347;
		gridBagConstraints.ipady = 10;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		add(jToolBar1, gridBagConstraints);
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				gestSelectedNode(e);
			}
		});
	}

	/**
	 * gestore click sul pulsnte aggiungi
	 * @param evt
	 */
	private void jBAggiungiMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jBAggiungiMouseClicked
		/* apre il dialog */
		NuovoLinkDialog dialog = new NuovoLinkDialog(null, true);
		String cat = this.getCategory();
		//controlla la selezione di un nodo
		if (cat == null) {
			JOptionPane.showMessageDialog(null, "Devi selezionare una categoria", Common.APPNAME,
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		dialog.setCategoria(cat);
		dialog.setVisible(true);
		// valuta ritorno con tasto "ok/modifica" premuto
		if (dialog.getReturnStatus() == NuovoLinkDialog.RET_OK) {
			// ottiene i nuovi dati della nota dal form
			Link nuovo = dialog.getData();
			try {
				this.links.add(nuovo);
				//scrive l'xml
				this.links.writeXml(this.links.getJaxbObject(), "XmlData.Links",
						Common.getCommon().getLinksXml());
				// carica i nuovi valori
				this.loadData();
			} catch (XmlException e) {
				GestLog.Error(this.getClass(), e);
			}
			/* logga in vario modo */
			History.addEvent(History.MODIFY, Common.getCommon().getLinksXml());
			String msg = "MODIFICATO con successo: " + Common.getCommon().getLinksXml();
			MainFrame.setMessage(new Message(msg, Level.INFO));
		}
		

	}
	
	/**
	 * carica i dati nella vista
	 * @throws XmlException 
	 */
	public void loadData() throws XmlException {
		//(ri)legge l'xml
		links = new LinksXml();
		// mappa per ottenere i nodi
		Map<String, DefaultMutableTreeNode> nodiCategorie = new HashMap<String, DefaultMutableTreeNode>();

		rootNode = new DefaultMutableTreeNode("Links");
		treeModel = new DefaultTreeModel(rootNode);

		/* cicla su tutti i link */
		List<Link> lista = links.getLinks();
		for (Link l : lista) {
			/* gestisce le categorie */
			DefaultMutableTreeNode target;
			if (!nodiCategorie.containsKey(l.categoria)) {
				DefaultMutableTreeNode c = new DefaultMutableTreeNode(
						l.categoria);
				rootNode.add(c);
				nodiCategorie.put(l.categoria, c);
				target = c;
			} else {
				target = nodiCategorie.get(l.categoria);
			}
			// aggiunge il link all'albero
			DefaultMutableTreeNode currItem = new DefaultMutableTreeNode(l);
			target.add(currItem);
		}
		this.tree.setModel(treeModel);
	}
}
