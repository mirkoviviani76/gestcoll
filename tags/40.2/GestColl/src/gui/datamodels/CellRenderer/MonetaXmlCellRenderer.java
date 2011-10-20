package gui.datamodels.CellRenderer;

import gestXml.MonetaXml;


public class MonetaXmlCellRenderer extends GenericCellRenderer<MonetaXml> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getText(MonetaXml mng) {
		return String.format("%s, %s", mng.getId(), mng.getPaese()); //$NON-NLS-1$

	}

	@Override
	public String getTooltip(MonetaXml mng) {
		String tooltip = ""; //$NON-NLS-1$
		// imgD = mng.getImg(MonetaXml.lato.DRITTO);
		tooltip = String
				.format("%s %s revisione %s", mng.getNominale().getValore(),  //$NON-NLS-1$
						mng.getNominale().getValuta(),
						mng.getRevisione());
		return tooltip;
	}
	
	
}
