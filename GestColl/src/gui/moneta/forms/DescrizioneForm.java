/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DescrizioneForm.java
 *
 * Created on 11-feb-2011, 11.20.58
 */

package gui.moneta.forms;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * 
 * @author intecs
 */
@SuppressWarnings("serial")
public final class DescrizioneForm extends javax.swing.JDialog {
	/** A return status code - returned if Cancel button has been pressed */
	public static final int RET_CANCEL = 0;
	/** A return status code - returned if OK button has been pressed */
	public static final int RET_OK = 1;

	/** Creates new form DescrizioneForm */
	public DescrizioneForm(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		this.editButtonsPanel1.setupEditButtons(this.jTADescrizione);
	}

	/** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
	public int getReturnStatus() {
		return returnStatus;
	}

	public void setData(String s) {
		this.jTADescrizione.setText(s);
	}

	public void setImg(String filename) {
		this.jLImg.setIcon(null);
		/* i null servono per accelerare il garbage collection (verificare) */
		Image imgD = null;
		ImageIcon imgD_little = null;
		imgD = new ImageIcon(filename).getImage();
		// si da priorita' alla velocita'
		imgD_little = new ImageIcon(imgD.getScaledInstance(400, 400,
				Image.SCALE_FAST));
		this.jLImg.setIcon(imgD_little);
	}

	public String getData() {
		return this.jTADescrizione.getText();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		jPanel1 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTADescrizione = new javax.swing.JTextArea();
		editButtonsPanel1 = new gui.moneta.forms.EditButtonsPanel();
		jPanel2 = new javax.swing.JPanel();
		okButton = new javax.swing.JButton();
		cancelButton = new javax.swing.JButton();
		jPanel3 = new javax.swing.JPanel();
		jLImg = new javax.swing.JLabel();

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent evt) {
				closeDialog(evt);
			}
		});
		getContentPane().setLayout(new java.awt.GridBagLayout());

		jScrollPane1.setAutoscrolls(true);

		jTADescrizione.setColumns(20);
		jTADescrizione.setLineWrap(true);
		jTADescrizione.setRows(5);
		jTADescrizione.setWrapStyleWord(true);
		jScrollPane1.setViewportView(jTADescrizione);

		editButtonsPanel1.setLayout(new java.awt.FlowLayout(
				java.awt.FlowLayout.LEFT, 1, 1));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jScrollPane1,
						javax.swing.GroupLayout.DEFAULT_SIZE, 803,
						Short.MAX_VALUE)
				.addComponent(editButtonsPanel1,
						javax.swing.GroupLayout.PREFERRED_SIZE, 506,
						javax.swing.GroupLayout.PREFERRED_SIZE));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												122, Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												editButtonsPanel1,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												184, Short.MAX_VALUE)
										.addContainerGap()));

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 0.5;
		gridBagConstraints.weighty = 1.0;
		getContentPane().add(jPanel1, gridBagConstraints);

		okButton.setText("OK");
		okButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				okButtonActionPerformed(evt);
			}
		});

		cancelButton.setText("Cancel");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout
				.setHorizontalGroup(jPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 320, Short.MAX_VALUE)
						.addGroup(
								jPanel2Layout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												jPanel2Layout
														.createSequentialGroup()
														.addGap(0, 0,
																Short.MAX_VALUE)
														.addComponent(
																okButton,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																67,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGap(186, 186, 186)
														.addComponent(
																cancelButton)
														.addGap(0, 0,
																Short.MAX_VALUE))));
		jPanel2Layout
				.setVerticalGroup(jPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGap(0, 23, Short.MAX_VALUE)
						.addGroup(
								jPanel2Layout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												jPanel2Layout
														.createSequentialGroup()
														.addGap(0, 0,
																Short.MAX_VALUE)
														.addGroup(
																jPanel2Layout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																		.addComponent(
																				okButton)
																		.addComponent(
																				cancelButton))
														.addGap(0, 0,
																Short.MAX_VALUE))));

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		getContentPane().add(jPanel2, gridBagConstraints);

		jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3,
				javax.swing.BoxLayout.LINE_AXIS));
		jPanel3.add(jLImg);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 0.5;
		gridBagConstraints.weighty = 1.0;
		getContentPane().add(jPanel3, gridBagConstraints);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	/** Closes the dialog */
	private void closeDialog(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_closeDialog
		doClose(RET_CANCEL);
	}// GEN-LAST:event_closeDialog

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelButtonActionPerformed
		doClose(RET_CANCEL);
	}// GEN-LAST:event_cancelButtonActionPerformed

	private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_okButtonActionPerformed
		doClose(RET_OK);
	}// GEN-LAST:event_okButtonActionPerformed

	private void doClose(int retStatus) {
		returnStatus = retStatus;
		setVisible(false);
		dispose();
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton cancelButton;
	private gui.moneta.forms.EditButtonsPanel editButtonsPanel1;
	private javax.swing.JLabel jLImg;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextArea jTADescrizione;
	private javax.swing.JButton okButton;
	// End of variables declaration//GEN-END:variables

	private int returnStatus = RET_CANCEL;
}
