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
            <script type="text/javascript">
                    function mapMoveEndHandler() {
                        var center = map1.getCenter();
                        document.getElementById("message").innerHTML = "Center of map is " + center.toString();
                    }
                    function marker1ClickHandler() {
                        alert("You clicked on Egypt marker");
                    }
                    function marker2ClickHandler() {
                        alert("You clicked on US marker");
                    }
                    function mapClickHandler(overlay,  latlng,  overlaylatlng) {
                        alert("You click on (lat, lng): " + latlng);
                    }
                    function markerWindowClosed() {
                        alert("You closed the marker window");
                        //Incase of the HTMLInfoWindow "closeclick" event, you should call the Map
                        //closeInfoWindow() method.
                        //It is a Google Maps bug, Google Maps APIs do not execute the window close
                        //when removing the "closeclick" event listener as it depends on the event.
                        //map1.closeInfoWindow();
                    }
                    function mapWindowClosed() {
                        alert("You closed the map window");
                        //Incase of the HTMLInfoWindow "closeclick" event, you should call the Map
                        //closeInfoWindow() method.
                        //It is a Google Maps bug, Google Maps APIs do not execute the window close
                        //when removing the "closeclick" event listener as it depends on the event.
                        map1.closeInfoWindow();
                    }
                </script>
        </head>
        <body onunload="GUnload()">
            <h:form id="form">
                <div>Drag the map, close the window and click the marker!!!</div>
                <m:map width="90%" height="90%" latitude="24" longitude="15" jsVariable="map1" zoom="2">
                    <m:marker latitude="30.01" longitude="31.14" jsVariable="marker1">
                        <m:eventListener eventName="click" jsFunction="marker1ClickHandler"/>
                        <m:htmlInformationWindow htmlText="Close the marker window!!!">
                            <m:eventListener eventName="closeclick" jsFunction="markerWindowClosed"/>
                        </m:htmlInformationWindow>
                    </m:marker>
                    <m:marker latitude="39" longitude="-101" jsVariable="marker2">
                        <m:eventListener eventName="click" jsFunction="marker2ClickHandler"/>
                    </m:marker>
                    <m:htmlInformationWindow latitude="-26.745610382199007" longitude="119.8828125" htmlText="Close the map window!!!">
                        <m:eventListener eventName="closeclick" jsFunction="mapWindowClosed"/>
                    </m:htmlInformationWindow>
                    <m:eventListener eventName="moveend" jsFunction="mapMoveEndHandler"/>
                </m:map>
                <div id="message"/>ç
            </h:form>
            <%@include file="../templates/footer.jspf" %>
        </body>
    </f:view>
</html>