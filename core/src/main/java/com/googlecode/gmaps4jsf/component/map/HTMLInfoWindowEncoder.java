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
import com.googlecode.gmaps4jsf.component.htmlInformationWindow.HTMLInformationWindow;
import com.googlecode.gmaps4jsf.component.marker.Marker;

public class HTMLInfoWindowEncoder {

    public HTMLInfoWindowEncoder() {
    }

    private static void encodeMapHTMLInfoWindow(FacesContext facesContext,
	    Map mapComponent, HTMLInformationWindow window,
	    ResponseWriter writer) throws IOException {
	
	String latitude;
	if (window.getLatitude() != null) {
	    latitude = window.getLatitude();
	} else {
	    latitude = "map_base_variable.getCenter().lat()";
	}
	
	String longitude;
	if (window.getLongitude() != null) {
	    longitude = window.getLongitude();
	} else {
	    longitude = "map_base_variable.getCenter().lng()";
	}
	
	writer.write("map_base_variable.openInfoWindowHtml(new GLatLng("
		+ latitude + ", " + longitude + "), \"" + window.getHtmlText()
		+ "\");");
	writer.write("var window_" + window.getId() + " = "
		+ "map_base_variable" + ".getInfoWindow();");
	
	Iterator iterator = window.getChildren().iterator();
	
	do {
	    if (!iterator.hasNext()) {
		break;
	    }
	    
	    UIComponent component = (UIComponent) iterator.next();
	    
	    if (component instanceof EventListener) {
		EventEncoder.encodeEventListenersFunctionScript(facesContext,
			window, writer, "window_" + window.getId());
		EventEncoder.encodeEventListenersFunctionScriptCall(
			facesContext, window, writer, "window_"
				+ window.getId());
	    }
	} while (true);
    }

    public static void encodeMarkerHTMLInfoWindow(FacesContext facesContext,
	    Marker marker, HTMLInformationWindow window, ResponseWriter writer)
	    throws IOException {

	writer.write("marker_" + marker.getId() + ".openInfoWindowHtml(\""
		+ window.getHtmlText() + "\");");

	writer.write("var window_" + window.getId() + " = "
		+ "map_base_variable" + ".getInfoWindow();");
	
	Iterator iterator = window.getChildren().iterator();
	
	do {
	    if (!iterator.hasNext()) {
		break;
	    }
	    
	    UIComponent component = (UIComponent) iterator.next();
	    
	    if (component instanceof EventListener) {
		EventEncoder.encodeEventListenersFunctionScript(facesContext,
			window, writer, "window_" + window.getId());
		EventEncoder.encodeEventListenersFunctionScriptCall(
			facesContext, window, writer, "window_"
				+ window.getId());
	    }
	} while (true);
    }

    public static void encodeHTMLInfoWindowsFunctionScript(
	    FacesContext facesContext, Map mapComponent, ResponseWriter writer)
	    throws IOException {
	
	writer.write("function createHTMLInfoWindowsFunction"
		+ mapComponent.getId() + "(" + "map_base_variable" + ") {");
	
	Iterator iterator = mapComponent.getChildren().iterator();
	
	do {
	    if (!iterator.hasNext()) {
		break;
	    }
	    
	    UIComponent component = (UIComponent) iterator.next();
	    
	    if ((component instanceof HTMLInformationWindow)
		    && component.isRendered()) {
		encodeMapHTMLInfoWindow(facesContext, mapComponent,
			(HTMLInformationWindow) component, writer);
	    }
	} while (true);
	writer.write("}");
    }

    public static void encodeHTMLInfoWindowsFunctionScriptCall(
	    FacesContext facesContext, Map mapComponent, ResponseWriter writer)
	    throws IOException {
	
	writer.write("createHTMLInfoWindowsFunction" + mapComponent.getId()
		+ "(" + "map_base_variable" + ");");
    }
}