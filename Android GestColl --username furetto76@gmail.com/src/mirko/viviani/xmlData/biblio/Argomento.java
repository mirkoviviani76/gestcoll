package mirko.viviani.xmlData.biblio;

import org.simpleframework.xml.Element;

public class Argomento {

	@Element(required=false, name="argomento")
	private String argomento;

	/**
	 * @return the argomento
	 */
	public String getArgomento() {
		return argomento;
	}
	
	

}
