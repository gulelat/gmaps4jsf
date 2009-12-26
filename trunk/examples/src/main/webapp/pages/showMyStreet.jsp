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
	    
	    <m:resources key="ABQIAAAAxrVS1QxlpJHXxQ2Vxg2bJBQdkFK-tWRbPPQS4ACM1pq_e-PltxQXeyH20wQuqDaQ_6EM5UeGGVpnIw"/> 
    </head>
    
	<body onunload="GUnload()">
    	<h:form id="form">
    		
		  	<div>Click the map, and see your street below!!!</div>       		
    		<m:map width="500px" height="300px" 
    			   latitude="41.033386" longitude="-73.781755" 
    			   type="G_NORMAL_MAP" zoom="14" 
    			   addStreetOverlay="true" > 			
				<m:eventListener eventName="click" jsFunction="showStreet" />		
    		</m:map>
    		<br>
    		<m:streetViewPanorama width="500px" height="200px" 
    							  latitude="41.033386" longitude="-73.781755" 
    							  jsVariable="pano1" />
    		
    		<script>
    			function showStreet(overlay, latlng) {
    				pano1.setLocationAndPOV(latlng);
    			}
    		</script>
    		
    	</h:form>
	<%@include file="../templates/footer.jspf" %>   	
    </body>
	</f:view>    
</html>  
