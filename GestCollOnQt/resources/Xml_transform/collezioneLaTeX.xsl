<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns="http://gestColl/coins" 
xmlns:cc="http://gestColl/coins">
<!-- versione 1.3 -->

<xsl:strip-space elements="*" />

<xsl:output method="text" indent="yes" />
<xsl:param name="dirImg"></xsl:param>
<xsl:param name="hyperref"></xsl:param>

<xsl:template match="/cc:monete" xmlns:cc="http://gestColl/coins">
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Template Collezione.tex versione 1.0
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\documentclass[11pt,a4paper]{book}
\usepackage{graphicx}
\usepackage[italian]{babel}
\usepackage{bbding}
\usepackage{pifont}
\usepackage{yfonts}
\usepackage[compact]{titlesec}
\usepackage[big]{layaureo}
\usepackage{aurical}
\usepackage{longtable}
<xsl:if test="$hyperref = 'TRUE'">\usepackage{hyperref}</xsl:if>
\usepackage{xltxtra}
\usepackage[perpage]{footmisc}
\usepackage[italian]{isodate}
%\setmainfont{Free Serif}
%\usepackage[utf8]{inputenc}
%\usepackage{lettrine}
%\usepackage{url}
\usepackage{eurosym}
%\usepackage{fancyhdr}
%\usepackage{chappg}
%\usepackage{scalebar}
%\usepackage{textcomp}
%\usepackage{slashbox}
%\usepackage{tocbibind}
%\usepackage{array}
%\usepackage{multirow}
%\usepackage{natbib}
%\usepackage{pgf}
%\usepackage{tikz}
%\usepackage{nicefrac}
%\usepackage{calc}
%\pagestyle{empty}
\usepackage{ifthen}

