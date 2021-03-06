package main;

import gui.datamodels.MonetaListModel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import works.CollectionWorker;

/**
 * Splash screen con una progress bar con stringhe
 * 
 */
public class Splash {

	private static Splash istanza = null;
	/**
	 * identifica il numero di "azioni" per lo splash screen
	 */
	private static final int TASKS = 10;
	/**
	 * Ottiene l'istanza di Splash
	 * 
	 * @return l'istanza Splash
	 */
	public static synchronized Splash getInstance() {
		if (istanza == null) {
			istanza = new Splash();
		}
		return istanza;
	}
	private Graphics2D bottomGraph;
	private JLabel jLAppName;
	private JLabel jLVersion;
	private JProgressBar jPBProgress;

	private Graphics2D labelGraph;

	private Graphics2D nameGraph;

	private SplashScreen splash;

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

			int countMonete = 0;
			try {
				/* ottiene il numero di monete */
				List<File> files = CollectionWorker.getFileListing(new File(Common
						.getCommon().getMoneteDir()), Common.COIN_END);
				countMonete = files.size();
			} catch (FileNotFoundException ex) {
				GestLog.Error(MonetaListModel.class, ex);
			}
						
			
			/* crea e inizializza la barra */
			jPBProgress = new JProgressBar(0, Splash.TASKS + countMonete);
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
