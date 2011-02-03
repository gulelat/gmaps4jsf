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
import javax.faces.render.Renderer;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import com.googlecode.gmaps4jsf.util.ComponentUtils;

/**
 * @author Hazem Saleh
 * @date May 8, 2010
 * The (DirectionRenderer) renders a google map direction.
 */
public class DirectionRenderer extends Renderer {

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer    = context.getResponseWriter();
        writer.write("\t\t\tparent.addDirection(" + convertToJavascriptObject((Direction) component) + ");");
    }

    protected String convertToJavascriptObject(Direction direction) {
        StringBuffer buffer = new StringBuffer("{");
        buffer.append("attachNodeId: '").append(ComponentUtils.unicode(direction.getAttachNodeId())).append("', ");
        buffer.append("fromAddress: '").append(ComponentUtils.unicode(direction.getFromAddress())).append("', ");
        buffer.append("toAddress: '").append(ComponentUtils.unicode(direction.getToAddress())).append("', ");
        buffer.append("locale: '").append(ComponentUtils.unicode(direction.getLocale())).append("', ");
        buffer.append("travelMode: ").append(direction.getTravelMode()).append(", ");
        buffer.append("avoidHighways: ").append(direction.getAvoidHighways()).append(", ");
        buffer.append("preserveViewport: ").append(direction.getPreserveViewport()).append("}");
        return buffer.toString();
    }

}