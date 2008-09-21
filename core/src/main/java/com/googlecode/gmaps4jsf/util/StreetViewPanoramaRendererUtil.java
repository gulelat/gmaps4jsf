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
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.googlecode.gmaps4jsf.component.eventlistener.EventListener;
import com.googlecode.gmaps4jsf.component.map.EventEncoder;
import com.googlecode.gmaps4jsf.component.streetviewpanorama.StreetViewPanorama;

/**
 * @author Hazem Saleh
 * @date Sep 21, 2008
 * The StreetViewPanoramaRendererUtil is used for encoding the StreetViewPanorama scripts.
 */
public class StreetViewPanoramaRendererUtil {

	public static void renderStreetViewPanorama(FacesContext facesContext,
			StreetViewPanorama streetViewPanoramaComponent,
			ResponseWriter writer) throws IOException {

		String streetViewPanoramaOptions = "{latlng:new "
				+ ComponentConstants.JS_GLatLng_OBJECT + "("
				+ streetViewPanoramaComponent.getLatitude() + ","
				+ streetViewPanoramaComponent.getLongitude() + "), pov:{yaw:"
				+ streetViewPanoramaComponent.getYaw() + ",pitch:"
				+ streetViewPanoramaComponent.getPitch() + ", zoom:"
				+ streetViewPanoramaComponent.getZoom() + "}}";
		
		writer.write("var "
				+ ComponentConstants.JS_GSTREET_VIEW_PANORAMA_VARIABLE
				+ streetViewPanoramaComponent.getId() + " = new "
				+ ComponentConstants.JS_GSTREET_VIEW_PANORAMA_CORE_OBJECT
				+ "(document.getElementById(\""
				+ streetViewPanoramaComponent.getClientId(facesContext)
				+ "\")," + streetViewPanoramaOptions + ");");
		
		// encode StreetViewPanorama events.
		for (Iterator iterator = streetViewPanoramaComponent.getChildren()
				.iterator(); iterator.hasNext();) {
			UIComponent component = (UIComponent) iterator.next();

			if (component instanceof EventListener) {
				
				EventEncoder.encodeEventListenersFunctionScript(facesContext,
						streetViewPanoramaComponent, writer,
						ComponentConstants.JS_GSTREET_VIEW_PANORAMA_VARIABLE
								+ streetViewPanoramaComponent.getId());
				
				EventEncoder.encodeEventListenersFunctionScriptCall(
						facesContext, streetViewPanoramaComponent, writer,
						ComponentConstants.JS_GSTREET_VIEW_PANORAMA_VARIABLE
								+ streetViewPanoramaComponent.getId());
			}
		}			
		
		updateStreetViewPanoramaJSVariable(facesContext,
				streetViewPanoramaComponent, writer);
	}
	
	private static void updateStreetViewPanoramaJSVariable(
			FacesContext facesContext,
			StreetViewPanorama streetViewPanoramaComponent,
			ResponseWriter writer) throws IOException {

		if (streetViewPanoramaComponent.getJsVariable() != null) {
			writer.write("\r\n" + streetViewPanoramaComponent.getJsVariable()
					+ "="
					+ ComponentConstants.JS_GSTREET_VIEW_PANORAMA_VARIABLE
					+ streetViewPanoramaComponent.getId() + ";\r\n");
		}
	}	

}
