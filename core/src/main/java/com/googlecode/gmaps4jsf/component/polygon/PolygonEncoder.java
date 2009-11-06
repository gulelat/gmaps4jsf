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
package com.googlecode.gmaps4jsf.component.polygon;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.googlecode.gmaps4jsf.component.eventlistener.EventListener;
import com.googlecode.gmaps4jsf.component.map.EventEncoder;
import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.util.ComponentUtils;

/**
 * @author Hazem Saleh
 * @date September 20, 2008
 * The PolygonEncoder is used for encoding the map polygons.
 */
public class PolygonEncoder {

    public static void startEncodingPolygonFunctionScript(FacesContext facesContext,
                                                          Map mapComponent, 
                                                          Polygon polygon, 
                                                          ResponseWriter writer)
                                                          throws IOException {

        writer.write(ComponentConstants.JS_FUNCTION
                    + ComponentConstants.JS_CREATE_POLYGON_FUNCTION_PREFIX
                    + getUniquePolygonId(facesContext, polygon) + "("
                    + ComponentConstants.JS_GMAP_BASE_VARIABLE + ") {");

        if (polygon.isRendered()) {
            declarePolygonJSArray(facesContext, mapComponent, polygon, writer);
        }
    }
    
    public static void endEncodingPolygonFunctionScript(FacesContext facesContext,
                                                        Map mapComponent, 
                                                        Polygon polygon, 
                                                        ResponseWriter writer)
                                                        throws IOException {

        if (polygon.isRendered()) {
            encodePolygon(facesContext, mapComponent, polygon, writer);
        }

        writer.write("}");
    }    

    public static void encodePolygonFunctionScriptCall(FacesContext facesContext, 
                                                       Map parentMap, 
                                                       Polygon polygon,
                                                       ResponseWriter writer) 
                                                       throws IOException {

        writer.write(ComponentConstants.JS_CREATE_POLYGON_FUNCTION_PREFIX
                    + getUniquePolygonId(facesContext, polygon) + "("
                    + ComponentConstants.JS_GMAP_BASE_VARIABLE + ");     ");

    }

    private static void declarePolygonJSArray(FacesContext facesContext,
                                              Map mapComponent, 
                                              Polygon polygon, 
                                              ResponseWriter writer)
                                              throws IOException {

        // declare the polygon array.
        writer.write("var " + ComponentUtils.getComponentArrayVariable(polygon) + " = new Array();    ");
    }   
    
    private static void encodePolygon(FacesContext facesContext,
                                      Map mapComponent, 
                                      Polygon polygon, 
                                      ResponseWriter writer)
                                      throws IOException {
        
        // encode the polygon.
        writer.write("var polygon_" + polygon.getId() + "  = new "
                    + ComponentConstants.JS_GPolygon_OBJECT + "("
                    + ComponentUtils.getComponentArrayVariable(polygon) 
                    + ", '" + polygon.getHexStrokeColor()
                    + "', " + polygon.getLineWidth() + ","
                    + polygon.getStrokeOpacity() + ", '"
                    + polygon.getHexFillColor() + "', " + polygon.getFillOpacity()
                    + ");     ");        

        // add to the map.
        writer.write(ComponentConstants.JS_GMAP_BASE_VARIABLE
                    + ".addOverlay(polygon_" + polygon.getId() + ");     ");

        // encode polygon events.
        for (Iterator iterator = polygon.getChildren().iterator(); iterator.hasNext();) {
            UIComponent component = (UIComponent) iterator.next();

            if (component instanceof EventListener) {
                EventEncoder.encodeEventListenersFunctionScript(facesContext,
                                                                polygon, 
                                                                writer, 
                                                                "polygon_" + polygon.getId());
                
                EventEncoder.encodeEventListenersFunctionScriptCall(facesContext, 
                                                                    polygon, 
                                                                    writer, 
                                                                    "polygon_" + polygon.getId());
            }
        }

        // update polygon user variable.
        updatePolygonJSVariable(facesContext, polygon, writer);      
    }    

    private static void updatePolygonJSVariable(FacesContext facesContext, 
                                                Polygon polygon, 
                                                ResponseWriter writer) 
                                                throws IOException {

        if (polygon.getJsVariable() != null) {
            writer.write("     " + polygon.getJsVariable() + " = " + "polygon_"
                        + polygon.getId() + ";     ");
        }
    }

    private static String getUniquePolygonId(FacesContext facesContext, Polygon polygon) {
        String polygonID = polygon.getClientId(facesContext);

        return polygonID.replace(":", "_");
    }
}
