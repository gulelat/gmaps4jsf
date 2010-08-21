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
package com.googlecode.gmaps4jsf.component.point;

import junit.framework.TestCase;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class PointRendererTest extends TestCase {

    private PointRenderer renderer = new PointRenderer();

    public void testConvertToJavascriptObject() {
        Point point = new Point();
        point.setLatitude("10.1");
        point.setLongitude("45.823");
        assertEquals("Default point", "new google.maps.LatLng(10.1, 45.823)", renderer.convertToJavascriptObject(point));
    }

}
