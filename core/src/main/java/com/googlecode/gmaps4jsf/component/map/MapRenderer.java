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
package com.googlecode.gmaps4jsf.component.map;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.googlecode.gmaps4jsf.util.FileReaderUtils;
import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.util.ComponentUtils;

/**
 * @author Hazem Saleh
 * @date Jul 13, 2008
 * last modified at Jul 31, 2008
 * The (MapRenderer) renders a google map.
 */
public final class MapRenderer extends Renderer {
    private static final String UNDEFINED = "var temp";

    public boolean getRendersChildren() {
        return true;
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ComponentUtils.assertValidContext(context);
        ResponseWriter writer = context.getResponseWriter();

        Map map = (Map) component;
        encodeCommonJavascriptCode(map, writer);
        encodeHTMLModel(context, map, writer);
        startEncodingMapWorld(context, map, writer);
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ComponentUtils.assertValidContext(context);
        ResponseWriter writer = context.getResponseWriter();
        writer.write("\t});\n}) (window);");
        writer.endElement(ComponentConstants.HTML_SCRIPT);
    }

    /*
     * Writes the generic (not binded to a specific component) JS code.
     */
    protected void encodeCommonJavascriptCode(Map map, ResponseWriter writer) throws IOException {
        writer.startElement(ComponentConstants.HTML_SCRIPT, map);
        writer.writeAttribute(ComponentConstants.HTML_SCRIPT_TYPE, ComponentConstants.HTML_SCRIPT_LANGUAGE, ComponentConstants.HTML_SCRIPT_TYPE);
        writer.write(FileReaderUtils.getResourceContent("gmaps4jsf.js", "\n"));
        writer.endElement(ComponentConstants.HTML_SCRIPT);
    }

    private void encodeHTMLModel(FacesContext context, Map map, ResponseWriter writer) throws IOException {
        writer.startElement(ComponentConstants.HTML_DIV, map);
        writer.writeAttribute(ComponentConstants.HTML_ATTR_ID, map.getClientId(context), ComponentConstants.HTML_ATTR_ID);
        writer.writeAttribute(ComponentConstants.HTML_ATTR_STYLE, "width: " + ComponentUtils.getMapWidth(map) + "; height: " + ComponentUtils.getMapHeight(map), ComponentConstants.HTML_ATTR_STYLE);
        writer.endElement(ComponentConstants.HTML_DIV);
    }

    private void startEncodingMapWorld(FacesContext context, Map map, ResponseWriter writer) throws IOException {
        writer.startElement(ComponentConstants.HTML_SCRIPT, map);
        writer.writeAttribute(ComponentConstants.HTML_SCRIPT_TYPE, ComponentConstants.HTML_SCRIPT_LANGUAGE, ComponentConstants.HTML_SCRIPT_TYPE);
        writer.write("(function(window) {\n\twindow.gmaps4jsf.createMap(" + convertToJavascriptObject(context, map) + ", function (gmap) {\n");
    }

    protected String convertToJavascriptObject(FacesContext context, Map map) {
        StringBuffer buffer = new StringBuffer("{");
        buffer.append("id: '").append(map.getClientId(context)).append("',");
        buffer.append("zoom: ").append(map.getZoom()).append(",");
        buffer.append("location: {latitude: ").append(map.getLatitude())
            .append(", longitude: ").append(map.getLongitude())
            .append(", address: '").append(ComponentUtils.unicode(map.getAddress()));
        buffer.append("'}, jsVariable: '").append(ComponentUtils.unicode(map.getJsVariable())).append("'");
        return buffer.append("}").toString();
    }

}
