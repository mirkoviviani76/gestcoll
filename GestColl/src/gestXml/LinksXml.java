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

import exceptions.XmlException;

import main.Common;
import main.GestLog;
import XmlData.Links.Linklist;

/**
 *
 *
 */
public class LinksXml extends GestXml {

	private ArrayList<gestXml.data.Link> links;
    private XmlData.Links.Links xmllinks;
	
	/**
	 * Costruttore
	 * @throws XmlException 
	 */
    public LinksXml() throws XmlException {
    	super(new File(Common.getCommon().getLinksXml()));
    	links = new ArrayList<gestXml.data.Link>();
    	try {
    		JAXBContext jc = JAXBContext.newInstance("XmlData.Links");
    		Unmarshaller unmarshaller = jc.createUnmarshaller();
    		xmllinks = (XmlData.Links.Links) unmarshaller.unmarshal(new File(Common.getCommon().getLinksXml()));
    		
    		List<Linklist> listacat = xmllinks.getCategoria();
    		for (Linklist categoria : listacat)
    		{
    			for (XmlData.Links.Link l : categoria.getLink()) {
    				gestXml.data.Link curLink = new gestXml.data.Link(categoria.getId(), l.getNome(), l.getUrl(), l.getNote());
    				this.links.add(curLink);
    			}

    		}
    	} catch (JAXBException e) {
    		throw new XmlException("LinksXml()", e);
    	}

	}


	/**
	 * ottiene l'array di links 
	 * @return la lista
	 */
	public ArrayList<gestXml.data.Link> getLinks() {
		return this.links;
	}

}
