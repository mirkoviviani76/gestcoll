/*
 * Modifiche:
 * -
 */

package gui.moneta.forms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.Common;
import works.CollectionWorker;

/**
 * 
 * @author furetto76
 */
@SuppressWarnings("serial")
public class AddMonetaForm extends javax.swing.JDialog implements
		DocumentListener, ActionListener {
	/** A return status code - returned if Cancel button has been pressed */
	public static final int RET_CANCEL = 0;
	/** A return status code - returned if OK button has been pressed */
	public static final int RET_OK = 1;

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton cancelButton;

	private javax.swing.JComboBox jCBDim;

	private javax.swing.JLabel jLabel1;

	private javax.swing.JLabel jLabel2;

	private javax.swing.JLabel jLabel3;

	private javax.swing.JLabel jLabel4;

	private javax.swing.JPanel jPanel1;

	private javax.swing.JTextField jTFAnno;

	private javax.swing.JTextField jTFId;

	private javax.swing.JTextField jTFProgressivo;

	private javax.swing.JButton okButton;
	// End of variables declaration//GEN-END:variables
	private int returnStatus = RET_CANCEL;
	private List<File> tuttiXmlMonete;

	/**
	 * Creates new form AddMonetaForm
	 * 
	 * @param parent
	 * @param modal
	 */
	public AddMonetaForm(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		// carica i file xml delle monete
		this.tuttiXmlMonete = CollectionWorker.getFileListing(Common
				.getCommon().getMoneteDir(), Common.COIN_END);
		// aggiunge i listener
		this.jTFAnno.getDocument().addDocumentListener(this);
		this.jCBDim.addActionListener(this);
		// effettua un primo aggiornamento
		this.updateData();
	}

	/**
	 * 
	 * @param ae
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == this.jCBDim) {
			this.updateData();
		}
	}

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelButtonActionPerformed
		doClose(RET_CANCEL);
	}// GEN-LAST:event_cancelButtonActionPerformed

	/**
	 * 
	 * @param de
	 */
	@Override
	public void changedUpdate(DocumentEvent de) {
		this.updateData();
	}

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
	 * @return l'id calcolato della nuova moneta
	 */
	public String getId() {
		return this.jTFAnno.getText() + "-" + this.jCBDim.getSelectedItem()
				+ "-" + this.jTFProgressivo.getText();
	}

	/**
	 * @return the return status of this dialog - one of RET_OK or RET_CANCEL
	 */
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

		okButton = new javax.swing.JButton();
		cancelButton = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jTFAnno = new javax.swing.JTextField();
		jLabel2 = new javax.swing.JLabel();
		jCBDim = new javax.swing.JComboBox();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jTFProgressivo = new javax.swing.JTextField();
		jTFId = new javax.swing.JTextField();

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent evt) {
				closeDialog(evt);
			}
		});

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

		jPanel1.setLayout(new java.awt.GridBagLayout());

		jLabel1.setText("Anno");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel1.add(jLabel1, gridBagConstraints);

		jTFAnno.setColumns(4);
		jTFAnno.setText("16XX");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 66;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		jPanel1.add(jTFAnno, gridBagConstraints);

		jLabel2.setText("Dimensione");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel1.add(jLabel2, gridBagConstraints);

		jCBDim.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
				"A", "B", "C", "D" }));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		jPanel1.add(jCBDim, gridBagConstraints);

		jLabel3.setText("Progressivo");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new java.awt.Insets(25, 0, 0, 0);
		jPanel1.add(jLabel3, gridBagConstraints);

		jLabel4.setText("ID completo");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel1.add(jLabel4, gridBagConstraints);

		jTFProgressivo.setEditable(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 66;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		gridBagConstraints.insets = new java.awt.Insets(25, 0, 0, 0);
		jPanel1.add(jTFProgressivo, gridBagConstraints);

		jTFId.setEditable(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 66;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		jPanel1.add(jTFId, gridBagConstraints);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(259, 259, 259)
								.addComponent(okButton,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										81,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(6, 6, 6).addComponent(cancelButton))
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jPanel1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										427, Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(11, 11, 11)
								.addComponent(jPanel1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(52, 52, 52)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(okButton)
												.addComponent(cancelButton))));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	/**
	 * 
	 * @param de
	 */
	@Override
	public void insertUpdate(DocumentEvent de) {
		this.updateData();
	}

	private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_okButtonActionPerformed
		doClose(RET_OK);
	}// GEN-LAST:event_okButtonActionPerformed

	/**
	 * 
	 * @param de
	 */
	@Override
	public void removeUpdate(DocumentEvent de) {
		this.updateData();
	}

	/**
	 * aggiorna i campi a seconda di cio' che viene digitato
	 */
	private void updateData() {
		/* controlla se l'anno e' su 4 cifre e comincia con un 1 */
		String anno = this.jTFAnno.getText();
		if ((anno.length() != 4) || (!anno.startsWith("1"))) {
			this.jTFProgressivo.setText("");
			this.jTFId.setText("");
			return;
		}
		/* cerca il progressivo giusto */
		int counter = 1;
		for (File cur : tuttiXmlMonete) {
			if (cur.getName().startsWith(anno)) {
				counter++;
			}
		}
		// assegna il progressivo a jTFProgressivo
		String strcounter = String.format("%1$04d", counter);
		this.jTFProgressivo.setText(strcounter);
		// genera l'ID corrente
		this.jTFId.setText(this.getId());
	}
}
