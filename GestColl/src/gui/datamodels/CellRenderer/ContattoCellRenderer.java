package gui.datamodels.CellRenderer;

import gestXml.data.Contatto;

public class ContattoCellRenderer extends GenericCellRenderer<Contatto> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getText(Contatto mng) {
				return mng.getNome() + ": " + mng.getEmail() + " " + mng.getNote();
	}

	@Override
	public String getTooltip(Contatto mng) {
		return null;
	}
	
	
}
