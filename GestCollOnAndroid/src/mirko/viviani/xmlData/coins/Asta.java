package mirko.viviani.xmlData.coins;

import java.util.Date;

import org.simpleframework.xml.Element;

public class Asta {

	@Element
	private String casa;
	
	@Element
	private String idAsta;

	@Element
	private Date data;

	@Element
	private String lotto;

	@Element
	private Misura stima;
	
	@Element
	private Misura aggiudicazione;

	public String getCasa() {
		return casa;
	}

	public String getIdAsta() {
		return idAsta;
	}

	public Date getData() {
		return data;
	}

	public String getLotto() {
		return lotto;
	}

	public Misura getStima() {
		return stima;
	}

	public Misura getAggiudicazione() {
		return aggiudicazione;
	}

	
	
}
