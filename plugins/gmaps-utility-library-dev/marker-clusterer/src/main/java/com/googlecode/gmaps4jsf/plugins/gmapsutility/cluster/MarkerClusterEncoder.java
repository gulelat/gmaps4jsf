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
package com.googlecode.gmaps4jsf.plugins.gmapsutility.cluster;

import java.util.Iterator;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import com.googlecode.gmaps4jsf.plugins.MapPlugin;
import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.plugins.gmapsutility.component.MarkerCluster;

/**
 * Encodes the JS needed to group markers automatically when they reach a threshold.
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public final class MarkerClusterEncoder extends MapPlugin {

    private static final String MARKER_CLUSTERER_FUNCTION = "markerClusterer";

    public String encodeFunctionScript(FacesContext facesContext, UIComponent clusterComponent) {
        StringBuffer buffer = new StringBuffer("function ");
        buffer.append(MARKER_CLUSTERER_FUNCTION).append(clusterComponent.getId()).append("(map) {");
        for (Iterator iterator = clusterComponent.getChildren().iterator(); iterator.hasNext();) {
            UIComponent component = (UIComponent) iterator.next();
            if (component instanceof MarkerCluster  && component.isRendered()) {
                buffer.append("map.setCluster(new MarkerClusterer(map));");
            }
        }
        return buffer.append("}").toString();
    }

    public String encodeFunctionScriptCall(FacesContext facesContext, UIComponent clusterComponent) {
        StringBuffer buffer = new StringBuffer();
        return buffer.append(MARKER_CLUSTERER_FUNCTION).append(clusterComponent.getId())
            .append("(").append(ComponentConstants.JS_GMAP_BASE_VARIABLE).append(");").toString();
    }

}
