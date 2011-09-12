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

	private JButton buttons[];
	private String buttonsValue[] = { "\u00C4", "\u00D6", "\u00DC", "\u00E4",
			"\u00F6", "\u00FC", "\u00DF", "\u02D9", "\u00B7", "\u2022",
			"\u2042", "\u2043", "\u25C6", "\u25C7", "\u25CA", "\u25CB",
			"\u25CC", "\u25CF", "\u2716", "\u2719", "\u271A", "\u271B",
			"\u271C", "\u2720", "\u2722", "\u2723", "\u2724", "\u2725",
			"\u2726", "\u2727", "\u2729", "\u272A", "\u272B", "\u272C",
			"\u272D", "\u272E", "\u272F", "\u2730", "\u2731", "\u2732",
			"\u2733", "\u2734", "\u2735", "\u2736", "\u2737", "\u2738",
			"\u2739", "\u273A", "\u273B", "\u273C", "\u273D", "\u273E",
			"\u273F", "\u2740", "\u2741", "\u2742", "\u2743", "\u2744",
			"\u2745", "\u2746", "\u2747", "\u2748", "\u2749", "\u274A",
			"\u274B" };

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
	 * This method is called from within the constructor to initialize the form.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 1, 1));
	}// </editor-fold>//GEN-END:initComponents

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

	// Variables declaration - do not modify//GEN-BEGIN:variables
	// End of variables declaration//GEN-END:variables

}
