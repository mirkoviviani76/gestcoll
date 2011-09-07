package main;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 * Classe per gestire i messaggi di errore o di log
 * 
 */
public class GestLog {

	/**
	 * gestisce un errore, mostrando un message box e effettuando il log
	 * 
	 * @param source
	 *            la classe dove si e' verificato l'errore
	 * @param ex
	 *            l'eccezione
	 */
	@SuppressWarnings("rawtypes")
	public static void Error(Class source, Exception e) {
		JOptionPane.showMessageDialog(null,
				source.getName() + ": " + e.getMessage(), Common.APPNAME,
				JOptionPane.ERROR_MESSAGE);
		Logger.getLogger(source.getName()).logp(Level.SEVERE, source.getName(),
				null, null, e);
	}

	/**
	 * gestisce un errore, mostrando un message box e effettuando il log
	 * 
	 * @param source
	 *            la classe dove si e' verificato l'errore
	 * @param method
	 *            il metodo dove si e' verificato l'errore
	 * @param ex
	 *            l'eccezione
	 */
	@SuppressWarnings("rawtypes")
	public static void Error(Class source, String method, String ex) {
		JOptionPane.showMessageDialog(null, ex, Common.APPNAME,
				JOptionPane.ERROR_MESSAGE);
		Logger.getLogger(source.getName()).logp(Level.SEVERE, source.getName(),
				method, ex);
	}

	/**
	 * Gestisce un messaggio di informazione
	 * 
	 * @param source
	 *            la classe sorgente
	 * @param message
	 *            il messaggio
	 * @param showMessageBox
	 *            indica se visualizzare o meno un message box
	 */
	@SuppressWarnings("rawtypes")
	public static void Message(Class source, String message,
			boolean showMessageBox) {
		if (showMessageBox) {
			JOptionPane.showMessageDialog(null, message, Common.APPNAME,
					JOptionPane.INFORMATION_MESSAGE);
		}
		Logger.getLogger(source.getName()).logp(Level.INFO, source.getName(),
				null, message);
	}

	/**
	 * gestisce un warning, mostrando un message box e effettuando il log
	 * 
	 * @param source
	 *            la classe dove si e' verificato l'errore
	 * @param method
	 *            il metodo dove si e' verificato l'errore
	 * @param ex
	 *            l'eccezione
	 */
	@SuppressWarnings("rawtypes")
	public static void Warning(Class source, String method, String ex) {
		JOptionPane.showMessageDialog(null, ex, Common.APPNAME,
				JOptionPane.WARNING_MESSAGE);
		Logger.getLogger(source.getName()).logp(Level.WARNING,
				source.getName(), method, ex);
	}
}
