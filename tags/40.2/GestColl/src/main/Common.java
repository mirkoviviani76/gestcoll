/*
 * Modifiche:
 * -
 */
package main;

import exceptions.InternalGestCollError;
import gestXml.GestXml;

import java.io.File;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import Resources.i18n.Messages;

/**
 * Classe di utilita' per contenere le costanti comuni e gestire il file di ini.
 * 
 */
public final class Common extends GestXml {

	/** Nome dell'applicazione */
	public static final String APPNAME = "GestColl"; //$NON-NLS-1$
	/** versione del progetto */
	public static final String VERSION = "40.2"; //$NON-NLS-1$
	
	private String currentConfigId;
	
	
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
	public static final String[] ALL = { "html", "pdf", "png" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$


	
	/**
	 * estensione scheda
	 */
	public static final String[] COIN_END = { "xml" }; //$NON-NLS-1$


	/**
	 * formato data per xml
	 */
	public static final String DATE_XML_FORMAT = "yyyy-MM-dd"; //$NON-NLS-1$

	/**
	 * Nome del file di ini
	 */
	public static final String INI_FILE = "configurations.xml"; //$NON-NLS-1$

	/**
	 * estensioni file inutili/temporanei
	 */
	public static final String[] INUTILI = { "aux", "toc", "log", "out" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

	private static Common istanza = null;
	/**
	 * estensione template
	 */
	public static final String TEMPLATE_END = ".template"; //$NON-NLS-1$
	/**
	 * estensioni file temporanei
	 */
	public static final String[] TMP = { "tex" }; //$NON-NLS-1$

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
	
	
	private XmlData.Configurations.Configurations configs;
	private XmlData.Configurations.Configuration currentConfig;
	

	/**
	 * controlla se la configurazione scelta esiste nell'xml
	 * @return true se ok, false altrimenti.
	 */
	public boolean isValidConfig() {
		return (currentConfig == null ? false : true); 
	}

	/**
	 * ottiene l'id della configurazione corrente
	 * @return l'id della configurazione
	 */
	public String getCurrentConfigId() {
		return currentConfigId;
	}

	/**
	 * indica la configurazione da utilizzare
	 * @param currentConfigId la configurazione
	 */
	public void setCurrentConfigId(String currentConfigId) {
		this.currentConfigId = currentConfigId;
		this.currentConfig = null;
		/* cerca la configurazione con l'id corretto */
		for (XmlData.Configurations.Configuration config : this.configs.getConfiguration()) {
			if (config.getId().equals(currentConfigId)) {
				this.currentConfig = config;
				break;
			}
		}
			
	}

	/**
	 * Legge il file xml configurations.xml, posto nella directory del jar
	 */
	private Common() {
		super(new File(Common.INI_FILE));
		try {
			this.currentConfigId = ""; //$NON-NLS-1$
			JAXBContext jc = JAXBContext.newInstance("XmlData.Configurations"); //$NON-NLS-1$
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			/* posso fare il cast perche' le classi contengono @XmlRootElement se
			 * altrimenti si doveva fare JAXBElement<tipo> elem = (JAXBElement<tipo>)unmarshaller.unmarshal(xml)
			 * xmllinks = elem.getValue();
			 */
			configs = (XmlData.Configurations.Configurations) unmarshaller.unmarshal(new File(Common.INI_FILE));
		} catch (JAXBException e) {
			GestLog.Error(this.getClass(), e);
			// esce dal sistema
			System.exit(-1);
		}
	}

	/**
	 * Ottiene la dir di backup
	 * 
	 * @return la dir
	 */
	public String getBackupDir() {
		if (this.isValidConfig())
			return this.currentConfig.getDirs().getBackupDir();
		return null;
	}

	/**
	 * Ottiene la dir di base
	 * 
	 * @return la dir
	 */
	public String getBaseDir() {
		if (this.isValidConfig())
			return this.currentConfig.getDirs().getBaseDir();
		return null;
	}

	/**
	 * Ottiene la dir della biblioteca
	 * 
	 * @return la dir
	 */
	public String getBibliotecaDir() {
		if (this.isValidConfig())
			return this.currentConfig.getDirs().getBibliotecaDir();
		return null;
	}

	/**
	 * Ottiene il nome del file della biblioteca
	 * 
	 * @return il nome
	 */
	public String getBiblioXml() {
		if (this.isValidConfig())
			return this.currentConfig.getData().getBiblioXml();
		return null;

	}

	/**
	 * Ottiene il nome del file dei contatti
	 * 
	 * @return il nome
	 */
	public String getContattiXml() {
		if (this.isValidConfig())
			return this.currentConfig.getData().getContattiXml();
		return null;
	}

	/**
	 * Ottiene il nome del file dei contenitori
	 * 
	 * @return il nome
	 */
	public String getContenitoriXml() {
		if (this.isValidConfig())
			return this.currentConfig.getData().getContenitoriXml();
		return null;
	}

	/**
	 * Ottiene il nome del file di history
	 * 
	 * @return il nome del file
	 */
	public String getHistoryLog() {
		if (this.isValidConfig())
			return this.currentConfig.getLogs().getHistoryLog();
		return null;
	}

	/**
	 * Ottiene la dir di html
	 * 
	 * @return la dir
	 */
	public String getHtmlDir() {
		if (this.isValidConfig())
			return this.currentConfig.getDirs().getHtmlDir();
		return null;
	}

	/**
	 * Ottiene la dir di latex
	 * 
	 * @return la dir
	 */
	public String getLatexDir() {
		if (this.isValidConfig())
			return this.currentConfig.getDirs().getLatexDir();
		return null;
	}

	/**
	 * Ottiene il nome del file dei link
	 * 
	 * @return il nome
	 */
	public String getLinksXml() {
		if (this.isValidConfig())
			return this.currentConfig.getData().getLinksXml();
		return null;

	}

	/**
	 * Ottiene la dir delle immagini
	 * 
	 * @return la dir
	 */
	public String getImgDir() {
		if (this.isValidConfig())
			return this.currentConfig.getDirs().getImgDir();
		return null;

	}
	
	public String getMoneteXml() {
		if (this.isValidConfig())
			return this.currentConfig.getData().getCollezioneXml();
		return null;
	}
	
	public String getLogProperty() {
		if (this.isValidConfig())
			return this.currentConfig.getLogs().getLogProperty();
		return null;
 
	}

	/**
	 * Ottiene la dir di qr
	 * 
	 * @return la dir
	 */
	public String getQrDir() {
		if (this.isValidConfig())
			return this.currentConfig.getDirs().getQrDir();
		return null;
	}
	

	/**
	 * Ottiene l'inputstream della risorsa (dentro il jar)
	 * @param id
	 * @return lo stream
	 * @throws InternalGestCollError nel caso la risorsa non sia stata trovata
	 */
	public InputStream getResource(String id) throws InternalGestCollError {
		/* ottiene la risorsa xsl */
		InputStream ret = getClass().getResourceAsStream(id);
		if (ret == null) {
			throw new InternalGestCollError(Messages.getString("Common.0")); //$NON-NLS-1$
		}
		return ret;
	}
	


}
