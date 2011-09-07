/*
 * Modifiche:
 * -
 */

package gui.extraPanels;

import exceptions.XmlException;
import gestXml.BibliotecaXml;
import gestXml.data.Pubblicazione;

import java.awt.Font;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ProgressMonitor;

import main.GestLog;
import main.Progress;

/**
 * Gestione pannello biblioteca
 * 
 * @author intecs
 * 
 */
public class BibliotecaPanel extends javax.swing.JPanel implements Observer,
		ClipboardOwner {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BibliotecaXml biblioteca;
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private gui.extraPanels.BibliotecaViewer bibliotecaViewer1;

	private javax.swing.JList jLBiblioteca;

	private javax.swing.JPanel jPanel1;

	private javax.swing.JScrollPane jScrollPane1;

	private javax.swing.JSplitPane jSplitPane1;

	private javax.swing.JToolBar jToolBar1;
	// End of variables declaration//GEN-END:variables
	private ProgressMonitor pm;

	/** Creates new form BibliotecaPanel */
	public BibliotecaPanel() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jToolBar1 = new javax.swing.JToolBar();
		jPanel1 = new javax.swing.JPanel();
		jSplitPane1 = new javax.swing.JSplitPane();
		jScrollPane1 = new javax.swing.JScrollPane();
		jLBiblioteca = new javax.swing.JList();
		jLBiblioteca.setFont(new Font("Tahoma", Font.PLAIN, 11));
		bibliotecaViewer1 = new gui.extraPanels.BibliotecaViewer();

		jToolBar1.setFloatable(false);
		jToolBar1.setRollover(true);

		jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1,
				javax.swing.BoxLayout.LINE_AXIS));

		jScrollPane1.setMaximumSize(new java.awt.Dimension(50, 32767));

		jLBiblioteca.setModel(new javax.swing.AbstractListModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String[] strings = { "a" };

			@Override
			public Object getElementAt(int i) {
				return strings[i];
			}

			@Override
			public int getSize() {
				return strings.length;
			}
		});
		jLBiblioteca
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		jLBiblioteca
				.setCellRenderer(new gui.datamodels.GenericCellRenderer<Pubblicazione>());
		jLBiblioteca
				.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
					@Override
					public void valueChanged(
							javax.swing.event.ListSelectionEvent evt) {
						jLBibliotecaValueChanged(evt);
					}
				});
		jScrollPane1.setViewportView(jLBiblioteca);

		jSplitPane1.setLeftComponent(jScrollPane1);
		jSplitPane1.setRightComponent(bibliotecaViewer1);

		jPanel1.add(jSplitPane1);
		jSplitPane1.setDividerLocation(250);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE,
						431, Short.MAX_VALUE)
				.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
						431, Short.MAX_VALUE));
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
										270, Short.MAX_VALUE)));
	}// </editor-fold>//GEN-END:initComponents

	private void jLBibliotecaValueChanged(
			javax.swing.event.ListSelectionEvent evt) {// GEN-FIRST:event_jLBibliotecaValueChanged
		Pubblicazione content = (Pubblicazione) this.jLBiblioteca
				.getSelectedValue();
		// NB se la stampa viene fatta due volte, non importa: e' ok.
		this.bibliotecaViewer1.setDati(content.toHtmlString(),
				content.getFilename());
	}// GEN-LAST:event_jLBibliotecaValueChanged

	/**
	 * serve perche' NetBeans (non java!) si incasina per il MainForm
	 */
	public void loadData() {
		// sld = new ScanLibri();
		pm = new ProgressMonitor(this, "", "", 0, 100);
		// sld.addObserver(this);
		if (this.biblioteca == null) {
			try {
				biblioteca = new BibliotecaXml();
			} catch (XmlException e) {
				GestLog.Error(this.getClass(), e);
			}
			// setta il modello
			this.jLBiblioteca
					.setModel(new gui.datamodels.GenericListModel<Pubblicazione>(
							this.biblioteca.getItems()));
		}
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		// non deve far niente
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Progress) {
			Progress p = (Progress) (arg);
			this.pm.setMaximum(p.getMax());
			this.pm.setProgress(p.getCurrent());
			this.pm.setNote(p.getMsg());
		} else {
			GestLog.Error(BibliotecaPanel.class, "update",
					"Tipo di argomento non gestito.");
		}
	}
}
