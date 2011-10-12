/*
 * Modifiche:
 * -
 */
package works;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;

import main.Common;
import main.GenericUtil;
import main.Message;
import main.Progress;

import org.apache.commons.io.FileUtils;

import Resources.i18n.Messages;
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

	private static final String POSIZIONI_TEMPLATE = "/Resources/templates/posizioni.tex.template"; //$NON-NLS-1$
	private static final String POSIZIONI_FILE = "posizioni.tex"; //$NON-NLS-1$
	public static final String COLLEZIONE_TEX = "/Resources/templates/Collezione.tex.template"; //$NON-NLS-1$
	public static final String COLLEZIONE_FILE = "Collezione.tex"; //$NON-NLS-1$
	
	private static final String XSL_FILE = "/Resources/Xsl_tranformations/schedaLaTeX.xsl"; //$NON-NLS-1$

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
		File out = new File(outDir + "/" + mng.getId() + ".tex"); //$NON-NLS-1$ //$NON-NLS-2$

		mng.xsltConvert(XSL_FILE, out);
		String[][] conversione = { { "&", "\\\\&" } }; //$NON-NLS-1$ //$NON-NLS-2$
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

		String allXmlList = "\\\\include{posizioni}\n"; //$NON-NLS-1$
		String posizioni = ""; //$NON-NLS-1$
		int i = 1;

		int size = files.size();
		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			MonetaXml mng;
			mng = new MonetaXml((iterator.next()));
			String id = mng.getId();
			Progress p = new Progress(i, size, Messages.getString("MoneteXml2Tex.0")); //$NON-NLS-1$

			// converte il file xls in tex
			this.convert(mng, outDir);

			/* prepara il file di posizioni */
			String paese = mng.getPaese();
			String valuta = mng.getNominale().getValore() + " " //$NON-NLS-1$
					+ mng.getNominale().getValuta();
			String cont = mng.getPosizione().getContenitore().toString();
			String vass = mng.getPosizione().getVassoio().toString();
			String r = mng.getPosizione().getRiga().toString();
			String c = mng.getPosizione().getColonna().toString();

			posizioni = posizioni + "\\\\hline\n" + id + " & " + paese + " " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ valuta + " & " + cont + " & " + vass + " & " + r + " & " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					+ c + " \\\\\\\\\n"; //$NON-NLS-1$
			allXmlList = allXmlList + "\\\\include{" + id + "}\n";  //$NON-NLS-1$//$NON-NLS-2$

			/* gestisce i documenti aggiuntivi linkati dalla moneta */
			List<DocumentoAddizionale> documenti = mng.getItemAddizionali();
			for (DocumentoAddizionale f : documenti) {
				if (f.getFilename().endsWith("tex")) { //$NON-NLS-1$
					// copia il tex in outdir
					File xxx = new File(inDir.getPath() + "/" + mng.getId() //$NON-NLS-1$
							+ "/" + f.getFilename()); //$NON-NLS-1$
					FileUtils.copyFile(xxx,
							new File(outDir + "/" + xxx.getName())); //$NON-NLS-1$
					String tmp = f.getFilename().replace(".tex", ""); //$NON-NLS-1$ //$NON-NLS-2$
					allXmlList = allXmlList + "\\\\include{" + tmp + "}\n"; //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
			this.setChanged();
			this.notifyObservers(p);
			i++;

		}
		String[][] conversione = { { "%POSIZIONI", posizioni } }; //$NON-NLS-1$
		String[][] conversione_uno = { { "%INCLUDES", allXmlList } }; //$NON-NLS-1$
		/* crea le posizioni usando il template */
		InputStream is = Common.getCommon().getResource(POSIZIONI_TEMPLATE);
		GenericUtil.fillTemplate(is, outDir + "/" + POSIZIONI_FILE, //$NON-NLS-1$
				conversione);
		/* crea il file principale usando il template */
		InputStream is2 = Common.getCommon().getResource(COLLEZIONE_TEX);
		GenericUtil.fillTemplate(is2, outDir + "/" + COLLEZIONE_FILE, conversione_uno); //$NON-NLS-1$

		Message m = new Message(Messages.getString("MoneteXml2Tex.35"), Level.INFO); //$NON-NLS-1$
		this.setChanged();
		this.notifyObservers(m);

		return null;
	}
}
