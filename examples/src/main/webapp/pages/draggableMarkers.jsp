<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
	<f:view>
	
    <HEAD> 	    
    <title>Welcome to GMaps4JSF</title>    
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />    	
    <m:resources key="ABQIAAAAD6cDv-a0AnpzXA4gj6utCRTUlDOzA1Sd8h2eDxLDJEZUtkHZ_xQlPmNUQ-At6YLqCd29cGkwT8i95A"/>
	<style>
		html, body { height: 100% }
	</style>
    </HEAD>
	
	<body onunload="GUnload()">
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
	<%@include file="../templates/footer.jspf" %>   	
    </body>
	</f:view>    
</HTML>  
