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
    <m:resources key="ABQIAAAAxrVS1QxlpJHXxQ2Vxg2bJBQdkFK-tWRbPPQS4ACM1pq_e-PltxQXeyH20wQuqDaQ_6EM5UeGGVpnIw"/>
	<style>
		html, body { height: 100% }
	</style>
    </HEAD>
	
	<body onunload="GUnload()">
    	<h:form id="form">
		  	<div>Addressable Markers</div>
    		<m:map width="90%" height="90%" latitude="24" longitude="15" jsVariable="map1" zoom="2">
    			
    			<m:marker address="Cairo, Egypt"/>						   				
    			
    			<m:marker address="NY, USA" draggable="true">
					<m:eventListener eventName="dragend" jsFunction="markerDragHandler"/>    										
    			    <m:htmlInformationWindow htmlText="Iam a draggable marker, you can drag me"/>					
    			</m:marker>    				
    			
    			<m:marker address="Vienna, Austria"/>				
    		
    		</m:map>

		    <script>
		   	function markerDragHandler(latlng) {
		   		alert("Current marker latlng is: " + latlng);  	
		   	}			    
		   	</script>    		

    	</h:form>
	<%@include file="../templates/footer.jspf" %>   	
    </body>
	</f:view>    
</HTML>  
