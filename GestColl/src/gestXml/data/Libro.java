/*
 * Modifiche:
 * -
 */
package gestXml.data;

/**
 * In un Libro solo il titolo e' obbligatorio.
 * @author furetto76
 */
public class Libro extends Pubblicazione {

    private String isbn;
    private String volume;

    /**
     *
     */
    public Libro() {
        super();
        isbn = "";
        volume = "";
    }

    /**
     *
     * @return
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     *
     * @param isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     *
     * @return
     */
    public String getVolume() {
        return volume;
    }

    /**
     *
     * @param volume
     */
    public void setVolume(String volume) {
        this.volume = volume;
    }

    
    /**
     *
     * @return
     */
    @Override
    public String toHtmlString() {
        return "<h1>LIBRO</h1>"
               + super.toHtmlString() + "<br>"
               + "<b>isbn:</b> " + isbn + "<br>"
               + "<b>volume:</b> " + volume;
    }


    @Override
    public boolean isValid() {
        boolean ret = false;
        if (!titolo.equals("")) {
            ret = true;
        }
        return ret;
    }

    @Override
    public String toString() {
        String curTitolo = this.getTitolo();
        /* riduce il titolo, tanto non serve a molto*/
        if (curTitolo.length() > 30) {
            curTitolo = curTitolo.substring(0, 30) + "...";
        }
        String s = "";
        if (this.getAutori().size() > 0) {
            s = String.format("(L) %s %s", this.getAutori().get(0), curTitolo);
        } else {
            s = String.format("(L) %s", curTitolo);
        }
        return s;
    }


}
