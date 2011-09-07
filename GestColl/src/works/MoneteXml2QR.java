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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import exceptions.XmlException;
import exceptions.XsltException;
import gestXml.MonetaXml;

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
	 * @throws XsltException 
	 * @throws IOException 
	 * @throws WriterException 
	 */
	@Override
	public File convert(MonetaXml mng, File outDir) throws XsltException, WriterException, IOException {
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
	 * @throws XmlException 
	 * @throws IOException 
	 * @throws WriterException 
	 * @throws XsltException 
	 */
	@Override
	public Object[] doWork(File inDir, File outDir, Object[] params) throws XmlException, XsltException, WriterException, IOException  {
		/* ottiene l'elenco di tutte le monete */
		List<File> files = getFileListing(inDir, Common.COIN_END);
		ListIterator<File> iterator = files.listIterator();
		int count = files.size();

		int i = 1;
		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			MonetaXml mng;
			mng = new MonetaXml((iterator.next()));
			convert(mng, outDir);
			Progress p = new Progress(i, count, "QR");
			this.setChanged();
			this.notifyObservers(p);
			i++;
		}

		Message m = new Message("QR creati", Level.INFO);
		this.setChanged();
		this.notifyObservers(m);

		return null;
	}

	/**
	 * genera un codice qr contenente la stringa data
	 * @param data
	 * @param out
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void encode(String data, File out) throws WriterException, IOException {
		 //get a byte matrix for the data
		 QRCodeWriter writer = new QRCodeWriter();
		 BitMatrix matrix = writer.encode(data, BarcodeFormat.QR_CODE, 500,
		 500);
		 MatrixToImageWriter.writeToFile(matrix, "png", out);
	}
}
