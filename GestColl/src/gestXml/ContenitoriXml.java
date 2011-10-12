/*
 * Modifiche:
 * -
 */

package gestXml;

import exceptions.XmlException;
import gestXml.data.Armadio;
import gestXml.data.Contenitore;
import gestXml.data.DimensioneCaselle;
import gestXml.data.Vassoio;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.TransformerException;

import Resources.i18n.Messages;

import main.Common;
import main.GestLog;
import works.CollectionWorker;

/**
 * Gestisce i contenitori, utilzzando i dati di un xml
 * 
 */
public class ContenitoriXml extends GestXml {

	private HashMap<String, Armadio> armadi;

	/**
	 * Costruttore
	 * 
	 * @throws XmlException
	 */
	public ContenitoriXml() throws XmlException {
		super(new File(Common.getCommon().getContenitoriXml()));
		if (!new File(Common.getCommon().getContenitoriXml()).exists()) {
			GestLog.Message(this.getClass(), Messages.getString("ContenitoriXml.0"), true); //$NON-NLS-1$
			System.exit(-1);
		}
		armadi = new HashMap<String, Armadio>();
		// legge i dati dall'xml
		readXml();
	}

	/**
	 * aggiunge un vassoio ad un contenitore
	 * 
	 * @param contenitore
	 *            il contenitore
	 * @param curVass
	 *            il vassoio
	 * @param r
	 *            numero righe
	 * @param c
	 *            numero colonne
	 * @param dim
	 *            dimensione
	 */
	private void addVass(Contenitore contenitore, String curVass, String r,
			String c, String dim) {
		int vass = Integer.parseInt(curVass);
		int righe = Integer.parseInt(r);
		int colonne = Integer.parseInt(c);
		// aggiungere il vassoio
		DimensioneCaselle dc = null;
		if (dim.equals("A")) { //$NON-NLS-1$
			dc = DimensioneCaselle.A;
		}
		if (dim.equals("B")) { //$NON-NLS-1$
			dc = DimensioneCaselle.B;
		}
		if (dim.equals("C")) { //$NON-NLS-1$
			dc = DimensioneCaselle.C;
		}
		if (dim.equals("D")) { //$NON-NLS-1$
			dc = DimensioneCaselle.D;
		}
		Vassoio v = new Vassoio(vass, righe, colonne, dc);
		// aggiunge il vassoio
		contenitore.add(v);

	}

	/**
	 * ottiene l'armadio di default (SRI)
	 * 
	 * @return l'armadio
	 */
	public Armadio getArmadio() {
		return armadi.get("SRI"); //$NON-NLS-1$
	}

	/**
	 * ottiene l'armadio
	 * 
	 * @param id
	 *            l'id
	 * @return l'armadio
	 */
	public Armadio getArmadio(String id) {
		return armadi.get(id);
	}

