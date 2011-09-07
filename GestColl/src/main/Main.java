/**
 *  
 * TODO pagina che visualizzi sigla -> libro (magari da integrare con la scheda moneta...)
 *
 */
package main;

import exceptions.XmlException;
import gui.MainFrame;

import java.awt.Frame;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

/**
 *
 * La classe Main, che contiene l'entry point di GestColl. 
 */
public class Main {

	/**
	 * l'entry point di GestColl
	 * @param args gli argomenti passati da riga di comando
	 */
	public static void main(String[] args) {
		/* setta il log manager (non sarebbe necessario, ma cosi' legge da file */
		LogManager mn = LogManager.getLogManager();
		try {
			// le proprieta' specificate prevedono logging su video e su file
			FileInputStream fis = new FileInputStream(Common.getCommon().getDataDir() + "/"
					+ "myLoggingProperties.properties");
			mn.readConfiguration(fis);
		} catch (IOException ex) {
			//NB: non posso scrivere su log, perche' si e' verificato un errore durante il setup del log
			System.err.println("ERRORE: " + ex.getMessage());
		} catch (SecurityException ex) {
			//NB: non posso scrivere su log, perche' si e' verificato un errore durante il setup del log
			System.err.println("ERRORE: " + ex.getMessage());
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
						//rende visibile
						mf.setVisible(true);
					} catch (XmlException e) {
						GestLog.Error(this.getClass(), e);
					}
			}
		});
	}
}
