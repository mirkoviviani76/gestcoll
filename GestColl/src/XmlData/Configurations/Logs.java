//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.2-147 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.09.23 at 02:45:25 PM CEST 
//


package XmlData.Configurations;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Tipo che definisce un elenco di logs
 * 
 * <p>Java class for logs complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="logs">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="history_log" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="log_property" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "logs", propOrder = {

})
public class Logs {

    @XmlElement(name = "history_log", required = true)
    protected String historyLog;
    @XmlElement(name = "log_property", required = true)
    protected String logProperty;

    /**
     * Gets the value of the historyLog property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHistoryLog() {
        return historyLog;
    }

    /**
     * Sets the value of the historyLog property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHistoryLog(String value) {
        this.historyLog = value;
    }

    /**
     * Gets the value of the logProperty property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogProperty() {
        return logProperty;
    }

    /**
     * Sets the value of the logProperty property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogProperty(String value) {
        this.logProperty = value;
    }

}
