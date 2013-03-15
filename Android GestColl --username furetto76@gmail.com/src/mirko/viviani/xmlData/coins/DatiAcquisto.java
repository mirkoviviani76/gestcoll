package mirko.viviani.xmlData.coins;

import java.util.Date;

import org.simpleframework.xml.Element;

@Element
public class DatiAcquisto {
	
	@Element(required=false, name="luogo")
	private String luogo;
	
	@Element(required=false, name="data")
	private Date data;
	
	@Element(required=false, name="prezzo")
	private Misura prezzo;

	public String getLuogo() {
		return luogo;
	}

	public Date getData() {
		return data;
	}

	public Misura getPrezzo() {
		return prezzo;
	}
	
	

}
