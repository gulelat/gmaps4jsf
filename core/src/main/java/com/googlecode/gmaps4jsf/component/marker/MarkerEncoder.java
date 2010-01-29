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
import javax.faces.event.ValueChangeListener;

import com.googlecode.gmaps4jsf.component.eventlistener.EventListener;
import com.googlecode.gmaps4jsf.component.htmlInformationWindow.HTMLInformationWindow;
import com.googlecode.gmaps4jsf.component.icon.Icon;
import com.googlecode.gmaps4jsf.component.map.EventEncoder;
import com.googlecode.gmaps4jsf.component.map.HTMLInfoWindowEncoder;
import com.googlecode.gmaps4jsf.component.map.IconEncoder;
import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.util.ComponentUtils;
import com.googlecode.gmaps4jsf.util.MapRendererUtil;

/**
 * @author Hazem Saleh
 * @date Jul 31, 2008
 * The MarkerEncoder is used for encoding the map markers.
 */
public class MarkerEncoder {

	public static void encodeMarkerFunctionScript(FacesContext facesContext,
			Map map, Marker marker, ResponseWriter writer) throws IOException {

		writer.write("function "
				+ ComponentConstants.JS_CREATE_MARKER_FUNCTION_PREFIX
				+ getUniqueMarkerId(facesContext, marker) + "("
				+ ComponentConstants.JS_GMAP_BASE_VARIABLE + ") {");

		if (marker.isRendered()) {
			encodeMarker(facesContext, map, marker, writer);
		}

		writer.write("}");
	}

	public static void encodeMarkerFunctionScriptCall(
			FacesContext facesContext, Map map, Marker marker,
			ResponseWriter writer) throws IOException {

		writer.write(ComponentConstants.JS_CREATE_MARKER_FUNCTION_PREFIX
				+ getUniqueMarkerId(facesContext, marker) + "("
				+ ComponentConstants.JS_GMAP_BASE_VARIABLE + ");     ");

	}
    
    private static String getMarkerState(String markerID, Object mapState) {
        if (mapState == null) {
            return null;
        }

        String[] markersState = ((String) mapState).split("&");

        for (int i = 0; i < markersState.length; ++i) {
            if (markersState[i].indexOf(markerID) >= 0) {
                return markersState[i].split("=")[1];
            }
        }

        return null;
    }