\begin{document}

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%\newcommand{\campo}[2]{\textbf{#1:} #2\\}
\newcommand{\legenda}[2]{#1  \ifthenelse{\equal{#2}{}}{}{\footnote{#2}}}
\newcommand{\zecca}[2]{#1 \ding{239} #2} %#1=nome #3=simbolo/sigle

\newcommand{\zecchiere}[3]{#1 \ding{239} #2 #3} %#1=nome #2=simbolo/sigle #3 ruolo
%\renewcommand{\mtctitle}{Contenuto}
%\renewcommand{\mlftitle}{Figure}
\renewcommand{\dot}{$\,\cdot\,$\linebreak[2]}
\newcommand{\parte}[1]{\part*{#1}\setcounter{chapter}{0}}
\newcommand{\moneta}[2]{\chapter*{#1\\#2} \addcontentsline{toc}{chapter}{#1 -- #2}\vspace{-20mm}}
\newcommand{\sezione}[1]{\section*{\Fontlukas \underline{#1}}}
\newcommand{\subsezione}[1]{\subsection*{\Fontlukas #1}}
\newcommand{\HRule}{\rule{\linewidth}{0.5mm}}
\newcommand{\posizioni}{\chapter*{Posizioni} \addcontentsline{toc}{chapter}{Posizioni}}

\makeatletter
\@addtoreset{footnote}{section}
\makeatother

\titlespacing{\chapter}{0pt}{-0.5in}{1in}
\titleformat{\chapter}[hang]{\normalfont\Large\filcenter\Fontlukas\bfseries}
{\LARGE \thechapter.}{1pc}{ \vspace{1pc} \Huge}


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\pagestyle{empty}

\begin{titlepage}
\vspace*{5cm}
\begin{center}
% Title
{\LARGE Mirko Viviani} \\[0.5cm]
\HRule
\\[0.4cm]
{ \Huge \bfseries Collezione Numismatica \\[0.3cm] il Sacro Romano Impero 1600-1806}\\[0.4cm]
{ \large \bfseries dal 2 luglio 2005}\\[0.4cm]
\HRule 
\vfill
\includegraphics[width=8cm]{SRI.png}

% Bottom of the page

\end{center}

\end{titlepage}

<xsl:if test="$hyperref = 'TRUE'">
\tableofcontents
</xsl:if>

<xsl:call-template name="posizioni" />
<xsl:call-template name="monete" />

\end{document}

</xsl:template>

<xsl:template name="monete" xmlns:cc="http://gestColl/coins">


<xsl:apply-templates select="cc:moneta" />
</xsl:template>


<xsl:template match="/cc:monete/cc:info" />

<!-- posizione -->
<xsl:template name="posizioni" xmlns:cc="http://gestColl/coins">
\posizioni
\begin{longtable}{| r l || c c c c | }
\hline
\multicolumn{2}{| c ||}{\textbf{ID}} $AMPERSAND$ \textbf{Box} $AMPERSAND$ \textbf{Vass.} $AMPERSAND$ \textbf{Riga} $AMPERSAND$ \textbf{Col.}\\
\hline
\hline
\endhead
<xsl:for-each select="cc:moneta">
<xsl:value-of select="@id"/> $AMPERSAND$ <xsl:value-of select="cc:paese"/><xsl:text>&#x20;</xsl:text><xsl:value-of select="cc:nominale/cc:valore"/>~<xsl:value-of select="cc:nominale/cc:valuta"/>\dotfill $AMPERSAND$ <xsl:value-of select="cc:posizione/cc:contenitore"/> $AMPERSAND$ <xsl:value-of select="cc:posizione/cc:vassoio"/> $AMPERSAND$ <xsl:value-of select="cc:posizione/cc:riga"/> $AMPERSAND$ <xsl:value-of select="cc:posizione/cc:colonna"/> \\
</xsl:for-each>
\hline
\caption{Posizioni} \\
\end{longtable}

\cleardoublepage
</xsl:template>


<xsl:template match="/cc:monete/cc:moneta" xmlns:cc="http://gestColl/coins">
<xsl:variable name="imgDritto">  
<xsl:value-of select="cc:datiArtistici/cc:dritto/cc:fileImmagine"/>  
</xsl:variable>
<xsl:variable name="imgRovescio">  
<xsl:value-of select="cc:datiArtistici/cc:rovescio/cc:fileImmagine"/>  
</xsl:variable>
<xsl:variable name="imgTaglio">  
<xsl:value-of select="cc:datiArtistici/cc:taglio/cc:fileImmagine"/>  
</xsl:variable>

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

\moneta{<xsl:value-of select="cc:paese"/>}{<xsl:value-of select="cc:nominale/cc:valore"/>~<xsl:value-of select="cc:nominale/cc:valuta"/>
        <xsl:text>&#x20;</xsl:text>(<xsl:value-of select="cc:anno"/>)}
\thispagestyle{empty}
\sezione{Descrizione}
\begin{description}
	\item[ID:]<xsl:value-of select="@id"/>
	\item[Autorita:] <xsl:for-each select="cc:autorita/cc:nome"><xsl:value-of select="."></xsl:value-of><xsl:if test="not(position() = last())">; </xsl:if> </xsl:for-each>
	<xsl:for-each select="cc:zecca">
	\item[Zecca:] \zecca{<xsl:value-of select="cc:nome"/>}{<xsl:value-of select="cc:segno"/>}
	</xsl:for-each>
	<xsl:variable name="conta1" select="count(cc:zecchieri/cc:zecchiere)"/>
	<xsl:if test="$conta1 != 0"><xsl:for-each select="cc:zecchieri/cc:zecchiere">\item[Zecchieri:] \zecchiere{<xsl:value-of select="cc:nome"/>}{<xsl:value-of select="cc:segno"/>}{<xsl:value-of select="cc:ruolo"/>}
	</xsl:for-each></xsl:if>

	\item[Dritto:]<xsl:value-of select="cc:datiArtistici/cc:dritto/cc:descrizione"/> 
	<xsl:variable name="conta2" select="count(cc:datiArtistici/cc:dritto/cc:legenda)"/>
	<xsl:if test="$conta2 != 0">
	\begin{description}
		<xsl:for-each select="cc:datiArtistici/cc:dritto/cc:legenda">\item[Legenda:]\legenda{<xsl:value-of select="cc:testo"/>}{<xsl:value-of select="cc:scioglimento"/>}</xsl:for-each>
	\end{description}</xsl:if>

	\item[Rovescio:]<xsl:value-of select="cc:datiArtistici/cc:rovescio/cc:descrizione"/> 
	<xsl:variable name="conta3" select="count(cc:datiArtistici/cc:rovescio/cc:legenda)"/>
	<xsl:if test="$conta3 != 0">
	\begin{description}
		<xsl:for-each select="cc:datiArtistici/cc:rovescio/cc:legenda">\item[Legenda:]\legenda{<xsl:value-of select="cc:testo"/>}{<xsl:value-of select="cc:scioglimento"/>}</xsl:for-each>
	\end{description}</xsl:if>

	\item[Taglio:]<xsl:value-of select="cc:datiArtistici/cc:taglio/cc:descrizione"/> 
	<xsl:variable name="conta4" select="count(cc:datiArtistici/cc:taglio/cc:legenda)"/>
	<xsl:if test="$conta4 != 0">
	\begin{description}
		<xsl:for-each select="cc:datiArtistici/cc:taglio/cc:legenda">\item[Legenda:]\legenda{<xsl:value-of select="cc:testo"/>}{<xsl:value-of select="cc:scioglimento"/>}</xsl:for-each>
	\end{description}</xsl:if>

	\item[Forma:]<xsl:value-of select="cc:datiFisici/cc:forma"/>
	\item[Diametro:]<xsl:value-of select="cc:datiFisici/cc:diametro/cc:valore"/>~<xsl:value-of select="cc:datiFisici/cc:diametro/cc:unita"/>
	\item[Metallo:]<xsl:value-of select="cc:datiFisici/cc:metallo"/>
	\item[Peso:]<xsl:value-of select="cc:datiFisici/cc:peso/cc:valore"/>~<xsl:value-of select="cc:datiFisici/cc:peso/cc:unita"/>
\end{description}

<xsl:variable name="conta5" select="count(cc:letteratura/cc:libro)"/>
<xsl:if test="$conta5 != 0">
\sezione{Letteratura}
\begin{description}
	\item[Letteratura:] 
	<xsl:for-each select="cc:letteratura/cc:libro"><xsl:value-of select="cc:sigla"/><xsl:text>&#x20;</xsl:text><xsl:value-of select="cc:numero"/><xsl:if test="not(position() = last())">; </xsl:if><xsl:text>&#x20;</xsl:text></xsl:for-each>
\end{description}
</xsl:if>

<xsl:variable name="conta6" select="count(cc:note/cc:nota)"/>
<xsl:if test="$conta6 != 0">
\sezione{Note}
\begin{itemize}
<xsl:for-each select="cc:note/cc:nota">
	\item{<xsl:value-of select="."></xsl:value-of>}
</xsl:for-each>
\end{itemize}
</xsl:if>

\sezione{Dati di acquisto}
\begin{description}
	\item[Provenienza:]<xsl:value-of select="cc:datiAcquisto/cc:luogo" />,<xsl:text>&#x20;</xsl:text>\printdate{<xsl:value-of select="cc:datiAcquisto/cc:data"/>},<xsl:text>&#x20;</xsl:text>\EUR{<xsl:value-of select="cc:datiAcquisto/cc:prezzo/cc:valore"/>}
\end{description}

\sezione{Ultima revisione}
\printdate{<xsl:value-of select="substring(cc:revisione,1,10)"></xsl:value-of>}


\newpage
<xsl:if test="$imgDritto != ''">
\begin{figure}[H]
	\centering
	\includegraphics[width=10cm,keepaspectratio=true]{<xsl:value-of select="$dirImg" />/<xsl:value-of select="$imgDritto"/>}
\end{figure}
</xsl:if>
<xsl:if test="$imgRovescio != ''">
\begin{figure}[H]
	\centering
	\includegraphics[width=10cm,keepaspectratio=true]{<xsl:value-of select="$dirImg" />/<xsl:value-of select="$imgRovescio"/>}
\end{figure}
</xsl:if>
<xsl:if test="$imgTaglio != ''">
\begin{figure}[H]
	\centering
	\includegraphics[width=10cm,keepaspectratio=true]{<xsl:value-of select="$dirImg" />/<xsl:value-of select="$imgTaglio"/>}
\end{figure}
</xsl:if>

\cleardoublepage
</xsl:template>



</xsl:stylesheet>


