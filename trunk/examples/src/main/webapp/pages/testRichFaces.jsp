<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a"%>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich"%>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <f:view>
        <head>
        	<%@include file="../templates/internalHeader.jspf" %>               
        </head>  
        <body onunload="GUnload()">
            <a:form id="form">
				<rich:modalPanel id="panel" width="520" height="550" onshow="myMap.checkResize();">
					<f:facet name="header">
						<h:panelGroup>
							<h:outputText value="Modal Panel"></h:outputText>
						</h:panelGroup>
					</f:facet>
					<f:facet name="controls">
						<h:panelGroup>
							<h:outputLink styleClass="hidelink" id="hidelink">
								Close (X)
							</h:outputLink>
							
							<rich:componentControl for="panel" attachTo="hidelink" operation="hide" event="onclick"/>
						</h:panelGroup>
					</f:facet>
	
				   <m:map id="gmaps" height="500px" width="500px"
									 address="Cairo, Egypt" jsVariable="myMap">
						
						<m:htmlInformationWindow htmlText="some text" />
						<m:marker address="Cairo, Egypt" />
				   </m:map>
				</rich:modalPanel>
				
				<h:outputLink value="#" id="link">
					Show Modal Panel Including the GMaps4JSF Map 
					<rich:componentControl for="panel" attachTo="link" operation="show" event="onclick"/>
				</h:outputLink>
				
            </a:form>
            <%@include file="../templates/footer.jspf" %>
        </body>
    </f:view>
</html>
