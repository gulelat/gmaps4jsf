<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<HTML>
    <HEAD> 
    <title>Get My Location Example</title> 
    <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAxrVS1QxlpJHXxQ2Vxg2bJBT2yXp_ZAY8_ufC3CFXhHIE1NvwkxS9AOPy_YJl48ifAy4mq6I8SgK8fg"
            type="text/javascript"></script>    
    </HEAD>
	<body>
	<f:view>
    	<h:form id="form">
    	
            <h:panelGrid columns="2">                
                <h:outputText value="Enter your address like 'Giza, Egypt' :"/>
                <h:inputText id="txtAddress" value="#{addressBean.address}"/>
                <h:commandButton value="Get my location on the map" action="#{addressBean.doSearch}"/>                                                 
            </h:panelGrid>      
            
            <h:panelGrid>                     	
	    		<m:map width="300" height="300" address="#{addressBean.address}">
	    			<m:marker/>
	    			<m:htmlInformationWindow htmlText="#{addressBean.address}"/>
	    		</m:map>
            </h:panelGrid>      		
    	</h:form>
	</f:view>
	<%@include file="../templates/footer.jspf" %>   	
    </body>
</HTML>  
