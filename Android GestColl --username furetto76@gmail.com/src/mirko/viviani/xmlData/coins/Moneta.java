package mirko.viviani.xmlData.coins;

import java.util.ArrayList;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class Moneta {
    
    @Attribute(required=true, name="id")
    private String id;

	@ElementList(required=false, name="ambiti")
	private ArrayList<Ambito> ambito;

	@Element(required=true, name="paese")
	private String paese;

        @Element(required=false, name="anno")
	private String anno;
	
	@Element(required=false, name="grado")
	private String grado;
	
	@ElementList(required=false, name="autorita")
	private ArrayList<String> autorita;

	@Element(name="nominale")
	private Nominale nominale;
	

	@Element(required=false, name="zecca")
	private Zecca zecca;
	

	@ElementList(required=false, name="zecchieri")
	private ArrayList<Zecchiere> zecchieri;
	
	@Element(name="datiArtistici")
	private DatiArtistici datiArtistici;
	
	@Element(name="datiFisici")
	private DatiFisici datiFisici;
	
	@Element(name="datiAcquisto")
	private DatiAcquisto datiAcquisto;
	
	@Element(required=false, name="posizione")
	private Posizione posizione;
	

	@ElementList(required=false, name="note")
	private ArrayList<String> note;

	@Element(name="revisione")
	private String revisione;
	

	@Element(name="stato")
	private Stato stato;
	
	@Element(required=false, name="letteratura")
	private Letteratura letteratura;
	
	@Element(required=false, name="itemAddizionali")
	private ItemAddizionali itemAddizionali;

 	public String getId() {
		return id;
	}

	public String getPaese() {
		return paese;
	}


	public String getAnno() {
		return anno;
	}

	public String getGrado() {
		return grado;
	}

	

	public ArrayList<Ambito> getAmbito() {
		return ambito;
	}

	public ArrayList<String> getAutorita() {
		return autorita;
	}

	public Nominale getNominale() {
		return nominale;
	}

	public Zecca getZecca() {
		return zecca;
	}

	public ArrayList<Zecchiere> getZecchieri() {
		return zecchieri;
	}

	public DatiArtistici getDatiArtistici() {
		return datiArtistici;
	}

	public DatiFisici getDatiFisici() {
		return datiFisici;
	}

	public DatiAcquisto getDatiAcquisto() {
		return datiAcquisto;
	}

	public Posizione getPosizione() {
		return posizione;
	}

	public ArrayList<String> getNote() {
		return note;
	}

	public String getRevisione() {
		return revisione;
	}

	public Stato getStato() {
		return stato;
	}

	public Letteratura getLetteratura() {
		return letteratura;
	}

	public ItemAddizionali getItemAddizionali() {
		return itemAddizionali;
	}
	

	public String getSimpleName() {
		return this.getPaese()+" (" + this.getNominale().getValore() + " " + this.getNominale().getValuta() + ")";
	}
}
