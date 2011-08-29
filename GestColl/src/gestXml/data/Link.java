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
     *
     */
	public String nome;
	/**
     *
     */
	public URL url;
	/**
     *
     */
	public String note;

	

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
	public String toString() {
		return categoria + "." + nome + " -> " + url.toString() + " (" + note + ")";
	}

	@Override
	public int compareTo(Link o) {
		String n1 = this.categoria + this.nome;
		String n2 = o.categoria + o.nome;
		return (n1.compareTo(n2));
	}

}
