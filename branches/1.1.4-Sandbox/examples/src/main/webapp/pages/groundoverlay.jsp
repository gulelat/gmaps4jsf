<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <f:view>
        <head>
            <title>Welcome to GMaps4JSF</title>
            <m:resources key="ABQIAAAAD6cDv-a0AnpzXA4gj6utCRTUlDOzA1Sd8h2eDxLDJEZUtkHZ_xQlPmNUQ-At6YLqCd29cGkwT8i95A"/>
            <style type="text/css">
                html, body { height: 100% }
            </style>
        </head>
        <body onunload="GUnload()">
            <h:form id="form">
                <div>Ground Overlay</div>
                <m:map width="90%" height="90%" latitude="24" longitude="15" zoom="2">
                    <m:groundoverlay imageURL="http://www.jroller.com/HazemBlog/resource/gmaps4jsf-logo.png"
                                     startLatitude="7" endLatitude="23"
                                     startLongitude="-54" endLongitude="84" />
                </m:map>
            </h:form>
            <%@include file="../templates/footer.jspf" %>
        </body>
    </f:view>
</html>
