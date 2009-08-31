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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.googlecode.gmaps4jsf.component.map.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

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
        plugins = new HashMap(1);
        plugins.put(Map.class, new ArrayList());
        try {
            URL pluginsFile = PluginEncoder.class.getResource("/META-INF/plugins.txt");
            Scanner scanner = new Scanner(pluginsFile.openStream());
            while (scanner.hasNext()) {
                String pluginClass = scanner.nextLine();
                Plugin plugin = (Plugin) PluginEncoder.class.forName(pluginClass).newInstance();
                register(plugin.getModifiedComponent(), plugin);
            }
        } catch (Exception ex) {
            System.out.println("--->PLUGINS SYSTME FAILED: " + ex.getMessage());
        }
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
            writer.write(((Plugin) it.next()).encodeFunctionScript(facesContext, map));
        }
    }

    /**
     * Invoked by MapRenderer to call the plugins build functions.
     */
    public static void encodeMapPluginsFunctionCalls(FacesContext facesContext, Map map, ResponseWriter writer) throws IOException {
        for (Iterator it = ((List) plugins.get(Map.class)).iterator(); it.hasNext();) {
            writer.write(((Plugin) it.next()).encodeFunctionScriptCall(facesContext, map));
        }
    }

}
