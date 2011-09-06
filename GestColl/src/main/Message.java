/*
 * Modifiche:
 * -
 */

package main;

import java.util.logging.Level;

/**
 * Rappresenta un messaggio, costituito da un testo e da un livello di severita'.
 * 
 */
public class Message {
	/**
     * il messaggio
     */
	private String message;
	/**
     * il livello
     */
	private Level level;

	/**
	 * Costruttore
	 * @param s il messaggio
	 * @param l il livello
	 */
	public Message(String s, Level l) {
		message = s;
		level = l;
	}

	/**
	 * controlla se il livello e' SEVERE
	 * @return se il livello e' SEVERE
	 */
	public boolean isSevere() {
		return (this.level == Level.SEVERE);
	}

	@Override
	public String toString() {
		return message;
	}

}
