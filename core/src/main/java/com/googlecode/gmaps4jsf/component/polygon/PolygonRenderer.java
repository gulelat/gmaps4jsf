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
package com.googlecode.gmaps4jsf.component.polygon;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import com.googlecode.gmaps4jsf.util.ComponentUtils;
import com.googlecode.gmaps4jsf.component.polyline.AbstractPolyshape;

/**
 * @author Hazem Saleh
 * @date April 12, 2009
 * The (PolygonRenderer) renders a google map polygon.
 */
public class PolygonRenderer extends AbstractPolyshape {

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {    
        if (component.isRendered()) {
            ResponseWriter writer = context.getResponseWriter();
            writer.write("\t\t\tvar " + getJSVariableName(component) + " = " +
                         "parent.createPolygon(" + convertToJavascriptObject((Polygon) component) + ", function () {\n\t\t\tvar points = [null");
        }
    }

    protected String convertToJavascriptObject(Polygon polygon) {
        StringBuffer buffer = new StringBuffer("{");
        buffer.append("hexStrokeColor: '").append(polygon.getHexStrokeColor()).append("', ");
        buffer.append("lineWidth: ").append(polygon.getLineWidth()).append(", ");
        buffer.append("strokeOpacity: ").append(polygon.getStrokeOpacity()).append(", ");
        buffer.append("hexFillColor: '").append(polygon.getHexFillColor()).append("', ");
        buffer.append("fillOpacity: ").append(polygon.getFillOpacity()).append(", ");
        buffer.append("jsVariable: '").append(ComponentUtils.unicode(polygon.getJsVariable())).append("'");
        return buffer.append("}").toString();
    }  
}