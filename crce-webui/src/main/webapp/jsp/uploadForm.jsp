<%@page import="cz.zcu.kiv.crce.metadata.Resource"%>
<%@page import="cz.zcu.kiv.crce.webui.internal.Activator"%>

<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="cs" lang="cs">
  <head>
    <meta name="generator" content="PSPad editor, www.pspad.com" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    
    <link charset="utf-8" href="css/styl.css" rel="stylesheet" type="text/css" />

    <title>Software components storage</title>
  </head>
 
  <body>

  <div id="stranka">
  	
  	<div id="hlavicka">
  		<div class="logo_img"><a href="index.html"><img src="graphic/logo.png" alt="logo" /></a></div>
  		<div class="nazev">Software components storage</div>
      
  		<div class="vyhledavani">
        <form method="get" action="#">
          <input class="text" type="text" name="search" />
          <input class="tlacitko" type="submit" value="search" />
        </form>
      </div>
  	</div>
  	<div class="konec"></div>
    
    <ul id="menu" class="vycisteni">
    	<li><a href="resource?link=buffer">Buffer</a></li>
    	<li><a href="resource?link=store">Store</a></li>
    	<li><a class="aktivni" href="#">Upload</a></li>
        <li><a href="resource?link=plugins">Plugins</a></li>
    </ul>
    
  	<div id="telo">
  	
      <h2>Upload form</h2>
      <form method="post" enctype="multipart/form-data" action="/crce/upload" accept-charset="utf-8">
	      <table>
	        <tr>
	        	<th>jar file:</th>
	        	<td><input class="text" type="file" name="bundle" value="" /></td>
	        	<td class="chyba">${bundleError}</td>
	        </tr>
	        <tr>
	        	<td colspan="2"><input class="tlacitko" type="submit" value="upload file" /></td>
	        </tr>
	      </table>
      </form>
  		
  	</div>
  
  	<div id="paticka">&copy; ASWI project 2011</div>

  </div>
  </body>
</html>

