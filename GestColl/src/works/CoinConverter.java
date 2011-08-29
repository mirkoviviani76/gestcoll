/*
 * Modifiche:
 * -
 */

package works;

import gestXml.MonetaXml;

import java.io.File;

/**
 * Definisce un'interfaccia comune alle classi che utilizzate per convertire un
 * xml di moneta in un altro formato.
 */
public interface CoinConverter {
	/**
	 * Converte l'xml in un altro formato
	 * 
	 * @param in
	 * @param outDir
	 * @return
	 * @throws Exception
	 */
	public File convert(MonetaXml in, File outDir) throws Exception;
}
