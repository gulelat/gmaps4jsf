/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.googlecode.gmaps4jsf.component.marker;

import java.io.IOException;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.googlecode.gmaps4jsf.component.eventlistener.EventListener;
import com.googlecode.gmaps4jsf.component.htmlInformationWindow.HTMLInformationWindow;
import com.googlecode.gmaps4jsf.component.icon.Icon;
import com.googlecode.gmaps4jsf.component.map.EventEncoder;
import com.googlecode.gmaps4jsf.component.map.HTMLInfoWindowEncoder;
import com.googlecode.gmaps4jsf.component.map.IconEncoder;
import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.util.ComponentUtils;

public class MarkerEncoder {

    public MarkerEncoder() {
    }

    private static String getMarkerState(String markerID, Object mapState) {
	if (mapState == null) {
	    return null;
	}
	
	String markersState[] = ((String) mapState).split("&");
	
	for (int i = 0; i < markersState.length; i++) {
	    if (markersState[i].contains(markerID)) {
		return markersState[i].split("=")[1];
	    }
	}

	return null;
    }

    private static void encodeMarker(FacesContext context, Map map,
	    Marker marker, ResponseWriter writer) throws IOException {
	
	Object mapState = ComponentUtils.getValueToRender(context, map);
	String markerState = getMarkerState(marker.getId(), mapState);
	
	if (markerState != null) {
	    updateMarkerModel(context, markerState, marker);
	    writer.write("var marker_" + marker.getId() + " = new " + "GMarker"
		    + "(new " + "GLatLng" + markerState + ","
		    + getMarkerOptions(context, marker, writer) + ");");
	} else if (marker.getAddress() != null) {
	    writer.write("var geocoder_" + marker.getId() + " = new "
		    + "GClientGeocoder" + "();");
	    writer.write("geocoder_" + marker.getId() + ".getLatLng(\""
		    + marker.getAddress() + "\"," + "function(location) {\n"
		    + "if (!location) {\n" + "alert(\""
		    + marker.getLocationNotFoundErrorMessage() + "\");\n"
		    + "} else {\n");
	    writer.write("var marker_" + marker.getId() + " = new " + "GMarker"
		    + "(location, " + getMarkerOptions(context, marker, writer)
		    + ");");
	} else {
	    String latitude;
	    if (marker.getLatitude() != null) {
		latitude = marker.getLatitude();
	    } else {
		latitude = "map_base_variable.getCenter().lat()";
	    }
	    
	    String longitude;
	    
	    if (marker.getLongitude() != null) {
		longitude = marker.getLongitude();
	    } else {
		longitude = "map_base_variable.getCenter().lng()";
	    }
	    
	    writer.write("var marker_" + marker.getId() + " = new " + "GMarker"
		    + "(new " + "GLatLng" + "(" + latitude + ", " + longitude
		    + ")," + getMarkerOptions(context, marker, writer) + ");");
	}
	completeMarkerRendering(context, map, marker, writer);
	
	if (marker.getAddress() != null) {
	    writer.write("}}\n);\n");
	}
    }

    private static void updateMarkerModel(FacesContext context,
	    String markerState, Marker marker) {
	
	if (marker.getValueBinding("latitude") != null) {
	    marker.getValueBinding("latitude").setValue(context,
		    markerState.split(",")[0].substring(1));
	}
	
	if (marker.getValueBinding("longitude") != null) {
	    marker.getValueBinding("longitude").setValue(
		    context,
		    markerState.split(",")[1].substring(0, markerState
			    .split(",")[1].length() - 1));
	}
    }

    private static void completeMarkerRendering(FacesContext facesContext,
	    Map map, Marker marker, ResponseWriter writer) throws IOException {
	
	writer.write("map_base_variable.addOverlay(marker_" + marker.getId()
		+ ");");
	saveMarkerState(facesContext, map, marker, writer);
	encodeMarkerChildren(facesContext, marker, writer);
	updateMarkerJSVariable(facesContext, marker, writer);
    }

