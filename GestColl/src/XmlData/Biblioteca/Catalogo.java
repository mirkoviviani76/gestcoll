//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.12 at 11:54:09 AM CEST 
//


package XmlData.Biblioteca;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Tipo che definisce una pubblicazione
 * 
 * <p>Java class for catalogo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="catalogo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="autori" type="{http://www.biblioteca.org}autori"/>
 *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="data" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="filename" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="supporti" type="{http://www.biblioteca.org}supporti" minOccurs="0"/>
 *         &lt;element name="argomenti" type="{http://www.biblioteca.org}argomenti" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "catalogo", propOrder = {

})
public class Catalogo {

    @XmlElement(required = true)
    protected Autori autori;
    @XmlElement(required = true)
    protected String numero;
    protected String data;
    protected String filename;
    protected Supporti supporti;
    protected Argomenti argomenti;

    /**
     * Gets the value of the autori property.
     * 
     * @return
     *     possible object is
     *     {@link Autori }
     *     
     */
    public Autori getAutori() {
        return autori;
    }

    /**
     * Sets the value of the autori property.
     * 
     * @param value
     *     allowed object is
     *     {@link Autori }
     *     
     */
    public void setAutori(Autori value) {
        this.autori = value;
    }

    /**
     * Gets the value of the numero property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Sets the value of the numero property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumero(String value) {
        this.numero = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setData(String value) {
        this.data = value;
    }

    /**
     * Gets the value of the filename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Sets the value of the filename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilename(String value) {
        this.filename = value;
    }

    /**
     * Gets the value of the supporti property.
     * 
     * @return
     *     possible object is
     *     {@link Supporti }
     *     
     */
    public Supporti getSupporti() {
        return supporti;
    }

    /**
     * Sets the value of the supporti property.
     * 
     * @param value
     *     allowed object is
     *     {@link Supporti }
     *     
     */
    public void setSupporti(Supporti value) {
        this.supporti = value;
    }

    /**
     * Gets the value of the argomenti property.
     * 
     * @return
     *     possible object is
     *     {@link Argomenti }
     *     
     */
    public Argomenti getArgomenti() {
        return argomenti;
    }

    /**
     * Sets the value of the argomenti property.
     * 
     * @param value
     *     allowed object is
     *     {@link Argomenti }
     *     
     */
    public void setArgomenti(Argomenti value) {
        this.argomenti = value;
    }

}
