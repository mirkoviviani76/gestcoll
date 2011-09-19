/*
 * Modifiche:
 * -
 */

package gui.datamodels;

import exceptions.XmlException;
import gestXml.MonetaXml;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import main.Splash;
import works.CollectionWorker;

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
		List<File> files = CollectionWorker.getCoinsFileListing();
		iterator = files.listIterator();

		if (iterator == null)
			return;

		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			//carica una moneta
			MonetaXml mng = new MonetaXml((File) (iterator.next()));
			//mostra lo splash (se necessario)
			Splash.getInstance().splashProgress(mng.getId());
			//sistema l'ordinamento
			mng.setOrdering(ordering);
			//aggiunge alla lista
			contenuto.add(mng);
		}
		Collections.sort(contenuto);

	}

}
