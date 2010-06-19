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

import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Collections;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.component.marker.Marker;
//import com.googlecode.gmaps4jsf.component.marker.MarkerEncoder;
import com.googlecode.gmaps4jsf.util.FileReaderUtils;

/**
 * Enables plugins to register themselfes to be rendered by their parent
 * component.
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public final class PluginEncoder {

    private static java.util.Map plugins;

    private PluginEncoder() {
        throw new AssertionError("Do not instantiate this class");
    }

    static {
        plugins = new HashMap(2);
        plugins.put(Map.class, new ArrayList());
        plugins.put(Marker.class, new ArrayList());
        String[] readedPlugins = FileReaderUtils.readLines("/META-INF/plugins.txt");
        for (int index = 0; index < readedPlugins.length; index++) {
            String pluginClass = readedPlugins[index];
            try {
                Plugin plugin = (Plugin) Class.forName(pluginClass).newInstance();
                register(plugin.getModifiedComponent(), plugin);
            } catch (Exception ex) {
                System.out.println("Gmaps4Jsf plugin system failed to initialize properly: " + ex.getMessage());
            }
        }
    }

    /**
     * For testing purposes.
     *
     * @return a non null non modifiable map
     */
    protected static java.util.Map getPlugins() {
        return plugins == null ? Collections.EMPTY_MAP : Collections.unmodifiableMap(plugins);
    }

    /**
     * Adds a new plugin to the list of available plugins for a specific 
     * core component.
     *
     * @param component
     * @param plugin
     */
    public static synchronized void register(Class component, Plugin plugin) {
        ((List) plugins.get(component)).add(plugin);
    }

    /**
     * Invoked by MapRenderer to create the JS code required by Map plugins.
     *
     */
    public static void encodeMapPluginsFunctionScripts(FacesContext facesContext, Map map, ResponseWriter writer) throws IOException {
        for (Iterator it = ((List) plugins.get(Map.class)).iterator(); it.hasNext();) {
            String function  = ((Plugin) it.next()).encodeFunctionScript(facesContext, map);
            if (function != null) { 
                writer.write(function);
            }
        }
    }

    /**
     * Invoked by MapRenderer to call the plugins build functions.
     */
    public static void encodeMapPluginsFunctionCalls(FacesContext facesContext, Map map, ResponseWriter writer) throws IOException {
        for (Iterator it = ((List) plugins.get(Map.class)).iterator(); it.hasNext();) {
            String functionCall = ((Plugin) it.next()).encodeFunctionScriptCall(facesContext, map);
            if (functionCall != null) {
                writer.write(functionCall);
            }
        }
    }

    /**
     * Invoked by MarkerEncoder to create the JS code required by Map plugins.
     *
     */
    public static void encodeMarkerPluginsFunctionScripts(FacesContext facesContext, Marker marker, ResponseWriter writer) throws IOException {
        for (Iterator it = ((List) plugins.get(Marker.class)).iterator(); it.hasNext();) {
            String function  = ((Plugin) it.next()).encodeFunctionScript(facesContext, marker);
            if (function != null) {
                writer.write(function);
            }
        }
    }

    /**
     * Invoked by MarkerEncoder to call the plugins build functions.
     */
    public static void encodeMarkerPluginsFunctionCalls(FacesContext facesContext, Marker marker, ResponseWriter writer) throws IOException {
        for (Iterator it = ((List) plugins.get(Marker.class)).iterator(); it.hasNext();) {
            String functionCall = ((Plugin) it.next()).encodeFunctionScriptCall(facesContext, marker);
            if (functionCall != null) {
                writer.write(functionCall);
            }
        }
    }
    
    
    public static String getUniqueMarkerId(FacesContext facesContext, Marker marker) {
        return null;//MarkerEncoder.getUniqueMarkerId(facesContext, marker);
    }       

}
