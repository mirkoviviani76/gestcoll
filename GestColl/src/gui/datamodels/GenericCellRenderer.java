/*
 * Modifiche:
 * -
 */
package gui.datamodels;

import gestXml.Tooltipper;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 * 
 * 
 * @param <E>
 */
// public class GenericCellRenderer<E extends Tooltipper> extends
// DefaultListCellRenderer {
public class GenericCellRenderer<E> extends DefaultListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param list
	 * @param value
	 * @param index
	 * @param isSelected
	 * @param hasFocus
	 * @return
	 */
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean hasFocus) {
		// ottiene la label di default
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value,
				index, isSelected, hasFocus);
		@SuppressWarnings("unchecked")
		E mng = (E) value;
		label.setText(mng.toString());
		// se E implementa Tooltipper, ottiene il tooltip
		if (mng instanceof Tooltipper) {
			label.setToolTipText(((Tooltipper) mng).toTooltip());
		}

		if (hasFocus || isSelected) {
			label.setBackground(Color.yellow);
		} else {
			// colora il background a zebra
			label.setBackground((index % 2 == 0) ? Color.LIGHT_GRAY
					: Color.WHITE);
		}

		return label;

	}

}
