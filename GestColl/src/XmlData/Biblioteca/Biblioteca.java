//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.08.29 at 01:40:30 PM CEST 
//

package XmlData.Biblioteca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="libri" type="{http://www.biblioteca.org}libri"/>
 *         &lt;element name="cataloghi" type="{http://www.biblioteca.org}cataloghi" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "biblioteca")
public class Biblioteca {

	protected Cataloghi cataloghi;
	@XmlElement(required = true)
	protected Libri libri;

	/**
	 * Gets the value of the cataloghi property.
	 * 
	 * @return possible object is {@link Cataloghi }
	 * 
	 */
	public Cataloghi getCataloghi() {
		return cataloghi;
	}

	/**
	 * Gets the value of the libri property.
	 * 
	 * @return possible object is {@link Libri }
	 * 
	 */
	public Libri getLibri() {
		return libri;
	}

	/**
	 * Sets the value of the cataloghi property.
	 * 
	 * @param value
	 *            allowed object is {@link Cataloghi }
	 * 
	 */
	public void setCataloghi(Cataloghi value) {
		this.cataloghi = value;
	}

	/**
	 * Sets the value of the libri property.
	 * 
	 * @param value
	 *            allowed object is {@link Libri }
	 * 
	 */
	public void setLibri(Libri value) {
		this.libri = value;
	}

}
