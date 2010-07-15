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
package com.googlecode.gmaps4jsf.component.overlay;

import java.io.IOException;
import javax.faces.render.Renderer;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import com.googlecode.gmaps4jsf.util.ComponentUtils;

/**
 * @author Hazem Saleh
 * @date September 20, 2008
 * The GroundOverlayEncoder is used for encoding the map ground overlay.
 */
public final class GroundOverlayRenderer extends Renderer {

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (component.isRendered()) {
            ResponseWriter writer = context.getResponseWriter();
            writer.write(ComponentUtils.pad(component) + "parent.createOverlay(" + convertToJavascriptObject((GroundOverlay) component) + ", function (gmap, parent) {\n");
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        if (component.isRendered()) {
            ResponseWriter writer = context.getResponseWriter();
            writer.write(ComponentUtils.pad(component) + "});\n");
        }
    }

    protected String convertToJavascriptObject(GroundOverlay overlay) {
        StringBuffer buffer = new StringBuffer("{");
        buffer.append("imageURL: '").append(ComponentUtils.unicode(overlay.getImageURL())).append("', ");
        buffer.append("startLatitude: ").append(overlay.getStartLatitude()).append(", ");
        buffer.append("startLongitude: ").append(overlay.getStartLongitude()).append(", ");
        buffer.append("endLatitude: ").append(overlay.getEndLatitude()).append(", ");
        buffer.append("endLongitude: ").append(overlay.getEndLongitude()).append(", ");
        buffer.append("jsVariable: '").append(ComponentUtils.unicode(overlay.getJsVariable())).append("'}");
        return buffer.toString();
    }

}
