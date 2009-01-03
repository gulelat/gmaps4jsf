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
import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.util.ComponentUtils;
import com.googlecode.gmaps4jsf.util.MapRendererUtil;

/**
 * @author Hazem Saleh
 * @date Jul 13, 2008
 * last modified at Jul 31, 2008 
 * The (MapRenderer) renders a google map with all its children 
 * (markers, informationWindows, polygons, polylines, ...etc).
 */
public class MapRenderer extends Renderer {

	/*
	 * Declare the JS variables for the map and its related objects 
	 * such as markers, polygons, polylines ...
	 */
	private void declareJSVariables(FacesContext facesContext, Map map,
			ResponseWriter writer) throws IOException {

		if (map.getJsVariable() != null) {
			writer.write("\r\n var " + map.getJsVariable() + ";");
		}

		for (Iterator iterator = map.getChildren().iterator(); iterator
				.hasNext();) {
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
					writer.write("\r\n var " + groundOverlay.getJsVariable() + ";");
				}
			}		
		}
	}
	
	/*
	 * This is the core method that is responsible for the whole map rendering.
	 * It encodes the scripts that would be used for rendering the map objects.
	 * @param facesContext
	 * @param mapComponent
	 * @param writer
	 * @throws IOException
	 */
	private void startEncodingMapRendererWrapper(FacesContext facesContext,
			Map mapComponent, ResponseWriter writer) throws IOException {

		writer.write("function " + ComponentConstants.JS_RENDER_MAP_FUNC
				+ mapComponent.getId() + "(){");

		HTMLInfoWindowEncoder.encodeHTMLInfoWindowsFunctionScript(
				facesContext, mapComponent, writer);

		MapControlEncoder.encodeMapControlsFunctionScript(facesContext,
				mapComponent, writer);
		
		EventEncoder.encodeEventListenersFunctionScript(facesContext,
				mapComponent, writer, ComponentConstants.JS_GMAP_BASE_VARIABLE);
		
		PolylineEncoder.encodePolylinesFunctionScript(facesContext,
				mapComponent, writer);
		
		PolygonEncoder.encodePolygonsFunctionScript(facesContext, mapComponent,
				writer);
		
		GroundOverlayEncoder.encodeGroundOverlaysFunctionScript(facesContext,
				mapComponent, writer);
		
		MapRendererUtil.startEncodingMapScript(facesContext, mapComponent, writer);		
	}
	
	private void endEncodingMapRendererWrapper(FacesContext facesContext,
			Map mapComponent, ResponseWriter writer) throws IOException {
		
		MapRendererUtil.endEncodingMapScript(facesContext, mapComponent, writer);	

		writer.write("}");		
	}
	
	private void callMapRendererWrapper(FacesContext facesContext,
			UIComponent component, ResponseWriter writer) throws IOException {

		writer.write(ComponentConstants.JS_RENDER_MAP_FUNC + component.getId()
				+ "();\r\n");
	}		

	private void encodeHTMLModel(FacesContext facesContext,
			UIComponent component, ResponseWriter writer) throws IOException {

		Map mapComponent = (Map) component;

		writer.startElement(ComponentConstants.HTML_DIV, mapComponent);

		writer.writeAttribute(ComponentConstants.HTML_ATTR_ID, mapComponent
				.getClientId(facesContext), ComponentConstants.HTML_ATTR_ID);
		writer.writeAttribute(ComponentConstants.HTML_ATTR_NAME, mapComponent
				.getClientId(facesContext), ComponentConstants.HTML_ATTR_NAME);
		writer.writeAttribute(ComponentConstants.HTML_ATTR_STYLE, "width: "
				+ mapComponent.getWidth() + "; height: "
				+ mapComponent.getHeight(), ComponentConstants.HTML_ATTR_STYLE);

		writer.endElement(ComponentConstants.HTML_DIV);
	}
	
	private void startEncodingMapWorld(FacesContext context,
			UIComponent component, ResponseWriter writer) throws IOException {

		Map map = (Map) component;

		writer.startElement(ComponentConstants.HTML_SCRIPT, component);

		declareJSVariables(context, map, writer);

		ComponentUtils.startEncodingBrowserCompatabilityChecking(context,
				component, writer);

		startEncodingMapRendererWrapper(context, map, writer);
	}
	
	private void endEncodingMapWorld(FacesContext context,
			UIComponent component, ResponseWriter writer) throws IOException {

		Map mapComponent = (Map) component;

		endEncodingMapRendererWrapper(context, mapComponent, writer);

		// determines whether to render map on window onload.
		if ("true".equals(mapComponent.getRenderOnWindowLoad())) {
			ComponentUtils.encodeJSFunctionInWindowOnLoad(writer,
					ComponentConstants.JS_RENDER_MAP_FUNC + component.getId());
		} else {
			callMapRendererWrapper(context, component, writer);
		}

		ComponentUtils.endEncodingBrowserCompatabilityChecking(context,
				component, writer);

		writer.endElement(ComponentConstants.HTML_SCRIPT);
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
}