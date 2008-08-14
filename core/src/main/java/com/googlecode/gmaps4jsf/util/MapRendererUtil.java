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

import com.googlecode.gmaps4jsf.component.map.Map;

/**
 * @author Hazem Saleh
 * @date Aug 13, 2008
 * The MapRendererUtil is a utility class for the map.
 */
public class MapRendererUtil {
	
	public static void createMap(FacesContext facesContext, Map mapComponent,
			ResponseWriter writer) throws IOException {

		writer.write("var " + ComponentConstants.JS_GMAP_BASE_VARIABLE
				+ " = new " + ComponentConstants.JS_GMAP_CORE_OBJECT
				+ "(document.getElementById(\""
				+ mapComponent.getClientId(facesContext) + "\"));");		
	}
	
	/**
	 * The (getMapPointersJSCode) is used for generating the map controls JS
	 * code. Pointers means  (Markers and HTMLInfoWindows);
	 */
	public static String getMapPointersJSCode(Map mapComponent) {
		
		String output = "";
		output += "createHTMLInfoWindowsFunction" + mapComponent.getId()
				+ "();";
		output += "createMarkerFunction" + mapComponent.getId() + "();";
		output += ComponentConstants.JS_GMAP_BASE_VARIABLE + ".setMapType("
				+ mapComponent.getType() + ");";

		return output;
	}

	public static void renderMap(FacesContext facesContext, Map mapComponent,
			ResponseWriter writer) throws IOException {

		if (mapComponent.getAddress() == null) {

			// if address doesnot exist then use longitude and latitude. 			
			writer.write(ComponentConstants.JS_GMAP_BASE_VARIABLE
					+ ".setCenter(new GLatLng(" + mapComponent.getLatitude()
					+ ", " + mapComponent.getLongitude() + "), "
					+ mapComponent.getZoom() + ");");
			writer.write(getMapPointersJSCode(mapComponent));
		} else {

			// use the GClientGeocoder service to get the longitude and latitude. 
			GClientGeocoderUtil.renderMapXHR(facesContext, mapComponent,
					writer);
		}		
	}
}
