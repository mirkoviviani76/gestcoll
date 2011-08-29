/*
 * Modifiche:
 * -
 */

package works;

import gestXml.ContenitoriXml;
import gestXml.MonetaXml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import main.Common;
import main.GenericUtil;
import main.GestLog;
import main.Message;
import main.Progress;

import org.xml.sax.SAXException;

import XmlData.Moneta.Zecca;

/**
 * Gestisce la conversione da xml a etichette
 * 
 */
public class MoneteXml2Etichette extends CollectionWorker {

	/**
     *
     */
	public static final String OUTFILE_ET = "etichette.tex";
	private static final String MARKER_ETA = "%ETICHETTEA";
	private static final String MARKER_ETB = "%ETICHETTEB";
	private static final String MARKER_ETC = "%ETICHETTEC";
	private static final String MARKER_ETD = "%ETICHETTED";
	private static final String MARKER_QRA = "%QRA";
	private static final String MARKER_QRB = "%QRB";
	private static final String MARKER_QRC = "%QRC";
	private static final String MARKER_QRD = "%QRD";

	/**
	 * 
	 * @param name
	 * @param description
	 */
	public MoneteXml2Etichette(String name, String description) {
		super(name, description);
	}

	/**
	 * Crea le etichette per le monete
	 * 
	 * @param inDir
	 * @param outDir
	 * @param params
	 * @throws TransformerException
	 * @throws FileNotFoundException
	 * @throws SAXException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@Override
	public Object[] doWork(File inDir, File outDir, Object[] params)
			throws TransformerException, FileNotFoundException, SAXException,
			IOException, InterruptedException {
		String etichetteA = "";
		String etichetteB = "";
		String etichetteC = "";
		String etichetteD = "";
		String qrA = "";
		String qrB = "";
		String qrC = "";
		String qrD = "";
		Integer[] contatore = { 0, 0, 0, 0 };

		/* ottiene l'elenco di tutte le monete */
		List<File> files = getFileListing(inDir, Common.COIN_END);
		ListIterator<File> iterator = files.listIterator();
		int i = 1;
		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			Progress notify = new Progress(i, files.size(), "Etichette");
			MonetaXml mng;
			try {
				mng = new MonetaXml((iterator.next()));
				/* prepara il file di output */
				String id = mng.getId();
				/* ottiene la dimensione della casella */
				String dimensione = getDim(mng);
				/* ottiene l'etichetta singola */
				String etichetta = getEtichetta(mng, dimensione, id);

				/* aggiorna l'elenco globale di etichette e codici qr */
				if (dimensione.equals("A")) {
					etichetteA = etichetteA + etichetta + "\n";
					qrA = qrA + "\\\\qrA{" + id + "}" + "\n";
					contatore[0]++;
				}
				if (dimensione.equals("B")) {
					etichetteB = etichetteB + etichetta + "\n";
					qrB = qrB + "\\\\qrB{" + id + "}" + "\n";
					contatore[1]++;
				}
				if (dimensione.equals("C")) {
					etichetteC = etichetteC + etichetta + "\n";
					qrC = qrC + "\\\\qrC{" + id + "}" + "\n";
					contatore[2]++;
				}
				if (dimensione.equals("D")) {
					etichetteD = etichetteD + etichetta + "\n";
					qrD = qrD + "\\\\qrD{" + id + "}" + "\n";
					contatore[3]++;
				}
			} catch (JAXBException e) {
				GestLog.Error(this.getClass(), e);
			}

			this.setChanged();
			this.notifyObservers(notify);
			i++;

		}

		String[][] conversione = { { MARKER_ETA, etichetteA },
				{ MARKER_ETB, etichetteB }, { MARKER_ETC, etichetteC },
				{ MARKER_ETD, etichetteD }, { MARKER_QRA, qrA },
				{ MARKER_QRB, qrB }, { MARKER_QRC, qrC }, { MARKER_QRD, qrD } };

		/* aggiorna il template */
		GenericUtil.fillTemplate(Common.TEMPLATE_DIR + "/" + OUTFILE_ET
				+ Common.TEMPLATE_END, outDir + "/" + OUTFILE_ET, conversione);

		Message m = new Message("Etichette.tex creati", Level.INFO);
		this.setChanged();
		this.notifyObservers(m);

		return contatore;

	}

	/**
	 * Ottiene la dimensione della casella
	 * 
	 * @param xml
	 *            il file della moneta
	 * @return la dimensione espressa come stringa A,B,C,D
	 * @throws TransformerException
	 */
	private String getDim(MonetaXml xml) throws TransformerException {
		ContenitoriXml collezione = new ContenitoriXml();
		int cont = xml.getPosizione().getContenitore().intValue();
		int vass = xml.getPosizione().getVassoio().intValue();
		String dim = collezione.getArmadio().getDim(cont, vass).toString();
		return dim;
	}

	/**
	 * Ottiene l'etichetta della singola moneta
	 * 
	 * @param xml
	 *            il file di moneta
	 * @param dimensione
	 *            la dimensione della casella
	 * @param id
	 *            l'id della moneta
	 * @return
	 * @throws TransformerException
	 */
	private String getEtichetta(MonetaXml xml, String dimensione, String id)
			throws TransformerException {
		String out = "";
		String autorita = "";
		
		String paese = xml.getPaese();
		if (xml.getAutorita() != null && xml.getAutorita().getNome() != null) {
			for (String nome : xml.getAutorita().getNome()) {
				autorita = autorita + ", " + nome;
			}
		}
		Zecca valZecca = xml.getZecca();
		String zecca = "";
		if (valZecca != null)
		{
			zecca = valZecca.toString();
		}
		// serve almeno uno spazio per il latex
		if (autorita.equals(""))
			autorita = " ";

		if (!zecca.equals(" ") && !autorita.equals(" ")) {
			zecca = "\\\\\\\\" + zecca;
		}

		String valore = xml.getNominale().getValore() + " "
				+ xml.getNominale().getValuta();
		String anno = xml.getAnno();
		String nominale = valore + " " + anno;
		/* compone l'etichetta */
		out = "\\\\casella" + dimensione + "{" + paese + "}{" + autorita + "}{"
				+ zecca + "}{" + nominale + "}{" + id + "}";
		return out;

	}

}
