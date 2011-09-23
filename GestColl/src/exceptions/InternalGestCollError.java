/**
 * 
 */
package exceptions;

/**
 * @author intecs
 * 
 */
public class InternalGestCollError extends GenericException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Costruttore
	 */
	public InternalGestCollError() {
		super();
	}

	/**
	 * Costruttore
	 * 
	 * @param arg0
	 *            il messaggio
	 */
	public InternalGestCollError(String arg0) {
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
	public InternalGestCollError(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 *            la causa
	 */
	public InternalGestCollError(Throwable cause) {
		super(cause);
	}

}
