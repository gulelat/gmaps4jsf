<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE PUBLIC html "-//W3C//DTD Xhtml 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head> 
    <title>Welcome to GMaps4JSF</title> 
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />    	
    <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAxrVS1QxlpJHXxQ2Vxg2bJBQdkFK-tWRbPPQS4ACM1pq_e-PltxQXeyH20wQuqDaQ_6EM5UeGGVpnIw"
      type="text/javascript"></script>
	<style>
		html, body { height: 100% }
	</style>  
    </head>
	
	<body onunload="GUnload()">
	<f:view>
    	<h:form id="form">
		  	<div>Mixed Polylines</div>    	
    		<m:map width="90%" height="90%" latitude="24" longitude="15" zoom="2">
    			<m:polyline lineWidth="10" hexaColor="#ff0000" geodesic="false">
    				<m:point latitude="30.01" longitude="31.14"/>
    				<m:point latitude="-33" longitude="19"/>    				
    				<m:point latitude="39" longitude="-101"/> 			
    			</m:polyline>
    			<m:polyline lineWidth="10" hexaColor="#ff0000" geodesic="true">
    				<m:point latitude="30.01" longitude="31.14"/>
    				<m:point latitude="48" longitude="2"/>    				
    				<m:point latitude="43" longitude="141"/>  	   								
    			</m:polyline>            			
    		</m:map>		
    	</h:form>
	</f:view>
	<%@include file="../templates/footer.jspf" %>   	
    </body>
</html>  
