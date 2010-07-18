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

import java.util.List;
import java.io.IOException;
import javax.faces.render.Renderer;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import com.googlecode.gmaps4jsf.util.ComponentUtils;
import com.googlecode.gmaps4jsf.plugins.gmapsutility.component.Tab;
import com.googlecode.gmaps4jsf.plugins.gmapsutility.component.MaxInfoWindow;
import com.googlecode.gmaps4jsf.plugins.gmapsutility.tabbedmaxcontent.util.TabbedUtils;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public final class ExtendedInfoWindowRenderer extends Renderer {

    public boolean getRendersChildren() {
        return true;
    }

    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        List children = component.getChildren();
        for (int i = 0; i < children.size(); i++) {
            UIComponent child = (UIComponent) children.get(i);
            if (child.isRendered() && child instanceof Tab) {
                ResponseWriter writer = context.getResponseWriter();
                writer.write(ComponentUtils.pad(component) + "\tdata.tabs.push(");
                child.encodeBegin(context);
                writer.write(");\n");
            }
        }
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (component.isRendered()) {
            ResponseWriter writer = context.getResponseWriter();
            writer.write(ComponentUtils.pad(component) + "parent.createTabbedInfoWindow(" + convertToJavascriptObject((MaxInfoWindow) component) + ", function (map) {\n");
            writer.write(ComponentUtils.pad(component) + "\tvar data = {};\n");
            writer.write(ComponentUtils.pad(component) + "\tdata.tabs = [];\n");
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        if (component.isRendered()) {
            ResponseWriter writer = context.getResponseWriter();
            writer.write(ComponentUtils.pad(component) + "\treturn data;\n");
            writer.write(ComponentUtils.pad(component) + "});\n");
        }
    }

    protected String convertToJavascriptObject(MaxInfoWindow infoWindow) {
        StringBuffer buffer = new StringBuffer("{");
        buffer.append("regular: '").append(TabbedUtils.parse(infoWindow.getRegular())).append("', ");
        buffer.append("summary: '").append(TabbedUtils.parse(infoWindow.getSummary())).append("', ");
        buffer.append("maxTitle: '").append(TabbedUtils.parse(infoWindow.getMaxTitle())).append("', ");
        buffer.append("selectedTab: ").append(getSelectedTab(infoWindow)).append(", ");
        buffer.append("maximized: ").append(infoWindow.isMaximized()).append(", ");
        buffer.append("onClose: ").append(encodeOnClose(infoWindow)).append(", ");
        encodeButtons(infoWindow, buffer);
        return buffer.append("}").toString();
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

    protected void encodeButtons(MaxInfoWindow maxInfoWindow, StringBuffer buffer) {
        buffer.append("buttons: {");
        if (!maxInfoWindow.isShowCloseButton()) buffer.append("close: {show: 4},");
        if (!maxInfoWindow.isShowMaximizeButton()) buffer.append("maximize: {show: 4},");
        if (!maxInfoWindow.isShowMinimizeButton()) buffer.append("restore: {show: 4},");
        if (buffer.charAt(buffer.length() - 1) == ',') buffer.deleteCharAt(buffer.length() - 1);
        buffer.append("}");
    }

    protected final StringBuffer encodeOnClose(MaxInfoWindow maxInfoWindow) {
        String onClose = maxInfoWindow.getOnClose();
        StringBuffer buffer = new StringBuffer("function() {");
        if ((onClose != null) && (onClose.trim().length() > 0)) {
            buffer.append(onClose);
        }
        return buffer.append("}");
    }

}
