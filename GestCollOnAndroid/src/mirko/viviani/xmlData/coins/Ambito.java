package mirko.viviani.xmlData.coins;

import org.simpleframework.xml.Element;

public class Ambito {

	@Element(required=true)
	private String titolo;
	
	@Element(required=false)
	private String icon;

	public String getTitolo() {
		return titolo;
	}

	public String getIcon() {
		return icon;
	}
	
	
	
}
