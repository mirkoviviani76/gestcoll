/*
 * Modifiche:
 * -
 */
package gestXml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Classe per la gestione di un generico file xml. Fornisce alcuni metodi basi
 * per l'apertura, la lettura, la scrittura e l'estrazione di dati.
 * 
 * @author furetto76
 */
public class GestXml {
	/**
	 * il file xml
	 */
	protected File xmlFile;

	/**
	 * costruttore
	 * 
	 * @param _xmlFile il file xml
	 */
	public GestXml(File _xmlFile) {
		xmlFile = _xmlFile;
	}

	/**
	 * Scrive nel file "out" il Documento xmldoc
	 * @param myJAXBObject l'oggetto jaxb
	 * @param jaxbContext il contesto
	 * @param out il file out
	 * @throws JAXBException
	 */
	public void writeXml(Object myJAXBObject, String jaxbContext, String out) throws JAXBException
	 {
		JAXBContext jc = JAXBContext.newInstance(jaxbContext);
		//Crea il marshaller
		Marshaller m = jc.createMarshaller();
		//richiede un output formattato
		m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
		//Esegue il marshalling dell'oggetto nel file.
		m.marshal(myJAXBObject, new File(out));
		
	}

	/**
	 * Converte utilizzando un xslt
	 * 
	 * @param xslt il foglio xslt utilizzato per la conversione
	 * @param outFile il file di output generato
	 * @throws TransformerException 
	 * @throws FileNotFoundException 
	 */
	public void xsltConvert(File xslt, File outFile) throws FileNotFoundException, TransformerException {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory
				.newTransformer(new StreamSource(xslt));
		transformer.transform(new StreamSource(this.xmlFile), new StreamResult(
				new FileOutputStream(outFile)));
	}

	/**
	 * Converte in stringa utilizzando un xslt
	 * 
	 * @param xslt il foglio xslt utilizzato per la conversione
	 * @return la stringa con la conversione effettuata
	 * @throws TransformerException 
	 */
	public String xsltConvert(File xslt) throws TransformerException {
		StringWriter strWriter = new StringWriter();
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory
				.newTransformer(new StreamSource(xslt));
		transformer.transform(new StreamSource(this.xmlFile), new StreamResult(
				strWriter));
		return strWriter.toString();
	}


}
