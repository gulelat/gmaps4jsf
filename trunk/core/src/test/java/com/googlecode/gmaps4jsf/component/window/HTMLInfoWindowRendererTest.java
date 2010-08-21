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
package com.googlecode.gmaps4jsf.component.window;

import junit.framework.TestCase;
import com.googlecode.gmaps4jsf.component.window.HTMLInformationWindow;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class HTMLInfoWindowRendererTest extends TestCase {
    
    private HTMLInfoWindowRenderer renderer = new HTMLInfoWindowRenderer();

    public void testConvertToJavascriptObject() {
        HTMLInformationWindow infoWindow  = new HTMLInformationWindow();
        infoWindow.setHtmlText("some text");
        assertEquals("Info window object", "{latitude: null, longitude: null, htmlText: 'some text'}", renderer.convertToJavascriptObject(null, infoWindow));
        infoWindow.setLatitude("10.0");
        infoWindow.setLongitude("20.0");
        assertEquals("Info window object with lat/lng", "{latitude: 10.0, longitude: 20.0, htmlText: 'some text'}", renderer.convertToJavascriptObject(null, infoWindow));
        infoWindow.setHtmlText("someone's \"text\"");
        assertEquals("Info window object with special chars", "{latitude: 10.0, longitude: 20.0, htmlText: 'someone\\u0027s \"text\"'}", renderer.convertToJavascriptObject(null, infoWindow));
    }

}
