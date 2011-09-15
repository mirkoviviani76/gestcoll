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
import XmlData.Links.Linklist;
import exceptions.XmlException;
import gestXml.data.Link;

/**
 *
 *
 */
public class LinksXml extends GestXml {

	private ArrayList<gestXml.data.Link> links;
	private XmlData.Links.Links xmllinks;

	/**
	 * Costruttore
	 * 
	 * @throws XmlException
	 */
	public LinksXml() throws XmlException {
		super(new File(Common.getCommon().getLinksXml()));
		links = new ArrayList<gestXml.data.Link>();
		try {
			JAXBContext jc = JAXBContext.newInstance("XmlData.Links");
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			/* posso fare il cast perche' le classi contengono @XmlRootElement se
			 * altrimenti si doveva fare JAXBElement<tipo> elem = (JAXBElement<tipo>)unmarshaller.unmarshal(xml)
			 * xmllinks = elem.getValue();
			 */
			xmllinks = (XmlData.Links.Links) unmarshaller.unmarshal(new File(
					Common.getCommon().getLinksXml()));

			List<Linklist> listacat = xmllinks.getCategoria();
			for (Linklist categoria : listacat) {
				for (XmlData.Links.Link l : categoria.getLink()) {
					gestXml.data.Link curLink = new gestXml.data.Link(
							categoria.getId(), l.getNome(), l.getUrl(),
							l.getNote());
					this.links.add(curLink);
				}

			}
		} catch (JAXBException e) {
			throw new XmlException("LinksXml()", e);
		}

	}

	/**
	 * ottiene l'array di links
	 * 
	 * @return la lista
	 */
	public ArrayList<gestXml.data.Link> getLinks() {
		return this.links;
	}
	
	
	/**
	 * aggiunge un contatto
	 * @param nuovo
	 * @throws XmlException 
	 */
	public void add(Link nuovo) throws XmlException {
		XmlData.Links.Link c = new XmlData.Links.Link();
		c.setNome(nuovo.nome);
		c.setUrl(nuovo.url.toString());
		c.setNote(nuovo.note);
		/* cerca la categoria "giusta" e aggiunge il link */
		List<Linklist> tutti = this.xmllinks.getCategoria();
		for (Linklist curr : tutti) {
			if (curr.getId().equals(nuovo.categoria)) {
				curr.getLink().add(c);
				break;
			}
		}
		
	}

	public Object getJaxbObject() {
		return this.xmllinks;
	}

}
