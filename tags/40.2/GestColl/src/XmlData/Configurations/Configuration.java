//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.10.14 at 01:49:47 PM CEST 
//


package XmlData.Configurations;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for configuration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="configuration">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dirs" type="{http://gestColl/configurations}dirs"/>
 *         &lt;element name="data" type="{http://gestColl/configurations}data"/>
 *         &lt;element name="logs" type="{http://gestColl/configurations}logs"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "configuration", propOrder = {
    "dirs",
    "data",
    "logs"
})
public class Configuration {

    @XmlElement(required = true)
    protected Dirs dirs;
    @XmlElement(required = true)
    protected Data data;
    @XmlElement(required = true)
    protected Logs logs;
    @XmlAttribute(name = "id", required = true)
    protected String id;

    /**
     * Gets the value of the dirs property.
     * 
     * @return
     *     possible object is
     *     {@link Dirs }
     *     
     */
    public Dirs getDirs() {
        return dirs;
    }

    /**
     * Sets the value of the dirs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Dirs }
     *     
     */
    public void setDirs(Dirs value) {
        this.dirs = value;
    }

    /**
     * Gets the value of the data property.
     * 
     * @return
     *     possible object is
     *     {@link Data }
     *     
     */
    public Data getData() {
        return data;
    }

    /**
     * Sets the value of the data property.
     * 
     * @param value
     *     allowed object is
     *     {@link Data }
     *     
     */
    public void setData(Data value) {
        this.data = value;
    }

    /**
     * Gets the value of the logs property.
     * 
     * @return
     *     possible object is
     *     {@link Logs }
     *     
     */
    public Logs getLogs() {
        return logs;
    }

    /**
     * Sets the value of the logs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Logs }
     *     
     */
    public void setLogs(Logs value) {
        this.logs = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}