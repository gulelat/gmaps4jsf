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

import java.io.IOException;
import javax.faces.render.Renderer;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import com.googlecode.gmaps4jsf.plugins.gmapsutility.component.Tab;
import com.googlecode.gmaps4jsf.plugins.gmapsutility.tabbedmaxcontent.util.TabbedUtils;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public final class TabRenderer extends Renderer {

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (component.isRendered()) {
            ResponseWriter writer = context.getResponseWriter();
            writer.write(convertToJavascriptObject((Tab) component));
        }
    }

    protected String convertToJavascriptObject(Tab tab) {
        StringBuffer buffer = new StringBuffer("map.encodeTab('");
        buffer.append(tab.getId()).append("', '").append(TabbedUtils.parse(tab.getTitle())).append("', ")
            .append(getTabContent(tab)).append(", ").append(getTabNode(tab))
            .append(", ").append(getTabOnSelect(tab)).append(")");
        return buffer.toString();
    }

    protected final StringBuffer getTabOnSelect(Tab tab) {
        StringBuffer buffer = new StringBuffer();
        if ((tab.getOnSelect() != null) && (tab.getOnSelect().trim().length() > 0)) {
            buffer.append("function (tab) {").append(tab.getOnSelect()).append("}");
        } else {
            buffer.append("null");
        }
        return buffer;
    }

    protected final StringBuffer getTabContent(Tab tab) {
        return getStringOrNull(tab.getContent());
    }

    protected final StringBuffer getTabNode(Tab tab) {
        return getStringOrNull(tab.getContentNode());
    }

    private StringBuffer getStringOrNull(String content) {
        StringBuffer buffer = new StringBuffer();
        if ((content != null) && (content.trim().length() > 0)) {
            buffer.append("'").append(TabbedUtils.parse(content)).append("'");
        } else {
            buffer.append("null");
        }
        return buffer;
    }

}
