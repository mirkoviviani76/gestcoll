/*
 * Modifiche:
 * -
 */

package main;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.eclipse.persistence.tools.file.FileUtil;

/**
 * Recursive file listing under a specified directory.
 * 
 * @author javapractices.com
 * @author Alex Wong
 * @author anonymous user
 */
public final class GenericUtil {

	private static final String UTF8 = "UTF-8"; //$NON-NLS-1$

	/**
	 * Riempie un template. Copia il file template in outFile e effettua le
	 * modifiche.
	 * 
	 * @param in
	 *            il template
	 * @param outFile
	 *            il file destinazione
	 * @param conversione
	 *            una lista di {marker, sostituzione}
	 * @throws IOException
	 */
	public static void fillTemplate(InputStream in, String outFile,
			String[][] conversione) throws IOException {
		FileOutputStream out = new FileOutputStream(outFile);
		FileUtil.copy(in, out);
		GenericUtil.replaceInFile(new File(outFile), conversione);
	}

	/**
	 * ottiene la data e l'ora attuali
	 * 
	 * @return la data e l'ora
	 */
	public static String getDateTime() {
		return getDateTime("dd-MM-yyyy HH:mm:ss"); //$NON-NLS-1$
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
	 * @return la stringa 
	 */
	static private String readAll(File inout)
			throws UnsupportedEncodingException, FileNotFoundException,
			IOException {
		String oldtext = ""; //$NON-NLS-1$
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(inout), GenericUtil.UTF8));
		String line = ""; //$NON-NLS-1$
		while ((line = reader.readLine()) != null) {
			oldtext += line + "\n"; //$NON-NLS-1$
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
	

	/**
	 * comprime il file di input come bz2
	 * @param inputFile il file di input
	 * @param outFile il file di output
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void compressBz2(String inputFile, String outFile) throws FileNotFoundException, IOException {
		final File source = new File(inputFile);
		final File destination = new File(outFile);
		final BZip2CompressorOutputStream output = new BZip2CompressorOutputStream(new FileOutputStream(destination));
		final FileInputStream input = new FileInputStream(source);
		final byte[] buffer = new byte[8024];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}

		input.close();
		output.close();	
	
	}
	
	public static String getDigestForFile(String file, String algorithm) throws IOException {
		File input = new File(file);
	    byte[] buffer = new byte[(int) input.length()];
	    BufferedInputStream f = null;
	    String digest = "";
	    try {
	        f = new BufferedInputStream(new FileInputStream(input));
	        f.read(buffer);
	    } finally {
	        if (f != null) try { f.close(); } catch (IOException ignored) { }
	    }
	    try {
			MessageDigest messagedigest = MessageDigest.getInstance(algorithm);
			byte[] sha = messagedigest.digest(buffer);
			
			for (int i=0; i < sha.length; i++) {
				digest +=
						Integer.toString( ( sha[i] & 0xff ) + 0x100, 16).substring( 1 );
			}
		} catch (NoSuchAlgorithmException e) {
			GestLog.Error(GenericUtil.class, e);
		}

	    return digest.toUpperCase();
		
	}
	

}
