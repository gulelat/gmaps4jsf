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

import com.googlecode.gmaps4jsf.component.marker.Marker;
import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.util.ComponentUtils;
import com.googlecode.gmaps4jsf.util.HTMLInfoWindowRendererUtil;
import com.googlecode.gmaps4jsf.util.MapRendererUtil;
import com.googlecode.gmaps4jsf.util.MarkerRendererUtil;

/**
 * @author Hazem Saleh
 * @date Jul 13, 2008
 * last modified at Jul 31, 2008 
 * The (MapRenderer) renders a google map with all its children 
 * (markers, informationWindows ...etc).
 */
public class MapRenderer extends Renderer {

	private void encodeScripts(FacesContext facesContext,
			UIComponent component, ResponseWriter writer) throws IOException {

		Map mapComponent = (Map) component;

		writer.startElement(ComponentConstants.HTML_SCRIPT, component);
		
		declareJSVariables(facesContext, mapComponent, writer);

		startEncodingBrowserCompatabilityChecking(facesContext, component,
				writer);
		
		encodeMapRendererWrapper(facesContext, mapComponent, writer);
		
		// determines whether to render map on window onload.
		if ("true".equals(mapComponent.getRenderOnWindowLoad())) {
			injectMapCodeInWindowOnLoad(facesContext, component, writer);
		} else {
			callMapRendererWrapper(facesContext, component, writer);
		}

		endEncodingBrowserCompatabilityChecking(facesContext, component, writer);

		writer.endElement(ComponentConstants.HTML_SCRIPT);
	}

	/*
	 * Declare the JS variables for the map and its related objects 
	 * such as markers.
	 */
	private void declareJSVariables(FacesContext facesContext, Map map,
			ResponseWriter writer) throws IOException {

		// declare the map and markers variables.
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
			}
		}
	}

	private void startEncodingBrowserCompatabilityChecking(
			FacesContext facesContext, UIComponent component,
			ResponseWriter writer) throws IOException {
		
		writer.write("if (" + ComponentConstants.JS_GBrowserIsCompatible_OBJECT
				+ "()) {");
	}
	
	/*
	 * This is the core method that is responsible for the whole map rendering.
	 * @param facesContext
	 * @param mapComponent
	 * @param writer
	 * @throws IOException
	 */
	private void encodeMapRendererWrapper(FacesContext facesContext,
			Map mapComponent, ResponseWriter writer) throws IOException {

		writer.write("function " + ComponentConstants.JS_RENDER_MAP_FUNC + mapComponent.getId()
				+ "(){");
		
		MarkerRendererUtil.encodeMarkersFunctionScript(facesContext,
				mapComponent, writer);

		HTMLInfoWindowRendererUtil.encodeHTMLInfoWindowsFunctionScript(
				facesContext, mapComponent, writer);

		encodeMap(facesContext, mapComponent, writer);
		
		writer.write("}");		
	}
	
	/*
	 * The injectMapCodeInWindowOnLoad method is used for injecting the map code 
	 * executes in the window onload (IE will complain without this method). 
	 */
	private void injectMapCodeInWindowOnLoad(
			FacesContext facesContext, UIComponent component,
			ResponseWriter writer) throws IOException {

		 String injectMapCodeInWindowOnLoadScript = "\r\n// Inject code on the load of the window\r\n"
				+ "var oldonload = window.onload;\r\n"
				+ "if (typeof window.onload != 'function') {\r\n"
				+ "window.onload = "
				+ ComponentConstants.JS_RENDER_MAP_FUNC
				+ component.getId()
				+ ";\r\n"
				+ "} else {\r\n"
				+ "window.onload = function() {\r\n"
				+ "if (oldonload) {\r\n"
				+ "oldonload();\r\n"
				+ "}\r\n"
				+ ComponentConstants.JS_RENDER_MAP_FUNC
				+ component.getId()
				+ "();\r\n" + "}\r\n" + "}\r\n";
		 
		 writer.write(injectMapCodeInWindowOnLoadScript);
	}	
	
	private void callMapRendererWrapper(FacesContext facesContext,
			UIComponent component, ResponseWriter writer) throws IOException {

		writer.write(ComponentConstants.JS_RENDER_MAP_FUNC + component.getId()
				+ "();\r\n");
	}		

	private void endEncodingBrowserCompatabilityChecking(
			FacesContext facesContext, UIComponent component,
			ResponseWriter writer) throws IOException {
		
		writer.write("}");
	}

	private void encodeMap(FacesContext facesContext, Map mapComponent,
			ResponseWriter writer) throws IOException {

		MapRendererUtil.renderMap(facesContext, mapComponent, writer);
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

	public void encodeBegin(FacesContext context, UIComponent component)
			throws IOException {
	}

	public void encodeEnd(FacesContext context, UIComponent component)
			throws IOException {

		ComponentUtils.assertValidContext(context);

		ResponseWriter writer = context.getResponseWriter();

		encodeHTMLModel(context, component, writer);

		encodeScripts(context, component, writer);
	}

	public void decode(FacesContext context, UIComponent component) {
	}
}