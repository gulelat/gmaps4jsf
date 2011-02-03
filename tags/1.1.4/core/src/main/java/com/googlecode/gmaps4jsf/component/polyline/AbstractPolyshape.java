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
package com.googlecode.gmaps4jsf.component.polyline;

import java.util.List;
import java.io.IOException;
import javax.faces.render.Renderer;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import com.googlecode.gmaps4jsf.component.point.Point;
import com.googlecode.gmaps4jsf.component.map.EventEncoder;
import com.googlecode.gmaps4jsf.util.ComponentUtils;

/**
 * Base class for shapes made by a set of points.
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public abstract class AbstractPolyshape extends Renderer {

    public boolean getRendersChildren() {
        return true;
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        if (component.isRendered()) {
            ResponseWriter writer = context.getResponseWriter();

            // encode poly listeners.
            writer.write(ComponentUtils.pad(component) + "\tdata.callback = function (parent) {\n");
            EventEncoder.encodeEventListeners(context, component, writer);
            writer.write(ComponentUtils.pad(component) + "\t};\n");

            writer.write(ComponentUtils.pad(component) + "\treturn data;\n");
            writer.write(ComponentUtils.pad(component) + "});\n");
        }
    }
    
    protected String getJSVariableName(UIComponent component) {
    	return "poly_" + component.getId();
    }

}
