/*
 * Modifiche:
 * -
 */
package main;

/**
 * Classe di utilita' per contenere le costanti comuni.
 * 
 * FIXME modificare per leggere un file ini (property file)
 */
public final class Common {

	/**
	 * identifica il verso della moneta
	 */
	public enum Lato {
		/** indica il dritto */
		DRITTO,
		/** indica il rovescio */
		ROVESCIO,
		/** indica il taglio */
		TAGLIO
	};

	/** Nome dell'applicazione */
	public static final String APPNAME = "GestColl";
	/** versione del progetto */
	public static final String VERSION = "31.2alfa";
	/** directory base */
	public static final String BASE_DIR = "D:/mieProve/Java/GestColl/";
	/** directory monete */
	public static final String MONETE_DIR = BASE_DIR + "Monete";
	//public static final String MONETE_DIR = "/dati/Collezione/Monete";
	/** directory di backup */
	public static final String BACKUP_DIR = BASE_DIR + "Backup";
	/** directory biblioteca */
	public static final String BIBLIOTECA_DIR = BASE_DIR + "Biblioteca";
	/** directory aste, libri, articoli e documenti */
	public static final String LIBRI_DIR = BIBLIOTECA_DIR + "/Libri";
	/**
     *
     */
	public static final String DOCUMENTI_DIR = BIBLIOTECA_DIR + "/Documenti";
	/**
     *
     */
	public static final String ARTICOLI_DIR = BIBLIOTECA_DIR + "/Articoli";
	/**
     *
     */
	public static final String ASTE_DIR = BIBLIOTECA_DIR + "/Aste";

	/**
     *
     */
	public static final String REPORTS_DIR = BASE_DIR + "Reports";
	/**
	 * directory html
	 */
	public static final String HTML_DIR = REPORTS_DIR + "/Html";
	/**
	 * directory latex
	 */
	public static final String LATEX_DIR = REPORTS_DIR + "/LaTeX";
	/**
	 * directory qr
	 */
	public static final String QR_DIR = REPORTS_DIR + "/QR";
	/**
	 * directory template
	 */
	public static final String TEMPLATE_DIR = BASE_DIR + "bin/template";
	/**
	 * directory data
	 */
	public static final String DATA_DIR = BASE_DIR + "bin/data";
	/**
	 * file xsl per conversione da scheda a tex
	 */
	public static final String XSL_LATEX = TEMPLATE_DIR + "/schedaLaTeX.xsl";
	/**
	 * file xsl per conversione da scheda a html
	 */
	public static final String XSL_HTML = TEMPLATE_DIR + "/schedaHtml.xsl";
	/**
	 * file xsl per conversione da scheda a testo
	 */
	public static final String XSL_TXT = TEMPLATE_DIR + "/schedaTxt.xsl";
	/**
	 * schema scheda
	 */
	public static final String XSD = TEMPLATE_DIR + "/scheda.xsd";
	/**
	 * nome del template moneta xml
	 */
	public static final String XML_MONETA_VOID_INSTANCE = Common.TEMPLATE_DIR
			+ "/" + "instance.xml" + Common.TEMPLATE_END;
	/**
	 * nome dell'xml dei contenitori (vassoi)
	 */
	public static final String CONTENITORI_XML = DATA_DIR + "/contenitori.xml";
	/**
	 * nome dell'xml della biblioteca
	 */
	public static final String BIBLIO_XML = Common.BIBLIOTECA_DIR
			+ "/biblioteca.xml";
	/**
	 * nome dell'xml dei contatti
	 */
	public static final String CONTATTI_XML = DATA_DIR + "/contatti.xml";
	/**
	 * nome dell'xml dei link
	 */
	public static final String LINKS_XML = DATA_DIR + "/links.xml";
	/**
	 * nome del file dello storico
	 */
	public static final String HISTORY_LOG = DATA_DIR + "/history.txt";

	/**
     *
     */
	public static final String COLLEZIONE_TEX = "Collezione.tex";
	/**
	 * estensioni file inutili/temporanei
	 */
	public static final String[] INUTILI = { "aux", "toc", "log", "out" };
	/**
	 * estensioni file temporanei
	 */
	public static final String[] TMP = { "tex" };
	/**
	 * estensioni file generati
	 */
	public static final String[] ALL = { "html", "pdf", "png" };
	/**
	 * estensione scheda
	 */
	public static final String[] COIN_END = { "xml" };
	/**
	 * estensione template
	 */
	public static final String TEMPLATE_END = ".template";
	/**
	 * formato data per xml
	 */
	public static final String DATE_XML_FORMAT = "yyyy-MM-dd";
}
