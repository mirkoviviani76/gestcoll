package gui.datamodels.CellRenderer;

import XmlData.Moneta.Zecchiere;


public class ZecchiereCellRenderer extends GenericCellRenderer<Zecchiere> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getText(Zecchiere mng) {
		return (mng.getNome() != null ? "" + mng.getNome() + ", " : "") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ (mng.getSegno() != null ? " [" + mng.getSegno() + "] " : "") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ (mng.getRuolo() != null ? " " + mng.getRuolo() : "") + ""; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Override
	public String getTooltip(Zecchiere mng) {
		return null;
	}
	

	
}
