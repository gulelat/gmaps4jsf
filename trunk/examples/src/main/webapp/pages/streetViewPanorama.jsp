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
     
    </head>
	<body onunload="GUnload()">
	<f:view>
    	<h:form id="form">
		  	<div>Street View Panorama using latitude and longitude</div>       	
    		<m:streetViewPanorama width="500px" height="500px" 
    							  latitude="42.345573" longitude="-71.098326" />
    	</h:form>
	</f:view>
	<%@include file="../templates/footer.jspf" %>   	
    </body>
</html>  
