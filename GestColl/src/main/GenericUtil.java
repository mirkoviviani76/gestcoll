/*
 * Modifiche:
 * -
 */

package main;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.io.FileUtils;

/**
 * Recursive file listing under a specified directory.
 * 
 * @author javapractices.com
 * @author Alex Wong
 * @author anonymous user
 */
public final class GenericUtil {

	private static final String UTF8 = "UTF-8";

	/**
	 * Riempie un template. Copia il file template in outFile e effettua le
	 * modifiche.
	 * 
	 * @param template
	 *            il file sorgente
	 * @param outFile
	 *            il file destinazione
	 * @param conversione
	 *            una lista di {marker, sostituzione}
	 * @throws IOException
	 */
	public static void fillTemplate(String template, String outFile,
			String[][] conversione) throws IOException {
		File source = new File(template);
		if (source.exists()) {
			FileUtils.copyFile(source, new File(outFile));
			GenericUtil.replaceInFile(new File(outFile), conversione);
		} else {
			GestLog.Error(GenericUtil.class, "fillTemplate",
					"Il file instance.xml.template non esiste");
		}
	}

	/**
	 * ottiene la data e l'ora attuali
	 * 
	 * @return la data e l'ora
	 */
	public static String getDateTime() {
		return getDateTime("dd-MM-yyyy HH:mm:ss");
	}

	/**
	 * ottiene la data e il tempo
	 * 
	 * @param format
	 *            il formato
	 * @return la data
	 */
	public static String getDateTime(String format) {
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat ff = new SimpleDateFormat(format);
		return ff.format(calendar.getTime());
	}

	/**
	 * apre il browser esterno
	 * 
	 * @param uri
	 *            la pagina da mostrare
	 * @throws IOException
	 */
	public static void openBrowser(URI uri) throws IOException {
		if (Desktop.isDesktopSupported()) {
			// apre il browser
			Desktop.getDesktop().browse(uri);
		}
	}

	/**
	 * Legge tutto il file in una stringa UTF-8
	 * 
	 * @param inout
	 * @return
	 */
	static private String readAll(File inout)
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		String oldtext = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(inout), GenericUtil.UTF8));
		String line = "";
		while ((line = reader.readLine()) != null) {
			oldtext += line + "\n";
		}
		reader.close();
		return oldtext;
	}

	/**
	 * 
	 * @param inout
	 * @param conversione
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	static public void replaceInFile(File inout, String[][] conversione)
			throws FileNotFoundException, IOException {
		String newtext = GenericUtil.readAll(inout);
		for (String[] sostituzione : conversione) {
			// esegue la sostituzione
			newtext = newtext.replaceAll(sostituzione[0], sostituzione[1]);
		}
		// scrive il testo
		GenericUtil.writeAll(inout, newtext);
	}

	/**
	 * Scrive il testo text sul file inout (charset UTF-8)
	 * 
	 * @param inout
	 * @param text
	 */
	static private void writeAll(File inout, String text)
			throws FileNotFoundException, UnsupportedEncodingException,
			IOException {
		FileOutputStream fw = new FileOutputStream(inout);
		OutputStreamWriter osw = new OutputStreamWriter(fw, GenericUtil.UTF8);
		BufferedWriter bw = null;
		bw = new BufferedWriter(osw);
		bw.write(text);
		bw.close();
		osw.close();
		fw.close();
	}

}
