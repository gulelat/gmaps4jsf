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
package com.googlecode.gmaps4jsf.plugins.popupmarker;

import junit.framework.TestCase;
import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.plugins.component.Popup;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class PopupMarkerRendererTest extends TestCase {
    
    private PopupMarkerRenderer renderer = new PopupMarkerRenderer();

    public void testConvertToJavascriptObject() {
        Popup popup = new Popup();
        popup.setParent(new Map());
        popup.setIcon("icon");
        popup.setContent("content");
        assertEquals("Popup control call", "\t\t\tgmap.addLabel(parent, '" + PopupMarkerRenderer.CHART_SERVER + "chst=d_bubble_icon_text_small&chld=icon|bb|content|FFFFFF|000000');\n", renderer.convertToJavascriptObject(popup));
    }

    public void testGetUrl() {
        Popup popup = new Popup();
        popup.setIcon("icon");
        popup.setContent("content");
        assertEquals("Popup chart URL", PopupMarkerRenderer.CHART_SERVER + "chst=d_bubble_icon_text_small&chld=icon|bb|content|FFFFFF|000000", renderer.getUrl(popup));
    }

}
