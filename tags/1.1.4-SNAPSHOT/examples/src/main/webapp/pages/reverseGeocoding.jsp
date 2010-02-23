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
		  	<div>Reverse Geocoding - Get the address after dragging the marker!!!</div>
    		<m:map width="500px" height="400px" latitude="40.730885" longitude="-73.997383" jsVariable="map1" zoom="15">
    			<m:marker id="marker1" latitude="40.727421" longitude="-73.992620" jsVariable="marker1" draggable="true" 
    			          submitOnValueChange="true" valueChangeListener="#{map2.processValueChangeForFirstMarker}">
					<m:icon shadowImageURL="http://www.google.com/mapfiles/shadow50.png" 
					imageURL="http://www.google.com/mapfiles/markerA.png"/>    			          
    			    <m:htmlInformationWindow htmlText="Iam Marker A"/>
    			</m:marker>
    			
				
    			<m:marker id="marker2" latitude="40.735161" longitude="-73.996696" jsVariable="marker2" draggable="true" 
    					  submitOnValueChange="true" valueChangeListener="#{map2.processValueChangeForSecondMarker}">
					<m:icon shadowImageURL="http://www.google.com/mapfiles/shadow50.png" 
					imageURL="http://www.google.com/mapfiles/markerB.png"/>     					  									
    			    <m:htmlInformationWindow htmlText="Iam Marker B"/>			
    			</m:marker>      			
    		</m:map>
    		
    		<h:outputText value="<b>Marker A details:</b><br/>" rendered="#{map2.firstLocationInformation ne null}" escape="false"/> 
    		<h:outputText value="#{map2.firstLocationInformation}<br/>" rendered="#{map2.firstLocationInformation ne null}" escape="false"/>     		
    		<h:outputText value="<b>Marker B details:</b><br/>" rendered="#{map2.secondLocationInformation ne null}" escape="false"/>   		
    		<h:outputText value="#{map2.secondLocationInformation}<br/>" rendered="#{map2.secondLocationInformation ne null}" escape="false"/>   		    		
    	</h:form>
	<%@include file="../templates/footer.jspf" %>   	
    </body>
	</f:view>    
</HTML>  
