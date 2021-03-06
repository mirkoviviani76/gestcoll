/*
 * Modifiche:
 * -
 */

package gui.moneta;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Resources.i18n.Messages;

import main.GestLog;

/**
 *
 * 
 */
public class MonetaImgViewer extends javax.swing.JDialog implements
		MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton growDown;
	private JButton growUp;
	/** altezza ricalcolata per il video */
	private int height;
	private BufferedImage imgb;

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel jImg;

	private javax.swing.JScrollPane jScrollPane1;
	private JPanel panel_1;

	private JPanel panel_2;

	/** somma delle rotazioni */
	private int rotateAmount;

	private JButton rotateLeft;
	private JButton rotateRight;

	/** larghezza ricalcolata per il video */
	private int width;

	/**
	 * Creates new form MonetaImgViewer
	 * 
	 * @param parent
	 * @param modal
	 */
	public MonetaImgViewer(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		imgb = null;
		rotateAmount = 0;
		height = 0;
		width = 0;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */

	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		jImg = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		jImg.setAutoscrolls(true);
		jScrollPane1.setViewportView(jImg);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING,
				javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING,
				javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE));

		JPanel panel = new JPanel();
		jScrollPane1.setColumnHeaderView(panel);

		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, Messages.getString("MonetaImgViewer.0"), TitledBorder.LEADING, //$NON-NLS-1$
				TitledBorder.TOP, null, null));
		panel.add(panel_1);

		this.rotateLeft = new JButton("<"); //$NON-NLS-1$
		panel_1.add(rotateLeft);

		this.rotateRight = new JButton(">"); //$NON-NLS-1$
		panel_1.add(rotateRight);
		this.rotateRight.addMouseListener(this);
		this.rotateLeft.addMouseListener(this);

		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, Messages.getString("MonetaImgViewer.3"), //$NON-NLS-1$
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_2);

		this.growUp = new JButton("+"); //$NON-NLS-1$
		panel_2.add(growUp);

		this.growDown = new JButton("-"); //$NON-NLS-1$
		panel_2.add(growDown);
		this.growDown.addMouseListener(this);
		this.growUp.addMouseListener(this);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	// End of variables declaration//GEN-END:variables

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getSource().equals(this.growDown)) {
			this.resize(-10);
		} else if (arg0.getSource().equals(this.growUp)) {
			this.resize(10);
		} else if (arg0.getSource().equals(this.rotateLeft)) {
			this.ruota(-5);
		} else if (arg0.getSource().equals(this.rotateRight)) {
			this.ruota(5);
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	private void resize(int d) {
		this.height = this.height + d;
		this.width = this.width + d;
		Image i1 = imgb.getScaledInstance(this.width, this.height,
				Image.SCALE_FAST);
		ImageIcon ii1 = new ImageIcon(i1);
		this.jImg.setIcon(ii1);

	}

	private void ruota(int i) {
		this.rotateAmount = this.rotateAmount + i;
		int w = imgb.getWidth();
		int h = imgb.getHeight();
		BufferedImage dimg = new BufferedImage(w, h, imgb.getType());
		Graphics2D g = dimg.createGraphics();
		g.rotate(Math.toRadians(rotateAmount), w / 2, h / 2);
		g.drawImage(imgb, null, 0, 0);
		/* mostra l'immagine ruotata e scalata */
		Image i1 = dimg.getScaledInstance(this.width, this.height,
				Image.SCALE_FAST);
		ImageIcon ii1 = new ImageIcon(i1);
		this.jImg.setIcon(ii1);

	}

	/**
	 * 
	 * @param filename
	 */
	public void setImg(String filename) {
		this.setTitle(filename);
		try {
			imgb = ImageIO.read(new File(filename));
		} catch (IOException e) {
			GestLog.Error(this.getClass(), e);
		}
		// ottiene la massima dimensione dello schermo
		Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
		int maxH = s.height - 20;
		int maxW = s.width - 20;
		/* scala l'immagine */
		this.width = (imgb.getWidth() >= maxW ? maxW : imgb.getWidth());
		this.height = (imgb.getHeight() >= maxH ? maxH : imgb.getHeight());
		this.setSize(this.width + 30, this.height + 30);

		Image i1 = imgb.getScaledInstance(width, height, Image.SCALE_FAST);
		ImageIcon ii1 = new ImageIcon(i1);
		this.jImg.setIcon(ii1);
	}

}
