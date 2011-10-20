/*
 * Modifiche:
 * -
 */
package gui.datamodels.CellRenderer;



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
public abstract class GenericCellRenderer<T> extends DefaultListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public abstract String getText(T mng);
	
	public abstract String getTooltip(T mng);
	
	/**
	 * 
	 * @param list
	 * @param value
	 * @param index
	 * @param isSelected
	 * @param hasFocus
	 * @return il componente
	 */
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean hasFocus) {
		// ottiene la label di default
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value,
				index, isSelected, hasFocus);
		@SuppressWarnings("unchecked")
		T mng = (T) value;
		label.setText(this.getText(mng));
		if (this.getTooltip(mng) != null)
			label.setToolTipText(this.getTooltip(mng));
		
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
