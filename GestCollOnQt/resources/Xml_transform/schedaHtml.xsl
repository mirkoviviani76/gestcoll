<?xml version="1.0" encoding="UTF-8"?> <!-- Prologo XML-->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns="http://gestColl/coins" 
xmlns:cc="http://gestColl/coins">



<xsl:param name="monetaId"></xsl:param>
<xsl:param name="dirImg"></xsl:param>

<xsl:template match="/cc:monete/cc:info" />
<xsl:template match="cc:monete/cc:moneta">
<xsl:if test="@id = $monetaId">
	<xsl:call-template name="templateMoneta" />
</xsl:if>
</xsl:template>

<xsl:template name="templateMoneta" xmlns:cc="http://gestColl/coins">

<xsl:variable name="imgDritto">  
<xsl:value-of select="cc:datiArtistici/cc:dritto/cc:fileImmagine"/>  
</xsl:variable>
<xsl:variable name="imgRovescio">  
<xsl:value-of select="cc:datiArtistici/cc:rovescio/cc:fileImmagine"/>  
</xsl:variable>
<xsl:variable name="imgTaglio">  
<xsl:value-of select="cc:datiArtistici/cc:taglio/cc:fileImmagine"/>  
</xsl:variable>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><xsl:value-of select="@id"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="report.css" />
</head>

