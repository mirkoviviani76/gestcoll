/*
 * Modifiche:
 * -
 */

package gestXml.data;

/**
 * Classe che rappresenta un vassoio, organizzato in (righe x colonne) caselle
 * 
 */
public class Vassoio {

    /**
     *
     */
    public int righe; ///numero di righe
    /**
     *
     */
    public int colonne; ///numero di colonne
    /**
     *
     */
    public int id; ///id del vassoio
    private final DimensioneCaselle dim;

    /**
     *
     * @param nome
     * @param righe
     * @param colonne
     * @param dim
     */
    public Vassoio(int nome, int righe, int colonne, DimensioneCaselle dim) {
        this.righe = righe;
        this.colonne = colonne;
        this.id = nome;
        this.dim = dim;
    }

    /**
     * Ottiene la dimensione della casella
     * @return la dimensione (A, B, C, D)
     */
    public DimensioneCaselle getDim()
    {
        return this.dim;
    }
}
