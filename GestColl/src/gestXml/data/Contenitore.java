/*
 * Modifiche:
 * -
 */
package gestXml.data;

import java.util.HashMap;

/**
 * Classe che rappresenta un contenitore, inteso come raccoglitore di vassoi
 * 
 */
public class Contenitore {
    
    /**
     *
     */
    public HashMap<Integer, Vassoio> contenitore;
    /**
     *
     */
    public int id;

    /**
     * Crea un contenitore
     * @param nome
     */
    public Contenitore(int nome)
    {
        this.id = nome;
        this.contenitore = new HashMap<Integer, Vassoio>();
    }

    /**
     * Aggiunge un vassoio
     * @param v il vassoio
     */
    public void add(Vassoio v)
    {
        contenitore.put(v.id, v);
    }

    /**
     * Ottiene la dimensione delle caselle del vassoio specificato
     * @param nomeVassoio il vassoio
     * @return la dimensione della casella
     */
    public DimensioneCaselle getDim(int nomeVassoio)
    {
        return contenitore.get(nomeVassoio).getDim();
    }

}
