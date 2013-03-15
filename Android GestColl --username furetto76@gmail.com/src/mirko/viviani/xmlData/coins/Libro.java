package mirko.viviani.xmlData.coins;

import org.simpleframework.xml.Element;

public class Libro {

	@Element(required=false, name="sigla")
	private String sigla;
	
	@Element(required=false, name="numero")
	private String numero;

	public String getSigla() {
		return sigla;
	}

	public String getNumero() {
		return numero;
	}
	
	

}
