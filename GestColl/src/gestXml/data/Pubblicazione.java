/*
 * Modifiche:
 * -
 */
package gestXml.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	public final static String SUPPORTO_ELETTRONICO = "E";
	/**
     *
     */
	public final static String SUPPORTO_MANCANTE = "X";
	/**
     *
     */
	public final static String SUPPORTO_STAMPA = "S";
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
		filename = "";
		data = "";
		autori = new ArrayList<String>();
		titolo = "";
		id = "";
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
		if (url == null || url.equals("")) {
		} else {
			url = "";
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
		if (this.filename != null && !this.filename.equals("")) {
				try {
					this.filename = new File(Common.getCommon().getBibliotecaDir() + "/"
							+ filename).getCanonicalPath();
				} catch (IOException e) {
					this.filename = "";
				}
		} else {
			this.filename = "";
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

		String supp = "[";
		for (String f : supporti) {
			supp = supp + f;
		}
		supp = supp + "]";

		String args = "<br>";
		if (argomenti != null && !argomenti.isEmpty()) {
			args = "<ul>";
			for (String f : argomenti) {
				args = args + "<li>" + f + "</li>";
			}
			args = args + "</ul>";
		}

		String aut = "<br>";
		if (autori != null && !autori.isEmpty()) {
			aut = "<h2>";
			for (String f : autori) {
				aut = aut + f + ", ";
			}
			aut = aut + "</h2>";
			aut = aut.replace(", </h2>", "</h2>");
		}

		return "<h2>" + titolo + "</h2>" + aut + "<b>id:</b> " + id + "<br>"
				+ "<b>supporti:</b> " + supp + "<br>" + "<b>argomenti:</b> "
				+ args + "<b>filename:</b> <a href=\"file://"+filename+"\">" + filename + "</a><br>"
				+ "<b>data:</b> " + data;

	}

}
