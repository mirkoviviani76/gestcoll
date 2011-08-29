/*
 * Modifiche:
 * -
 */
package works;

import gestXml.MonetaXml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import main.Common;
import main.GenericUtil;
import main.GestLog;
import main.Message;
import main.Progress;

/**
 * Gestisce la conversione da xml a tex e pdf
 * 
 * 
 */
public class MoneteXml2Tex extends CollectionWorker implements CoinConverter {

	private static final String POSIZIONI = "posizioni.tex";

	/**
	 * 
	 * @param name
	 * @param description
	 */
	public MoneteXml2Tex(String name, String description) {
		super(name, description);
	}

	/**
	 * 
	 * @param mng
	 * @param outDir
	 * @return
	 * @throws TransformerException
	 * @throws TransformerConfigurationException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Override
	public File convert(MonetaXml mng, File outDir)
			throws TransformerException, TransformerConfigurationException,
			FileNotFoundException, IOException {
		/* prepara il file di output */
		File out = new File(outDir + "/" + mng.getId() + ".tex");
		mng.xsltConvert(new File(Common.XSL_LATEX), out);
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
	 * @throws IOException 
	 * @throws TransformerException 
	 * @throws TransformerConfigurationException 
	 */
	@Override
	public Object[] doWork(File inDir, File outDir, Object[] params) throws TransformerConfigurationException, TransformerException, IOException {
		/* ottiene l'elenco di tutte le monete */
		List<File> files = getFileListing(inDir, Common.COIN_END);
		Collections.sort(files);

		ListIterator<File> iterator = files.listIterator();

		String allXmlList = "\\\\include{posizioni}\n";
		String posizioni = "";
		int i = 1;

		int size = files.size();
		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			MonetaXml mng;
			try {
				mng = new MonetaXml((File) (iterator.next()));
			String id = mng.getId();
			Progress p = new Progress(i, size, "Tex");

			//converte il file xls in tex
			this.convert(mng, outDir);

			/* prepara il file di posizioni */
			String paese = mng.getPaese();
			String valuta = mng.getNominale().getValore() + " " + mng.getNominale().getValuta();
			String cont = mng.getPosizione().getContenitore().toString();
			String vass = mng.getPosizione().getVassoio().toString();
			String r = mng.getPosizione().getRiga().toString();
			String c = mng.getPosizione().getColonna().toString();

			posizioni = posizioni + "\\\\hline\n" + id + " & " + paese + " " +
					valuta + " & " + cont + " & " + vass + " & " + r + " & " + c +
					" \\\\\\\\\n";
			allXmlList = allXmlList + "\\\\include{" + id + "}\n";

			//TODO verificare
//			/* gestisce i documenti aggiuntivi linkati dalla moneta */
//			List<DocumentoAddizionale> documenti = mng.getItemAddizionali();
//			for (DocumentoAddizionale f : documenti) {
//				if (f.getFilename().endsWith("tex")) {
//					//copia il tex in outdir
//					File xxx = new File(inDir.getPath() + "/" + mng.getId() + "/" +
//							f.getFilename());
//					FileUtils.copyFile(xxx, new File(outDir + "/" + xxx.getName()));
//					String tmp = f.getFilename().replace(".tex", "");
//					allXmlList = allXmlList + "\\\\include{" + tmp + "}\n";
//				}
//			}
			this.setChanged();
			this.notifyObservers(p);
			} catch (JAXBException e) {
				GestLog.Error(this.getClass(), e);
			}
			i++;

		}
		String[][] conversione = {{"%POSIZIONI", posizioni}};
		String[][] conversione_uno = {{"%INCLUDES", allXmlList}};
		/* crea le posizioni usando il template */
		GenericUtil.fillTemplate(Common.TEMPLATE_DIR + "/" + POSIZIONI +
				Common.TEMPLATE_END, outDir + "/" + POSIZIONI, conversione);
		/* crea il file principale usando il template */
		GenericUtil.fillTemplate(Common.TEMPLATE_DIR + "/" +
				Common.COLLEZIONE_TEX + Common.TEMPLATE_END, outDir + "/" +
						Common.COLLEZIONE_TEX, conversione_uno);

		Message m = new Message("Tex creati", Level.INFO);
		this.setChanged();
		this.notifyObservers(m);

		return null;
	}
}
