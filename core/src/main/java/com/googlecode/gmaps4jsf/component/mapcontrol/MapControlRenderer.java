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
package com.googlecode.gmaps4jsf.component.mapcontrol;

import java.io.IOException;
import javax.faces.render.Renderer;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import com.googlecode.gmaps4jsf.util.ComponentUtils;

/**
 * Renders map controls.
 * @Data 22 Dec 2012
 * @author Jose Noheda [jose.noheda@gmail.com] and Hazem Saleh
 */
public final class MapControlRenderer extends Renderer {

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (component.isRendered()) {
            ResponseWriter writer = context.getResponseWriter();
            writer.write(ComponentUtils.pad(component) + "parent.createControl(" + convertToJavascriptObject((MapControl) component) + ");\n");
        }
    }

    protected String convertToJavascriptObject(MapControl control) {
        StringBuffer buffer = new StringBuffer("{");
        
        buffer.append("name: '").append(ComponentUtils.unicode(control.getName()));
        buffer.append("', position: ").append(ComponentUtils.unicode(control.getPosition()));
        
        if (control.getControlStyle() != null) {
        	buffer.append(", style: ").append(control.getControlStyle());
        } 
        
        return buffer.append("}").toString();
    }

}
