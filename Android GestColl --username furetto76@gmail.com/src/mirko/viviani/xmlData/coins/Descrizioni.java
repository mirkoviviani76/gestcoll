package mirko.viviani.xmlData.coins;

import java.util.ArrayList;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class Descrizioni {

	@Element(required=false, name="descrizione")
	private String descrizione;
	
	@ElementList(required=false, inline=true, name="legenda")
	private ArrayList<Legenda> legenda;
	
	@Element(required=false, name="fileImmagine")
	private String fileImmagine;

	public String getDescrizione() {
		return descrizione;
	}

	public ArrayList<Legenda> getLegenda() {
		return legenda;
	}

	public String getFileImmagine() {
		return fileImmagine;
	}
	
	

}
