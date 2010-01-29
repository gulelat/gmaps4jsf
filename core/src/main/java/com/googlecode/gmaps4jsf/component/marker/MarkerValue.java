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
package com.googlecode.gmaps4jsf.component.marker;

/**
 * @author Hazem Saleh
 * @date May 22, 2009
 * The MarkerValue is used as the inner data structure of the Marker.
 */
public class MarkerValue implements java.io.Serializable {
	String longitude;
	String latitude;
	
	public MarkerValue() {	
	}
	
	public MarkerValue(String longitude, String latitude) {
		super();
		this.longitude = longitude;
		this.latitude  = latitude;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public boolean equals(Object object) {
		if (object != null && object instanceof MarkerValue) {
            MarkerValue marker = (MarkerValue) object;
            
			return (longitude.equals(marker.getLongitude()) &&
					latitude.equals(marker.getLatitude()));  
		}
		
		return false;
	}

	public String toString() {
		return "(" + longitude + ", " + latitude + ")";
	}
}
