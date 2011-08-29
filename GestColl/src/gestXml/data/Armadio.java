/*
 * Modifiche:
 * -
 */

package gestXml.data;

import java.util.HashMap;

/**
 * Classe che rappresenta una raccolta di contenitori, ovvero un'intera collezione
 * 
 */
public final class Armadio {

    /**
     *
     */
    public HashMap<Integer, Contenitore> armadio;
    /**
     *
     */
    public String nome;

    /**
     *
     * @param nome
     */
    public Armadio(String nome) {
        armadio = new HashMap<Integer, Contenitore>();
        this.nome = nome;
    }


    /**
     * Aggiunge un contenitore
     * @param v il contenitore
     */
    public void add(Contenitore v) {
        this.armadio.put(v.id, v);
    }

    /**
     * Ottiene la dimensione della casella
     * @param nomeContenitore il contenitore
     * @param nomeVassoio il vassoio
     * @return la dimensione
     */
    public DimensioneCaselle getDim(int nomeContenitore, int nomeVassoio) {
        return armadio.get(nomeContenitore).getDim(nomeVassoio);
    }

}
