package mirko.viviani.xmlData.coins;

import org.simpleframework.xml.Element;

public class Legenda {

	@Element(required=false)
	private String testo;
	
	@Element(required=false)
	private String scioglimento;

	public String getTesto() {
		return testo;
	}

	public String getScioglimento() {
		return scioglimento;
	}
	

	
	
}
