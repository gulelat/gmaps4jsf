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
package com.googlecode.gmaps4jsf.plugins.gmapsutility.dragzoom;

import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.plugins.Plugin;
import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.plugins.gmapsutility.component.DragZoomControl;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class DragZoomControlEncoder implements Plugin {

    private static final String DRAG_ZOOM_FUNCTION = "dragZoom";

    public Class getModifiedComponent() {
        return Map.class;
    }

    public String encodeFunctionScript(FacesContext facesContext, UIComponent mapComponent) {
        StringBuffer buffer = new StringBuffer("function ");
        buffer.append(DRAG_ZOOM_FUNCTION).append(mapComponent.getId()).append("(map) {");
        for (Iterator iterator = mapComponent.getChildren().iterator(); iterator.hasNext();) {
            UIComponent component = (UIComponent) iterator.next();
            if (component instanceof DragZoomControl  && component.isRendered()) {
                encodeDragZoomControl((DragZoomControl) component, buffer);
            }
        }
        return buffer.append("}").toString();
    }

    public String encodeFunctionScriptCall(FacesContext facesContext, UIComponent mapComponent) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(DRAG_ZOOM_FUNCTION).append(mapComponent.getId())
            .append("(").append(ComponentConstants.JS_GMAP_BASE_VARIABLE).append(");");
        return buffer.toString();
    }

    private void encodeDragZoomControl(DragZoomControl zoomControl, StringBuffer buffer) {
        buffer.append("var box = {opacity: .2, border: \\\"2px solid red\\\"};")
            .append("var opts = {buttonHTML: '<img src=\\\"").append(zoomControl.getImageURL())
            .append("\\\" />', buttonZoomingHTML: '<img src=\\\"")
            .append(zoomControl.getImageZoomingURL()).append("\\\" />', buttonStartingStyle: {")
            .append("width: '").append(zoomControl.getImageWidth()).append("', height: '")
            .append(zoomControl.getImageWidth()).append("'}};")
            .append(ComponentConstants.JS_GMAP_BASE_VARIABLE)
            .append(".addControl(new DragZoomControl(box, opts, {}));");
    }

}
