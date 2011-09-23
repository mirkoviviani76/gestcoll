/*
 * Modifiche:
 * -
 */

package works;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.Common;
import main.GestLog;
import main.Progress;

/**
 * Converte i tex in pdf utilizzando xelatex
 * 
 * 
 */
public class XelatexPdfCreator extends CollectionWorker {

	private static final String COMMAND_STRING = "xelatex -halt-on-error ";
	public static final String COLLEZIONE_TEX = "Collezione.tex";

	/**
	 * Costruttore
	 * 
	 * @param name
	 * @param descr
	 */
	public XelatexPdfCreator(String name, String descr) {
		super(name, descr);
	}

	private void creaCollezione(File outDir) throws IOException,
			InterruptedException {
		String inFile = COLLEZIONE_TEX;
		File cur = new File(Common.getCommon().getLatexDir() + "/" + inFile);
		if (cur.exists()) {
			/* esegue la conversione a pdf */
			String cmd = COMMAND_STRING + inFile;
			this.execute(cmd, outDir, 3, "Genero " + inFile);
		} else
			throw new FileNotFoundException();
		/* pulizia */
		deleteFiles(outDir, Common.INUTILI);
	}

	/**
	 * genera i pdf delle etichette
	 * 
	 * @param outDir
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void creaEtichette(File outDir) throws IOException,
			InterruptedException {

		String[] filesEtichette = { MoneteXml2Etichette.OUTFILE_ET };
		/* esegue la creazione del pdf */
		for (String f : filesEtichette) {
			File cur = new File(Common.getCommon().getLatexDir() + "/" + f);
			if (cur.exists()) {
				String cmd = COMMAND_STRING + f;
				this.execute(cmd, outDir, 2, "Genero " + f);
			} else
				throw new FileNotFoundException();
		}

		/* pulizia */
		deleteFiles(outDir, Common.INUTILI);
	}

	@Override
	public Object[] doWork(File inDir, File outDir, Object[] extraParam)
			throws Exception {
		try {
			// genera etichette pdf
			creaEtichette(outDir);
		} catch (FileNotFoundException ex) {
			GestLog.Error(XelatexPdfCreator.class, ex);
		}

		try {
			// genera collezione pdf
			creaCollezione(outDir);
		} catch (FileNotFoundException ex) {
			GestLog.Error(XelatexPdfCreator.class, ex);
		}
		return null;
	}

	/**
	 * Esegue per times volte il comando command nella directory workDir
	 * 
	 * @param command
	 *            il comando da eseguire
	 * @param workDir
	 *            la dir di lavoro
	 * @param times
	 *            il numero di volte
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void execute(String command, File workDir, int times, String title)
			throws IOException, InterruptedException {
		Runtime runtime = Runtime.getRuntime();

		for (int i = 1; i <= times; i++) {
			// Util.printProgress(String.format("%d/%d", i, times), ps);
			Progress p = new Progress(i, times + 1, title);
			this.setChanged();
			this.notifyObservers(p);
			Process process = runtime.exec(command, null, workDir);
			InputStream is = process.getInputStream();
			process.getErrorStream();

			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
			}
			// su win questo si blocca se non leggo prima da inputstream... bug
			// di java!
			process.waitFor();

		}
		Progress p = new Progress(times + 1, times + 1, title);
		this.setChanged();
		this.notifyObservers(p);

		// Util.printProgress("", ps);

	}

}
