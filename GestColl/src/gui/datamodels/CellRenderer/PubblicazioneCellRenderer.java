package gui.datamodels.CellRenderer;

import gestXml.data.Pubblicazione;

public class PubblicazioneCellRenderer extends GenericCellRenderer<Pubblicazione> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getText(Pubblicazione mng) {
		return mng.getText();
	}

	public String getTooltip(Pubblicazione mng) {
		return null;
	}
	
	
}
