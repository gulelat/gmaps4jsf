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

import com.googlecode.gmaps4jsf.component.line.Line;
import com.googlecode.gmaps4jsf.component.polygon.Polygon;
import com.googlecode.gmaps4jsf.util.ComponentConstants;

/**
 * @author Hazem Saleh
 * @date September 20, 2008
 * The PolygonEncoder is used for encoding the map polygons.
 */
public class PolygonEncoder {

	private static void encodePolygon(FacesContext facesContext,
			Map mapComponent, Polygon polygon, ResponseWriter writer)
			throws IOException {

		String polygonLinesStr = "";

		// Go through all the lines and consolidate them.
		for (Iterator iterator = polygon.getChildren().iterator(); iterator
				.hasNext();) {
			UIComponent component = (UIComponent) iterator.next();

			if (component instanceof Line) {

				Line line = (Line) component;

				if (!polygonLinesStr.equals("")) {
					polygonLinesStr += ",";
				}

				polygonLinesStr += "new GLatLng(" + line.getLatitude() + ", "
						+ line.getLongitude() + ")";
			}
		}

		// encode the polygon.
		writer.write("var polygon = new "
				+ ComponentConstants.JS_GPolygon_OBJECT + "(["
				+ polygonLinesStr + "], \"" + polygon.getHexStrokeColor()
				+ "\", " + polygon.getLineWidth() + ","
				+ polygon.getStrokeOpacity() + ", \""
				+ polygon.getHexFillColor() + "\", " + polygon.getFillOpacity()
				+ ");");

		writer.write(ComponentConstants.JS_GMAP_BASE_VARIABLE
				+ ".addOverlay(polygon);");
	}
	
	public static void encodePolygonsFunctionScript(FacesContext facesContext,
			Map mapComponent, ResponseWriter writer) throws IOException {

		writer.write("function "
				+ ComponentConstants.JS_CREATE_POLYGONS_FUNCTION_PREFIX
				+ mapComponent.getId() + "("
				+ ComponentConstants.JS_GMAP_BASE_VARIABLE + ") {");
		for (Iterator iterator = mapComponent.getChildren().iterator(); iterator
				.hasNext();) {
			UIComponent component = (UIComponent) iterator.next();

			if (component instanceof Polygon) {
				encodePolygon(facesContext, mapComponent,
						(Polygon) component, writer);
			}
		}
		writer.write("}");
	}
	
	public static void encodePolygonsFunctionScriptCall(
			FacesContext facesContext, Map mapComponent, ResponseWriter writer)
			throws IOException {

		writer.write(ComponentConstants.JS_CREATE_POLYGONS_FUNCTION_PREFIX
				+ mapComponent.getId() + "("
				+ ComponentConstants.JS_GMAP_BASE_VARIABLE + ");");
	}
}
