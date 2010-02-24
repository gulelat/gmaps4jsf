<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
	<f:view>
	
    <HEAD> 	    
    <title>Welcome to GMaps4JSF</title>    
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />    	
    <m:resources key="ABQIAAAAD6cDv-a0AnpzXA4gj6utCRTUlDOzA1Sd8h2eDxLDJEZUtkHZ_xQlPmNUQ-At6YLqCd29cGkwT8i95A"/>
	<style>
		html, body { height: 100% }
	</style>
    </HEAD>
	
	<body onunload="GUnload()">
    	<h:form id="form">
		  	<div>Control your marker position from the JSF EL</div>    	
    		<m:map width="90%" height="90%" latitude="24" longitude="15" jsVariable="map1" type="G_NORMAL_MAP" zoom="2">
    			<m:marker latitude="#{place1.latitude}" longitude="#{place1.longitude}" jsVariable="marker1" draggable="true" submitOnValueChange="true"> 				
    			</m:marker> 				
    		</m:map>
    		
    		<h:panelGrid columns="2">
    			<h:outputText value="Current Marker Latitude: "/>    		
    			<h:inputText value="#{place1.latitude}"/>
    			
    			<h:outputText value="Current Marker Longitude: "/>    		
    			<h:inputText value="#{place1.longitude}"/>    			
    		</h:panelGrid>
            <h:commandButton value="Set the marker place!!!" action="#{addressBean.doSearch}"/>  <br>                                      		

   		 		
    	</h:form>
	<%@include file="../templates/footer.jspf" %>   	
    </body>
	</f:view>    
</HTML>  
