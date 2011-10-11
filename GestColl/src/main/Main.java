package main;

import exceptions.XmlException;
import gui.MainFrame;

import java.awt.Frame;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

import javax.swing.JOptionPane;

/**
 * 
 * La classe Main, che contiene l'entry point di GestColl.
 */
public class Main {

	/**
	 * l'entry point di GestColl
	 * 
	 * @param args
	 *            gli argomenti passati da riga di comando
	 */
	public static void main(String[] args) {
		
		String msgErrore = "Occorre specificare una configurazione presente in configurations.xml"; 
		/* controlla l'elenco dei parametri */
		if (args.length != 1) {
			//emette un messaggio di errore ed esce
			JOptionPane.showMessageDialog(null, msgErrore, Common.APPNAME, JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		// ottiene il nome della configurazione specificata da riga di comando
		String config = args[0];
		/* controlla la configurazione scelta */
		Common.getCommon().setCurrentConfigId(config);
		if (!Common.getCommon().isValidConfig()) {
			//emette un messaggio di errore ed esce
			JOptionPane.showMessageDialog(null, msgErrore, Common.APPNAME, JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		/* setta il log manager (non sarebbe necessario, ma cosi' legge da file */
		LogManager mn = LogManager.getLogManager();
		try {
			// le proprieta' specificate prevedono logging su video e su file
			FileInputStream fis = new FileInputStream(Common.getCommon().getLogProperty());
			mn.readConfiguration(fis);
		} catch (IOException ex) {
			// NB: non posso scrivere su log, perche' si e' verificato un errore durante il setup del log
			JOptionPane.showMessageDialog(null, "ERRORE: " + ex.getMessage(), Common.APPNAME, JOptionPane.ERROR_MESSAGE);
		} catch (SecurityException ex) {
			// NB: non posso scrivere su log, perche' si e' verificato un errore durante il setup del log
			JOptionPane.showMessageDialog(null, "ERRORE: " + ex.getMessage(), Common.APPNAME, JOptionPane.ERROR_MESSAGE);
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
					// rende visibile
					mf.setVisible(true);
				} catch (XmlException e) {
					GestLog.Error(this.getClass(), e);
				}
			}
		});
	}
}
