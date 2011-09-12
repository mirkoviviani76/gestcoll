/*
 * Modifiche:
 * -
 */
package gestXml.data;

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
		isbn = "";
		volume = "";
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
		if (!titolo.equals("")) {
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
		return "<h1>LIBRO</h1>" + super.toHtmlString() + "<br>"
				+ "<b>isbn:</b> " + isbn + "<br>" + "<b>volume:</b> " + volume;
	}

	@Override
	public String toString() {
		String curTitolo = this.getTitolo();
		/* riduce il titolo, tanto non serve a molto */
		if (curTitolo.length() > 30) {
			curTitolo = curTitolo.substring(0, 30) + "...";
		}
		String s = "";
		if (this.getAutori().size() > 0) {
			s = String.format("(L) %s %s", this.getAutori().get(0), curTitolo);
		} else {
			s = String.format("(L) %s", curTitolo);
		}
		return s;
	}

}
