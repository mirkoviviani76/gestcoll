package mirko.viviani.gestcoll.customadapter;

import java.util.ArrayList;

public class LetteraturaDettagli {
	
	private String titolo;
	
	private ArrayList<String> autori;
	
	private String id;
	
	private String filename;

	/**
	 * @return the titolo
	 */
	public String getTitolo() {
		return titolo;
	}

	/**
	 * @param titolo the titolo to set
	 */
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	
	
	/**
	 * @return the autori
	 */
	public ArrayList<String> getAutori() {
		return autori;
	}

	/**
	 * @param autori the autori to set
	 */
	public void setAutori(ArrayList<String> autori) {
		this.autori = autori;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	

}
