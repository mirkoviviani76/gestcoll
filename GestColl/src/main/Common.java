/*
 * Modifiche:
 * -
 */
package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Classe di utilita' per contenere le costanti comuni e gestire il file di ini.
 * 
 */
public final class Common {

	/** Nome dell'applicazione */
	public static final String APPNAME = "GestColl";
	/** versione del progetto */
	public static final String VERSION = "31.8";
	
	
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

	/**
	 * estensioni file generati
	 */
	public static final String[] ALL = { "html", "pdf", "png" };


	
	/**
	 * estensione scheda
	 */
	public static final String[] COIN_END = { "xml" };

	/**
	 * nome del file tex per generare il pdf con la collezione
	 */
	public static final String COLLEZIONE_TEX = "Collezione.tex";

	/**
	 * formato data per xml
	 */
	public static final String DATE_XML_FORMAT = "yyyy-MM-dd";

	/**
	 * Nome del file di ini
	 */
	public static final String INI_FILE = "GestColl.ini";

	/**
	 * estensioni file inutili/temporanei
	 */
	public static final String[] INUTILI = { "aux", "toc", "log", "out" };

	private static Common istanza = null;
	/**
	 * estensione template
	 */
	public static final String TEMPLATE_END = ".template";
	/**
	 * estensioni file temporanei
	 */
	public static final String[] TMP = { "tex" };

	/**
	 * Metodo della classe impiegato per accedere al Singleton
	 * 
	 * @return l'istanza della classe singleton
	 */
	public static synchronized Common getCommon() {
		if (istanza == null) {
			istanza = new Common();
		}
		return istanza;
	}

	// Read properties file.
	private Properties properties;

	/**
	 * Legge il file ini GestColl.ini, posto nella directory del jar
	 */
	private Common() {
		super();
		if (this.properties == null) {
			try {
				properties = new Properties();
				properties.load(new FileInputStream(Common.INI_FILE));
			} catch (IOException e) {
				GestLog.Error(this.getClass(), e);
				// esce dal sistema
				System.exit(-1);
			}
		}
	}

	/**
	 * Ottiene la dir di backup
	 * 
	 * @return la dir
	 */
	public String getBackupDir() {
		return this.properties.getProperty("BACKUP_DIR");
	}

	/**
	 * Ottiene la dir di base
	 * 
	 * @return la dir
	 */
	public String getBaseDir() {
		return this.properties.getProperty("BASE_DIR");
	}

	/**
	 * Ottiene la dir della biblioteca
	 * 
	 * @return la dir
	 */
	public String getBibliotecaDir() {
		return this.properties.getProperty("BIBLIOTECA_DIR");
	}

	/**
	 * Ottiene il nome del file della biblioteca
	 * 
	 * @return il nome
	 */
	public String getBiblioXml() {
		return this.properties.getProperty("BIBLIO_XML");
	}

	/**
	 * Ottiene il nome del file dei contatti
	 * 
	 * @return il nome
	 */
	public String getContattiXml() {
		return this.properties.getProperty("CONTATTI_XML");
	}

	/**
	 * Ottiene il nome del file dei contenitori
	 * 
	 * @return il nome
	 */
	public String getContenitoriXml() {
		return this.properties.getProperty("CONTENITORI_XML");
	}

	/**
	 * ottiene la dir dei dati
	 * 
	 * @return la dir
	 */
	public String getDataDir() {
		return this.properties.getProperty("DATA_DIR");
	}

	/**
	 * Ottiene il nome del file di history
	 * 
	 * @return il nome del file
	 */
	public String getHistoryLog() {
		return this.properties.getProperty("HISTORY_LOG");
	}

	/**
	 * Ottiene la dir di html
	 * 
	 * @return la dir
	 */
	public String getHtmlDir() {
		return this.properties.getProperty("HTML_DIR");
	}

	/**
	 * Ottiene la dir di latex
	 * 
	 * @return la dir
	 */
	public String getLatexDir() {
		return this.properties.getProperty("MONETE_DIR");
	}

	/**
	 * Ottiene il nome del file dei link
	 * 
	 * @return il nome
	 */
	public String getLinksXml() {
		return this.properties.getProperty("LINKS_XML");
	}

	/**
	 * Ottiene la dir delle monete
	 * 
	 * @return la dir
	 */
	public String getMoneteDir() {
		return this.properties.getProperty("MONETE_DIR");
	}

	/**
	 * Ottiene la dir di qr
	 * 
	 * @return la dir
	 */
	public String getQrDir() {
		return this.properties.getProperty("QR_DIR");
	}

	/**
	 * Ottiene la dir dei template
	 * 
	 * @return la dir
	 */
	public String getTemplateDir() {
		return this.properties.getProperty("TEMPLATE_DIR");
	}

	/**
	 * Ottiene il nome del template xml di moneta
	 * 
	 * @return il nome
	 */
	public String getVoidMoneta() {
		return this.properties.getProperty("XML_MONETA_VOID_INSTANCE");
	}

	/**
	 * Ottiene il nome del file xsd verso html
	 * 
	 * @return il file
	 */
	public String getXslHtml() {
		return this.properties.getProperty("XSL_HTML");
	}

	/**
	 * Ottiene il nome del file xsd verso tex
	 * 
	 * @return il file
	 */
	public String getXslLatex() {
		return this.properties.getProperty("XSL_LATEX");
	}

	/**
	 * Ottiene il nome del file xsd verso txt
	 * 
	 * @return il file
	 */
	public String getXslTxt() {
		return this.properties.getProperty("XSL_TXT");
	}

}
