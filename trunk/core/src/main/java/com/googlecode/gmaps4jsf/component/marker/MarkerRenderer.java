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

import java.util.Iterator;
import java.io.IOException;
import javax.faces.render.Renderer;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import com.googlecode.gmaps4jsf.component.icon.Icon;
import com.googlecode.gmaps4jsf.component.map.EventEncoder;
import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.util.ComponentUtils;

/**
 * @author Hazem Saleh
 * @date Jan 3, 2009
 * The (MarkerRenderer) renders a google map marker.
 */
public final class MarkerRenderer extends Renderer {
    
    protected static String getUniqueMarkerId(FacesContext facesContext, Marker marker) {
        return marker.getClientId(facesContext).replace(':', '_');
    }        
	
    public void encodeBegin(FacesContext facesContext, UIComponent component) throws IOException {
        Marker         marker      = (Marker) component;
        ResponseWriter writer      = facesContext.getResponseWriter();
        Map            map         = (Map) ComponentUtils.findParentMap(facesContext, marker);        
        Object         mapState    = ComponentUtils.getValueToRender(facesContext, map);
        String         markerState = getMarkerState(getUniqueMarkerId(facesContext, marker), mapState);
        
        // restore the marker if it has previous state
    	updateELBinding(facesContext, marker, markerState);            
        
    	// render the marker
        writer.write(ComponentUtils.pad(marker) + "parent.createMarker(" + convertToJavascriptObject(facesContext, marker, markerState) + ", function (gmap, parent) {\n");
        
        // encode marker client side events ...
        EventEncoder.encodeEventListeners(facesContext, marker, writer);
    }

    public void encodeEnd(FacesContext facesContext, UIComponent component) throws IOException {
        ResponseWriter writer = facesContext.getResponseWriter();
        
        writer.write(ComponentUtils.pad(component) + "});\n");
    }
    
    public void decode(FacesContext facesContext, UIComponent component) {
        Marker marker   = (Marker) component;
        Map    map      = (Map) ComponentUtils.findParentMap(facesContext, marker);
        String mapState = (String) facesContext.getExternalContext().getRequestParameterMap().get(ComponentUtils.getMapStateHiddenFieldId(map));

        decodeMarker(facesContext, marker, mapState);
    }    

    protected String convertToJavascriptObject(FacesContext facesContext, Marker marker, String markerState) {
        StringBuffer buffer = new StringBuffer("{address: '");
        
        if (markerState != null) {
            String[] markersLngLat = markerState.split(",");
            buffer.append("', latitude: ").append(markersLngLat[0].substring(1).trim());
            buffer.append(", longitude: ").append(markersLngLat[1].substring(0, markersLngLat[1].length() - 1).trim());
        } else {
            buffer.append(ComponentUtils.unicode(marker.getAddress()));
            buffer.append("', latitude: ").append(marker.getLatitude());
            buffer.append(", longitude: ").append(marker.getLongitude());
        }

        buffer.append(", parentFormID: '").append(ComponentUtils.findParentForm(facesContext, marker).getId());
        buffer.append("', showInformationEvent: '").append(marker.getShowInformationEvent());        
        buffer.append("', submitOnValueChange: '").append(marker.getSubmitOnValueChange().toLowerCase());
        buffer.append("', markerID: '").append(getUniqueMarkerId(facesContext, marker));
        buffer.append("', stateHiddenFieldID: '").append(ComponentUtils.getMapStateHiddenFieldId((Map) ComponentUtils.findParentMap(facesContext, marker)));        
        buffer.append("', markerOptions: {draggable: ").append(marker.getDraggable());

        for (Iterator iterator = marker.getChildren().iterator(); iterator.hasNext();) {
            UIComponent component = (UIComponent) iterator.next();
            
            if (component instanceof Icon) {
                buffer.append(", icon: parent.buildIcon(").append(convertIconToJavascriptObject((Icon) component)).append(")");
            }
        }

        buffer.append("}, jsVariable: '").append(ComponentUtils.unicode(marker.getJsVariable())).append("'}");
        return buffer.toString();
    }

    protected StringBuffer convertIconToJavascriptObject(Icon icon) {
        StringBuffer buffer = new StringBuffer("{");
        buffer.append("shadow: '").append(ComponentUtils.unicode(icon.getShadowImageURL()));
        buffer.append("', iconSize: {width: ").append(icon.getWidth())
            .append(", height: ").append(icon.getHeight());
        buffer.append("}, shadowSize: {width: ").append(icon.getShadowWidth())
            .append(", height: ").append(icon.getShadowHeight());
        buffer.append("}, iconAnchor: {x: ").append(icon.getXcoordAnchor())
            .append(", y: ").append(icon.getYcoordAnchor());
        buffer.append("}, infoWindowAnchor: {x: ").append(icon.getXcoordInfoWindowAnchor())
            .append(", y: ").append(icon.getYcoordInfoWindowAnchor());
        buffer.append("}, image: '").append(icon.getImageURL()).append("'");
        return buffer.append("}");
    }
    
    private static String getMarkerState(String markerClientID, Object mapState) {
        if (mapState == null) {
            return null;
        }

        String[] markersState = ((String) mapState).split("&");

        for (int i = 0; i < markersState.length; ++i) {
            if (markersState[i].indexOf(markerClientID) >= 0) {
                return markersState[i].split("=")[1];
            }
        }

        return null;
    }    

    private static MarkerValue getMarkerValueFromState(String markerState) {
        MarkerValue markerValue      = new MarkerValue();
        String[]    markerExpression = markerState.split("=");
        String[]    markersLngLat    = markerExpression[1].split(",");

        markerValue.setLatitude(markersLngLat[0].substring(1).trim());
        markerValue.setLongitude(markersLngLat[1].substring(0, markersLngLat[1].length() - 1).trim());

        return markerValue;
    }  
    
    private void decodeMarker(FacesContext facesContext, Marker marker, String mapState) {
        if (markerStateChanged(facesContext, marker, mapState)) {
            int         start       = mapState.indexOf(getUniqueMarkerId(facesContext, marker) + "=");
            int         end         = mapState.indexOf(")", start);
            
            String      markerState = mapState.substring(start, end + 1);
            MarkerValue markerValue = getMarkerValueFromState(markerState);
            marker.setSubmittedValue(markerValue);
        }
    }

	private boolean markerStateChanged(FacesContext facesContext, Marker marker, String mapState) {
		return mapState != null
			   && mapState.indexOf(getUniqueMarkerId(facesContext, marker)) == 0;
	}
    
    private void updateELBinding(FacesContext facesContext, Marker marker, String markerState) {
        if (markerState != null && (! markerState.equals(""))) {
            if (marker.getValueBinding(ComponentConstants.MARKER_ATTR_LATITUDE) != null) {
                marker.getValueBinding(ComponentConstants.MARKER_ATTR_LATITUDE)
                      .setValue(facesContext, markerState.split(",")[0].substring(1).trim());
            }

            if (marker.getValueBinding(ComponentConstants.MARKER_ATTR_LONGITUDE) != null) {
                marker.getValueBinding(ComponentConstants.MARKER_ATTR_LONGITUDE)
                      .setValue(facesContext,
                                markerState.split(",")[1].substring(0,
                                markerState.split(",")[1].length() - 1).trim());
            }
        }
    }
}
