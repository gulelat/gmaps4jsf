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
package com.googlecode.gmaps4jsf.component.circle;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.util.FileReaderUtils;

/**
 * @author Hazem Saleh
 * @date September 20, 2008
 * The CircleEncoder is used for encoding the map circles.
 */
public class CircleEncoder {
    private static final String TEMPLATE_FILE_NAME = "template.circle.script";
    
    public static void encodeCircleFunctionScript(FacesContext facesContext,
                                                  Map mapComponent, 
                                                  Circle circle, 
                                                  ResponseWriter writer)
                                                  throws IOException {
        
        encodeCircle(facesContext, mapComponent, circle, writer);
    }    

    public static void encodeCircleFunctionScriptCall(FacesContext facesContext, 
                                                      Map parentMap, 
                                                      Circle circle,
                                                      ResponseWriter writer) 
                                                      throws IOException {

        writer.write(ComponentConstants.JS_CREATE_CIRCLE_FUNCTION_PREFIX
                    + getUniqueCircleId(facesContext, circle) + "("
                    + ComponentConstants.JS_GMAP_BASE_VARIABLE + ");     ");

    } 
    
    private static void encodeCircle(FacesContext facesContext,
                                     Map mapComponent, 
                                     Circle circle, 
                                     ResponseWriter writer)
                                     throws IOException {
        
        // read the circle script code from the template.
        String circleScript = FileReaderUtils.getResourceContent(TEMPLATE_FILE_NAME, " ");
        circleScript        = circleScript.replaceAll("#ID", getUniqueCircleId(facesContext, circle))
                                          .replaceAll("#JS_VARIABLE", circle.getJsVariable())
                                          .replaceAll("#UNIT", "'" + circle.getUnit() + "'")
                                          .replaceAll("#RADUIS", circle.getRaduis())
                                          .replaceAll("#LAT", circle.getLatitude())
                                          .replaceAll("#LNG", circle.getLongitude())
                                          .replaceAll("#STROKE_COLOUR", "'" + circle.getHexStrokeColor() + "'")
                                          .replaceAll("#STROKE_OPACITY", circle.getStrokeOpacity())
                                          .replaceAll("#LINE_WIDTH", circle.getLineWidth())
                                          .replaceAll("#FILL_COLOUR", "'" + circle.getHexFillColor() + "'")
                                          .replaceAll("#FILL_OPACITY", circle.getFillOpacity());
        
        
        writer.write(circleScript);        
    }
    
    private static String getUniqueCircleId(FacesContext facesContext, Circle circle) {
        String circleID = circle.getClientId(facesContext);

        return circleID.replace(':', '_');
    }
}
