//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.10.14 at 12:30:04 PM CEST 
//


package XmlData.Moneta;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for posizione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="posizione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="contenitore" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="vassoio" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="riga" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="colonna" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "posizione", propOrder = {

})
public class Posizione {

    @XmlElement(required = true)
    protected BigInteger contenitore;
    @XmlElement(required = true)
    protected BigInteger vassoio;
    @XmlElement(required = true)
    protected BigInteger riga;
    @XmlElement(required = true)
    protected BigInteger colonna;

    /**
     * Gets the value of the contenitore property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getContenitore() {
        return contenitore;
    }

    /**
     * Sets the value of the contenitore property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setContenitore(BigInteger value) {
        this.contenitore = value;
    }

    /**
     * Gets the value of the vassoio property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVassoio() {
        return vassoio;
    }

    /**
     * Sets the value of the vassoio property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVassoio(BigInteger value) {
        this.vassoio = value;
    }

    /**
     * Gets the value of the riga property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getRiga() {
        return riga;
    }

    /**
     * Sets the value of the riga property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setRiga(BigInteger value) {
        this.riga = value;
    }

    /**
     * Gets the value of the colonna property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getColonna() {
        return colonna;
    }

    /**
     * Sets the value of the colonna property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setColonna(BigInteger value) {
        this.colonna = value;
    }

}
