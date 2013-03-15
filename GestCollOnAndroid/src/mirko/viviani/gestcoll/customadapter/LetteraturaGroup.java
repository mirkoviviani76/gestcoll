package mirko.viviani.gestcoll.customadapter;

import java.util.ArrayList;

public class LetteraturaGroup {
	
	private String sigla;
	private String numero;
	
	private ArrayList<LetteraturaDettagli> Items;
	
	/**
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}
	/**
	 * @param sigla the sigla to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}
	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public ArrayList<LetteraturaDettagli> getItems() {
		return Items;
	}
	public void setItems(ArrayList<LetteraturaDettagli> Items) {
		this.Items = Items;
	}
	
	

}
