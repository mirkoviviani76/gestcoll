package gui.datamodels.CellRenderer;

import XmlData.Moneta.Legenda;

public class LegendaCellRenderer extends GenericCellRenderer<Legenda> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getText(Legenda mng) {
		return mng.getTesto();
	}
	
	@Override
	public String getTooltip(Legenda mng) {
		return mng.getScioglimento();
	}

	

	
}
