/**
 * 
 */
package exceptions;

/**
 * Eccezione generica
 * 
 * @author intecs
 * 
 */
public class GenericException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore
	 */
	public GenericException() {
		super();
	}

	/**
	 * Costruttore
	 * 
	 * @param arg0
	 *            il messaggio
	 */
	public GenericException(String arg0) {
		super(arg0);
	}

	/**
	 * Costruttore
	 * 
	 * @param message
	 *            il messaggio
	 * @param cause
	 *            la causa
	 */
	public GenericException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 *            la causa
	 */
	public GenericException(Throwable cause) {
		super(cause);
	}

}
