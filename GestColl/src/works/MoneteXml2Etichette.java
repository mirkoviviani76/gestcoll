/*
 * Modifiche:
 * -
 */

package works;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;

import main.Common;
import main.GenericUtil;
import main.Message;
import main.Progress;
import Resources.i18n.Messages;
import XmlData.Moneta.Zecca;
import exceptions.InternalGestCollError;
import exceptions.XmlException;
import gestXml.CollezioneXml;
import gestXml.ContenitoriXml;
import gestXml.MonetaXml;

/**
 * Gestisce la conversione da xml a etichette
 * 
 */
public class MoneteXml2Etichette extends CollectionWorker {

	private static final String MARKER_ETA = "%ETICHETTEA"; //$NON-NLS-1$
	private static final String MARKER_ETB = "%ETICHETTEB"; //$NON-NLS-1$
	private static final String MARKER_ETC = "%ETICHETTEC"; //$NON-NLS-1$
	private static final String MARKER_ETD = "%ETICHETTED"; //$NON-NLS-1$
	private static final String MARKER_QRA = "%QRA"; //$NON-NLS-1$
	private static final String MARKER_QRB = "%QRB"; //$NON-NLS-1$
	private static final String MARKER_QRC = "%QRC"; //$NON-NLS-1$
	private static final String MARKER_QRD = "%QRD"; //$NON-NLS-1$
	/**
     *
     */
	public static final String TEMPLATE_ET = "/Resources/templates/etichette.tex.template"; //$NON-NLS-1$
	public static final String OUTFILE = "etichette.tex"; //$NON-NLS-1$
	

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
	 * @throws XmlException
	 * @throws IOException
	 * @throws InternalGestCollError 
	 */
	@Override
	public Object[] doWork(File outDir, Object[] params)
			throws XmlException, IOException, InternalGestCollError {
		String etichetteA = ""; //$NON-NLS-1$
		String etichetteB = ""; //$NON-NLS-1$
		String etichetteC = ""; //$NON-NLS-1$
		String etichetteD = ""; //$NON-NLS-1$
		String qrA = ""; //$NON-NLS-1$
		String qrB = ""; //$NON-NLS-1$
		String qrC = ""; //$NON-NLS-1$
		String qrD = ""; //$NON-NLS-1$
		Integer[] contatore = { 0, 0, 0, 0 };

		/* ottiene l'elenco di tutte le monete */
		List<MonetaXml> monete = CollezioneXml.getCollezione().getMonete();
		//crea la dir se non esiste
		createPath(outDir);

		ListIterator<MonetaXml> iterator = monete.listIterator();
		int i = 1;
		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			Progress notify = new Progress(i, monete.size(), Messages.getString("MoneteXml2Etichette.1")); //$NON-NLS-1$
			MonetaXml mng = (MonetaXml) iterator.next();
			/* prepara il file di output */
			String id = mng.getId();
			/* ottiene la dimensione della casella */
			String dimensione = getDim(mng);
			/* ottiene l'etichetta singola */
			String etichetta = getEtichetta(mng, dimensione, id);

			/* aggiorna l'elenco globale di etichette e codici qr */
			if (dimensione.equals("A")) { //$NON-NLS-1$
				etichetteA = etichetteA + etichetta + "\n"; //$NON-NLS-1$
				qrA = qrA + "\\\\qrA{" + id + "}" + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				contatore[0]++;
			}
			if (dimensione.equals("B")) { //$NON-NLS-1$
				etichetteB = etichetteB + etichetta + "\n"; //$NON-NLS-1$
				qrB = qrB + "\\\\qrB{" + id + "}" + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				contatore[1]++;
			}
			if (dimensione.equals("C")) { //$NON-NLS-1$
				etichetteC = etichetteC + etichetta + "\n"; //$NON-NLS-1$
				qrC = qrC + "\\\\qrC{" + id + "}" + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				contatore[2]++;
			}
			if (dimensione.equals("D")) { //$NON-NLS-1$
				etichetteD = etichetteD + etichetta + "\n"; //$NON-NLS-1$
				qrD = qrD + "\\\\qrD{" + id + "}" + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				contatore[3]++;
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
		InputStream is = Common.getCommon().getResource(TEMPLATE_ET);
		GenericUtil.fillTemplate(is, outDir + "/" + OUTFILE, //$NON-NLS-1$
				conversione);

		Message m = new Message(OUTFILE + " " + Messages.getString("MoneteXml2Etichette.0"), Level.INFO); //$NON-NLS-1$
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
	 * @throws XmlException
	 */
	private String getDim(MonetaXml xml) throws XmlException {
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
	 * @return l'etichetta come stringa
	 * 
	 */
	private String getEtichetta(MonetaXml xml, String dimensione, String id) {
		String out = ""; //$NON-NLS-1$
		String autorita = ""; //$NON-NLS-1$

		String paese = xml.getPaese();
		if (xml.getAutorita() != null && xml.getAutorita().getNome() != null) {
			for (String nome : xml.getAutorita().getNome()) {
				autorita = autorita + ", " + nome; //$NON-NLS-1$
			}
		}
		Zecca valZecca = xml.getZecca();
		String zecca = ""; //$NON-NLS-1$
		if (valZecca != null) {
			zecca = valZecca.toString();
		}
		// serve almeno uno spazio per il latex
		if (autorita.equals("")) { //$NON-NLS-1$
			autorita = " "; //$NON-NLS-1$
		}

		if (!zecca.equals(" ") && !autorita.equals(" ")) { //$NON-NLS-1$ //$NON-NLS-2$
			zecca = "\\\\\\\\" + zecca; //$NON-NLS-1$
		}

		String valore = xml.getNominale().getValore() + " " //$NON-NLS-1$
				+ xml.getNominale().getValuta();
		String anno = xml.getAnno();
		String nominale = valore + " " + anno; //$NON-NLS-1$
		/* compone l'etichetta */
		out = "\\\\casella" + dimensione + "{" + paese + "}{" + autorita + "}{" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ zecca + "}{" + nominale + "}{" + id + "}"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		return out;

	}

	@Override
	public Object[] doWork(File inDir, File outDir, Object[] extraParam)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
