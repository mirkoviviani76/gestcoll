package mirko.viviani.gestcoll.data;

import java.util.SortedMap;
import java.util.TreeMap;

import mirko.viviani.utils.Utils;
import mirko.viviani.xmlData.biblio.Catalogo;
import mirko.viviani.xmlData.biblio.Libro;




public class BiblioData {

	private static SortedMap<String, Libro> dataLibri;
	private static SortedMap<String, Catalogo> dataCataloghi;

	static {
		dataLibri = new TreeMap<String, Libro>();
		dataCataloghi = new TreeMap<String, Catalogo>();
	}
	
	
	public static void Clear() {
		dataLibri.clear();
		dataCataloghi.clear();
	}

	public static void addLibro(Libro l) {
		dataLibri.put(l.getId(), l);
	}
	
	public static void addCatalogo(Catalogo c) {
		String aut = Utils.concatStringsWSep(c.getAutori(), ", ");
		dataCataloghi.put(aut+c.getNumero(), c);
	}
	
	public static Libro getLibro(String id) {
		return dataLibri.get(id);
	}
	
	public static Catalogo getCatalogo(String id) {
		return dataCataloghi.get(id);
	}
}
