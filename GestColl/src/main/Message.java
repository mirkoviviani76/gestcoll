/*
 * Modifiche:
 * -
 */

package main;

import java.util.logging.Level;

/**
 *
 * 
 */
public class Message {
	/**
     *
     */
	private String message;
	/**
     *
     */
	private Level level;

	/**
	 * 
	 * @param s
	 * @param l
	 */
	public Message(String s, Level l) {
		message = s;
		level = l;
	}

	public boolean isSevere() {
		return (this.level == Level.SEVERE);
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public String toString() {
		return message;
	}

}
