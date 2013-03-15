package mirko.viviani.xmlData.coins;

import org.simpleframework.xml.Element;

public class Zecchiere {


	@Element(required=false, name="nome")
	private String nome;
	
	@Element(required=false, name="segno")
	private String segno;

	@Element(required=false, name="ruolo")
	private String ruolo;

	public String getNome() {
		return nome;
	}

	public String getSegno() {
		return segno;
	}

	public String getRuolo() {
		return ruolo;
	}

	
	
	
}
