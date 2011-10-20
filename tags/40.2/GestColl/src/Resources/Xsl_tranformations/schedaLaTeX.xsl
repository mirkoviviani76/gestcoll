<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns="http://gestColl/coins" 
xmlns:ns0="http://gestColl/coins">
<!-- versione 1.3 -->

<xsl:output method="text" indent="yes" />

<xsl:param name="monetaId"></xsl:param>
<xsl:template match="ns0:monete/ns0:moneta">
<xsl:if test="@id = $monetaId">
	<xsl:call-template name="templateMoneta" />
</xsl:if>
</xsl:template>


<xsl:template name="templateMoneta" xmlns:ns0="http://gestColl/coins">
<xsl:variable name="directoryMoneta">../../bin/data/img</xsl:variable>

<xsl:variable name="imgDritto">  
<xsl:value-of select="ns0:datiArtistici/ns0:dritto/ns0:fileImmagine"/>  
</xsl:variable>
<xsl:variable name="imgRovescio">  
<xsl:value-of select="ns0:datiArtistici/ns0:rovescio/ns0:fileImmagine"/>  
</xsl:variable>
<xsl:variable name="imgTaglio">  
<xsl:value-of select="ns0:datiArtistici/ns0:taglio/ns0:fileImmagine"/>  
</xsl:variable>

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\moneta{<xsl:value-of select="ns0:paese"/>}{<xsl:value-of select="ns0:nominale/ns0:valore"/>
		<xsl:text> </xsl:text><xsl:value-of select="ns0:nominale/ns0:valuta"/> (<xsl:value-of select="ns0:anno"/>)}
\thispagestyle{empty}
\sezione{Descrizione}
\begin{description}
	\item[ID:]<xsl:value-of select="@id"/>
	\item[Autorita:] <xsl:for-each select="ns0:autorita/ns0:nome"><xsl:apply-templates/>; </xsl:for-each>
	<xsl:for-each select="ns0:zecca">
	\item[Zecca:] \zecca{<xsl:value-of select="ns0:nome"/>}{<xsl:value-of select="ns0:segno"/>}
	</xsl:for-each>
	<xsl:variable name="conta1" select="count(ns0:zecchieri/ns0:zecchiere)"/>
	<xsl:if test="$conta1 != 0"><xsl:for-each select="ns0:zecchieri/ns0:zecchiere">\item[Zecchieri:] \zecchiere{<xsl:value-of select="nome"/>}{<xsl:value-of select="ns0:segno"/>}{<xsl:value-of select="ns0:ruolo"/>}
	</xsl:for-each></xsl:if>

	\item[Dritto:]<xsl:value-of select="ns0:datiArtistici/ns0:dritto/ns0:descrizione"/> 
	<xsl:variable name="conta2" select="count(ns0:datiArtistici/ns0:dritto/ns0:legenda)"/>
	<xsl:if test="$conta2 != 0">
	\begin{description}
		<xsl:for-each select="ns0:datiArtistici/ns0:dritto/ns0:legenda">\item[Legenda:]\legenda{<xsl:value-of select="ns0:testo"/>}{<xsl:value-of select="ns0:scioglimento"/>}</xsl:for-each>
	\end{description}</xsl:if>

	\item[Rovescio:]<xsl:value-of select="ns0:datiArtistici/ns0:rovescio/ns0:descrizione"/> 
	<xsl:variable name="conta3" select="count(ns0:datiArtistici/ns0:rovescio/ns0:legenda)"/>
	<xsl:if test="$conta3 != 0">
	\begin{description}
		<xsl:for-each select="ns0:datiArtistici/ns0:rovescio/ns0:legenda">\item[Legenda:]\legenda{<xsl:value-of select="ns0:testo"/>}{<xsl:value-of select="ns0:scioglimento"/>}</xsl:for-each>
	\end{description}</xsl:if>

	\item[Taglio:]<xsl:value-of select="ns0:datiArtistici/ns0:taglio/ns0:descrizione"/> 
	<xsl:variable name="conta4" select="count(ns0:datiArtistici/ns0:taglio/ns0:legenda)"/>
	<xsl:if test="$conta4 != 0">
	\begin{description}
		<xsl:for-each select="ns0:datiArtistici/ns0:taglio/ns0:legenda">\item[Legenda:]\legenda{<xsl:value-of select="ns0:testo"/>}{<xsl:value-of select="ns0:scioglimento"/>}</xsl:for-each>
	\end{description}</xsl:if>

	\item[Forma:]<xsl:value-of select="ns0:datiFisici/ns0:forma"/>
	\item[Diametro:]<xsl:value-of select="ns0:datiFisici/ns0:diametro/ns0:valore"/>~<xsl:value-of select="ns0:datiFisici/ns0:diametro/ns0:unita"/>
	\item[Metallo:]<xsl:value-of select="ns0:datiFisici/ns0:metallo"/>
	\item[Peso:]<xsl:value-of select="ns0:datiFisici/ns0:peso/ns0:valore"/>~<xsl:value-of select="ns0:datiFisici/ns0:peso/ns0:unita"/>
\end{description}

<xsl:variable name="conta5" select="count(ns0:letteratura/ns0:libro)"/>
<xsl:if test="$conta5 != 0">
\sezione{Letteratura}
\begin{description}
	\item[Letteratura:] <xsl:for-each select="ns0:letteratura/ns0:libro"><xsl:value-of select="ns0:sigla"/><xsl:text> </xsl:text><xsl:value-of select="ns0:numero"/>;<xsl:text> </xsl:text></xsl:for-each>
\end{description}
</xsl:if>

<xsl:variable name="conta6" select="count(ns0:note/ns0:nota)"/>
<xsl:if test="$conta6 != 0">
\sezione{Note}
<xsl:for-each select="ns0:note/ns0:nota">
<xsl:apply-templates/>
</xsl:for-each>
</xsl:if>

\sezione{Dati Acquisto}
\begin{description}
	\item[Provenienza:]<xsl:value-of select="ns0:datiAcquisto/ns0:luogo" />, <xsl:value-of select="ns0:datiAcquisto/ns0:data"/><xsl:text>, </xsl:text> <xsl:value-of select="ns0:datiAcquisto/ns0:prezzo/ns0:valore"/> <xsl:value-of select="ns0:datiAcquisto/ns0:prezzo/ns0:unita"/>
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


