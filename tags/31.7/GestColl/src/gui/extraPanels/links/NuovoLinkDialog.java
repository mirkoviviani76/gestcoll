/*
 * Modifiche:
 * -
 */
package gui.extraPanels.links;

import gestXml.data.Link;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JDialog;


/**
 * Una finestra di dialogo per aggiungere un contatto
 * 
 */
@SuppressWarnings("serial")
public class NuovoLinkDialog extends javax.swing.JDialog {
	/** A return status code - returned if Cancel button has been pressed */
	public static final int RET_CANCEL = 0;
	/** A return status code - returned if OK button has been pressed */
	public static final int RET_OK = 1;

	private int returnStatus = RET_CANCEL;
	private JTextField jTFName;
	private JTextField jTFUrl;
	private JTextField jTFNote;
	private JPanel panel;
	private JButton okbutton;
	private JButton cancelbutton;
	private JLabel lblCategoria;
	private JTextField jTFCategoria;

	/**
	 * Creates new form
	 * 
	 * @param parent
	 * @param modal
	 */
	public NuovoLinkDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(300, 300));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{27, 86, 24, 0};
		gridBagLayout.rowHeights = new int[]{0, 20, 33, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		lblCategoria = new JLabel("Categoria");
		GridBagConstraints gbc_lblCategoria = new GridBagConstraints();
		gbc_lblCategoria.anchor = GridBagConstraints.EAST;
		gbc_lblCategoria.insets = new Insets(0, 0, 5, 5);
		gbc_lblCategoria.gridx = 0;
		gbc_lblCategoria.gridy = 1;
		getContentPane().add(lblCategoria, gbc_lblCategoria);
		
		jTFCategoria = new JTextField();
		jTFCategoria.setEditable(false);
		GridBagConstraints gbc_jTFCategoria = new GridBagConstraints();
		gbc_jTFCategoria.insets = new Insets(0, 0, 5, 5);
		gbc_jTFCategoria.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTFCategoria.gridx = 1;
		gbc_jTFCategoria.gridy = 1;
		getContentPane().add(jTFCategoria, gbc_jTFCategoria);
		jTFCategoria.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		GridBagConstraints gbc_lblNome = new GridBagConstraints();
		gbc_lblNome.anchor = GridBagConstraints.WEST;
		gbc_lblNome.insets = new Insets(0, 0, 5, 5);
		gbc_lblNome.gridx = 0;
		gbc_lblNome.gridy = 2;
		getContentPane().add(lblNome, gbc_lblNome);
		
		jTFName = new JTextField();
		GridBagConstraints gbc_jTFName = new GridBagConstraints();
		gbc_jTFName.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTFName.insets = new Insets(0, 0, 5, 5);
		gbc_jTFName.gridx = 1;
		gbc_jTFName.gridy = 2;
		getContentPane().add(jTFName, gbc_jTFName);
		jTFName.setColumns(1);
		
		JLabel lblUrl = new JLabel("Url");
		GridBagConstraints gbc_lblUrl = new GridBagConstraints();
		gbc_lblUrl.anchor = GridBagConstraints.WEST;
		gbc_lblUrl.insets = new Insets(0, 0, 5, 5);
		gbc_lblUrl.gridx = 0;
		gbc_lblUrl.gridy = 3;
		getContentPane().add(lblUrl, gbc_lblUrl);
		
		jTFUrl = new JTextField();
		jTFUrl.setText("http://");
		GridBagConstraints gbc_jTFUrl = new GridBagConstraints();
		gbc_jTFUrl.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTFUrl.insets = new Insets(0, 0, 5, 5);
		gbc_jTFUrl.gridx = 1;
		gbc_jTFUrl.gridy = 3;
		getContentPane().add(jTFUrl, gbc_jTFUrl);
		jTFUrl.setColumns(10);
		
		JLabel lblNote = new JLabel("Note");
		GridBagConstraints gbc_lblNote = new GridBagConstraints();
		gbc_lblNote.anchor = GridBagConstraints.WEST;
		gbc_lblNote.insets = new Insets(0, 0, 5, 5);
		gbc_lblNote.gridx = 0;
		gbc_lblNote.gridy = 4;
		getContentPane().add(lblNote, gbc_lblNote);
		
		jTFNote = new JTextField();
		GridBagConstraints gbc_jTFNote = new GridBagConstraints();
		gbc_jTFNote.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTFNote.insets = new Insets(0, 0, 5, 5);
		gbc_jTFNote.gridx = 1;
		gbc_jTFNote.gridy = 4;
		getContentPane().add(jTFNote, gbc_jTFNote);
		jTFNote.setColumns(10);
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel.gridwidth = 2;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 5;
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
		setTitle("Aggiungi Link");
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent evt) {
				closeDialog(evt);
			}
		});

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
	public Link getData() {
		Link n = new Link(this.jTFCategoria.getText(),
				this.jTFName.getText(),
				this.jTFUrl.getText(),
				this.jTFNote.getText());
		return n;
	}
	/** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
	public int getReturnStatus() {
		return returnStatus;
	}

	/**
	 * setta la categoria
	 * @param cat
	 */
	public void setCategoria(String cat) {
		this.jTFCategoria.setText(cat);
	}
	

	private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_okButtonActionPerformed
		doClose(RET_OK);
	}// GEN-LAST:event_okButtonActionPerformed

}
