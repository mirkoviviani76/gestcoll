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
import main.GestLog;
import XmlData.Contatti.Contatti;

/**
 *
 * 
 */
public class ContattiXml extends GestXml {

	private Contatti contatti;

	/**
	 * Costruttore.
	 */
	public ContattiXml() {
		super(new File(Common.getCommon().getContattiXml()));
		try {
			JAXBContext jc = JAXBContext.newInstance("XmlData.Contatti");
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		contatti = (Contatti) unmarshaller.unmarshal(new File(Common.getCommon().getContattiXml()));
		} catch (JAXBException e) {
			GestLog.Error(this.getClass(), e);
		}
	}

	/**
	 * costruisce l'elenco dei contatti
	 * @return l'elenco
	 */
	public List<gestXml.data.Contatto> getContatti()
	{
		List<gestXml.data.Contatto> ret = new ArrayList<gestXml.data.Contatto>();
		List<XmlData.Contatti.Contatto> lista = contatti.getContatto();
		for (XmlData.Contatti.Contatto c : lista)
		{
			String nome = c.getNome();
			String email = c.getEmail();
			String note = c.getNote();
			gestXml.data.Contatto curr = new gestXml.data.Contatto(nome, email, note);
			ret.add(curr);
		}
		return ret;
	}
	

}
