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
package com.googlecode.gmaps4jsf.component.circle;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.googlecode.gmaps4jsf.util.ComponentUtils;

/**
 * @author Hazem Saleh
 * @date December 19, 2009
 * The (CircleRenderer) renders a google map circle.
 */
public class CircleRenderer extends Renderer {

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (component.isRendered()) {
            ResponseWriter writer = context.getResponseWriter();
            writer.write(ComponentUtils.pad(component) + "parent.createCircle(" + convertToJavascriptObject((Circle) component) + ");\n");
        }
    }

    protected String convertToJavascriptObject(Circle control) {
        StringBuffer buffer = new StringBuffer("{");
        buffer.append("raduis: '").append(ComponentUtils.unicode(control.getRaduis()));
        buffer.append("', unit: '").append(ComponentUtils.unicode(control.getUnit()));
        buffer.append("', latitude: '").append(control.getLatitude());
        buffer.append("', longitude: '").append(control.getLongitude());
        buffer.append("', strokeColour: '").append(control.getHexStrokeColor());        
        buffer.append("', lineWidth: '").append(control.getLineWidth());        
        buffer.append("', strokeOpacity: '").append(control.getStrokeOpacity());        
        buffer.append("', fillColour: '").append(control.getHexFillColor());        
        buffer.append("', fillOpacity: '").append(control.getFillOpacity() + "'");        
        return buffer.append("}").toString();
    }

    public boolean getRendersChildren() {
        return true;
    }
}