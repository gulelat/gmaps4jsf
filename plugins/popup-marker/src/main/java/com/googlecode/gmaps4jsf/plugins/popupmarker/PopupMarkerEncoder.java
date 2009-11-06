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
package com.googlecode.gmaps4jsf.plugins.popupmarker;

import java.util.Iterator;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import com.googlecode.gmaps4jsf.plugins.Plugin;
import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.component.marker.Marker;
import com.googlecode.gmaps4jsf.plugins.component.Popup;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class PopupMarkerEncoder implements Plugin {

    protected static final String POPUP_MARKER_FUNCTION = "popupMarker";
    protected static final String CREATE_POPUP_MARKER_FUNCTION = "createPopupMarker";
    protected static final String EVENTS_POPUP_MARKER_FUNCTION = "eventsPopupMarker";
    protected static final String CHART_SERVER = "http://chart.apis.google.com/chart?";

    public Class getModifiedComponent() {
        return Marker.class;
    }

    public String encodeFunctionScript(FacesContext facesContext, UIComponent parentComponent) {
        StringBuffer buffer = new StringBuffer();
        encodeCreatePopupFunction(facesContext, buffer, parentComponent);
        encodePopupEventsFunction(facesContext, buffer, parentComponent);
        buffer.append("function ").append(POPUP_MARKER_FUNCTION).append(parentComponent.getClientId(facesContext).replace(':', '_')).append("(map, parent) {");
        for (Iterator iterator = parentComponent.getChildren().iterator(); iterator.hasNext();) {
            UIComponent component = (UIComponent) iterator.next();
            if (component instanceof Popup  && component.isRendered()) {
                encodePopup(facesContext, (Popup) component, buffer, parentComponent);
            }
        }
        return buffer.append("}").toString();
    }

    private void encodeCreatePopupFunction(FacesContext facesContext, StringBuffer buffer, UIComponent parentComponent) {
        buffer.append("function ").append(CREATE_POPUP_MARKER_FUNCTION).append(parentComponent.getClientId(facesContext).replace(':', '_'))
            .append("(map, marker, img) {")
            .append("var origin = marker.getLatLng();")
            .append("var point = map.fromLatLngToContainerPixel(origin);")
            .append("var end = new GPoint(point.x + img.width, point.y - img.height);")
            .append("var ne = gmap.fromContainerPixelToLatLng(end);")
            .append("var bounds = new GLatLngBounds(origin, ne);")
            .append("var overlay = new GGroundOverlay(img.src, bounds);")
            .append("map.addOverlay(overlay);return overlay;}");
    }

    private void encodePopupEventsFunction(FacesContext facesContext, StringBuffer buffer, UIComponent parentComponent) {
        buffer.append("function ").append(EVENTS_POPUP_MARKER_FUNCTION).append(parentComponent.getClientId(facesContext).replace(':', '_'))
            .append("(map, marker, img, overlay) {var dglst = ");
        buffer.append(ComponentConstants.JS_GEVENT_OBJECT)
            .append(".addListener(marker, 'dragstart', function(latlng) {")
            .append("GEvent.removeListener(dglst);map.removeOverlay(overlay);")
            .append("});var dgelst = ");
        buffer.append(ComponentConstants.JS_GEVENT_OBJECT)
            .append(".addListener(marker, 'dragend', function(latlng) {")
            .append("GEvent.removeListener(dgelst);GEvent.clearInstanceListeners(marker);")
            .append("var ov = ").append(CREATE_POPUP_MARKER_FUNCTION)
            .append(parentComponent.getClientId(facesContext).replace(':', '_')).append("(map, marker, img);")
            .append(EVENTS_POPUP_MARKER_FUNCTION).append(parentComponent.getClientId(facesContext).replace(':', '_'))
            .append("(map, marker, img, ov);").append("});var lst = ");
        buffer.append(ComponentConstants.JS_GEVENT_OBJECT)
            .append(".addListener(map, 'zoomend', function(a,b) {")
            .append("GEvent.removeListener(lst);")
            .append("map.removeOverlay(overlay); var ov =")
            .append(CREATE_POPUP_MARKER_FUNCTION).append(parentComponent.getClientId(facesContext).replace(':', '_'))
            .append("(map, marker, img);").append(EVENTS_POPUP_MARKER_FUNCTION)
            .append(parentComponent.getClientId(facesContext).replace(':', '_')).append("(map, marker, img, ov);")
            .append("});");
        buffer.append("}");

    }

    public String encodeFunctionScriptCall(FacesContext facesContext, UIComponent markerComponent) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(POPUP_MARKER_FUNCTION).append(markerComponent.getClientId(facesContext).replace(':', '_'))
            .append("(").append(ComponentConstants.JS_GMAP_BASE_VARIABLE).append(",")
            .append(ComponentConstants.CONST_MARKER_PREFIX).append(markerComponent.getClientId(facesContext).replace(':', '_'))
            .append(");");
        return buffer.toString();
    }

    private void encodePopup(FacesContext facesContext, Popup popup, StringBuffer buffer, UIComponent parentComponent) {
        buffer.append("var img = new Image();var url = \\\"")
            .append(getUrl(popup)).append("\\\";")
            .append("img.src = url;")
            .append("img.onload = function() {var ov = ")
            .append(CREATE_POPUP_MARKER_FUNCTION).append(parentComponent.getClientId(facesContext).replace(':', '_'))
            .append("(map, parent, this);").append(EVENTS_POPUP_MARKER_FUNCTION)
            .append(parentComponent.getClientId(facesContext).replace(':', '_')).append("(map, parent, this, ov);");
        buffer.append("};");
    }

    private String getUrl(Popup popup) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(CHART_SERVER).append("chst=")
            .append(popup.getChartStyle()).append("&chld=")
            .append(popup.getIcon()).append("|bb|");
        if (popup.getChartStyle().indexOf("texts") > 0) {
            buffer.append(popup.getBackgroundColor()).append("|")
                .append(popup.getTextColor()).append("|")
                .append(popup.getContent());
        } else {
            buffer.append(popup.getContent()).append("|")
                .append(popup.getBackgroundColor()).append("|")
                .append(popup.getTextColor());
        }
        return buffer.toString();
    }

}
