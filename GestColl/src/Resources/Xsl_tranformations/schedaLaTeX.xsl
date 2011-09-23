<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fn="http://www.w3.org/2006/xpath-functions" xmlns:xs="http://www.w3.org/2001/XMLSchema">
<!-- versione 1.3 -->

<xsl:output method="text" indent="yes" />
<xsl:template match="/">

<xsl:variable name="directoryMoneta">../../Monete/<xsl:value-of select="/moneta/@id"/></xsl:variable>

<xsl:variable name="imgDritto">  
<xsl:value-of select="/moneta/datiArtistici/dritto/fileImmagine"/>  
</xsl:variable>
<xsl:variable name="imgRovescio">  
<xsl:value-of select="/moneta/datiArtistici/rovescio/fileImmagine"/>  
</xsl:variable>
<xsl:variable name="imgTaglio">  
<xsl:value-of select="/moneta/datiArtistici/taglio/fileImmagine"/>  
</xsl:variable>

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\moneta{<xsl:value-of select="/moneta/paese"/>}{<xsl:value-of select="/moneta/nominale/valore"/>
		<xsl:text> </xsl:text><xsl:value-of select="/moneta/nominale/valuta"/> (<xsl:value-of select="/moneta/anno"/>)}
\thispagestyle{empty}
\sezione{Descrizione}
\begin{description}
	\item[ID:]<xsl:value-of select="/moneta/@id"/>
	\item[Autorita:] <xsl:for-each select="/moneta/autorita/nome"><xsl:apply-templates/>; </xsl:for-each>
	<xsl:for-each select="/moneta/zecca">
	\item[Zecca:] \zecca{<xsl:value-of select="nome"/>}{<xsl:value-of select="segno"/>}
	</xsl:for-each>
	<xsl:variable name="conta1" select="count(/moneta/zecchieri/zecchiere)"/>
	<xsl:if test="$conta1 != 0"><xsl:for-each select="/moneta/zecchieri/zecchiere">\item[Zecchieri:] \zecchiere{<xsl:value-of select="nome"/>}{<xsl:value-of select="segno"/>}{<xsl:value-of select="ruolo"/>}
	</xsl:for-each></xsl:if>

	\item[Dritto:]<xsl:value-of select="/moneta/datiArtistici/dritto/descrizione"/> 
	<xsl:variable name="conta2" select="count(/moneta/datiArtistici/dritto/legenda)"/>
	<xsl:if test="$conta2 != 0">
	\begin{description}
		<xsl:for-each select="/moneta/datiArtistici/dritto/legenda">\item[Legenda:]\legenda{<xsl:value-of select="testo"/>}{<xsl:value-of select="scioglimento"/>}</xsl:for-each>
	\end{description}</xsl:if>

	\item[Rovescio:]<xsl:value-of select="/moneta/datiArtistici/rovescio/descrizione"/> 
	<xsl:variable name="conta3" select="count(/moneta/datiArtistici/rovescio/legenda)"/>
	<xsl:if test="$conta3 != 0">
	\begin{description}
		<xsl:for-each select="/moneta/datiArtistici/rovescio/legenda">\item[Legenda:]\legenda{<xsl:value-of select="testo"/>}{<xsl:value-of select="scioglimento"/>}</xsl:for-each>
	\end{description}</xsl:if>

	\item[Taglio:]<xsl:value-of select="/moneta/datiArtistici/taglio/descrizione"/> 
	<xsl:variable name="conta4" select="count(/moneta/datiArtistici/taglio/legenda)"/>
	<xsl:if test="$conta4 != 0">
	\begin{description}
		<xsl:for-each select="/moneta/datiArtistici/taglio/legenda">\item[Legenda:]\legenda{<xsl:value-of select="testo"/>}{<xsl:value-of select="scioglimento"/>}</xsl:for-each>
	\end{description}</xsl:if>

	\item[Forma:]<xsl:value-of select="/moneta/datiFisici/forma"/>
	\item[Diametro:]<xsl:value-of select="/moneta/datiFisici/diametro/valore"/>~<xsl:value-of select="/moneta/datiFisici/diametro/unita"/>
	\item[Metallo:]<xsl:value-of select="/moneta/datiFisici/metallo"/>
	\item[Peso:]<xsl:value-of select="/moneta/datiFisici/peso/valore"/>~<xsl:value-of select="/moneta/datiFisici/peso/unita"/>
\end{description}

<xsl:variable name="conta5" select="count(/moneta/letteratura/libro)"/>
<xsl:if test="$conta5 != 0">
\sezione{Letteratura}
\begin{description}
	\item[Letteratura:] <xsl:for-each select="/moneta/letteratura/libro"><xsl:value-of select="sigla"/><xsl:text> </xsl:text><xsl:value-of select="numero"/>;<xsl:text> </xsl:text></xsl:for-each>
\end{description}
</xsl:if>

<xsl:variable name="conta6" select="count(/moneta/note/nota)"/>
<xsl:if test="$conta6 != 0">
\sezione{Note}
<xsl:for-each select="/moneta/note/nota">
<xsl:apply-templates/>
</xsl:for-each>
</xsl:if>

\sezione{Dati Acquisto}
\begin{description}
	\item[Provenienza:]<xsl:value-of select="/moneta/datiAcquisto/luogo" />, <xsl:value-of select="/moneta/datiAcquisto/data"/><xsl:text>, </xsl:text> <xsl:value-of select="/moneta/datiAcquisto/prezzo/valore"/> <xsl:value-of select="/moneta/datiAcquisto/prezzo/unita"/>
\end{description}

\newpage
<xsl:if test="$imgDritto != ''">
\begin{figure}[H]
	\centering
	\includegraphics[width=10cm,keepaspectratio=true]{<xsl:value-of select="$directoryMoneta"/>/<xsl:value-of select="$imgDritto"/>}
\end{figure}
</xsl:if>
<xsl:if test="$imgRovescio != ''">
\begin{figure}[H]
	\centering
	\includegraphics[width=10cm,keepaspectratio=true]{<xsl:value-of select="$directoryMoneta"/>/<xsl:value-of select="$imgRovescio"/>}
\end{figure}
</xsl:if>
<xsl:if test="$imgTaglio != ''">
\begin{figure}[H]
	\centering
	\includegraphics[width=10cm,keepaspectratio=true]{<xsl:value-of select="$directoryMoneta"/>/<xsl:value-of select="$imgTaglio"/>}
\end{figure}
</xsl:if>


</xsl:template>
</xsl:stylesheet>


