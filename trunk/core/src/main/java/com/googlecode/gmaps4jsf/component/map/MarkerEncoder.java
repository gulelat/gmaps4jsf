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

import com.googlecode.gmaps4jsf.component.geventlistener.GEventListener;
import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.component.marker.Marker;
import com.googlecode.gmaps4jsf.util.ComponentConstants;

/**
 * @author Hazem Saleh
 * @date Jul 31, 2008
 * The MarkerEncoder is used for encoding the map markers.
 */
public class MarkerEncoder {

	private static void encodeMarker(FacesContext facesContext,
			Map mapComponent, Marker marker, ResponseWriter writer)
			throws IOException {

		String longitude;
		String latitude;
		String markerOptions;

		// encode marker script.
		if (marker.getLatitude() != null) {
			latitude = marker.getLatitude();
		} else {
			latitude = ComponentConstants.JS_GMAP_BASE_VARIABLE
					+ ".getCenter().lat()";
		}

		if (marker.getLongitude() != null) {
			longitude = marker.getLongitude();
		} else {
			longitude = ComponentConstants.JS_GMAP_BASE_VARIABLE
					+ ".getCenter().lng()";
		}

		// check dragability.
		if ("true".equalsIgnoreCase(marker.getDraggable())) {
			markerOptions = "{draggable: true}";
		} else {
			markerOptions = "{draggable: false}";		
		}		
		
		// construct the marker.
		writer.write("var marker_" + marker.getId() + " = new "
				+ ComponentConstants.JS_GMarker_OBJECT + "(new "
				+ ComponentConstants.JS_GLatLng_OBJECT + "(" + latitude + ", "
				+ longitude + ")," + markerOptions + ");");

		writer.write(ComponentConstants.JS_GMAP_BASE_VARIABLE
				+ ".addOverlay(marker_" + marker.getId() + ");");
		
		// encode marker events.
		for (Iterator iterator = marker.getChildren().iterator(); iterator
				.hasNext();) {
			UIComponent component = (UIComponent) iterator.next();

			if (component instanceof GEventListener) {
				GEventEncoder.encodeEventListenersFunctionScript(facesContext,
						marker, writer, "marker_" + marker.getId());
				GEventEncoder.encodeEventListenersFunctionScriptCall(
						facesContext, marker, writer, "marker_"
								+ marker.getId());
			}
		}		
		
		// update marker user variable.
		updateMarkerJSVariable(facesContext, marker, writer);
	}
	
	private static void updateMarkerJSVariable(FacesContext facesContext,
			Marker marker, ResponseWriter writer) throws IOException {

		if (marker.getJsVariable() != null) {
			writer.write("\r\n" + marker.getJsVariable() + " = " + "marker_"
					+ marker.getId() + ";\r\n");
		}
	}	
	
	public static void encodeMarkersFunctionScript(FacesContext facesContext,
			Map mapComponent, ResponseWriter writer) throws IOException {

		writer.write("function "
				+ ComponentConstants.JS_CREATE_MARKERS_FUNCTION_PREFIX
				+ mapComponent.getId() + "("
				+ ComponentConstants.JS_GMAP_BASE_VARIABLE + ") {");
		for (Iterator iterator = mapComponent.getChildren().iterator(); iterator
				.hasNext();) {
			UIComponent component = (UIComponent) iterator.next();

			if (component instanceof Marker) {
				encodeMarker(facesContext, mapComponent, (Marker) component,
						writer);
			}
		}
		writer.write("}");
	}
	
	public static void encodeMarkersFunctionScriptCall(
			FacesContext facesContext, Map mapComponent, ResponseWriter writer)
			throws IOException {

		writer.write(ComponentConstants.JS_CREATE_MARKERS_FUNCTION_PREFIX
						+ mapComponent.getId()
						+ "("
						+ ComponentConstants.JS_GMAP_BASE_VARIABLE + ");");
	}
}
