/*
 * Modifiche:
 * -
 */

package gestXml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import main.Common;
import exceptions.XmlException;
import gestXml.data.Catalogo;
import gestXml.data.Libro;
import gestXml.data.Pubblicazione;

/**
 *
 * 
 */
public class BibliotecaXml extends GestXml {

	private XmlData.Biblioteca.Biblioteca biblio;

	/**
	 * Costruttore
	 * 
	 * @throws XmlException
	 */
	public BibliotecaXml() throws XmlException {
		super(new File(Common.getCommon().getBiblioXml()));
		File xml = new File(Common.getCommon().getBiblioXml());
		try {
			JAXBContext jc = JAXBContext.newInstance("XmlData.Biblioteca");
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			biblio = (XmlData.Biblioteca.Biblioteca) unmarshaller
					.unmarshal(xml);
		} catch (JAXBException e) {
			throw new XmlException("BibliotecaXml(), file: "+xml.getAbsolutePath(), e);
		}

	}

	/**
	 * Carica i dati dei cataloghi d'asta prelevandoli dall'xml
	 */
	public List<XmlData.Biblioteca.Catalogo> getCataloghi() {
		return this.biblio.getCataloghi().getCatalogo();
	}

	/**
	 * carica i dati
	 * 
	 * @return la lista di pubblicazioni
	 */
	public List<Pubblicazione> getItems() {
		List<XmlData.Biblioteca.Catalogo> cataloghi = this.getCataloghi();
		List<XmlData.Biblioteca.Librotype> libri = this.getLibri();
		List<Pubblicazione> ret = new ArrayList<Pubblicazione>();
		/* cicla su tutti i libri nell'xml */
		for (XmlData.Biblioteca.Librotype l : libri) {
			/* costruisce il dato pubblicazione */
			Libro libro = new Libro();
			libro.setTitolo(l.getTitolo());
			if (l.getSupporti() != null) {
				libro.setSupporti(l.getSupporti().getSupporto());
			}
			if (l.getAutori() != null) {
				libro.setAutori(l.getAutori().getAutore());
			}
			libro.setFilename(l.getFilename());
			libro.setId(l.getId());
			ret.add(libro);
		}
		/* cicla su tutti i cataloghi nell'xml */
		for (XmlData.Biblioteca.Catalogo c : cataloghi) {
			/* costruisce il dato pubblicazione */
			Catalogo catalogo = new Catalogo();
			if (c.getArgomenti() != null) {
				catalogo.setArgomenti(c.getArgomenti().getArgomento());
			}
			if (c.getAutori() != null) {
				catalogo.setAutori(c.getAutori().getAutore());
			}
			catalogo.setData(c.getData());
			catalogo.setFilename(c.getFilename());
			catalogo.setNumero(c.getNumero());
			if (c.getSupporti() != null) {
				catalogo.setSupporti(c.getSupporti().getSupporto());
			}
			ret.add(catalogo);
		}
		return ret;

	}

	/**
	 * Carica i dati dei cataloghi d'asta prelevandoli dall'xml
	 */
	public List<XmlData.Biblioteca.Librotype> getLibri() {
		return this.biblio.getLibri().getLibro();
	}

}
