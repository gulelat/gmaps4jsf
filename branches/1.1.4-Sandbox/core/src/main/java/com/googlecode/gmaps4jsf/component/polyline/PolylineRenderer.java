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

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import com.googlecode.gmaps4jsf.util.ComponentUtils;

/**
 * @author Hazem Saleh
 * @date April 12, 2009
 * The (PolylineRenderer) renders a google map polyline.
 */
public class PolylineRenderer extends AbstractPolyshape {

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        if (component.isRendered()) {
            ResponseWriter writer = context.getResponseWriter();
            writer.write("\t\tparent.createPolyline(" + convertToJavascriptObject((Polyline) component) + ", function () {\n\t\t\tvar points = [null");
        }
    }

    protected String convertToJavascriptObject(Polyline polyline) {
        StringBuffer buffer = new StringBuffer("{");
        buffer.append("lineWidth: ").append(polyline.getLineWidth()).append(", ");
        buffer.append("hexaColor: '").append(polyline.getHexaColor()).append("', ");
        buffer.append("opacity: ").append(polyline.getOpacity()).append(", ");
        buffer.append("geodesic: ").append(polyline.getGeodesic()).append(", ");
        buffer.append("jsVariable: '").append(ComponentUtils.unicode(polyline.getJsVariable())).append("'");
        return buffer.append("}").toString();
    }

}