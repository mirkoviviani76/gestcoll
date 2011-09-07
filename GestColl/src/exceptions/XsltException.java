/**
 * 
 */
package exceptions;

/**
 * @author intecs
 *
 */
public class XsltException extends GenericException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore
	 */
	public XsltException() {
		super();
	}

	/**
	 * Costruttore
	 * @param arg0 il messaggio
	 */
	public XsltException(String arg0) {
		super(arg0);
	}

	/**
	 * @param cause la causa
	 */
	public XsltException(Throwable cause) {
		super(cause);
	}

	/**
	 * Costruttore
	 * @param message il messaggio
	 * @param cause la causa
	 */
	public XsltException(String message, Throwable cause) {
		super(message, cause);
	}
	

}
