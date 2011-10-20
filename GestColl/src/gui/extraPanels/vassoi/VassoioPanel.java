/*
 * Modifiche:
 * -
 */

package gui.extraPanels.vassoi;

import gestXml.MonetaXml;
import gui.datamodels.VassoioTableModel;
import gui.datamodels.CellRenderer.VassoioCellRenderer;

import javax.swing.JList;
import javax.swing.JTable;

/**
 * 
 */
public class VassoioPanel extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private int colonne;
	private JList elencoMonete;
	private javax.swing.JScrollPane jScrollPane2;
	// End of variables declaration//GEN-END:variables
	private JTable jTable1;

	private int righe;

	/** Creates new form VassoioPanel */
	public VassoioPanel() {
		initComponents();
		jTable1 = null;
	}

	/**
	 * 
	 * @param elencoMonete
	 */
	public VassoioPanel(JList elencoMonete) {

		initComponents();
		jTable1 = null;
		this.elencoMonete = elencoMonete;

	}

	/**
	 * ottiene l'indice della moneta "id" nell'elenco
	 * 
	 * @param id
	 * @return l'indice
	 */
	public int getIndexOf(String id) {
		boolean found = false;
		int i = 0;
		for (i = 0; i < elencoMonete.getModel().getSize(); i++) {
			MonetaXml cur = (MonetaXml) (elencoMonete.getModel()
					.getElementAt(i));

			if (cur.getId().equals(id)) {
				found = true;
				break;
			}

		}
		if (!found) {
			i = -1;
		}
		return i;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		jScrollPane2 = new javax.swing.JScrollPane();

		setLayout(new java.awt.GridBagLayout());
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		add(jScrollPane2, gridBagConstraints);
	}// </editor-fold>//GEN-END:initComponents
		// Variables declaration - do not modify//GEN-BEGIN:variables

	/**
	 * Aggiunge la tabella alla vista e la riempie con i dati. Sistema anche i
	 * listener e i modelli
	 * 
	 * @param righe
	 * @param colonne
	 * @param dati
	 */
	public void riempieDati(int righe, int colonne, String[][] dati) {
		// costruisce la tabella
		this.righe = righe;
		this.colonne = colonne;
		this.jTable1 = new JTable(new VassoioTableModel(righe, colonne, dati));
		this.jTable1
				.setDefaultRenderer(String.class, new VassoioCellRenderer());

		/* setup tabella */
		this.jTable1.setRowHeight(70);
		this.jTable1.setRowSelectionAllowed(true);
		this.jTable1.setColumnSelectionAllowed(true);

		this.jTable1
				.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		this.jScrollPane2.setViewportView(jTable1);
		this.jTable1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			/**
			 * Risponde al doppio clic del mouse sulla casella
			 */
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					int col = jTable1.getSelectedColumn();
					int row = jTable1.getSelectedRow();
					String valore = (String) jTable1.getValueAt(row, col);
					// trovare la corrispondenza fra valore e indice dell'elenco
					// monete
					int indice = getIndexOf(valore);
					if (indice != -1) {
						// effettua la selezione dall'elenco
						elencoMonete.setSelectedIndex(indice);
						// scorre la lista per visualizzare l'indice
						elencoMonete.ensureIndexIsVisible(indice);
					}
				}
			}
		});

	}

	/**
	 * seleziona la cella di coordinate specificate, considerando che le righe
	 * sono messe alla rovescia e sono "1-based"
	 * 
	 * @param row
	 * @param col
	 */
	public void select(int row, int col) {
		this.jTable1.clearSelection();
		// this.jTable1.changeSelection(3, 1, true, true);
		this.jTable1.changeSelection(this.righe - row, col - 1, true, false);
	}
}
