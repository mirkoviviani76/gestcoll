/*
 * Modifiche:
 * -
 */

package gui.datamodels;

import exceptions.XmlException;
import gestXml.MonetaXml;
import gui.extraPanels.vassoi.VassoioCell;

import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import Resources.i18n.Messages;

import main.Common;
import main.GestLog;

/**
 * Renderer per le celle (caselle) del vassoio.
 * 
 */
public class VassoioCellRenderer extends DefaultTableCellRenderer {
	// This method is called each time a cell in a column
	// using this renderer needs to be rendered.

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     *
     */
	public VassoioCellRenderer() {
		super();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex) {
		// 'value' is value contained in the cell located at
		// (rowIndex, vColIndex)

		VassoioCell vc = new VassoioCell();

		String id = value.toString();
		String contenuto = id;
		// se e' un posto occupato, mostra i dati
		if (!id.equals("")) { //$NON-NLS-1$
			// estrae i dati dall'xml
			String filename = Common.getCommon().getMoneteDir() + "/" + id //$NON-NLS-1$
					+ "/" + id + ".xml"; //$NON-NLS-1$ //$NON-NLS-2$
			MonetaXml mng;
			try {
				mng = new MonetaXml(new File(filename));
				contenuto = mng.getPaese() + "<br>" //$NON-NLS-1$
						+ mng.getNominale().getValore() + " " //$NON-NLS-1$
						+ mng.getNominale().getValuta() + " (" + mng.getAnno() //$NON-NLS-1$
						+ ")"; //$NON-NLS-1$
			} catch (XmlException e) {
				GestLog.Error(this.getClass(), e);
			}
		}

		if (hasFocus || isSelected) {
			vc.evidenzia(Color.yellow);
		} else {
			vc.evidenzia(Color.white);
		}

		// mette i dati
		vc.setText(contenuto);
		// mette il tooltip
		vc.setToolTipText(Messages.getString("VassoioCellRenderer.0")); //$NON-NLS-1$

		// Since the renderer is a component, return itself
		return vc;
	}

}
