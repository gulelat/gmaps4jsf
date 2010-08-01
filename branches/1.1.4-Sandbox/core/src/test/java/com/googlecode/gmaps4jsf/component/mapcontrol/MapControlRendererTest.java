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
package com.googlecode.gmaps4jsf.component.mapcontrol;

import junit.framework.TestCase;

/**
 *
 * @author Jose Noheda
 */
public class MapControlRendererTest extends TestCase {

    private MapControlRenderer renderer = new MapControlRenderer();

    public void testConvertToJavascriptObject() {
        MapControl control = new MapControl();
        control.setName("GLargeMapControl");
        assertEquals("Map control JS", "{name: 'GLargeMapControl', position: 'G_ANCHOR_TOP_LEFT', offsetWidth: 10, offsetHeight: 10}", renderer.convertToJavascriptObject(control));
    }

}
