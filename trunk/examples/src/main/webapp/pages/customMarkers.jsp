<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
    <HEAD> 
    <title>Welcome to GMaps4JSF</title> 
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />    	
    <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAxrVS1QxlpJHXxQ2Vxg2bJBQdkFK-tWRbPPQS4ACM1pq_e-PltxQXeyH20wQuqDaQ_6EM5UeGGVpnIw"
      type="text/javascript"></script>
	<style>
		html, body { height: 100% }
	</style>
    </HEAD>
	
	<body onunload="GUnload()">
	<f:view>
    	<h:form id="form">
		  	<div>Custom markers (Click and drag the markers)!!!</div>
    		<m:map width="90%" height="90%" latitude="24" longitude="15" jsVariable="map1" zoom="2">
    			<m:marker latitude="30.01" longitude="31.14" jsVariable="marker1" draggable="true">
					<m:eventListener eventName="dragend" jsFunction="marker1DragHandler"/> 
					<m:icon shadowImageURL="http://www.google.com/mapfiles/shadow50.png" 
					imageURL="http://www.google.com/mapfiles/markerA.png"/> 	
    			    <m:htmlInformationWindow htmlText="Iam Marker A"/>									   				
    			</m:marker>
    			<m:marker latitude="39" longitude="-101" jsVariable="marker2" draggable="true">
					<m:eventListener eventName="dragend" jsFunction="marker2DragHandler"/>    
					<m:icon shadowImageURL="http://www.google.com/mapfiles/shadow50.png" 
					imageURL="http://www.google.com/mapfiles/markerB.png"/> 										
    			    <m:htmlInformationWindow htmlText="Iam Marker B"/>					
    			</m:marker>    				
    			<m:marker latitude="-33" longitude="19" jsVariable="marker3" draggable="true">
					<m:eventListener eventName="dragend" jsFunction="marker3DragHandler"/>    
					<m:icon shadowImageURL="http://www.google.com/mapfiles/shadow50.png" 
					imageURL="http://www.google.com/mapfiles/markerC.png"/> 										
    			    <m:htmlInformationWindow htmlText="Iam Marker C"/>					
    			</m:marker>        			
    		</m:map>

		    <script>
		   	function marker1DragHandler(latlng) {
		   		alert("Current markerA latlng is: " + latlng);  	
		   	}	
		   	function marker2DragHandler(latlng) {
		   		alert("Current markerB latlng is: " + latlng);  	
		   	}		
		   	function marker3DragHandler(latlng) {
		   		alert("Current markerC latlng is: " + latlng);  	
		   	}			   	   		   		   	
		    </script>    		

		    <div id="message"/>    		
    	</h:form>
	</f:view>
	<%@include file="../templates/footer.jspf" %>   	
    </body>
</HTML>  
