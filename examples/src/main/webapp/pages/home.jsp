<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<HTML>
    <HEAD> 
    <title>Welcome to GMaps4JSF</title>  
    </HEAD>
    <body>
        <f:view>
	        <h:form>	                
	                <h:outputText value="Example 1" />
	                <h:panelGrid style="padding-left:25px">
	                    <h:outputLink value="pages/simple.jsf">
	                        <f:verbatim>A simple map with no other components</f:verbatim>
	                    </h:outputLink>
	                </h:panelGrid>         
	                
	                <h:outputText value="Example 2" />
	                <h:panelGrid style="padding-left:25px">                
	                    <h:outputLink value="pages/mapWithMarkersAndInfoWindows.jsf">
	                    	<f:verbatim>A map with both markers and Information windows</f:verbatim>
	                    </h:outputLink>                     
	                </h:panelGrid>                             
	
			</h:form>
        </f:view>
    </body>
</HTML>  
