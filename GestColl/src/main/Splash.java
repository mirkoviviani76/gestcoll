package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

/**
 * Splash screen con una progress bar con stringhe
 * 
 * @author furetto76
 */
public class Splash {

	private Graphics2D bottomGraph;
	private Graphics2D labelGraph;
	private Graphics2D nameGraph;
	private JProgressBar jPBProgress;
	private JLabel jLVersion;
	private JLabel jLAppName;
	private SplashScreen splash;

	private static Splash istanza = null;

	/**
	 * identifica il numero di "azioni" per lo splash screen
	 */
	private static final int TASKS = 10;

	/** Creates new form Splash */
	private Splash() {
		super();
		// ottiene l'istanza dello SplashScreen
		splash = SplashScreen.getSplashScreen();

		if (splash != null) {
			/* ottiene le dimensioni */
			Dimension ssDim = splash.getSize();
			int height = ssDim.height;
			int width = ssDim.width;

			int appSize = 90;
			int versionSize = 30;

			/* crea le aree per disegnare */
			nameGraph = (Graphics2D) splash.createGraphics().create(0,
					(height - appSize) / 2 - 50, width, appSize);
			labelGraph = (Graphics2D) splash.createGraphics().create(0,
					height - 160, width, versionSize);
			bottomGraph = (Graphics2D) splash.createGraphics().create(0,
					height - 30, width, 30);

			/* scrive il nome */
			jLAppName = new JLabel(Common.APPNAME, SwingConstants.CENTER);
			jLAppName.setSize(width, appSize);
			jLAppName.setFont(new Font("Verdana", Font.BOLD, appSize));

			/* scrive la versione */
			jLVersion = new JLabel("Versione: " + Common.VERSION,
					SwingConstants.CENTER);
			jLVersion.setSize(width, versionSize);

			/* crea e inizializza la barra */
			jPBProgress = new JProgressBar(0, Splash.TASKS);
			jPBProgress.setStringPainted(true);
			jPBProgress.setSize(width, 30);

			/* disegna */
			jLAppName.update(nameGraph);
			jLVersion.update(labelGraph);

		} else {
			System.out.println("SPLASH null");
		}
	}

	/**
	 * Ottiene l'istanza di Splash
	 * 
	 * @return
	 */
	public static synchronized Splash getInstance() {
		if (istanza == null) {
			istanza = new Splash();
		}
		return istanza;
	}

	/**
	 * Incrementa la barra di progresso e visualizza un messaggio
	 * 
	 * @param str
	 *            il messaggio
	 */
	public void splashProgress(String str) {
		if (splash != null && splash.isVisible()) {
			jPBProgress.setValue(jPBProgress.getValue() + 1);
			jPBProgress.setString(str);
			jPBProgress.update(bottomGraph);
			splash.update();
		}

	}

}
