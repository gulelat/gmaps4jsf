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
import com.googlecode.gmaps4jsf.util.ComponentUtils;
import com.googlecode.gmaps4jsf.util.MapRendererUtil;

public class MapRenderer extends Renderer {

    public MapRenderer() {
    }

    private void declareJSVariables(FacesContext facesContext, Map map,
	    ResponseWriter writer) throws IOException {
	
	if (map.getJsVariable() != null) {
	    writer.write("\r\n var " + map.getJsVariable() + ";");
	}
	
	Iterator iterator = map.getChildren().iterator();
	
	do {
	    if (!iterator.hasNext()) {
		break;
	    }
	    
	    UIComponent component = (UIComponent) iterator.next();
	    
	    if (component instanceof Marker) {
		Marker marker = (Marker) component;
		
		if (marker.getJsVariable() != null) {
		    writer.write("\r\n var " + marker.getJsVariable() + ";");
		}
	    } else if (component instanceof Polyline) {
		Polyline polyline = (Polyline) component;
		
		if (polyline.getJsVariable() != null) {
		    writer.write("\r\n var " + polyline.getJsVariable() + ";");
		}
	    } else if (component instanceof Polygon) {
		Polygon polygon = (Polygon) component;
		
		if (polygon.getJsVariable() != null) {
		    writer.write("\r\n var " + polygon.getJsVariable() + ";");
		}
	    } else if (component instanceof GroundOverlay) {
		GroundOverlay groundOverlay = (GroundOverlay) component;
		
		if (groundOverlay.getJsVariable() != null) {
		    writer.write("\r\n var " + groundOverlay.getJsVariable()
			    + ";");
		}
	    }
	} while (true);
    }

    private void startEncodingMapRendererWrapper(FacesContext facesContext,
	    Map mapComponent, ResponseWriter writer) throws IOException {
	
	writer.write("function renderMap" + mapComponent.getId() + "(){");
	
	HTMLInfoWindowEncoder.encodeHTMLInfoWindowsFunctionScript(facesContext,
		mapComponent, writer);
	MapControlEncoder.encodeMapControlsFunctionScript(facesContext,
		mapComponent, writer);
	EventEncoder.encodeEventListenersFunctionScript(facesContext,
		mapComponent, writer, "map_base_variable");
	PolylineEncoder.encodePolylinesFunctionScript(facesContext,
		mapComponent, writer);
	PolygonEncoder.encodePolygonsFunctionScript(facesContext, mapComponent,
		writer);
	GroundOverlayEncoder.encodeGroundOverlaysFunctionScript(facesContext,
		mapComponent, writer);
	MapRendererUtil.startEncodingMapScript(facesContext, mapComponent,
		writer);
    }

    private void endEncodingMapRendererWrapper(FacesContext facesContext,
	    Map mapComponent, ResponseWriter writer) throws IOException {

	MapRendererUtil
		.endEncodingMapScript(facesContext, mapComponent, writer);
	writer.write("}");
    }

    private void callMapRendererWrapper(FacesContext facesContext,
	    UIComponent component, ResponseWriter writer) throws IOException {
	writer.write("renderMap" + component.getId() + "();\r\n");
    }

    private void encodeHTMLModel(FacesContext context, UIComponent component,
	    ResponseWriter writer) throws IOException {
	
	Map map = (Map) component;
	
	writer.startElement("DIV", map);
	writer.writeAttribute("id", map.getClientId(context), "id");
	writer.writeAttribute("name", map.getClientId(context), "name");
	writer.writeAttribute("style", "width: " + map.getWidth()
		+ "; height: " + map.getHeight(), "style");
	writer.endElement("DIV");
	writer.startElement("input", map);
	writer.writeAttribute("id", ComponentUtils
		.getMapStateHiddenFieldId(map), "id");
	writer.writeAttribute("name", ComponentUtils
		.getMapStateHiddenFieldId(map), "name");
	writer.writeAttribute("type", "hidden", "type");
	writer.writeAttribute("value", 
			     ComponentUtils.getValueToRender(context, map), 
			     "value");
	writer.endElement("input");
    }

    private void startEncodingMapWorld(FacesContext context,
	    UIComponent component, ResponseWriter writer) throws IOException {
	
	Map map = (Map) component;
	
	writer.startElement("script", component);
	declareJSVariables(context, map, writer);
	ComponentUtils.startEncodingBrowserCompatabilityChecking(context,
		component, writer);
	startEncodingMapRendererWrapper(context, map, writer);
    }

    private void endEncodingMapWorld(FacesContext context,
	    UIComponent component, ResponseWriter writer) throws IOException {
	
	Map mapComponent = (Map) component;
	
	endEncodingMapRendererWrapper(context, mapComponent, writer);
	
	if ("true".equals(mapComponent.getRenderOnWindowLoad())) {
	    ComponentUtils.encodeJSFunctionInWindowOnLoad(writer, "renderMap"
		    + component.getId());
	} else {
	    callMapRendererWrapper(context, component, writer);
	}
	
	ComponentUtils.endEncodingBrowserCompatabilityChecking(context,
		component, writer);
	writer.endElement("script");
    }

    public boolean getRendersChildren() {
	return true;
    }

    public void encodeBegin(FacesContext context, UIComponent component)
	    throws IOException {
	
	ComponentUtils.assertValidContext(context);
	ResponseWriter writer = context.getResponseWriter();
	encodeHTMLModel(context, component, writer);
	startEncodingMapWorld(context, component, writer);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
	    throws IOException {
	
	ComponentUtils.assertValidContext(context);
	ResponseWriter writer = context.getResponseWriter();
	endEncodingMapWorld(context, component, writer);
    }

    public void decode(FacesContext context, UIComponent component) {	
	Map map = (Map) component;
	String submittedValue = (String) context.getExternalContext()
		.getRequestParameterMap()
		.get(ComponentUtils.getMapStateHiddenFieldId(map));
	
	map.setSubmittedValue(submittedValue);
    }
}