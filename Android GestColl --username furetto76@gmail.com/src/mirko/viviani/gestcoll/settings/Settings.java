package mirko.viviani.gestcoll.settings;

import java.io.File;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Settings {
    private static Settings instance = null;
    
    private File xmlFile;
    private String imgDir;
    private String docDir;
    private String biblioDir;
    private File xmlBiblio;
    private SharedPreferences preferences;
    
    private final static String XML_DATA_KEY = "CollezioneXml";
    private final static String XML_DATA_DEF = "collezione.xml";
    private final static String XML_BIBLIO_DEF = "biblioteca.xml";
    private final static String IMG_SUBDIR = "img";
    private final static String DOCS_SUBDIR = "documents";
    private final static String BIBLIO_SUBDIR = "biblioteca";
    
    private Settings() {
    	xmlFile = new File(XML_DATA_DEF);
    	imgDir = xmlFile.getParent() + File.separator + IMG_SUBDIR;
    	docDir = xmlFile.getParent() + File.separator + DOCS_SUBDIR;
    	biblioDir = xmlFile.getParent() + File.separator + DOCS_SUBDIR + File.separator + BIBLIO_SUBDIR;
    	xmlBiblio = new File(xmlFile.getParent() + File.separator + XML_BIBLIO_DEF);
    }
    
    
    
    /**
	 * @return the preferences
	 */
	public SharedPreferences getPreferences() {
		return preferences;
	}



	/**
	 * @param preferences the preferences to set
	 */
	public void setPreferences(SharedPreferences preferences) {
		this.preferences = preferences;
		if (preferences.contains(XML_DATA_KEY) == true) {
			xmlFile = new File(preferences.getString(XML_DATA_KEY, XML_DATA_DEF));
		}
    	imgDir = xmlFile.getParent() + File.separator + IMG_SUBDIR;
    	docDir = xmlFile.getParent() + File.separator + DOCS_SUBDIR;
    	biblioDir = xmlFile.getParent() + File.separator + DOCS_SUBDIR + File.separator + BIBLIO_SUBDIR;
    	xmlBiblio = new File(xmlFile.getParent() + File.separator + XML_BIBLIO_DEF);
	}



	public void UpdateXmlFile(String newFile) {
    	xmlFile = new File(newFile);
    	imgDir = xmlFile.getParent() + File.separator + IMG_SUBDIR;
    	docDir = xmlFile.getParent() + File.separator + DOCS_SUBDIR;
    	biblioDir = xmlFile.getParent() + File.separator + DOCS_SUBDIR + File.separator + BIBLIO_SUBDIR;
    	xmlBiblio = new File(xmlFile.getParent() + File.separator + XML_BIBLIO_DEF);

    	Editor prefEdit = preferences.edit();
    	prefEdit.putString(XML_DATA_KEY, newFile);
    	prefEdit.commit();
    	
    }
    
 
    public static synchronized Settings getInstance() {
        if (instance == null) 
        	instance = new Settings();
        return instance;
    }
    
    

	/**
	 * @return the docDir
	 */
	public String getDocDir() {
		return docDir;
	}



	/**
	 * @param docDir the docDir to set
	 */
	public void setDocDir(String docDir) {
		this.docDir = docDir;
	}



	/**
	 * @return the biblioDir
	 */
	public String getBiblioDir() {
		return biblioDir;
	}



	/**
	 * @param biblioDir the biblioDir to set
	 */
	public void setBiblioDir(String biblioDir) {
		this.biblioDir = biblioDir;
	}



	/**
	 * @return the xmlFile
	 */
	public File getXmlFile() {
		return xmlFile;
	}

	/**
	 * @return the xmlFile
	 */
	public File getXmlBiblio() {
		return xmlBiblio;
	}
	
	
	
	/**
	 * @param xmlBiblio the xmlBiblio to set
	 */
	public void setXmlBiblio(File xmlBiblio) {
		this.xmlBiblio = xmlBiblio;
	}



	/**
	 * @param xmlFile the xmlFile to set
	 */
	public void setXmlFile(File xmlFile) {
		this.xmlFile = xmlFile;
	}

	/**
	 * @return the imgDir
	 */
	public String getImgDir() {
		return imgDir;
	}

	/**
	 * @param imgDir the imgDir to set
	 */
	public void setImgDir(String imgDir) {
		this.imgDir = imgDir;
	}
    

}
