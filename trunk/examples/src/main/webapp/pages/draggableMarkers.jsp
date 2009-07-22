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
		  	<div>Drag the markers and select your favorite places!!!</div>    	
    		<m:map width="90%" height="90%" latitude="24" longitude="15" jsVariable="map1" type="G_NORMAL_MAP" zoom="2">
    			<m:marker latitude="#{place1.latitude}" longitude="#{place1.longitude}" jsVariable="marker1" draggable="true">
					<m:eventListener eventName="dragend" jsFunction="marker1DragHandler"/>    				
    			</m:marker>
    			<m:marker latitude="#{place2.latitude}" longitude="#{place2.longitude}" jsVariable="marker2" draggable="true">
					<m:eventListener eventName="dragend" jsFunction="marker2DragHandler"/>    				
    			</m:marker>    				
    		</m:map>
            <h:commandButton value="Save as a favorite place!!!" action="#{addressBean.doSearch}"/>  <br>                                      		
			<h:outputText value="First place (lat, lng): (#{place1.latitude}, #{place1.longitude})"/> <br>
			<h:outputText value="Second place (lat, lng): (#{place2.latitude}, #{place2.longitude})"/> <br>		
		    <script>
		   	function marker1DragHandler(latlng) {
		   		alert("Current marker1 latlng is: " + latlng);  	
		   	}	
		   	function marker2DragHandler(latlng) {
		   		alert("Current marker2 latlng is: " + latlng);  	
		   	}		   		   		   	
		    </script>    		 		
    	</h:form>
	</f:view>
	<%@include file="../templates/footer.jspf" %>   	
    </body>
</html>  
