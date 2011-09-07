/**
 * 
 */
package exceptions;

/**
 * @author intecs
 *
 */
public class XmlException extends GenericException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore
	 */
	public XmlException() {
		super();
	}

	/**
	 * Costruttore
	 * @param arg0 il messaggio
	 */
	public XmlException(String arg0) {
		super(arg0);
	}

	/**
	 * @param cause la causa
	 */
	public XmlException(Throwable cause) {
		super(cause);
	}

	/**
	 * Costruttore
	 * @param message il messaggio
	 * @param cause la causa
	 */
	public XmlException(String message, Throwable cause) {
		super(message, cause);
	}
	

}
