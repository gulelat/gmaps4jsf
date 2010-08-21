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

import java.io.IOException;
import javax.faces.render.Renderer;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import com.googlecode.gmaps4jsf.util.ComponentUtils;
import com.googlecode.gmaps4jsf.plugins.component.Popup;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class PopupMarkerRenderer extends Renderer {

    protected static final String POPUP_MARKER_FUNCTION = "popupMarker";
    protected static final String CHART_SERVER = "http://chart.apis.google.com/chart?";

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (component.isRendered()) {
            ResponseWriter writer = context.getResponseWriter();
            writer.write(convertToJavascriptObject((Popup) component));
        }
    }

    protected String convertToJavascriptObject(Popup popup) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(ComponentUtils.pad(popup));
        buffer.append("gmap.addLabel(parent, '").append(getUrl(popup)).append("');\n");
        return buffer.toString();
    }

    protected String getUrl(Popup popup) {
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