    private static void encodeMarker(FacesContext context, Map map,
            Marker marker, ResponseWriter writer) throws IOException {

        Object mapState = ComponentUtils.getValueToRender(context, map);
        String markerState = getMarkerState(marker.getId(), mapState);

        if (markerState != null) { /* Marker comes from postback and it has a state */ 

            // update marker model.
            updateMarkerModel(context, markerState, marker);

            writer.write("var " + ComponentConstants.CONST_MARKER_PREFIX
                    + marker.getId() + " = new "
                    + ComponentConstants.JS_GMarker_OBJECT + "(new "
                    + ComponentConstants.JS_GLatLng_OBJECT + markerState + ","
                    + getMarkerOptions(context, marker, writer) + ");     ");
            
            if (MapRendererUtil.isAutoReshapeMap(map)) {
                writer.write("setBounds" + map.getId() + markerState + ";     ");
            }

        } else if (marker.getAddress() != null) { /* Marker does not have a state, and it has an address */
            String errorMessageScript = "";

            // create the marker instance from address.
            writer.write("var geocoder_" + marker.getId() + " = new "
                    + ComponentConstants.JS_GClientGeocoder_OBJECT + "();     ");

            if ("true".equalsIgnoreCase(marker.getShowLocationNotFoundMessage())) {
                errorMessageScript = "alert('"
                        + marker.getLocationNotFoundErrorMessage() + "');     ";
            }

            // send XHR request to get the address location and write to the
            // response.
            writer.write("geocoder_" + marker.getId() + ".getLatLng('"
                    + marker.getAddress() + "'," + "function(location) {     "
                    + "if (!location) {     " 
                    + errorMessageScript
                    + "} else {     ");

            writer.write("var " + ComponentConstants.CONST_MARKER_PREFIX
                    + marker.getId() + " = new "
                    + ComponentConstants.JS_GMarker_OBJECT + "(location, "
                    + getMarkerOptions(context, marker, writer) + ");     ");
            
            if (MapRendererUtil.isAutoReshapeMap(map)) {
                writer.write("reshapeMap" + map.getId() + "("
                        + ComponentConstants.JS_GMAP_BASE_VARIABLE + ", "
                        + "location.lat(), location.lng());     ");
            }
            
        } else { /* Marker does not have a state, and it has a longitude and latitude */

            // create the marker instance from latlng.
            String longitude;
            String latitude;

            if (marker.getLatitude() != null) {
                latitude = marker.getLatitude();
            } else {
                latitude = ComponentConstants.JS_GMAP_BASE_VARIABLE
                        + ".getCenter().lat()";
            }

            if (marker.getLongitude() != null) {
                longitude = marker.getLongitude();
            } else {
                longitude = ComponentConstants.JS_GMAP_BASE_VARIABLE
                        + ".getCenter().lng()";
            }

            writer.write("var " + ComponentConstants.CONST_MARKER_PREFIX
                    + marker.getId() + " = new "
                    + ComponentConstants.JS_GMarker_OBJECT + "(new "
                    + ComponentConstants.JS_GLatLng_OBJECT + "(" + latitude
                    + ", " + longitude + "),"
                    + getMarkerOptions(context, marker, writer) + ");     ");
            
            if (MapRendererUtil.isAutoReshapeMap(map)) {
                writer.write("reshapeMap" + map.getId() + "(" + 
					ComponentConstants.JS_GMAP_BASE_VARIABLE + ", " + latitude
					+ ", " + longitude + ");     ");
            }           
        }

        completeMarkerRendering(context, map, marker, writer);

        if (markerState == null && marker.getAddress() != null) {
            writer.write("}" + "}     " + ");     ");
        }
    }

    private static void updateMarkerModel(FacesContext context,
            String markerState, Marker marker) {

        if (marker.getValueBinding(ComponentConstants.MARKER_ATTR_LATITUDE) != null) {
            marker.getValueBinding(ComponentConstants.MARKER_ATTR_LATITUDE)
                    .setValue(context, markerState.split(",")[0].substring(1));
        }

        if (marker.getValueBinding(ComponentConstants.MARKER_ATTR_LONGITUDE) != null) {
            marker.getValueBinding(ComponentConstants.MARKER_ATTR_LONGITUDE)
                    .setValue(
                            context,
                            markerState.split(",")[1].substring(0, markerState
                                    .split(",")[1].length() - 1));
        }
    }

    private static void completeMarkerRendering(FacesContext facesContext,
            Map map, Marker marker, ResponseWriter writer) throws IOException {

        writer.write(ComponentConstants.JS_GMAP_BASE_VARIABLE
                + ".addOverlay(marker_" + marker.getId() + ");     ");

        // save the marker state.
        saveMarkerState(facesContext, map, marker, writer);

        // process marker events.
        encodeMarkerChildren(facesContext, marker, writer);

        // update marker user variable.
        updateMarkerJSVariable(facesContext, marker, writer);
    }

