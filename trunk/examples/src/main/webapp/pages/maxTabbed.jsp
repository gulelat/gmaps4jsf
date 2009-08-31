<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE html "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Welcome to GMaps4JSF</title>
        <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
        <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAxrVS1QxlpJHXxQ2Vxg2bJBQdkFK-tWRbPPQS4ACM1pq_e-PltxQXeyH20wQuqDaQ_6EM5UeGGVpnIw" type="text/javascript"> </script>
        <script src="http://gmaps-utility-library-dev.googlecode.com/svn/tags/tabbedmaxcontent/1.0/src/tabbedmaxcontent_packed.js" type="text/javascript"> </script>
	<style>
            html, body { height: 100% }
	</style>
    </head>
    <body onunload="GUnload()">
	<f:view>
            <h:form id="form">
                <div>Max tabbed content</div>
                <m:map width="90%" height="90%" latitude="40.41701670242653" longitude="-3.703519105911255" zoom="5">
                    <m:extendedHtmlInformationWindow regular="This is the center of Spain" summary="<div style='text-align:center;width:100%;'><img src='http://sobreespana.com/wp-content/uploads/2009/04/puerta-del-sol-madrid.jpg' style='width:300px' /></div>" maxTitle="Puerta del Sol-Madrid" />
                </m:map>
            </h:form>
	</f:view>
	<%@include file="../templates/footer.jspf" %>
    </body>
</HTML>