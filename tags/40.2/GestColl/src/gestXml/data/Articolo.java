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
		this.provenienza = ""; //$NON-NLS-1$
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
		if (!titolo.equals("")) { //$NON-NLS-1$
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
		return "<h1>ARTICOLO</h1>" + super.toHtmlString() + "<br>" //$NON-NLS-1$ //$NON-NLS-2$
				+ "<b>provenienza:</b> " + provenienza + "<br>"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public String getText() {
		return String.format("(A) %s %s", this.getAutori().get(0), //$NON-NLS-1$
				this.getTitolo());
	}

}
