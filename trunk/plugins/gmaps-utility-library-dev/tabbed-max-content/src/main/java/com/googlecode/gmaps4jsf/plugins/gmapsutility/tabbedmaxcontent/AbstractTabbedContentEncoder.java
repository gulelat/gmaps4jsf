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

    public final String encodeFunctionScript(FacesContext facesContext, UIComponent parentComponent) {
        StringBuffer buffer = new StringBuffer("function ");
        buffer.append(TABBED_INFO_WINDOW_FUNCTION).append(parentComponent.getId()).append("(map, parent) {");
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

    private void encodeMaxInfoWindow(MaxInfoWindow maxInfoWindow, StringBuffer buffer) {
        buffer.append("var regular = \\\"").append(maxInfoWindow.getRegular())
            .append("\\\";var summary = \\\"")
            .append(maxInfoWindow.getSummary() == null ? "" : maxInfoWindow.getSummary())
            .append("\\\";").append("var maxTitle = \\\"")
            .append(maxInfoWindow.getMaxTitle() == null ? "" : maxInfoWindow.getMaxTitle())
            .append("\\\";").append("var maximized= ")
            .append(maxInfoWindow.isMaximized()).append(";var selectedTab = 0;");
        try {
            int index = Integer.parseInt(maxInfoWindow.getSelectedTab());
            buffer.append("selectedTab = ").append(index);
        } catch (Exception ex) {
            buffer.append("selectedTab = \\\"").append(maxInfoWindow.getSelectedTab()).append("\\\"");
        }
        encodeTabs(maxInfoWindow, buffer.append(";"));
        encodeWindowCreation(maxInfoWindow, buffer);
        buffer.append("parent.openMaxContentTabsHtml(target, regular, summary, tabs, {")
            .append("maxTitle: maxTitle,").append("selectedTab: selectedTab,")
            .append("maximized: maximized").append("});");
        encodeWindowCreationEnd(maxInfoWindow, buffer);

    }

    private void encondeOnSelectSupport(MaxInfoWindow maxInfoWindow, StringBuffer buffer) {
        buffer.append("if (window.gSelectTabFunctions === undefined) {")
            .append("window.gSelectTabFunctions = {};");
        buffer.append("window.gSelectTabFunctions['").append(maxInfoWindow.getId())
            .append("'] = {};");
        buffer.append("GEvent.addListener(").append(ComponentConstants.JS_GMAP_BASE_VARIABLE)
            .append(".getTabbedMaxContent(), 'selecttab', function(tab) {")
            .append("var selection = window.gSelectTabFunctions['")
            .append(maxInfoWindow.getId()).append("'][tab.id];if(selection) {")
            .append("for (var index = 0; selection.length > index; index++) {")
            .append("if (selection[index]) selection[index](tab);")
            .append("}}});}");
    }

    private void encodeTabs(MaxInfoWindow maxInfoWindow, StringBuffer buffer) {
        encondeOnSelectSupport(maxInfoWindow, buffer);
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

    private void encodeTab(MaxInfoWindow maxInfoWindow, Tab tab, StringBuffer buffer) {
        buffer.append("(function() {var t = new MaxContentTab(\\\"")
            .append(tab.getTitle()).append("\\\", ");
        if ((tab.getContent() != null) && (tab.getContent().trim().length() > 0)) {
            buffer.append("\\\"").append(tab.getContent()).append("\\\"");
        } else if ((tab.getContentNode() != null) && (tab.getContentNode().trim().length() > 0)) {
            buffer.append("(function() {window.gSelectTabFunctions['")
                .append(maxInfoWindow.getId()).append("']['")
                .append(tab.getId()).append("'] = [function(tab){")
                .append("var node = document.getElementById('").append(tab.getContentNode())
                .append("');if (node) {node.style.display='block';")
                .append("node.style.width='98%';node.style.height='98%';")
                .append("tab.getContentNode().appendChild(node);}}];")
                .append("return document.createElement('div');})()");
        } else {
            buffer.append("document.createElement('div')");
        }
        buffer.append(");t.id = '").append(tab.getId()).append("';");
        if ((tab.getOnSelect() != null) && (tab.getOnSelect().trim().length() > 0)) {
            buffer.append("var selection = window.gSelectTabFunctions['")
                .append(maxInfoWindow.getId()).append("']['")
                .append(tab.getId()).append("'];if (!selection){")
                .append("window.gSelectTabFunctions['")
                .append(maxInfoWindow.getId()).append("']['")
                .append(tab.getId()).append("'] = []; selection = ")
                .append("window.gSelectTabFunctions['")
                .append(maxInfoWindow.getId()).append("']['")
                .append(tab.getId()).append("'];}selection.push(")
                .append("function (tab) {").append(tab.getOnSelect())
                .append("});");
        }
        buffer.append("return t;})(),");
    }

}
