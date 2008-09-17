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
package com.googlecode.gmaps4jsf.util;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.googlecode.gmaps4jsf.component.map.GEventEncoder;
import com.googlecode.gmaps4jsf.component.map.HTMLInfoWindowEncoder;
import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.component.map.MapControlEncoder;
import com.googlecode.gmaps4jsf.component.map.MarkerEncoder;

/**
 * @author Hazem Saleh
 * @date Aug 13, 2008
 * The MapRendererUtil is a utility class for the map.
 */
public class MapRendererUtil {
	
	private static void createMapJSObject(FacesContext facesContext,
			Map mapComponent, ResponseWriter writer) throws IOException {

		// create the map object.
		writer.write("var " + ComponentConstants.JS_GMAP_BASE_VARIABLE
				+ " = new " + ComponentConstants.JS_GMAP_CORE_OBJECT
				+ "(document.getElementById(\""
				+ mapComponent.getClientId(facesContext) + "\"));");
		
		// attach properties to it.
		if (!"true".equalsIgnoreCase(mapComponent.getEnableDragging())) {
			writer.write(ComponentConstants.JS_GMAP_BASE_VARIABLE
					+ ".disableDragging();");
		}
	}
	
	private static void encodeMapType(FacesContext facesContext,
			Map mapComponent, ResponseWriter writer) throws IOException {
		
		writer.write(ComponentConstants.JS_GMAP_BASE_VARIABLE + ".setMapType("
				+ mapComponent.getType() + ");");
	}
	
	/*
	 * Completing the map rendering stuff like 
	 * (markers, notes, controls, eventHandlers' creator function...etc).
	 */
	private static void completeMapRendering(FacesContext facesContext,
			Map mapComponent, ResponseWriter writer) throws IOException {

		HTMLInfoWindowEncoder.encodeHTMLInfoWindowsFunctionScriptCall(
				facesContext, mapComponent, writer);

		MarkerEncoder.encodeMarkersFunctionScriptCall(facesContext,
				mapComponent, writer);

		encodeMapType(facesContext, mapComponent, writer);
		
		GEventEncoder.encodeEventListenersFunctionScriptCall(facesContext,
				mapComponent, writer, ComponentConstants.JS_GMAP_BASE_VARIABLE);

		MapControlEncoder.encodeMapControlsFunctionScriptCall(
				facesContext, mapComponent, writer);

		updateMapJSVariable(facesContext, mapComponent, writer);
	}	
	
	private static void updateMapJSVariable(FacesContext facesContext,
			Map mapComponent, ResponseWriter writer) throws IOException {

		if (mapComponent.getJsVariable() != null) {
			writer.write("\r\n" + mapComponent.getJsVariable() + "="
					+ ComponentConstants.JS_GMAP_BASE_VARIABLE + ";\r\n");
		}
	}

	private static void renderMapUsingLatLng(FacesContext facesContext,
			Map mapComponent, ResponseWriter writer) throws IOException {

		writer.write(ComponentConstants.JS_GMAP_BASE_VARIABLE
				+ ".setCenter(new GLatLng(" + mapComponent.getLatitude() + ", "
				+ mapComponent.getLongitude() + "), " + mapComponent.getZoom()
				+ ");");
		completeMapRendering(facesContext, mapComponent, writer);
	}	
	
	private static void renderMapUsingAddress(FacesContext facesContext, Map mapComponent,
			ResponseWriter writer) throws IOException {

		writer.write("var geocoder_" + mapComponent.getId()
				+ " = new GClientGeocoder();");

		// send XHR request to get the address location and write to the response.
		writer.write("geocoder_" + mapComponent.getId() + ".getLatLng(\""
				+ mapComponent.getAddress() + "\"," + "function(location) {\n"
				+ "if (!location) {\n" + "alert(\""
				+ mapComponent.getLocationNotFoundErrorMessage() + "\");\n"
				+ "} else {\n");
		
		writer.write(ComponentConstants.JS_GMAP_BASE_VARIABLE
				+ ".setCenter(location, " + mapComponent.getZoom() + ");\n");		
		
		completeMapRendering(facesContext, mapComponent, writer);
				
		writer.write("}" + "}\n" + ");\n");
	}	
	
	public static void renderMap(FacesContext facesContext, Map mapComponent,
			ResponseWriter writer) throws IOException {

		createMapJSObject(facesContext, mapComponent, writer);
		
		if (mapComponent.getAddress() == null) {

			renderMapUsingLatLng(facesContext, mapComponent, writer);

		} else {

			renderMapUsingAddress(facesContext, mapComponent, writer);
		}
	}
}
