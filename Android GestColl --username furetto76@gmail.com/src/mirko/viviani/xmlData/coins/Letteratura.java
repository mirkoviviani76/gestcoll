package mirko.viviani.xmlData.coins;

import java.util.ArrayList;

import org.simpleframework.xml.ElementList;

public class Letteratura {

	@ElementList(required=false, inline=true, name="libro")
	private ArrayList<Libro> libro;
	
	@ElementList(required=false, inline=true, name="asta")
	private ArrayList<Asta> asta;

	public ArrayList<Libro> getLibro() {
		return libro;
	}

	public ArrayList<Asta> getAsta() {
		return asta;
	}
	
	
	
}
