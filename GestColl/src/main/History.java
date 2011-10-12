/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Resources.i18n.Messages;

/**
 * Gestisce la history, memorizzando su file gli eventi.
 * 
 * @author intecs
 */
public class History {

	public static final String ADD = Messages.getString("History.0"); //$NON-NLS-1$
	public static final String DELETE = Messages.getString("History.1"); //$NON-NLS-1$
	public static final String MODIFY = Messages.getString("History.2"); //$NON-NLS-1$

	/**
	 * aggiunge un evento alla history
	 * 
	 * @param e
	 * @param id
	 */
	public static void addEvent(String e, String id) {
		FileWriter fw = null;
		try {
			/* genera l'item da scrivere */
			String item = GenericUtil.getDateTime() + "\t" + e + "\t" + id; //$NON-NLS-1$ //$NON-NLS-2$
			/* scrive l'item su file */
			fw = new FileWriter(Common.getCommon().getHistoryLog(), true);
			BufferedWriter bfw = new BufferedWriter(fw);
			bfw.write(item);
			bfw.newLine();
			bfw.close();
		} catch (IOException ex) {
			GestLog.Error(History.class, ex);
		} finally {
			try {
				fw.close();
			} catch (IOException ex) {
				GestLog.Error(History.class, ex);
			}
		}

	}
}
