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

import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.util.ComponentUtils;
import com.googlecode.gmaps4jsf.util.StreetViewPanoramaRendererUtil;

/**
 * @author Hazem Saleh
 * @date Sep 20, 2008
 * The (StreetViewPanoramaRenderer) renders a StreetViewPanorama.
 */
public class StreetViewPanoramaRenderer extends Renderer {

    private void encodeScripts(FacesContext facesContext, 
                               UIComponent component, 
                               ResponseWriter writer) 
                               throws IOException {

        StreetViewPanorama streetViewPanoramaComponent = (StreetViewPanorama) component;

        writer.startElement(ComponentConstants.HTML_SCRIPT, component);
        
        declareJSVariables(facesContext, streetViewPanoramaComponent, writer);

        ComponentUtils.startEncodingBrowserCompatabilityChecking(facesContext, component, writer);
        
        encodeStreetViewPanoramaRendererWrapper(facesContext, streetViewPanoramaComponent, writer);
        
        // determines whether to render streetViewPanorama on window onload.
        if ("true".equals(streetViewPanoramaComponent.getRenderOnWindowLoad())) {
            ComponentUtils.encodeJSFunctionInWindowOnLoad(writer,
                                                          ComponentConstants.JS_RENDER_STREET_VIEW_PANORAMA_FUNC + component.getId());
        } else {
            callStreetViewPanoramaRendererWrapper(facesContext, component, writer);
        }

        ComponentUtils.endEncodingBrowserCompatabilityChecking(facesContext, component, writer);

        writer.endElement(ComponentConstants.HTML_SCRIPT);
    }

    /*
     * Declare the JS variables for the streetViewPanorama.
     */
    private void declareJSVariables(FacesContext facesContext, 
                                    StreetViewPanorama streetViewPanorama,
                                    ResponseWriter writer) 
                                    throws IOException {

        // declare the streetViewPanorama
        if (streetViewPanorama.getJsVariable() != null) {
            writer.write("      var " + streetViewPanorama.getJsVariable() + ";     ");
        }
    }

    /*
     * This is the core method that is responsible for the whole streetViewPanorama rendering.
     * @param facesContext
     * @param streetViewPanoramaComponent
     * @param writer
     * @throws IOException
     */
    private void encodeStreetViewPanoramaRendererWrapper(FacesContext facesContext,
                                                         StreetViewPanorama streetViewPanoramaComponent, 
                                                         ResponseWriter writer) 
                                                         throws IOException {

        writer.write(ComponentConstants.JS_FUNCTION 
                    + ComponentConstants.JS_RENDER_STREET_VIEW_PANORAMA_FUNC
                    + streetViewPanoramaComponent.getId() + "(){");

        encodeStreetViewPanoramaScript(facesContext, streetViewPanoramaComponent, writer);

        writer.write("}");
    }    
    
    private void callStreetViewPanoramaRendererWrapper(FacesContext facesContext,
                                                       UIComponent component, 
                                                       ResponseWriter writer) 
                                                       throws IOException {

        writer.write(ComponentConstants.JS_RENDER_STREET_VIEW_PANORAMA_FUNC + component.getId() + "();     ");
    }        

    private void encodeStreetViewPanoramaScript(FacesContext facesContext,
                                                StreetViewPanorama streetViewPanoramaComponent,
                                                ResponseWriter writer) 
                                                throws IOException {

        StreetViewPanoramaRendererUtil.renderStreetViewPanorama(facesContext, 
                                                                streetViewPanoramaComponent, 
                                                                writer);
    }

    private void encodeHTMLModel(FacesContext facesContext, 
                                 UIComponent component, 
                                 ResponseWriter writer) 
                                 throws IOException {

        StreetViewPanorama streetViewPanoramaComponent = (StreetViewPanorama) component;

        writer.startElement(ComponentConstants.HTML_DIV, streetViewPanoramaComponent);

        writer.writeAttribute(ComponentConstants.HTML_ATTR_ID, 
                              streetViewPanoramaComponent.getClientId(facesContext), 
                              ComponentConstants.HTML_ATTR_ID);
        
        writer.writeAttribute(ComponentConstants.HTML_ATTR_NAME, 
                              streetViewPanoramaComponent.getClientId(facesContext), 
                              ComponentConstants.HTML_ATTR_NAME);
        
        writer.writeAttribute(ComponentConstants.HTML_ATTR_STYLE, 
                              "width: " + streetViewPanoramaComponent.getWidth() 
                              + "; height: " + streetViewPanoramaComponent.getHeight(), 
                              ComponentConstants.HTML_ATTR_STYLE);

        writer.endElement(ComponentConstants.HTML_DIV);
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ComponentUtils.assertValidContext(context);

        ResponseWriter writer = context.getResponseWriter();

        encodeHTMLModel(context, component, writer);

        encodeScripts(context, component, writer);
    }

    public void decode(FacesContext context, UIComponent component) {
    }
}