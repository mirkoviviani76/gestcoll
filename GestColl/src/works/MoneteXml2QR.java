/*
 * Modifiche:
 * -
 */

package works;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;

import main.Common;
import main.Message;
import main.Progress;

import Resources.i18n.Messages;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import exceptions.InternalGestCollError;
import exceptions.XmlException;
import exceptions.XsltException;
import gestXml.CollezioneXml;
import gestXml.MonetaXml;

/**
 * Gestisce la creazione di un codice QR per ogni moneta.
 * 
 * @author furetto76
 */
public class MoneteXml2QR extends CollectionWorker implements CoinConverter {

	
	private static final String XSL_FILE = "/Resources/Xsl_tranformations/schedaTxt.xsl"; //$NON-NLS-1$
	
	/**
	 * genera un codice qr contenente la stringa data
	 * 
	 * @param data
	 * @param out
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void encode(String data, File out) throws WriterException,
			IOException {
		// get a byte matrix for the data
		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix matrix = writer.encode(data, BarcodeFormat.QR_CODE, 500, 500);
		MatrixToImageWriter.writeToFile(matrix, "png", out); //$NON-NLS-1$
	}

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
	 * converte una moneta in codice qr
	 * @param mng
	 * @param outDir
	 * @return il file con l'immagine qr
	 * @throws XsltException
	 * @throws IOException
	 * @throws WriterException
	 * @throws InternalGestCollError 
	 * @throws XmlException 
	 */
	@Override
	public File convert(MonetaXml mng, File outDir) throws XsltException,
			WriterException, IOException, InternalGestCollError, XmlException {
		File out = new File(outDir + "/" + mng.getId() + ".png"); //$NON-NLS-1$ //$NON-NLS-2$

		String dati = CollezioneXml.getCollezione().xsltConvert(mng.getId(), XSL_FILE);
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
	 * @throws XmlException
	 * @throws IOException
	 * @throws WriterException
	 * @throws XsltException
	 * @throws InternalGestCollError 
	 */
	@Override
	public Object[] doWork(File outDir, Object[] params)
			throws XmlException, XsltException, WriterException, IOException, InternalGestCollError {
		//crea la dir se non esiste
		createPath(outDir);
		
		List<MonetaXml> monete = CollezioneXml.getCollezione().getMonete();
		ListIterator<MonetaXml> iterator = monete.listIterator();
		int count = monete.size();

		int i = 1;
		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			MonetaXml mng = (MonetaXml) iterator.next();
			convert(mng, outDir);
			Progress p = new Progress(i, count, Messages.getString("MoneteXml2QR.4")); //$NON-NLS-1$
			this.setChanged();
			this.notifyObservers(p);
			i++;
		}

		Message m = new Message(Messages.getString("MoneteXml2QR.5"), Level.INFO); //$NON-NLS-1$
		this.setChanged();
		this.notifyObservers(m);

		return null;
	}

	@Override
	public Object[] doWork(File inDir, File outDir, Object[] extraParam)
			throws Exception {
		return null;
	}
}
