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

import junit.framework.TestCase;
import javax.faces.context.FacesContext;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class StreetViewPanoramaRendererTest extends TestCase {

    private StreetViewPanoramaRenderer renderer = new StreetViewPanoramaRenderer();

    public void testConvertToJavascriptObject() {
        StreetViewPanorama panorama = new StreetViewPanorama() {
            public String getClientId(FacesContext context) {
                return "whatever";
            }
        };
        assertEquals("Panorama converted", "{id: 'whatever', location: {latitude: 42.345573, longitude: -71.098326, address: ''}, pov: {zoom: 0, yaw: 0, pitch: 0}, jsVariable: ''}", renderer.convertToJavascriptObject(null, panorama));
        panorama.setZoom("4");
        panorama.setPitch("3");
        panorama.setYaw("0.34");
        panorama.setJsVariable("somevar");
        assertEquals("Full panorama converted", "{id: 'whatever', location: {latitude: 42.345573, longitude: -71.098326, address: ''}, pov: {zoom: 4, yaw: 0.34, pitch: 3}, jsVariable: 'somevar'}", renderer.convertToJavascriptObject(null, panorama));
    }

}
