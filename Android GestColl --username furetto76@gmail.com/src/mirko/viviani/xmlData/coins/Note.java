package mirko.viviani.xmlData.coins;

import java.util.ArrayList;

import org.simpleframework.xml.ElementList;

public class Note {

	@ElementList
	private ArrayList<String> note;

	public ArrayList<String> getNote() {
		return note;
	}
	
	
	
}


