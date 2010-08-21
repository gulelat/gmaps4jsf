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
package com.googlecode.gmaps4jsf.plugins.gmapsutility.cluster;

import junit.framework.TestCase;

import com.googlecode.gmaps4jsf.component.map.Map;
import com.googlecode.gmaps4jsf.plugins.gmapsutility.component.MarkerCluster;

/**
 *
 * @author Jose Noheda [jose.noheda@gmail.com]
 */
public class MarkerClusterRendererTest extends TestCase {
    
    private MarkerClusterRenderer renderer = new MarkerClusterRenderer();

    public void testConvertToJavascriptObject() {
        MarkerCluster cluster = new MarkerCluster();
        cluster.setParent(new Map());
        assertEquals("Marker custer call", "\t\t\tvar cluster = new MarkerClusterer(parent);\n\t\t\tparent.setCluster(cluster, function (parent) {\n", renderer.convertToJavascriptObject(cluster));
    }

}
