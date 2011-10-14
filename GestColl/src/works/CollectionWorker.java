/*
 * Modifiche:
 * -
 */

package works;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import main.GestLog;

import org.apache.commons.io.FileUtils;

import Resources.i18n.Messages;

/**
 *
 * 
 */
public abstract class CollectionWorker extends Observable {
	/**
     * elenco delle estensioni dei file importanti
     */
	public static final String[] ALL = { "html", "pdf", "png" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	/**
     * elenco delle estensioni dei file da cancellare
     */
	public static final String[] INUTILI = { "aux", "toc", "log", "out" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	/**
     * nome dell'estensione dei template
     */
	public static final String TEMPLATE_END = ".template"; //$NON-NLS-1$
	/**
     * elenco delle estensioni dei file temporanei
     */
	public static final String[] TMP = { "tex" }; //$NON-NLS-1$

	/**
	 * Cancella tutti i file con le estensioni contenute in pattern a partire
	 * dalla directory specificata in startPath
	 * 
	 * @param startPath
	 *            la directory dalla quale cancellare ricorsivamente i files
	 * @param pattern
	 *            un array di estensioni
	 * @return true nel caso siano stati cancellati tutti i files, false
	 *         altrimenti.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	static public synchronized int deleteFiles(File startPath, String[] pattern)
			throws FileNotFoundException, IOException {
		List<File> daEliminare = getFileListing(startPath, pattern);
		int count = 0;
		for (File f : daEliminare) {
			if (f.delete()) {
				count++;
			}
		}
		if (count != daEliminare.size()) {
			String msg = Messages.getString("CollectionWorker.0") + count //$NON-NLS-1$
					+ "/" + daEliminare.size(); //$NON-NLS-1$
			GestLog.Warning(CollectionWorker.class, "deleteFiles", msg); //$NON-NLS-1$
		}

		return count;
	}
	
	/**
	 * Crea un path, costruendo l'albero se non esiste
	 * @param path il path
	 * @throws IOException 
	 */
	static public synchronized void createPath(File path) throws IOException {
		FileUtils.forceMkdir(path);
	}

	/**
	 * Recursively walk a directory tree and return a List of all Files found;
	 * the List is sorted using File.compareTo().
	 * 
	 * @param aStartingDir
	 *            is a valid directory, which can be read.
	 * @param pattern
	 *            is a regexp for filename matching.
	 * @return la lista di file
	 * @throws FileNotFoundException
	 */
	static public List<File> getFileListing(File aStartingDir, String[] pattern)
			throws FileNotFoundException {
		List<File> res = new ArrayList<File>();
		if (aStartingDir.isDirectory()) {
			Iterator<File> lista = FileUtils.iterateFiles(aStartingDir,
					pattern, true);
			while (lista.hasNext()) {
				File curFile = lista.next();
				res.add(curFile);
			}
		}
		return res;
	}
	

	/**
	 * Ottiene la lista dei file con un determinato pattern a partire da una dir
	 * @param aStartingDir la directory di partenza
	 * @param pattern il pattern dei file
	 * @return la lista dei file
	 */
	static public List<File> getFileListing(String aStartingDir,
			String[] pattern) {
		List<File> res = null;
		try {
			res = getFileListing(new File(aStartingDir), pattern);
		} catch (FileNotFoundException ex) {
			GestLog.Error(CollectionWorker.class, ex);
		}
		return res;
	}

	/**
	 * Rimuove i file inutili, temporanei e gli output (html e pdf)
	 * 
	 * @param dirs
	 *            le directory in cui effettuare la cancellazione
	 * @return il numero di file eliminati
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static int removeAll(String[] dirs) throws FileNotFoundException,
			IOException {
		int count = 0;
		/* cicla su tutte le directory specificate */
		for (String d : dirs) {
			count = count + deleteFiles(new File(d), INUTILI);
			count = count + deleteFiles(new File(d), TMP);
			count = count + deleteFiles(new File(d), ALL);
		}
		return count;
	}

	/**
	 * Rimuove i file inutili e temporanei
	 * 
	 * @param dirs
	 *            le directory in cui effettuare la cancellazione
	 * @return il numero di file eliminati
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static int removeTemp(String[] dirs) throws FileNotFoundException,
			IOException {
		int count = 0;
		/* cicla su tutte le directory specificate */
		for (String d : dirs) {
			count = count + deleteFiles(new File(d), INUTILI);
			count = count + deleteFiles(new File(d), TMP);
		}
		return count;
	}

	private String description;

	private String name;

	/**
	 * Costruttore
	 * @param name
	 */
	public CollectionWorker(String name) {
		this.name = name;
		this.description = ""; //$NON-NLS-1$
	}

	/**
	 * Costruttore
	 * @param name
	 * @param description
	 */
	public CollectionWorker(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/**
	 * Metodo astratto relativo all'esecuzione del lavoro sulla collezione
	 * 
	 * @param outDir
	 * @param extraParam
	 * @return un elenco di risultati del lavoro
	 * @throws Exception
	 */
	public abstract Object[] doWork(File outDir, Object[] extraParam)
			throws Exception;

	/**
	 * Metodo astratto relativo all'esecuzione del lavoro
	 * 
	 * @param inDir
	 * @param outDir
	 * @param extraParam
	 * @return un elenco di risultati del lavoro
	 * @throws Exception
	 */
	public abstract Object[] doWork(File inDir, File outDir, Object[] extraParam)
			throws Exception;
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	

}
