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
package com.googlecode.gmaps4jsf.component.streetviewpanorama;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import com.googlecode.gmaps4jsf.util.ComponentUtils;
import com.googlecode.gmaps4jsf.util.StreetViewPanoramaRendererUtil;

public class StreetViewPanoramaRenderer extends Renderer {

    public StreetViewPanoramaRenderer() {
    }

    private void encodeScripts(FacesContext facesContext,
	    UIComponent component, ResponseWriter writer) throws IOException {
	
	StreetViewPanorama streetViewPanoramaComponent = (StreetViewPanorama) component;
	
	writer.startElement("script", component);
	declareJSVariables(facesContext, streetViewPanoramaComponent, writer);
	ComponentUtils.startEncodingBrowserCompatabilityChecking(facesContext,
		component, writer);
	encodeStreetViewPanoramaRendererWrapper(facesContext,
		streetViewPanoramaComponent, writer);
	
	if ("true".equals(streetViewPanoramaComponent.getRenderOnWindowLoad())) {
	    ComponentUtils.encodeJSFunctionInWindowOnLoad(writer,
		    "renderStreetViewPanorama" + component.getId());
	} else {
	    callStreetViewPanoramaRendererWrapper(facesContext, component,
		    writer);
	}
	
	ComponentUtils.endEncodingBrowserCompatabilityChecking(facesContext,
		component, writer);
	writer.endElement("script");
    }

    private void declareJSVariables(FacesContext facesContext,
	    StreetViewPanorama streetViewPanorama, ResponseWriter writer)
	    throws IOException {
	
	if (streetViewPanorama.getJsVariable() != null) {
	    writer.write("\r\n var " + streetViewPanorama.getJsVariable()
			    + ";");
	}
    }

    private void encodeStreetViewPanoramaRendererWrapper(
	    FacesContext facesContext,
	    StreetViewPanorama streetViewPanoramaComponent,
	    ResponseWriter writer) throws IOException {
	
	writer.write("function renderStreetViewPanorama"
		+ streetViewPanoramaComponent.getId() + "(){");
	encodeStreetViewPanoramaScript(facesContext,
		streetViewPanoramaComponent, writer);
	writer.write("}");
    }

    private void callStreetViewPanoramaRendererWrapper(
	    FacesContext facesContext, UIComponent component,
	    ResponseWriter writer) throws IOException {
	
	writer.write("renderStreetViewPanorama" + component.getId()
			+ "();\r\n");
    }

    private void encodeStreetViewPanoramaScript(FacesContext facesContext,
	    StreetViewPanorama streetViewPanoramaComponent,
	    ResponseWriter writer) throws IOException {
	
	StreetViewPanoramaRendererUtil.renderStreetViewPanorama(facesContext,
		streetViewPanoramaComponent, writer);
    }

    private void encodeHTMLModel(FacesContext facesContext,
	    UIComponent component, ResponseWriter writer) throws IOException {
	
	StreetViewPanorama streetViewPanoramaComponent = (StreetViewPanorama) component;
	
	writer.startElement("DIV", streetViewPanoramaComponent);
	writer.writeAttribute("id", streetViewPanoramaComponent
		.getClientId(facesContext), "id");
	writer.writeAttribute("name", streetViewPanoramaComponent
		.getClientId(facesContext), "name");
	writer.writeAttribute("style", "width: "
		+ streetViewPanoramaComponent.getWidth() + "; height: "
		+ streetViewPanoramaComponent.getHeight(), "style");
	writer.endElement("DIV");
    }

    public void encodeBegin(FacesContext facescontext, UIComponent uicomponent)
	    throws IOException {
    }

    public void encodeEnd(FacesContext context, UIComponent component)
	    throws IOException {
	
	ComponentUtils.assertValidContext(context);
	ResponseWriter writer = context.getResponseWriter();
	encodeHTMLModel(context, component, writer);
	encodeScripts(context, component, writer);
    }

    public void decode(FacesContext facescontext, UIComponent uicomponent) {
    }
}