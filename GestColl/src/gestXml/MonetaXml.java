/*
 * Modifiche:
 * 28.01.11 sistemato il caso in cui la data non c'e'
 */
package gestXml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import main.Common;
import main.Common.Lato;
import main.GestLog;
import XmlData.Moneta.Autorita;
import XmlData.Moneta.DatiAcquisto;
import XmlData.Moneta.DatiArtistici;
import XmlData.Moneta.DatiFisici;
import XmlData.Moneta.Descrizioni;
import XmlData.Moneta.DocumentoAddizionale;
import XmlData.Moneta.Legenda;
import XmlData.Moneta.Libro;
import XmlData.Moneta.Misura;
import XmlData.Moneta.Nominale;
import XmlData.Moneta.Posizione;
import XmlData.Moneta.Zecca;
import XmlData.Moneta.Zecchiere;
import exceptions.XmlException;

/**
 * Classe per gestire il file xml di una moneta.
 * 
 */
public class MonetaXml extends GestXml implements Comparable<MonetaXml>,
		Tooltipper {

	public static enum Fields {
		ANNO, DATA, DESCRIZIONE_D, DESCRIZIONE_R, DESCRIZIONE_T, DIAMETRO_M, DIAMETRO_V, FORMA, LAST, LUOGO, METALLO, PAESE, PESO_M, PESO_V, PREZZO_M, PREZZO_V, VALORE, VALUTA, ZECCA_N, ZECCA_S
	}

	public static enum Ordering {
		BY_ID, BY_PAESE
	}

	private XmlData.Moneta.Moneta moneta;

	private Ordering ordering;
	private String path;

	/**
	 * Il file della moneta
	 * 
	 * @param _xmlFile
	 * @throws XmlException
	 */
	public MonetaXml(File _xmlFile) throws XmlException {
		super(_xmlFile);
		this.path = _xmlFile.getParent();
		ordering = Ordering.BY_ID;
		try {
			JAXBContext jc = JAXBContext.newInstance("XmlData.Moneta");
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			moneta = (XmlData.Moneta.Moneta) unmarshaller
					.unmarshal(new InputStreamReader(new FileInputStream(
							_xmlFile), "UTF-8"));
		} catch (JAXBException e) {
			throw new XmlException("MonetaXml(): JAXB exception reading "
					+ _xmlFile, e);
		} catch (UnsupportedEncodingException e) {
			throw new XmlException("MonetaXml(): Encoding error reading "
					+ _xmlFile, e);
		} catch (FileNotFoundException e) {
			throw new XmlException("MonetaXml(): File not found "
					+ _xmlFile, e);
		}
	}

	/**
	 * fornisce una relazione d'ordine. Gli id contenenti X vanno prima
	 * nell'ambito del loro secolo. Ordina prima in base all'anno, e poi in base
	 * al progressivo. Fornisce anche un'ordinamento per paese.
	 * 
	 * @param t
	 *            l'oggetto moneta
	 * @return l'ordinamento
	 */
	@Override
	public int compareTo(MonetaXml t) {
		int ret = 0;

		// TODO aggiungere l'ordinamento per "ultima revisione"
		if (this.getOrdering() == Ordering.BY_ID) {
			String id1 = "";
			String id2 = "";
			id1 = this.moneta.getId();
			id2 = t.moneta.getId();
			// sostituisce X con spazio (' ' precede numeri e lettere)
			id1 = id1.replace('X', ' ');
			id2 = id2.replace('X', ' ');
			// se entrambi cominciano con lo stesso valore, controlla il
			// progressivo
			if (id1.substring(0, 4).equals(id2.substring(0, 4))) {
				String progressivo1 = id1.split("-")[2];
				String progressivo2 = id2.split("-")[2];
				ret = progressivo1.compareTo(progressivo2);
			} else {
				ret = id1.compareTo(id2);
			}
		} else if (this.getOrdering() == Ordering.BY_PAESE) {
			String paese1 = "";
			String paese2 = "";
			paese1 = this.moneta.getPaese();
			paese2 = t.moneta.getPaese();
			ret = paese1.compareTo(paese2);

		}
		return ret;
	}

	/**
	 * 
	 * @return l'anno
	 */
	public String getAnno() {
		return this.moneta.getAnno();
	}

	/**
	 * 
	 * @return le autorita
	 */
	public Autorita getAutorita() {
		return this.moneta.getAutorita();
	}

	/**
	 * 
	 * @return la data
	 */
	public String getData() {
		String ret = "";
		if (this.moneta.getDatiAcquisto() != null
				&& this.moneta.getDatiAcquisto().getData() != null) {
			ret = this.moneta.getDatiAcquisto().getData().toString();
		}
		return ret;
	}

	/**
	 * 
	 * @param lato
	 *            il lato
	 * @return la descrizione del lato
	 */
	public String getDescrizione(Lato lato) {
		String s = "";
		if (lato == Common.Lato.DRITTO
				&& this.moneta.getDatiArtistici().getDritto() != null) {
			s = this.moneta.getDatiArtistici().getDritto().getDescrizione();
		}
		if (lato == Common.Lato.ROVESCIO
				&& this.moneta.getDatiArtistici().getRovescio() != null) {
			s = this.moneta.getDatiArtistici().getRovescio().getDescrizione();
		}
		if (lato == Common.Lato.TAGLIO
				&& this.moneta.getDatiArtistici().getTaglio() != null) {
			s = this.moneta.getDatiArtistici().getTaglio().getDescrizione();
		}
		return s;

	}

	/**
	 * 
	 * @return il diametro
	 */
	public Misura getDiametro() {
		return this.moneta.getDatiFisici().getDiametro();
	}

	/**
	 * 
	 * @param lato
	 *            il lato
	 * @return il file immagine del lato
	 */
	public String getFileImmagine(Lato lato) {
		String s = "";
		if (lato == Common.Lato.DRITTO
				&& this.moneta.getDatiArtistici().getDritto() != null) {
			s = this.moneta.getDatiArtistici().getDritto().getFileImmagine();
		}
		if (lato == Common.Lato.ROVESCIO
				&& this.moneta.getDatiArtistici().getRovescio() != null) {
			s = this.moneta.getDatiArtistici().getRovescio().getFileImmagine();
		}
		if (lato == Common.Lato.TAGLIO
				&& this.moneta.getDatiArtistici().getTaglio() != null) {
			s = this.moneta.getDatiArtistici().getTaglio().getFileImmagine();
		}
		return this.getPath() + "/" + s;

	}

	/**
	 * 
	 * @return la forma
	 */
	public String getForma() {
		return this.moneta.getDatiFisici().getForma();
	}

	/**
	 * ottiene l'id
	 * 
	 * @return l'id
	 */
	public String getId() {
		return this.moneta.getId();
	}

	/**
	 * 
	 * @return i documenti
	 */
	public List<DocumentoAddizionale> getItemAddizionali() {
		return this.moneta.getItemAddizionali().getDocumento();
	}

	/**
	 * @return l'oggetto moneta
	 */
	public XmlData.Moneta.Moneta getJaxbObject() {
		return moneta;
	}

	/**
	 * 
	 * @param lato
	 *            il lato
	 * @return le legende del lato
	 */
	public List<Legenda> getLegende(Lato lato) {
		List<Legenda> s = null;
		if (lato == Common.Lato.DRITTO
				&& this.moneta.getDatiArtistici().getDritto() != null) {
			s = this.moneta.getDatiArtistici().getDritto().getLegenda();
		}
		if (lato == Common.Lato.ROVESCIO
				&& this.moneta.getDatiArtistici().getRovescio() != null) {
			s = this.moneta.getDatiArtistici().getRovescio().getLegenda();
		}
		if (lato == Common.Lato.TAGLIO
				&& this.moneta.getDatiArtistici().getTaglio() != null) {
			s = this.moneta.getDatiArtistici().getTaglio().getLegenda();
		}
		return s;

	}

	/**
	 * 
	 * @return la letteratura
	 */
	public List<Libro> getLetteratura() {
		List<Libro> ret = null;
		if (this.moneta.getLetteratura() != null) {
			ret = this.moneta.getLetteratura().getLibro();
		}
		return ret;
	}

	/**
	 * 
	 * @return il luogo
	 */
	public String getLuogo() {

		return this.moneta.getDatiAcquisto().getLuogo();
	}

	/**
	 * 
	 * @return il metallo
	 */
	public String getMetallo() {
		return this.moneta.getDatiFisici().getMetallo();
	}

	/**
	 * ottiene il nominale
	 * 
	 * @return il nominale
	 */
	public Nominale getNominale() {
		return this.moneta.getNominale();
	}

	/**
	 * 
	 * @return le note
	 */
	public List<String> getNote() {
		List<String> ret = null;
		if (this.moneta.getNote() != null) {
			ret = this.moneta.getNote().getNota();
		}
		return ret;
	}

	/**
	 * @return the ordering
	 */
	public Ordering getOrdering() {
		return ordering;
	}

	/**
	 * 
	 * @return il paese
	 */
	public String getPaese() {
		return this.moneta.getPaese();
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 
	 * @return il peso
	 */
	public Misura getPeso() {
		return this.moneta.getDatiFisici().getPeso();
	}

	/**
	 * 
	 * @return la posizione
	 */
	public Posizione getPosizione() {
		return this.moneta.getPosizione();
	}

	/**
	 * 
	 * @return il prezzo
	 */
	public Misura getPrezzo() {
		Misura ret = new Misura();
		if (this.moneta.getDatiAcquisto() != null) {
			ret = this.moneta.getDatiAcquisto().getPrezzo();
		}
		return ret;
	}

	/**
	 * Ottiene la data dell'ultima revisione come stringa
	 * 
	 * @return la data
	 */
	public String getRevisione() {
		String ret = "";
		if (this.moneta.getRevisione() != null) {
			ret = this.moneta.getRevisione().toString();
		}
		return ret;
	}

	/**
	 * 
	 * @return la zecca
	 */
	public Zecca getZecca() {
		return this.moneta.getZecca();
	}

	/**
	 * 
	 * @return gli zecchieri
	 */
	public List<Zecchiere> getZecchieri() {
		List<Zecchiere> ret = null;
		if (this.moneta.getZecchieri() != null) {
			ret = this.moneta.getZecchieri().getZecchiere();
		}
		return ret;
	}

	/**
	 * sostituisce un elemento nella lista
	 * 
	 * @param list
	 *            la lista da modificare
	 * @param oldVal
	 *            l'elemento da sostituire
	 * @param newVal
	 *            il sostituto
	 */
	private <T> void modify(List<T> list, T oldVal, T newVal) {
		int index = list.indexOf(oldVal);
		if (index >= 0) {
			list.set(index, newVal);
		}

	}

	/**
	 * modifica l'autorita
	 * 
	 * @param autorita
	 *            la vecchia autorita
	 * @param nuovaAutorita
	 *            la nuova autorita
	 */
	public void modifyAutorita(String autorita, String nuovaAutorita) {
		List<String> lista = this.getAutorita().getNome();
		this.modify(lista, autorita, nuovaAutorita);
	}

	/**
	 * modifica il documento
	 * 
	 * @param doc
	 *            il vecchio documento
	 * @param nuovoDoc
	 *            il nuovo documento
	 */
	public void modifyDocumento(DocumentoAddizionale doc,
			DocumentoAddizionale nuovoDoc) {
		List<DocumentoAddizionale> lista = this.getItemAddizionali();
		this.modify(lista, doc, nuovoDoc);

	}

	/**
	 * modifica il libro
	 * 
	 * @param libro
	 *            il vecchio valore
	 * @param nuovoLibro
	 *            il nuovo valore
	 */
	public void modifyLibro(Libro libro, Libro nuovoLibro) {
		List<Libro> lista = this.getLetteratura();
		this.modify(lista, libro, nuovoLibro);
	}

	/**
	 * modifica la nota
	 * 
	 * @param nota
	 *            il vecchio valore
	 * @param nuovaNota
	 *            il nuovo valore
	 */
	public void modifyNota(String nota, String nuovaNota) {
		List<String> lista = this.getNote();
		this.modify(lista, nota, nuovaNota);
	}

	/**
	 * modifica lo zecchiere
	 * 
	 * @param zecchiere
	 *            il vecchio valore
	 * @param nuovoZecchiere
	 *            il nuovo valore
	 */
	public void modifyZecchiere(Zecchiere zecchiere, Zecchiere nuovoZecchiere) {
		List<Zecchiere> lista = this.getZecchieri();
		this.modify(lista, zecchiere, nuovoZecchiere);
	}
	

	/**
	 * modifica la legenda
	 * @param lato
	 * @param legenda il vecchio valore
	 * @param nuovaLegenda il nuovo valore
	 */
	public void modifyLegenda(Lato lato, Legenda legenda, Legenda nuovaLegenda) {
		List<Legenda> lista = this.getLegende(lato);
		this.modify(lista, legenda, nuovaLegenda);
	}
	

	/**
	 * @param ordering
	 *            the ordering to set
	 */
	public void setOrdering(Ordering ordering) {
		this.ordering = ordering;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Salva i valori modificati del campo
	 * 
	 * @param field
	 *            il campo da aggiornare
	 * @param value
	 *            il nuovo valore
	 */
	public void setValue(Fields field, String value) {
		switch (field) {
		case PAESE:
			this.moneta.setPaese(value);
			break;
		case ANNO:
			this.moneta.setAnno(value);
			break;
		case LUOGO:
			if (this.moneta.getDatiAcquisto() == null) {
				this.moneta.setDatiAcquisto(new DatiAcquisto());
			}
			this.moneta.getDatiAcquisto().setLuogo(value);
			break;
		case METALLO:
			if (this.moneta.getDatiFisici() == null) {
				this.moneta.setDatiFisici(new DatiFisici());
			}
			this.moneta.getDatiFisici().setMetallo(value);
			break;
		case FORMA:
			if (this.moneta.getDatiFisici() == null) {
				this.moneta.setDatiFisici(new DatiFisici());
			}
			this.moneta.getDatiFisici().setForma(value);
			break;
		case DESCRIZIONE_D:
			if (this.moneta.getDatiArtistici() == null) {
				this.moneta.setDatiArtistici(new DatiArtistici());
			}
			if (this.moneta.getDatiArtistici().getDritto() == null) {
				this.moneta.getDatiArtistici().setDritto(new Descrizioni());
			}
			this.moneta.getDatiArtistici().getDritto().setDescrizione(value);
			break;
		case DESCRIZIONE_R:
			if (this.moneta.getDatiArtistici() == null) {
				this.moneta.setDatiArtistici(new DatiArtistici());
			}
			if (this.moneta.getDatiArtistici().getRovescio() == null) {
				this.moneta.getDatiArtistici().setRovescio(new Descrizioni());
			}
			this.moneta.getDatiArtistici().getRovescio().setDescrizione(value);
			break;
		case DESCRIZIONE_T:
			if (this.moneta.getDatiArtistici() == null) {
				this.moneta.setDatiArtistici(new DatiArtistici());
			}
			if (this.moneta.getDatiArtistici().getTaglio() == null) {
				this.moneta.getDatiArtistici().setTaglio(new Descrizioni());
			}
			this.moneta.getDatiArtistici().getTaglio().setDescrizione(value);
			break;
		case PESO_V:
			if (this.moneta.getDatiFisici() == null) {
				this.moneta.setDatiFisici(new DatiFisici());
			}
			this.moneta.getDatiFisici().getPeso()
					.setValore(new BigDecimal(value));
			break;
		case PESO_M:
			if (this.moneta.getDatiFisici() == null) {
				this.moneta.setDatiFisici(new DatiFisici());
			}
			this.moneta.getDatiFisici().getPeso().setUnita(value);
			break;
		case DIAMETRO_V:
			if (this.moneta.getDatiFisici() == null) {
				this.moneta.setDatiFisici(new DatiFisici());
			}
			this.moneta.getDatiFisici().getDiametro()
					.setValore(new BigDecimal(value));
			break;
		case DIAMETRO_M:
			if (this.moneta.getDatiFisici() == null) {
				this.moneta.setDatiFisici(new DatiFisici());
			}
			this.moneta.getDatiFisici().getDiametro().setUnita(value);
			break;
		case PREZZO_V:
			if (this.moneta.getDatiAcquisto() == null) {
				this.moneta.setDatiAcquisto(new DatiAcquisto());
			}
			this.moneta.getDatiAcquisto().getPrezzo()
					.setValore(new BigDecimal(value));
			break;
		case PREZZO_M:
			if (this.moneta.getDatiAcquisto() == null) {
				this.moneta.setDatiAcquisto(new DatiAcquisto());
			}
			this.moneta.getDatiAcquisto().getPrezzo().setUnita(value);
			break;
		case VALORE:
			this.moneta.getNominale().setValore(value);
			break;
		case VALUTA:
			this.moneta.getNominale().setValuta(value);
			break;
		case ZECCA_N:
			if (this.moneta.getZecca() == null) {
				this.moneta.setZecca(new Zecca());
			}
			this.moneta.getZecca().setNome(value);
			break;
		case ZECCA_S:
			if (this.moneta.getZecca() == null) {
				this.moneta.setZecca(new Zecca());
			}
			this.moneta.getZecca().setSegno(value);
			break;
		case DATA:
			if (this.moneta.getDatiAcquisto() == null) {
				this.moneta.setDatiAcquisto(new DatiAcquisto());
			}
			try {
				XMLGregorianCalendar cal = DatatypeFactory.newInstance()
						.newXMLGregorianCalendar(value);
				this.moneta.getDatiAcquisto().setData(cal);
			} catch (DatatypeConfigurationException e1) {
				GestLog.Error(this.getClass(), e1);
			}
			break;
		default:
			GestLog.Message(this.getClass(), "SetValue: Campo sconosciuto: "
					+ field.toString(), true);
			break;
		}
		// modifica la data dell'ultima revisione
		GregorianCalendar now = new GregorianCalendar();
		try {
			XMLGregorianCalendar cal;
			// TODO verificare se la timezone fa casino
			cal = DatatypeFactory.newInstance().newXMLGregorianCalendar(now);
			this.moneta.setRevisione(cal);
		} catch (DatatypeConfigurationException e) {
			GestLog.Error(this.getClass(), e);
		}

	}

	/**
	 * Ottiene una stringa di dati completi.
	 * 
	 * @return la stringa
	 */
	public String toFullText() {
		String s = "";
		s = s
				+ String.format("%s: %s %s (%s)", this.moneta.getPaese(),
						this.moneta.getNominale().getValore(), this.moneta
								.getNominale().getValuta(), this.moneta
								.getAnno()) + "\n";
		List<String> nomi = this.moneta.getAutorita().getNome();
		for (String nome : nomi) {
			s = s + nome + ", ";
		}
		s = s + "\n";
		return s;
	}

	@Override
	public String toString() {
		String s = "";
		s = String
				.format("%s, %s", this.moneta.getId(), this.moneta.getPaese());
		return s;
	}

	@Override
	public String toTooltip() {
		String tooltip = "";
		// imgD = mng.getImg(MonetaXml.lato.DRITTO);
		tooltip = this.moneta.getNominale().getValore() + " "
				+ this.moneta.getNominale().getValuta();
		return tooltip;
	}

	
}
