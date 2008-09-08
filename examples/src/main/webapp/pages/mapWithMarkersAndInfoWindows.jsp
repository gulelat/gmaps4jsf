<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<HTML>
    <HEAD> 
    <title>Welcome to GMaps4JSF</title> 
    <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAxrVS1QxlpJHXxQ2Vxg2bJBQdkFK-tWRbPPQS4ACM1pq_e-PltxQXeyH20wQuqDaQ_6EM5UeGGVpnIw"
      type="text/javascript"></script>
  
    </HEAD>
	
	<body onunload="GUnload()">
	<f:view>
    	<h:form id="form">
    		<m:map width="500" height="500" latitude="30.01" longitude="31.14">
    			<m:marker latitude="30.01" longitude="31.14"/>
    			<m:htmlInformationWindow latitude="30.01" longitude="31.14" 
    			   htmlText="<center>Welcome to Cairo, Egypt<br><img height='30' width='50' src='http://www.appliedlanguage.com/flags_of_the_world/large_flag_of_egypt.gif'></center>"/>
    		</m:map>
    	</h:form>
	</f:view>
	<%@include file="../templates/footer.jspf" %>   	
    </body>
</HTML>  
