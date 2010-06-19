<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<%@ taglib uri="http://code.google.com/p/gmaps4jsf/" prefix="m" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Welcome to GMaps4JSF</title>
    </head>
    <body>
        <%@include file="templates/header.jspf" %>
        <f:view>
            <h:form>
                <t:panelTabbedPane activePanelTabVar="true" width="750px" title="Gmaps4JSF components" serverSideTabSwitch="false" inactiveTabStyleClass="bluetab">

                    <t:panelTab label="Map & Markers">

                        <h:outputText value="Simple" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/simple.jsf">
                                <f:verbatim>Some simple maps with no other components</f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="Repeated" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/repeat.jsf">
                                <f:verbatim><span style="color:red">A list of markers with info windows (for-each sample)</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="Auto-Arrange Map" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/autoReshape.jsf">
                                <f:verbatim><span style="color:red">A map that is reshaped according to its markers' positions</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="Markers and Information Windows" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/mapWithMarkersAndInfoWindows.jsf">
                                <f:verbatim><span style="color:red">A map with both markers and Information windows</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="Draggable Markers" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/draggableMarkers.jsf">
                                <f:verbatim><span style="color:red">Map with draggable markers</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="Addressable Markers" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/addressableMarkers.jsf">
                                <f:verbatim><span style="color:red">Map with addressable markers</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="Custom Markers" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/customMarkers.jsf">
                                <f:verbatim><span style="color:red">Map with custom draggable markers</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="Controls" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/mapWithControls.jsf">
                                <f:verbatim><span style="color:red">Adding controls to your map</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="Client-side EventListeners" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/mapEvents.jsf">
                                <f:verbatim><span style="color:red">Listening to client-side events on your map and markers</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="Server-side EventListeners" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/markersServerSideEvents.jsf">
                                <f:verbatim><span style="color:red">Listening to server-side events on markers</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="Geocoding" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/getMyLocation.jsf">
                                <f:verbatim><span style="color:red">An example that demonstrates the usage of the address attribute of the map</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="Reverse Geocoding" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/reverseGeocoding.jsf">
                                <f:verbatim><span style="color:red">An example that demonstrates the usage of the GMaps4JSF ReverseGeocoder service</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                    </t:panelTab>
                    <t:panelTab label="Drawings">

                        <h:outputText value="Polylines" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/mapSimplePolyline.jsf">
                                <f:verbatim>Draw a simple polyline on your map</f:verbatim>
                            </h:outputLink>
                            <h:outputLink value="pages/mapGeodesicPolyline.jsf">
                                <f:verbatim>Draw a geodesic polyline on your map</f:verbatim>
                            </h:outputLink>
                            <h:outputLink value="pages/mapMixedPolylines.jsf">
                                <f:verbatim>Draw mixed polylines on your map</f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="Polygons" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/mapPolygon.jsf">
                                <f:verbatim>Draw a polygon on your map</f:verbatim>
                            </h:outputLink>
                            <h:outputLink value="pages/mapPolygons.jsf">
                                <f:verbatim>Draw many polygons on your map</f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="Circles" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/mapCircle.jsf">
                                <f:verbatim><span style="color:red">Draw a circle on your map</span></f:verbatim>
                            </h:outputLink>
                            <h:outputLink value="pages/mapCircles.jsf">
                                <f:verbatim><span style="color:red">Draw many circles on your map</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="Polygons + Polylines + Events" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/mixedGraphics.jsf">
                                <f:verbatim><span style="color:red">Draw polygons, polylines on your map and listen to their events</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="GroundOverlay" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/groundoverlay.jsf">
                                <f:verbatim><span style="color:red">Place GMaps4JSF logo on the earth</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>
                        
                        
                        <h:outputText value="Direction" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/directions.jsf">
                                <f:verbatim>Usage of the direction component for displaying the driving directions results</f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="JavaScript Customization" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/jsVariableExample.jsf">
                                <f:verbatim>An example that demonstrates the ability to use map and markers using JavaScript using the (jsVariable) attribute</f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                    </t:panelTab>
                    <t:panelTab label="Streetview">

                        <h:outputText value="StreetViewPanorama" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/streetViewPanorama.jsf">
                                <f:verbatim>An example that shows the street view panorama using latitude and longitude</f:verbatim>
                            </h:outputLink>
                            <h:outputLink value="pages/streetViewPanoramaUsingAddress.jsf">
                                <f:verbatim>An example that shows the street view panorama using address</f:verbatim>
                            </h:outputLink>
                            <h:outputLink value="pages/streetViewPanoramaEvents.jsf">
                                <f:verbatim><span style="color:red">An event listener example that listens to the street view panorama</span></f:verbatim>
                            </h:outputLink>
                            <h:outputLink value="pages/showMyStreet.jsf">
                                <f:verbatim><span style="color:red">An example that uses the street view panorama to show the map streets</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>
                    </t:panelTab>

                    <t:panelTab label="Plugins & Extensions">

                        <h:outputText value="DragZoom (Extended Component - Experimental)" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/dragZoom.jsf">
                                <f:verbatim><span style="color:red">An example that demonstrates the DragZoom extended component that uses the gmaps-utility-library-dev project</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="Max tabbed content (Extended Component - Experimental)" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/maxTabbed.jsf">
                                <f:verbatim><span style="color:red">An example that creates enhanced info windows.</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="PopupMarker(Extended Component - Experimental)" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/popupMarkers.jsf">
                                <f:verbatim><span style="color:red">An example that creates popup markers (AKA labels).</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                        <h:outputText value="Marker Clusterer(Extended Component - Experimental)" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/markerCluster.jsf">
                                <f:verbatim><span style="color:red">An example that groups markers in clusters depending on zoom level.</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                    </t:panelTab>

                    <t:panelTab label="Mashups">

                        <h:outputText value="Web 2.0 Mashup Demo" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/mashups.jsf">
                                <f:verbatim>A Web 2.0 Mashup Demo that uses both Apache Tomahawk + Ajax4JSF + GMaps4JSF</f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                    </t:panelTab>

                    <t:panelTab label="Other">

                        <h:outputText value="Performance" />
                        <h:panelGrid style="padding-left:25px">
                            <h:outputLink value="pages/multiple.jsf">
                                <f:verbatim><span style="color:red">Performance test with 500 markers</span></f:verbatim>
                            </h:outputLink>
                        </h:panelGrid>

                    </t:panelTab>

                </t:panelTabbedPane>

            </h:form>
        </f:view>
    </body>
</html>
