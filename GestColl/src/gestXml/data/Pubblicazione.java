/*
 * Modifiche:
 * -
 */
package gestXml.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * In una pubblicazione nessun campo e' obbligatorio.
 * @author furetto76
 */
public class Pubblicazione {

    /**
     *
     */
    public final static String SUPPORTO_MANCANTE = "X";
    /**
     *
     */
    public final static String SUPPORTO_ELETTRONICO = "E";
    /**
     *
     */
    public final static String SUPPORTO_STAMPA = "S";
    /**
     *
     */
    protected List<String> supporti;
    /**
     *
     */
    protected List<String> argomenti;
    /**
     *
     */
    protected String filename;
    /**
     *
     */
    protected String data;
    /**
     *
     */
    protected List<String> autori;
    /**
     *
     */
    protected String titolo;
    /**
     *
     */
    protected String id;

    /**
     *
     */
    public Pubblicazione() {
        supporti = new ArrayList<String>();
        filename = "";
        data = "";
        autori = new ArrayList<String>();
        titolo = "";
        id = "";
    }

	/**
	 * 
	 * @return i supporti
	 */
    public List<String> getSupporti() {
        return supporti;
    }

    /**
     *
     * @return gli argomenti
     */
    public List<String> getArgomenti() {
        return argomenti;
    }

    /**
     * cambia gli argomenti
     * @param list lista degli argomenti
     */
    public void setArgomenti(List<String> list) {
        this.argomenti = list;
    }

    /**
     *
     * @return la data
     */
    public String getData() {
        return data;
    }

    /**
     * setta la data
     * @param data la data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     *
     * @return il filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * setta il filename
     * @param filename il filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     *
     * @return la lista degli autori
     */
    public List<String> getAutori() {
        return autori;
    }

    /**
     * setta gli autori
     * @param autori lista degli autori
     */
    public void setAutori(List<String> autori) {
        this.autori = autori;
    }

    /**
     *
     * @return l'id
     */
    public String getId() {
        return id;
    }

    /**
     * setta l'id
     * @param id l'id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return il titolo
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * setta il titolo
     * @param titolo 
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * setta i supporti
     * @param list la lista
     */
    public void setSupporti(List<String> list) {
        this.supporti = list;
    }

    /**
     * controlla se ha un url
     * @return l'esito del controllo
     */
    public boolean hasElectronicForm() {
        return this.getSupporti().contains(Pubblicazione.SUPPORTO_ELETTRONICO);
    }

    /**
     * controlla se e' posseduto
     * @return l'esito del controllo
     */
    public boolean posseduto() {
        return !this.getSupporti().contains(Pubblicazione.SUPPORTO_MANCANTE);
    }

    /**
     * converte in xml
     * @return la stringa
     */
    public String toXmlString() {
        String _autori = "";
        for (String autore : this.getAutori()) {
            _autori = _autori + "<autore>" + autore + "</autore>";
        }
        String _supporti = "";
        for (String supp : this.getSupporti()) {
            _supporti = _supporti + "<supporto>" + supp + "</supporto>";
        }
        boolean isAsta = false;
        String xml = "\t\t";
        //controlla se contiene Aste
        if (!this.getFilename().contains("Libri_e_Documenti")) {
            isAsta = true;
            xml = xml + "<catalogo>";
        } else {
            xml = xml + "<pubblicazione>";
        }
        xml = xml + "\n\t\t\t<titolo>" + this.getTitolo() + "</titolo>";
        xml = xml + "\n\t\t\t<autori>" + _autori + "</autori>";
        xml = xml + "\n\t\t\t<supporti>" + _supporti + "</supporti>";
        if (this.hasElectronicForm()) {
            File f = new File(this.getFilename());
            String fff = f.toURI().toString().replaceAll(".*Biblioteca/", "./");
            xml = xml + "\n\t\t\t<filename>" + fff + "</filename>";
        }
        if (isAsta) {
            xml = xml + "\n\t\t</catalogo>";
        } else {
            xml = xml + "\n\t\t</pubblicazione>";
        }

        return xml;

    }

    /**
     * converte in html
     * @return la stringa
     */
    public String toHtmlString() {

        String supp = "[";
        for (String f : supporti) {
            supp = supp + f;
        }
        supp = supp + "]";

        String args = "<br>";
        if (argomenti != null && !argomenti.isEmpty()) {
            args = "<ul>";
            for (String f : argomenti) {
                args = args + "<li>" + f + "</li>";
            }
            args = args + "</ul>";
        }

        String aut = "<br>";
        if (autori != null && !autori.isEmpty()) {
            aut = "<h2>";
            for (String f : autori) {
                aut = aut + f + ", ";
            }
            aut = aut + "</h2>";
            aut = aut.replace(", </h2>", "</h2>");
        }

        return "<h2>" + titolo + "</h2>"
                + aut
                + "<b>id:</b> " + id + "<br>"
                + "<b>supporti:</b> " + supp + "<br>"
                + "<b>argomenti:</b> " + args
                + "<b>filename:</b> " + filename + "<br>"
                + "<b>data:</b> " + data;

    }

    /**
     * una pubblicazione non ha nessun campo obbligatorio, quindi e' sempre valida
     * @return l'esito del controllo
     */
    public boolean isValid() {
        return true;
    }
}
