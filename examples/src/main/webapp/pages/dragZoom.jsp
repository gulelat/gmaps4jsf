<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
	<f:view>
	
    <head>
        <title>Welcome to GMaps4JSF</title>
        <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />

    <m:resources key="ABQIAAAAxrVS1QxlpJHXxQ2Vxg2bJBQdkFK-tWRbPPQS4ACM1pq_e-PltxQXeyH20wQuqDaQ_6EM5UeGGVpnIw" includeExtendedComponents="true"/>

	<style>
            html, body { height: 100% }
	</style>
    </head>
    <body onunload="GUnload()">
            <h:form id="form">
                <div>Drag Zoom Control</div>
                <m:map width="90%" height="90%" latitude="24" longitude="15" jsVariable="map1" zoom="2">
                    <m:dragZoom imageURL="http://gmaps-utility-library.googlecode.com/svn/trunk/dragzoom/release/examples/images/zoom-button.gif" imageZoomingURL="http://gmaps-utility-library.googlecode.com/svn/trunk/dragzoom/release/examples/images/zoom-button-activated.gif" />
                </m:map>
            </h:form>
	<%@include file="../templates/footer.jspf" %>
    </body>
	</f:view>    
</HTML>