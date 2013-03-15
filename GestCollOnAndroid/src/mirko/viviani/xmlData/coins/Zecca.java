package mirko.viviani.xmlData.coins;

import org.simpleframework.xml.Element;

public class Zecca {
	
	@Element(required=false, name="nome")
	private String nome;
	
	@Element(required=false, name="segno")
	private String segno;

	public String getNome() {
		return nome;
	}

	public String getSegno() {
		return segno;
	}
	
	

}
