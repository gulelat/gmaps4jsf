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
import com.googlecode.gmaps4jsf.component.groundoverlay.GroundOverlay;
import com.googlecode.gmaps4jsf.util.ComponentConstants;

/**
 * @author Hazem Saleh
 * @date September 20, 2008
 * The GroundOverlayEncoder is used for encoding the map ground overlay.
 */
public class GroundOverlayEncoder {

	private static void encodeGroundOverlay(FacesContext facesContext,
			Map mapComponent, GroundOverlay groundOverlay, ResponseWriter writer)
			throws IOException {
	
		// encode the groundOverlay.
		writer.write("var boundaries = new "
				+ ComponentConstants.JS_GLatLngBounds_OBJECT + "(new "
				+ ComponentConstants.JS_GLatLng_OBJECT + "("
				+ groundOverlay.getStartLatitude() + ","
				+ groundOverlay.getStartLongitude() + "), " + "new "
				+ ComponentConstants.JS_GLatLng_OBJECT + "("
				+ groundOverlay.getEndLatitude() + ","
				+ groundOverlay.getEndLongitude() + "));");

		writer.write("var groundOverlay_" + groundOverlay.getId() + " = new "
				+ ComponentConstants.JS_GGroundOverlay_OBJECT + "(\""
				+ groundOverlay.getImageURL() + "\", boundaries);");

		writer.write(ComponentConstants.JS_GMAP_BASE_VARIABLE
				+ ".addOverlay(groundOverlay_" + groundOverlay.getId() + ");");

		// encode GroundOverlay events.
		for (Iterator iterator = groundOverlay.getChildren().iterator(); iterator
				.hasNext();) {
			UIComponent component = (UIComponent) iterator.next();

			if (component instanceof EventListener) {
				EventEncoder.encodeEventListenersFunctionScript(facesContext,
						groundOverlay, writer, "groundOverlay_"
								+ groundOverlay.getId());
				EventEncoder.encodeEventListenersFunctionScriptCall(
						facesContext, groundOverlay, writer, "groundOverlay_"
								+ groundOverlay.getId());
			}
		}			
		
		// update GroundOverlay user variable.
		updateGroundOverlayJSVariable(facesContext, groundOverlay, writer);
	}
	
	private static void updateGroundOverlayJSVariable(FacesContext facesContext,
			GroundOverlay groundOverlay, ResponseWriter writer) throws IOException {

		if (groundOverlay.getJsVariable() != null) {
			writer.write("\r\n" + groundOverlay.getJsVariable() + " = " + "groundOverlay_"
					+ groundOverlay.getId() + ";\r\n");
		}
	}	
	
	public static void encodeGroundOverlaysFunctionScript(
			FacesContext facesContext, Map mapComponent, ResponseWriter writer)
			throws IOException {

		writer.write("function "
				+ ComponentConstants.JS_CREATE_GROUND_OVERLAYS_FUNCTION_PREFIX
				+ mapComponent.getId() + "("
				+ ComponentConstants.JS_GMAP_BASE_VARIABLE + ") {");

		for (Iterator iterator = mapComponent.getChildren().iterator(); iterator
				.hasNext();) {
			UIComponent component = (UIComponent) iterator.next();

			if (component instanceof GroundOverlay && component.isRendered()) {
				encodeGroundOverlay(facesContext, mapComponent,
						(GroundOverlay) component, writer);
			}
		}
		writer.write("}");
	}
	
	public static void encodeGroundOverlaysFunctionScriptCall(
			FacesContext facesContext, Map mapComponent, ResponseWriter writer)
			throws IOException {

		writer.write(ComponentConstants.JS_CREATE_GROUND_OVERLAYS_FUNCTION_PREFIX
				+ mapComponent.getId() + "("
				+ ComponentConstants.JS_GMAP_BASE_VARIABLE + ");");
	}
}
