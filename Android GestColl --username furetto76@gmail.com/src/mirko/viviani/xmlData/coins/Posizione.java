package mirko.viviani.xmlData.coins;

import org.simpleframework.xml.Element;

public class Posizione {

	@Element(name="contenitore")
	private Integer contenitore;
	
	@Element(name="vassoio")
	private Integer vassoio;

	@Element(name="riga")
	private Integer riga;

	@Element(name="colonna")
	private Integer colonna;

	public Integer getContenitore() {
		return contenitore;
	}

	public Integer getVassoio() {
		return vassoio;
	}

	public Integer getRiga() {
		return riga;
	}

	public Integer getColonna() {
		return colonna;
	}
	
	
	
}
