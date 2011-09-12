/*
 * Modifiche:
 * -
 */

package gestXml.data;

/**
 * In un articolo solo il titolo e' obbligatorio.
 * 
 * @author furetto76
 */
public class Articolo extends Pubblicazione {

	private String provenienza;

	/**
	 * costruttore
	 */
	public Articolo() {
		super();
		this.provenienza = "";
	}

	/**
	 * 
	 * @return la provenienza
	 */
	public String getProvenienza() {
		return provenienza;
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
	 * setta la provenienza
	 * 
	 * @param provenienza
	 */
	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	@Override
	public String toHtmlString() {
		return "<h1>ARTICOLO</h1>" + super.toHtmlString() + "<br>"
				+ "<b>provenienza:</b> " + provenienza + "<br>";
	}

	@Override
	public String toString() {
		return String.format("(A) %s %s", this.getAutori().get(0),
				this.getTitolo());
	}

}
