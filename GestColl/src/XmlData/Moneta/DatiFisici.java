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
 * <p>
 * Java class for datiFisici complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="datiFisici">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="peso" type="{}misura"/>
 *         &lt;element name="diametro" type="{}misura"/>
 *         &lt;element name="forma" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="metallo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datiFisici", propOrder = {

})
public class DatiFisici {

	@XmlElement(required = true)
	protected Misura peso;
	@XmlElement(required = true)
	protected Misura diametro;
	@XmlElement(required = true)
	protected String forma;
	@XmlElement(required = true)
	protected String metallo;

	/**
	 * Gets the value of the peso property.
	 * 
	 * @return possible object is {@link Misura }
	 * 
	 */
	public Misura getPeso() {
		return peso;
	}

	/**
	 * Sets the value of the peso property.
	 * 
	 * @param value
	 *            allowed object is {@link Misura }
	 * 
	 */
	public void setPeso(Misura value) {
		this.peso = value;
	}

	/**
	 * Gets the value of the diametro property.
	 * 
	 * @return possible object is {@link Misura }
	 * 
	 */
	public Misura getDiametro() {
		return diametro;
	}

	/**
	 * Sets the value of the diametro property.
	 * 
	 * @param value
	 *            allowed object is {@link Misura }
	 * 
	 */
	public void setDiametro(Misura value) {
		this.diametro = value;
	}

	/**
	 * Gets the value of the forma property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getForma() {
		return forma;
	}

	/**
	 * Sets the value of the forma property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setForma(String value) {
		this.forma = value;
	}

	/**
	 * Gets the value of the metallo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getMetallo() {
		return metallo;
	}

	/**
	 * Sets the value of the metallo property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setMetallo(String value) {
		this.metallo = value;
	}

}
