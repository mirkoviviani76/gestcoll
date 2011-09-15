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
import XmlData.Contatti.Contatti;
import exceptions.XmlException;
import gestXml.data.Contatto;

/**
 *
 * 
 */
public class ContattiXml extends GestXml {

	private Contatti contatti;

	/**
	 * Costruttore.
	 * 
	 * @throws XmlException
	 */
	public ContattiXml() throws XmlException {
		super(new File(Common.getCommon().getContattiXml()));
		try {
			JAXBContext jc = JAXBContext.newInstance("XmlData.Contatti");
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			/* posso fare il cast perche' le classi contengono @XmlRootElement se
			 * altrimenti si doveva fare JAXBElement<tipo> elem = (JAXBElement<tipo>)unmarshaller.unmarshal(xml)
			 * contatti = elem.getValue();
			 */
			contatti = (Contatti) unmarshaller.unmarshal(new File(Common
					.getCommon().getContattiXml()));
		} catch (JAXBException e) {
			throw new XmlException("ContattiXml", e);
		}
	}

	/**
	 * @return l'oggetto moneta
	 */
	public XmlData.Contatti.Contatti getJaxbObject() {
		return contatti;
	}
	

	/**
	 * costruisce l'elenco dei contatti
	 * 
	 * @return l'elenco
	 */
	public List<gestXml.data.Contatto> getContatti() {
		List<gestXml.data.Contatto> ret = new ArrayList<gestXml.data.Contatto>();
		List<XmlData.Contatti.Contatto> lista = contatti.getContatto();
		for (XmlData.Contatti.Contatto c : lista) {
			String nome = c.getNome();
			String email = c.getEmail();
			String note = c.getNote();
			gestXml.data.Contatto curr = new gestXml.data.Contatto(nome, email,
					note);
			ret.add(curr);
		}
		return ret;
	}

	/**
	 * aggiunge un contatto
	 * @param nuovo
	 */
	public void add(Contatto nuovo) {
		XmlData.Contatti.Contatto c = new XmlData.Contatti.Contatto();
		c.setNome(nuovo.nome);
		c.setEmail(nuovo.email);
		c.setNote(nuovo.note);
		this.contatti.getContatto().add(c);
	}

}
