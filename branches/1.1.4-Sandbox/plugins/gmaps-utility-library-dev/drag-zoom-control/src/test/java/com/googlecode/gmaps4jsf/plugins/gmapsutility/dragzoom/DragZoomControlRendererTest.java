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

import junit.framework.TestCase;
import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.plugins.gmapsutility.component.DragZoomControl;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class DragZoomControlRendererTest extends TestCase {

    private DragZoomControlRenderer renderer = new DragZoomControlRenderer();

    public void testConvertToJavascriptObject() {
        DragZoomControl drag = new DragZoomControl();
        drag.setParent(new Map());
        drag.setImageURL("http://www.google.com");
        drag.setImageZoomingURL("http://www.google.com/image");
        assertEquals("Drag zoom contro call", "\t\t\tparent.dragZoom('http://www.google.com', 'http://www.google.com/image', '24px');\n", renderer.convertToJavascriptObject(drag));
    }

}
