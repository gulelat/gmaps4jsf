<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
    <HEAD> 
    <title>Welcome to GMaps4JSF</title> 
    <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAxrVS1QxlpJHXxQ2Vxg2bJBQdkFK-tWRbPPQS4ACM1pq_e-PltxQXeyH20wQuqDaQ_6EM5UeGGVpnIw"
      type="text/javascript"></script>
  
    </HEAD>
	
	<body onunload="GUnload()">
	<f:view>
    	<h:form id="form">
		  	<div>Drag the map and click the marker!!!</div>    	
    		<m:map width="90%" height="90%" latitude="24" longitude="15" jsVariable="map1" zoom="2">
    			<m:marker latitude="30.01" longitude="31.14" jsVariable="marker1">
					<m:gEventListener eventName="click" jsFunction="marker1ClickHandler"/>    				
    			</m:marker>
    			<m:marker latitude="39" longitude="-101" jsVariable="marker2">
					<m:gEventListener eventName="click" jsFunction="marker2ClickHandler"/>    				
    			</m:marker>    			
				<m:gEventListener eventName="moveend" jsFunction="mapMoveEndHandler"/>		
    		</m:map>

		    <script>
		   	function mapMoveEndHandler() {
			   var center = map1.getCenter();
			   document.getElementById("message").innerHTML = "Center of map is " + center.toString();    	
		   	}
		   	function marker1ClickHandler() {
		   		alert("You clicked on Egypt marker");  	
		   	}	
		   	function marker2ClickHandler() {
		   		alert("You clicked on US marker");  	
		   	}		   	
		   	function mapClickHandler(overlay,  latlng,  overlaylatlng) {
				  alert("You click on (lat, lng): " + latlng);
		   	}		   		   	
		    </script>    		

		    <div id="message"/>    		
    	</h:form>
	</f:view>
	<%@include file="../templates/footer.jspf" %>   	
    </body>
</HTML>  
