<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <f:view>
        <head>
        	<%@include file="../templates/internalHeader.jspf" %>               
        </head>  
		<body onunload="GUnload()">
	            <h:form id="form">
	                <div>Multiple markers with event listeners</div>
	    		<m:map width="500px" latitude="10.1" longitude="10.1" height="500px" zoom="2">
	                    <a4j:repeat var="loc" value="#{multiple.locations}">
	                        <m:marker latitude="#{loc.latitude}" longitude="#{loc.longitude}" />
	                    </a4j:repeat>
	    		</m:map>
	            </h:form>
	            <%@include file="../templates/footer.jspf" %>
	     </body>
    </f:view>
</html>