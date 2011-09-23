<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fn="http://www.w3.org/2006/xpath-functions" xmlns:xs="http://www.w3.org/2001/XMLSchema">
<!-- versione 1.3 -->

<xsl:output method="text" indent="no" />
<xsl:template match="/">
<xsl:value-of select="/moneta/paese"/><xsl:text> </xsl:text><xsl:value-of select="/moneta/nominale/valore"/>
		<xsl:text> </xsl:text><xsl:value-of select="/moneta/nominale/valuta"/> (<xsl:value-of select="/moneta/anno"/>) - <xsl:for-each select="/moneta/autorita/nome"><xsl:apply-templates/>; </xsl:for-each>
ID:<xsl:value-of select="/moneta/@id"/>
<xsl:for-each select="/moneta/zecca">
ZECCA:<xsl:value-of select="nome"/> (<xsl:value-of select="segno"/>) 
</xsl:for-each>
<xsl:variable name="conta1" select="count(/moneta/zecchieri/zecchiere)"/>
<xsl:if test="$conta1 != 0"><xsl:for-each select="/moneta/zecchieri/zecchiere">ZECCHIERE: <xsl:value-of select="nome"/> <xsl:value-of select="segno"/> <xsl:value-of select="ruolo"/>
</xsl:for-each></xsl:if>
D/: <xsl:value-of select="/moneta/datiArtistici/dritto/descrizione"/> 
<xsl:variable name="conta2" select="count(/moneta/datiArtistici/dritto/legenda)"/>
<xsl:if test="$conta2 != 0">
<xsl:for-each select="/moneta/datiArtistici/dritto/legenda"><xsl:value-of select="testo"/> <xsl:value-of select="scioglimento"/>
</xsl:for-each></xsl:if>
R/:<xsl:value-of select="/moneta/datiArtistici/rovescio/descrizione"/> 
	<xsl:variable name="conta3" select="count(/moneta/datiArtistici/rovescio/legenda)"/>
	<xsl:if test="$conta3 != 0">
<xsl:for-each select="/moneta/datiArtistici/rovescio/legenda"><xsl:value-of select="testo"/> <xsl:value-of select="scioglimento"/>
</xsl:for-each></xsl:if>
T/: <xsl:value-of select="/moneta/datiArtistici/taglio/descrizione"/> 
	<xsl:variable name="conta4" select="count(/moneta/datiArtistici/taglio/legenda)"/>
	<xsl:if test="$conta4 != 0">
		<xsl:for-each select="/moneta/datiArtistici/taglio/legenda"><xsl:value-of select="testo"/> <xsl:value-of select="scioglimento"/>
</xsl:for-each></xsl:if>

DATI FISICI:<xsl:value-of select="/moneta/datiFisici/forma"/>, <xsl:value-of select="/moneta/datiFisici/diametro/valore"/><xsl:value-of select="/moneta/datiFisici/diametro/unita"/>, <xsl:value-of select="/moneta/datiFisici/metallo"/>, <xsl:value-of select="/moneta/datiFisici/peso/valore"/><xsl:value-of select="/moneta/datiFisici/peso/unita"/>
ACQUISTO:<xsl:value-of select="/moneta/datiAcquisto/luogo" />, <xsl:value-of select="/moneta/datiAcquisto/data"/>, <xsl:value-of select="/moneta/datiAcquisto/prezzo/valore"/> <xsl:value-of select="/moneta/datiAcquisto/prezzo/unita"/>
NOTE: <xsl:for-each select="/moneta/note/nota"><xsl:apply-templates />
</xsl:for-each> 

<xsl:variable name="conta5" select="count(/moneta/letteratura/libro)"/>
<xsl:if test="$conta5 != 0">
LETTERATURA: <xsl:for-each select="/moneta/letteratura/libro"><xsl:value-of select="sigla"/><xsl:text> </xsl:text><xsl:value-of select="numero"/>;<xsl:text> </xsl:text>
</xsl:for-each>
</xsl:if>
 


</xsl:template>
</xsl:stylesheet>


