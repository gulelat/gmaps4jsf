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
package com.googlecode.gmaps4jsf.component.direction;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.util.ComponentUtils;

/**
 * @author Hazem Saleh
 * @date May 8, 2010
 * The (DirectionRenderer) renders a google map direction.
 */
public class DirectionRenderer extends Renderer {

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        Direction      direction = (Direction) component;
        ResponseWriter writer    = context.getResponseWriter();
                
        encodeDirectionHTMLModel(writer, direction);
        encodeDirectionScriptModel(context, writer, direction);
    }

    private void encodeDirectionHTMLModel(ResponseWriter writer, Direction direction) throws IOException {
        writer.startElement(ComponentConstants.HTML_DIV, direction);
        
        writer.writeAttribute(ComponentConstants.HTML_ATTR_ID, direction.getId(), ComponentConstants.HTML_ATTR_ID);
        
        if (direction.getStyle() != null) {
            writer.writeAttribute(ComponentConstants.HTML_ATTR_STYLE, direction.getStyle(), ComponentConstants.HTML_ATTR_STYLE);
        }
        
        if (direction.getStyleClass() != null) {
            writer.writeAttribute(ComponentConstants.HTML_ATTR_STYLE_CLASS, direction.getStyleClass(), ComponentConstants.HTML_ATTR_STYLE_CLASS);
        }        
        
        writer.endElement(ComponentConstants.HTML_DIV);
    }
    
    private void encodeDirectionScriptModel(FacesContext context, ResponseWriter writer, Direction direction) throws IOException {
        writer.startElement(ComponentConstants.HTML_SCRIPT, direction);
        writer.writeAttribute(ComponentConstants.HTML_SCRIPT_TYPE, ComponentConstants.HTML_SCRIPT_LANGUAGE,
                              ComponentConstants.HTML_SCRIPT_TYPE);    
        
        ComponentUtils.startEncodingBrowserCompatabilityChecking(context, direction, writer);
        
        String script = "\"var " + getDirectionPanelJSVariable(direction) + "=" + "document.getElementById('" + direction.getId() + "');" + ComponentConstants.JS_TAB;
        
        script += "var " + getDirectionJSVariable(direction) + "=" +
                  " new " + ComponentConstants.JS_GDirection_OBJECT +
                  "(" +
                  getDirectionMapJSVariable(direction) +
                  ", " +
                  getDirectionPanelJSVariable(direction) +
                  ");" + ComponentConstants.JS_TAB;
        
        script += getDirectionJSVariable(direction) + 
                  ".load(" +
                  "\\\"from: " +
                  direction.getFromAddress() +
                  " to: " +
                  direction.getToAddress() +
                  "\\\"" +
                  ", {" + constructDirectionAttributes(direction)  + "});\"";
        
        executeDirectionScriptAfterMapLoading(writer, script, direction);
        
        ComponentUtils.endEncodingBrowserCompatabilityChecking(context, direction, writer);        
        writer.endElement(ComponentConstants.HTML_SCRIPT);
    }

    private String constructDirectionAttributes(Direction direction) {
        String directionAttributes = "locale: '" + direction.getLocale() + "'";
        
        if (direction.getTravelMode() != null) {
            directionAttributes += ", travelMode:" + direction.getTravelMode();
        }
        
        if (direction.getAvoidHighways() != null) {
            directionAttributes += ", avoidHighways:" + direction.getAvoidHighways();
        }        
        
        if (direction.getPreserveViewport() != null) {
            directionAttributes += ", preserveViewport:" + direction.getPreserveViewport();
        }           
        
        return directionAttributes;
    }
    
    private void executeDirectionScriptAfterMapLoading(ResponseWriter writer, String script, Direction direction) throws IOException {
        String executeScript = "var interval" + direction.getId() +
                               " = window.setInterval(function(){ " +
                                   "if (" + getDirectionMapJSVariable(direction) + ") { " +
                                       "clearInterval(interval" + direction.getId() + "); " +
                                       "window.setTimeout(function() {" + "window.eval(" + script + ");}, 10);" +
                                   "} " +
                               "}, 10);";
        
        writer.write(executeScript);
    }    
    
    private String getDirectionMapJSVariable(Direction direction) {
        return ComponentConstants.JS_MAP_VARIABLE_PREFIX + direction.getFor();
    }

    private String getDirectionPanelJSVariable(Direction direction) {
        return "directionsPanel" + direction.getId();
    }

    private String getDirectionJSVariable(Direction direction) {
        return "directions" + direction.getId();
    }
}