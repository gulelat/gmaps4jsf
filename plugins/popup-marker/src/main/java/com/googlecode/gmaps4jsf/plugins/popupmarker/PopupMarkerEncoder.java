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
import com.googlecode.gmaps4jsf.plugins.MarkerPlugin;
import com.googlecode.gmaps4jsf.plugins.PluginEncoder;
import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.component.marker.Marker;
import com.googlecode.gmaps4jsf.plugins.component.Popup;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class PopupMarkerEncoder extends MarkerPlugin {

    protected static final String POPUP_MARKER_FUNCTION = "popupMarker";
    protected static final String CHART_SERVER = "http://chart.apis.google.com/chart?";

    public String encodeFunctionScript(FacesContext facesContext, UIComponent marker) {  
        StringBuffer buffer = new StringBuffer();
        buffer.append("function ").append(POPUP_MARKER_FUNCTION).append(PluginEncoder.getUniqueMarkerId(facesContext, (Marker) marker)).append("(map, marker) {");
        for (Iterator iterator = marker.getChildren().iterator(); iterator.hasNext();) {
            UIComponent component = (UIComponent) iterator.next();
            if (component instanceof Popup  && component.isRendered()) {
                buffer.append("map.addLabel(marker, '").append(getUrl((Popup) component)).append("');");
            }
        }
        return buffer.append("}").toString();
    }

    public String encodeFunctionScriptCall(FacesContext facesContext, UIComponent marker) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(POPUP_MARKER_FUNCTION).append(PluginEncoder.getUniqueMarkerId(facesContext, (Marker) marker))
            .append("(").append(ComponentConstants.JS_GMAP_BASE_VARIABLE).append(",")
            .append(ComponentConstants.CONST_MARKER_PREFIX).append(PluginEncoder.getUniqueMarkerId(facesContext, (Marker) marker))
            .append(");");
        return buffer.toString();
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
