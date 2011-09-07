/*
 * Modifiche:
 * -
 */

package gui.datamodels;

import javax.swing.table.AbstractTableModel;

/**
 *
 * 
 */
public class VassoioTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int colonne;
	private String[][] dati;
	private int righe;

	/**
     *
     */
	public VassoioTableModel() {
	}

	/**
	 * 
	 * @param righe
	 * @param colonne
	 * @param data
	 */
	public VassoioTableModel(int righe, int colonne, String[][] data) {
		super();
		this.righe = righe;
		this.colonne = colonne;
		this.dati = data;
	}

	/*
	 * JTable uses this method to determine the default renderer/ editor for
	 * each cell.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	@Override
	public int getColumnCount() {
		return colonne;
	}

	@Override
	public String getColumnName(int col) {
		return "" + (col + 1);
	}

	@Override
	public int getRowCount() {
		return righe;
	}

	@Override
	public Object getValueAt(int row, int col) {
		return dati[row][col];
	}

}
