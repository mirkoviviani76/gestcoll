/*
 * Modifiche:
 * -
 */
package gestXml.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Resources.i18n.Messages;

import main.Common;

/**
 * In una pubblicazione nessun campo e' obbligatorio.
 * 
 * @author furetto76
 */
public class Pubblicazione {

	/**
     *
     */
	public final static String SUPPORTO_ELETTRONICO = "E"; //$NON-NLS-1$
	/**
     *
     */
	public final static String SUPPORTO_MANCANTE = "X"; //$NON-NLS-1$
	/**
     *
     */
	public final static String SUPPORTO_STAMPA = "S"; //$NON-NLS-1$
	/**
     *
     */
	protected List<String> argomenti;
	/**
     *
     */
	protected List<String> autori;
	/**
     *
     */
	protected String data;
	/**
     *
     */
	protected String filename;
	/**
     *
     */
	protected String id;
	/**
     *
     */
	protected List<String> supporti;
	/**
     *
     */
	protected String titolo;

	/**
     *
     */
	public Pubblicazione() {
		supporti = new ArrayList<String>();
		filename = ""; //$NON-NLS-1$
		data = ""; //$NON-NLS-1$
		autori = new ArrayList<String>();
		titolo = ""; //$NON-NLS-1$
		id = ""; //$NON-NLS-1$
	}

	/**
	 * 
	 * @return gli argomenti
	 */
	public List<String> getArgomenti() {
		return argomenti;
	}

	/**
	 * 
	 * @return la lista degli autori
	 */
	public List<String> getAutori() {
		return autori;
	}

	/**
	 * 
	 * @return la data
	 */
	public String getData() {
		return data;
	}

	/**
	 * corregge il filename con la dir della biblioteca
	 * @return il filename corretto o null
	 */
	public String getFilename() {
		String url = filename; 
		if (url == null || url.equals("")) { //$NON-NLS-1$
		} else {
			url = ""; //$NON-NLS-1$
		}
		
		return url;
	}

	/**
	 * 
	 * @return l'id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @return i supporti
	 */
	public List<String> getSupporti() {
		return supporti;
	}

	/**
	 * 
	 * @return il titolo
	 */
	public String getTitolo() {
		return titolo;
	}

	/**
	 * controlla se ha un url
	 * 
	 * @return l'esito del controllo
	 */
	public boolean hasElectronicForm() {
		return this.getSupporti().contains(Pubblicazione.SUPPORTO_ELETTRONICO);
	}

	/**
	 * una pubblicazione non ha nessun campo obbligatorio, quindi e' sempre
	 * valida
	 * 
	 * @return l'esito del controllo
	 */
	public boolean isValid() {
		return true;
	}

	/**
	 * controlla se e' posseduto
	 * 
	 * @return l'esito del controllo
	 */
	public boolean posseduto() {
		return !this.getSupporti().contains(Pubblicazione.SUPPORTO_MANCANTE);
	}

	/**
	 * cambia gli argomenti
	 * 
	 * @param list
	 *            lista degli argomenti
	 */
	public void setArgomenti(List<String> list) {
		this.argomenti = list;
	}

	/**
	 * setta gli autori
	 * 
	 * @param autori
	 *            lista degli autori
	 */
	public void setAutori(List<String> autori) {
		this.autori = autori;
	}

	/**
	 * setta la data
	 * 
	 * @param data
	 *            la data
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * setta il filename
	 * 
	 * @param filename
	 *            il filename
	 */
	public void setFilename(String filename) {
		this.filename = filename; 
		if (this.filename != null && !this.filename.equals("")) { //$NON-NLS-1$
				try {
					this.filename = new File(Common.getCommon().getBibliotecaDir() + "/" //$NON-NLS-1$
							+ filename).getCanonicalPath();
				} catch (IOException e) {
					this.filename = ""; //$NON-NLS-1$
				}
		} else {
			this.filename = ""; //$NON-NLS-1$
		}
	}

	/**
	 * setta l'id
	 * 
	 * @param id
	 *            l'id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * setta i supporti
	 * 
	 * @param list
	 *            la lista
	 */
	public void setSupporti(List<String> list) {
		this.supporti = list;
	}

	/**
	 * setta il titolo
	 * 
	 * @param titolo
	 */
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	/**
	 * converte in html
	 * 
	 * @return la stringa
	 */
	public String toHtmlString() {

		String supp = "["; //$NON-NLS-1$
		for (String f : supporti) {
			supp = supp + f;
		}
		supp = supp + "]"; //$NON-NLS-1$

		String args = "<br>"; //$NON-NLS-1$
		if (argomenti != null && !argomenti.isEmpty()) {
			args = "<ul>"; //$NON-NLS-1$
			for (String f : argomenti) {
				args = args + "<li>" + f + "</li>"; //$NON-NLS-1$ //$NON-NLS-2$
			}
			args = args + "</ul>"; //$NON-NLS-1$
		}

		String aut = "<br>"; //$NON-NLS-1$
		if (autori != null && !autori.isEmpty()) {
			aut = "<h2>"; //$NON-NLS-1$
			for (String f : autori) {
				aut = aut + f + ", "; //$NON-NLS-1$
			}
			aut = aut + "</h2>"; //$NON-NLS-1$
			aut = aut.replace(", </h2>", "</h2>"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		return "<h2>" + titolo + "</h2>" + aut + "<b>"+Messages.getString("Pubblicazione.4") +"</b>"+ id + "<br>" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
				+ "<b>"+Messages.getString("Pubblicazione.3")+"</b> " + supp + "<br>" + "<b>"+Messages.getString("Pubblicazione.2")+"</b> " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
				+ args + "<b>"+Messages.getString("Pubblicazione.1")+"</b> <a href=\"file://"+filename+"\">" + filename + "</a><br>" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
				+ "<b>"+Messages.getString("Pubblicazione.0")+"</b> " + data; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	}

}
