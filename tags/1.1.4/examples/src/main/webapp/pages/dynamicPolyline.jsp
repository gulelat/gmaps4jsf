<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j" %>

<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <f:view>
        <head>
        	<%@include file="../templates/internalHeader.jspf" %>               
        </head>  
        <body onunload="GUnload()">
            <h:form id="form">
                <div>A polyline that is constructed using points inside repeat tag</div>
                <m:map width="90%" height="90%" latitude="24" longitude="15" zoom="2">
                    <m:polyline lineWidth="4">
                    	<a4j:repeat var="loc" value="#{shapeData.locations}">
							<m:point latitude="#{loc.latitude}" longitude="#{loc.longitude}"/>
                        </a4j:repeat>
                    </m:polyline>
                </m:map>
            </h:form>
            <%@include file="../templates/footer.jspf" %>
        </body>
    </f:view>
</html>