/**
 *
 *  
 * TODO migliorare contatti e links
 * TODO pagina che visualizzi sigla -> libro (magari da integrare con la scheda moneta...)
 *
 * TODO Progetto: aggiungere cartella "scripts" per ant linux e java
 */
package main;

import gui.MainFrame;

import java.awt.Frame;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

/**
 *
 * 
 */
public class Main {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// create Options object

		/* setta un log manager (non sarebbe necessario, ma cosi' legge da file */
		LogManager mn = LogManager.getLogManager();
		try {
			// le proprieta' specificate prevedono logging su video e su file
			FileInputStream fis = new FileInputStream(Common.getCommon().getTemplateDir() + "/"
					+ "myLoggingProperties.properties");
			mn.readConfiguration(fis);
		} catch (IOException ex) {
			System.err.println("ERRORE:" + ex.getMessage());
		} catch (SecurityException ex) {
			System.err.println("ERRORE:" + ex.getMessage());
		}

		/* invoca la gui */
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainFrame mf = new MainFrame();
					// massimizza
					mf.setExtendedState(mf.getExtendedState()
							| Frame.MAXIMIZED_BOTH);
					mf.setVisible(true);
				} catch (NullPointerException ex) {
					GestLog.Error(Main.class, ex);
				} catch (IllegalStateException ex) {
					GestLog.Error(Main.class, ex);
				}
			}
		});
	}
}
