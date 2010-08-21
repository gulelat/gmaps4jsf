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

import junit.framework.TestCase;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class PolygonRendererTest extends TestCase {

    private PolygonRenderer renderer = new PolygonRenderer();

    public void testConvertToJavascriptObject() {
        Polygon polygon = new Polygon();
        polygon.setHexStrokeColor("#f33f00");
        polygon.setLineWidth("5");
        polygon.setStrokeOpacity("1");
        polygon.setHexFillColor("#ff0000");
        polygon.setFillOpacity("0.2");
        assertEquals("Default polygon", "{hexStrokeColor: '#f33f00', lineWidth: 5, strokeOpacity: 1, hexFillColor: '#ff0000', fillOpacity: 0.2, jsVariable: ''}", renderer.convertToJavascriptObject(polygon));
    }

}
