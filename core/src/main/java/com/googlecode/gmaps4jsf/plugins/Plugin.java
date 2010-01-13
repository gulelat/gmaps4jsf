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

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * Base interface to be implement by plugins.
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public interface Plugin {

    /**
     * 
     * @return clazz of the parent component
     */
    Class getModifiedComponent();

    /**
     * Method to be invoked during the rendering of the parent component
     * to include the client code needed to show this plugin on screen.
     *
     * @param facesContext the current JSF context
     * @param parent the tag being rendered
     * @return the JS code to write in the page to render this plugin
     */
    String encodeFunctionScript(FacesContext facesContext, UIComponent parent) throws IOException;

    /**
     * JS code that can invoke the rendering of this plugin in the client.
     *
     * @param facesContext the current JSF context
     * @param parent the tag being rendered
     * @return a call to a build function
     */
    String encodeFunctionScriptCall(FacesContext facesContext, UIComponent parent) throws IOException;

}
