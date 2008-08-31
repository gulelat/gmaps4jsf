<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>

<html>

<head>
    <title>GMaps4JSF Mashups Example</title> 
    <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAxrVS1QxlpJHXxQ2Vxg2bJBT2yXp_ZAY8_ufC3CFXhHIE1NvwkxS9AOPy_YJl48ifAy4mq6I8SgK8fg"
            type="text/javascript"></script>    	
</head>


<body onunload="GUnload()">

<f:view>

	<h1>GMaps4JSF Mashups Example</h1>

	<h:form id="myForm">
	
			<h:panelGrid id="panelGrid" columns="1">
			<t:tree2 id="clientTree" value="#{tree.data}" var="node"
					varNodeToggler="t" clientSideToggle="true">
		
		
					<f:facet name="world">
						<h:panelGroup>
							<t:graphicImage value="../images/myfaces.gif" border="0" />
							<h:outputText value="#{node.description}" styleClass="nodeFolder" />
						</h:panelGroup>
					</f:facet>
		
					<f:facet name="continent">
						<h:panelGroup>
							<t:graphicImage value="../images/yellow-folder-open.png"
								 rendered="#{t.nodeExpanded}" border="0" />
							<t:graphicImage value="../images/yellow-folder-closed.png"
								 rendered="#{!t.nodeExpanded}" border="0" />
							<h:outputText value="#{node.description}" styleClass="nodeFolder" />
						</h:panelGroup>
					</f:facet>
		
		
					<f:facet name="country">
						<h:panelGroup>
								<a4j:commandLink immediate="true"
									styleClass="#{t.nodeSelected ? 'documentSelected':'document'}"
									actionListener="#{t.setNodeSelected}" 
									reRender="countryMapGroup, countryImageGroup">
									<t:graphicImage value="../images/document.png" border="0" />
									<h:outputText value="#{node.description}" />
									<f:param name="countryName" value="#{node.description}" />
								</a4j:commandLink>
						</h:panelGroup>
					</f:facet>
		
			</t:tree2>

			
			<h:panelGrid columns="2">
				
				<h:panelGroup id="countryMapGroup">
					<h:outputText id="lblCountryName" value="#{param.countryName}"></h:outputText>
			   		<m:map id="mapCountry" width="300" height="300" 
			   			   address="#{param.countryName}" 
			   			   rendered="#{param.countryName ne null}">
			   			<m:marker/>
			   			<m:htmlInformationWindow htmlText="#{param.countryName}"/>
			   		</m:map>		
				</h:panelGroup>

				<h:panelGroup id="countryImageGroup">	
					<h:outputText id="lblDesc" value="Screenshot of the country<br>" 
								  rendered="#{param.countryName ne null}" 
								  escape="false"></h:outputText>							
					<t:graphicImage id="countryImage"
									value="../images/#{param.countryName}.jpg" 
									rendered="#{param.countryName ne null}"
									width="300" height="300"></t:graphicImage>												
				</h:panelGroup>
			</h:panelGrid>
			
			</h:panelGrid>

	</h:form>
</f:view>

<%@include file="../templates/footer.jspf" %>   	
</body>

</html>

