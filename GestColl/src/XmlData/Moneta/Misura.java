//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.08.22 at 10:19:40 PM CEST 
//

package XmlData.Moneta;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Tipo che definisce una unita di misura e un valore (numerico)
 * 
 * <p>
 * Java class for misura complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="misura">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="unita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valore" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "misura", propOrder = { "unita", "valore" })
public class Misura {

	@XmlElement(required = true)
	protected String unita;
	@XmlElement(required = true)
	protected BigDecimal valore;

	/**
	 * Gets the value of the unita property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getUnita() {
		return unita;
	}

	/**
	 * Gets the value of the valore property.
	 * 
	 * @return possible object is {@link BigDecimal }
	 * 
	 */
	public BigDecimal getValore() {
		return valore;
	}

	/**
	 * Sets the value of the unita property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setUnita(String value) {
		this.unita = value;
	}

	/**
	 * Sets the value of the valore property.
	 * 
	 * @param value
	 *            allowed object is {@link BigDecimal }
	 * 
	 */
	public void setValore(BigDecimal value) {
		this.valore = value;
	}

}
