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
	
    private static Common istanza = null;
    
	/** Nome dell'applicazione */
	public static final String APPNAME = "GestColl";
	/** versione del progetto */
	public static final String VERSION = "31.3alfa";
	
	// Read properties file.
	private Properties properties;

	
	/**
	 * Legge il file ini GestColl.ini, posto nella directory del jar
	 */
	private Common() {
		super();
		if (this.properties == null)
		{
			try {
				properties = new Properties();
				properties.load(new FileInputStream("GestColl.ini"));
			} catch (IOException e) {
				GestLog.Error(this.getClass(), e);
			}
		}
	}
    
    /**
     * Metodo della classe impiegato per accedere al Singleton
     * @return l'istanza della classe singleton
     */
    public static synchronized Common getCommon() {
        if (istanza == null) 
            istanza = new Common();
        return istanza;
    }

    
	
	
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



	public String getTemplateDir() {
		return this.properties.getProperty("TEMPLATE_DIR");
	}

	public String getHistoryLog() {
		return this.properties.getProperty("HISTORY_LOG");
	}

	public String getXslHtml() {
		return this.properties.getProperty("XSL_HTML");
	}

	public String getXslTxt() {
		return this.properties.getProperty("XSL_TXT");
	}

	public String getXslLatex() {
		return this.properties.getProperty("XSL_LATEX");
	}

	public String getMoneteDir() {
		return this.properties.getProperty("MONETE_DIR");
	}

	public String getLatexDir() {
		return this.properties.getProperty("MONETE_DIR");
	}

	public String getBaseDir() {
		return this.properties.getProperty("BASE_DIR");
	}

	public String getBiblioXml() {
		return this.properties.getProperty("BIBLIO_XML");
	}

	public String getContattiXml() {
		return this.properties.getProperty("CONTATTI_XML");
	}

	public String getLinksXml() {
		return this.properties.getProperty("LINKS_XML");
	}

	public String getContenitoriXml() {
		return this.properties.getProperty("CONTENITORI_XML");
	}

	public String getQrDir() {
		return this.properties.getProperty("QR_DIR");
	}

	public String getHtmlDir() {
		return this.properties.getProperty("HTML_DIR");
	}

	public String getVoidMoneta() {
		return this.properties.getProperty("XML_MONETA_VOID_INSTANCE");
	}

	public String getBibliotecaDir() {
		return this.properties.getProperty("BIBLIOTECA_DIR");	
		}
	
	public String getBackupDir() {
		return this.properties.getProperty("BACKUP_DIR");
	}
	
	
	

}
