<?xml version="1.0" encoding="UTF-8"?> <!-- Prologo XML-->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns="http://gestColl/coins" 
xmlns:ns0="http://gestColl/coins">



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

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><xsl:value-of select="@id"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="report.css" />
</head>

<body>
	<div id='collezione_header' class='collezione_header'><h1>
		<xsl:value-of select="ns0:paese"/><xsl:text> </xsl:text><xsl:value-of select="ns0:nominale/ns0:valore"/><xsl:text> </xsl:text><xsl:value-of select="ns0:nominale/ns0:valuta"/> (<xsl:value-of select="ns0:anno"/>)<br/>
		<xsl:value-of select="@id"/></h1>
	</div> <!-- fine collezione_header -->
 
	<div id='collezione_content' class='collezione_content'> 
		<h2><a name="datiMonetali">Dati monetali</a></h2>
			<ul>
				<li><span class="categoria">Paese:</span>
					<ul>
						<xsl:for-each select="ns0:paese"><li><xsl:apply-templates/></li></xsl:for-each>
					</ul>
				</li>
				<li><span class="categoria">Autorita:</span>
					<ul>
						<xsl:for-each select="ns0:autorita/ns0:nome"><li><xsl:apply-templates/></li></xsl:for-each>
					</ul>
				</li>
				<li><span class="categoria">Zecca:</span>
					<xsl:for-each select="ns0:zecca">Nome: <xsl:value-of select="ns0:nome"/> Segno: <xsl:value-of select="ns0:segno"/><br /></xsl:for-each>
				</li>
				<li><span class="categoria">Zecchieri:</span>
					<xsl:for-each select="ns0:zecchieri/ns0:zecchiere">Nome: <xsl:value-of select="ns0:nome"/> Segno: <xsl:value-of select="ns0:segno"/> Ruolo: <xsl:value-of select="ns0:ruolo"/><br /></xsl:for-each>
				</li>
			</ul>
		<h2><a name="datiArtistici">Dati artistici</a></h2>
			<table>
				<tr>
					<th width="40%"><span class="categoria">Dritto</span></th>
					<th width="40%"><span class="categoria">Rovescio</span></th>
					<th width="20%"><span class="categoria">Taglio</span></th></tr>
				<tr>
					<td>
						<xsl:value-of select="ns0:datiArtistici/ns0:dritto/ns0:descrizione"/><br/>
						<xsl:for-each select="ns0:datiArtistici/ns0:dritto/ns0:legenda">
							<div class="legenda">
								<xsl:if test="ns0:testo != ''">
									<span class="categoria">Legenda:</span> <span class="testoLegenda"><xsl:value-of select="ns0:testo"/></span><br/>
								</xsl:if>
								<xsl:if test="ns0:scioglimento != ''">
									<span class="categoria">Scioglimento:</span> <span class="scioglimentoLegenda"><xsl:value-of select="ns0:scioglimento"/><br/></span>
								</xsl:if>
							</div>
						</xsl:for-each>
					</td>
					<td>
						<xsl:value-of select="ns0:datiArtistici/ns0:rovescio/ns0:descrizione"/><br/>
						<xsl:for-each select="ns0:datiArtistici/ns0:rovescio/ns0:legenda">
							<div class="legenda">
								<xsl:if test="ns0:testo != ''">
									<span class="categoria">Legenda:</span> <span class="testoLegenda"><xsl:value-of select="ns0:testo"/></span><br/>
								</xsl:if>
								<xsl:if test="ns0:scioglimento != ''">
									<span class="categoria">Scioglimento:</span> <span class="scioglimentoLegenda"><xsl:value-of select="ns0:scioglimento"/><br/></span>
								</xsl:if>
							</div>
						</xsl:for-each>
					</td>
					<td>
						<xsl:value-of select="ns0:datiArtistici/ns0:taglio/ns0:descrizione"/><br/>
						<xsl:for-each select="ns0:datiArtistici/ns0:taglio/ns0:legenda">
							<div class="legenda">
								<xsl:if test="ns0:testo != ''">
									<span class="categoria">Legenda:</span> <span class="testoLegenda"><xsl:value-of select="ns0:testo"/></span><br/>
								</xsl:if>
								<xsl:if test="ns0:scioglimento != ''">
									<span class="categoria">Scioglimento:</span> <span class="scioglimentoLegenda"><xsl:value-of select="ns0:scioglimento"/><br/></span>
								</xsl:if>
							</div>
						</xsl:for-each>
					</td>
				</tr>
				<tr>
					<td><xsl:if test="$imgDritto != ''"><a href="{$directoryMoneta}/{$imgDritto}"><img src="{$directoryMoneta}/{$imgDritto}" width="200px" alt="imgDritto" /></a></xsl:if></td>
					<td><xsl:if test="$imgRovescio != ''"><a href="{$directoryMoneta}/{$imgRovescio}"><img src="{$directoryMoneta}/{$imgRovescio}" width="200px" alt="imgRovescio" /></a></xsl:if></td>
					<td><xsl:if test="$imgTaglio != ''"><a href="{$directoryMoneta}/{$imgTaglio}"><img src="{$directoryMoneta}/{$imgTaglio}" width="200px" alt="imgTaglio" /></a></xsl:if></td>
				</tr>
			</table>

		<h2><a name="datiFisici">Dati fisici</a></h2>
			<ul>
				<li><span class="categoria">Forma: </span> <xsl:value-of select="ns0:datiFisici/ns0:forma"/></li>
				
				<li><span class="categoria">Diametro: </span> <xsl:value-of select="ns0:datiFisici/ns0:diametro/ns0:valore"/><xsl:text> </xsl:text><xsl:value-of select="ns0:datiFisici/ns0:diametro/ns0:unita"/></li>
				<li><span class="categoria">Metallo: </span> <xsl:value-of select="ns0:datiFisici/ns0:metallo"/></li>
				<li><span class="categoria">Peso: </span> <xsl:value-of select="ns0:datiFisici/ns0:peso/ns0:valore"/><xsl:text> </xsl:text><xsl:value-of select="ns0:datiFisici/ns0:peso/ns0:unita"/></li>

			</ul>

		<h2><a name="datiCollezione">Dati collezione</a></h2>
			<span class="categoria">Acquisto: </span> <xsl:value-of select="ns0:datiAcquisto/ns0:luogo" /><xsl:text> </xsl:text>
			<xsl:value-of select="ns0:datiAcquisto/ns0:data"/><xsl:text> </xsl:text>
			<xsl:value-of select="ns0:datiAcquisto/ns0:prezzo/ns0:valore"/><xsl:text> </xsl:text>
			<xsl:value-of select="ns0:datiAcquisto/ns0:prezzo/ns0:unita"/>

		<h2><a name="datiLetteratura">Letteratura</a></h2>
			<ul>
				<xsl:for-each select="ns0:letteratura/ns0:libro">
					<li><xsl:value-of select="ns0:sigla"/><xsl:text> </xsl:text><xsl:value-of select="ns0:numero"/></li>
				</xsl:for-each>
			</ul>

		<h2><a name="note">Note</a></h2>
			<ul>
				<xsl:for-each select="ns0:note/ns0:nota">
					<li><xsl:apply-templates/></li>
				</xsl:for-each>
			</ul>
		<h2><a name="Documenti">Documenti</a></h2>
			<ul>
				<xsl:for-each select="ns0:itemAddizionali/ns0:documento">
					<xsl:variable name="curFile" select="ns0:filename"/>
					<xsl:variable name="curUrl" select="ns0:url"/>
					<li><a href="{$directoryMoneta}/{$curUrl}/{$curFile}"><xsl:value-of select="ns0:filename"/></a><xsl:text> </xsl:text><xsl:value-of select="ns0:descrizione"/></li>
				</xsl:for-each>
			</ul>
	</div> <!-- fine collezione_content' -->
</body>
</html>

</xsl:template>

</xsl:stylesheet>