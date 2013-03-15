package mirko.viviani.xmlData.coins;

import java.util.ArrayList;
import java.util.Date;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class Info {

	@Element
	private String titolo;
	
	@Element
	private String proprietario;
	
	@Element
	private Date inizio;
	
	@ElementList
	private ArrayList<Ambito> ambiti;

	public String getTitolo() {
		return titolo;
	}

	public String getProprietario() {
		return proprietario;
	}

	public Date getInizio() {
		return inizio;
	}
	
	public ArrayList<Ambito> getAmbiti() {
		return ambiti;
	}

	

}
