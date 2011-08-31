/**
 * Metodologia versionamento (xx.yy.zz)
 *  xx : incrementato con l'aggiunta di nuove funzionalita' (major) (+10 in caso di super funzionalita')
 *  yy : incrementato per miglioramenti (minor)
 *  zz : incrementato per aggiustamenti progressivi
 * 
 * @version 1.0 prima emissione, codice convertito a partire da ruby.
 * @version 1.1 modellizzazione delle classi.
 * @version 1.2 monete.html, libri.html e aste.html generati da template.
 * @version 2.0 aggiunta funzionalita' di creazione QR.
 * @version 3.0 Implementazione GUI.
 * @version 3.1 migliorie alla GUI, alla gestione delle monete,
 *              all'uso dei template.
 * @version 3.2 inseriti getter per MonetaXml, gestione della ricerca su google
 *              per autorita, gestione data.
 * @version 3.3 migliorata gestione legende, sistemata gestione zecca e zecchiere.
 * @version 3.4 sistemata gui, aggiunta gestione note e letteratura (mancano aste)
 * @version 4.0 sistemata gui.
 * @version 5.0Beta inizio implementazione salvataggio modifiche in xml
 * @version 5.1Beta sistemata Gui. Aggiunta gestione "misure" (peso, nominale...)
 *              anche per xml.
 * @version 5.2 salvataggio file con backup
 * @version 5.3 modifica zecca e data
 * @version 5.4.1 gestione dialogo per modifica zecchiere
 * @version 5.4.2 salvataggio zecchiere in DOM
 * @version 5.5.1 gestione dialogo per modifica letteratura
 * @version 5.5.2 salvataggio letteratura in DOM
 * @version 5.6.1 incapsulamento dati zecca in classi
 * @version 5.6.2 incapsulamento dati zecchiere in classi
 * @version 5.6.3 incapsulamento dati letteratura in classi
 * @version 5.7.1 modifica e salvataggio Note
 * @version 6.1 aggiunta nodi singoli non esistenti (es. aggiunta ruolo zecchiere, aggiunta anno)
 * @version 6.2 aggiunta miglioramenti MonetaDescrizione, aggiunti pulsanti "+", sistemate actionlistener,
 *              migliorata gui, enum lato appartiene ora a common.
 * @version 6.3 aggiunta gerarchia di nodi (libro, zecchiere, nota, autorita, legenda)
 * @version 6.4 MonetaXml deriva ora da GestXml, che racchiude le operazione generiche sull'xml
 * @version 7.0 gestione Documenti (visualizzazione)
 * @version 7.1 gestione Documenti (aggiunta, modifica)
 * @version 8.0 suggerimento id per nuova moneta.
 * @version 8.1 creazione xml nuova moneta. Ordinamento della lista monete ora effettuata per anno e per progressivo.
 * @version 9.0 inserimento di un textfield in alto a destra per contenere i testi dei messagebox... cliccandoci sopra si apre il messagebox.
 * @version 9.1 riorganizzata la classe Message per prendere anche un livello, utilizzato per mostrare il messaggio in diversi colori.
 * @version 10.0.0 creazione xml "biblioteca" per libri, documenti, aste
 * @version 10.0.1 eliminazione Libri2Html
 * @version 10.1.0 lettura xml libreria cataloghi
 * @version 10.1.1 lettura xml libreria item libri
 * @version 10.1.2 lettura xml libreria item documenti
 * @version 10.1.3 lettura xml libreria item articoli
 * @version 10.2.0 visualizzazione xml libreria cataloghi
 * @version 10.2.1 visualizzazione xml libreria item libri
 * @version 10.2.2 visualizzazione xml libreria item documenti
 * @version 10.2.3 visualizzazione xml libreria item articoli
 * @version 10.3.0 miglioramento visualizzazione biblioteca
 * @version 20.0 gestione posizione e vassoi (visualizzazione)
 * @version 20.0.1 vassoi visualizzati a tabella
 * @version 20.0.2 migliorata table modello
 * @version 20.0.3 migliorata gestione classi contenitori
 * @version 20.1 aggiunta gestore clic nei vassoi
 * @version 20.2 aggiunta gestore clic alla posizione
 * @version 20.3 i contenitori sono configurati da xml (contenitori.xml)
 * @version 20.4 aggiunto splashscreen (modifica manifest.xml e le proprieta' del progetto)
 * @version 20.4.1 migliorato splashscreen
 * @version 21.0.0 gestione contatti e link (visualizzazione e clic)
 * @version 21.0.1 migliorata gui: i pulsanti rimuovi sono ora nel menu Strumenti
 *                 e altre modifiche minori.
 * @version 21.0.2 - bug risolto in gestione etichette
 *                   (autorita deve essere valorizzata almeno con uno spazio)
 *                 - Aggiunto scorrimento automatico all'indice della moneta
 *                   in caso di doppio click sul vassoio.
 *                 - Aggiunto pulsante alla biblioteca per files disponibili
 *                   (non funziona sotto windows)
 *                 - Riorganizzato progetto raggruppando i datamodels in un package
 * @version 22.0   - aggiunta scansione libri non presenti in biblioteca.xml
 *                 - semplificata tipologia item di biblioteca
 *                 - titolo nella lista biblioteca ridotto per comodita' di vista
 * @version 22.1   - migliorata gestione "scansione libri" per distinguere
 *                   fra libri e cataloghi
 *                 - Unificati la maggior parte dei ListModel in una classe
 *                   template GenericListModel
 *                 - Unificati la maggior parte dei CellRenderer in una classe
 *                   template GenericCellRenderer
 * @version 23.0   - inserita ricerca (embrionale) per links e contatti
 *                 - inserita ricerca (embrionale) per monete
 * @version 23.1   - migliorata struttura cellrenderer e listmodel
 *                 - ridotto il numero di package
 *                 - introdotta classe Legenda
 *                 - introdotta ricerca "cerca il prossimo" automatico
 * @version 23.2   - introdotta dir di backup per le monete modificate,
 *                   template per i template e data per gli ausiliari xml
 *                 - corretto bug in caso di data non presente
 * @version 23.2.1 - piccoli miglioramenti
 * @version 23.2.2 - lo splash mostra ora anche la versione
 *                 - il nome dell'applicazione nello splash e' ora a codice
 * @version 23.3   - aggiunto pulsante "->Clip" per copiare il testo completo
 *                   dei dati monetali nella clipboard.
 * @version 23.3.1 - modifiche minori
 * @version 23.4   - l'elenco delle monete si puo' ordinare anche per paese
 * @version 23.5   - form per edit della descrizione con pulsanti di aiuto
 *                   per caratteri strani.
 * @version 30.0   - introdotte statistiche (solo bozza)
 * @version 30.1   - aggiunti grafici
 *                 - aggiunta classe History
 *                 - aggiunta messaggi di info al MainForm.
 * @version 30.2.0 - aggiunta visualizzazione history.
 * @version 30.2.1 - risolto bug in visualizzazione data.
 * @version 30.3   - aggiunta EditButtonPanel per inserimento caratteri Unicode
 *                   in Descrizione e Legenda.
 * @version 30.3.1 - sistemato bug in thread etichette e tex 
 *                   (in questo caso un unico thread con due funzioni)
 * @version 30.4   - in edit, la descrizione mostra anche l'immagine della moneta.
 *                 - migliorata/aggiunta gestione messaggi e avanzamento
 * @version 31.0a  - xml letto tramite jaxb. Progetto sotto eclipse. Downgrade delle feature durante il refactoring.                
 * @version 31.1a  - Sistemato visualizzazione, vassoi, tex, html, statistiche. Sistemare modifica e salvataggio. Downgrade delle feature durante il refactoring.                
 * @version 31.2a  - Sistemata visualizzazione links (da migliorare), proprieta' lette dal file GestColl.ini
 * @version 31.3a  - Sistemata visualizzazione immagini con possiblita' di ruotare e scalare
 *
 *  
 * TODO migliorare contatti e links
 * TODO pagina che visualizzi sigla -> libro (magari da integrare con la scheda moneta...)
 *
 * TODO Progetto: aggiungere cartella "scripts" per ant linux e java
 */
package main;

import gui.MainFrame;

import java.awt.Frame;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;

/**
 *
 * 
 */
public class Main {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// create Options object

		/* setta un log manager (non sarebbe necessario, ma cosi' legge da file */
		LogManager mn = LogManager.getLogManager();
		try {
			// le proprieta' specificate prevedono logging su video e su file
			FileInputStream fis = new FileInputStream(Common.getCommon().getTemplateDir() + "/"
					+ "myLoggingProperties.properties");
			mn.readConfiguration(fis);
		} catch (IOException ex) {
			System.err.println("ERRORE:" + ex.getMessage());
		} catch (SecurityException ex) {
			System.err.println("ERRORE:" + ex.getMessage());
		}

		/* invoca la gui */
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainFrame mf = new MainFrame();
					// massimizza
					mf.setExtendedState(mf.getExtendedState()
							| Frame.MAXIMIZED_BOTH);
					mf.setVisible(true);
				} catch (NullPointerException ex) {
					GestLog.Error(Main.class, ex);
				} catch (IllegalStateException ex) {
					GestLog.Error(Main.class, ex);
				}
			}
		});
	}
}
