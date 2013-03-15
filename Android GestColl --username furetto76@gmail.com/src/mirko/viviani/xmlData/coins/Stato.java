package mirko.viviani.xmlData.coins;

import org.simpleframework.xml.Element;

public class Stato {

	@Element(name="colore")
	private String colore;
	
	@Element(name="motivo")
	private String motivo;

	public String getColore() {
		return colore;
	}

	public String getMotivo() {
		return motivo;
	}
	
	

}
