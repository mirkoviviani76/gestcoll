/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * EditButtonsPanel.java
 *
 * Created on 21-mar-2011, 10.44.29
 */

package gui.moneta.forms;

import java.awt.Font;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JTextArea;

/**
 * 
 * @author intecs
 */
@SuppressWarnings("serial")
public class EditButtonsPanel extends javax.swing.JPanel {

	private String buttonsValue[] = { "Ã„", "Ã–", "Ãœ", "Ã¤", "Ã¶", "Ã¼", "ÃŸ", "Ë™",
			"Â·", "â€¢", "â�‚", "â�ƒ", "â—†", "â—‡", "â—Š", "â—‹", "â—Œ", "â—�", "âœ–", "âœ™", "âœš",
			"âœ›", "âœœ", "âœ ", "âœ¢", "âœ£", "âœ¤", "âœ¥", "âœ¦", "âœ§", "âœ©", "âœª", "âœ«", "âœ¬",
			"âœ­", "âœ®", "âœ¯", "âœ°", "âœ±", "âœ²", "âœ³", "âœ´", "âœµ", "âœ¶", "âœ·", "âœ¸", "âœ¹",
			"âœº", "âœ»", "âœ¼", "âœ½", "âœ¾", "âœ¿", "â�€", "â��", "â�‚", "â�ƒ", "â�„", "â�…", "â�†",
			"â�‡", "â�ˆ", "â�‰", "â�Š", "â�‹" };
	private JButton buttons[];

	/**
	 * Gestore del click su un pulsante: inserisce il testo del pulsante nel
	 * testo della descrizione alla posizione del cursore.
	 * 
	 * @param evt
	 */
	private void editButtonClicked(MouseEvent evt, JTextArea target) {
		AbstractButton ab = (AbstractButton) evt.getSource();
		// estrae il testo
		String text = ab.getText();
		// inserisce il testo nella descrizione dove si trova il cursore
		target.insert(text, target.getCaretPosition());
		// assegna il focus al target
		target.requestFocus();
	}

	/**
	 * Aggiunge il gestore dei pulsanti
	 */
	public void setupEditButtons(final JTextArea target) {
		for (int i = 0; i < this.buttonsValue.length; i++) {
			this.buttons[i].addMouseListener(new java.awt.event.MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent evt) {
					editButtonClicked(evt, target);
				}
			});
		}
	}

	/** Creates new form EditButtonsPanel */
	public EditButtonsPanel() {
		initComponents();
		/* aggiunge i pulsanti */
		this.buttons = new JButton[this.buttonsValue.length];
		for (int i = 0; i < this.buttonsValue.length; i++) {
			this.buttons[i] = new JButton(this.buttonsValue[i]);
			Font f = new Font(Font.MONOSPACED, Font.PLAIN, 11);
			this.buttons[i].setFont(f);
			this.buttons[i].setSize(50, 50);
			this.add(this.buttons[i]);
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 1, 1));
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	// End of variables declaration//GEN-END:variables

}
