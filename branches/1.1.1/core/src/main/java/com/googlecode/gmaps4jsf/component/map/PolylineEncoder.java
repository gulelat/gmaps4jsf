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

public class PolylineEncoder {

    public PolylineEncoder() {
    }

    private static void encodePolyline(FacesContext facesContext,
	    Map mapComponent, Polyline polyline, ResponseWriter writer)
	    throws IOException {
	
	String polyOptionsStr = "";
	String polyLinesStr = "";
	Iterator iterator = polyline.getChildren().iterator();
	
	do {
	    if (!iterator.hasNext()) {
		break;
	    }
	    
	    UIComponent component = (UIComponent) iterator.next();
	    
	    if (component instanceof Point) {
		Point point = (Point) component;
		
		if (!polyLinesStr.equals("")) {
		    polyLinesStr = polyLinesStr + ",";
		}
		polyLinesStr = polyLinesStr + "new GLatLng("
			+ point.getLatitude() + ", " + point.getLongitude()
			+ ")";
	    }
	} while (true);
	
	if ("true".equalsIgnoreCase(polyline.getGeodesic())) {
	    polyOptionsStr = "{geodesic:true}";
	} else {
	    polyOptionsStr = "{geodesic:false}";
	}
	
	writer.write("var polyline_" + polyline.getId() + " = new "
		+ "GPolyline" + "([" + polyLinesStr + "], \""
		+ polyline.getHexaColor() + "\", " + polyline.getLineWidth()
		+ "," + polyline.getOpacity() + ", " + polyOptionsStr + ");");
	writer.write("map_base_variable.addOverlay(polyline_"
		+ polyline.getId() + ");");
	
	iterator = polyline.getChildren().iterator();
	
	do {
	    if (!iterator.hasNext()) {
		break;
	    }
	    
	    UIComponent component = (UIComponent) iterator.next();
	    
	    if (component instanceof EventListener) {
		EventEncoder.encodeEventListenersFunctionScript(facesContext,
			polyline, writer, "polyline_" + polyline.getId());
		EventEncoder.encodeEventListenersFunctionScriptCall(
			facesContext, polyline, writer, "polyline_"
				+ polyline.getId());
	    }
	} while (true);
	
	updatePolylineJSVariable(facesContext, polyline, writer);
    }

    private static void updatePolylineJSVariable(FacesContext facesContext,
	    Polyline polyline, ResponseWriter writer) throws IOException {
	
	if (polyline.getJsVariable() != null) {
	    writer.write("\r\n" + polyline.getJsVariable() + " = "
		    + "polyline_" + polyline.getId() + ";\r\n");
	}
    }

    public static void encodePolylinesFunctionScript(FacesContext facesContext,
	    Map mapComponent, ResponseWriter writer) throws IOException {
	
	writer.write("function createPolylinesFunction" + mapComponent.getId()
		+ "(" + "map_base_variable" + ") {");
	
	Iterator iterator = mapComponent.getChildren().iterator();
	
	do {
	    if (!iterator.hasNext()) {
		break;
	    }
	    
	    UIComponent component = (UIComponent) iterator.next();
	    
	    if ((component instanceof Polyline) && component.isRendered()) {
		encodePolyline(facesContext, mapComponent,
			(Polyline) component, writer);
	    }
	} while (true);
	
	writer.write("}");
    }

    public static void encodePolylinesFunctionScriptCall(
	    FacesContext facesContext, Map mapComponent, ResponseWriter writer)
	    throws IOException {
	
	writer.write("createPolylinesFunction" + mapComponent.getId() + "("
		+ "map_base_variable" + ");");
    }
}