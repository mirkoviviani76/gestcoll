//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.08.22 at 10:19:40 PM CEST 
//

package XmlData.Moneta;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Tipo che definisce una descrizione
 * 
 * <p>
 * Java class for descrizioni complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="descrizioni">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrizione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="legenda" type="{}legenda" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="fileImmagine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "descrizioni", propOrder = { "descrizione", "legenda",
		"fileImmagine" })
public class Descrizioni {

	@XmlElement(required = true)
	protected String descrizione;
	protected String fileImmagine;
	protected List<Legenda> legenda;

	/**
	 * Gets the value of the descrizione property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * Gets the value of the fileImmagine property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getFileImmagine() {
		return fileImmagine;
	}

	/**
	 * Gets the value of the legenda property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a
	 * snapshot. Therefore any modification you make to the returned list will
	 * be present inside the JAXB object. This is why there is not a
	 * <CODE>set</CODE> method for the legenda property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getLegenda().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link Legenda }
	 * 
	 * 
	 */
	public List<Legenda> getLegenda() {
		if (legenda == null) {
			legenda = new ArrayList<Legenda>();
		}
		return this.legenda;
	}

	/**
	 * Sets the value of the descrizione property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDescrizione(String value) {
		this.descrizione = value;
	}

	/**
	 * Sets the value of the fileImmagine property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setFileImmagine(String value) {
		this.fileImmagine = value;
	}

}