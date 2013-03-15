package mirko.viviani.xmlData.biblio;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class Catalogo {

	@ElementList(required=true, inline=false)
	private ArrayList<String> autori;
	
	@Element(required=true, name="numero")
	private String numero;
	
	@Element(required=false, name="data")
	private String data;

	@Element(required=false, name="filename")
	private String filename;

	@ElementList(required=false, inline=false)
	private ArrayList<String> supporti;
	
	@ElementList(required=false, inline=false)
	private ArrayList<Argomento> argomenti;

	/**
	 * @return the autori
	 */
	public ArrayList<String> getAutori() {
		return autori;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @return the supporti
	 */
	public ArrayList<String> getSupporti() {
		return supporti;
	}

	/**
	 * @return the argomenti
	 */
	public ArrayList<Argomento> getArgomenti() {
		return argomenti;
	}
	
	
	
}