    private static void saveMarkerState(FacesContext facesContext, Map map,
            Marker marker, ResponseWriter writer) throws IOException {
        
        // Start creating the drag end listener.
        String markerDragEndHandler = "function " + "marker_" + marker.getId()
                + "_dragEnd(latlng) " + "{     " +

                "var markersState = document.getElementById('"
                + ComponentUtils.getMapStateHiddenFieldId(map)
                + "').value;     " 
                
                
                + "if (markersState.indexOf('" + marker.getId() + "=') != -1) {     "
                + "var markersArray = markersState.split('&');     "
                + "var updatedMarkersState = '';     " 
                
                + "for (i = 0; i < markersArray.length; ++i) {     " 

                    + "if (markersArray[i].indexOf('" + marker.getId() + "=') == -1) {     "
                    
                        + "updatedMarkersState += markersArray[i];     "
                        + "if (markersArray.length != 1 && i < markersArray.length - 1) {     "
                            + "updatedMarkersState += '&';     " 
                        + "}     " 
                    
                    + "}     "
                + "}     " 
                
                + "markersState = updatedMarkersState;     " 
                + "}     " 
                
                
                + "if (markersState != '' && markersState.charAt(markersState.length - 1) != '&') {     " 
                    + "markersState += '&';     "
                + "}     " 
                
                + "markersState += '" + marker.getId() + "=' + latlng;     "
                
                // Save the marker state.
                + "document.getElementById('"
                + ComponentUtils.getMapStateHiddenFieldId(map)
                + "').value = markersState;     " 
                
                // Submit the form on marker value change if required.
                + ("true".equalsIgnoreCase(marker.getSubmitOnValueChange()) ? "document.getElementById('" 
                + ComponentUtils.findParentForm(facesContext, marker).getId() + "')." 
                + "submit();     ;"
                : "") 
                
                // End the drag end listener.
                + "}     "
                
                // Attach the listener to the marker drag end event.
                + ComponentConstants.JS_GEVENT_OBJECT + ".addListener("
                + "marker_" + marker.getId() + ", 'dragend', " + "marker_"
                + marker.getId() + "_dragEnd" + ");     ";

        writer.write(markerDragEndHandler);
    }

    private static void encodeMarkerChildren(FacesContext facesContext,
            Marker marker, ResponseWriter writer) throws IOException {

        // encode marker client side events.
        for (Iterator iterator = marker.getChildren().iterator(); iterator
                .hasNext();) {
            UIComponent component = (UIComponent) iterator.next();

            if (component instanceof EventListener) {
                EventEncoder.encodeEventListenersFunctionScript(facesContext,
                        marker, writer, ComponentConstants.CONST_MARKER_PREFIX
                                + marker.getId());
                EventEncoder.encodeEventListenersFunctionScriptCall(facesContext,
                                marker, writer,
                                ComponentConstants.CONST_MARKER_PREFIX
                                + marker.getId());
            }
        }

        // encode marker information.
        for (Iterator iterator = marker.getChildren().iterator(); iterator
                .hasNext();) {
            UIComponent component = (UIComponent) iterator.next();

            if (component instanceof HTMLInformationWindow) {
                HTMLInformationWindow window = (HTMLInformationWindow) component;
                writer.write(ComponentConstants.JS_GEVENT_OBJECT
                                + ".addListener("
                                + ComponentConstants.CONST_MARKER_PREFIX
                                + marker.getId() + ", '"
                                + marker.getShowInformationEvent()
                                + "', function() {");

                HTMLInfoWindowEncoder.encodeMarkerHTMLInfoWindow(facesContext,
                        marker, window, writer);

                writer.write("});     ");

                break;
            }
        }
    }

    private static void updateMarkerJSVariable(FacesContext facesContext,
            Marker marker, ResponseWriter writer) throws IOException {

        if (marker.getJsVariable() != null) {
            writer.write("     " + marker.getJsVariable() + " = "
                    + ComponentConstants.CONST_MARKER_PREFIX + marker.getId()
                    + ";     ");
        }
    }

    private static String getUniqueMarkerId(FacesContext facesContext,
            Marker marker) {
        String markerID = marker.getClientId(facesContext);

        return markerID.replace(':', '_');
    }

    private static String getMarkerOptions(FacesContext facesContext,
            Marker marker, ResponseWriter writer) throws IOException {

        String markerOptions = "{";

        // check dragability.
        if ("true".equalsIgnoreCase(marker.getDraggable())) {
            markerOptions += "draggable: true";
        } else {
            markerOptions += "draggable: false";
        }

        // check if the marker has an icon.
        for (Iterator iterator = marker.getChildren().iterator(); iterator
                .hasNext();) {
            UIComponent component = (UIComponent) iterator.next();

            if (component instanceof Icon) {
                Icon icon = (Icon) component;

                // encode the marker icon script.
                IconEncoder.encodeIconFunctionScript(facesContext, icon, writer);

                // call the icon script.
                markerOptions += ", icon: "
                              + IconEncoder.getIconFunctionScriptCall(facesContext,
                                icon, writer);
                break;
            }
        }

        markerOptions += "}";

        return markerOptions;
    }    
}