<body>
	<div id='collezione_header' class='collezione_header'><h1>
		<xsl:value-of select="cc:paese"/><xsl:text> </xsl:text><xsl:value-of select="cc:nominale/cc:valore"/><xsl:text> </xsl:text><xsl:value-of select="cc:nominale/cc:valuta"/> (<xsl:value-of select="cc:anno"/>)<br/>
		<xsl:value-of select="@id"/></h1>
	</div> <!-- fine collezione_header -->
 
	<div id='collezione_content' class='collezione_content'> 
		<h2><a name="datiMonetali">Dati monetali</a></h2>
			<ul>
				<li><span class="categoria">Paese:</span>
					<ul>
						<xsl:for-each select="cc:paese"><li><xsl:apply-templates/></li></xsl:for-each>
					</ul>
				</li>
				<li><span class="categoria">Autorita:</span>
					<ul>
						<xsl:for-each select="cc:autorita/cc:nome"><li><xsl:apply-templates/></li></xsl:for-each>
					</ul>
				</li>
				<li><span class="categoria">Zecca:</span>
					<xsl:for-each select="cc:zecca">Nome: <xsl:value-of select="cc:nome"/> Segno: <xsl:value-of select="cc:segno"/><br /></xsl:for-each>
				</li>
				<li><span class="categoria">Zecchieri:</span>
					<xsl:for-each select="cc:zecchieri/cc:zecchiere">Nome: <xsl:value-of select="cc:nome"/> Segno: <xsl:value-of select="cc:segno"/> Ruolo: <xsl:value-of select="cc:ruolo"/><br /></xsl:for-each>
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
						<xsl:value-of select="cc:datiArtistici/cc:dritto/cc:descrizione"/><br/>
						<xsl:for-each select="cc:datiArtistici/cc:dritto/cc:legenda">
							<div class="legenda">
								<xsl:if test="cc:testo != ''">
									<span class="categoria">Legenda:</span> <span class="testoLegenda"><xsl:value-of select="cc:testo"/></span><br/>
								</xsl:if>
								<xsl:if test="cc:scioglimento != ''">
									<span class="categoria">Scioglimento:</span> <span class="scioglimentoLegenda"><xsl:value-of select="cc:scioglimento"/><br/></span>
								</xsl:if>
							</div>
						</xsl:for-each>
					</td>
					<td>
						<xsl:value-of select="cc:datiArtistici/cc:rovescio/cc:descrizione"/><br/>
						<xsl:for-each select="cc:datiArtistici/cc:rovescio/cc:legenda">
							<div class="legenda">
								<xsl:if test="cc:testo != ''">
									<span class="categoria">Legenda:</span> <span class="testoLegenda"><xsl:value-of select="cc:testo"/></span><br/>
								</xsl:if>
								<xsl:if test="cc:scioglimento != ''">
									<span class="categoria">Scioglimento:</span> <span class="scioglimentoLegenda"><xsl:value-of select="cc:scioglimento"/><br/></span>
								</xsl:if>
							</div>
						</xsl:for-each>
					</td>
					<td>
						<xsl:value-of select="cc:datiArtistici/cc:taglio/cc:descrizione"/><br/>
						<xsl:for-each select="cc:datiArtistici/cc:taglio/cc:legenda">
							<div class="legenda">
								<xsl:if test="cc:testo != ''">
									<span class="categoria">Legenda:</span> <span class="testoLegenda"><xsl:value-of select="cc:testo"/></span><br/>
								</xsl:if>
								<xsl:if test="cc:scioglimento != ''">
									<span class="categoria">Scioglimento:</span> <span class="scioglimentoLegenda"><xsl:value-of select="cc:scioglimento"/><br/></span>
								</xsl:if>
							</div>
						</xsl:for-each>
					</td>
				</tr>
				<tr>
					<td><xsl:if test="$imgDritto != ''"><a href="file://{$dirImg}/{$imgDritto}"><img src="file://{$dirImg}/{$imgDritto}" width="200px" alt="imgDritto" /></a></xsl:if></td>
					<td><xsl:if test="$imgRovescio != ''"><a href="file://{$dirImg}/{$imgRovescio}"><img src="file://{$dirImg}/{$imgRovescio}" width="200px" alt="imgRovescio" /></a></xsl:if></td>
					<td><xsl:if test="$imgTaglio != ''"><a href="file://{$dirImg}/{$imgTaglio}"><img src="file://{$dirImg}/{$imgTaglio}" width="200px" alt="imgTaglio" /></a></xsl:if></td>
				</tr>
			</table>

		<h2><a name="datiFisici">Dati fisici</a></h2>
			<ul>
				<li><span class="categoria">Forma: </span> <xsl:value-of select="cc:datiFisici/cc:forma"/></li>
				
				<li><span class="categoria">Diametro: </span> <xsl:value-of select="cc:datiFisici/cc:diametro/cc:valore"/><xsl:text> </xsl:text><xsl:value-of select="cc:datiFisici/cc:diametro/cc:unita"/></li>
				<li><span class="categoria">Metallo: </span> <xsl:value-of select="cc:datiFisici/cc:metallo"/></li>
				<li><span class="categoria">Peso: </span> <xsl:value-of select="cc:datiFisici/cc:peso/cc:valore"/><xsl:text> </xsl:text><xsl:value-of select="cc:datiFisici/cc:peso/cc:unita"/></li>

			</ul>

		<h2><a name="datiCollezione">Dati collezione</a></h2>
			<span class="categoria">Acquisto: </span> <xsl:value-of select="cc:datiAcquisto/cc:luogo" /><xsl:text> </xsl:text>
			<xsl:value-of select="cc:datiAcquisto/cc:data"/><xsl:text> </xsl:text>
			<xsl:value-of select="cc:datiAcquisto/cc:prezzo/cc:valore"/><xsl:text> </xsl:text>
			<xsl:value-of select="cc:datiAcquisto/cc:prezzo/cc:unita"/>

		<h2><a name="datiLetteratura">Letteratura</a></h2>
			<ul>
				<xsl:for-each select="cc:letteratura/cc:libro">
					<li><xsl:value-of select="cc:sigla"/><xsl:text> </xsl:text><xsl:value-of select="cc:numero"/></li>
				</xsl:for-each>
			</ul>

		<h2><a name="note">Note</a></h2>
			<ul>
				<xsl:for-each select="cc:note/cc:nota">
					<li><xsl:apply-templates/></li>
				</xsl:for-each>
			</ul>
		<h2><a name="Documenti">Documenti</a></h2>
			<ul>
				<xsl:for-each select="cc:itemAddizionali/cc:documento">
					<xsl:variable name="curFile" select="cc:filename"/>
					<xsl:variable name="curUrl" select="cc:url"/>
					<li><a href="{$dirImg}/{$curUrl}/{$curFile}"><xsl:value-of select="cc:filename"/></a><xsl:text> </xsl:text><xsl:value-of select="cc:descrizione"/></li>
				</xsl:for-each>
			</ul>
	</div> <!-- fine collezione_content' -->
</body>
</html>

</xsl:template>

</xsl:stylesheet>