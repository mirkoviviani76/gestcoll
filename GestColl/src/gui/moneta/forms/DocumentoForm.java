/*
 * Modifiche:
 * -
 */
package gui.moneta.forms;

import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import main.Common;
import main.GestLog;

/**
 * Una finestra di dialogo per modificare lo zecchiere
 * 
 */
@SuppressWarnings("serial")
public class DocumentoForm extends javax.swing.JDialog {
	/** A return status code - returned if Cancel button has been pressed */
	public static final int RET_CANCEL = 0;
	/** A return status code - returned if OK button has been pressed */
	public static final int RET_OK = 1;

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton cancelButton;

	private javax.swing.JButton jBBrowse;

	private javax.swing.JLabel jLabel1;

	private javax.swing.JLabel jLabel2;

	private javax.swing.JLabel jLabel3;

	private javax.swing.JPanel jPanel1;

	private javax.swing.JTextField jTFDescrizione;

	private javax.swing.JTextField jTFFile;

	private javax.swing.JTextField jTFUrl;

	private javax.swing.JButton okButton;
	// End of variables declaration//GEN-END:variables

	private int returnStatus = RET_CANCEL;
	/**
	 * Creates new form ZecchiereForm
	 * 
	 * @param parent
	 * @param modal
	 */
	public DocumentoForm(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
	}
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
	 * @return
	 */
	public XmlData.Moneta.DocumentoAddizionale getData() {
		XmlData.Moneta.DocumentoAddizionale ret = new XmlData.Moneta.DocumentoAddizionale();
		ret.setDescrizione(this.jTFDescrizione.getText());
		ret.setFilename(this.jTFFile.getText());
		ret.setUrl(this.jTFUrl.getText());
		return ret;
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

		okButton = new javax.swing.JButton();
		cancelButton = new javax.swing.JButton();
		jPanel1 = new javax.swing.JPanel();
		jBBrowse = new javax.swing.JButton();
		jLabel2 = new javax.swing.JLabel();
		jTFUrl = new javax.swing.JTextField();
		jTFDescrizione = new javax.swing.JTextField();
		jLabel1 = new javax.swing.JLabel();
		jTFFile = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();

		setTitle("Modifica zecchiere");
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent evt) {
				closeDialog(evt);
			}
		});

		okButton.setText("Modifica");
		okButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				okButtonActionPerformed(evt);
			}
		});

		cancelButton.setText("Annulla");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});

		jPanel1.setLayout(new java.awt.GridBagLayout());

		jBBrowse.setText("Browse...");
		jBBrowse.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jBBrowseMouseClicked(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		jPanel1.add(jBBrowse, gridBagConstraints);

		jLabel2.setText("URL");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel1.add(jLabel2, gridBagConstraints);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 106;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		jPanel1.add(jTFUrl, gridBagConstraints);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 106;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 1.0;
		jPanel1.add(jTFDescrizione, gridBagConstraints);

		jLabel1.setText("Descrizione");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
		jPanel1.add(jLabel1, gridBagConstraints);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		jPanel1.add(jTFFile, gridBagConstraints);

		jLabel3.setText("File");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		jPanel1.add(jLabel3, gridBagConstraints);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(216, 216, 216)
								.addComponent(okButton,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										86,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(6, 6, 6).addComponent(cancelButton)
								.addGap(23, 23, 23))
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(jPanel1,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										394, Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGap(74, 74, 74)
								.addComponent(jPanel1,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(52, 52, 52)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(okButton)
												.addComponent(cancelButton))
								.addContainerGap(60, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents
	private void jBBrowseMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_jBBrowseMouseClicked
		try {
			FileDialog fd = new FileDialog(this, "File");
			fd.setVisible(true);
			// String selectedFile = fd.getDirectory()+"/"+fd.getFile();
			File selectedFile = new File(fd.getDirectory());
			URI file = selectedFile.toURI();
			File globalDir = new File(Common.getCommon().getBaseDir());
			URI global = globalDir.getCanonicalFile().toURI();
			this.jTFUrl.setText("../../" + global.relativize(file).toString());
			this.jTFFile.setText(fd.getFile());
		} catch (IOException ex) {
			GestLog.Error(DocumentoForm.class, ex);
		}
	}// GEN-LAST:event_jBBrowseMouseClicked
	private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_okButtonActionPerformed
		doClose(RET_OK);
	}// GEN-LAST:event_okButtonActionPerformed

	/**
	 * 
	 * @param doc
	 */
	public void setData(XmlData.Moneta.DocumentoAddizionale doc) {
		this.jTFDescrizione.setText(doc.getDescrizione());
		this.jTFUrl.setText(doc.getUrl());
	}
}
