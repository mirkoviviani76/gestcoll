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
     * @return
     */
    public List<String> getSupporti() {
        return supporti;
    }

    /**
     *
     * @return
     */
    public List<String> getArgomenti() {
        return argomenti;
    }

    /**
     *
     * @param list
     */
    public void setArgomenti(List<String> list) {
        this.argomenti = list;
    }

    /**
     *
     * @return
     */
    public String getData() {
        return data;
    }

    /**
     *
     * @param data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     *
     * @return
     */
    public String getFilename() {
        return filename;
    }

    /**
     *
     * @param filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     *
     * @return
     */
    public List<String> getAutori() {
        return autori;
    }

    /**
     *
     * @param autori
     */
    public void setAutori(List<String> autori) {
        this.autori = autori;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     *
     * @param titolo
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     *
     * @param list
     */
    public void setSupporti(List<String> list) {
        this.supporti = list;
    }

    /**
     *
     * @return
     */
    public boolean hasElectronicForm() {
        return this.getSupporti().contains(Pubblicazione.SUPPORTO_ELETTRONICO);
    }

    public boolean posseduto() {
        return !this.getSupporti().contains(Pubblicazione.SUPPORTO_MANCANTE);
    }

    /**
     *
     * @return
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
     *
     * @return
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
     * @return
     */
    public boolean isValid() {
        return true;
    }
}
