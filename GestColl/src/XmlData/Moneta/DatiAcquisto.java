//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.10.18 at 02:59:54 PM CEST 
//


package XmlData.Moneta;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for datiAcquisto complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="datiAcquisto">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="luogo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="prezzo" type="{http://gestColl/coins}misura" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "datiAcquisto", propOrder = {
    "luogo",
    "data",
    "prezzo"
})
public class DatiAcquisto {

    protected String luogo;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar data;
    protected Misura prezzo;

    /**
     * Gets the value of the luogo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLuogo() {
        return luogo;
    }

    /**
     * Sets the value of the luogo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLuogo(String value) {
        this.luogo = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setData(XMLGregorianCalendar value) {
        this.data = value;
    }

    /**
     * Gets the value of the prezzo property.
     * 
     * @return
     *     possible object is
     *     {@link Misura }
     *     
     */
    public Misura getPrezzo() {
        return prezzo;
    }

    /**
     * Sets the value of the prezzo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Misura }
     *     
     */
    public void setPrezzo(Misura value) {
        this.prezzo = value;
    }

}
