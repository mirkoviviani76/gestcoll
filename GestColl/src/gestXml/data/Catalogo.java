/*
 * Modifiche:
 * -
 */
package gestXml.data;

import java.util.ArrayList;

import Resources.i18n.Messages;

/**
 * In un catalogo d'asta, solo l'autore (la Casa) e' obbligatoria.
 * 
 */
public class Catalogo extends Pubblicazione {

	private String numero;

	/**
	 * costruttore
	 */
	public Catalogo() {
		numero = ""; //$NON-NLS-1$
		argomenti = new ArrayList<String>();
	}

	/**
	 * 
	 * @return il numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * controlla la validita'
	 * 
	 * @return l'esito del controllo
	 */
	@Override
	public boolean isValid() {
		boolean ret = false;
		if (!autori.isEmpty()) {
			ret = true;
		}
		return ret;
	}

	/**
	 * setta il numero
	 * 
	 * @param numero
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	@Override
	public String toHtmlString() {
		return "<h1>"+Messages.getString("Catalogo.0")+"</h1>" + "<b>"+Messages.getString("Catalogo.1")+"</b> " + numero + "<br>" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
				+ super.toHtmlString();
	}

	@Override
	public String toString() {
		return String.format("(C) %s %s (%s)", this.getAutori().get(0), //$NON-NLS-1$
				this.getNumero(), this.getData());
	}

}
