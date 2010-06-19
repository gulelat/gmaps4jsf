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
	
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        Marker         marker      = (Marker) component;
        ResponseWriter writer      = context.getResponseWriter();
        Map            map         = (Map) ComponentUtils.findParentMap(context, marker);        
        Object         mapState    = ComponentUtils.getValueToRender(context, map);
        String         markerState = getMarkerState(marker.getId(), mapState);
        
        // restore the marker if it has previous state
    	updateELBinding(context, marker, markerState);            
        
    	// render the marker
        writer.write("\t\t\tparent.createMarker(" + convertToJavascriptObject(context, marker, markerState) + ", function (gmap, parent) {\n");
        
        // encode marker client side events ...
        EventEncoder.encodeEventListeners(context, marker, writer, "parent");
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.write("\t\t\t});\n");
    }
    
    public void decode(FacesContext context, UIComponent component) {
        Marker marker   = (Marker) component;
        Map    map      = (Map) ComponentUtils.findParentMap(context, marker);
        String mapState = (String) context.getExternalContext().getRequestParameterMap().get(ComponentUtils.getMapStateHiddenFieldId(map));

        decodeMarker(marker, mapState);
    }    

    protected String convertToJavascriptObject(FacesContext context, Marker marker, String markerState) {
        StringBuffer buffer = new StringBuffer("{");

        if (markerState != null) {
            String[] markersLngLat = markerState.split(",");
            String latitude  = markersLngLat[0].substring(1).trim();
            String longitude = markersLngLat[1].substring(0, markersLngLat[1].length() - 1).trim();
            
            buffer.append("address: ''");
            buffer.append(", latitude: ").append(latitude);
            buffer.append(", longitude: ").append(longitude);
        } else {
            buffer.append("address: '").append(ComponentUtils.unicode(marker.getAddress()));
            buffer.append("', latitude: ").append(marker.getLatitude());
            buffer.append(", longitude: ").append(marker.getLongitude());
        }

        buffer.append(", parentFormID: ").append("'" + ComponentUtils.findParentForm(context, marker).getId() + "'");
        buffer.append(", submitOnValueChange: ").append("'" + marker.getSubmitOnValueChange().toLowerCase() + "'");        
        buffer.append(", markerID: ").append("'" + getUniqueMarkerId(context, marker) + "'");        
        buffer.append(", stateHiddenFieldID: ").append("'" + ComponentUtils.getMapStateHiddenFieldId((Map) ComponentUtils.findParentMap(context, marker)) + "'");        

        buffer.append(", markerOptions: {draggable: ").append(marker.getDraggable());
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

    private static MarkerValue getMarkerValueFromState(String markerState) {
        MarkerValue markerValue      = new MarkerValue();
        String[]    markerExpression = markerState.split("=");
        String[]    markersLngLat    = markerExpression[1].split(",");

        markerValue.setLatitude(markersLngLat[0].substring(1).trim());
        markerValue.setLongitude(markersLngLat[1].substring(0, markersLngLat[1].length() - 1).trim());

        return markerValue;
    }  
    
    private void decodeMarker(Marker marker, String mapState) {
        if (mapState != null && mapState.indexOf(marker.getId()) != -1) {
            int         start       = mapState.indexOf(marker.getId() + "=");
            int         end         = mapState.indexOf(")", start);
            String      markerState = mapState.substring(start, end + 1);
            MarkerValue markerValue = getMarkerValueFromState(markerState);
                          
            marker.setSubmittedValue(markerValue);
        }
    }
    
    private void updateELBinding(FacesContext context, Marker marker, String markerState) {
        if (markerState != null && (! markerState.equals(""))) {
            if (marker.getValueBinding(ComponentConstants.MARKER_ATTR_LATITUDE) != null) {
                marker.getValueBinding(ComponentConstants.MARKER_ATTR_LATITUDE)
                  .setValue(context, markerState.split(",")[0].substring(1).trim());
            }

            if (marker.getValueBinding(ComponentConstants.MARKER_ATTR_LONGITUDE) != null) {
                marker.getValueBinding(ComponentConstants.MARKER_ATTR_LONGITUDE)
                    .setValue(context,
                                markerState.split(",")[1].substring(0,
                                markerState.split(",")[1].length() - 1).trim()
                    );
            }
        }
    }

}
