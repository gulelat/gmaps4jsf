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
package com.googlecode.gmaps4jsf.component.streetviewpanorama;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.googlecode.gmaps4jsf.component.common.BaseRenderer;
import com.googlecode.gmaps4jsf.component.map.EventEncoder;
import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.util.ComponentUtils;
import com.googlecode.gmaps4jsf.util.FileReaderUtils;

/**
 * @author Hazem Saleh
 * @date Dec 23, 2012
 * The (StreetViewPanoramaRenderer) renders a StreetViewPanorama.
 */
public class StreetViewPanoramaRenderer extends BaseRenderer {

    public boolean getRendersChildren() {
        return true;
    }	

    private void encodeHTMLModel(FacesContext facesContext, StreetViewPanorama panorama, ResponseWriter writer) throws IOException {
        writer.startElement(ComponentConstants.HTML_DIV, panorama);
        writer.writeAttribute(ComponentConstants.HTML_ATTR_ID, panorama.getClientId(facesContext), ComponentConstants.HTML_ATTR_ID);
        writer.writeAttribute(ComponentConstants.HTML_ATTR_NAME, panorama.getClientId(facesContext), ComponentConstants.HTML_ATTR_NAME);
        writer.writeAttribute(ComponentConstants.HTML_ATTR_STYLE, "width: " + ComponentUtils.getPanoramaWidth(panorama) + ";height: " + ComponentUtils.getPanoramaHeight(panorama), ComponentConstants.HTML_ATTR_STYLE);
        writer.endElement(ComponentConstants.HTML_DIV);
    }

    private void startEncodingPanoramaWorld(FacesContext context, StreetViewPanorama panorama, ResponseWriter writer) throws IOException {
        writer.startElement(ComponentConstants.HTML_SCRIPT, panorama);
        writer.writeAttribute(ComponentConstants.HTML_SCRIPT_TYPE, ComponentConstants.HTML_SCRIPT_LANGUAGE, ComponentConstants.HTML_SCRIPT_TYPE);
        writer.write("(function(window) {\n\twindow.gmaps4jsf.createPanorama(" + convertToJavascriptObject(context, panorama) + ", function (parent) {\n");
        
        // encode panorama client side events ...
        EventEncoder.encodeEventListeners(context, panorama, writer);        
    }

    /*
     * Writes panorama JS code. This code has no relation to the map JS.
     */
    protected void encodeCommonJavascriptCode(StreetViewPanorama panorama, ResponseWriter writer) throws IOException {
        writer.startElement(ComponentConstants.HTML_SCRIPT, panorama);
        writer.writeAttribute(ComponentConstants.HTML_SCRIPT_TYPE, ComponentConstants.HTML_SCRIPT_LANGUAGE, ComponentConstants.HTML_SCRIPT_TYPE);
        String debug = "true".equals(panorama.getDebug()) ? "\n" : "";
        writer.write(FileReaderUtils.getResourceContent("gmaps4jsf.js", debug));
        writer.write(debug);
        writer.write(FileReaderUtils.getResourceContent("gmaps4jsf-panorama.js", debug));
        writer.endElement(ComponentConstants.HTML_SCRIPT);
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        StreetViewPanorama panorama = (StreetViewPanorama) component;
        
        encodeCommonJavascriptCode(panorama, writer);
        encodeHTMLModel(context, panorama, writer);
        startEncodingPanoramaWorld(context, panorama, writer);
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ComponentUtils.assertValidContext(context);
        ResponseWriter writer = context.getResponseWriter();
        writer.write("\n\t});\n}) (window);");
        writer.endElement(ComponentConstants.HTML_SCRIPT);
    }

    protected String convertToJavascriptObject(FacesContext context, StreetViewPanorama panorama) {
        StringBuffer buffer = new StringBuffer("{");
        buffer.append("id: '").append(panorama.getClientId(context)).append("', ");
        buffer.append("location: {latitude: ").append(panorama.getLatitude()).append(", ");
        buffer.append("longitude: ").append(panorama.getLongitude()).append(", ");
        buffer.append("address: '").append(ComponentUtils.unicode(panorama.getAddress())).append("'}, ");
        buffer.append("pov: {zoom: ").append(panorama.getZoom()).append(", ");
        buffer.append("yaw: ").append(panorama.getYaw()).append(", ");
        buffer.append("pitch: ").append(panorama.getPitch()).append("}, ");
        buffer.append("jsVariable: '").append(ComponentUtils.unicode(panorama.getJsVariable())).append("'}");
        return buffer.toString();
    }

}