    private static void saveMarkerState(FacesContext facesContext, Map map,
	    Marker marker, ResponseWriter writer) throws IOException {
	
	String markerDragEndHandler = "function marker_" + marker.getId()
		+ "_dragEnd(latlng) " + "{\r\n"
		+ "var markersState = document.getElementById(\""
		+ ComponentUtils.getMapStateHiddenFieldId(map)
		+ "\").value;\r\n" + "if (markersState.indexOf('"
		+ marker.getId() + "=') != -1) {\r\n"
		+ "var markersArray = markersState.split('&');\r\n"
		+ "var updatedMarkersState = \"\";\r\n"
		+ "for (i = 0; i < markersArray.length; ++i) {\r\n"
		+ "if (markersArray[i].indexOf('" + marker.getId()
		+ "=') == -1) {\r\n"
		+ "updatedMarkersState += markersArray[i];\r\n"
		+ "if (i != 0 && i < markersArray.length - 1) {\r\n"
		+ "updatedMarkersState += \"&\";\r\n" + "}\r\n" + "}\r\n"
		+ "}\r\n" + "markersState = updatedMarkersState;\r\n" + "}\r\n"
		+ "if (markersState != \"\") {\r\n"
		+ "markersState += '&';\r\n" + "}\r\n" + "markersState += \""
		+ marker.getId() + "=\" + latlng;\r\n"
		+ "document.getElementById(\""
		+ ComponentUtils.getMapStateHiddenFieldId(map)
		+ "\").value = markersState;" + "}" + "GEvent"
		+ ".addListener(" + "marker_" + marker.getId()
		+ ", \"dragend\", " + "marker_" + marker.getId() + "_dragEnd"
		+ ");";
	writer.write(markerDragEndHandler);
    }

    private static void encodeMarkerChildren(FacesContext facesContext,
	    Marker marker, ResponseWriter writer) throws IOException {
	
	Iterator iterator = marker.getChildren().iterator();
	
	do {
	    if (!iterator.hasNext()) {
		break;
	    }
	    
	    UIComponent component = (UIComponent) iterator.next();
	    
	    if (component instanceof EventListener) {
		EventEncoder.encodeEventListenersFunctionScript(facesContext,
			marker, writer, "marker_" + marker.getId());
		EventEncoder.encodeEventListenersFunctionScriptCall(
			facesContext, marker, writer, "marker_"
				+ marker.getId());
	    }
	} while (true);
	
	iterator = marker.getChildren().iterator();
	
	do {
	    if (!iterator.hasNext()) {
		break;
	    }
	    
	    UIComponent component = (UIComponent) iterator.next();
	    
	    if (!(component instanceof HTMLInformationWindow)) {
		continue;
	    }
	    
	    HTMLInformationWindow window = (HTMLInformationWindow) component;
	    writer.write("GEvent.addListener(marker_" + marker.getId() + ", \""
		    + marker.getShowInformationEvent() + "\", function() {");
	    HTMLInfoWindowEncoder.encodeMarkerHTMLInfoWindow(facesContext,
		    marker, window, writer);
	    writer.write("});");
	    break;
	} while (true);
    }

    private static void updateMarkerJSVariable(FacesContext facesContext,
	    Marker marker, ResponseWriter writer) throws IOException {
	
	if (marker.getJsVariable() != null) {
	    writer.write("\r\n" + marker.getJsVariable() + " = " + "marker_"
		    + marker.getId() + ";\r\n");
	}
    }

    private static String getUniqueMarkerId(FacesContext facesContext,
	    Marker marker) {
	
	String markerID = marker.getClientId(facesContext);
	return markerID.replace(":", "_");
    }

    private static String getMarkerOptions(FacesContext facesContext,
	    Marker marker, ResponseWriter writer) throws IOException {
	
	String markerOptions = "{";
	if ("true".equalsIgnoreCase(marker.getDraggable())) {
	    markerOptions = markerOptions + "draggable: true";
	} else {
	    markerOptions = markerOptions + "draggable: false";
	}
	
	Iterator iterator = marker.getChildren().iterator();
	
	do {
	    if (!iterator.hasNext()) {
		break;
	    }
	    
	    UIComponent component = (UIComponent) iterator.next();
	    
	    if (!(component instanceof Icon)) {
		continue;
	    }
	    
	    Icon icon = (Icon) component;
	    IconEncoder.encodeIconFunctionScript(facesContext, icon, writer);
	    markerOptions = markerOptions
		    + ", icon: "
		    + IconEncoder.getIconFunctionScriptCall(facesContext, icon,
			    writer);
	    break;
	} while (true);
	
	markerOptions = markerOptions + "}";
	return markerOptions;
    }

    public static void encodeMarkerFunctionScript(FacesContext facesContext,
	    Map map, Marker marker, ResponseWriter writer) throws IOException {
	
	writer.write("function createMarkerFunction"
		+ getUniqueMarkerId(facesContext, marker) + "("
		+ "map_base_variable" + ") {");
	if ((marker instanceof Marker) && marker.isRendered()) {
	    encodeMarker(facesContext, map, marker, writer);
	}
	writer.write("}");
    }

    public static void encodeMarkerFunctionScriptCall(
	    FacesContext facesContext, Map map, Marker marker,
	    ResponseWriter writer) throws IOException {
	
	writer.write("createMarkerFunction"
		+ getUniqueMarkerId(facesContext, marker) + "("
		+ "map_base_variable" + ");");
    }
}