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
package com.googlecode.gmaps4jsf.component.map;

import javax.faces.context.FacesContext;
import junit.framework.TestCase;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class MapRendererTest extends TestCase {

    private MapRenderer renderer = new MapRenderer();

    public void testConvertToJavascriptObject() {
        Map map = new Map() {
            public String getClientId(FacesContext context) {
                return "whatever";
            }
        };
        map.setAddress("Barcelona");
        assertEquals("Map address object", "{id: 'whatever',zoom: 11,location: {latitude: 30.01, longitude: 31.14, address: 'Barcelona'}, jsVariable: ''}", renderer.convertToJavascriptObject(null, map));
        map.setAddress("Rat's Nest");
        map.setJsVariable("mapa");
        assertEquals("Map JS object", "{id: 'whatever',zoom: 11,location: {latitude: 30.01, longitude: 31.14, address: 'Rat\\u0027s Nest'}, jsVariable: 'mapa'}", renderer.convertToJavascriptObject(null, map));
    }

}
