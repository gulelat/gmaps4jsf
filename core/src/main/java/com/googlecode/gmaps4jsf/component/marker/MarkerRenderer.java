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
import com.googlecode.gmaps4jsf.util.ComponentUtils;

/**
 * @author Hazem Saleh
 * @date Jan 3, 2009
 * The (MarkerRenderer) renders a google map marker.
 */
public final class MarkerRenderer extends Renderer {

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        Marker marker = (Marker) component;
        ResponseWriter writer = context.getResponseWriter();
        writer.write("\t\t\tparent.createMarker(" + convertToJavascriptObject(marker) + ", function (gmap, parent) {\n");
        
        // encode marker client side events ...
        EventEncoder.encodeEventListeners(context, marker, writer, "parent");
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.write("\n\t\t\t});\n");
    }

    protected String convertToJavascriptObject(Marker marker) {
        StringBuffer buffer = new StringBuffer("{");
        buffer.append("address: '").append(ComponentUtils.unicode(marker.getAddress()));
        buffer.append("', latitude: ").append(marker.getLatitude());
        buffer.append(", longitude: ").append(marker.getLongitude());
        buffer.append(", markerOptions: {draggable: ").append(marker.getDraggable());
        for (Iterator iterator = marker.getChildren().iterator(); iterator.hasNext();) {
            UIComponent component = (UIComponent) iterator.next();
            if (component instanceof Icon) {
                buffer.append(", icon: parent.buildIcon(").append(convertIconToJavascriptObject((Icon) component)).append(")");
            }
        }
        return buffer.append("}}").toString();
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
}