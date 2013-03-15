package mirko.viviani.xmlData.coins;

import org.simpleframework.xml.Element;

public class DatiFisici {
	
	@Element(name="peso")
	private Misura peso;
	
	@Element(name="diametro")
	private Misura diametro;
	
	@Element(name="forma")
	private String forma;
	
	@Element(name="metallo")
	private String metallo;

	public Misura getPeso() {
		return peso;
	}

	public Misura getDiametro() {
		return diametro;
	}

	public String getForma() {
		return forma;
	}

	public String getMetallo() {
		return metallo;
	}
	
	
	

}
