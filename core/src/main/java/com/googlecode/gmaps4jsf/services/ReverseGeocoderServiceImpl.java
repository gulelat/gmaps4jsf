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

/**
 * <p>
 * @author Hazem Saleh
 * @date Dec 10, 2009
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
            URL            url        = new URL("http://maps.google.com/maps/geo?q=" 
                                      + latitude.trim()
                                      + "," 
                                      + longitude.trim() 
                                      + "&output=json&sensor=false&key=abcdef");
            URLConnection  connection = url.openConnection();
            StringBuilder  builder    = new StringBuilder();
            BufferedReader reader     = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PlaceMark      placeMark  = new PlaceMark();
            String         line;
            
            while((line = reader.readLine()) != null) {
             builder.append(line);
            }
    
            JSONObject json = new JSONObject(builder.toString());
            
            JSONArray results = (JSONArray) json.getJSONArray("Placemark");
    
            JSONObject placeMarkPrimaryData = (JSONObject) results.get(0);
            
            getPlaceMarkPrimaryInformation(placeMark, placeMarkPrimaryData);
            
            return placeMark;
        } catch (Exception exception) {
            throw new Exception("Error: " + exception.getMessage());
        }
    }

    /*
     * The <code>getPlaceMarkPrimaryInformation</code> is used for getting all of the place marker
     * primary information.
     */
    private void getPlaceMarkPrimaryInformation(PlaceMark placeMark, JSONObject placeMarkPrimaryData) {
        
        // ID
        try {
            placeMark.setId((String) placeMarkPrimaryData.get("id"));            
        } catch (JSONException exception) {
            //System.out.println("[warning] id is not available ...");
        }
        
        // ADDRESS
        try {
            placeMark.setAddress((String) placeMarkPrimaryData.get("address"));
        } catch (JSONException exception) {
            //System.out.println("[warning] address is not available ...");
        }        
        
        try {
            
            // ADDRESS DETAILS
            JSONObject addressDetailsObject = (JSONObject) placeMarkPrimaryData.get("AddressDetails");            
            
            // ACCURACY
            try {
                placeMark.setAccuracy((Integer) addressDetailsObject.get("Accuracy"));
            } catch (JSONException exception) {
                //System.out.println("[warning] accuracy is not available ...");
            }
            
            try {
                
                // COUNTRY                
                JSONObject country = (JSONObject) addressDetailsObject.get("Country");  
                
                // POSTAL CODE.
                try { 
                   String postalCode = ((JSONObject) ((JSONObject) ((JSONObject) country.get("AdministrativeArea")).get("Locality")).get("PostalCode")).get("PostalCodeNumber").toString();

                   placeMark.setPostalCodeNumber(postalCode);
                } catch (Exception exception) {
                    try {
                        String postalCode = ((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) country.get("AdministrativeArea")).get("SubAdministrativeArea")).get("Locality")).get("DependentLocality")).get("PostalCode")).get("PostalCodeNumber").toString();
                        
                        placeMark.setPostalCodeNumber(postalCode);
                    } catch (Exception innerException) {
                        try {
                            String postalCode = ((JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) country.get("AdministrativeArea")).get("SubAdministrativeArea")).get("Locality")).get("PostalCode")).get("PostalCodeNumber").toString();
                            
                            placeMark.setPostalCodeNumber(postalCode);
                        } catch (Exception innerException2) {
                        }
                    }
                }
                
                // COUNTRYNAME
                try {
                    placeMark.setCountryName((String) country.get("CountryName"));
                } catch (JSONException exception) {
                    //System.out.println("[warning] countryName is not available ...");
                }                
                
                // COUNTRYNAMECODE
                try {
                    placeMark.setCountryCode((String) country.get("CountryNameCode"));
                } catch (JSONException exception) {
                    //System.out.println("[warning] countryNameCode is not available ...");
                }
                
            } catch (JSONException exception) {
                //System.out.println("[warning] country is not available ...");
            }                
            

        } catch (JSONException exception) {
            //System.out.println("[warning] addressDetailsObject is not available ...");
        }
    }    
    
    private ReverseGeocoderServiceImpl() {
    }

}
