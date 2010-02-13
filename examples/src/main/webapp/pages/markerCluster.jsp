<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <f:view>
        <head>
            <title>Welcome to GMaps4JSF</title>
            <m:resources key="ABQIAAAAxrVS1QxlpJHXxQ2Vxg2bJBQdkFK-tWRbPPQS4ACM1pq_e-PltxQXeyH20wQuqDaQ_6EM5UeGGVpnIw" includeExtendedComponents="true" />
        </head>
        <body onunload="GUnload()">
            <h:form id="form">
                <m:map width="650px" height="450px" latitude="40.417923" longitude="-3.700333" jsVariable="gmap" zoom="11">
                    <m:mapControl name="GSmallMapControl" />
                    <m:markerCluster>
                        <m:marker address="Moncloa, Madrid" />
                        <m:marker address="Cibeles, Madrid" />
                        <m:marker address="Puerta del Sol, Madrid" />
                        <m:marker address="Plaza de España, Madrid" />
                        <m:marker address="Majadahonda, Madrid" />
                    </m:markerCluster>
                </m:map>
            </h:form>
            <%@include file="../templates/footer.jspf" %>
        </body>
    </f:view>
</html>
