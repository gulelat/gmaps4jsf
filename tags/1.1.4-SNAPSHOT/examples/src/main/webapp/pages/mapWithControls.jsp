<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<f:view>
	
    <head> 
    <title>Welcome to GMaps4JSF</title> 
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />    	
    <m:resources key="ABQIAAAAD6cDv-a0AnpzXA4gj6utCRTUlDOzA1Sd8h2eDxLDJEZUtkHZ_xQlPmNUQ-At6YLqCd29cGkwT8i95A"/>
  
    </head>
	
	<body onunload="GUnload()">
    	<h:form id="form">
    		<m:map width="500px" height="500px" latitude="30.01" longitude="31.14">
    			<m:marker latitude="30.01" longitude="31.14"/>
    			<m:htmlInformationWindow latitude="30.01" longitude="31.14" htmlText="Egypt"/>
    			<m:mapControl name="GLargeMapControl" position="G_ANCHOR_BOTTOM_RIGHT"/>
    			<m:mapControl name="GMapTypeControl"/>    			
    		</m:map>
    	</h:form>
	<%@include file="../templates/footer.jspf" %>   	
    </body>
	</f:view>    
</html>  
