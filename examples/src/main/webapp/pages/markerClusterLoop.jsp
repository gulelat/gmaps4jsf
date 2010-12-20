<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<%@ taglib uri="https://ajax4jsf.dev.java.net/ajax" prefix="a4j" %>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <f:view>
        <head>
            <title>Welcome to GMaps4JSF</title>
            <m:resources key="ABQIAAAAxrVS1QxlpJHXxQ2Vxg2bJBQdkFK-tWRbPPQS4ACM1pq_e-PltxQXeyH20wQuqDaQ_6EM5UeGGVpnIw" includeExtendedComponents="true" />
        </head>
        <body onunload="GUnload()">
            <h:form id="form">
                <m:map width="500px" latitude="10.1" longitude="10.1" height="500px" zoom="2">
                    <m:mapControl name="GSmallMapControl" />
                    <m:markerCluster>
                         <a4j:repeat var="loc" value="#{multiple.locations}">
                            <m:marker latitude="#{loc.latitude}" longitude="#{loc.longitude}" />
                        </a4j:repeat>
                    </m:markerCluster>
                </m:map>
            </h:form>
            <%@include file="../templates/footer.jspf" %>
        </body>
    </f:view>
</html>
