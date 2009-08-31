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
package com.googlecode.gmaps4jsf.plugins.gmapsutility.tabbedmaxcontent;

import java.util.Iterator;

import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;

import com.googlecode.gmaps4jsf.plugins.Plugin;
import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.plugins.gmapsutility.component.MaxInfoWindow;


/**
 * Creates an enhanced information window that can be maximized.
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class MaxInfoWindowEncoder implements Plugin {

    private static final String TABBED_INFO_WINDOW_FUNCTION = "tabbedContent";

    public Class getModifiedComponent() {
        return Map.class;
    }

    public String encodeFunctionScript(FacesContext facesContext, UIComponent mapComponent) {
        StringBuffer buffer = new StringBuffer("function ");
        buffer.append(TABBED_INFO_WINDOW_FUNCTION).append(mapComponent.getId()).append("(map) {");
        for (Iterator iterator = mapComponent.getChildren().iterator(); iterator.hasNext();) {
            UIComponent component = (UIComponent) iterator.next();
            if (component instanceof MaxInfoWindow  && component.isRendered()) {
                encodeMaxInfoWindow((MaxInfoWindow) component, buffer);
            }
        }
        return buffer.append("}").toString();
    }

    public String encodeFunctionScriptCall(FacesContext facesContext, UIComponent mapComponent) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(TABBED_INFO_WINDOW_FUNCTION).append(mapComponent.getId())
            .append("(").append(ComponentConstants.JS_GMAP_BASE_VARIABLE).append(");");
        return buffer.toString();
    }

    private void encodeMaxInfoWindow(MaxInfoWindow maxInfoWindow, StringBuffer buffer) {
        buffer.append("var regular = \\\"").append(maxInfoWindow.getRegular())
            .append("\\\";var summary = \\\"").append(maxInfoWindow.getSummary())
            .append("\\\";var tabs = ");
        encodeTabs(maxInfoWindow, buffer);
        buffer.append("map.openMaxContentTabsHtml(map.getCenter(), regular, summary, tabs, {");
        buffer.append("maxTitle: \\\"").append(maxInfoWindow.getMaxTitle()).append("\\\",");
        try {
            int index = Integer.parseInt(maxInfoWindow.getSelectedTab());
            buffer.append("selectedTab: ").append(index).append(",");
        } catch (Exception ex) {
            buffer.append("selectedTab: \\\"").append(maxInfoWindow.getSelectedTab()).append("\\\",");
        }
        buffer.append("maximized: ").append(maxInfoWindow.isMaximized());
        buffer.append("});");
    }

    private void encodeTabs(MaxInfoWindow maxInfoWindow, StringBuffer buffer) {
        buffer.append("[];");
    }

}
