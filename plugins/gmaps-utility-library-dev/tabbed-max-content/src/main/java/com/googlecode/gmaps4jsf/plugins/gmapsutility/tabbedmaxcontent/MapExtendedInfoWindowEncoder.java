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
package com.googlecode.gmaps4jsf.plugins.gmapsutility.tabbedmaxcontent;

import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;

import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.util.ComponentConstants;
import com.googlecode.gmaps4jsf.plugins.gmapsutility.component.MaxInfoWindow;

/**
 * Creates an enhanced information window usable inside Maps that can be maximized.
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public final class MapExtendedInfoWindowEncoder extends AbstractTabbedContentEncoder {

    public Class getModifiedComponent() {
        return Map.class;
    }

    public String encodeFunctionScriptCall(FacesContext facesContext, UIComponent mapComponent) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(TABBED_INFO_WINDOW_FUNCTION).append(mapComponent.getId())
            .append("(").append(ComponentConstants.JS_GMAP_BASE_VARIABLE).append(",")
            .append(ComponentConstants.JS_GMAP_BASE_VARIABLE).append(");");
        return buffer.toString();
    }

}
