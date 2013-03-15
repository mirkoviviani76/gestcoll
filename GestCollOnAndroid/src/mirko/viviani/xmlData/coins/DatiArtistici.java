package mirko.viviani.xmlData.coins;

import org.simpleframework.xml.Element;

public class DatiArtistici {

	@Element(name="dritto")
	private Descrizioni dritto;
	
	@Element(name="rovescio")
	private Descrizioni rovescio;
	
	@Element(required=false, name="taglio")
	private Descrizioni taglio;

	public Descrizioni getDritto() {
		return dritto;
	}

	public Descrizioni getRovescio() {
		return rovescio;
	}

	public Descrizioni getTaglio() {
		return taglio;
	}
	
	
}
