/*
 * Modifiche:
 * -
 */

package gestXml;

import gestXml.data.Contatto;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.transform.TransformerException;

import main.Common;
import main.GestLog;

/**
 *
 * 
 */
public class ContattiXml extends GestXml {

	private ArrayList<Contatto> contatti;

	/**
	 * Costruttore
	 */
	public ContattiXml() {
		super(new File(Common.getCommon().getContattiXml()));
		try {
			contatti = new ArrayList<Contatto>();
			// legge i dati dall'xml
			readXml();
			// ordina i dati
			Collections.sort(contatti);
		} catch (TransformerException ex) {
			GestLog.Error(ContattiXml.class, ex);
		}
	}

	
	/**
	 * @return
	 */
	public ArrayList<Contatto> getContatti() {
		return contatti;
	}

	/**
	 * @throws TransformerException
	 *             FIXME
	 */
	private void readXml() throws TransformerException {
		// int contaContatti = 1;
		// String nome = "A";
		// //cicla su tutti gli armadi
		// while (!nome.equals("")) {
		// //ottiene l'id dell'armadio corrente
		// String xpath = "/contatti/contatto[" + contaContatti + "]";
		// String xpathNome = xpath + "/nome";
		// String xpathEmail = xpath + "/email";
		// String xpathNote = xpath + "/note";
		// nome = this.getNodesAsString(xpathNome);
		// if (!nome.equals(""))
		// {
		// String email = this.getNodesAsString(xpathEmail);
		// String note = this.getNodesAsString(xpathNote);
		// Contatto c = new Contatto(nome, email, note);
		// contaContatti = contaContatti + 1;
		// //aggiunge il contenitore
		// this.contatti.add(c);
		// }
		// }
	}

}
