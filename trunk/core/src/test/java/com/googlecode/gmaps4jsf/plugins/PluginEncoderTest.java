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
package com.googlecode.gmaps4jsf.plugins;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import junit.framework.TestCase;
import java.util.List;
import com.googlecode.gmaps4jsf.component.map.Map;


/**
 * Checks the plugin encoder start up methods.
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class PluginEncoderTest extends TestCase {

    public void testSetUp() {
        assertEquals("Static initializer called", 2, PluginEncoder.getPlugins().size());
        assertEquals("Plugins.txt parsed", 2, ((List) PluginEncoder.getPlugins().get(Map.class)).size());
    }

    public static class PluginImpl implements Plugin {

        public String encodeFunctionScript(FacesContext facesContext, UIComponent parent) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String encodeFunctionScriptCall(FacesContext facesContext, UIComponent parent) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Class getModifiedComponent() {
            return Map.class;
        }

    }

    public static class AnotherPluginImpl implements Plugin {
        public String encodeFunctionScript(FacesContext facesContext, UIComponent parent) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public String encodeFunctionScriptCall(FacesContext facesContext, UIComponent parent) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public Class getModifiedComponent() {
            return Map.class;
        }
    }

}
