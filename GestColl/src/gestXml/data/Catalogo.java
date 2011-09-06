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
     * costruttore
     */
    public Catalogo() {
        numero = "";
        argomenti = new ArrayList<String>();
    }

    /**
     * controlla la validita'
     * @return l'esito del controllo
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
     * setta il numero
     * @param numero
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }


    /**
     *
     * @return il numero
     */
    public String getNumero() {
        return numero;
    }

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
