package mirko.viviani.xmlData.coins;

import java.util.ArrayList;

import org.simpleframework.xml.ElementList;

public class Zecchieri {

	@ElementList(required=false)
	ArrayList<Zecchiere> zecchieri;

	public ArrayList<Zecchiere> getZecchieri() {
		return zecchieri;
	}
	
	
}
