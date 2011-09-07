/*
 * Modifiche:
 * -
 */

package gui.datamodels;

import exceptions.XmlException;
import gestXml.MonetaXml;
import gui.extraPanels.VassoioCell;

import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.xml.bind.JAXBException;

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
		if (!id.equals("")) {
			// estrae i dati dall'xml
			String filename = Common.getCommon().getMoneteDir() + "/" + id + "/" + id + ".xml";
			MonetaXml mng;
			try {
				mng = new MonetaXml(new File(filename));
				contenuto = mng.getPaese() + "<br>"
						+ mng.getNominale().getValore() + " "
						+ mng.getNominale().getValuta() + " (" + mng.getAnno()
						+ ")";
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
		vc.setToolTipText("Doppio clic per vedere la moneta");

		// Since the renderer is a component, return itself
		return vc;
	}

}
