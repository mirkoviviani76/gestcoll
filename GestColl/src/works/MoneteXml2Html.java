/*
 * Modifiche:
 * -
 */

package works;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;

import main.Common;
import main.GenericUtil;
import main.Message;
import main.Progress;

import org.apache.commons.io.FileUtils;

import exceptions.XsltException;
import gestXml.MonetaXml;

/**
 * Gestisce la conversione da xml a html
 * 
 */
public class MoneteXml2Html extends CollectionWorker implements CoinConverter {

	private static final String OUTFILE_MONETE = "Monete.html";

	/**
	 * 
	 * @param name
	 * @param description
	 */
	public MoneteXml2Html(String name, String description) {
		super(name, description);
	}

	/**
	 * converte una moneta in html
	 * @param mng
	 * @param outDir
	 * @return il file convertito
	 * @throws XsltException
	 */
	@Override
	public File convert(MonetaXml mng, File outDir) throws XsltException {
		/* prepara il file di output */
		File ret = new File(outDir + "/" + mng.getId() + ".html");
		mng.xsltConvert(new File(Common.getCommon().getXslHtml()), ret);
		return ret;
	}

	/**
	 * copia il file index.html nella directory html
	 * 
	 * @param destDir
	 * @throws IOException
	 */
	private void copyIndex(File destDir) throws IOException {
		if (!(new File(destDir + "/" + "index.html")).exists()) {
			FileUtils.copyFile(new File(Common.getCommon().getTemplateDir()
					+ "/" + "index.html.template"), new File(destDir + "/"
					+ "index.html"));
		}
	}

	/**
	 * converte tutte le monete in html
	 * 
	 * @param inDir
	 * @param outDir
	 * @param params
	 * @return null
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	@Override
	public Object[] doWork(File inDir, File outDir, Object[] params)
			throws FileNotFoundException, Exception {
		/* ottiene l'elenco di tutte le monete */
		List<File> files = getFileListing(inDir, Common.COIN_END);
		//crea la dir se non esiste
		createPath(outDir);

		ListIterator<File> iterator = files.listIterator();

		String data = "";

		data = data + "<table>" + "\n";
		int i = 1;
		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			MonetaXml mng = new MonetaXml((iterator.next()));
			String id = mng.getId();
			/* prepara il file di output */
			String outFile = outDir + "/" + id + ".html";
			mng.xsltConvert(new File(Common.getCommon().getXslHtml()),
					new File(outFile));

			/* scrive l'item nell'indice */
			data = data + "<tr>" + "\n";
			data = data + "<td><a href=\"" + id + ".html\"'>" + id
					+ "</a></td>" + "\n";
			data = data + "<td>" + mng.getPaese() + "\n";
			data = data + "<td>" + mng.getNominale().getValore() + "\n";
			data = data + "<td>" + mng.getNominale().getValuta() + "\n";
			data = data + "<td>" + mng.getAnno() + "\n";
			data = data + "<td>" + mng.getPosizione().getContenitore() + "\n";
			data = data + "<td>" + mng.getPosizione().getVassoio() + "\n";
			data = data + "<td>" + mng.getPosizione().getRiga() + "\n";
			data = data + "<td>" + mng.getPosizione().getColonna() + "\n";
			data = data + "</tr>" + "\n";
			// outBuffer.println(id);
			// GenericUtil.printProgress(String.format("%d/%d",
			// (i++),files.size()), outBuffer);
			Progress p = new Progress(i, files.size(), "HTML");
			this.setChanged();
			this.notifyObservers(p);
			i++;
		}
		data = data + "</table>" + "\n";

		String[][] conversione = { { "%DATA", data } };
		/* crea il file di output usando il template */
		GenericUtil.fillTemplate(Common.getCommon().getTemplateDir() + "/"
				+ OUTFILE_MONETE + Common.TEMPLATE_END, outDir + "/"
				+ OUTFILE_MONETE, conversione);

		/* copia il file index.html */
		this.copyIndex(outDir);

		Message m = new Message("HTML creati", Level.INFO);
		this.setChanged();
		this.notifyObservers(m);

		return null;
	}

}
