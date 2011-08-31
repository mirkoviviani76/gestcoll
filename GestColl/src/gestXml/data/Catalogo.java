/*
 * Modifiche:
 * -
 */
package gestXml.data;

import java.util.ArrayList;

/**
 * In un catalogo d'asta, solo l'autore (la Casa) e' obbligatoria.
 * 
 */
public class Catalogo extends Pubblicazione {

    private String numero;

    /**
     *
     */
    public Catalogo() {
        numero = "";
        argomenti = new ArrayList<String>();
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isValid()
    {
        boolean ret = false;
        if (!autori.isEmpty())
            ret = true;
        return ret;
    }


    /**
     *
     * @param numero
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }


    /**
     *
     * @return
     */
    public String getNumero() {
        return numero;
    }

    /**
     *
     * @return
     */
    @Override
    public String toHtmlString() {
        return "<h1>CATALOGO</h1>"
               + "<b>numero:</b> " + numero + "<br>"
               + super.toHtmlString();
    }

    @Override
    public String toString() {
        return String.format("(C) %s %s (%s)", this.getAutori().get(0), this.getNumero(), this.getData());
    }


    


}
