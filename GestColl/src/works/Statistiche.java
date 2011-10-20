/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package works;

import java.util.List;
import java.util.ListIterator;
import java.util.TreeMap;

import XmlData.Moneta.Misura;
import exceptions.XmlException;
import gestXml.CollezioneXml;
import gestXml.MonetaXml;

/**
 * Gestisce la pagina delle statistiche
 * 
 * @author intecs
 */
public class Statistiche {

	public static TreeMap<String, Number> coinByMetal() throws XmlException {
		TreeMap<String, Number> valori = new TreeMap<String, Number>();
		/* ottiene l'elenco di tutte le monete */
		List<MonetaXml> monete = CollezioneXml.getCollezione().getMonete();
		ListIterator<MonetaXml> iterator = monete.listIterator();

		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			MonetaXml mng = (MonetaXml)iterator.next();
			String metallo = mng.getMetallo();
			if (valori.containsKey(metallo)) {
				int v = valori.get(metallo).intValue() + 1;
				valori.put(metallo, v);
			} else {
				valori.put(metallo, 1);
			}
		}
		return valori;
	}

	public static TreeMap<String, Number> coinBySize() throws XmlException {
		TreeMap<String, Number> valori = new TreeMap<String, Number>();
		/* ottiene l'elenco di tutte le monete */
		List<MonetaXml> monete = CollezioneXml.getCollezione().getMonete();
		ListIterator<MonetaXml> iterator = monete.listIterator();

		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			MonetaXml mng = (MonetaXml)iterator.next();
			Misura diam = mng.getDiametro();
			String d = diam.getValore().toPlainString();
			if (valori.containsKey(d)) {
				int v = valori.get(d).intValue() + 1;
				valori.put(d, v);
			} else {
				valori.put(d, 1);
			}
		}
		return valori;

	}

	public static TreeMap<String, Number> coinByYear() throws XmlException {
		TreeMap<String, Number> valori = new TreeMap<String, Number>();
		/* ottiene l'elenco di tutte le monete */
		List<MonetaXml> monete = CollezioneXml.getCollezione().getMonete();
		ListIterator<MonetaXml> iterator = monete.listIterator();

		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			MonetaXml mng = (MonetaXml)iterator.next();
			String anno = mng.getAnno();
			if (valori.containsKey(anno)) {
				valori.put(anno, valori.get(anno).intValue() + 1);
			} else {
				valori.put(anno, 1);
			}
		}
		return valori;
	}

	public static TreeMap<String, Number> coinByNominal() throws XmlException {
		TreeMap<String, Number> valori = new TreeMap<String, Number>();
		/* ottiene l'elenco di tutte le monete */
		List<MonetaXml> monete = CollezioneXml.getCollezione().getMonete();
		ListIterator<MonetaXml> iterator = monete.listIterator();

		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			MonetaXml mng = (MonetaXml)iterator.next();
			String chiave = mng.getNominale().getValore()+" "+mng.getNominale().getValuta(); //$NON-NLS-1$
			if (valori.containsKey(chiave)) {
				valori.put(chiave, valori.get(chiave).intValue() + 1);
			} else {
				valori.put(chiave, 1);
			}
		}
		return valori;

	}
	
}
