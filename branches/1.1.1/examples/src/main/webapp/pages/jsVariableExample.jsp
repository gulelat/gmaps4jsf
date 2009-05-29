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
    		<m:map width="500px" height="500px" latitude="30.01" longitude="31.14" jsVariable="map1">
    			<m:marker latitude="30.01" longitude="31.14" jsVariable="marker1"/>
    			<m:htmlInformationWindow latitude="30.01" longitude="31.14" htmlText="Egypt"/>
    		</m:map>
    		<br/>
    		<input type="button" value="Zoom In" onclick="zoomIn();">
    		<input type="button" value="Zoom Out" onclick="zoomOut();"> <br/>
    		<input type="button" value="Hide Marker" onclick="hideMarker();">
    		<input type="button" value="Show Marker" onclick="showMarker();"><br/>			
    		<script>
    			function zoomIn() {
    				map1.zoomIn();
    			}
  
    			function zoomOut() {
    				map1.zoomOut();
    			}  
  
    			function hideMarker() {
    				marker1.hide();
    			}  
    			
    			function showMarker() {
    				marker1.show();
    			}      			    			  			  			
    		</script>
    		
    	</h:form>
	</f:view>
	<%@include file="../templates/footer.jspf" %>   	
    </body>
</HTML>  
