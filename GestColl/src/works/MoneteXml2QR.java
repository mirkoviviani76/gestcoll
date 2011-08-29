/*
 * Modifiche:
 * -
 */

package works;

import gestXml.MonetaXml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import main.Common;
import main.GestLog;
import main.Message;
import main.Progress;

import org.xml.sax.SAXException;

/**
 * Gestisce la creazione di un codice QR per ogni moneta.
 * 
 * @author furetto76
 */
public class MoneteXml2QR extends CollectionWorker implements CoinConverter {

	/**
	 * Costruttore.
	 * 
	 * @param name
	 *            il nome della procedura
	 * @param description
	 *            la descrizione della procedura
	 */
	public MoneteXml2QR(String name, String description) {
		super(name, description);
	}

	/**
	 * 
	 * @param mng
	 * @param outDir
	 * @return
	 * @throws TransformerException
	 * @throws TransformerConfigurationException
	 * @throws IOException
	 * @throws WriterException
	 */
	@Override
	public File convert(MonetaXml mng, File outDir)
			throws TransformerException, TransformerConfigurationException,
			IOException {
		File out = new File(outDir + "/" + mng.getId() + ".png");
		 String dati = mng.xsltConvert(new File(Common.getCommon().getXslTxt()));
		 MoneteXml2QR.encode(dati, out);
		return out;
	}

	/**
	 * Genera un codice QR per ogni moneta, utilizzando per generare i dati il
	 * foglio xslt schedaTxt.xslt
	 * 
	 * @param inDir
	 * @param outDir
	 * @param params
	 * @throws FileNotFoundException
	 * @throws SAXException
	 * @throws TransformerException
	 * @throws IOException
	 * @throws WriterException
	 */
	@Override
	public Object[] doWork(File inDir, File outDir, Object[] params)
			throws FileNotFoundException, SAXException, TransformerException,
			IOException {
		/* ottiene l'elenco di tutte le monete */
		List<File> files = getFileListing(inDir, Common.COIN_END);
		ListIterator<File> iterator = files.listIterator();
		int count = files.size();

		int i = 1;
		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			MonetaXml mng;
			try {
				mng = new MonetaXml((iterator.next()));
				convert(mng, outDir);
				Progress p = new Progress(i, count, "QR");
				this.setChanged();
				this.notifyObservers(p);
				i++;
			} catch (JAXBException e) {
				GestLog.Error(this.getClass(), e);
			}
		}

		Message m = new Message("QR creati", Level.INFO);
		this.setChanged();
		this.notifyObservers(m);

		return null;
	}

	/**
	 * genera un codice qr contenente i dati data
	 * 
	 * @param data
	 * @param out
	 * @throws IOException
	 * @throws WriterException
	 *             FIXME
	 */
	public static void encode(String data, File out) throws IOException {
//		 //get a byte matrix for the data
//		 Writer writer = new QRCodeWriter();
//		 BitMatrix matrix = writer.encode(data, BarcodeFormat.QR_CODE, 500,
//		 500);
//		 MatrixToImageWriter.writeToFile(matrix, "png", out);
	}
}
