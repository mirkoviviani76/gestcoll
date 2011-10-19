/*
 * Modifiche:
 * -
 */

package gui.datamodels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

/**
 * Classe per rappresentare un generico modello per una JList. I dati sono di un
 * tipo generico. La classe fornisce un metodo di ricerca testuale.
 * 
 * @param <E>
 */
@SuppressWarnings("serial")
public class GenericListModel<T> extends DefaultListModel {

	/**
     *
     */
	protected ArrayList<T> contenuto;

	/**
	 * Costruttore
	 */
	public GenericListModel() {
		contenuto = new ArrayList<T>();
	}

	/**
	 * 
	 * @param list
	 */
	public GenericListModel(List<T> list) {
		contenuto = new ArrayList<T>();
		if (list == null)
			return;
		for (T l : list) {
			contenuto.add(l);
		}
	}

	/**
	 * 
	 * @param index
	 * @return l'oggetto di indice specificato
	 */
	@Override
	public Object getElementAt(int index) {
		Object ret = null;
		if (index < contenuto.size()) {
			ret = contenuto.get(index);
		}
		return ret;
	}

	/**
	 * 
	 * @return il numero di oggetti
	 */
	@Override
	public int getSize() {
		return contenuto.size();
	}

	/**
	 * cerca il testo text nell'elenco e ritorna l'indice dell'elemento
	 * 
	 * @param text
	 * @param fromIndex
	 * @return -1 se text non e' stato trovato. L'indice altrimenti.
	 */
	public int search(String text, int fromIndex) {
		int retVal = -1;
		/* se l'indice e' troppo grande, ricomincia a cerca dall'inizio */
		if (fromIndex > this.contenuto.size()) {
			fromIndex = 0;
		}

		for (int i = fromIndex; i < this.contenuto.size(); i++) {
			@SuppressWarnings("unchecked")
			T curVal = (T) this.getElementAt(i);
			if (curVal.toString().contains(text)) {
				retVal = i;
				break;
			}
		}
		return retVal;
	}
	
}
