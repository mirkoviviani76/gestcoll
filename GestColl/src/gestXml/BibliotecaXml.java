/*
 * Modifiche:
 * -
 */

package gestXml;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import main.Common;

/**
 *
 * 
 */
public class BibliotecaXml extends GestXml {


    private XmlData.Biblioteca.Biblioteca biblio;

    /**
     * Costruttore
     * @throws JAXBException 
     */
    public BibliotecaXml() throws JAXBException {
        super(new File(Common.BIBLIO_XML));
		JAXBContext jc = JAXBContext.newInstance("XmlData.Biblioteca");
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		biblio = (XmlData.Biblioteca.Biblioteca) unmarshaller.unmarshal(new File(Common.BIBLIO_XML));

    }

    /**
     * Carica i dati dei cataloghi d'asta prelevandoli dall'xml
     */
    public List<XmlData.Biblioteca.Catalogo> getCataloghi() {
        return this.biblio.getCataloghi().getCatalogo();
    }
  
    /**
     * Carica i dati dei cataloghi d'asta prelevandoli dall'xml
     */
    public List<XmlData.Biblioteca.Librotype> getLibri() {
        return this.biblio.getLibri().getLibro();
    }
    
  }
