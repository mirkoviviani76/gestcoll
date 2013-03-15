package mirko.viviani.xmlData.coins;

import org.simpleframework.xml.Element;

public class Documento {
	
	@Element(required=false, name="filename")
	private String filename;
	
	@Element(required=false, name="descrizione")
	private String descrizione;

	public String getFilename() {
		return filename;
	}

	public String getDescrizione() {
		return descrizione;
	}
	

}
