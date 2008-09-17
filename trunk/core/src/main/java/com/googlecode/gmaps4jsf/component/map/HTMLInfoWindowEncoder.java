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

import com.googlecode.gmaps4jsf.component.htmlInformationWindow.HTMLInformationWindow;
import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.util.ComponentConstants;

/**
 * @author Hazem Saleh
 * @date Jul 31, 2008
 * The HTMLInfoWindowEncoder is used for encpding the map info windows.
 */
public class HTMLInfoWindowEncoder {

	private static void encodeHTMLInfoWindow(FacesContext facesContext,
			Map mapComponent, HTMLInformationWindow window,
			ResponseWriter writer) throws IOException {

		String longitude;
		String latitude;

		if (window.getLatitude() != null) {
			latitude = window.getLatitude();
		} else {
			latitude = ComponentConstants.JS_GMAP_BASE_VARIABLE
					+ ".getCenter().lat()";
		}

		if (window.getLongitude() != null) {
			longitude = window.getLongitude();
		} else {
			longitude = ComponentConstants.JS_GMAP_BASE_VARIABLE
					+ ".getCenter().lng()";
		}

		writer.write(ComponentConstants.JS_GMAP_BASE_VARIABLE
				+ ".openInfoWindowHtml(new GLatLng(" + latitude + ", "
				+ longitude + "), \"" + window.getHtmlText() + "\");");
	}
	
	public static void encodeHTMLInfoWindowsFunctionScript(FacesContext facesContext,
			Map mapComponent, ResponseWriter writer) throws IOException {

		writer.write("function "
				+ ComponentConstants.JS_CREATE_HTMLINFOWINDOWS_FUNCTION_PREFIX
				+ mapComponent.getId() + "("
				+ ComponentConstants.JS_GMAP_BASE_VARIABLE + ") {");
		for (Iterator iterator = mapComponent.getChildren().iterator(); iterator
				.hasNext();) {
			UIComponent component = (UIComponent) iterator.next();

			if (component instanceof HTMLInformationWindow) {
				encodeHTMLInfoWindow(facesContext, mapComponent,
						(HTMLInformationWindow) component, writer);
			}
		}
		writer.write("}");
	}	
	
	public static void encodeHTMLInfoWindowsFunctionScriptCall(
			FacesContext facesContext, Map mapComponent, ResponseWriter writer)
			throws IOException {

		writer.write(ComponentConstants.JS_CREATE_HTMLINFOWINDOWS_FUNCTION_PREFIX
						+ mapComponent.getId()
						+ "("
						+ ComponentConstants.JS_GMAP_BASE_VARIABLE + ");");
	}	
}