	/**
	 * Ritorna una mappa la cui chiave e' l'id della moneta e il valore e' la
	 * posizione nel formato armadio-contenitore-vassoio-riga-colonna
	 * 
	 * @return la mappa costruita come sopra
	 * @throws FileNotFoundException
	 * @throws TransformerException
	 * @throws XmlException
	 */
	public HashMap<String, String> getMapIdPosizioni()
			throws FileNotFoundException, TransformerException, XmlException {
		/* ottiene l'elenco di tutte le monete */
		List<File> files = CollectionWorker.getCoinsFileListing();
		ListIterator<File> iterator = files.listIterator();
		HashMap<String, String> posizioniId = new HashMap<String, String>(
				files.size());

		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			MonetaXml mng = new MonetaXml((iterator.next()));
			String pos = getPosAsString(mng);
			posizioniId.put(mng.getId(), pos);
		}
		return posizioniId;
	}

	/**
	 * Ritorna una mappa la cui chiave e' la posizione nel formato
	 * armadio-contenitore-vassoio-riga-colonna e il valore e' l'id della moneta
	 * 
	 * @return la mappa costruita come sopra
	 * @throws FileNotFoundException
	 * @throws XmlException
	 */
	public HashMap<String, String> getMapPosizioniId()
			throws FileNotFoundException, XmlException {
		/* ottiene l'elenco di tutte le monete */
		List<File> files = CollectionWorker.getCoinsFileListing();
		ListIterator<File> iterator = files.listIterator();
		HashMap<String, String> posizioniId = new HashMap<String, String>(
				files.size());

		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			MonetaXml mng = new MonetaXml((iterator.next()));
			String cont = mng.getPosizione().getContenitore().toString();
			String vass = mng.getPosizione().getVassoio().toString();
			String riga = mng.getPosizione().getRiga().toString();
			String col = mng.getPosizione().getColonna().toString();
			String pos = this.getArmadio().nome + "-" + cont + "-" + vass + "-" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ riga + "-" + col; //$NON-NLS-1$
			posizioniId.put(pos, mng.getId());
		}
		return posizioniId;
	}

	/**
	 * ottiene la posizione come stringa
	 * 
	 * @param mng
	 *            l'oggetto moneta
	 * @return la posizione
	 */
	public String getPosAsString(MonetaXml mng) {
		String cont = mng.getPosizione().getContenitore().toString();
		String vass = mng.getPosizione().getVassoio().toString();
		String riga = mng.getPosizione().getRiga().toString();
		String col = mng.getPosizione().getColonna().toString();
		String pos = this.getArmadio().nome + "-" + cont + "-" + vass + "-" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ riga + "-" + col; //$NON-NLS-1$
		return pos;
	}

	/**
	 * legge il file xml
	 * 
	 * @throws XmlException
	 */
	private void readXml() throws XmlException {
		try {
			JAXBContext jc = JAXBContext.newInstance("XmlData.Contenitori"); //$NON-NLS-1$
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			/* posso fare il cast perche' le classi contengono @XmlRootElement se
			 * altrimenti si doveva fare JAXBElement<tipo> elem = (JAXBElement<tipo>)unmarshaller.unmarshal(xml)
			 * contenitori = elem.getValue();
			 */
			XmlData.Contenitori.Contenitori contenitori = (XmlData.Contenitori.Contenitori) unmarshaller
					.unmarshal(this.xmlFile);

			int contaArmadi = 1;
			int contaCont = 1;
			int contaVass = 1;
			// cicla su tutti gli armadi
			List<XmlData.Contenitori.Armadio> armadi = contenitori.getArmadio();
			for (XmlData.Contenitori.Armadio curArmadioItem : armadi) {
				// ottiene l'id dell'armadio corrente
				String curArmadio = curArmadioItem.getId();
				if (!curArmadio.equals("")) { //$NON-NLS-1$
					Armadio a = new Armadio(curArmadio);
					// cicla su tutti i contenitori
					List<XmlData.Contenitori.Contenitore> curcontenitori = curArmadioItem
							.getContenitore();
					for (XmlData.Contenitori.Contenitore curCont : curcontenitori) {
						// ottiene l'id del contenitore corrente
						int cont = Integer.parseInt(curCont.getId());
						Contenitore c = new Contenitore(cont);
						// cicla su tutti i vassoi
						List<XmlData.Contenitori.Vassoio> curVassoi = curCont
								.getVassoio();
						for (XmlData.Contenitori.Vassoio curVass : curVassoi) {
							// estrae i dati del vassoio corrente
							String righe = curVass.getRighe().toString();
							String cols = curVass.getColonne().toString();
							String dim = curVass.getDimensione();
							contaVass = contaVass + 1;
							// aggiunge il vassoio
							this.addVass(c, curVass.getId(), righe, cols, dim);
						}
						contaCont = contaCont + 1;
						// aggiunge il contenitore
						a.add(c);

					}
					contaArmadi = contaArmadi + 1;
					// aggiunge l'armadio
					this.armadi.put(curArmadio, a);
				}
			}
		} catch (JAXBException e) {
			throw new XmlException("ReadXml()", e); //$NON-NLS-1$
		}

	}

}
