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

public class StreetViewPanoramaRendererUtil {

    public StreetViewPanoramaRendererUtil() {
    }

    public static void renderStreetViewPanorama(FacesContext facesContext,
	    StreetViewPanorama streetViewPanoramaComponent,
	    ResponseWriter writer) throws IOException {
	
	String streetViewPanoramaOptions = "";
	
	if (streetViewPanoramaComponent.getAddress() != null) {
	    writer.write("var geocoder_" + streetViewPanoramaComponent.getId()
		    + " = new " + "GClientGeocoder" + "();");
	    writer.write("geocoder_"
		    + streetViewPanoramaComponent.getId()
		    + ".getLatLng(\""
		    + streetViewPanoramaComponent.getAddress()
		    + "\","
		    + "function(location) {\n"
		    + "if (!location) {\n"
		    + "alert(\""
		    + streetViewPanoramaComponent
			    .getLocationNotFoundErrorMessage() + "\");\n"
		    + "} else {\n");
	    
	    streetViewPanoramaOptions = "{latlng:location, pov:{yaw:"
		    + streetViewPanoramaComponent.getYaw() + ", pitch:"
		    + streetViewPanoramaComponent.getPitch() + ", zoom:"
		    + streetViewPanoramaComponent.getZoom() + "}}";
	    
	    writer.write("var pano_base_variable"
		    + streetViewPanoramaComponent.getId() + " = new "
		    + "GStreetviewPanorama" + "(document.getElementById(\""
		    + streetViewPanoramaComponent.getClientId(facesContext)
		    + "\")," + streetViewPanoramaOptions + ");");
	    writer.write("}}\n);\n");
	} else {
	    streetViewPanoramaOptions = "{latlng:new GLatLng("
		    + streetViewPanoramaComponent.getLatitude() + ","
		    + streetViewPanoramaComponent.getLongitude()
		    + "), pov:{yaw:" + streetViewPanoramaComponent.getYaw()
		    + ", pitch:" + streetViewPanoramaComponent.getPitch()
		    + ", zoom:" + streetViewPanoramaComponent.getZoom() + "}}";
	    
	    writer.write("var pano_base_variable"
		    + streetViewPanoramaComponent.getId() + " = new "
		    + "GStreetviewPanorama" + "(document.getElementById(\""
		    + streetViewPanoramaComponent.getClientId(facesContext)
		    + "\")," + streetViewPanoramaOptions + ");");
	}
	
	Iterator iterator = streetViewPanoramaComponent.getChildren()
		.iterator();
	
	do {
	    if (!iterator.hasNext()) {
		break;
	    }
	    
	    UIComponent component = (UIComponent) iterator.next();
	    
	    if (component instanceof EventListener) {
		EventEncoder.encodeEventListenersFunctionScript(facesContext,
			streetViewPanoramaComponent, writer,
			"pano_base_variable"
				+ streetViewPanoramaComponent.getId());
		EventEncoder.encodeEventListenersFunctionScriptCall(
			facesContext, streetViewPanoramaComponent, writer,
			"pano_base_variable"
				+ streetViewPanoramaComponent.getId());
	    }
	} while (true);

	updateStreetViewPanoramaJSVariable(facesContext,
		streetViewPanoramaComponent, writer);
    }

    private static void updateStreetViewPanoramaJSVariable(
	    FacesContext facesContext,
	    StreetViewPanorama streetViewPanoramaComponent,
	    ResponseWriter writer) throws IOException {
	
	if (streetViewPanoramaComponent.getJsVariable() != null) {
	    writer.write("\r\n" + streetViewPanoramaComponent.getJsVariable()
		    + "=" + "pano_base_variable"
		    + streetViewPanoramaComponent.getId() + ";\r\n");
	}
    }
}