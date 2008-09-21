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

import com.googlecode.gmaps4jsf.component.eventlistener.EventListener;
import com.googlecode.gmaps4jsf.component.point.Point;
import com.googlecode.gmaps4jsf.component.polyline.Polyline;
import com.googlecode.gmaps4jsf.util.ComponentConstants;

/**
 * @author Hazem Saleh
 * @date September 20, 2008
 * The PolylineEncoder is used for encoding the map polylines.
 */
public class PolylineEncoder {

	private static void encodePolyline(FacesContext facesContext,
			Map mapComponent, Polyline polyline, ResponseWriter writer)
			throws IOException {

		String polyOptionsStr = "";
		String polyLinesStr = "";

		// Go through all the lines and consolidate them.
		for (Iterator iterator = polyline.getChildren().iterator(); iterator
				.hasNext();) {
			UIComponent component = (UIComponent) iterator.next();

			if (component instanceof Point) {

				Point point = (Point) component;

				if (!polyLinesStr.equals("")) {
					polyLinesStr += ",";
				}

				polyLinesStr += "new GLatLng(" + point.getLatitude() + ", "
						+ point.getLongitude() + ")";
			}
		}

		// Is a Geodesic polyline.
		if ("true".equalsIgnoreCase(polyline.getGeodesic())) {
			polyOptionsStr = "{geodesic:true}";
		} else {
			polyOptionsStr = "{geodesic:false}";
		}

		// encode the polyline.
		writer.write("var polyline_" + polyline.getId() + " = new "
				+ ComponentConstants.JS_GPolyline_OBJECT + "([" + polyLinesStr
				+ "], \"" + polyline.getHexaColor() + "\", "
				+ polyline.getLineWidth() + "," + polyline.getOpacity() + ", "
				+ polyOptionsStr + ");");

		writer.write(ComponentConstants.JS_GMAP_BASE_VARIABLE
				+ ".addOverlay(polyline_" + polyline.getId() + ");");
		
		// encode polyline events.
		for (Iterator iterator = polyline.getChildren().iterator(); iterator
				.hasNext();) {
			UIComponent component = (UIComponent) iterator.next();

			if (component instanceof EventListener) {
				EventEncoder.encodeEventListenersFunctionScript(facesContext,
						polyline, writer, "polyline_" + polyline.getId());
				EventEncoder.encodeEventListenersFunctionScriptCall(
						facesContext, polyline, writer, "polyline_"
								+ polyline.getId());
			}
		}			
		
		// update polyline user variable.
		updatePolylineJSVariable(facesContext, polyline, writer);
	}
	
	private static void updatePolylineJSVariable(FacesContext facesContext,
			Polyline polyline, ResponseWriter writer) throws IOException {

		if (polyline.getJsVariable() != null) {
			writer.write("\r\n" + polyline.getJsVariable() + " = " + "polyline_"
					+ polyline.getId() + ";\r\n");
		}
	}	
	
	public static void encodePolylinesFunctionScript(FacesContext facesContext,
			Map mapComponent, ResponseWriter writer) throws IOException {

		writer.write("function "
				+ ComponentConstants.JS_CREATE_POLYLINES_FUNCTION_PREFIX
				+ mapComponent.getId() + "("
				+ ComponentConstants.JS_GMAP_BASE_VARIABLE + ") {");
		for (Iterator iterator = mapComponent.getChildren().iterator(); iterator
				.hasNext();) {
			UIComponent component = (UIComponent) iterator.next();

			if (component instanceof Polyline) {
				encodePolyline(facesContext, mapComponent,
						(Polyline) component, writer);
			}
		}
		writer.write("}");
	}
	
	public static void encodePolylinesFunctionScriptCall(
			FacesContext facesContext, Map mapComponent, ResponseWriter writer)
			throws IOException {

		writer.write(ComponentConstants.JS_CREATE_POLYLINES_FUNCTION_PREFIX
				+ mapComponent.getId() + "("
				+ ComponentConstants.JS_GMAP_BASE_VARIABLE + ");");
	}
}
