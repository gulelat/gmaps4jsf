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

import com.googlecode.gmaps4jsf.component.marker.Marker;
import com.googlecode.gmaps4jsf.plugins.Plugin;
import com.googlecode.gmaps4jsf.plugins.PluginEncoder;
import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.plugins.gmapsutility.component.Tab;
import com.googlecode.gmaps4jsf.plugins.gmapsutility.component.MaxInfoWindow;

/**
 * Base implementation of the Max Tabbed Content JS functions.
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public abstract class AbstractTabbedContentEncoder implements Plugin {

    protected static final String TABBED_INFO_WINDOW_FUNCTION = "tabbedContent";

    private String getParentId(FacesContext facesContext, UIComponent parentComponent) {
        return parentComponent instanceof Marker
            ? PluginEncoder.getUniqueMarkerId(facesContext, (Marker) parentComponent)
            : parentComponent.getId();
    }

    public final String encodeFunctionScript(FacesContext facesContext, UIComponent parentComponent) {
        StringBuffer buffer = new StringBuffer("function ");
        String parentID = getParentId(facesContext, parentComponent);
        buffer.append(TABBED_INFO_WINDOW_FUNCTION).append(parentID).append("(map, parent) {");
        for (Iterator iterator = parentComponent.getChildren().iterator(); iterator.hasNext();) {
            UIComponent component = (UIComponent) iterator.next();
            if (component instanceof MaxInfoWindow  && component.isRendered()) {
                encodeMaxInfoWindow((MaxInfoWindow) component, buffer);
            }
        }
        return buffer.append("}").toString();
    }

    protected void encodeWindowCreation(MaxInfoWindow maxInfoWindow, StringBuffer buffer) {
        buffer.append("var target = parent.getCenter();");
    }

    protected void encodeWindowCreationEnd(MaxInfoWindow maxInfoWindow, StringBuffer buffer) {

    }

    protected final StringBuffer getSelectedTab(MaxInfoWindow maxInfoWindow) {
        StringBuffer buffer = new StringBuffer();
        String selectedTab = maxInfoWindow.getSelectedTab();
        if ((selectedTab != null) && (selectedTab.matches("\\d{1,}"))) {
            buffer.append(selectedTab);
        } else {
            buffer.append("'").append(selectedTab).append("'");
        }
        return buffer;
    }

    protected final StringBuffer parse(StringBuffer source) {
        return parse(source.toString());
    }

    protected final StringBuffer parse(String source) {
        StringBuffer buffer = new StringBuffer();
        if ((source != null) && (source.trim().length() > 0)) {
            buffer.append(source.trim().replace("'", "\\\\\'").replace("\"", "\\\\\\\""));
        }
        return buffer;
    }

    private void encodeMaxInfoWindow(MaxInfoWindow maxInfoWindow, StringBuffer buffer) {
        encodeWindowCreation(maxInfoWindow, buffer);
        encodeTabs(maxInfoWindow, buffer);
        encodeOnClose(maxInfoWindow, buffer);
        encodeButtons(maxInfoWindow, buffer);
        buffer.append("var regular = '").append(parse(maxInfoWindow.getRegular()).append("';"));
        buffer.append("var summary = '").append(parse(maxInfoWindow.getSummary()).append("';"));
        buffer.append("var maxTitle = '").append(parse(maxInfoWindow.getMaxTitle()).append("';"));
        buffer.append("map.")
            .append(TABBED_INFO_WINDOW_FUNCTION)
            .append("(parent, target, regular, summary, maxTitle, ")
            .append(getSelectedTab(maxInfoWindow)).append(", ")
            .append(maxInfoWindow.isMaximized()).append(", buttons, tabs, onClose);");
        encodeWindowCreationEnd(maxInfoWindow, buffer);
    }

    protected final void encodeButtons(MaxInfoWindow maxInfoWindow, StringBuffer buffer) {
        buffer.append("var buttons = {");
        if (!maxInfoWindow.isShowCloseButton()) buffer.append("close: {show: 4},");
        if (!maxInfoWindow.isShowMaximizeButton()) buffer.append("maximize: {show: 4},");
        if (!maxInfoWindow.isShowMinimizeButton()) buffer.append("restore: {show: 4},");
        if (buffer.charAt(buffer.length() - 1) == ',') buffer.deleteCharAt(buffer.length() - 1);
        buffer.append("};");
    }

    protected final void encodeOnClose(MaxInfoWindow maxInfoWindow, StringBuffer buffer) {
        buffer.append("var onClose");
        String onClose = maxInfoWindow.getOnClose();
        if ((onClose != null) && (onClose.trim().length() > 0)) {
            buffer.append(" = function() {").append(onClose).append("}");
        }
        buffer.append(";");
    }

    private void encodeTabs(MaxInfoWindow maxInfoWindow, StringBuffer buffer) {
        buffer.append("var tabs = [");
        for (Iterator iterator = maxInfoWindow.getChildren().iterator(); iterator.hasNext();) {
            UIComponent component = (UIComponent) iterator.next();
            if (component instanceof Tab  && component.isRendered()) {
                encodeTab(maxInfoWindow, (Tab) component, buffer);
            }
        }
        int bufferLength = buffer.length() - 1;
        if (',' == buffer.charAt(bufferLength)) {
            buffer.deleteCharAt(bufferLength);
        }
        buffer.append("];");
    }

    protected final StringBuffer getTabContent(Tab tab) {
        return getStringOrNull(tab.getContent());
    }

    protected final StringBuffer getTabNode(Tab tab) {
        return getStringOrNull(tab.getContentNode());
    }

    protected final StringBuffer getTabOnSelect(Tab tab) {
        StringBuffer buffer = new StringBuffer();
        if ((tab.getOnSelect() != null) && (tab.getOnSelect().trim().length() > 0)) {
            buffer.append("function (tab) {").append(parse(tab.getOnSelect())).append("}");
        } else {
            buffer.append("null");
        }
        return buffer;
    }

    private StringBuffer getStringOrNull(String content) {
        StringBuffer buffer = new StringBuffer();
        if ((content != null) && (content.trim().length() > 0)) {
            buffer.append("'").append(parse(content)).append("'");
        } else {
            buffer.append("null");
        }
        return buffer;
    }

    private void encodeTab(MaxInfoWindow maxInfoWindow, Tab tab, StringBuffer buffer) {
        buffer.append(ComponentConstants.JS_GMAP_BASE_VARIABLE).append(".encodeTab('")
            .append(tab.getId()).append("', '").append(parse(tab.getTitle())).append("', ")
            .append(getTabContent(tab)).append(", ").append(getTabNode(tab))
            .append(", ").append(getTabOnSelect(tab)).append("),");
    }

}
