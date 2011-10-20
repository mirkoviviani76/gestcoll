<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns="http://gestColl/coins" 
xmlns:ns0="http://gestColl/coins">

<!-- versione 1.3 -->

<xsl:output method="text" indent="no" />

<xsl:param name="monetaId"></xsl:param>
<xsl:template match="ns0:monete/ns0:moneta">
<xsl:if test="@id = $monetaId">
	<xsl:call-template name="templateMoneta" />
</xsl:if>
</xsl:template>


<xsl:template name="templateMoneta" xmlns:ns0="http://gestColl/coins">
<xsl:value-of select="ns0:paese"/><xsl:text> </xsl:text><xsl:value-of select="ns0:nominale/valore"/>
<xsl:text> </xsl:text><xsl:value-of select="ns0:nominale/ns0:valuta"/> (<xsl:value-of select="ns0:anno"/>) - <xsl:for-each select="ns0:autorita/ns0:nome"><xsl:apply-templates/>; </xsl:for-each>
ID:<xsl:value-of select="@id"/>
<xsl:for-each select="ns0:zecca">
ZECCA:<xsl:value-of select="ns0:nome"/> (<xsl:value-of select="ns0:segno"/>) 
</xsl:for-each>
<xsl:variable name="conta1" select="count(ns0:zecchieri/ns0:zecchiere)"/>
<xsl:if test="$conta1 != 0"><xsl:for-each select="ns0:zecchieri/ns0:zecchiere">ZECCHIERE: <xsl:value-of select="ns0:nome"/> <xsl:value-of select="ns0:segno"/> <xsl:value-of select="ruolo"/>
</xsl:for-each></xsl:if>
D/: <xsl:value-of select="ns0:datiArtistici/ns0:dritto/ns0:descrizione"/> 
<xsl:variable name="conta2" select="count(ns0:datiArtistici/ns0:dritto/ns0:legenda)"/>
<xsl:if test="$conta2 != 0">
<xsl:for-each select="ns0:datiArtistici/ns0:dritto/ns0:legenda"><xsl:value-of select="ns0:testo"/> <xsl:value-of select="ns0:scioglimento"/>
</xsl:for-each></xsl:if>
R/:<xsl:value-of select="ns0:datiArtistici/ns0:rovescio/ns0:descrizione"/> 
	<xsl:variable name="conta3" select="count(ns0:datiArtistici/ns0:rovescio/ns0:legenda)"/>
	<xsl:if test="$conta3 != 0">
<xsl:for-each select="ns0:datiArtistici/ns0:rovescio/ns0:legenda"><xsl:value-of select="ns0:testo"/> <xsl:value-of select="ns0:scioglimento"/>
</xsl:for-each></xsl:if>
T/: <xsl:value-of select="ns0:datiArtistici/ns0:taglio/ns0:descrizione"/> 
	<xsl:variable name="conta4" select="count(ns0:datiArtistici/ns0:taglio/ns0:legenda)"/>
	<xsl:if test="$conta4 != 0">
		<xsl:for-each select="ns0:datiArtistici/ns0:taglio/ns0:legenda"><xsl:value-of select="ns0:testo"/> <xsl:value-of select="ns0:scioglimento"/>
</xsl:for-each></xsl:if>

DATI FISICI:<xsl:value-of select="ns0:datiFisici/ns0:forma"/>, <xsl:value-of select="ns0:datiFisici/ns0:diametro/ns0:valore"/><xsl:value-of select="ns0:datiFisici/ns0:diametro/ns0:unita"/>, <xsl:value-of select="ns0:datiFisici/ns0:metallo"/>, <xsl:value-of select="ns0:datiFisici/ns0:peso/ns0:valore"/><xsl:value-of select="ns0:datiFisici/ns0:peso/ns0:unita"/>
ACQUISTO:<xsl:value-of select="ns0:datiAcquisto/ns0:luogo" />, <xsl:value-of select="ns0:datiAcquisto/ns0:data"/>, <xsl:value-of select="ns0:datiAcquisto/ns0:prezzo/ns0:valore"/> <xsl:value-of select="ns0:datiAcquisto/ns0:prezzo/ns0:unita"/>
NOTE: <xsl:for-each select="ns0:note/ns0:nota"><xsl:apply-templates />
</xsl:for-each> 

<xsl:variable name="conta5" select="count(ns0:letteratura/ns0:libro)"/>
<xsl:if test="$conta5 != 0">
LETTERATURA: <xsl:for-each select="ns0:letteratura/ns0:libro"><xsl:value-of select="ns0:sigla"/><xsl:text> </xsl:text><xsl:value-of select="ns0:numero"/>;<xsl:text> </xsl:text>
</xsl:for-each>
</xsl:if>
 


</xsl:template>
</xsl:stylesheet>


