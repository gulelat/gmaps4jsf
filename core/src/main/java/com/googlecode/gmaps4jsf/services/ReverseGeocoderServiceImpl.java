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
package com.googlecode.gmaps4jsf.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.googlecode.gmaps4jsf.services.data.PlaceMark;
import com.googlecode.gmaps4jsf.util.ComponentConstants;

/**
 * <p>
 * @author Hazem Saleh
 * @date Dec 23, 2012
 * The <code>ReverseGeocoderServiceImpl</code> is implementation of the <code>ReverseGeocoderService</code> interface.
 * </p>
 */
public class ReverseGeocoderServiceImpl implements ReverseGeocoderService {
    private static ReverseGeocoderServiceImpl reverseGeocoder = new ReverseGeocoderServiceImpl();
    
    public static ReverseGeocoderServiceImpl getInstance() {
        return reverseGeocoder;
    }
    
    public PlaceMark getPlaceMark(String latitude, String longitude) throws Exception {
        try {
            URL            url       = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" 
                                     + latitude.trim()
                                     + "," 
                                     + longitude.trim() 
                                     + "&sensor=false");
            
            PlaceMark      placeMark = new PlaceMark();
            JSONObject     json      = new JSONObject(readURL(url));
            JSONArray      results   = (JSONArray) json.getJSONArray(ComponentConstants.RESULTS_LABEL);
            
            JSONObject placeMarkPrimaryData = (JSONObject) results.get(0);
            
            getPlaceMarkPrimaryInformation(placeMark, placeMarkPrimaryData);
            
            return placeMark;
        } catch (Exception exception) {
            throw new Exception("Error: " + exception.getMessage());
        }
    }
    
    private static String readURL(URL url) throws Exception {
        URLConnection  connection = url.openConnection();
        StringBuilder  builder    = new StringBuilder();
        BufferedReader reader     = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String         line;
        
        while((line = reader.readLine()) != null) {
        	builder.append(line);
        }        
        
        return builder.toString();
    }    

    /*
     * The <code>getPlaceMarkPrimaryInformation</code> is used for getting all of the place marker
     * primary information.
     */
    private void getPlaceMarkPrimaryInformation(PlaceMark placeMark, JSONObject placeMarkPrimaryData) {
        
        // Address
        try {
            placeMark.setAddress((String) placeMarkPrimaryData.get(ComponentConstants.FORMATTED_ADDRESS_LABEL));
        } catch (JSONException exception) {
        	System.err.println("Unable to get the place mark address");          	
        }
        
        // Postal code
        try {
        	JSONArray results = (JSONArray) placeMarkPrimaryData.getJSONArray(ComponentConstants.ADDRESS_COMPONENT_LABEL);
        	
        	for (int i = 0; i < results.length(); ++i) {
        		JSONObject jsonObject = results.getJSONObject(i);
        		
        		String shortName = jsonObject.getString("short_name");        		
        		JSONArray types = jsonObject.getJSONArray("types");
        		
        		for (int j = 0; j < types.length(); ++j) {
        			String attribute = types.getString(j);
        			
        			if ("postal_code".equals(attribute)) {
        				placeMark.setPostalCodeNumber(shortName);
        				break;
        			}
        		}
        	}
        	
        	
        } catch (JSONException exception) {
        	System.err.println("Unable to get the place mark address");          	
        }
    }
    
    private ReverseGeocoderServiceImpl() {
    }

}
