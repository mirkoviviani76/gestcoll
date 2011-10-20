//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.10.18 at 02:59:54 PM CEST 
//


package XmlData.Moneta;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Tipo che definisce un passaggio in asta
 * 
 * <p>Java class for asta complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="asta">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="casa" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="idAsta" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="lotto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="stima" type="{http://gestColl/coins}misura"/>
 *         &lt;element name="aggiudicazione" type="{http://gestColl/coins}misura"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "asta", propOrder = {

})
public class Asta {

    @XmlElement(required = true)
    protected String casa;
    @XmlElement(required = true)
    protected String idAsta;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar data;
    @XmlElement(required = true)
    protected String lotto;
    @XmlElement(required = true)
    protected Misura stima;
    @XmlElement(required = true)
    protected Misura aggiudicazione;

    /**
     * Gets the value of the casa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCasa() {
        return casa;
    }

    /**
     * Sets the value of the casa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCasa(String value) {
        this.casa = value;
    }

    /**
     * Gets the value of the idAsta property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAsta() {
        return idAsta;
    }

    /**
     * Sets the value of the idAsta property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAsta(String value) {
        this.idAsta = value;
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
     * Gets the value of the lotto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLotto() {
        return lotto;
    }

    /**
     * Sets the value of the lotto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLotto(String value) {
        this.lotto = value;
    }

    /**
     * Gets the value of the stima property.
     * 
     * @return
     *     possible object is
     *     {@link Misura }
     *     
     */
    public Misura getStima() {
        return stima;
    }

    /**
     * Sets the value of the stima property.
     * 
     * @param value
     *     allowed object is
     *     {@link Misura }
     *     
     */
    public void setStima(Misura value) {
        this.stima = value;
    }

    /**
     * Gets the value of the aggiudicazione property.
     * 
     * @return
     *     possible object is
     *     {@link Misura }
     *     
     */
    public Misura getAggiudicazione() {
        return aggiudicazione;
    }

    /**
     * Sets the value of the aggiudicazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link Misura }
     *     
     */
    public void setAggiudicazione(Misura value) {
        this.aggiudicazione = value;
    }

}
