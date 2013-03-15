package mirko.viviani.xmlData.biblio;

import java.util.ArrayList;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Biblioteca {
	
	@Attribute(required=false) 
	private String schemaLocation; 
	
	@ElementList(inline=false)
	ArrayList<Libro> libri;

	@ElementList(inline=false)
	ArrayList<Catalogo> cataloghi;

	/**
	 * @return the schemaLocation
	 */
	public String getSchemaLocation() {
		return schemaLocation;
	}

	/**
	 * @return the libri
	 */
	public ArrayList<Libro> getLibri() {
		return libri;
	}

	/**
	 * @return the cataloghi
	 */
	public ArrayList<Catalogo> getCataloghi() {
		return cataloghi;
	}


}
