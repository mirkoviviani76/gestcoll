/*
 * Modifiche:
 * -
 */
package gui.extraPanels.contatti;

import gestXml.data.Contatto;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


/**
 * Una finestra di dialogo per aggiungere un contatto
 * 
 */
@SuppressWarnings("serial")
public class NuovoContattoDialog extends javax.swing.JDialog {
	/** A return status code - returned if Cancel button has been pressed */
	public static final int RET_CANCEL = 0;
	/** A return status code - returned if OK button has been pressed */
	public static final int RET_OK = 1;

	private int returnStatus = RET_CANCEL;
	private JTextField jTFName;
	private JTextField jTFEmail;
	private JTextField jTFNote;
	private JPanel panel;
	private JButton okbutton;
	private JButton cancelbutton;

	/**
	 * Creates new form
	 * 
	 * @param parent
	 * @param modal
	 */
	public NuovoContattoDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setMinimumSize(new Dimension(300, 150));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{27, 86, 24, 0};
		gridBagLayout.rowHeights = new int[]{20, 33, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNome = new JLabel("Nome");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.WEST;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 0;
		getContentPane().add(lblNome, gbc_lblNome);
		
		jTFName = new JTextField();
		GridBagConstraints gbc_jTFName = new GridBagConstraints();
		gbc_jTFName.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTFName.anchor = GridBagConstraints.NORTH;
		gbc_jTFName.insets = new Insets(0, 0, 5, 5);
		gbc_jTFName.gridx = 1;
		gbc_jTFName.gridy = 0;
		getContentPane().add(jTFName, gbc_jTFName);
		jTFName.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 1;
		getContentPane().add(lblEmail, gbc_lblEmail);
		
		jTFEmail = new JTextField();
		GridBagConstraints gbc_jTFEmail = new GridBagConstraints();
		gbc_jTFEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTFEmail.anchor = GridBagConstraints.NORTH;
		gbc_jTFEmail.insets = new Insets(0, 0, 5, 5);
		gbc_jTFEmail.gridx = 1;
		gbc_jTFEmail.gridy = 1;
		getContentPane().add(jTFEmail, gbc_jTFEmail);
		jTFEmail.setColumns(10);
		
		JLabel lblNote = new JLabel("Note");
		GridBagConstraints gbc_lblNote = new GridBagConstraints();
		gbc_lblNote.anchor = GridBagConstraints.WEST;
		gbc_lblNote.insets = new Insets(0, 0, 5, 5);
		gbc_lblNote.gridx = 0;
		gbc_lblNote.gridy = 2;
		getContentPane().add(lblNote, gbc_lblNote);
		
		jTFNote = new JTextField();
		GridBagConstraints gbc_jTFNote = new GridBagConstraints();
		gbc_jTFNote.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTFNote.anchor = GridBagConstraints.NORTH;
		gbc_jTFNote.insets = new Insets(0, 0, 5, 5);
		gbc_jTFNote.gridx = 1;
		gbc_jTFNote.gridy = 2;
		getContentPane().add(jTFNote, gbc_jTFNote);
		jTFNote.setColumns(10);
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel.gridwidth = 2;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 3;
		getContentPane().add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		okbutton = new JButton("Aggiungi");
		okbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aevt) {
				okButtonActionPerformed(aevt);
			}
		});
		panel.add(okbutton);
		
		cancelbutton = new JButton("Cancella");
		cancelbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aevt) {
				cancelButtonActionPerformed(aevt);
			}
		});
		panel.add(cancelbutton);
		initComponents();
	}
	
	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelButtonActionPerformed
		doClose(RET_CANCEL);
	}
	
	/** Closes the dialog */
	private void closeDialog(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_closeDialog
		doClose(RET_CANCEL);
	}

	private void doClose(int retStatus) {
		returnStatus = retStatus;
		setVisible(false);
		dispose();
	}

	/**
	 * 
	 * @return il contatto
	 */
	public Contatto getData() {
		Contatto n = new Contatto(this.jTFName.getText(),
				this.jTFEmail.getText(),
				this.jTFNote.getText());
		return n;
	}
	/** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
	public int getReturnStatus() {
		return returnStatus;
	}
	/**
	 * This method is called from within the constructor to initialize the form.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		setTitle("Aggiungi Contatto");
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent evt) {
				closeDialog(evt);
			}
		});


	}// </editor-fold>//GEN-END:initComponents
	private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_okButtonActionPerformed
		doClose(RET_OK);
	}// GEN-LAST:event_okButtonActionPerformed

}
