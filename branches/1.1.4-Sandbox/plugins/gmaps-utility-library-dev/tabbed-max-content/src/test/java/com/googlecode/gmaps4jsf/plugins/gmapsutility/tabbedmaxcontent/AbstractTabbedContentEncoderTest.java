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

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import junit.framework.TestCase;
import com.googlecode.gmaps4jsf.component.marker.Marker;
import com.googlecode.gmaps4jsf.plugins.gmapsutility.component.Tab;
import com.googlecode.gmaps4jsf.plugins.gmapsutility.component.MaxInfoWindow;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class AbstractTabbedContentEncoderTest extends TestCase {

    private AbstractTabbedContentEncoder encoder = new AbstractTabbedContentEncoder() {

        public Class getModifiedComponent() {
            return Marker.class;
        }

        public String encodeFunctionScriptCall(FacesContext facesContext, UIComponent parent) throws IOException {
            return "";
        }
    };

    public void testGetSelectedTab() {
        MaxInfoWindow window = new MaxInfoWindow();
        assertEquals("Default tab", "0", encoder.getSelectedTab(window).toString());
        window.setSelectedTab("6");
        assertEquals("Integer tab", "6", encoder.getSelectedTab(window).toString());
        window.setSelectedTab("56");
        assertEquals("Dozens of tabs", "56", encoder.getSelectedTab(window).toString());
        window.setSelectedTab("whatever");
        assertEquals("Named tab", "'whatever'", encoder.getSelectedTab(window).toString());
    }

    public void testGetTabContent() {
        Tab tab = new Tab();
        assertEquals("No tab content", "null", encoder.getTabContent(tab).toString());
        tab.setContent("Something");
        assertEquals("Tab content", "'Something'", encoder.getTabContent(tab).toString());
    }

    public void testGetTabOnSelect() {
        Tab tab = new Tab();
        assertEquals("No tab select function", "null", encoder.getTabOnSelect(tab).toString());
        tab.setOnSelect("alert(1);");
        assertEquals("JS function on select", "function (tab) {alert(1);}", encoder.getTabOnSelect(tab).toString());
    }

    public void testEncodeOnClose() {
        StringBuffer buffer = new StringBuffer();
        MaxInfoWindow window = new MaxInfoWindow();
        encoder.encodeOnClose(window, buffer);
        assertEquals("No onClose function", "var onClose;", buffer.toString());
        window.setOnClose("alert(1);");
        buffer = new StringBuffer();
        encoder.encodeOnClose(window, buffer);
        assertEquals("onClose function", "var onClose = function() {alert(1);};", buffer.toString());
    }

    public void testEncodeButtons() {
        StringBuffer buffer = new StringBuffer();
        MaxInfoWindow window = new MaxInfoWindow();
        window.setShowCloseButton(false);
        window.setShowMaximizeButton(false);
        window.setShowMinimizeButton(false);
        encoder.encodeButtons(window, buffer);
        assertEquals("No buttons", "var buttons = {close: {show: 4},maximize: {show: 4},restore: {show: 4}};", buffer.toString());
        buffer = new StringBuffer();
        window.setShowCloseButton(true);
        encoder.encodeButtons(window, buffer);
        assertEquals("Remove close button", "var buttons = {maximize: {show: 4},restore: {show: 4}};", buffer.toString());
        buffer = new StringBuffer();
        window.setShowMaximizeButton(true);
        encoder.encodeButtons(window, buffer);
        assertEquals("Only minimize button", "var buttons = {restore: {show: 4}};", buffer.toString());
        buffer = new StringBuffer();
        window.setShowMinimizeButton(true);
        encoder.encodeButtons(window, buffer);
        assertEquals("All buttons", "var buttons = {};", buffer.toString());
    }

}
