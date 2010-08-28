<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j" %>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
	<f:view>
	
    <HEAD> 
    <title>Welcome to GMaps4JSF</title> 
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />    	
    
    <m:resources key="ABQIAAAAxrVS1QxlpJHXxQ2Vxg2bJBS8410bdnzkbe-vDlgNgne89CDDOxTyHld6pWoukVxVKUeDFU_bCukCeg"/>
	<style>
		html, body { height: 100% }
	</style>
    </HEAD>
	
	<body onunload="GUnload()">
    	<h:form id="form">
		  	<div>Resetting marker position after dragging!!!</div>
		  	
		  	<h:panelGroup id="updatablePanel">
				<m:map id="map1" width="500px" height="500px" latitude="24" longitude="15" 
				       jsVariable="map1" zoom="2" partiallyTriggered="true">
				    
					<m:marker id="marker1" 
						  latitude="#{place3.latitude}" longitude="#{place3.longitude}" 
						  jsVariable="marker1" draggable="true" 
						  submitOnValueChange="false" 
						  valueChangeListener="#{map.processValueChangeForMarker}">
							<m:icon shadowImageURL="http://www.google.com/mapfiles/shadow50.png" 
							imageURL="http://www.google.com/mapfiles/markerA.png"/>    			          
					</m:marker>    
								
				</m:map>
				
				<h:outputText value="Marker state (Longitude, Latitude):  (#{place3.longitude}, #{place3.latitude})"/>	    		
				<h:outputText value="<br>" escape="false"/>				
    			</h:panelGroup>
    		
			<a4j:commandButton value="Save Markers Positions" reRender="updatablePanel">
			</a4j:commandButton>
			
			<a4j:commandButton value="Reset Markers Positions" action="#{place3.moveToDefaults}" reRender="updatablePanel" >
			</a4j:commandButton>			
    			
    	</h:form>
	<%@include file="../templates/footer.jspf" %>   	
    </body>
	</f:view>    
</HTML>  
