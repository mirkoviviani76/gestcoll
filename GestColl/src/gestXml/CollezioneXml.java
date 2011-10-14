package gestXml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import main.Common;
import main.GestLog;
import Resources.i18n.Messages;
import exceptions.InternalGestCollError;
import exceptions.XmlException;

public class CollezioneXml extends GestXml {
	
	private final static String NEW_MONETA_TEMPLATE = "/Resources/templates/instance.xml.template"; //$NON-NLS-1$
	
	
	private XmlData.Moneta.Monete collezioneXml;
	
	private ArrayList<MonetaXml> collezione;

	private static CollezioneXml istanza = null;
	
	/**
	 * Il file della moneta
	 * 
	 * @param _xmlFile
	 * @throws XmlException
	 */
	private CollezioneXml() throws XmlException {
		super(new File(Common.getCommon().getMoneteXml()));
		this.LoadData(new File(Common.getCommon().getMoneteXml()));
	}

	/**
	 * carica i dati dall'xml
	 * @param _xmlFile
	 * @throws XmlException
	 */
	private void LoadData(File _xmlFile) throws XmlException {
		try {
			JAXBContext jc = JAXBContext.newInstance("XmlData.Moneta");
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			this.collezioneXml = (XmlData.Moneta.Monete) unmarshaller
					.unmarshal(new InputStreamReader(new FileInputStream(
							_xmlFile
							), "UTF-8")); //$NON-NLS-1$
			collezione = new ArrayList<MonetaXml>(); 
			for (XmlData.Moneta.Moneta m : this.collezioneXml.getMoneta()) {
				MonetaXml mmm = new MonetaXml(m);
				collezione.add(mmm);
			}
			System.gc();
		} catch (JAXBException e) {
			throw new XmlException("MonetaXml() "+Messages.getString("Generic.12") //$NON-NLS-1$
					+ _xmlFile, e);
		} catch (UnsupportedEncodingException e) {
			throw new XmlException("MonetaXml() "+Messages.getString("Generic.13") //$NON-NLS-1$
					+ _xmlFile, e);
		} catch (FileNotFoundException e) {
			throw new XmlException("MonetaXml() "+Messages.getString("Generic.14") //$NON-NLS-1$
					+ _xmlFile, e);
		}
	}
	
	/**
	 * Metodo della classe impiegato per accedere al Singleton
	 * 
	 * @return l'istanza della classe singleton
	 * @throws XmlException 
	 */
	public static synchronized CollezioneXml getCollezione() throws XmlException {
		if (istanza == null) {
			istanza = new CollezioneXml();
		}
		return istanza;
	}
	
	
	public ArrayList<MonetaXml> getMonete(MonetaXml.Ordering ord) {
		for (MonetaXml m : this.collezione) {
			m.setOrdering(ord);
		}
		Collections.sort(this.collezione);
		return this.collezione;
	}

	public ArrayList<MonetaXml> getMonete() {
		return this.collezione;
	}

	public Object getJaxbObject() {
		return this.collezioneXml;
	}

	public void writeXml(String string, String outFile) throws XmlException {
		super.writeXml(this.collezioneXml, string, outFile);
	}

	public MonetaXml getMoneta(String id) {
		MonetaXml ret = null;
		for (MonetaXml m : this.collezione) {
			if (m.getId().equals(id)) {
				ret = m;
				break;
			}
		}
		return ret; 
	}

	public static int size() {
		int ret = 0;
		try {
			ret = CollezioneXml.getCollezione().getMonete().size();
		} catch (XmlException e) {
			GestLog.Error(CollezioneXml.class, e);
			ret = 0;
		}
		return ret;
	}

	/**
	 * aggiunge una moneta (vuota)
	 * @param id
	 * @throws XmlException 
	 */
	public void addMoneta(String anno, String id) throws XmlException {
		InputStream xmlTemplate;
		try {
			//carica il template
			xmlTemplate = Common.getCommon().getResource(NEW_MONETA_TEMPLATE);
			JAXBContext jc = JAXBContext.newInstance("XmlData.Moneta");
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			XmlData.Moneta.Monete temp = (XmlData.Moneta.Monete) unmarshaller
					.unmarshal(xmlTemplate); //$NON-NLS-1$
			//TODO sistemare il template
			XmlData.Moneta.Moneta nuova = temp.getMoneta().get(0);
			nuova.setAnno(anno);
			nuova.setId(id);
			nuova.getDatiArtistici().getDritto().setFileImmagine(id+"-D.jpg");
			nuova.getDatiArtistici().getRovescio().setFileImmagine(id+"-R.jpg");
			nuova.getDatiArtistici().getTaglio().setFileImmagine(id+"-T.jpg");
			
			
			//aggiunge la moneta
			this.collezioneXml.getMoneta().add(nuova);
			//salva il nuovo file
			this.writeXml(this.collezioneXml, "XmlData.Moneta", Common.getCommon().getMoneteXml());
			//ricarica i dati
			this.LoadData(new File(Common.getCommon().getMoneteXml()));
			

		} catch (InternalGestCollError e) {
			throw new XmlException("MonetaXml() "+Messages.getString("Generic.12") //$NON-NLS-1$
					+ NEW_MONETA_TEMPLATE, e);
		} catch (JAXBException e) {
			throw new XmlException("MonetaXml() "+Messages.getString("Generic.12") //$NON-NLS-1$
					+ NEW_MONETA_TEMPLATE, e);
		}			
	}
	
}
