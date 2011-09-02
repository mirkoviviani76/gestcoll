/*
 * Modifiche:
 * -
 */
package gestXml.data;

/**
 * In un documento, solo la descrizione e' obbligatoria
 * @author furetto76
 */
public class Documento extends Pubblicazione {
    private String descrizione;

    /**
     *
     */
    public Documento() {
        super();
        this.descrizione = "";
    }

    /**
     *
     * @return
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     *
     * @param descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    @Override
    public boolean isValid() {
        boolean ret = false;
        if (!descrizione.equals("")) {
            ret = true;
        }
        return ret;
    }

    /**
     *
     * @return
     */
    @Override
    public String toHtmlString() {
        return "<h1>DOCUMENTO</h1>"
                +super.toHtmlString() + "<br>"
                + "<b>descrizione:</b> " + descrizione;
    }

    @Override
    public String toString() {
            return String.format("(D) %s %s", this.getAutori().get(0), this.getTitolo());
    }



}