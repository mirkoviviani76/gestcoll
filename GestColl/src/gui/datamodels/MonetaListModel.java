/*
 * Modifiche:
 * -
 */

package gui.datamodels;

import exceptions.XmlException;
import gestXml.CollezioneXml;
import gestXml.MonetaXml;

import java.util.ArrayList;
import java.util.ListIterator;

import main.Splash;

/**
 *
 * 
 */
public class MonetaListModel extends GenericListModel<MonetaXml> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @throws XmlException
	 * 
	 */
	public MonetaListModel(MonetaXml.Ordering ordering) throws XmlException {
		super();
		/* ottiene l'elenco di tutte le monete */
		@SuppressWarnings("rawtypes")
		ListIterator iterator = null;
		ArrayList<MonetaXml> monete = CollezioneXml.getCollezione().getMonete(ordering);
		iterator = monete.listIterator();

		if (iterator == null)
			return;

		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			//carica una moneta
			MonetaXml mng = (MonetaXml) (iterator.next());
			//mostra lo splash (se necessario)
			Splash.getInstance().splashProgress(mng.getId());
			//sistema l'ordinamento
			mng.setOrdering(ordering);
			//aggiunge alla lista
			contenuto.add(mng);
		}
	}

}
