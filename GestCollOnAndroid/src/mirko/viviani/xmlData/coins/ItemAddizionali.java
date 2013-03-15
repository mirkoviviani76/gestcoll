package mirko.viviani.xmlData.coins;

import java.util.ArrayList;

import org.simpleframework.xml.ElementList;

public class ItemAddizionali {

	@ElementList(required=false, inline=true)
	private ArrayList<Documento> documento;

	public ArrayList<Documento> getDocumento() {
		return documento;
	}
	
	
	
}
