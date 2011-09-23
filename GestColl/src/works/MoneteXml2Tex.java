/*
 * Modifiche:
 * -
 */
package works;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;

import main.Common;
import main.GenericUtil;
import main.Message;
import main.Progress;

import org.apache.commons.io.FileUtils;

import XmlData.Moneta.DocumentoAddizionale;
import exceptions.InternalGestCollError;
import exceptions.XmlException;
import exceptions.XsltException;
import gestXml.MonetaXml;

/**
 * Gestisce la conversione da xml a tex e pdf
 * 
 * 
 */
public class MoneteXml2Tex extends CollectionWorker implements CoinConverter {

	private static final String POSIZIONI = "/works/templates/posizioni.tex.template";
	public static final String COLLEZIONE_TEX = "/works/templates/Collezione.tex.template";
	
	private static final String XSL_FILE = "/works/Xsl_tranformations/schedaLaTeX.xsl";

	/**
	 * 
	 * @param name
	 * @param description
	 */
	public MoneteXml2Tex(String name, String description) {
		super(name, description);
	}

	/**
	 * converte la moneta in tex
	 * @param mng
	 * @param outDir
	 * @return il file
	 * @throws XsltException
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws InternalGestCollError 
	 */
	@Override
	public File convert(MonetaXml mng, File outDir) throws XsltException,
			FileNotFoundException, IOException, InternalGestCollError {
		/* prepara il file di output */
		File out = new File(outDir + "/" + mng.getId() + ".tex");

		mng.xsltConvert(XSL_FILE, out);
		String[][] conversione = { { "&", "\\\\&" } };
		GenericUtil.replaceInFile(out, conversione);
		return out;
	}

	/**
	 * Converte da xml a latex
	 * 
	 * @param inDir
	 * @param outDir
	 * @param params
	 * @throws XmlException
	 * @throws IOException
	 * @throws XsltException
	 * @throws InternalGestCollError 
	 */
	@Override
	public Object[] doWork(File inDir, File outDir, Object[] params)
			throws XmlException, XsltException, IOException, InternalGestCollError {
		/* ottiene l'elenco di tutte le monete */
		List<File> files = getFileListing(inDir, Common.COIN_END);
		Collections.sort(files);
		//crea la dir se non esiste
		createPath(outDir);


		ListIterator<File> iterator = files.listIterator();

		String allXmlList = "\\\\include{posizioni}\n";
		String posizioni = "";
		int i = 1;

		int size = files.size();
		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			MonetaXml mng;
			mng = new MonetaXml((iterator.next()));
			String id = mng.getId();
			Progress p = new Progress(i, size, "Tex");

			// converte il file xls in tex
			this.convert(mng, outDir);

			/* prepara il file di posizioni */
			String paese = mng.getPaese();
			String valuta = mng.getNominale().getValore() + " "
					+ mng.getNominale().getValuta();
			String cont = mng.getPosizione().getContenitore().toString();
			String vass = mng.getPosizione().getVassoio().toString();
			String r = mng.getPosizione().getRiga().toString();
			String c = mng.getPosizione().getColonna().toString();

			posizioni = posizioni + "\\\\hline\n" + id + " & " + paese + " "
					+ valuta + " & " + cont + " & " + vass + " & " + r + " & "
					+ c + " \\\\\\\\\n";
			allXmlList = allXmlList + "\\\\include{" + id + "}\n";

			/* gestisce i documenti aggiuntivi linkati dalla moneta */
			List<DocumentoAddizionale> documenti = mng.getItemAddizionali();
			for (DocumentoAddizionale f : documenti) {
				if (f.getFilename().endsWith("tex")) {
					// copia il tex in outdir
					File xxx = new File(inDir.getPath() + "/" + mng.getId()
							+ "/" + f.getFilename());
					FileUtils.copyFile(xxx,
							new File(outDir + "/" + xxx.getName()));
					String tmp = f.getFilename().replace(".tex", "");
					allXmlList = allXmlList + "\\\\include{" + tmp + "}\n";
				}
			}
			this.setChanged();
			this.notifyObservers(p);
			i++;

		}
		String[][] conversione = { { "%POSIZIONI", posizioni } };
		String[][] conversione_uno = { { "%INCLUDES", allXmlList } };
		/* crea le posizioni usando il template */
		InputStream is = Common.getCommon().getResource(POSIZIONI);
		GenericUtil.fillTemplate(is, outDir + "/" + "posizioni.tex",
				conversione);
		/* crea il file principale usando il template */
		InputStream is2 = Common.getCommon().getResource(COLLEZIONE_TEX);
		GenericUtil.fillTemplate(is2, outDir + "/" + "Collezione.tex", conversione_uno);

		Message m = new Message("Tex creati", Level.INFO);
		this.setChanged();
		this.notifyObservers(m);

		return null;
	}
}
