package mirko.viviani.xmlData.coins;

import org.simpleframework.xml.Element;

public class Nominale {

	@Element(name="valore")
	private String valore;
	
	@Element(name="valuta")
	private String valuta;

	public String getValore() {
		return valore;
	}

	public String getValuta() {
		return valuta;
	}
	
	
}
