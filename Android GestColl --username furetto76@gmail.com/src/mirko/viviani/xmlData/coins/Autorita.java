package mirko.viviani.xmlData.coins;

import java.util.ArrayList;

import org.simpleframework.xml.ElementList;

public class Autorita {

	@ElementList(required=false, inline=true)
	ArrayList<Nome> autorita;

	public ArrayList<Nome> getAutorita() {
		return autorita;
	}
	
	
	
}
