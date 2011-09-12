/*
 * Modifiche:
 * -
 */

package gestXml.data;

import java.net.MalformedURLException;
import java.net.URL;

import main.GestLog;

/**
 * 
 * 
 */
public class Link implements Comparable<Link> {
	public String categoria;

	/**
	 * il nome
	 */
	public String nome;
	/**
	 * le note
	 */
	public String note;
	/**
	 * l'url
	 */
	public URL url;

	/**
	 * costruttore
	 * 
	 * @param categoria
	 *            la categoria
	 * @param nome
	 *            il nome
	 * @param url
	 *            l'url
	 * @param note
	 *            le note
	 */
	public Link(String categoria, String nome, String url, String note) {
		super();
		this.categoria = categoria;
		this.nome = nome;
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			GestLog.Error(this.getClass(), e);
		}
		this.note = note;
	}

	@Override
	public int compareTo(Link o) {
		String n1 = this.categoria + this.nome;
		String n2 = o.categoria + o.nome;
		return (n1.compareTo(n2));
	}

	public String toHtml() {
		String ret;
		ret = "<html><a href=\"" + this.url + "\">" + this.nome + "</a> <br>"
				+ this.note + "</html>";
		return ret;
	}

	@Override
	public String toString() {
		return nome;
	}

}
