package mirko.viviani.xmlData.biblio;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class Libro {

	@Element(required=false, name="id")
	private String id;
	
	@ElementList(required=false, inline=false)
	private ArrayList<String> autori;
	
	@ElementList(required=false, inline=false)
	private ArrayList<String> supporti;

	@Element(required=true, name="titolo")
	private String titolo;
	
	@Element(required=false, name="filename")
	private String filename;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the autori
	 */
	public ArrayList<String> getAutori() {
		return autori;
	}

	/**
	 * @return the supporti
	 */
	public ArrayList<String> getSupporti() {
		return supporti;
	}

	/**
	 * @return the titolo
	 */
	public String getTitolo() {
		return titolo;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	
	
	
}
