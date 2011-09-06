/*
 * Modifiche:
 * -
 */

package main;

/**
 * Rappresenta lo stato di avanzamento di un'attivita'.
 */
public class Progress {

	private int cur;
	private int max;
	private String msg;

	/**
	 * Costruttore
	 * 
	 * @param c il valore corrente
	 * @param m valore massimo
	 * @param msg il messaggio
	 */
	public Progress(int c, int m, String msg) {
		this.cur = c;
		this.max = m;
		this.msg = msg;
	}

	/**
	 * Ottiene il valore corrente
	 * @return il valore
	 */
	public int getCurrent() {
		return cur;
	}

	/**
	 * Ottiene il valore massimo
	 * @return il valore
	 */
	public int getMax() {
		return max;
	}

	/**
	 * ottiene il messaggio
	 * @return il messaggio
	 */
	public String getMsg() {
		return msg;
	}
}
