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
	 * Costruttore
	 */
	public GenericListModel() {
	}

	/**
	 * 
	 * @param list
	 */
	public GenericListModel(List<T> list) {
		if (list == null)
			return;
		for (T l : list) {
			this.addElement(l);
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
		if (index < this.getSize()) {
			ret = this.get(index);
		}
		return ret;
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
		if (fromIndex > this.getSize()) {
			fromIndex = 0;
		}

		for (int i = fromIndex; i < this.getSize(); i++) {
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
