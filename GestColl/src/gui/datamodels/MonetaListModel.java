/*
 * Modifiche:
 * -
 */

package gui.datamodels;

import gestXml.MonetaXml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import javax.xml.bind.JAXBException;

import main.Common;
import main.GestLog;
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
     *
     */
	public MonetaListModel(MonetaXml.Ordering ordering) {
		super();
		/* ottiene l'elenco di tutte le monete */
		@SuppressWarnings("rawtypes")
		ListIterator iterator = null;
		try {
			List<File> files = CollectionWorker.getFileListing(new File(
					Common.getCommon().getMoneteDir()), Common.COIN_END);
			iterator = files.listIterator();

		} catch (FileNotFoundException ex) {
			GestLog.Error(MonetaListModel.class, ex);
		}
		if (iterator == null) {
			return;
		}

		/* cicla su tutte le monete */
		while (iterator.hasNext()) {
			MonetaXml mng;
			try {
				mng = new MonetaXml((File) (iterator.next()));
				mng.setOrdering(ordering);
				contenuto.add(mng);
			} catch (JAXBException e) {
				GestLog.Error(this.getClass(), e);
			}
		}
		Collections.sort(contenuto);

	}

}
