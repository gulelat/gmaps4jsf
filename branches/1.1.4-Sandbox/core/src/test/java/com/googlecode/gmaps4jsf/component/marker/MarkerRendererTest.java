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
package com.googlecode.gmaps4jsf.component.marker;

import java.util.List;
import java.util.ArrayList;
import junit.framework.TestCase;
import javax.faces.context.FacesContext;
import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.component.icon.Icon;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class MarkerRendererTest extends TestCase {

    private List children = new ArrayList();
    private MarkerRenderer renderer = new MarkerRenderer();

    public void testConvertToJavascriptObject() {
    	/*
        Map map = new Map() {
            public String getClientId(FacesContext context) {
                return "whatever";
            }
        };
        Marker marker = new Marker() {
            public List getChildren() {
                return children;
            }
        };
        assertEquals("Marker converted", "{address: '', latitude: null, longitude: null, markerOptions: {draggable: false}, jsVariable: ''}", renderer.convertToJavascriptObject(null, marker));
        marker.setLatitude("4.21");
        marker.setLongitude("3.82");
        marker.setJsVariable("marker1");
        Icon icon = new Icon();
        icon.setImageURL("http://www.google.com");
        children.add(icon);
        assertEquals("Marker converted", "{address: '', latitude: 4.21, longitude: 3.82, markerOptions: {draggable: false, icon: parent.buildIcon({shadow: '', iconSize: {width: 20, height: 34}, shadowSize: {width: 37, height: 34}, iconAnchor: {x: 9, y: 34}, infoWindowAnchor: {x: 9, y: 2}, image: 'http://www.google.com'})}, jsVariable: 'marker1'}", renderer.convertToJavascriptObject(null, marker));
        */
    }

    public void testConvertIconToJavascriptObject() {
    	/*
        Icon icon = new Icon();
        icon.setImageURL("http://www.google.com");
        assertEquals("Icon converted", "{shadow: '', iconSize: {width: 20, height: 34}, shadowSize: {width: 37, height: 34}, iconAnchor: {x: 9, y: 34}, infoWindowAnchor: {x: 9, y: 2}, image: 'http://www.google.com'}", renderer.convertIconToJavascriptObject(icon).toString());
        */
    }

}
