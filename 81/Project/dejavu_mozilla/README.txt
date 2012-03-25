README.txt -- July 2010
-----------------------------------------------------------------------------

[I. Contents]

This archive contains the output of the DejaVu static bugfinding tool on
two open-source Mozilla projects: the Firefox web browser and the 
Thunderbird email client.

DejaVu is a system for locating potential defects in C and C++ source code.

It is described in a technical research paper, "Scalable and Systematic
Detection of Buggy Inconsistencies in Source Code" (Gabel et al.) published
at the 2010 OOPSLA/SPLASH conference sponsored by the ACM.

This archive contains the following files:

 dejavu_mozilla.txt

         The raw output of DejaVu on the Mozilla Firefox and Thunderbird
         code bases.  This file contains bug reports consisting of pairs
         of source code fragments coupled with potentially buggy 
         inconsistencies.

         The scanned code base is a Mercurial snapshot from July 2010.  

         DejaVu scanned /only/ the code that was actively compiled by a 
         standard build of Firefox and Thunderbird on the Windows 
         platform.

 dejavu_mozilla_annotations.txt

         Our classification of the bug reports into five categories:

           BUG       Likely bug
           SMELL     Code smell
           STYLE     Style smell
           CHECK     Possible bug; requires domain knowledge to verify
           FALSE     Likely false report

         Further detail can be found in the technical paper.

README.txt

         This file.


[II. Legal]

DejaVu is currently a proprietary system and its copyright is held by
Microsoft Corporation.  These experiments were performed under a temporary
license by researchers not currently employed by Microsoft Corporation.

All source code included in the bug reports retains its original copyright
by the Mozilla Foundation.  Source code snippets are included for clarity 
and convenience only.

