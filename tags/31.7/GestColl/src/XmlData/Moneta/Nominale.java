//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.08.22 at 10:19:40 PM CEST 
//

package XmlData.Moneta;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Tipo che definisce un nominale
 * 
 * <p>
 * Java class for nominale complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="nominale">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="valuta" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nominale", propOrder = { "valuta", "valore" })
public class Nominale {

	@XmlElement(required = true)
	protected String valore;
	@XmlElement(required = true)
	protected String valuta;

	/**
	 * Gets the value of the valore property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getValore() {
		return valore;
	}

	/**
	 * Gets the value of the valuta property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getValuta() {
		return valuta;
	}

	/**
	 * Sets the value of the valore property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setValore(String value) {
		this.valore = value;
	}

	/**
	 * Sets the value of the valuta property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setValuta(String value) {
		this.valuta = value;
	}

}
