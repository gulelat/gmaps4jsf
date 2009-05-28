<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
    <HEAD> 
    <title>Get My Location Example</title> 
    <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAxrVS1QxlpJHXxQ2Vxg2bJBQdkFK-tWRbPPQS4ACM1pq_e-PltxQXeyH20wQuqDaQ_6EM5UeGGVpnIw"
      type="text/javascript"></script>

    </HEAD>
	
	<body onunload="GUnload()">
	<f:view>
    	<h:form id="form">
    	
            <h:panelGrid columns="2">                
                <h:outputText value="Enter your address like 'Giza, Egypt' :"/>
                <h:inputText id="txtAddress" value="#{addressBean.address}"/>
                <h:commandButton value="Get my location on the map" action="#{addressBean.doSearch}"/>                                                 
            </h:panelGrid>      
            
            <h:panelGrid>                     	
	    		<m:map width="500px" height="500px" address="#{addressBean.address}">
	    			<m:marker/>
	    			<m:htmlInformationWindow htmlText="#{addressBean.address}"/>
	    		</m:map>
            </h:panelGrid>      		
    	</h:form>
	</f:view>
	<%@include file="../templates/footer.jspf" %>   	
    </body>
</HTML>  
