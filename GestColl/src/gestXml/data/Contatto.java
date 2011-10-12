/*
 * Modifiche:
 * -
 */
package gestXml.data;

/**
 *
 * 
 */
public class Contatto implements Comparable<Contatto> {
	/**
     *
     */
	public String email;
	/**
     *
     */
	public String nome;
	/**
     *
     */
	public String note;

	/**
	 * 
	 * @param nome
	 * @param email
	 * @param note
	 */
	public Contatto(String nome, String email, String note) {
		this.nome = nome;
		this.email = email;
		this.note = note;
	}

	@Override
	public int compareTo(Contatto o) {
		return this.nome.compareTo(o.nome);
	}

	@Override
	public String toString() {
		return nome + ": " + email + " " + note; //$NON-NLS-1$ //$NON-NLS-2$
	}

}
