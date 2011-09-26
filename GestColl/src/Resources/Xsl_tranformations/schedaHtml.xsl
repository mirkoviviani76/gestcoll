<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fn="http://www.w3.org/2006/xpath-functions" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:exslt="http://exslt.org/common" xmlns:dyn="http://exslt.org/dynamic">
<!-- versione 1.4 -->

<xsl:output method="xml" omit-xml-declaration="yes" doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd" doctype-public="-//W3C//DTD XHTML 1.0 Strict//EN" indent="yes"/>

<!-- TEMPLATE PRINCIPALE -->

<xsl:template match="/">
<xsl:variable name="imgDritto">  
<xsl:value-of select="/moneta/datiArtistici/dritto/fileImmagine"/>  
</xsl:variable>
<xsl:variable name="imgRovescio">  
<xsl:value-of select="/moneta/datiArtistici/rovescio/fileImmagine"/>  
</xsl:variable>
<xsl:variable name="imgTaglio">  
<xsl:value-of select="/moneta/datiArtistici/taglio/fileImmagine"/>  
</xsl:variable>

<xsl:variable name="directoryMoneta">../../Monete/<xsl:value-of select="/moneta/@id"/></xsl:variable>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><xsl:value-of select="/moneta/@id"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="report.css" />
</head>

<body>
	<div id='collezione_header' class='collezione_header'><h1>
		<xsl:value-of select="/moneta/paese"/><xsl:text> </xsl:text><xsl:value-of select="/moneta/nominale/valore"/><xsl:text> </xsl:text><xsl:value-of select="/moneta/nominale/valuta"/> (<xsl:value-of select="/moneta/anno"/>)<br/>
		<xsl:value-of select="/moneta/@id"/></h1>
	</div> <!-- fine collezione_header -->
 
	<div id='collezione_content' class='collezione_content'> 
		<h2><a name="datiMonetali">Dati monetali</a></h2>
			<ul>
				<li><span class="categoria">Paese:</span>
					<ul>
						<xsl:for-each select="/moneta/paese"><li><xsl:apply-templates/></li></xsl:for-each>
					</ul>
				</li>
				<li><span class="categoria">Autorita:</span>
					<ul>
						<xsl:for-each select="/moneta/autorita/nome"><li><xsl:apply-templates/></li></xsl:for-each>
					</ul>
				</li>
				<li><span class="categoria">Zecca:</span>
					<xsl:for-each select="/moneta/zecca">Nome: <xsl:value-of select="nome"/> Segno: <xsl:value-of select="segno"/><br /></xsl:for-each>
				</li>
				<li><span class="categoria">Zecchieri:</span>
					<xsl:for-each select="/moneta/zecchieri/zecchiere">Nome: <xsl:value-of select="nome"/> Segno: <xsl:value-of select="segno"/> Ruolo: <xsl:value-of select="ruolo"/><br /></xsl:for-each>
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
						<xsl:value-of select="/moneta/datiArtistici/dritto/descrizione"/><br/>
						<xsl:for-each select="/moneta/datiArtistici/dritto/legenda">
							<div class="legenda">
								<xsl:if test="testo != ''">
									<span class="categoria">Legenda:</span> <span class="testoLegenda"><xsl:value-of select="testo"/></span><br/>
								</xsl:if>
								<xsl:if test="scioglimento != ''">
									<span class="categoria">Scioglimento:</span> <span class="scioglimentoLegenda"><xsl:value-of select="scioglimento"/><br/></span>
								</xsl:if>
							</div>
						</xsl:for-each>
					</td>
					<td>
						<xsl:value-of select="/moneta/datiArtistici/rovescio/descrizione"/><br/>
						<xsl:for-each select="/moneta/datiArtistici/rovescio/legenda">
							<div class="legenda">
								<xsl:if test="testo != ''">
									<span class="categoria">Legenda:</span> <span class="testoLegenda"><xsl:value-of select="testo"/></span><br/>
								</xsl:if>
								<xsl:if test="scioglimento != ''">
									<span class="categoria">Scioglimento:</span> <span class="scioglimentoLegenda"><xsl:value-of select="scioglimento"/><br/></span>
								</xsl:if>
							</div>
						</xsl:for-each>
					</td>
					<td>
						<xsl:value-of select="/moneta/datiArtistici/taglio/descrizione"/><br/>
						<xsl:for-each select="/moneta/datiArtistici/taglio/legenda">
							<div class="legenda">
								<xsl:if test="testo != ''">
									<span class="categoria">Legenda:</span> <span class="testoLegenda"><xsl:value-of select="testo"/></span><br/>
								</xsl:if>
								<xsl:if test="scioglimento != ''">
									<span class="categoria">Scioglimento:</span> <span class="scioglimentoLegenda"><xsl:value-of select="scioglimento"/><br/></span>
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
				<li><span class="categoria">Forma: </span> <xsl:value-of select="/moneta/datiFisici/forma"/></li>
				<li><span class="categoria">Diametro: </span> <xsl:value-of select="/moneta/datiFisici/diametro/valore"/><xsl:text> </xsl:text><xsl:value-of select="/moneta/datiFisici/diametro/unita"/></li>
				<li><span class="categoria">Metallo: </span> <xsl:value-of select="/moneta/datiFisici/metallo"/></li>
				<li><span class="categoria">Peso: </span> <xsl:value-of select="/moneta/datiFisici/peso/valore"/><xsl:text> </xsl:text><xsl:value-of select="/moneta/datiFisici/peso/unita"/></li>
			</ul>

		<h2><a name="datiCollezione">Dati collezione</a></h2>
			<span class="categoria">Acquisto: </span> <xsl:value-of select="/moneta/datiAcquisto/luogo" /><xsl:text> </xsl:text>
			<xsl:value-of select="/moneta/datiAcquisto/data"/><xsl:text> </xsl:text>
			<xsl:value-of select="/moneta/datiAcquisto/prezzo/valore"/><xsl:text> </xsl:text>
			<xsl:value-of select="/moneta/datiAcquisto/prezzo/unita"/>

		<h2><a name="datiLetteratura">Letteratura</a></h2>
			<ul>
				<xsl:for-each select="/moneta/letteratura/libro">
					<li><xsl:value-of select="sigla"/><xsl:text> </xsl:text><xsl:value-of select="numero"/></li>
				</xsl:for-each>
			</ul>

		<h2><a name="note">Note</a></h2>
			<ul>
				<xsl:for-each select="/moneta/note/nota">
					<li><xsl:apply-templates/></li>
				</xsl:for-each>
			</ul>
		<h2><a name="Documenti">Documenti</a></h2>
			<ul>
				<xsl:for-each select="/moneta/itemAddizionali/documento">
					<xsl:variable name="curFile" select="filename"/>
					<xsl:variable name="curUrl" select="url"/>
					<li><a href="{$directoryMoneta}/{$curUrl}/{$curFile}"><xsl:value-of select="filename"/></a><xsl:text> </xsl:text><xsl:value-of select="descrizione"/></li>
				</xsl:for-each>
			</ul>
	</div> <!-- fine collezione_content' -->
</body>
</html>

</xsl:template>
</xsl:stylesheet>
