/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package works;

import gestXml.MonetaXml;

import java.io.File;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeMap;

import javax.xml.bind.JAXBException;

import main.Common;
import main.GestLog;
import XmlData.Moneta.Misura;

/**
 * 
 * @author intecs
 */
public class Statistiche {

	public static TreeMap<String, Integer> coinByYear() {
		TreeMap<String, Integer> valori = new TreeMap<String, Integer>();
		/* ottiene l'elenco di tutte le monete */
		List<File> files = CollectionWorker.getFileListing(Common.MONETE_DIR,
				Common.COIN_END);
		ListIterator<File> iterator = files.listIterator();

		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			try {
				MonetaXml mng = new MonetaXml((iterator.next()));
				String anno = mng.getAnno();
				if (valori.containsKey(anno)) {
					valori.put(anno, valori.get(anno) + 1);
				} else {
					valori.put(anno, 1);
				}
			} catch (JAXBException e) {
				GestLog.Error(Statistiche.class, e);
			}
		}
		return valori;
	}

	public static TreeMap<String, Integer> coinByMetal() {
		TreeMap<String, Integer> valori = new TreeMap<String, Integer>();
		/* ottiene l'elenco di tutte le monete */
		List<File> files = CollectionWorker.getFileListing(Common.MONETE_DIR,
				Common.COIN_END);
		ListIterator<File> iterator = files.listIterator();

		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			try {
				MonetaXml mng = new MonetaXml((iterator.next()));
				String metallo = mng.getMetallo();
				if (valori.containsKey(metallo)) {
					valori.put(metallo, valori.get(metallo) + 1);
				} else {
					valori.put(metallo, 1);
				}
			} catch (JAXBException ex) {
				GestLog.Error(Statistiche.class, ex);
			}
		}
		return valori;
	}

	public static TreeMap<Double, Integer> coinBySize() {
		TreeMap<Double, Integer> valori = new TreeMap<Double, Integer>();
		/* ottiene l'elenco di tutte le monete */
		List<File> files = CollectionWorker.getFileListing(Common.MONETE_DIR,
				Common.COIN_END);
		ListIterator<File> iterator = files.listIterator();

		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			try {
				MonetaXml mng = new MonetaXml((iterator.next()));
				Misura diam = mng.getDiametro();
				double d = Double.parseDouble(diam.getValore().toPlainString());
				if (valori.containsKey(d)) {
					valori.put(d, valori.get(d) + 1);
				} else {
					valori.put(d, 1);
				}
			} catch (JAXBException ex) {
				GestLog.Error(Statistiche.class, ex);
			}
		}
		return valori;

	}

}
