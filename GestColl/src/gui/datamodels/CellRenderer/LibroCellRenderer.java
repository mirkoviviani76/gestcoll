package gui.datamodels.CellRenderer;

import XmlData.Moneta.Libro;


public class LibroCellRenderer extends GenericCellRenderer<Libro> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getText(Libro mng) {
		return (mng.getSigla() != null ? "" + mng.getSigla() + " " : "") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ (mng.getNumero() != null ? "" + mng.getNumero() : "") + ""; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	}

	@Override
	public String getTooltip(Libro mng) {
		return null;
	}

	
}
