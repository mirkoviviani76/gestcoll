package mirko.viviani.xmlData.coins;

import java.util.ArrayList;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class Monete {
	
	@Attribute(required=false) 
	private String schemaLocation; 
	
	@Element(name="info")
	Info info;
	
	@ElementList(inline=true)
	ArrayList<Moneta> moneta;

	public Info getInfo() {
		return info;
	}

	public ArrayList<Moneta> getMonete() {
		return moneta;
	}

	public String getSchemaLocation() {
		return schemaLocation;
	}


}
