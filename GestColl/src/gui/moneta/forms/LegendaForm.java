/*
 * Modifiche:
 * -
 */

package gui.moneta.forms;

/**
 *
 * 
 */
public final class LegendaForm extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** A return status code - returned if Cancel button has been pressed */
	public static final int RET_CANCEL = 0;
	/** A return status code - returned if OK button has been pressed */
	public static final int RET_OK = 1;

	/**
	 * Creates new form LegendaForm
	 * 
	 * @param parent
	 * @param modal
	 */
	public LegendaForm(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		this.editButtonsPanel1.setupEditButtons(this.jTALegenda);
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

		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTALegenda = new javax.swing.JTextArea();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTAScioglimento = new javax.swing.JTextArea();
		okButton = new javax.swing.JButton();
		cancelButton = new javax.swing.JButton();
		editButtonsPanel1 = new gui.moneta.forms.EditButtonsPanel();

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent evt) {
				closeDialog(evt);
			}
		});

		jPanel1.setLayout(new java.awt.GridBagLayout());

		jLabel1.setText("Legenda");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.ipadx = 18;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel1.add(jLabel1, gridBagConstraints);

		jLabel2.setText("Scioglimento");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel1.add(jLabel2, gridBagConstraints);

		jTALegenda.setColumns(20);
		jTALegenda.setRows(5);
		jScrollPane1.setViewportView(jTALegenda);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 297;
		gridBagConstraints.ipady = 124;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		jPanel1.add(jScrollPane1, gridBagConstraints);

		jTAScioglimento.setColumns(20);
		jTAScioglimento.setLineWrap(true);
		jTAScioglimento.setRows(5);
		jTAScioglimento.setWrapStyleWord(true);
		jScrollPane2.setViewportView(jTAScioglimento);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 297;
		gridBagConstraints.ipady = 124;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		jPanel1.add(jScrollPane2, gridBagConstraints);

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

		editButtonsPanel1.setLayout(new java.awt.FlowLayout(
				java.awt.FlowLayout.LEFT, 1, 1));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		editButtonsPanel1,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		380,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addContainerGap())
												.addGroup(
														layout.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
																.addGroup(
																		layout.createSequentialGroup()
																				.addComponent(
																						jPanel1,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						380,
																						Short.MAX_VALUE)
																				.addContainerGap())
																.addGroup(
																		javax.swing.GroupLayout.Alignment.TRAILING,
																		layout.createSequentialGroup()
																				.addComponent(
																						okButton,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						67,
																						javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addGap(18,
																						18,
																						18)
																				.addComponent(
																						cancelButton)
																				.addGap(110,
																						110,
																						110))))));

		layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { cancelButton, okButton });

		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jPanel1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										251,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(editButtonsPanel1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										184, Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(okButton)
												.addComponent(cancelButton))
								.addContainerGap()));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_okButtonActionPerformed
		doClose(RET_OK);
	}// GEN-LAST:event_okButtonActionPerformed

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelButtonActionPerformed
		doClose(RET_CANCEL);
	}// GEN-LAST:event_cancelButtonActionPerformed

	/** Closes the dialog */
	private void closeDialog(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_closeDialog
		doClose(RET_CANCEL);
	}// GEN-LAST:event_closeDialog

	private void doClose(int retStatus) {
		returnStatus = retStatus;
		setVisible(false);
		dispose();
	}

	/**
	 * 
	 * @param item
	 */
	public void setData(XmlData.Moneta.Legenda item) {
		if (item != null) {
			this.jTALegenda.setText(item.getTesto());
			this.jTAScioglimento.setText(item.getScioglimento());
		}
	}

	/**
	 * 
	 * @return
	 */
	public XmlData.Moneta.Legenda getData() {
		String[] ret = new String[2];
		ret[0] = this.jTALegenda.getText();
		ret[1] = this.jTAScioglimento.getText();
		XmlData.Moneta.Legenda l = new XmlData.Moneta.Legenda();
		l.setTesto(ret[0]);
		l.setScioglimento(ret[1]);
		return l;
	}

	/**
	 * 
	 * @param flag
	 */
	public void setEditable(boolean flag) {
		this.jTALegenda.setEditable(flag);
		this.jTAScioglimento.setEditable(flag);
		/* cambia il testo del bottone ok */
		if (flag) {
			this.okButton.setText("Modifica");
		} else {
			this.okButton.setText("OK");
		}
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton cancelButton;
	private gui.moneta.forms.EditButtonsPanel editButtonsPanel1;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JTextArea jTALegenda;
	private javax.swing.JTextArea jTAScioglimento;
	private javax.swing.JButton okButton;
	// End of variables declaration//GEN-END:variables

	private int returnStatus = RET_CANCEL;
}
