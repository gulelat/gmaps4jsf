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

/**
 * @author Hazem Saleh
 * @date Jul 13, 2008
 */
public class MapRenderer extends Renderer {

	private void encodeScripts(FacesContext facesContext,
			UIComponent component, ResponseWriter writer) throws IOException {

		Map mapComponent = (Map) component;

		writer.startElement("script", component);

		writer.write("if (GBrowserIsCompatible()) {\n");
		writer.write("var map = new GMap2(document.getElementById(\""
				+ mapComponent.getClientId(facesContext) + "\"));");

		writer.write("map.setCenter(new GLatLng("
				+ mapComponent.getLatitude() + ", "
				+ mapComponent.getLongitude() + "), 11);");

		writer.write("map.setMapType(G_HYBRID_MAP);");

		writer.write("}\n");
		writer.endElement("script");
	}

	private void assertValidContext(FacesContext context) {
		if(context == null) {
			throw new NullPointerException("context cannot be null");
		}
	}

	private void encodeHTMLModel(FacesContext facesContext,
			UIComponent component, ResponseWriter writer) throws IOException {

		Map mapComponent = (Map) component;

		writer.startElement("DIV", mapComponent);

		writer.writeAttribute("id", mapComponent.getClientId(facesContext),
				"id");
		writer.writeAttribute("name", mapComponent.getClientId(facesContext),
				"name");
		writer.writeAttribute("style", "width: " + mapComponent.getWidth()
				+ "; height: " + mapComponent.getHeight(), "style");

		writer.endElement("DIV");

	}


	public void encodeBegin(FacesContext context,
							UIComponent component) throws IOException {
	}

	public void encodeEnd(FacesContext context,
						  UIComponent component) throws IOException {
		assertValidContext(context);

		ResponseWriter writer = context.getResponseWriter();

		encodeHTMLModel(context, component, writer);

		encodeScripts(context, component, writer);
	}

	public void decode(FacesContext context,
					   UIComponent component) {
	}
}