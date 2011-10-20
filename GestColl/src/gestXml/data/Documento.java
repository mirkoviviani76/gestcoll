/*
 * Modifiche:
 * -
 */
package gestXml.data;

import Resources.i18n.Messages;

/**
 * In un documento, solo la descrizione e' obbligatoria
 * 
 * @author furetto76
 */
public class Documento extends Pubblicazione {
	private String descrizione;

	/**
	 * costruttore
	 */
	public Documento() {
		super();
		this.descrizione = ""; //$NON-NLS-1$
	}

	/**
	 * 
	 * @return la descrizione
	 */
	public String getDescrizione() {
		return descrizione;
	}

	@Override
	public boolean isValid() {
		boolean ret = false;
		if (!descrizione.equals("")) { //$NON-NLS-1$
			ret = true;
		}
		return ret;
	}

	/**
	 * setta la descrizione
	 * 
	 * @param descrizione
	 */
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public String toHtmlString() {
		return "<h1>"+Messages.getString("Documento.3")+"</h1>" + super.toHtmlString() + "<br>" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ "<b>"+Messages.getString("Generic.19")+"</b> " + descrizione; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Override
	public String getText() {
		return String.format("(D) %s %s", this.getAutori().get(0), //$NON-NLS-1$
				this.getTitolo());
	}

}
