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

import com.googlecode.gmaps4jsf.component.htmlInformationWindow.HTMLInformationWindow;
import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.component.marker.Marker;
import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.util.ComponentUtils;
import com.googlecode.gmaps4jsf.util.HTMLInfoWindowRendererUtil;
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

		writer.write("if (GBrowserIsCompatible()) {\n");

		encodeMap(facesContext, mapComponent, writer);

		encodeMarkers(facesContext, mapComponent, writer);

		encodeHTMLInfoWindows(facesContext, mapComponent, writer);

		writer.write("}\n");
		writer.endElement(ComponentConstants.HTML_SCRIPT);
	}

	private void encodeHTMLInfoWindows(FacesContext facesContext,
			Map mapComponent, ResponseWriter writer) throws IOException {

		for (Iterator iterator = mapComponent.getChildren().iterator(); iterator
				.hasNext();) {
			UIComponent component = (UIComponent) iterator.next();

			if (component instanceof HTMLInformationWindow) {
				HTMLInfoWindowRendererUtil
						.encodeMarker(facesContext, mapComponent,
								(HTMLInformationWindow) component, writer);
			}
		}
	}

	private void encodeMarkers(FacesContext facesContext, Map mapComponent,
			ResponseWriter writer) throws IOException {

		for (Iterator iterator = mapComponent.getChildren().iterator(); iterator
				.hasNext();) {
			UIComponent component = (UIComponent) iterator.next();

			if (component instanceof Marker) {
				MarkerRendererUtil.encodeMarker(facesContext, mapComponent,
						(Marker) component, writer);
			}
		}
	}

	private void encodeMap(FacesContext facesContext, Map mapComponent,
			ResponseWriter writer) throws IOException {

		writer.write("var " + ComponentConstants.JS_GMAP_BASE_VARIABLE
				+ " = new " + ComponentConstants.JS_GMAP_CORE_OBJECT
				+ "(document.getElementById(\""
				+ mapComponent.getClientId(facesContext) + "\"));");

		writer.write(ComponentConstants.JS_GMAP_BASE_VARIABLE
				+ ".setCenter(new GLatLng(" + mapComponent.getLatitude() + ", "
				+ mapComponent.getLongitude() + "), " + mapComponent.getZoom()
				+ ");");

		writer.write(ComponentConstants.JS_GMAP_BASE_VARIABLE + ".setMapType("
				+ mapComponent.getType() + ");");
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