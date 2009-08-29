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
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.googlecode.gmaps4jsf.component.groundoverlay.GroundOverlay;
import com.googlecode.gmaps4jsf.component.marker.Marker;
import com.googlecode.gmaps4jsf.component.polygon.Polygon;
import com.googlecode.gmaps4jsf.component.polyline.Polyline;
import com.googlecode.gmaps4jsf.plugins.PluginEncoder;
import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.util.ComponentUtils;
import com.googlecode.gmaps4jsf.util.MapRendererUtil;

/**
 * @author Hazem Saleh
 * @date Jul 13, 2008
 * last modified at Jul 31, 2008 
 * The (MapRenderer) renders a google map.
 */
public class MapRenderer extends Renderer {

    public boolean getRendersChildren() {
        return true;
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ComponentUtils.assertValidContext(context);

        ResponseWriter writer = context.getResponseWriter();

        encodeHTMLModel(context, component, writer);

        startEncodingMapWorld(context, component, writer);
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {

        ComponentUtils.assertValidContext(context);

        ResponseWriter writer = context.getResponseWriter();

        endEncodingMapWorld(context, component, writer);
    }

    public void decode(FacesContext context, UIComponent component) {
        Map map = (Map) component;
        String submittedValue = (String) context.getExternalContext().getRequestParameterMap().get(
                ComponentUtils.getMapStateHiddenFieldId(map));

        map.setSubmittedValue(submittedValue);
    }
    
    /*
     * Declare the JS variables for the map and its related objects such as
     * markers, polygons, polylines ...
     */
    private void declareJSVariables(FacesContext facesContext, Map map, ResponseWriter writer) throws IOException {
        if (map.getJsVariable() != null) {
            writer.write("      var " + map.getJsVariable() + ";     ");
        }

        for (Iterator iterator = map.getChildren().iterator(); iterator.hasNext();) {
            UIComponent component = (UIComponent) iterator.next();

            if (component instanceof Marker) {
                Marker marker = (Marker) component;
                if (marker.getJsVariable() != null) {
                    writer.write("      var " + marker.getJsVariable() + ";     ");
                }
            } else if (component instanceof Polyline) {
                Polyline polyline = (Polyline) component;
                if (polyline.getJsVariable() != null) {
                    writer.write("      var " + polyline.getJsVariable() + ";     ");
                }
            } else if (component instanceof Polygon) {
                Polygon polygon = (Polygon) component;
                if (polygon.getJsVariable() != null) {
                    writer.write("      var " + polygon.getJsVariable() + ";     ");
                }
            } else if (component instanceof GroundOverlay) {
                GroundOverlay groundOverlay = (GroundOverlay) component;
                if (groundOverlay.getJsVariable() != null) {
                    writer.write("      var " + groundOverlay.getJsVariable() + ";     ");
                }
            }
        }
    }

    /*
     * This is the core method that is responsible for the whole map rendering.
     * It encodes the scripts that would be used for rendering the map objects.
     * @param facesContext @param mapComponent @param writer @throws IOException
     */
    private void startEncodingMapRendererWrapper(FacesContext facesContext, Map map, ResponseWriter writer)
            throws IOException {

        writer.write("function " + ComponentConstants.JS_RENDER_MAP_FUNC + map.getId() + "(){");

        if (MapRendererUtil.isAutoReshapeMap(map)) {
            MapRendererUtil.encodeMapAutoReshapeFunctionScript(map, writer);
        }

        HTMLInfoWindowEncoder.encodeHTMLInfoWindowsFunctionScript(facesContext, map, writer);

        MapControlEncoder.encodeMapControlsFunctionScript(facesContext, map, writer);

        EventEncoder.encodeEventListenersFunctionScript(facesContext, map, writer,
                ComponentConstants.JS_GMAP_BASE_VARIABLE);

        GroundOverlayEncoder.encodeGroundOverlaysFunctionScript(facesContext, map, writer);

        PluginEncoder.encodeMapPluginsFunctionScripts(facesContext, map, writer);

        MapRendererUtil.startEncodingMapScript(facesContext, map, writer);
    }

    private void endEncodingMapRendererWrapper(FacesContext facesContext, Map map, ResponseWriter writer)
            throws IOException {

        MapRendererUtil.endEncodingMapScript(facesContext, map, writer);

        if (MapRendererUtil.isAutoReshapeMap(map)) {
            MapRendererUtil.reshapeMapToCurrentBounds(map, writer);
        }

        writer.write("}");
    }

    private void callMapRendererWrapper(FacesContext facesContext, UIComponent component, ResponseWriter writer)
            throws IOException {

        writer.write(ComponentConstants.JS_RENDER_MAP_FUNC + component.getId() + "();     ");
    }

    private void encodeHTMLModel(FacesContext context, UIComponent component, ResponseWriter writer) throws IOException {
        Map map = (Map) component;

        // encode map model.
        writer.startElement(ComponentConstants.HTML_DIV, map);

        writer.writeAttribute(ComponentConstants.HTML_ATTR_ID, map.getClientId(context),
                ComponentConstants.HTML_ATTR_ID);
        writer.writeAttribute(ComponentConstants.HTML_ATTR_STYLE, "width: " + map.getWidth() + "; height: "
                + map.getHeight(), ComponentConstants.HTML_ATTR_STYLE);

        writer.endElement(ComponentConstants.HTML_DIV);

        // encode map state holder.
        Object mapState = ComponentUtils.getValueToRender(context, map);

        writer.startElement(ComponentConstants.HTML_INPUT, map);

        writer.writeAttribute(ComponentConstants.HTML_ATTR_ID, ComponentUtils.getMapStateHiddenFieldId(map),
                ComponentConstants.HTML_ATTR_ID);
        writer.writeAttribute(ComponentConstants.HTML_ATTR_NAME, ComponentUtils.getMapStateHiddenFieldId(map),
                ComponentConstants.HTML_ATTR_NAME);
        writer.writeAttribute(ComponentConstants.HTML_ATTR_TYPE, ComponentConstants.HTML_ATTR_TYPE_HIDDEN,
                ComponentConstants.HTML_ATTR_TYPE);

        if (null != mapState) {
            writer.writeAttribute(ComponentConstants.HTML_ATTR_VALUE, mapState, ComponentConstants.HTML_ATTR_VALUE);
        }

        writer.endElement(ComponentConstants.HTML_INPUT);
    }

    private void startEncodingMapWorld(FacesContext context, UIComponent component, ResponseWriter writer)
            throws IOException {

        Map map = (Map) component;

        writer.startElement(ComponentConstants.HTML_SCRIPT, component);
        writer.writeAttribute(ComponentConstants.HTML_SCRIPT_TYPE, ComponentConstants.HTML_SCRIPT_LANGUAGE,
                ComponentConstants.HTML_SCRIPT_TYPE);
        
        writer.write("var renderingScript = \"");

        declareJSVariables(context, map, writer);

        ComponentUtils.startEncodingBrowserCompatabilityChecking(context, component, writer);

        startEncodingMapRendererWrapper(context, map, writer);
    }

    private void endEncodingMapWorld(FacesContext context, UIComponent component, ResponseWriter writer)
            throws IOException {

        Map map = (Map) component;

        endEncodingMapRendererWrapper(context, map, writer);

        // determines whether to render map on window onload.
        if ("true".equals(map.getRenderOnWindowLoad())) {
            ComponentUtils.encodeJSFunctionInWindowOnLoad(writer, ComponentConstants.JS_RENDER_MAP_FUNC
                    + component.getId());
        } else {
            callMapRendererWrapper(context, component, writer);
        }

        ComponentUtils.endEncodingBrowserCompatabilityChecking(context, component, writer);

        writer.write("\";");        /* End of script variable that contains the script code. */
        writer.write("/*window.setTimeout(function() {window.eval(renderingScript);}, 10);*/ eval(renderingScript); "); /* Evaluate the script now! */
        //writer.write("/*alert('rendering script evaluated');*/ ");
        
        writer.endElement(ComponentConstants.HTML_SCRIPT);
    }    
}
