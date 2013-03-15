package mirko.viviani.xmlData.coins;

import org.simpleframework.xml.Element;

public class Misura {

	@Element(name="unita")
	private String unita;
	
	@Element(name="valore")
	private Float valore;

	public String getUnita() {
		return unita;
	}

	public Float getValore() {
		return valore;
	}

	

}
