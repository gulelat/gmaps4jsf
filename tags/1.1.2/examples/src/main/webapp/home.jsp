<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
    <HEAD> 
    <title>Welcome to GMaps4JSF</title>  
    </HEAD>
	<body>
	<%@include file="../templates/header.jspf" %>           
        <f:view> 
	        <h:form>	                
	                <h:outputText value="Simple" />
	                <h:panelGrid style="padding-left:25px">
	                    <h:outputLink value="pages/simple.jsf">
	                        <f:verbatim>A simple map with no other components</f:verbatim>
	                    </h:outputLink>
	                </h:panelGrid>      
	                
	                <h:outputText value="Auto-Arrange Map" />
	                <h:panelGrid style="padding-left:25px">
	                    <h:outputLink value="pages/autoReshape.jsf">
	                        <f:verbatim>A map that is reshaped according to its markers' positions</f:verbatim>
	                    </h:outputLink>
	                </h:panelGrid>   	                   
	                
	                <h:outputText value="Markers and Information Windows" />
	                <h:panelGrid style="padding-left:25px">                
	                    <h:outputLink value="pages/mapWithMarkersAndInfoWindows.jsf">
	                    	<f:verbatim>A map with both markers and Information windows</f:verbatim>
	                    </h:outputLink>                     
	                </h:panelGrid>	                
	                
	                <h:outputText value="Draggable Markers" />
	                <h:panelGrid style="padding-left:25px">                
	                    <h:outputLink value="pages/draggableMarkers.jsf">
	                    	<f:verbatim>Map with draggable markers</f:verbatim>
	                    </h:outputLink>                     
	                </h:panelGrid>
	                
	                <h:outputText value="Addressable Markers" />
	                <h:panelGrid style="padding-left:25px">                
	                    <h:outputLink value="pages/addressableMarkers.jsf">
	                    	<f:verbatim>Map with addressable markers</f:verbatim>
	                    </h:outputLink>                     
	                </h:panelGrid>	                
	                
	                <h:outputText value="Custom Markers" />
	                <h:panelGrid style="padding-left:25px">                
	                    <h:outputLink value="pages/customMarkers.jsf">
	                    	<f:verbatim>Map with custom draggable markers</f:verbatim>
	                    </h:outputLink>                     
	                </h:panelGrid>	                
	                
	                <h:outputText value="Controls" />
	                <h:panelGrid style="padding-left:25px">                
	                    <h:outputLink value="pages/mapWithControls.jsf">
	                    	<f:verbatim>Adding controls to your map</f:verbatim>
	                    </h:outputLink>                     
	                </h:panelGrid>	                
	                
	                <h:outputText value="Client-side EventListeners" />
	                <h:panelGrid style="padding-left:25px">                
	                    <h:outputLink value="pages/mapEvents.jsf">
	                    	<f:verbatim>Listening to client-side events on your map and markers</f:verbatim>
	                    </h:outputLink>                     
	                </h:panelGrid>	  	  
	                
	                <h:outputText value="Server-side EventListeners" />
	                <h:panelGrid style="padding-left:25px">                
	                    <h:outputLink value="pages/markersServerSideEvents.jsf">
	                    	<f:verbatim>Listening to server-side events on markers</f:verbatim>
	                    </h:outputLink>                     
	                </h:panelGrid>	  	   	                              
	                
	                <h:outputText value="Geocoding" />
	                <h:panelGrid style="padding-left:25px">          
	                    <h:outputLink value="pages/getMyLocation.jsf">
	                    	<f:verbatim>An example that demonstrates the usage of the address attribute of the map</f:verbatim>
	                    </h:outputLink>                     
	                </h:panelGrid>      
	                
	                <h:outputText value="Polylines" />
	                <h:panelGrid style="padding-left:25px">          
	                    <h:outputLink value="pages/mapSimplePolyline.jsf">
	                    	<f:verbatim>Draw a simple polyline on your map</f:verbatim>
	                    </h:outputLink>     
	                    <h:outputLink value="pages/mapGeodesicPolyline.jsf">
	                    	<f:verbatim>Draw a geodesic polyline on your map</f:verbatim>
	                    </h:outputLink>  	
	                    <h:outputLink value="pages/mapMixedPolylines.jsf">
	                    	<f:verbatim>Draw mixed polylines on your map</f:verbatim>
	                    </h:outputLink>  	                                       
	                </h:panelGrid>    	 
	                
	                <h:outputText value="Polygons" />
	                <h:panelGrid style="padding-left:25px">          
	                    <h:outputLink value="pages/mapPolygon.jsf">
	                    	<f:verbatim>Draw a polygon on your map</f:verbatim>
	                    </h:outputLink> 	
	                    <h:outputLink value="pages/mapPolygons.jsf">
	                    	<f:verbatim>Draw many polygons on your map</f:verbatim>
	                    </h:outputLink> 	                                        
	                </h:panelGrid>    
	                
	                <h:outputText value="Polygons + Polylines + Events" />
	                <h:panelGrid style="padding-left:25px">          
	                    <h:outputLink value="pages/mixedGraphics.jsf">
	                    	<f:verbatim>Draw polygons, polylines on your map and listen to their events</f:verbatim>
	                    </h:outputLink> 	                                        
	                </h:panelGrid>    	                
	                
	                <h:outputText value="GroundOverlay" />
	                <h:panelGrid style="padding-left:25px">          
	                    <h:outputLink value="pages/groundoverlay.jsf">
	                    	<f:verbatim>Place GMaps4JSF logo on the earth</f:verbatim>
	                    </h:outputLink> 	                                        
	                </h:panelGrid>   	                		                               	                   	
	                
	                <h:outputText value="JavaScript Customization" />
	                <h:panelGrid style="padding-left:25px">                
	                    <h:outputLink value="pages/jsVariableExample.jsf">
	                    	<f:verbatim>An example that demonstrates the ability to use map and markers using JavaScript using the (jsVariable) attribute</f:verbatim>
	                    </h:outputLink>                     
	                </h:panelGrid>  	
	                
	                <h:outputText value="StreetViewPanorama" />
	                <h:panelGrid style="padding-left:25px">          
	                    <h:outputLink value="pages/streetViewPanorama.jsf">
	                    	<f:verbatim>An example that shows the street view panorama using latitude and longitude</f:verbatim>
	                    </h:outputLink>
	                    <h:outputLink value="pages/streetViewPanoramaUsingAddress.jsf">
	                    	<f:verbatim>An example that shows the street view panorama using address</f:verbatim>
	                    </h:outputLink>	                    
	                    <h:outputLink value="pages/streetViewPanoramaEvents.jsf">
	                    	<f:verbatim>An event listener example that listens to the street view panorama</f:verbatim>
	                    </h:outputLink> 	                    
	                    <h:outputLink value="pages/showMyStreet.jsf">
	                    	<f:verbatim>An example that uses the street view panorama to show the map streets</f:verbatim>
	                    </h:outputLink> 	                     	                  	                                                  
	                </h:panelGrid>   	 	                                
	                
	                <h:outputText value="Web 2.0 Mashup Demo" />
	                <h:panelGrid style="padding-left:25px">                
	                    <h:outputLink value="pages/mashups.jsf">
	                    	<f:verbatim>A Web 2.0 Mashup Demo that uses both Apache Tomahawk + Ajax4JSF + GMaps4JSF</f:verbatim>
	                    </h:outputLink>                     
	                </h:panelGrid>   	                                                             
	
			</h:form>
        </f:view>      
    </body>
</HTML>  
