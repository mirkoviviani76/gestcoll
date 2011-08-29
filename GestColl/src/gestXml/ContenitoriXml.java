/*
 * Modifiche:
 * -
 */

package gestXml;

import gestXml.data.Armadio;
import gestXml.data.Contenitore;
import gestXml.data.DimensioneCaselle;
import gestXml.data.Vassoio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.TransformerException;

import main.Common;
import main.GestLog;

import org.xml.sax.SAXException;

import works.CollectionWorker;

/**
 *
 * 
 */
public class ContenitoriXml extends GestXml {

	private HashMap<String, Armadio> armadi;

	/**
	 * Costruttore
	 */
	public ContenitoriXml() {
		super(new File(Common.getCommon().getContenitoriXml()));
		armadi = new HashMap<String, Armadio>();
		// legge i dati dall'xml
		try {
			readXml();
		} catch (JAXBException e) {
			GestLog.Error(this.getClass(), e);
		}
	}

	private void addVass(Contenitore contenitore, String curVass, String r,
			String c, String dim) {
		int vass = Integer.parseInt(curVass);
		int righe = Integer.parseInt(r);
		int colonne = Integer.parseInt(c);
		// aggiungere il vassoio
		DimensioneCaselle dc = null;
		if (dim.equals("A")) {
			dc = DimensioneCaselle.A;
		}
		if (dim.equals("B")) {
			dc = DimensioneCaselle.B;
		}
		if (dim.equals("C")) {
			dc = DimensioneCaselle.C;
		}
		if (dim.equals("D")) {
			dc = DimensioneCaselle.D;
		}
		Vassoio v = new Vassoio(vass, righe, colonne, dc);
		// aggiunge il vassoio
		contenitore.add(v);

	}

	/**
	 * 
	 * @throws JAXBException
	 */
	private void readXml() throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance("XmlData.Contenitori");
		Unmarshaller unmarshaller = jc.createUnmarshaller();
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
			if (!curArmadio.equals("")) {
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
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Armadio getArmadio(String id) {
		return armadi.get(id);
	}

	/**
	 * 
	 * @return
	 */
	public Armadio getArmadio() {
		return armadi.get("SRI");
	}

	/**
	 * Ritorna una mappa la cui chiave e' la posizione nel formato
	 * armadio-contenitore-vassoio-riga-colonna e il valore e' l'id della moneta
	 * 
	 * @return la mappa costruita come sopra
	 * @throws FileNotFoundException
	 */
	public HashMap<String, String> getMapPosizioniId()
			throws FileNotFoundException {
		/* ottiene l'elenco di tutte le monete */
		List<File> files = CollectionWorker.getFileListing(new File(
				Common.getCommon().getMoneteDir()), Common.COIN_END);
		ListIterator<File> iterator = files.listIterator();
		HashMap<String, String> posizioniId = new HashMap<String, String>(
				files.size());

		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			MonetaXml mng;
			try {
				mng = new MonetaXml((iterator.next()));
				String cont = mng.getPosizione().getContenitore().toString();
				String vass = mng.getPosizione().getVassoio().toString();
				String riga = mng.getPosizione().getRiga().toString();
				String col = mng.getPosizione().getColonna().toString();
				String pos = this.getArmadio().nome + "-" + cont + "-" + vass
						+ "-" + riga + "-" + col;
				posizioniId.put(pos, mng.getId());
			} catch (JAXBException e) {
				GestLog.Error(this.getClass(), e);
			}
		}
		return posizioniId;
	}

	/**
	 * Ritorna una mappa la cui chiave e' l'id della moneta e il valore e' la
	 * posizione nel formato armadio-contenitore-vassoio-riga-colonna
	 * 
	 * @return la mappa costruita come sopra
	 * @throws SAXException
	 * @throws IOException
	 * @throws TransformerException
	 */
	public HashMap<String, String> getMapIdPosizioni() throws SAXException,
			IOException, TransformerException {
		/* ottiene l'elenco di tutte le monete */
		List<File> files = CollectionWorker.getFileListing(new File(
				Common.getCommon().getMoneteDir()), Common.COIN_END);
		ListIterator<File> iterator = files.listIterator();
		HashMap<String, String> posizioniId = new HashMap<String, String>(
				files.size());

		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			MonetaXml mng;
			try {
				mng = new MonetaXml((iterator.next()));
				String pos = getPosAsString(mng);
				posizioniId.put(mng.getId(), pos);
			} catch (JAXBException e) {
				GestLog.Error(this.getClass(), e);
			}
		}
		return posizioniId;
	}

	/**
	 * 
	 * @param mng
	 * @return
	 * @throws TransformerException
	 */
	public String getPosAsString(MonetaXml mng) throws TransformerException {
		String cont = mng.getPosizione().getContenitore().toString();
		String vass = mng.getPosizione().getVassoio().toString();
		String riga = mng.getPosizione().getRiga().toString();
		String col = mng.getPosizione().getColonna().toString();
		String pos = this.getArmadio().nome + "-" + cont + "-" + vass + "-"
				+ riga + "-" + col;
		return pos;
	}

}
