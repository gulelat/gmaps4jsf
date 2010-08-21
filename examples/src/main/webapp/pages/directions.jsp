<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <f:view>
        <head>
            <title>Welcome to GMaps4JSF</title>
            <m:resources key="ABQIAAAAD6cDv-a0AnpzXA4gj6utCRTUlDOzA1Sd8h2eDxLDJEZUtkHZ_xQlPmNUQ-At6YLqCd29cGkwT8i95A"/>
        </head>
        <body onunload="GUnload()">
            <h:form id="form">
                <m:map id="map" width="600" height="480px" latitude="42.351505" longitude="-71.094455" zoom="15">
                    <m:direction
                        attachNodeId="steps"
                        fromAddress="500 Memorial Drive, Cambridge, MA"
                        toAddress="4 Yawkey Way, Boston, MA 02215 (Fenway Park)"
                    />
                </m:map>
                <div id="steps" style="width:600px;height:400px"></div>
            </h:form>
            <%@include file="../templates/footer.jspf" %>
        </body>
    </f:view>
</html>