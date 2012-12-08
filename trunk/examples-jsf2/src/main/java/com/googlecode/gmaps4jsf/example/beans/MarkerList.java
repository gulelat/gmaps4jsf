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
package com.googlecode.gmaps4jsf.example.beans;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hazem Saleh
 */
public class MarkerList {
	private double[] latitudes  = new double[] {31.14, 50};
	private double[] longitudes = new double[] {30.01, 30};
   
	private List markers;
    private String status;
    
    public MarkerList() {
        markers = new ArrayList();
        for (int i = 0; i < 2; i++) {
        	TestMarker marker = new TestMarker();
        	
        	marker.setLatitude(latitudes[i] + "");
        	marker.setLongitude(longitudes[i] + "");
            marker.setJsVariable("marker_" + (i + 1));
            
        	markers.add(marker);
        }
    }

    public List getData() {
        return markers;
    }
    
	public void setStatus(String status) {
		this.status = status;
	}       
    
	public String getStatus() {
		return status;
	}    
}
