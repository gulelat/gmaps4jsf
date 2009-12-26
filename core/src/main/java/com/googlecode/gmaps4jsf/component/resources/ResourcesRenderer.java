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
package com.googlecode.gmaps4jsf.component.resources;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.googlecode.gmaps4jsf.util.ComponentConstants;

/**
 * @author Hazem Saleh
 * @date Decemeber 26, 2009
 * 
 */
public class ResourcesRenderer extends Renderer {
    
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        
        if (component.isRendered()) {
            ResponseWriter writer = context.getResponseWriter();
            Resources resources = (Resources) component;

            // include the core map script.
            if (resources.getKey() != null) {
                writer.write("\n\r");
                writer.startElement(ComponentConstants.HTML_SCRIPT, component);
                writer.writeAttribute("src", 
                                      "http://maps.google.com/maps?file=api&amp;v=2&amp;sensor=false&amp;key="
                                      + resources.getKey(), null);
                writer.endElement(ComponentConstants.HTML_SCRIPT);
                writer.write("\n\r");
            }
            
            if (! resources.getIncludeExtendedComponents().equalsIgnoreCase("false")) {
                
                // include the drag zoom script.
                writer.write("\n\r");
                writer.startElement(ComponentConstants.HTML_SCRIPT, component);
                writer.writeAttribute("src", 
                                      "http://gmaps-utility-library.googlecode.com/svn/trunk/dragzoom/release/src/dragzoom_packed.js", 
                                      null);                
                writer.endElement(ComponentConstants.HTML_SCRIPT);
                writer.write("\n\r");       
                
                // include the max tabbed content script.
                writer.write("\n\r");
                writer.startElement(ComponentConstants.HTML_SCRIPT, component);
                writer.writeAttribute("src", 
                                      "http://gmaps-utility-library-dev.googlecode.com/svn/trunk/tabbedmaxcontent/src/tabbedmaxcontent_packed.js", 
                                      null);                
                writer.endElement(ComponentConstants.HTML_SCRIPT);
                writer.write("\n\r");                    
            }
        }
    }
    

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
    } 
}