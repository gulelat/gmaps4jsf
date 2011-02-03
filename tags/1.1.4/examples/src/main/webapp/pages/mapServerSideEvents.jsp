<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <f:view>
        <head>
        	<%@include file="../templates/internalHeader.jspf" %>               
        </head>  
        <body onunload="GUnload()">
			<f:subview id="subview">
				<h:form id="form">
					<div>Map Server side events - click the map to create markers dynamically (Non-Ajax)</div>
					<m:map width="90%" height="90%" latitude="24" longitude="15" 
						   jsVariable="map1" zoom="2" 
						   actionListener="#{map.addMarkerHere}" 
						   submitOnClick="true"/>
				</h:form>
			</f:subview>
            <%@include file="../templates/footer.jspf" %>
        </body>
    </f:view>
</html> 