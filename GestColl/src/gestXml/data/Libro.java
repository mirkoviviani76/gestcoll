/*
 * Modifiche:
 * -
 */
package gestXml.data;

import Resources.i18n.Messages;

/**
 * In un Libro solo il titolo e' obbligatorio.
 * 
 * @author furetto76
 */
public class Libro extends Pubblicazione {

	private String isbn;
	private String volume;

	/**
	 * costruttore
	 */
	public Libro() {
		super();
		isbn = ""; //$NON-NLS-1$
		volume = ""; //$NON-NLS-1$
	}

	/**
	 * 
	 * @return l'isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * 
	 * @return il volume
	 */
	public String getVolume() {
		return volume;
	}

	@Override
	public boolean isValid() {
		boolean ret = false;
		if (!titolo.equals("")) { //$NON-NLS-1$
			ret = true;
		}
		return ret;
	}

	/**
	 * setta l'isbn
	 * 
	 * @param isbn
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * setta il volume
	 * 
	 * @param volume
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}

	@Override
	public String toHtmlString() {
		return "<h1>"+Messages.getString("Generic.23")+"</h1>" + super.toHtmlString() + "<br>" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ "<b>"+Messages.getString("Libro.1")+"</b> " + isbn + "<br>" + "<b>"+Messages.getString("Libro.2")+"</b> " + volume; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
	}

	@Override
	public String getText() {
		String curTitolo = this.getTitolo();
		/* riduce il titolo, tanto non serve a molto */
		if (curTitolo.length() > 30) {
			curTitolo = curTitolo.substring(0, 30) + "..."; //$NON-NLS-1$
		}
		String s = ""; //$NON-NLS-1$
		if (this.getAutori().size() > 0) {
			s = String.format("(L) [%s] %s %s", this.getId(), this.getAutori().get(0), curTitolo); //$NON-NLS-1$
		} else {
			s = String.format("(L) [%s] %s", this.getId(), curTitolo); //$NON-NLS-1$
		}
		return s;
	}

}
