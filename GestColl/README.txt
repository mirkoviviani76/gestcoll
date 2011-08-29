Istruzioni per il setup del progetto

==========================================================================================
WindowBuilder
==========================================================================================
Cfr. http://eclipse.org/windowbuilder/download.php
Aggiungere http://download.eclipse.org/windowbuilder/WB/release/R201106211200/3.7/ agli "update sites"

==========================================================================================
Setup progetto JAXB
==========================================================================================
Background Info

The Eclipse Indigo release offers some new JAXB tooling from the Dali project (which is part of Web Tools Platform). You are now able to create a JAXB Project.

Configuration

A JAXB project can be configured to use the reference or EclipseLink MOXy implementation of JAXB. This can be done via:

   1. Right click your JAXB Project
   2. Select Properties
   3. Select Project Facets
   4. Check the JAXB facet (If you are using Java SE 6 set the JAXB version to 2.1, if you are using Java SE 7 then set the JAXB version to 2.2).
   5. Click the "Further configuration required" or "Further configuration available" links.

Using the JAXB RI

    * Choose Generic JAXB as your platform
    * Choose JRE as your JAXB Implementation Type

Using EclipseLink JAXB (MOXy)

    * Choose EclipseLink JAXB as your platform
    * Choose User Library as your JAXB Implementation
    * Click the Manage Libraries... icon
    * Add a new User Library for EclipseLink (the binary can be obtained from http://www.eclipse.org/eclipselink/downloads/). -

==========================================================================================
Extra
==========================================================================================
- Subversive
- CallGraph ???? (serve GEF graphical engine framework)

