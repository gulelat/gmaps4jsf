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
package com.googlecode.gmaps4jsf.plugins.gmapsutility.tabbedmaxcontent;

import junit.framework.TestCase;
import com.googlecode.gmaps4jsf.plugins.gmapsutility.component.MaxInfoWindow;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class ExtendedInfoWindowRendererTest extends TestCase {

    private ExtendedInfoWindowRenderer renderer = new ExtendedInfoWindowRenderer();

    public void testConvertToJavascriptObject() {
    }

    public void testEncodeButtons() {
        StringBuffer buffer = new StringBuffer();
        MaxInfoWindow window = new MaxInfoWindow();
        window.setShowCloseButton(false);
        window.setShowMaximizeButton(false);
        window.setShowMinimizeButton(false);
        renderer.encodeButtons(window, buffer);
        assertEquals("No buttons", "buttons: {close: {show: 4},maximize: {show: 4},restore: {show: 4}}", buffer.toString());
        buffer = new StringBuffer();
        window.setShowCloseButton(true);
        renderer.encodeButtons(window, buffer);
        assertEquals("Remove close button", "buttons: {maximize: {show: 4},restore: {show: 4}}", buffer.toString());
        buffer = new StringBuffer();
        window.setShowMaximizeButton(true);
        renderer.encodeButtons(window, buffer);
        assertEquals("Only minimize button", "buttons: {restore: {show: 4}}", buffer.toString());
        buffer = new StringBuffer();
        window.setShowMinimizeButton(true);
        renderer.encodeButtons(window, buffer);
        assertEquals("All buttons", "buttons: {}", buffer.toString());
    }

    public void testGetSelectedTab() {
        MaxInfoWindow window = new MaxInfoWindow();
        assertEquals("Default tab", "0", renderer.getSelectedTab(window).toString());
        window.setSelectedTab("6");
        assertEquals("Integer tab", "6", renderer.getSelectedTab(window).toString());
        window.setSelectedTab("56");
        assertEquals("Dozens of tabs", "56", renderer.getSelectedTab(window).toString());
        window.setSelectedTab("whatever");
        assertEquals("Named tab", "'whatever'", renderer.getSelectedTab(window).toString());
    }

    public void testEncodeOnClose() {
        MaxInfoWindow window = new MaxInfoWindow();
        assertEquals("Default onClose", "function() {}", renderer.encodeOnClose(window).toString());
        window.setOnClose("alert(1);");
        assertEquals("onClose function", "function() {alert(1);}", renderer.encodeOnClose(window).toString());
    }

}
