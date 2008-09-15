<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
    <HEAD> 
    <title>Welcome to GMaps4JSF</title>  
    </HEAD>
	<body>
	<%@include file="../templates/header.jspf" %>           
        <f:view> 
	        <h:form>	                
	                <h:outputText value="Simple Map" />
	                <h:panelGrid style="padding-left:25px">
	                    <h:outputLink value="pages/simple.jsf">
	                        <f:verbatim>A simple map with no other components</f:verbatim>
	                    </h:outputLink>
	                </h:panelGrid>         
	                
	                <h:outputText value="Using Markers and Information Windows" />
	                <h:panelGrid style="padding-left:25px">                
	                    <h:outputLink value="pages/mapWithMarkersAndInfoWindows.jsf">
	                    	<f:verbatim>A map with both markers and Information windows</f:verbatim>
	                    </h:outputLink>                     
	                </h:panelGrid>
	                
	                <h:outputText value="Get My Location Example" />
	                <h:panelGrid style="padding-left:25px">                
	                    <h:outputLink value="pages/getMyLocation.jsf">
	                    	<f:verbatim>An example that demonstrates the usage of the address attribute of the map</f:verbatim>
	                    </h:outputLink>                     
	                </h:panelGrid>         	
	                
	                <h:outputText value="Access your GMaps4JSF map using JavaScript" />
	                <h:panelGrid style="padding-left:25px">                
	                    <h:outputLink value="pages/jsVariableExample.jsf">
	                    	<f:verbatim>An example that demonstrates the ability to use map and markers using JavaScript using the (jsVariable) attribute</f:verbatim>
	                    </h:outputLink>                     
	                </h:panelGrid>  	                
	                
	                <h:outputText value="Web 2.0 Mashup Demo" />
	                <h:panelGrid style="padding-left:25px">                
	                    <h:outputLink value="pages/mashups.jsf">
	                    	<f:verbatim>A Web 2.0 Mashup Demo that uses both Apache Tomahawk + Ajax4JSF + GMaps4JSF</f:verbatim>
	                    </h:outputLink>                     
	                </h:panelGrid>   	                                                             
	
			</h:form>
        </f:view>      
    </body>
</HTML>  
