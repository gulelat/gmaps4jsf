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

import com.googlecode.gmaps4jsf.component.map.EventEncoder;
import com.googlecode.gmaps4jsf.component.map.GroundOverlayEncoder;
import com.googlecode.gmaps4jsf.component.map.HTMLInfoWindowEncoder;
import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.component.map.MapControlEncoder;
import com.googlecode.gmaps4jsf.component.map.PolygonEncoder;
import com.googlecode.gmaps4jsf.component.map.PolylineEncoder;

public class MapRendererUtil {

    public MapRendererUtil() {
    }

    private static void createMapJSObject(FacesContext facesContext,
	    Map mapComponent, ResponseWriter writer) throws IOException {
	
	writer.write("var map_base_variable = new GMap2(document.getElementById(\""
			+ mapComponent.getClientId(facesContext) + "\"));");
	
	if (!"true".equalsIgnoreCase(mapComponent.getEnableDragging())) {
	    writer.write("map_base_variable.disableDragging();");
	}
    }

    private static void encodeMapType(FacesContext facesContext,
	    Map mapComponent, ResponseWriter writer) throws IOException {
	
	writer.write("map_base_variable.setMapType(" + mapComponent.getType()
		+ ");");
    }

    private static void encodeMapStreetOverlay(FacesContext facesContext,
	    Map mapComponent, ResponseWriter writer) throws IOException {
	
	if ("true".equalsIgnoreCase(mapComponent.getAddStreetOverlay())) {
	    writer.write("map_base_variable.addOverlay(new GStreetviewOverlay()) ;");
	}
    }

    private static void completeMapRendering(FacesContext facesContext,
	    Map mapComponent, ResponseWriter writer) throws IOException {
	
	HTMLInfoWindowEncoder.encodeHTMLInfoWindowsFunctionScriptCall(
		facesContext, mapComponent, writer);
	PolylineEncoder.encodePolylinesFunctionScriptCall(facesContext,
		mapComponent, writer);
	PolygonEncoder.encodePolygonsFunctionScriptCall(facesContext,
		mapComponent, writer);
	encodeMapType(facesContext, mapComponent, writer);
	encodeMapStreetOverlay(facesContext, mapComponent, writer);
	EventEncoder.encodeEventListenersFunctionScriptCall(facesContext,
		mapComponent, writer, "map_base_variable");
	MapControlEncoder.encodeMapControlsFunctionScriptCall(facesContext,
		mapComponent, writer);
	GroundOverlayEncoder.encodeGroundOverlaysFunctionScriptCall(
		facesContext, mapComponent, writer);
	updateMapJSVariable(facesContext, mapComponent, writer);
    }

    private static void updateMapJSVariable(FacesContext facesContext,
	    Map mapComponent, ResponseWriter writer) throws IOException {
	
	if (mapComponent.getJsVariable() != null) {
	    writer.write("\r\n" + mapComponent.getJsVariable() + "="
		    + "map_base_variable" + ";\r\n");
	}
    }

    private static void renderMapUsingLatLng(FacesContext facesContext,
	    Map mapComponent, ResponseWriter writer) throws IOException {
	
	writer.write("map_base_variable.setCenter(new GLatLng("
		+ mapComponent.getLatitude() + ", "
		+ mapComponent.getLongitude() + "), " + mapComponent.getZoom()
		+ ");");
	completeMapRendering(facesContext, mapComponent, writer);
    }

    private static void startRenderingMapUsingAddress(
	    FacesContext facesContext, Map mapComponent, ResponseWriter writer)
	    throws IOException {
	
	writer.write("var geocoder_" + mapComponent.getId() + " = new "
		+ "GClientGeocoder" + "();");
	writer.write("geocoder_" + mapComponent.getId() + ".getLatLng(\""
		+ mapComponent.getAddress() + "\"," + "function(location) {\n"
		+ "if (!location) {\n" + "alert(\""
		+ mapComponent.getLocationNotFoundErrorMessage() + "\");\n"
		+ "} else {\n");
	writer.write("map_base_variable.setCenter(location, "
		+ mapComponent.getZoom() + ");\n");
	completeMapRendering(facesContext, mapComponent, writer);
    }

    private static void endRenderingMapUsingAddress(FacesContext facesContext,
	    Map mapComponent, ResponseWriter writer) throws IOException {
	
	writer.write("}}\n);\n");
    }

    public static void startEncodingMapScript(FacesContext facesContext,
	    Map mapComponent, ResponseWriter writer) throws IOException {
	
	createMapJSObject(facesContext, mapComponent, writer);
	if (mapComponent.getAddress() == null) {
	    renderMapUsingLatLng(facesContext, mapComponent, writer);
	} else {
	    startRenderingMapUsingAddress(facesContext, mapComponent, writer);
	}
    }

    public static void endEncodingMapScript(FacesContext facesContext,
	    Map mapComponent, ResponseWriter writer) throws IOException {
	
	if (mapComponent.getAddress() != null) {
	    endRenderingMapUsingAddress(facesContext, mapComponent, writer);
	}
    }
}