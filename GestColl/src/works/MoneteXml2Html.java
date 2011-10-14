/*
 * Modifiche:
 * -
 */

package works;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;

import main.Common;
import main.GenericUtil;
import main.Message;
import main.Progress;

import org.eclipse.persistence.tools.file.FileUtil;

import Resources.i18n.Messages;

import exceptions.InternalGestCollError;
import exceptions.XmlException;
import exceptions.XsltException;
import gestXml.CollezioneXml;
import gestXml.MonetaXml;

/**
 * Gestisce la conversione da xml a html
 * 
 */
public class MoneteXml2Html extends CollectionWorker implements CoinConverter {

	
	
	private static final String XSL_FILE = "/Resources/Xsl_tranformations/schedaHtml.xsl"; //$NON-NLS-1$
	private static final String INDEX_HTML_FILE = "index.html"; //$NON-NLS-1$
	private static final String OUTFILE_MONETE = "Monete.html"; //$NON-NLS-1$
	private static final String INDEX_HTML_TEMPLATE = "/Resources/templates/index.html.template"; //$NON-NLS-1$
	private static final String OUTFILE_MONETE_TEMPLATE = "/Resources/templates/Monete.html.template"; //$NON-NLS-1$
	private static final String CSS_FILE = "/Resources/css/report.css"; //$NON-NLS-1$

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
	 * @throws InternalGestCollError 
	 * @throws XmlException 
	 */
	@Override
	public File convert(MonetaXml mng, File outDir) throws XsltException, InternalGestCollError, XmlException {
		/* prepara il file di output */
		File ret = new File(outDir + "/" + mng.getId() + ".html"); //$NON-NLS-1$ //$NON-NLS-2$

		CollezioneXml.getCollezione().xsltConvert(mng.getId(), XSL_FILE, ret);
		return ret;
	}

	/**
	 * copia il file index.html nella directory html
	 * 
	 * @param destDir
	 * @throws IOException
	 * @throws InternalGestCollError 
	 */
	private void copyIndex(File destDir) throws IOException, InternalGestCollError {
		if (!(new File(destDir + "/" + INDEX_HTML_FILE)).exists()) { //$NON-NLS-1$
			InputStream in = Common.getCommon().getResource(INDEX_HTML_TEMPLATE);
			FileOutputStream out = new FileOutputStream(destDir + "/" + INDEX_HTML_FILE); //$NON-NLS-1$
			FileUtil.copy(in, out);
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
	public Object[] doWork(File outDir, Object[] params)
			throws FileNotFoundException, Exception {
		//crea la dir se non esiste
		createPath(outDir);
		
		/* copia il foglio stile */
		InputStream in = Common.getCommon().getResource(CSS_FILE);
		FileOutputStream out = new FileOutputStream(outDir + "/" + "report.css"); //$NON-NLS-1$ //$NON-NLS-2$
		FileUtil.copy(in, out);


		List<MonetaXml> monete = CollezioneXml.getCollezione().getMonete();
		ListIterator<MonetaXml> iterator = monete.listIterator();

		String data = ""; //$NON-NLS-1$
		
		data = data + "<table>" + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
		int i = 1;

		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			MonetaXml mng = (MonetaXml)iterator.next();
			String id = mng.getId();
			/* prepara il file di output */
			String outFile = outDir + "/" + id + ".html"; //$NON-NLS-1$ //$NON-NLS-2$
			CollezioneXml.getCollezione().xsltConvert(mng.getId(), XSL_FILE,
					new File(outFile));

			/* scrive l'item nell'indice */
			data = data + "<tr>" + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
			data = data + "<td><a href=\"" + id + ".html\"'>" + id //$NON-NLS-1$ //$NON-NLS-2$
					+ "</a></td>" + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
			data = data + "<td>" + mng.getPaese() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
			data = data + "<td>" + mng.getNominale().getValore() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
			data = data + "<td>" + mng.getNominale().getValuta() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
			data = data + "<td>" + mng.getAnno() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
			data = data + "<td>" + mng.getPosizione().getContenitore() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
			data = data + "<td>" + mng.getPosizione().getVassoio() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
			data = data + "<td>" + mng.getPosizione().getRiga() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
			data = data + "<td>" + mng.getPosizione().getColonna() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
			data = data + "</tr>" + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
			// outBuffer.println(id);
			// GenericUtil.printProgress(String.format("%d/%d",
			// (i++),files.size()), outBuffer);
			Progress p = new Progress(i, monete.size(), "HTML"); //$NON-NLS-1$
			this.setChanged();
			this.notifyObservers(p);
			i++;
		}
		data = data + "</table>" + "\n"; //$NON-NLS-1$ //$NON-NLS-2$

		String[][] conversione = { { "%DATA", data } }; //$NON-NLS-1$
		/* crea il file di output usando il template */
		
		InputStream is = Common.getCommon().getResource(OUTFILE_MONETE_TEMPLATE);
		GenericUtil.fillTemplate(is, outDir + "/" + OUTFILE_MONETE, conversione); //$NON-NLS-1$

		/* copia il file index.html */
		this.copyIndex(outDir);

		Message m = new Message(Messages.getString("MoneteXml2Html.0"), Level.INFO); //$NON-NLS-1$
		this.setChanged();
		this.notifyObservers(m);

		return null;
	}

	@Override
	public Object[] doWork(File inDir, File outDir, Object[] extraParam)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
