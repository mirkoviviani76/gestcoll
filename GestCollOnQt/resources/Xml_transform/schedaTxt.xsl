<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns="http://gestColl/coins" 
xmlns:cc="http://gestColl/coins">

<!-- versione 1.3 -->

<xsl:output method="text" indent="no" />

<xsl:param name="monetaId"></xsl:param>

<xsl:template match="/cc:monete/cc:info" />
<xsl:template match="cc:monete/cc:moneta">
<xsl:if test="@id = $monetaId">
	<xsl:call-template name="templateMoneta" />
</xsl:if>
</xsl:template>


<xsl:template name="templateMoneta" xmlns:cc="http://gestColl/coins">
<xsl:value-of select="cc:paese"/><xsl:text> </xsl:text><xsl:value-of select="cc:nominale/valore"/>
<xsl:text> </xsl:text><xsl:value-of select="cc:nominale/cc:valuta"/> (<xsl:value-of select="cc:anno"/>) - <xsl:for-each select="cc:autorita/cc:nome"><xsl:apply-templates/>; </xsl:for-each>
ID:<xsl:value-of select="@id"/>
<xsl:for-each select="cc:zecca">
ZECCA:<xsl:value-of select="cc:nome"/> (<xsl:value-of select="cc:segno"/>) 
</xsl:for-each>
<xsl:variable name="conta1" select="count(cc:zecchieri/cc:zecchiere)"/>
<xsl:if test="$conta1 != 0"><xsl:for-each select="cc:zecchieri/cc:zecchiere">ZECCHIERE: <xsl:value-of select="cc:nome"/> <xsl:value-of select="cc:segno"/> <xsl:value-of select="ruolo"/>
</xsl:for-each></xsl:if>
D/: <xsl:value-of select="cc:datiArtistici/cc:dritto/cc:descrizione"/> 
<xsl:variable name="conta2" select="count(cc:datiArtistici/cc:dritto/cc:legenda)"/>
<xsl:if test="$conta2 != 0">
<xsl:for-each select="cc:datiArtistici/cc:dritto/cc:legenda"><xsl:value-of select="cc:testo"/> <xsl:value-of select="cc:scioglimento"/>
</xsl:for-each></xsl:if>
R/:<xsl:value-of select="cc:datiArtistici/cc:rovescio/cc:descrizione"/> 
	<xsl:variable name="conta3" select="count(cc:datiArtistici/cc:rovescio/cc:legenda)"/>
	<xsl:if test="$conta3 != 0">
<xsl:for-each select="cc:datiArtistici/cc:rovescio/cc:legenda"><xsl:value-of select="cc:testo"/> <xsl:value-of select="cc:scioglimento"/>
</xsl:for-each></xsl:if>
T/: <xsl:value-of select="cc:datiArtistici/cc:taglio/cc:descrizione"/> 
	<xsl:variable name="conta4" select="count(cc:datiArtistici/cc:taglio/cc:legenda)"/>
	<xsl:if test="$conta4 != 0">
		<xsl:for-each select="cc:datiArtistici/cc:taglio/cc:legenda"><xsl:value-of select="cc:testo"/> <xsl:value-of select="cc:scioglimento"/>
</xsl:for-each></xsl:if>

DATI FISICI:<xsl:value-of select="cc:datiFisici/cc:forma"/>, <xsl:value-of select="cc:datiFisici/cc:diametro/cc:valore"/><xsl:value-of select="cc:datiFisici/cc:diametro/cc:unita"/>, <xsl:value-of select="cc:datiFisici/cc:metallo"/>, <xsl:value-of select="cc:datiFisici/cc:peso/cc:valore"/><xsl:value-of select="cc:datiFisici/cc:peso/cc:unita"/>
ACQUISTO:<xsl:value-of select="cc:datiAcquisto/cc:luogo" />, <xsl:value-of select="cc:datiAcquisto/cc:data"/>, <xsl:value-of select="cc:datiAcquisto/cc:prezzo/cc:valore"/> <xsl:value-of select="cc:datiAcquisto/cc:prezzo/cc:unita"/>
NOTE: <xsl:for-each select="cc:note/cc:nota"><xsl:apply-templates />
</xsl:for-each> 

<xsl:variable name="conta5" select="count(cc:letteratura/cc:libro)"/>
<xsl:if test="$conta5 != 0">
LETTERATURA: <xsl:for-each select="cc:letteratura/cc:libro"><xsl:value-of select="cc:sigla"/><xsl:text> </xsl:text><xsl:value-of select="cc:numero"/>;<xsl:text> </xsl:text>
</xsl:for-each>
</xsl:if>
 </xsl:template>
 
 <!-- elimina le righe vuote -->
 <xsl:template match="text&#40;&#41;" />

</xsl:stylesheet>


