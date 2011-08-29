/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author intecs
 */
public class History {

	public static final String ADD = "ADD";
	public static final String MODIFY = "MODIFY";
	public static final String DELETE = "DELETE";

	public static void addEvent(String e, String id) {
		FileWriter fw = null;
		try {
			/* genera l'item da scrivere */
			String item = GenericUtil.getDateTime() + "\t" + e + "\t" + id;
			/* scrive l'item su file */
			fw = new FileWriter(Common.HISTORY_LOG, true);
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
